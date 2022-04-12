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
package kr.co.smartcity.admin.feature.faq.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.feature.faq.service.FaqService;

@Controller
@RequestMapping(value = "/faq")
public class FaqController {
	
	@Resource(name = "faqService")
	private FaqService faqService;
	
	/**
	 * 
	 * FAQ 목록 페이지 이동
	 * @Author      : kyunga
	 * @Date        : 2019. 09. 7.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pageList.do")
	public String goPageFaqList(Model model, @RequestParam Map<String, String> params) {
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "FAQ_LIST"));
		}
		return "faq/faqList";
	}
	/**
	 * 
	 * FAQ 수정 페이지 이동
	 * @Author      : kyunga
	 * @Date        : 2019. 09. 7.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pageModify.do")
	public String goPageFaqModify() {
		return "faq/faqModify";
	}
	/**
	 * 
	 * FAQ 등록 페이지 이동
	 * @Author      : kyunga
	 * @Date        : 2019. 09. 7.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pageEdit.do")
	public String goPageFaqRegist() {
		return "faq/faqRegist";
	}
	/**
	 * 
	 * FAQ 상세 페이지 이동
	 * @Author      : kyunga
	 * @Date        : 2019. 09. 7.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pageDetail.do")
	public String goPageFaqDetail() {
		return "faq/faqDetail";
	}
	/**
	 * 
	 * FAQ 데이터 등록
	 * @Author      : kyunga
	 * @Date        : 2019. 09. 7.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create.do" , produces="text/plain;charset=UTF-8")
	public @ResponseBody String createFaq(@RequestParam Map<String, Object> params, HttpServletRequest req) throws Exception {
		return faqService.createFaq(params,req);
	}
	/**
	 * 
	 * FAQ 목록 데이터 호출
	 * @Author      : kyunga
	 * @Date        : 2019. 09. 7.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getFaqList(@RequestParam Map<String, Object> params) throws Exception {
		return faqService.getFaqList(params);
	}
	/**
	 * 
	 * FAQ 상세 데이터 호출
	 * @Author      : kyunga
	 * @Date        : 2019. 09. 7.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/get.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getFaq(@RequestParam Map<String, Object> params) throws Exception {
		return faqService.getFaq(params);
	}
	/**
	 * 
	 * FAQ 상세 데이터 수정
	 * @Author      : kyunga
	 * @Date        : 2019. 09. 7.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String modifyFaq(@RequestParam Map<String, Object> params, HttpServletRequest req) throws Exception {
		return faqService.modifyFaq(params, req);
	}
	
	/**
	 * 
	 * FAQ 상세 데이터 삭제
	 * @Author      : kyunga
	 * @Date        : 2019. 09. 7.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/remove.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String deleteFaq(@RequestBody Map<String, Object> params) throws Exception {
		return faqService.deleteFaq(params);
	}
	/**
	 * 
	 * <pre>FAQ 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param params
	 * @param res
	 */
	@RequestMapping(value = "/downloadFile.do", produces="text/plain;charset=UTF-8")
	public void downloadQnaFile(@RequestParam Map<String, Object> params, HttpServletResponse res) {
		faqService.downloadFaqFile(params, res);
	}
	
}
