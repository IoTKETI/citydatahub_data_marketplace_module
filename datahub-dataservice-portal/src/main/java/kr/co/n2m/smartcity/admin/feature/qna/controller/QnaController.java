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
package kr.co.n2m.smartcity.admin.feature.qna.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.admin.common.exceptions.ClientException;
import kr.co.n2m.smartcity.admin.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;
import kr.co.n2m.smartcity.admin.feature.qna.service.QnaService;
import kr.co.n2m.smartcity.admin.feature.qna.vo.QnaFileVo;
import kr.co.n2m.smartcity.admin.feature.qna.vo.QnaVo;
import kr.co.n2m.smartcity.admin.feature.qna.vo.SrchQnaVo;

@RestController
public class QnaController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private QnaService qnaService;

	/**
	 * QnA 목록
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchQnaVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/qna")
	public ResponseEntity<Object> getQnaList(SrchQnaVo srchQnaVo,@RequestParam HashMap<String, String> params) {
		Map<String,Object> resMap = qnaService.getQnaList(srchQnaVo,params);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	

	/**
	 * QnA 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 12.
	 * @param srchQnaVo
	 * @return
	 */
	@PostMapping("/dataserviceUi/qna")
	public ResponseEntity<Object> createQna(SrchQnaVo srchQnaVo, HttpServletRequest request, @RequestHeader("UserId") String qnaQuestionUsrId) {
		srchQnaVo.setQnaQuestionUsrId(qnaQuestionUsrId);
		String vo = qnaService.createQna(srchQnaVo, request);
		return ResponseUtil.makeResponseEntity(vo,HttpStatus.CREATED,"/dataserviceUi/qna/"+vo);
	}
	
	/**
	 * QnA 상세
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 12.
	 * @param qnaOid
	 * @param srchQnaVo
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/dataserviceUi/qna/{qnaOid}")
	public ResponseEntity<Object> getQna(@PathVariable long qnaOid, String nohit) throws Exception {
		QnaVo qnaVo = qnaService.getQna(qnaOid, "yes".equalsIgnoreCase(nohit));
		return ResponseUtil.makeResponseEntity(qnaVo);
	}
	
	
	/**
	 * QnA 질문 수정
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchQnaVo
	 * @return
	 * @throws Exception 
	 */
	@PutMapping("/dataserviceUi/qna/{qnaOid}")
	public ResponseEntity<Object> modifyQna(@PathVariable long qnaOid,SrchQnaVo srchQnaVo, HttpServletRequest request, @RequestHeader("UserId") String qnaQuestionUsrId) throws Exception {
		StringUtil.compareUniqueId(qnaOid, String.valueOf(srchQnaVo.getQnaOid()));
		srchQnaVo.setQnaQuestionUsrId(qnaQuestionUsrId);
		
		qnaService.modifyQna(srchQnaVo, request);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	/**
	 * QnA 질문 부분수정(답변 등록)
	 * @Author      : areum
	 * @Date        : 2020. 7. 14.
	 * @param srchQnaVo
	 * @return
	 * @throws Exception 
	 */
	@PatchMapping("/dataserviceUi/qna/{qnaOid}")
	public ResponseEntity<Object> patchQna(@PathVariable long qnaOid, @RequestBody SrchQnaVo srchQnaVo, @RequestHeader("UserId") String loginUserId) throws Exception {
		StringUtil.compareUniqueId(qnaOid, String.valueOf(srchQnaVo.getQnaOid()));
		srchQnaVo.setQnaAnswerUsrId(loginUserId);
		
		qnaService.modifyQnaPart(srchQnaVo);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	/**
	 * QnA 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchQnaVo
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping("/dataserviceUi/qna/{qnaOid}")
	public ResponseEntity<Object> deleteQna(@PathVariable String qnaOid,@RequestBody SrchQnaVo srchQnaVo) throws Exception {
		qnaService.deleteQna(srchQnaVo);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}
	
	
	
	/**
	 * Q&A 첨부파일 등록
	 * @Author      : areum
	 * @Date        : 2020. 7. 14.
	 * @return : ResponseEntity<Object>
	 */
	@PostMapping("/dataserviceUi/qna/{qnaOid}/attachFiles")
	public ResponseEntity<Object> createQnaFile(@PathVariable long qnaOid,HttpServletRequest request) {
		qnaService.createQnaFile(qnaOid, request);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.CREATED,"/dataserviceUi/qna/"+qnaOid+"/attachFiles");
	}
	
	/**
	 * Q&A 첨부파일 목록 조회
	 * @Author      : areum
	 * @Date        : 2020. 7. 14.
	 * @return : ResponseEntity<Object>
	 * @throws ClientException 
	 */
	@GetMapping("/dataserviceUi/qna/{qnaOid}/attachFiles")
	public ResponseEntity<Object> getQnaFileList(@PathVariable long qnaOid) throws ClientException {
		List<QnaFileVo> qnaFileList = qnaService.getQnaFileList(qnaOid);
		return ResponseUtil.makeResponseEntity(qnaFileList);
	}
	
	/**
	 * Q&A 첨부파일 수정
	 * @Author      : areum
	 * @Date        : 2020. 7. 14.
	 * @return : ResponseEntity<Object>
	 * @throws FileNotFoundException 
	 * @throws ClientException 
	 */
	@PutMapping("/dataserviceUi/qna/{qnaOid}/attachFiles/{fileId}")
	public ResponseEntity<Object> updateQnaFile(@PathVariable long qnaOid, @PathVariable long fileId, HttpServletRequest request,SrchQnaVo srchQnaVo ) throws FileNotFoundException, ClientException{
		StringUtil.compareUniqueId(qnaOid, srchQnaVo.getQnaOid());
		qnaService.updateQnaFiles(srchQnaVo, request);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	/**
	 * Q&A 첨부파일 다운로드
	 * @Author      : areum
	 * @Date        : 2020. 7. 14.
	 * @return : FileSystemResource
	 * @throws FileNotFoundException 
	 */
	@GetMapping("/dataserviceUi/qna/{qnaOid}/attachFiles/{fileId}")
	public FileSystemResource downloadQnaFile(@PathVariable long qnaOid, @PathVariable long fileId,HttpServletResponse response) throws FileNotFoundException {
		return qnaService.downloadQnaFile(qnaOid,fileId,response);
	}
	
	/**
	 * Q&A 첨부파일 삭제
	 * @Author      : areum
	 * @Date        : 2020. 7. 14.
	 * @return : ResponseEntity<Object>
	 * @throws ClientException 
	 * @throws FileNotFoundException 
	 */
	@DeleteMapping("/dataserviceUi/qna/{qnaOid}/attachFiles/{fileId}")
	public ResponseEntity<Object> deleteQnaFile(@PathVariable long qnaOid, @PathVariable long fileId,@RequestBody SrchQnaVo srchQnaVo) throws ClientException, FileNotFoundException{
		qnaService.deleteQnaFile(qnaOid);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}
	
	

}
