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
package kr.co.smartcity.user.feature.notice.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.user.common.CommonConst;
import kr.co.smartcity.user.common.component.HttpComponent;
import kr.co.smartcity.user.feature.notice.service.NoticeService;

@Controller
@RequestMapping(value="/cusvc/notice")
public class NoticeController extends HttpComponent{
	
	@Resource(name = "noticeService")
	private NoticeService noticeService;
	
	/**
	 * 공지사항 목록 페이지 이동
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageList.do", produces="text/plain;charset=UTF-8")
	public String goNoticePageList(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam Map<String, String> params) throws Exception{
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "NOTICE_LIST"));
		}
		return "cusvc/notice/noticeList";
	}
	
	/**
	 * 공지사항 상세 페이지 이동
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageDetail.do", produces="text/plain;charset=UTF-8")
	public String goNoticePageDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "cusvc/notice/noticeDetail";
	}
	
	/**
	 * 공지사항 목록 데이터 호출
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getNoticeList(@RequestParam Map<String, String> params) throws Exception {
		return noticeService.getNoticeList(params);
	}
	
	/**
	 * 공지사항 상세 데이터 호출
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/get.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getNotice(@RequestParam Map<String, String> params) throws Exception {
		return noticeService.getNotice(params);
	}
	
	/**
	 * 
	 * <pre>공지사항 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 28.
	 * @param params
	 * @param res
	 */
	@RequestMapping(value = "/downloadFile.do", produces="text/plain;charset=UTF-8")
	public void downloadNoticeFile(@RequestParam Map<String, Object> params, HttpServletResponse res) {
		noticeService.downloadNoticeFile(params, res);
	}
	
}
