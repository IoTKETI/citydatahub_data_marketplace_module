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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.smartcity.user.common.component.HttpComponent;
import kr.co.smartcity.user.common.util.CodeUtil;
import kr.co.smartcity.user.feature.codegroup.service.CodeGroupService;

@Controller
@RequestMapping(value="/codeadmin/")
public class CodeRefreshController extends HttpComponent{
	
	@Autowired private CodeGroupService codeGroupService;
	
	/**
	 * 코드 다시 받아오기
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 21.
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/refresh.do", produces="text/plain;charset=UTF-8")
	public void refresh(HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(CodeUtil.isEmpty()) {
			String codeJson = codeGroupService.getAllCodeGroupList();
			Map<String, Object> codeMap = gson.fromJson(codeJson, Map.class);
			List<Map<String, Object>> codeList = (List<Map<String, Object>>) codeMap.get("codeGroupList");
			CodeUtil.setCode(codeList);
		}
	}
}
