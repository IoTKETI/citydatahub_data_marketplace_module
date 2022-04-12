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
package kr.co.smartcity.admin.common.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import kr.co.smartcity.admin.common.entity.UserInfo;
import kr.co.smartcity.admin.common.util.CodeUtil;
import kr.co.smartcity.admin.common.util.MenuTreeUtil;
import kr.co.smartcity.admin.common.util.StringUtil;
import kr.co.smartcity.admin.feature.codegroup.service.CodeGroupService;
import kr.co.smartcity.admin.feature.menu.service.MenuService;
import kr.co.smartcity.admin.feature.menu.vo.MenuVo;


public class PageInterceptor extends WebContentInterceptor {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private CodeGroupService codeGroupService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		
		String requestUri = request.getRequestURI();
		logger.debug("::PageInterceptor RequestURI : " + requestUri);
		
		try {
			// 코드 설정(static)
			if(CodeUtil.isEmpty()) {
				codeGroupService.refresh();
			}
			
			// 메뉴 서버 렌더링
			this.setMenuTreeHtml(request);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new ServletException(e);
		}
		return super.preHandle(request, response, handler);
	}
	
	/**
	 * <pre>메뉴 Tree 서버 렌더링</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 22.
	 * @param request
	 * @throws Exception
	 */
	private void setMenuTreeHtml(HttpServletRequest request) throws Exception {
		String nav = request.getParameter("nav");
		if(StringUtil.isNotEmpty(nav)) {
			request.getSession().setAttribute("_nav", nav);
		}
		
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("menuGbCd", "adminPortal");
		params.put("userId", userInfo.getUserId());
		List<MenuVo> menuList = menuService.getAuthMenuList(params);
	
		String lnbHtml = MenuTreeUtil.getLnbHtml(menuList, request.getContextPath());
		request.setAttribute("lnbHtml", lnbHtml);
	}

}
