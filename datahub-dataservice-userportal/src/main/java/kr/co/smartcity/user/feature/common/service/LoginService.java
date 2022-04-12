/*******************************************************************************
 * BSD 3-Clause License
 * 
 * Copyright (c) 2021, N2M
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package kr.co.smartcity.user.feature.common.service;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import kr.co.smartcity.user.common.component.AuthComponent;
import kr.co.smartcity.user.common.component.HttpComponent;
import kr.co.smartcity.user.common.entity.UserInfo;
import kr.co.smartcity.user.common.exceptions.HttpInternalServerErrorException;
import kr.co.smartcity.user.common.util.StringUtil;
import kr.co.smartcity.user.common.vo.UserVo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

@Service
public class LoginService extends HttpComponent {
	public final static MediaType JSON = MediaType.get("application/json; charset=utf-8");
	@Autowired
	OkHttpClient client;
	@Autowired Gson gson;
	@Autowired
	AuthComponent authComponent;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	/**
	 * 
	 * 인증/인가 로그인 화면 이동
	 * @param request 
	 * @param response 
	 * @Author      : kyunga
	 * @Date        : 2019. 9. 19.
	 * @param 
	 * @return
	 * @throws HttpInternalServerErrorException 
	 */
	public String authorizationCode(HttpServletRequest request, HttpServletResponse response) throws HttpInternalServerErrorException {
		String authorizationCodeurl="";
		HttpSession session = request.getSession();
		String sessionid = session.getId();
		String SHA="";
		
        //state 암호화
		try{
			 MessageDigest md = MessageDigest.getInstance("SHA-256");
			 md.update(sessionid.getBytes());
			 SHA = byteToHexString(md.digest());
        } catch(Exception ex){
        	throw new HttpInternalServerErrorException("state 생성이 실패 되었습니다.");
        }
		
		authorizationCodeurl = props.getAuthorizationCodeUrl()
								+ "?response_type=code"
								+ "&redirect_uri=" + props.getOauthRedirectUri()
								+ "&client_id=" + props.getNormalClientId()
								+ "&state="+SHA;
		return authorizationCodeurl;
	}
	/**
	 * 
	 *  sha256
	 * @Author      : kyunga
	 * @Date        : 2019. 9. 19.
	 * @param 
	 * @return
	 */
	public String byteToHexString(byte[] data) {
	    StringBuilder sb = new StringBuilder();
	    for(byte b : data) {
	        sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));

	    }
	    return sb.toString();

	}
	
	/**
	 * 
	 * 로그인 성공 후 토큰 발급
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public UserInfo accessRefreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String code = request.getParameter("code");
		
		logger.debug("code :::::::::" + code);
		
		String userId = "";
		String tokenJson = (String) postAccessToken(HttpMethod.POST.toString(),props.getNormalAccessTokenUrl(),code).getBody();
		
		Map<String, Object> tokenMap = gson.fromJson(tokenJson, Map.class);
		
		String accessToken = (String) tokenMap.get("access_token");
		
		Cookie setCookie = new Cookie("chaut", accessToken);
		setCookie.setPath("/");
		if(accessToken !=null) {
			setCookie.setMaxAge(60*60*24*30);
		}else {
			setCookie.setMaxAge(0);
		}
		response.addCookie(setCookie);
		request.getSession().setAttribute("refreshtoken", (String) tokenMap.get("refresh_token"));
		request.getSession().setAttribute("authToken", accessToken);
		
		userId = authComponent.getUserId(accessToken);
		
		return getUserInfo(request,userId, accessToken);
	}
	/**
	 * 
	 * 로그인 사용자 정보 호출
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param request
	 * @param requestUserId
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public UserInfo getUserInfo(HttpServletRequest request,String requestUserId, String accessToken) throws Exception {
		
		logger.debug("Request User ID : " + requestUserId);
		logger.debug("AccessToken : " + accessToken);
		
		if ( StringUtil.isEmpty(requestUserId)) {
			throw new Exception("요청한 ID가 잘못 되었습니다.");
		}
		
		UserVo userVo = this.getUserByType(authComponent.getUserId(accessToken), UserType.NORMAL);
		return new UserInfo(userVo.getUserId(), userVo.getNickname(), userVo.getName(), userVo.getPhone(), userVo.getEmail());

	}
	/**
	 * 
	 * oauth2 로그인 성공 후  시큐리티 로그인 수동 처리
	 * @Author      : kyunga
	 * @Date        : 2019. 9. 24.
	 * @param req
	 * @param userInfo
	 */
	public void securityLogin(HttpServletRequest req, UserInfo userInfo) {
		//권한설정  
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("USER"));
		
		SecurityContext sc = SecurityContextHolder.getContext();
		//아이디, 패스워드, 권한 설정
		//패스워드  null
		sc.setAuthentication(new UsernamePasswordAuthenticationToken(userInfo, null, list));
		HttpSession session = req.getSession(true);
		
		//security 세션 설정
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
	}
	
	/**
	 * 
	 * 로그아웃 세션/쿠키 삭제
	 * @Author      : kyunga
	 * @Date        : 2019. 9. 24.
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return
	 */
	public ResponseEntity<Object> postLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String contextPath = request.getContextPath();
		Cookie[] cookies = request.getCookies();
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userInfo.getUserId());
		
		try {
			String json = this.strBody(method("POST", contextPath + props.getLogoutUrl(), params));
			logger.debug("LOGOUT RESULT : " + json);
		} catch (Exception e) { 
			logger.debug("LOGOUT ==> " + e.getMessage());
		}
		
		if (cookies != null) { 
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("chaut")) {
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
					break;
				}
			}
		}
		
		session.invalidate();
		
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
}
