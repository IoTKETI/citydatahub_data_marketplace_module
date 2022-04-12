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
package kr.co.n2m.smartcity.admin.feature.faq.controller;

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
import kr.co.n2m.smartcity.admin.feature.faq.service.FaqService;
import kr.co.n2m.smartcity.admin.feature.faq.vo.FaqFileVo;
import kr.co.n2m.smartcity.admin.feature.faq.vo.FaqVo;
import kr.co.n2m.smartcity.admin.feature.faq.vo.SrchFaqFileVo;
import kr.co.n2m.smartcity.admin.feature.faq.vo.SrchFaqVo;


@RestController
public class FaqController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FaqService faqService;
	/**
	 * 
	 * 목록 리스트
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 12.
	 * @param faqVo
	 * @return
	 */
	
	@GetMapping("/dataserviceUi/faq")
	public ResponseEntity<Object> getFaqList(SrchFaqVo srchFaqVo,@RequestParam HashMap<String, String> params) {
		Map<String,Object> resMap = faqService.getFaqList(srchFaqVo,params);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	/**
	 * 
	 * FAQ등록
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 12.
	 * @param faqVo
	 * @return
	 */
	
	@PostMapping("/dataserviceUi/faq")
	public ResponseEntity<Object> createFaq(@RequestHeader("UserId") String faqCreUsrId,SrchFaqVo srchFaqVo , HttpServletRequest request) {
		srchFaqVo.setFaqCreUsrId(faqCreUsrId);
		String vo = faqService.createFaq(srchFaqVo , request);
		return ResponseUtil.makeResponseEntity(vo,HttpStatus.CREATED,"/dataserviceUi/faq"+vo);
	}
	
	/**
	 * FAQ 상세 조회
	 * @param faqVo
	 * @return
	 * @throws Exception 
	 */
	
	@GetMapping("/dataserviceUi/faq/{faqOid}")
	public ResponseEntity<Object> getFaq(@PathVariable long faqOid, String nohit) throws Exception {
		FaqVo faq = faqService.getFaq(faqOid,"yes".equalsIgnoreCase(nohit));
		return ResponseUtil.makeResponseEntity(faq);
	}
	
	/**
	 * FAQ 수정
	 * @param faqVo
	 * @return
	 * @throws Exception 
	 */
	
	@PutMapping("/dataserviceUi/faq/{faqOid}")
	public ResponseEntity<Object> modifyFaq(@PathVariable String faqOid,@RequestHeader("UserId") String faqUptUsrId, SrchFaqVo srchFaqVo, HttpServletRequest request) throws Exception {
		StringUtil.compareUniqueId(faqOid, String.valueOf(srchFaqVo.getFaqOid()));
		srchFaqVo.setFaqUptUsrId(faqUptUsrId);
		faqService.modifyFaq(srchFaqVo, request);
		return ResponseUtil.makeResponseEntity(null);
	}
	/**
	 * 
	 * FAQ 삭제
	 * @Author      : kyunga
	 * @Date        :  2019. 7. 12.
	 * @param faqVo
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping("/dataserviceUi/faq/{faqOid}")
	public ResponseEntity<Object> deleteFaq(@PathVariable String faqOid,@RequestBody SrchFaqVo srchFaqVo) throws Exception {
		faqService.deleteFaq(srchFaqVo);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}

	/**
	 * 
	 * <pre>FAQ 첨부파일 등록</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param faqOid
	 * @param request
	 * @return
	 */
	@PostMapping("/dataserviceUi/faq/{faqOid}/attachFiles")
	public ResponseEntity<Object> createFaqFile(@PathVariable long faqOid, HttpServletRequest request){
		faqService.createFaqFile(faqOid,request);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.CREATED,"/dataserviceUi/faq/"+faqOid+"attachFiles");
	}
	
	/**
	 * 
	 * <pre>FAQ 첨부파일 목록 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param faqOid
	 * @param srchFaqFileVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/faq/{faqOid}/attachFiles")
	public ResponseEntity<Object> getFaqFileList(@PathVariable long faqOid, SrchFaqFileVo srchFaqFileVo){
		List<FaqFileVo> faqFileList = faqService.getfaqFileList(faqOid); 
		return ResponseUtil.makeResponseEntity(faqFileList);
	}
	
	/**
	 * 
	 * <pre>FAQ 첨부파일 수정</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param faqOid
	 * @param fileId
	 * @param request
	 * @param srchFaqVo
	 * @return
	 * @throws ClientException
	 */
	@PutMapping("/dataserviceUi/faq/{faqOid}/attachFiles/{fileId}")
	public ResponseEntity<Object> updateFaqFile(@PathVariable long faqOid,@PathVariable long fileId, HttpServletRequest request,SrchFaqVo srchFaqVo) throws ClientException{
		StringUtil.compareUniqueId(faqOid, srchFaqVo.getFaqOid());
		faqService.updateFaqFiles(srchFaqVo,request);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	/**
	 * 
	 * <pre>FAQ 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param faqOid
	 * @param fileId
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws FileNotFoundException 
	 */
	@GetMapping("/dataserviceUi/faq/{faqOid}/attachFiles/{fileId}")
	public FileSystemResource downloadFaqFile(@PathVariable long faqOid,@PathVariable long fileId, HttpServletResponse response) throws ClientException, FileNotFoundException{
		return faqService.downloadFaqFile(faqOid,fileId,response);
	}
	
	/**
	 * 
	 * <pre>FAQ 첨부파일 삭제</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param faqOid
	 * @param fileId
	 * @param srchFaqVo
	 * @return
	 * @throws ClientException
	 * @throws FileNotFoundException
	 */
	@DeleteMapping("/dataserviceUi/faq/{faqOid}/attachFiles/{fileId}")
	public ResponseEntity<Object> deleteFaqFile(@PathVariable long faqOid, @PathVariable long fileId,@RequestBody SrchFaqVo srchFaqVo) throws ClientException, FileNotFoundException{
		faqService.deleteFaqFile(fileId);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}
	
}
