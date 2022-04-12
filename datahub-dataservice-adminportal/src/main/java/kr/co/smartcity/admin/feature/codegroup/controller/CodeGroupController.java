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
package kr.co.smartcity.admin.feature.codegroup.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.feature.codegroup.service.CodeGroupService;

@Controller
@RequestMapping(value = "/codegroup")
public class CodeGroupController {
	
	@Resource(name = "codeGroupService")
	private CodeGroupService codeGroupService;
	
	/**
	 * 
	 * [화면이동] 코드그룹목록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @return
	 */
	@RequestMapping(value = "/pageList.do")
	public String goPageNoticeList(Model model, @RequestParam Map<String, Object> params) {
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "CODEGROUP_LIST"));
		}
		return "codegroup/codeGroupList";
	}
	
	/**
	 * 
	 * [화면이동] 코드그룹수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @return
	 */
	@RequestMapping(value = "/pageModify.do")
	public String goPageNoticeModify() {
		return "codegroup/codeGroupModify";
	}
	
	/**
	 * 
	 * [화면이동] 코드그룹등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @return
	 */
	@RequestMapping(value = "/pageEdit.do")
	public String goPageNoticeRegist() {
		return "codegroup/codeGroupRegist";
	}
	
	/**
	 * 
	 * [화면이동] 코드그룹상세
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @return
	 */
	@RequestMapping(value = "/pageDetail.do")
	public String goPageNoticeDetail() {
		return "codegroup/codeGroupDetail";
	}
	/**
	 * <pre>코드그룹ID 중복체크</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String checkCodeGroupId(@RequestParam Map<String, Object> params) throws Exception {
		return codeGroupService.checkCodeGroupId(params);
	}
	/**
	 * <pre>코드그룹 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/create.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String createCodeGroup(@RequestBody Map<String, Object> params) throws Exception {
		return codeGroupService.createCodeGroup(params);
	}
	/**
	 * <pre>코드그룹 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String modifyCodeGroup(@RequestBody Map<String, Object> params) throws Exception {
		return codeGroupService.modifyCodeGroup(params);
	}
	/**
	 * <pre>코드그룹목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getCodeGroupList(@RequestParam Map<String, Object> params) throws Exception {
		return codeGroupService.getCodeGroupList(params);
	}
	/**
	 * <pre>코드그룹상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getCodeGroup(@RequestParam Map<String, Object> params) throws Exception {
		return codeGroupService.getCodeGroup(params);
	}
	/**
	 * <pre>코드ID 중복체크</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/code/check.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String checkCodeId(@RequestParam Map<String, Object> params) throws Exception {
		return codeGroupService.checkCodeId(params);
	}
	/**
	 * <pre>코드 생성</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/code/create.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String createCode(@RequestBody Map<String, Object> params) throws Exception {
		return codeGroupService.createCode(params);
	}
	/**
	 * <pre>코드 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/code/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getCodeList(@RequestParam Map<String, Object> params) throws Exception {
		return codeGroupService.getCodeList(params);
	}
	/**
	 * <pre>코드 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/code/get.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getCode(@RequestParam Map<String, Object> params) throws Exception {
		return codeGroupService.getCode(params);
	}
	
	/**
	 * <pre>코드 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/code/modify.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String modifyCode(@RequestBody Map<String, Object> params) throws Exception {
		return codeGroupService.modifyCode(params);
	}
}
