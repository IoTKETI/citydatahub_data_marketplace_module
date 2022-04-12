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
package kr.co.smartcity.user.feature.common.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.user.common.component.AuthComponent;
import kr.co.smartcity.user.common.component.HttpComponent;
import kr.co.smartcity.user.common.entity.UserInfo;
import kr.co.smartcity.user.common.util.StringUtil;
import kr.co.smartcity.user.feature.common.service.LoginService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/login")
public class LoginController extends HttpComponent{
	
	@Resource(name = "loginService")
	private LoginService loginService;
	
	@Autowired
	AuthComponent authComponent;
	/**
	 * 
	 * 로그인 페이지 ( 현재 사용안함  )
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageLogin.do", produces="text/plain;charset=UTF-8")
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "login/login";
	}
	/**
	 * 
	 * getauthorizationCode
	 * @Author      : kyunga
	 * @Date        : 2019. 9. 19.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/oauthlogin.do", produces="text/plain;charset=UTF-8")
	public void getauthorizationCode(HttpServletRequest request,HttpServletResponse response, HttpSession session, Authentication authentication) throws Exception {
		
		// 로그인 화면 이동 전 인증/인가 sync를 위해 logout 선 호출 후 로그인 화면 이동하도록 추가.
		try {
			loginService.postLogout(request, response);	
		} catch (Exception ex) {
			// 로그아웃 200(OK) 아니여도 오류 무시하며, 계속 진행 
		}
		// 이전 호출 주소 저장
		session.setAttribute("redirectUri", request.getHeader("Referer"));
    
		response.sendRedirect(loginService.authorizationCode(request,response));
	}
	/**
	 * 
	 * redirect - access/RefreshToken 발급 / 사용자 정보조회 원래 요청 페이지로 리다이렉트
	 * @Author      : kyunga
	 * @Date        : 2019. 9. 19.
	 * @param
	 * @throws Exception 
	 * @return
	 */
	@RequestMapping(value = "/redirect.do", produces="text/plain;charset=UTF-8")
	public void redirect(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws Exception{
		HttpSession session = request.getSession();
		UserInfo userInfo = loginService.accessRefreshToken(request,response);
		
		logger.debug("UserInfo : " + userInfo.toString());
		
		//로그인 사용자 정보
		session.setAttribute("user", userInfo);
		//스프링 시큐리티 수동처리
		loginService.securityLogin(request,userInfo);
		//페이지 이동
		String redirectUri = (String) session.getAttribute("redirectUri");
		
		logger.debug("redirectUri : " + redirectUri);
		
		if (StringUtil.isEmpty(redirectUri)) {
			// 메인페이지 이동
			response.sendRedirect(request.getContextPath() + "/main/pageMain.do");
		} else {
			if ( redirectUri.contains(props.getAuthNormalServerUrl()) || redirectUri.contains("/oauthlogin.do")) {
				// 메인페이지 이동
				response.sendRedirect(request.getContextPath() + "/main/pageMain.do");
			} else {
				response.sendRedirect((String) session.getAttribute("redirectUri"));
			}
		}
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
	@RequestMapping(value = "/logout.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public Object loginout(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		return loginService.postLogout(request, response);
	}

}
