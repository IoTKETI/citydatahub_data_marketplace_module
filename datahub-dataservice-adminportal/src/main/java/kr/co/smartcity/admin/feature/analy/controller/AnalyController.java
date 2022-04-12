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
package kr.co.smartcity.admin.feature.analy.controller;

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
import kr.co.smartcity.admin.feature.analy.service.AnalyService;

@Controller
@RequestMapping(value = "/analy")
public class AnalyController {

	@Resource(name = "analyService")
	private AnalyService analyService;
	
	/**
	 * <pre>분석가 승인 목록 화면</pre>
	 * @Author      : thlee
	 * @Date        : 2019. 10. 10.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageList.do")
	public String goPageAnalyList(Model model,  @RequestParam Map<String, Object> params) {
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "ANALY_LIST"));
		}
		return "analy/analyList";
	}
	
	/**
	 * <pre>분석가 승인 목록 화면</pre>
	 * @Author      : thlee
	 * @Date        : 2019. 11. 25.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageDetail.do")
	public String goPageAnalyList() {
		return "analy/analyDetail";
	}
	
	/**
	 * <pre>분석가 승인 목록 조회</pre>
	 * @Author      : thlee
	 * @Date        : 2019. 10. 10.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getAnalyList(@RequestParam Map<String, Object> params) throws Exception {
		return analyService.getAnalyList(params);
	}
	
	/**
	 * <pre>분석가 승인 상태 수정</pre>
	 * @Author      : thlee
	 * @Date        : 2019. 10. 10.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String modifyAnaly(@RequestBody Map<String, Object> params) throws Exception {
		return analyService.modifyAnaly(params);
	}
	
	/**
	 * <pre>분석가 승인 상세 조회</pre>
	 * @Author      : thlee
	 * @Date        : 2019. 11. 25.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getAnaly(@RequestParam Map<String, Object> params) throws Exception {
		return analyService.getAnaly(params);
	}
}
