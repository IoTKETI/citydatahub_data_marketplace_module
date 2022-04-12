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
package kr.co.smartcity.user.common.interceptor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import kr.co.smartcity.user.common.entity.UserInfo;
import kr.co.smartcity.user.common.util.CodeUtil;
import kr.co.smartcity.user.common.util.MenuTreeUtil;
import kr.co.smartcity.user.common.util.StringUtil;
import kr.co.smartcity.user.common.vo.MenuVo;
import kr.co.smartcity.user.feature.codegroup.service.CodeGroupService;
import kr.co.smartcity.user.feature.common.service.LoginService;
import kr.co.smartcity.user.feature.main.service.MainService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageInterceptor extends WebContentInterceptor {

	@Autowired
	private MainService mainService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private CodeGroupService codeGroupService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		
		try {
			log.debug("Check [SSO] ::: " +  this.isRedirectSSO(request, response));
			
			if (this.isRedirectSSO(request, response)) {
				response.sendRedirect(loginService.authorizationCode(request,response));
				return false;
			}
			
			log.debug("Check [CODE] ==> ");
			// 코드 설정(static)
			if(CodeUtil.isEmpty()) {
				codeGroupService.refresh();
			}
			
			log.debug("Check [MENU] ==> ");
			// 메뉴 정보 attr 추가
			this.setMenuTreeHtml(request);
			
			log.debug("Check [USER] ==> ");
			// 사용자 정보 attr 추가
			this.setUserInfo(request);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new ServletException(e);
		}
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("ssoSite", "");
		}
		
		super.afterCompletion(request, response, handler, ex);
	}

	/**
	 * <pre>SSO 처리 여부</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 27.
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean isRedirectSSO(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String referer 		= request.getHeader("Referer");
		
		if (StringUtil.isNotEmpty(referer)) {
			String redirectUri 	= request.getRequestURL().toString();
			String ssoSite		= (String) session.getAttribute("ssoSite");
			
			List<String> checkSite = mainService.props.getAuthSSOSite();
			
			for (String site : checkSite) {
				if ( referer.contains(site) && StringUtil.isEmpty(ssoSite)) {
					session.setAttribute("redirectUri", redirectUri);
					session.setAttribute("ssoSite", referer);
					
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * <pre>포탈 메뉴 서버 렌더링 및 Attribute 추가</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 27.
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void setMenuTreeHtml(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		Map<String, String> params = new HashMap<String, String>();
		params.put("menuGbCd", "normalPortal");
		
		List<MenuVo> menuList = mainService.getAuthMenuList(params);
		Map<String, Object> lnb = (Map<String, Object>) session.getAttribute("lnb");
		
		String gnbHtml = MenuTreeUtil.getGnbHtml(menuList, Arrays.asList(new String[] {"MNU035"}), request.getContextPath());
		lnb = MenuTreeUtil.getLnbHtml(menuList, request.getContextPath());
		
		session.setAttribute("lnb", lnb);
		session.setAttribute("gnbHtml", gnbHtml);
		session.setAttribute("mypageUrl", MenuTreeUtil.findMypageFirstUrl(menuList, "MNU035"));
		
		List<MenuVo> navigation = MenuTreeUtil.findNavigationByUrl(menuList, request.getRequestURI());
		if (navigation != null && navigation.size() > 0) {
			
			request.setAttribute("navigation", navigation);
			request.setAttribute("lnbHtml", lnb.get(String.valueOf(navigation.get(0).getMenuOid())));
		}
	}

	/**
	 * <pre>ContextHolder에 저장 된 사용자 정보 Attribute 추가</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 27.
	 * @param request
	 * @throws Exception
	 */
	private void setUserInfo(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal != null && principal instanceof UserInfo) {
			UserInfo userInfo = (UserInfo) principal;
			session.setAttribute("userInfo", userInfo);
		}
	}
}
