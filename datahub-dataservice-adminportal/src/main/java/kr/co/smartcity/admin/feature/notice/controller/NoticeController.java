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
package kr.co.smartcity.admin.feature.notice.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.feature.common.service.FileService;
import kr.co.smartcity.admin.feature.notice.service.NoticeService;

@Controller
@RequestMapping(value = "/notice")
public class NoticeController {
	
	@Resource(name = "noticeService")
	private NoticeService noticeService;
	
	@Resource(name = "fileService")
	private FileService fileService;
	
	/**
	 * <pre>공지사항 목록 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pageList.do")
	public String goPageNoticeList(Model model, @RequestParam Map<String, String> params) {
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "NOTICE_LIST"));
		}
		return "notice/noticeList";
	}
	/**
	 * <pre>공지사항 수정 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageModify.do")
	public String goPageNoticeModify() {
		return "notice/noticeModify";
	}
	/**
	 * <pre>공지사항 등록 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageEdit.do")
	public String goPageNoticeRegist() {
		return "notice/noticeRegist";
	}
	/**
	 * <pre>공지사항 상세 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageDetail.do")
	public String goPageNoticeDetail() {
		return "notice/noticeDetail";
	}
	
	/**
	 * <pre>공지사항 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/create.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String createNotice(@RequestParam Map<String, Object> params, HttpServletRequest req) throws Exception {
		return noticeService.createNotice(params, req);
	}
	
	/**
	 * <pre>공지사항 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getNoticeList(@RequestParam Map<String, Object> params) throws Exception {
		return noticeService.getNoticeList(params);
	}
	/**
	 * <pre>공지사항 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/get.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getNotice(@RequestParam Map<String, Object> params) throws Exception {
		return noticeService.getNotice(params);
	}
	/**
	 * <pre>공지사항 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String modifyNotice(@RequestParam Map<String, Object> params, HttpServletRequest req) throws Exception {
		return noticeService.modifyNotice(params, req);
	}
	/**
	 * <pre>공지사항 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/remove.do")
	public @ResponseBody String deleteNotice(@RequestBody Map<String, Object> params) throws Exception {
		return noticeService.removeNotice(params);
	}
	
	/**
	 * 
	 * <pre>공지사항 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param params
	 * @param res
	 */
	@RequestMapping(value = "/downloadFile.do", produces="text/plain;charset=UTF-8")
	public void downloadNoticeFile(@RequestParam Map<String, Object> params, HttpServletResponse res) {
		noticeService.downloadNoticeFile(params, res);
	}
	
}
