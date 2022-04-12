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
package kr.co.n2m.smartcity.admin.feature.review.controller;

import java.io.FileNotFoundException;
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
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.admin.common.exceptions.ClientException;
import kr.co.n2m.smartcity.admin.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.admin.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;
import kr.co.n2m.smartcity.admin.feature.review.service.ReviewService;
import kr.co.n2m.smartcity.admin.feature.review.vo.ReviewFileVo;
import kr.co.n2m.smartcity.admin.feature.review.vo.SrchReviewFileVo;
import kr.co.n2m.smartcity.admin.feature.review.vo.SrchReviewVo;

@RestController
public class ReviewController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ReviewService reviewService;
	
	/**
	 * 활용후기  목록
	 * @Author      : junheecho
	 * @Date        : 2019. 11. 4.
	 * @param srchReviewVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/review")
	public ResponseEntity<Object> getReviewList(SrchReviewVo srchReviewVo) throws GlobalException {
		Map<String,Object> resMap = reviewService.getReviewList(srchReviewVo);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * 활용후기 등록 
	 * @Author      : junheecho
	 * @Date        : 2019. 11. 4.
	 * @param srchReviewVo
	 * @param request
	 * @param reviewDeveloperId
	 * @return
	 */
	@PostMapping("/dataserviceUi/review")
	public ResponseEntity<Object> createReview(SrchReviewVo srchReviewVo, HttpServletRequest request, @RequestHeader("UserId") String userId) throws GlobalException {
		srchReviewVo.setReviewCreUsrId(userId);
		String vo = reviewService.createReview(srchReviewVo, request);
		return ResponseUtil.makeResponseEntity(vo, HttpStatus.CREATED,"/dataserviceUi/review"+vo);
	}
	
	/**
	 * 활용후기 상세 
	 * @Author      : junheecho
	 * @Date        : 2019. 11. 4.
	 * @param reviewOid
	 * @param srchReviewVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/review/{reviewOid}")
	public ResponseEntity<Object> getReview(@PathVariable long reviewOid) throws GlobalException {
		Map<String,Object> resMap = reviewService.getReview(reviewOid);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * 활용후기 수정 
	 * @Author      : junheecho
	 * @Date        : 2019. 11. 4.
	 * @param srchReviewVo
	 * @param request
	 * @param reviewDeveloperId
	 * @return
	 * @throws Exception 
	 */
	@PutMapping("/dataserviceUi/review/{reviewOid}")
	public ResponseEntity<Object> modifyReview(@PathVariable String reviewOid,SrchReviewVo srchReviewVo, HttpServletRequest request, @RequestHeader("UserId") String userId) throws Exception {
		StringUtil.compareUniqueId(reviewOid, String.valueOf(srchReviewVo.getReviewOid()));
		srchReviewVo.setReviewUptUsrId(userId);
		reviewService.modifyReview(srchReviewVo, request);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	/**
	 * 활용후기 삭제 
	 * @Author      : thlee
	 * @Date        : 2019. 11. 15.
	 * @param dsOid
	 * @param srchReviewVo
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping(value="/dataserviceUi/review/{reviewOid}")
	public ResponseEntity<Object> deleteReview(@PathVariable String reviewOid, @RequestBody SrchReviewVo srchReviewVo) throws Exception {
		reviewService.deleteReview(srchReviewVo);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}
	
	/**
	 * 
	 * <pre>활용후기 첨부파일 등록</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param qnaOid
	 * @param request
	 * @return
	 */
	@PostMapping("/dataserviceUi/review/{reviewOid}/attachFiles")
	public ResponseEntity<Object> createReviewFile(@PathVariable long reviewOid,HttpServletRequest request) {
		reviewService.createReviewFile(reviewOid, request);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.CREATED,"/dataserviceUi/review/"+reviewOid+"/attachFiles");
	}
	
	/**
	 * 
	 * <pre>활용후기 첨부파일 목록 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param reviewOid
	 * @return
	 */
	@GetMapping("/dataserviceUi/review/{reviewOid}/attachFiles")
	public ResponseEntity<Object> getReviewFileList(@PathVariable long reviewOid){
		List<ReviewFileVo> reviewFileList = reviewService.getReviewFileList(reviewOid);
		return ResponseUtil.makeResponseEntity(reviewFileList);
	}
	
	/**
	 * 
	 * <pre>활용후기 첨부파일 수정</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param reviewOid
	 * @param fileId
	 * @param request
	 * @param srchReviewVo
	 * @param param
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClientException
	 */
	@PutMapping("/dataserviceUi/review/{reviewOid}/attachFiles/{fileId}")
	public ResponseEntity<Object> updateReviewFile(@PathVariable long reviewOid, @PathVariable long fileId, HttpServletRequest request,SrchReviewVo srchReviewVo) throws FileNotFoundException, ClientException{
		StringUtil.compareUniqueId(reviewOid, srchReviewVo.getReviewOid());
		reviewService.updateReviewFiles(srchReviewVo, request);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	
	/**
	 * 
	 * <pre>활용후기 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param qnaOid
	 * @param fileId
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 */
	@GetMapping("/dataserviceUi/review/{reviewOid}/attachFiles/{fileId}")
	public FileSystemResource downloadReviewFile(@PathVariable long reviewOid, @PathVariable long fileId,HttpServletResponse response) throws FileNotFoundException {
		return reviewService.downloadReviewFile(reviewOid,fileId,response);
	} 
	
	/**
	 * 
	 * <pre>활용후기 첨부파일 삭제</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param qnaOid
	 * @param fileId
	 * @param srchQnaVo
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping("/dataserviceUi/review/{reviewOid}/attachFiles/{fileId}")
	public ResponseEntity<Object> deleteQnaFile(@PathVariable long reviewOid, @PathVariable long fileId,@RequestBody SrchReviewFileVo srchReivewFileVo) throws Exception{
		reviewService.deleteReviewFile(reviewOid);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}
	
	
	
}
