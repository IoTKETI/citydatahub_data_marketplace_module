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
	 * ????????? ????????? ( ?????? ????????????  )
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
		
		// ????????? ?????? ?????? ??? ??????/?????? sync??? ?????? logout ??? ?????? ??? ????????? ?????? ??????????????? ??????.
		try {
			loginService.postLogout(request, response);	
		} catch (Exception ex) {
			// ???????????? 200(OK) ???????????? ?????? ????????????, ?????? ?????? 
		}
		// ?????? ?????? ?????? ??????
		session.setAttribute("redirectUri", request.getHeader("Referer"));
    
		response.sendRedirect(loginService.authorizationCode(request,response));
	}
	/**
	 * 
	 * redirect - access/RefreshToken ?????? / ????????? ???????????? ?????? ?????? ???????????? ???????????????
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
		
		//????????? ????????? ??????
		session.setAttribute("user", userInfo);
		//????????? ???????????? ????????????
		loginService.securityLogin(request,userInfo);
		//????????? ??????
		String redirectUri = (String) session.getAttribute("redirectUri");
		
		logger.debug("redirectUri : " + redirectUri);
		
		if (StringUtil.isEmpty(redirectUri)) {
			// ??????????????? ??????
			response.sendRedirect(request.getContextPath() + "/main/pageMain.do");
		} else {
			if ( redirectUri.contains(props.getAuthNormalServerUrl()) || redirectUri.contains("/oauthlogin.do")) {
				// ??????????????? ??????
				response.sendRedirect(request.getContextPath() + "/main/pageMain.do");
			} else {
				response.sendRedirect((String) session.getAttribute("redirectUri"));
			}
		}
	}
	
	/**
	 * 
	 * ???????????? ??????/?????? ??????
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
