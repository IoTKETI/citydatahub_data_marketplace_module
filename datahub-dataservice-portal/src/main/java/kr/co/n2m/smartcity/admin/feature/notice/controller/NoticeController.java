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
package kr.co.n2m.smartcity.admin.feature.notice.controller;

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
import kr.co.n2m.smartcity.admin.feature.notice.service.NoticeService;
import kr.co.n2m.smartcity.admin.feature.notice.vo.NoticeFileVo;
import kr.co.n2m.smartcity.admin.feature.notice.vo.NoticeVo;
import kr.co.n2m.smartcity.admin.feature.notice.vo.SrchNoticeFileVo;
import kr.co.n2m.smartcity.admin.feature.notice.vo.SrchNoticeVo;

@RestController
public class NoticeController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NoticeService noticeService;

	/**
	 * 공지사항 목록
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param noticeVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/notices")
	public ResponseEntity<Object> getNoticeList(SrchNoticeVo srchNoticeVo,@RequestParam HashMap<String, String> params) {
		Map<String,Object> resMap = noticeService.getNoticeList(srchNoticeVo,params);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * 공지사항 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param noticeVo
	 * @return
	 */
	@PostMapping("/dataserviceUi/notices")
	public ResponseEntity<Object> createNotice(SrchNoticeVo srchNoticeVo, HttpServletRequest request, @RequestHeader("UserId") String noticeCreUsrId) {
		srchNoticeVo.setNoticeCreUsrId(noticeCreUsrId);
		String vo = noticeService.createNotice(srchNoticeVo, request);
		return ResponseUtil.makeResponseEntity(vo,HttpStatus.CREATED,"/dataserviceUi/notices"+vo);
	}
	
	/**
	 * 공지사항 상세
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param noticeOid
	 * @param noticeVo
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/dataserviceUi/notices/{noticeOid}")
	public ResponseEntity<Object> getNotice(@PathVariable long noticeOid, String nohit) throws Exception {
		NoticeVo noticeVo = noticeService.getNotice(noticeOid,"yes".equalsIgnoreCase(nohit));
		return ResponseUtil.makeResponseEntity(noticeVo);
	}
	
	/**
	 * 공지사항 수정
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param noticeVo
	 * @param noticeUptUsrId
	 * @return
	 * @throws Exception 
	 */
	@PutMapping("/dataserviceUi/notices/{noticeOid}")
	public ResponseEntity<Object> modifyNotice(@PathVariable String noticeOid, SrchNoticeVo srchNoticeVo, HttpServletRequest request, @RequestHeader("UserId") String noticeUptUsrId) throws Exception {
		StringUtil.compareUniqueId(noticeOid, String.valueOf(srchNoticeVo.getNoticeOid()));
		srchNoticeVo.setNoticeUptUsrId(noticeUptUsrId);
		noticeService.modifyNotice(srchNoticeVo, request);
		return ResponseUtil.makeResponseEntity(null);
	}
	

	/**
	 * 공지사항 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param noticeOid
	 * @param noticeVo
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping("/dataserviceUi/notices/{noticeOid}")
	public ResponseEntity<Object> deleteNotice(@PathVariable String noticeOid, @RequestBody SrchNoticeVo srchNoticeVo) throws Exception {
		noticeService.deleteNotice(srchNoticeVo);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}
	
	/**
	 * 
	 * <pre>공지사항 첨부파일 등록</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param noticeOid
	 * @param request
	 * @return
	 */
	@PostMapping("/dataserviceUi/notices/{noticeOid}/attachFiles")
	public ResponseEntity<Object> createNoticeFile(@PathVariable long noticeOid, HttpServletRequest request){
		noticeService.createNoticeFile(noticeOid,request);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.CREATED,"/dataserviceUi/notice/"+noticeOid+"attachFiles");
	}
	
	/**
	 * 
	 * <pre>공지사항 첨부파일 목록 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param noticeOid
	 * @param srchNoticeFileVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/notices/{noticeOid}/attachFiles")
	public ResponseEntity<Object> getNoticeFileList(@PathVariable long noticeOid, SrchNoticeFileVo srchNoticeFileVo){
		List<NoticeFileVo> noticeFileList = noticeService.getNoticeFileList(noticeOid);
		return ResponseUtil.makeResponseEntity(noticeFileList);
	}
	
	/**
	 * 
	 * <pre>공지사항 첨부파일 수정</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param noticeOid
	 * @param srchNoticeFileVo
	 * @return
	 * @throws ClientException 
	 */
	@PutMapping("/dataserviceUi/notices/{noticeOid}/attachFiles/{fileId}")
	public ResponseEntity<Object> updateNoticeFile(@PathVariable long noticeOid, @PathVariable long fileId, SrchNoticeVo srchNoticeVo, HttpServletRequest request) throws ClientException{
		StringUtil.compareUniqueId(noticeOid, srchNoticeVo.getNoticeOid());
		noticeService.updateNoticeFiles(srchNoticeVo,request);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	/**
	 * 
	 * <pre>공지사항 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param noticeOid
	 * @param fileId
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws FileNotFoundException
	 */
	@GetMapping("/dataserviceUi/notices/{noticeOid}/attachFiles/{fileId}")
	public FileSystemResource downloadNoticeFile(@PathVariable long noticeOid, @PathVariable long fileId, HttpServletResponse response) throws ClientException, FileNotFoundException{
		return noticeService.downloadNoticeFile(noticeOid,fileId,response);
	}
	
	/**
	 * 
	 * <pre>공지사항 첨부파일 삭제</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param noticeOid
	 * @param fileId
	 * @param srchNoticeVo
	 * @param request
	 * @param param
	 * @return
	 */
	public ResponseEntity<Object> deleteNoticeFile(@PathVariable long noticeOid, @PathVariable long fileId, SrchNoticeVo srchNoticeVo, HttpServletRequest request, @RequestParam Map<String, Object> param){
		noticeService.deleteNoticeFile(noticeOid);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
}
