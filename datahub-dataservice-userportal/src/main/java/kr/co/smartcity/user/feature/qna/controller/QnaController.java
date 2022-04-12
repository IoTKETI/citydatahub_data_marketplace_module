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
package kr.co.smartcity.user.feature.qna.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.user.common.CommonConst;
import kr.co.smartcity.user.common.component.HttpComponent;
import kr.co.smartcity.user.feature.qna.service.QnaService;

@Controller
@RequestMapping(value="/cusvc/qna")
public class QnaController extends HttpComponent{
	
	@Resource(name="qnaService")
	private QnaService qnaService;
	
	/**
	 * QnA 목록 화면
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pageList.do", produces="text/plain;charset=UTF-8")
	public String goQnaPageList(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam Map<String, String> params) throws Exception{
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "QNA_LIST"));
		}
		return "cusvc/qna/qnaList";
	}
	
	/**
	 * QnA 등록 화면
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pageEdit.do", produces="text/plain;charset=UTF-8")
	public String goQnaPageEdit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "cusvc/qna/qnaRegist";
	}
	
	/**
	 * QnA 상세 화면
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pageDetail.do", produces="text/plain;charset=UTF-8")
	public String goQnaPageDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "cusvc/qna/qnaDetail";
	}
	
	/**
	 * QnA 수정 화면
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pageModify.do", produces="text/plain;charset=UTF-8")
	public String goQnaPageModify(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "cusvc/qna/qnaModify";
	}
	
	/**
	 * QnA 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @return
	 */
	@PostMapping(value="/create.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String createQna(@RequestParam Map<String, Object> params, HttpServletRequest req) throws Exception {
		return qnaService.createQna(params, req);
	}
	
	/**
	 * QnA 목록
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @return
	 */
	@GetMapping(value="/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getQnaList(@RequestParam Map<String, String> params) throws Exception {
		return qnaService.getQnaList(params);
	}
	
	/**
	 * QnA 상세
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @return
	 */
	@GetMapping(value="/get.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getQna(@RequestParam Map<String, String> params) throws Exception {
		return qnaService.getQna(params);
	}
	
	/**
	 * QnA 수정
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @return
	 */
	@PutMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String modifyQna(@RequestParam Map<String, Object> params, HttpServletRequest req) throws Exception {
		return qnaService.modifyQna(params, req);
	}
	
	/**
	 * QnA 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @return
	 */
	@DeleteMapping(value = "/remove.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody Object removeQna(@RequestBody Map<String, Object> params) throws Exception {
		return qnaService.removeQna(params);
	}
	
	/**
	 * 답변 등록(QnA 테이블의 답변 컬럼을 수정)
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @return
	 */
	@PutMapping(value="/reply.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String createQnaAnswer(@RequestBody Map<String, Object> params) throws Exception {
		return qnaService.createQnaAnswer(params);
	}
	
	/**
	 * 
	 * <pre>Q&A 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 28.
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/downloadFile.do", produces="text/plain;charset=UTF-8")
	public void downloadQnaFile(@RequestParam Map<String, Object> params, HttpServletResponse res) {
		qnaService.downloadQnaFile(params, res);
	}
	
	
}
