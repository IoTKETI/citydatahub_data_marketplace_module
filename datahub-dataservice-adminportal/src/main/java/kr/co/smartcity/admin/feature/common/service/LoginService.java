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
package kr.co.smartcity.admin.feature.common.service;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.admin.common.component.AuthComponent;
import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.common.entity.UserInfo;
import kr.co.smartcity.admin.common.util.StringUtil;
import kr.co.smartcity.admin.feature.user.service.UserService;
import kr.co.smartcity.admin.feature.user.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;

@Slf4j
@Service("loginService")
public class LoginService extends HttpComponent{
	
	@Autowired
	private UserService userService;
	
	public final static MediaType JSON = MediaType.get("application/json; charset=utf-8");
	
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);			
	
	@Autowired
	AuthComponent authComponent;
	
	/**
	 * <pre>로그인</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String login(Map<String, Object> params, HttpServletRequest request) throws Exception {
		Map<String, Object> resMap      = new HashMap<>();
		String resCd       = "";
		
		String responseString           = (String)(userService.login(params));
		Map<String, Object> responseMap = toMap(responseString);
		Map<String, Object> userMap = null;
		
		resCd = (String) responseMap.get("resCd");
		userMap = (Map<String, Object>) responseMap.get("user");
		
		if(StringUtil.equals(resCd, "100")) {
			try {
				String authToken = "test";
				HttpSession session = request.getSession();
				
				session.setAttribute("authToken", authToken);
				session.setAttribute("user", userMap);
				
				resMap.put("authToken", authToken);
			} catch (Exception e) { 
				resCd = "103";
			}
		}
		resMap.put("resCd", resCd);
		return toJson(resMap);
	}
	/**
	 * 
	 * authorizationCode get
	 * @param authentication 
	 * @param response 
	 * @Author      : kyunga
	 * @Date        : 2019. 9. 19.
	 * @param 
	 * @return
	 */
	public String authorizationCode(HttpServletRequest request, HttpServletResponse response) {
		String authorizationCodeurl="";
		HttpSession session = request.getSession();
		String sessionid = session.getId();
		String sha="";

        //state 암호화
		try{
			 MessageDigest md = MessageDigest.getInstance("SHA-256");
			 md.update(sessionid.getBytes());
			 sha = byteToHexString(md.digest());
        } catch(Exception ex){
        }
		
		authorizationCodeurl = props.getAuthorizationCodeUrl()
		+ "?response_type=code"
		+ "&redirect_uri=" + props.getOauthRedirectUri()
		+ "&client_id=" +props.getAdminClientId()
		+ "&state="+sha;
		logger.debug("authorizationcode :::::::::::" + authorizationCodeurl);
		return authorizationCodeurl;
	}
	/**
	 * 
	 * sha256
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
	 * <pre>액세스토큰 및 리프레시토큰 발급</pre>
	 * @Author      : kyunga
	 * @Date        : 2019. 10. 1.
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public String accessRefreshToken(HttpServletRequest request) throws Exception {
		String code = request.getParameter("code");
		String tokenJson = (String) postAccessToken(HttpMethod.POST, props.getAdminAccessTokenUrl(), code);
		
		log.debug("::::: Issued token : " + tokenJson);
		
		Map<String, Object> tokenMap = toMap(tokenJson);
		String accessToken = (String) tokenMap.get("access_token");
		request.getSession().setAttribute("authToken", accessToken);
		request.getSession().setAttribute("refreshtoken", (String) tokenMap.get("refresh_token"));
		return accessToken;
	}

	/**
	 * <pre>사용자 정보 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public UserInfo getUserInfo(String accessToken) throws Exception {
		
		UserVo userVo = this.getUserByType(authComponent.getUserId(accessToken), UserType.ADMIN);
		return new UserInfo(userVo.getUserId(), userVo.getNickname(), userVo.getName(), userVo.getPhone(), userVo.getEmail());
	}

	/**
	 * <pre>로그아웃</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String postLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String contextPath = request.getContextPath();
		Cookie[] cookies = request.getCookies();
		UserInfo userInfo = (UserInfo) session.getAttribute("user");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userInfo.getUserId());
		
		String result = method(HttpMethod.POST, contextPath + props.getLogoutUrl(), params);
		
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

		return result;
	}
}
