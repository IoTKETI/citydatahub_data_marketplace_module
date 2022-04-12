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
package kr.co.smartcity.user.feature.mypage.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.user.common.CommonConst;
import kr.co.smartcity.user.common.component.HttpComponent;
import kr.co.smartcity.user.feature.dataset.service.DatasetService;
import kr.co.smartcity.user.feature.dataset.vo.DatasetVo;
import kr.co.smartcity.user.feature.mypage.service.MypageService;

@Controller
@RequestMapping(value="/mypage")
public class MypageController extends HttpComponent{
	
	@Resource(name="mypageService")
	private MypageService mypageService;
	@Resource(name="datasetService")
	private DatasetService datasetService;
	
	/**
	 * 비밀번호 재입력화면 이동
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/usermod/pageInfo.do", produces="text/plain;charset=UTF-8")
	public String usermodPageInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "mypage/usermod";
	}
	/**
	 * 관심데이터목록 화면이동
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wishlist/pageList.do", produces="text/plain;charset=UTF-8")
	public String wishlistPageList(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam Map<String, String> params) throws Exception{
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "WISH_LIST"));
		}
		return "mypage/wishlist";
	}
	/**
	 * 데이터 이용내역 화면이동
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/usage/pageList.do", produces="text/plain;charset=UTF-8")
	public String usagePageList(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam Map<String, String> params) throws Exception{
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "USAGE_LIST"));
		}
		return "mypage/usage";
	}
	/**
	 * 데이터 명세서 목록 화면 이동
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dataset/pageList.do", produces="text/plain;charset=UTF-8")
	public String datasetPageList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		model.addAttribute("searchType", "mypage");
		return "dataset/datasetList";
	}
	/**
	 * 데이터 명세서 상세 화면 이동
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dataset/pageDetail.do", produces="text/plain;charset=UTF-8")
	public String datasetPageDetail(DatasetVo datasetVo, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		model.addAttribute("endpoint", datasetService.getDatasetEndPoint());
		model.addAttribute("alivePlatformServer", datasetService.isAlivePlatformServer());
		
		return "dataset/datasetDetail";
	}
	/**
	 * 데이터 명세서 등록 화면 이동
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dataset/pageEdit.do", produces="text/plain;charset=UTF-8")
	public String datasetPageEdit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "dataset/datasetEdit";
	}
	/**
	 * 데이터 명세서 수정 화면 이동
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dataset/pageModify.do", produces="text/plain;charset=UTF-8")
	public String datasetPageModify(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "dataset/datasetModify";
	}
	/**
	 * 데이터 사용자현황 화면 이동
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userstats/pageList.do", produces="text/plain;charset=UTF-8")
	public String userstatsPageList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "mypage/userstats";
	}
	/**
	 * 대시보드 화면 이동
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dashboard/pageList.do", produces="text/plain;charset=UTF-8")
	public String dashboardPageList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "mypage/dashboard";
	}
	/**
	 * 데이터 판매내역 화면이동
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saleshist/pageList.do", produces="text/plain;charset=UTF-8")
	public String saleshistPageList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "mypage/saleshist";
	}
	/**
	 * 문의사항 목록화면 이동
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dataset/inquiry/pageList.do", produces="text/plain;charset=UTF-8")
	public String qnaPageList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "mypage/qna";
	}
	/**
	 * 데이터분석가요청 목록화면 이동
	 * @Author      : thlee
	 * @Date        : 2019. 10. 08.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/analy/pageList.do", produces="text/plain;charset=UTF-8")
	public String analyPageList(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam Map<String, String> params) throws Exception{
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "ANALY_LIST"));
		}
		return "mypage/analyList";
	}
	/**
	 * 데이터분석가요청 등록화면 이동
	 * @Author      : thlee
	 * @Date        : 2019. 10. 08.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/analy/pageEdit.do", produces="text/plain;charset=UTF-8")
	public String analyPageEdit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "mypage/analyEdit";
	}
	/**
	 * 데이터분석가요청 상세화면 이동
	 * @Author      : thlee
	 * @Date        : 2019. 10. 11.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/analy/pageDetail.do", produces="text/plain;charset=UTF-8")
	public String analyPageDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "mypage/analyDetail";
	}
	
	/**
	 * 데이터분석가요청 목록
	 * @Author      : thlee
	 * @Date        : 2019. 10. 08.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="/analy/getList.do")
	@ResponseBody
	public Map<String, Object> getAnalyList(@RequestParam Map<String, String> params) throws Exception {
		return mypageService.getAnalyList(params);
	}
	/**
	 * 데이터분석가요청 등록
	 * @Author      : thlee
	 * @Date        : 2019. 10. 08.
	 * @param datamodelAnalystRequestVo
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value="/analy/create.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String createAnaly(@RequestBody Map<String, Object> params) throws Exception {
		return mypageService.createAnaly(params);
	}
	/**
	 * 데이터분석가요청 상세
	 * @Author      : thlee
	 * @Date        : 2019. 10. 11.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="/analy/get.do")
	@ResponseBody
	public Map<String, Object> getAnaly(@RequestParam Map<String, String> params) throws Exception {
		return mypageService.getAnaly(params);
	}
	/**
	 * 
	 * 데이터 이용내역 조회
	 * @Author      : kyunga
	 * @Date        : 2019. 10. 15.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="/usage/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getDatasetUsagelList(@RequestParam Map<String, String> params) throws Exception {
		return mypageService.getUsageList(params);
	}
	/**
	 * 
	 * 데이터 이용내역 조회
	 * @Author      : kyunga
	 * @Date        : 2019. 10. 15.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="/wishlist/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getDatasetWishlList(@RequestParam Map<String, String> params) throws Exception {
		return mypageService.getDatasetWishlList(params);
	}
	
	/**
	 * 
	 * <pre>데이터셋 어댑터 신청 목록 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 4.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/adaptor/pageList.do", produces="text/plain;charset=UTF-8")
	public String datasetAdaptorPageList(@RequestParam Map<String, String> params, Model model) throws Exception {
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "ADAPTOR_LIST"));
		}
		return "mypage/adaptorList";
	}
	/**
	 * 
	 * <pre>데이터셋 어댑터 신청 등록 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 4.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/adaptor/pageEdit.do", produces="text/plain;charset=UTF-8")
	public String datasetAdaptorPageEdit(@RequestParam Map<String, String> params, Model model) throws Exception {
		return "mypage/adaptorEdit";
	}
	/**
	 * 
	 * <pre>데이터셋 어댑터 상세 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 4.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/adaptor/pageDetail.do", produces="text/plain;charset=UTF-8")
	public String datasetAdaptorPageDetail(@RequestParam Map<String, String> params, Model model) throws Exception {
		return "mypage/adaptorDetail";
	}
	/**
	 * 
	 * <pre>데이터셋 어댑터 신청 내역</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 4.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/adaptor/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getDatasetAdaptorList(@RequestParam Map<String, String> params) throws Exception {
		return mypageService.getDatasetAdaptorList(params);
	}
	/**
	 * 
	 * <pre>데이터셋 어댑터 신청 상세 내역</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 4.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/adaptor/get.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getDatasetAdaptor(@RequestParam Map<String, String> params) throws Exception {
		return mypageService.getDatasetAdaptor(params);
	}
	
	/**
	 * 
	 * <pre>데이터셋 어댑터 신청</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 4.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/adaptor/create.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String createDatasetAdaptor(@RequestBody Map<String, Object> params) throws Exception {
		return mypageService.createDatasetAdaptor(params);
	}
	/**
	 * <pre>인센티브 토큰 전송</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 29.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/token/transfer.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String transferIncentiveToken(@RequestBody Map<String, Object> params) throws Exception {
		return mypageService.transferIncentiveToken(params);
	}
	
	/**
	 * <pre>인센티브 토근 발급 내역 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 29.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/token/log/pageList.do", produces="text/plain;charset=UTF-8")
	public String pageIncentiveTokenLog() throws Exception {
		return "mypage/tokenLog";
	}
	
	/**
	 * <pre>인센티브 토큰 발급 내역 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 29.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/token/log/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getIncentiveTokenLog(@RequestBody Map<String, Object> params) throws Exception {
		return mypageService.getIncentiveTokenLog(params);
	}
	
	/**
	 * <pre>데이터활용 데이터 환불 내역</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 7.
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/refundUser/pageList.do", produces="text/plain;charset=UTF-8")
	public String pageRefendRequest() throws Exception {
		return "mypage/refundUserList";
	}

	/**
	 * <pre>데이터제공 데이터 환불 내역</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 7.
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/refundProvider/pageList.do", produces="text/plain;charset=UTF-8")
	public String pageRefendResult(HttpServletResponse response) throws Exception {
		return "mypage/refundProviderList";
	}
	
}
