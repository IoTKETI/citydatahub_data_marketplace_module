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
package kr.co.smartcity.admin.feature.user.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.common.util.StringUtil;
import kr.co.smartcity.admin.feature.user.service.UserService;
import kr.co.smartcity.admin.feature.user.vo.UserVo;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Resource(name = "userService")
	private UserService userService;
	
	/**
	 * <pre>사용자 목록 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pageList.do")
	public String goPageUserList(Model model) {
		model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "USER_LIST"));
		return "user/userList";
	}
	/**
	 * <pre>사용자 수정 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageModify.do")
	public String goPageUserModify() {
		return "user/userModify";
	}
	/**
	 * <pre>사용자 등록 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageEdit.do")
	public String goPageUserRegist() {
		return "user/userRegist";
	}
	/**
	 * <pre>사용자 상세 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageDetail.do")
	public String goPageUserDetail() {
		return "user/userDetail";
	}
	
	/**
	 * <pre>사용자 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get.do", produces="application/json;charset=UTF-8")
	public @ResponseBody UserVo getUser(@RequestParam Map<String, String> params) throws Exception {
		// oauth api 거치도록 수정
		return userService.getUser(params);
	}
	
	/**
	 * <pre>사용자 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getUserList(@RequestParam Map<String, String> params) throws Exception {
		if(StringUtil.equals(params.get("user"), "admin")) {
			// 관리자 -> user_tb DB에 있는 유저
			return userService.getAdminUserList(params);
		} else if(StringUtil.equals(params.get("user"), "normal")){
			// 일반사용자 -> (유저 목록에서 DB에 저장된 관리자를 빼야한다)
			return userService.getNormalUserList(params);
		} else {
			// 전체사용자
			return userService.getAllUserList(params);
		}
	}
	
	
	
}
