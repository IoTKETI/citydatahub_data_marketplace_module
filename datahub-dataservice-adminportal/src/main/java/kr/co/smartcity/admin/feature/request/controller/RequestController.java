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
package kr.co.smartcity.admin.feature.request.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.feature.request.service.RequestService;
import kr.co.smartcity.admin.feature.request.vo.SrchRequestVo;

@Controller
@RequestMapping(value = "/request")
public class RequestController {
	
	@Autowired RequestService requestService;

	/**
	 * <pre>데이터셋 요청 목록 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 11.
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/pageList.do")
	public String goPageRequestList(Model model, @RequestParam Map<String, String> params) {
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "REQUEST_LIST"));
		}
		return "request/requestList";
	}
	
	/**
	 * <pre>데이터셋 요청 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 11.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getRequestList(SrchRequestVo srchRequestVo) throws Exception {
		return requestService.getRequestList(srchRequestVo);
	}
	
}
