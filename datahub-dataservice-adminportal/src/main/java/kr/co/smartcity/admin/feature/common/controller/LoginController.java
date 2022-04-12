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
package kr.co.smartcity.admin.feature.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.admin.common.entity.UserInfo;
import kr.co.smartcity.admin.feature.common.service.LoginService;

@Controller
@RequestMapping(value="/login")
public class LoginController{
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * 로그인 페이지 이동
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 26.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageLogin.do", produces="text/plain;charset=UTF-8")
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		String contextPath = request.getContextPath();
		
		if(session.getAttribute("authToken") != null) {
			response.sendRedirect(contextPath + "/pageIndex.do");
		}
		return "login";
	}
	
	/**
	 * Oauth 로그인 페이지 이동
	 * @Author      : kyunga
	 * @Date        : 2019. 9. 26.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@RequestMapping(value = "/oauthlogin.do", produces="text/plain;charset=UTF-8")
	public void loginOauth(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 로그인 화면 이동 전 인증/인가 sync를 위해 logout 선 호출 후 로그인 화면 이동하도록 추가.
		try {
			loginService.postLogout(request, response);	
		} catch (Exception ex) {
			// 로그아웃 200(OK) 아니여도 오류 무시하며, 계속 진행 
		}
		
		response.sendRedirect(loginService.authorizationCode(request,response));
	}
	
	/**
	 * 로그인
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 26.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String loginProc(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
		return loginService.login(params, request);
	}
	
	/**
	 * 로그아웃
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 26.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout.do", produces="application/text;charset=UTF-8")
	public @ResponseBody String logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return loginService.postLogout(request, response);
	}
	
	/**
	 * 
	 * redirect - access/RefreshToken 발급 / 사용자 정보조회 원래 요청 페이지로 리다이렉트
	 * @Author      : kyunga
	 * @Date        : 2019. 9. 19.
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/redirect.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody void redirect(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, String> userMap = new HashMap<String,String>();
		HttpSession session = request.getSession();
		String contextPath = request.getContextPath();

		try {
			//엑세스 토큰 쿠키 저장
			String accessToken=loginService.accessRefreshToken(request);
			
			
			//로그인 사용자 정보
			UserInfo userInfo = loginService.getUserInfo(accessToken);
			session.setAttribute("user", userInfo);
			
			userMap.put("userId", userInfo.getUserId());
			
			Cookie setCookie = new Cookie("chaut", accessToken);
			setCookie.setPath("/");
			setCookie.setMaxAge(60*60*24*30); // 30일
			response.addCookie(setCookie);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		response.sendRedirect(contextPath + "/pageIndex.do");
	}
}
