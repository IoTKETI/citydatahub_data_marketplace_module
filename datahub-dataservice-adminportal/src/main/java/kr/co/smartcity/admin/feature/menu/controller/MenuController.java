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
package kr.co.smartcity.admin.feature.menu.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.admin.feature.menu.service.MenuService;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {
	
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/**
	 * <pre>메뉴 목록 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageList.do")
	public String goPageMenuList() {
		return "menu/menuList";
	}
	/**
	 * <pre>메뉴 수정 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageModify.do")
	public String goPageMenuModify() {
		return "menu/menuModify";
	}
	/**
	 * <pre>메뉴 등록 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageEdit.do")
	public String goPageMenuRegist() {
		return "menu/menuRegist";
	}
	/**
	 * <pre>메뉴 상세 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageDetail.do")
	public String goPageMenuDetail() {
		return "menu/menuDetail";
	}
	
	/**
	 * <pre>메뉴 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/create.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String createMenu(@RequestBody Map<String, Object> params) throws Exception {
		return menuService.createMenu(params);
	}
	/**
	 * <pre>메뉴 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getMenuList(@RequestParam Map<String, Object> params) throws Exception {
		return menuService.getMenuList(params);
	}
	
	/**
	 * <pre>메뉴 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getMenu(@RequestParam Map<String, Object> params) throws Exception {
		return menuService.getMenu(params);
	}
	
	/**
	 * <pre>메뉴 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String modifyMenu(@RequestBody Map<String, Object> params) throws Exception {
		return menuService.modifyMenu(params);
	}
	
	/**
	 * <pre>메뉴 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/remove.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String removeMenu(@RequestBody Map<String, Object> params) throws Exception {
		return menuService.removeMenu(params);
	}
	

}
