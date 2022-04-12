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
package kr.co.smartcity.user.feature.dataset.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.user.common.CommonConst;
import kr.co.smartcity.user.common.component.HttpComponent;
import kr.co.smartcity.user.feature.dataset.service.DatasetService;
import kr.co.smartcity.user.feature.dataset.vo.DatasetUseRequestVo;
import kr.co.smartcity.user.feature.dataset.vo.DatasetVo;

@Controller
@RequestMapping("/dataset")
public class DatasetController {

	@Resource(name="datasetService")
	private DatasetService datasetService;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setAutoGrowNestedPaths(true); // insert this line
        binder.setAutoGrowCollectionLimit(5000); // insert thist line
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	
	/**
	 * 데이터셋 목록 화면
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/pageList.do", produces="text/plain;charset=UTF-8")
	public String goPageDatasetList(DatasetVo datasetVo, Model model, Map<String, String> params) throws Exception {
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "DATASET_LIST"));
		}
		return "dataset/datasetList";
	}
	
	/**
	 * 데이터셋 상세 화면
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/pageDetail.do", produces="text/plain;charset=UTF-8")
	public String goPageDatasetDetail(ModelMap model, DatasetVo datasetVo) throws Exception {
		model.addAttribute("endpoint", datasetService.getDatasetEndPoint());
		model.addAttribute("alivePlatformServer", datasetService.isAlivePlatformServer());
		
		return "dataset/datasetDetail";
	}
	
	/**
	 * 데이터셋 등록 화면
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @return
	 */
	@RequestMapping(value="/pageEdit.do", produces="text/plain;charset=UTF-8")
	public String goPageDatasetRegist() {
		return "dataset/datasetRegist";
	}
	
	/**
	 * 데이터셋 수정 화면
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @return
	 */
	@RequestMapping(value="/pageModify.do", produces="text/plain;charset=UTF-8")
	public String goPageDatasetModify() {
		return "dataset/datasetModify";
	}
	
	/**
	 * 데이터셋 이용신청 팝업
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @return
	 */
	@RequestMapping(value="/use/pageEdit.do", produces="text/plain;charset=UTF-8")
	public String popupPageEdit() {
		return "dataset/pop/datasetEdit";
	}
	
	/**
	 * 데이터셋 목록 데이터 호출
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getList(@RequestParam Map<String, String> params) throws Exception {
		return datasetService.getDatasetList(params);
	}
	
	/**
	 * 데이터셋 데이터 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 8.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/get.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getDataset(@RequestParam Map<String, String> params) throws Exception {
		return datasetService.getDataset(params);
	}
	
	/**
	 * 데이터셋 데이터 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@PostMapping(value="/create.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String createDataset(DatasetVo datasetVo, HttpServletRequest req) throws Exception {
		return datasetService.createDataset(datasetVo, req);
	}
	
	/**
	 * 데이터셋 데이터 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String modifyDataset(DatasetVo datasetVo, HttpServletRequest req) throws Exception {
		return datasetService.modifyDataset(datasetVo, req);
	}
	
	/**
	 * <pre>데이터셋 데이터 부분수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 29.
	 * @param datasetVo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@PatchMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String patchDataset(@RequestBody Map<String, Object> params) throws Exception {
		return datasetService.patchDataset(params);
	}
	
	/**
	 * 데이터셋 문의사항 목록
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/inquiry/getList.do", produces="text/plain;charset=UTF-8")
	public String popupPageModify(@RequestParam Map<String, String> params) throws Exception {
		return datasetService.getQnaList(params);
	}
	
	/**
	 * 데이터셋 문의하기 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value="/inquiry/create.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String qnaCreate(@RequestBody Map<String, Object> params) throws Exception {
		return datasetService.createQna(params);
	}
	
	/**
	 * 데이터셋 문의하기 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@PutMapping(value="/inquiry/modify.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String qnaModify(@RequestBody Map<String, Object> params) throws Exception {
		return datasetService.modifyQna(params);
	}

	/**
	 * 데이터셋 문의하기 삭제
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping("/inquiry/remove.do")
	@ResponseBody
	public String qnaRemove(@RequestParam Map<String, Object> params) throws Exception {
		return datasetService.deleteQna(params);
	}
	
	/**
	 * 데이터셋 관심상품 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value="/wishlist/create.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String wishlistCreate(@RequestBody  Map<String, Object> params, HttpServletRequest request) throws Exception {
		return datasetService.createDatasetWishlist(params, request);
	}
	
	/**
	 * 데이터셋 관심상품 해제
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping(value="/wishlist/remove.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String wishlistRemove(@RequestBody  Map<String, Object> params, HttpServletRequest request) throws Exception {
		return datasetService.deleteDatasetWishlist(params, request);
	}
	
	/**
	 * 데이터셋 이용신청 등록
	 * @return 
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value="/use/create.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String useCreate(@RequestBody Map<String, Object> params) throws Exception {
		return datasetService.createDatasetUsage(params);
	}
	
	/**
	 * <pre>데이터셋 이용신청 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 17.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PatchMapping(value="/use/modify.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public void modifyDatasetUsage(@RequestBody Map<String, Object> params) throws Exception {
		datasetService.modifyDatasetUsage(params);
	}
	
	/**
	 * 데이터셋 이용신청 해제
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping(value="/use/remove.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String cancelDatasetUsage(@RequestBody DatasetUseRequestVo DatasetUseRequestVo) throws Exception {
		return datasetService.deleteDatasetUsage(DatasetUseRequestVo);
	}
	
	/**
	 * <pre>데이터셋 이용신청 환불 내역 목록 조회(활용자)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/use/refundUser/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getDatasetUsageRefundListByUser(@RequestParam Map<String, String> params) throws Exception {
		return datasetService.getDatasetUsageRefundListByUser(params);
	}
	
	/**
	 * <pre>데이터셋 이용신청 환불 내역 목록 조회(제공자)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/use/refundProvider/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getDatasetUsageRefundListByProvider(@RequestParam Map<String, String> params) throws Exception {
		return datasetService.getDatasetUsageRefundListByProvider(params);
	}
	
	/**
	 * <pre>데이터셋 이용신청 환불 신청 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/use/refund/create.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String createDatasetUsageRefund(@RequestBody Map<String, Object> params) throws Exception {
		return datasetService.createDatasetUsageRefund(params);
	}
	/**
	 * <pre>데이터셋 이용신청 환불 신청 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param params
	 * @throws Exception
	 */
	@PatchMapping(value="/use/refund/modify.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public void modifyDatasetUsageRefund(@RequestBody Map<String, Object> params) throws Exception {
		datasetService.modifyDatasetUsageRefund(params);
	}
	
	/**
	 * 데이터 모델 리스트 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 1.
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="/model/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getDataModelList(DatasetVo datasetVo) throws Exception {
		return datasetService.getDataModelList(datasetVo); 
	}
	/**
	 * 데이터 모델  개별 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 1.
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="/model/getDetail.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getDataModelDetail(DatasetVo datasetVo) throws Exception {
		return datasetService.getDataModel(datasetVo); 
	}
	/**
	 * 데이터 인스턴스 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 1.
	 * @param dsvModelId
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="/instance/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getDataInstanceList(DatasetVo datasetVo) throws Exception {
		return datasetService.getDataInstanceList(datasetVo); 
	}
	
	/**
	 * 데이터 인스턴스 개별 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 1.
	 * @param dsvModelId
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="/instance/getDetail.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getDataInstanceDetail(DatasetVo datasetVo) throws Exception {
		return datasetService.getDataInstance(datasetVo); 
	}
	
	/**
	 * <pre>카테고리 목록 조회</pre>
	 * @Author      : thlee
	 * @Date        : 2019. 10. 17.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getCategoryList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getCategoryList(@RequestParam Map<String, String> params) throws Exception {
		return datasetService.getCategoryList(params);
	}
	
	/**
	 * <pre>데이터 샘플 조회</pre>
	 * @Author      : thlee
	 * @Date        : 2019. 10. 30.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/sample/get.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getSampleData(@RequestParam Map<String, String> params) throws Exception {
		return datasetService.getSampleData(params);
	}
	
	/**
	 * <pre>유저 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 6.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/user/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getUserList(@RequestParam Map<String, String> params) throws Exception {
		return datasetService.getUserList(params);
	}
	
	/**
	 * <pre>만족도 평가 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 7.
	 * @param datasetVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/satisfaction/create.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String createDatasetSatisfaction(@RequestBody Map<String, Object> params) throws Exception {
		return datasetService.createDatasetSatisfaction(params);
	}
	
	/**
	 * <pre>데이터셋 오리진 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 27.
	 * @param datasetVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/datasetorigin/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getDatasetOriginList(DatasetVo datasetVo) throws Exception {
		return datasetService.getDatasetOriginList(datasetVo); 
	}
	
	/**
	 * <pre>가격정책 기본 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param policyId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/pricePolicies/getList.do")
	public @ResponseBody String getPricePoliciesList() throws Exception {
		return datasetService.getPricePoliciesList();
	}
	
	/**
	 * <pre>데이터셋 가격정책 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param policyId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/datasetPricePolicies/getList.do")
	public @ResponseBody String getDatasetPricePoliciesList(@RequestParam Map<String, String> params) throws Exception {
		return datasetService.getDatasetPricePoliciesList(params);
	}
	
	/**
	 * <pre>임시 결제팝업</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 16.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/pay.do", produces="text/plain;charset=UTF-8")
	public String goPopupPay() throws Exception {
		return "popup/dataset/datasetUsagePayPopup";
	}
	/**
	 * <pre>임시 결제팝업</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 16.
	 * @return
	 * @throws Exception
	 */
	@PatchMapping(value="/pay_proc.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody void procPopupPaySuccess(@RequestBody Map<String, Object> params) throws Exception {
		datasetService.modifyDatasetUsagePay(params);
	}
	
	/**
	 * <pre>데이터셋 디바이스 연결 설정 목록 조회 (인터페이스)</pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 16.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/devices/getList.do")
	public @ResponseBody String getDatasetDeviceMappingList(@RequestParam Map<String, String> params) throws Exception {
		return datasetService.getDatasetDeviceMappingList(params);
	}
	
	/**
	 * <pre>데이터셋 디바이스 연결 설정 삭제 (인터페이스)</pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 19.
	 * @param params
	 * @throws Exception
	 */
	@DeleteMapping(value="/devices/remove.do")
	public @ResponseBody String removeDatasetDeviceMappingList(@RequestParam Map<String, String> params) throws Exception {
		return datasetService.removeDatasetDeviceMappingList(params);
	}
}
