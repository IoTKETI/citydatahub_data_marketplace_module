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
package kr.co.smartcity.admin.feature.authgroup.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.feature.authgroup.service.AuthgroupService;

@Controller
@RequestMapping(value = "/authgroup")
public class AuthgroupController {
	//페이지 이동
	@Resource(name = "authgroupService")
	private AuthgroupService authgroupService;

	/**
	 * 
	 * 권한그룹관리 목록 페이지 이동
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pageList.do")
	public String goPageAuthgroupList(Model model, @RequestParam Map<String, String> params) {
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "AUTHGROUP_LIST"));
		}
		return "authgroup/authgroupList";
	}
	/**
	 * 
	 * 권한그룹관리 수정 페이지 이동
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @return
	 */
	@RequestMapping(value = "/pageModify.do")
	public String goPageAuthgroupModify() {
		return "authgroup/authgroupModify";
	}
	/**
	 * 
	 * 권한그룹관리 등록 페이지 이동
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @return
	 */
	@RequestMapping(value = "/pageEdit.do")
	public String goPageAuthgroupRegist() {
		return "authgroup/authgroupRegist";
	}
	/**
	 * 
	 * 권한그룹관리 상세 페이지 이동
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @return
	 */
	@RequestMapping(value = "/pageDetail.do")
	public String goPageAuthgroupDetail() {
		return "authgroup/authgroupDetail";
	}
	
	
	/**
	 * 
	 * 권한그룹관리 데이터 등록
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/create.do" , produces="text/plain;charset=UTF-8")
	public @ResponseBody String createAuthgroup(@RequestBody Map<String, Object> params) throws Exception {
		return authgroupService.createAuthgroup(params);
	}
	/**
	 * 
	 * 권한그룹관리 목록 데이터 호출
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/getList.do" ,produces="text/plain;charset=UTF-8")
	public @ResponseBody String getAuthgroupList(@RequestParam Map<String, Object> params) throws Exception {
		return authgroupService.getAuthgroupList(params);
	}
	/**
	 * 
	 * 권한그룹관리 상세 데이터 호출
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getAuthgroup(@RequestParam Map<String, Object> params) throws Exception {
		return authgroupService.getAuthgroup(params);
	}
	/**
	 * 
	 * 권한그룹관리 데이터 삭제
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/remove.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String deleteAuthgroup(@RequestBody Map<String, Object> params) throws Exception {
		return authgroupService.deleteAuthgroup(params);
	}
	/**
	 * 
	 * 권한그룹관리 데이터 수정
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String modifyAuthgroup(@RequestBody Map<String, Object> params) throws Exception {
		return authgroupService.modifyAuthgroup(params);
	}
	/**
	 * 
	 * 권한그룹관리 사용자 데이터 수정
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/modify.do" , produces="text/plain;charset=UTF-8")
	public @ResponseBody String updateAuthgroupUser(@RequestBody Map<String, Object> params) throws Exception {
		return authgroupService.updateAuthgroupUser(params);
	}
	/**
	 * 
	 * 권한그룹관리 사용자 목록 데이터 호출
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/getList.do" ,produces="application/json;charset=UTF-8")
	public @ResponseBody String getAuthgroupUserList(@RequestParam Map<String, Object> params) throws Exception {
		return authgroupService.getAuthgroupUserList(params);
	}
	
	/**
	 * 
	 * 권한그룹 메뉴권한 목록 호출
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/menu/getList.do" ,produces="text/plain;charset=UTF-8")
	public @ResponseBody String getAuthgroupMenuList(@RequestParam Map<String, Object> params) throws Exception {
		return authgroupService.getAuthgroupMenuList(params);
	}
	/**
	 * 
	 * 권한그룹 메뉴권한 수정
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu/modify.do" , produces="text/plain;charset=UTF-8")
	public @ResponseBody String updateAuthgroupMenu(@RequestBody Map<String, Object> params) throws Exception {
		return authgroupService.updateAuthgroupMenu(params);
	}
}
