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
package kr.co.smartcity.user.feature.review.controller;

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
import kr.co.smartcity.user.feature.review.service.ReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController {

	@Resource(name="reviewService")
	private ReviewService reviewService;
	
	
	/**
	 * 활용후기 목록 화면
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @return
	 */
	@RequestMapping(value="/pageList.do", produces="text/plain;charset=UTF-8")
	public String reviewList(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam Map<String, String> params) throws Exception{
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "REVIEW_LIST"));
		}
		return "review/reviewList";
	}
	
	/**
	 * 활용후기 등록 화면
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return
	 */
	@RequestMapping(value="/pageEdit.do", produces="text/plain;charset=UTF-8")
	public String reviewEdit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "review/reviewEdit";
	}
	
	/**
	 * 활용후기 상세 화면
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return
	 */
	@RequestMapping(value="/pageDetail.do", produces="text/plain;charset=UTF-8")
	public String reviewDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "review/reviewDetail";
	}
	
	/**
	 * 활용후기 수정 화면
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return
	 */
	@RequestMapping(value="/pageModify.do", produces="text/plain;charset=UTF-8")
	public String reviewModify(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "review/reviewModify";
	}
	
	/**
	 * 활용후기 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @param req
	 * @throws Exception
	 * @return
	 */
	@PostMapping(value="/create.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String createReview(@RequestParam Map<String, Object> params, HttpServletRequest req) throws Exception {
		return reviewService.createReview(params, req);
	}
	
	/**
	 * 활용후기 목록
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @throws Exception
	 * @return
	 */
	@GetMapping(value="/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getReviewList(@RequestParam Map<String, String> params) throws Exception {
		return reviewService.getReviewList(params);
	}
	
	/**
	 * 활용후기 상세
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @throws Exception
	 * @return
	 */
	@GetMapping(value="/get.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getReview(@RequestParam Map<String, String> params) throws Exception {
		return reviewService.getReview(params);
	}
	
	/**
	 * 활용후기 수정
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @param req
	 * @throws Exception
	 * @return
	 */
	@PutMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String modifyReview(@RequestParam Map<String, Object> params, HttpServletRequest req) throws Exception {
		return reviewService.modifyReview(params, req);
	}
	
	/**
	 * 활용후기 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @throws Exception
	 * @return
	 */
	@DeleteMapping(value = "/remove.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String removeReview(@RequestBody Map<String, Object> params) throws Exception {
		return reviewService.removeReview(params);
	}
	
	/**
	 * 
	 * <pre>활용후기 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param params
	 * @param res
	 */
	@RequestMapping(value = "/downloadFile.do", produces="text/plain;charset=UTF-8")
	public void downloadFile(@RequestParam Map<String, Object> params, HttpServletResponse res) {
		reviewService.downloadReviewFile(params, res);
	}
	
	
}
