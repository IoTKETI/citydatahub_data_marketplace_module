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
package kr.co.smartcity.admin.feature.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.smartcity.admin.common.entity.UserInfo;
import kr.co.smartcity.admin.common.util.StringUtil;
import kr.co.smartcity.admin.feature.menu.service.MenuService;
import kr.co.smartcity.admin.feature.menu.vo.MenuVo;

@Controller
public class MainController  {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private MenuService menuService;

	/**
	 * <pre>메인 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @GetMapping(value = "/pageIndex.do")
    public String indexPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
		
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("menuGbCd", "adminPortal");
		params.put("userId", userInfo.getUserId());
		List<MenuVo> menuList = menuService.getAuthMenuList(params);
		
		// 관리자 로그인시 최상단 메뉴로 이동
		MenuVo targetMenu = null;
		for (MenuVo menu : menuList) {
			if (menu.getMenuParentId() == 0 || StringUtil.isEmpty(menu.getMenuUrl())) {
				continue;
			}
			
			long ord = menuList.get(menuList.size() - 1).getMenuOrd();
				
			if (ord >= menu.getMenuOrd()) {
				targetMenu = menu;
				break;
			}
		}
		response.sendRedirect(request.getContextPath() + targetMenu.getMenuUrl() + "?nav=" + targetMenu.getMenuId());
		
    	return "main/main";
    }
}
