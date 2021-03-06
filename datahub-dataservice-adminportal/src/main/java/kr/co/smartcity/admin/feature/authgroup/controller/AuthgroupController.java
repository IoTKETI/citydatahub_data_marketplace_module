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
	//????????? ??????
	@Resource(name = "authgroupService")
	private AuthgroupService authgroupService;

	/**
	 * 
	 * ?????????????????? ?????? ????????? ??????
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
	 * ?????????????????? ?????? ????????? ??????
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
	 * ?????????????????? ?????? ????????? ??????
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
	 * ?????????????????? ?????? ????????? ??????
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
	 * ?????????????????? ????????? ??????
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
	 * ?????????????????? ?????? ????????? ??????
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
	 * ?????????????????? ?????? ????????? ??????
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
	 * ?????????????????? ????????? ??????
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
	 * ?????????????????? ????????? ??????
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
	 * ?????????????????? ????????? ????????? ??????
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
	 * ?????????????????? ????????? ?????? ????????? ??????
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
	 * ???????????? ???????????? ?????? ??????
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
	 * ???????????? ???????????? ??????
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
