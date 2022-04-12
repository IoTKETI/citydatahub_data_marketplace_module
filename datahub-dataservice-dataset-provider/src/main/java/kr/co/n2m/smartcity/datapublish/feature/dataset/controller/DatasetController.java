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
package kr.co.n2m.smartcity.datapublish.feature.dataset.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import kr.co.n2m.smartcity.datapublish.common.component.CommonComponent;
import kr.co.n2m.smartcity.datapublish.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.datapublish.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.data.service.DataService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.DatasetService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.analysisApprovalRequests.DatasetAnalysisApprovalRequestsService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.attachFiles.DatasetAttachFilesService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.columns.DatasetColumnsService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.datasetAdaptor.DatasetAdaptorService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.datasetOrigin.DatasetOriginService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.infDevice.DatasetInfDeviceService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.inquiry.DatasetInquiryService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.instance.DatasetInstanceService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.pricePolicies.DatasetPricePoliciesService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.rating.DatasetRatingService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.requestUsage.DatasetRequestUsageService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.searchCondition.DatasetSearchConditionService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.usageHistory.DatasetUsageHistoryService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.wishlist.DatasetWishlistService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.DatasetVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.SrchDatasetVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.analysisApprovalRequests.DataAnalystRequestModelVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.analysisApprovalRequests.DataAnalystRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.analysisApprovalRequests.SrchDataAnalystRequestModelVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.analysisApprovalRequests.SrchDataAnalystRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.attachFiles.DatasetFileVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.attachFiles.SrchDatasetFileVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.DatasetOutputVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.SrchDatasetOutputVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor.DatasetAdaptorFieldVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor.DatasetAdaptorVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor.SrchDatasetAdaptorFieldVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor.SrchDatasetAdaptorVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetOrigin.DatasetOriginVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetOrigin.SrchDatasetOriginVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.infDevice.DatasetInfDeviceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.inquiry.DatasetInquiryVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.inquiry.SrchDatasetInquiryVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.instance.DatasetInstanceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.instance.SrchDatasetInstanceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.pricePolicies.DatasetPricePoliciesPeriodPriceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.pricePolicies.DatasetPricePoliciesVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.pricePolicies.SrchDatasetPricePoliciesPeriodPriceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.pricePolicies.SrchDatasetPricePoliciesVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.rating.DatasetSatisfactionRatingVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.rating.SrchDatasetSatisfactionRatingVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestPaymentVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestReceptionVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestRefundVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestPaymentVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestReceptionVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestRefundVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.DatasetSearchInfoVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.SrchDatasetSearchInfoVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.usageHistory.SrchDatasetUseHistoryVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.wishlist.SrchDatasetInterestVo;

@RestController
public class DatasetController extends CommonComponent{
	Logger logger = (Logger) LoggerFactory.getLogger(DatasetController.class);
	
	@Autowired DatasetService datasetService;
	@Autowired DatasetAttachFilesService datasetAttachFilesService;
	@Autowired DatasetColumnsService datasetColumnsService;
	@Autowired DatasetOriginService datasetOriginService;
	@Autowired DatasetInquiryService datasetInquiryService;
	@Autowired DatasetInstanceService datasetInstanceService;
	@Autowired DatasetRatingService datasetRatingService;
	@Autowired DatasetRequestUsageService datasetRequestUsageService;
	@Autowired DatasetSearchConditionService datasetSearchConditionService;
	@Autowired DatasetWishlistService datasetWishlistService;
	@Autowired DatasetAnalysisApprovalRequestsService datasetAnalysisApprovalRequestsService;
	@Autowired DatasetAdaptorService datasetAdaptorService;
	@Autowired DatasetUsageHistoryService datasetUsageHistoryService;
	@Autowired DatasetPricePoliciesService datasetPricePoliciesService;
	@Autowired DatasetInfDeviceService datasetInfDeviceService;
	@Autowired DataService dataService;
	
	/**
	 * <pre>데이터 샘플 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/sampleData")
	public ResponseEntity<Object> getSampleData(@PathVariable String datasetId, @RequestParam Map<String, String> params) throws Exception {
		return dataService.getDataList(datasetId, null, params, true);
	}
	
	/**
	 * <pre>데이터셋 어댑터 신청 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetAdaptorVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/datasetAdaptor")
	public ResponseEntity<Object> getDatasetAdaptorList(SrchDatasetAdaptorVo srchDatasetAdaptorVo, HttpServletRequest request) throws Exception {
		Map<String, Object> resMap = datasetAdaptorService.getDatasetAdaptorList(srchDatasetAdaptorVo);
		return ResponseUtil.makeResponseEntity(resMap);
	}

	
	/**
	 * <pre>데이터셋 어댑터 신청</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param loginUserId
	 * @param srchDatasetAdaptorVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/datasetAdaptor")
	public ResponseEntity<Object> createDatasetAdaptor(@RequestHeader("UserId") String loginUserId, @RequestBody SrchDatasetAdaptorVo srchDatasetAdaptorVo) throws Exception {
		srchDatasetAdaptorVo.setLoginUserId(loginUserId);
		
		String resultKey = datasetAdaptorService.createDatasetAdaptor(srchDatasetAdaptorVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/datasetAdaptor/" + resultKey);
	}
	
	/**
	 * <pre>데이터셋 어댑터 신청 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetAdapterId
	 * @param srchDatasetAdaptorVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/datasetAdaptor/{datasetAdapterId}")
	public ResponseEntity<Object> getDatasetAdaptor(@PathVariable String datasetAdapterId) throws Exception {
		DatasetAdaptorVo datasetAdaptorVo = datasetAdaptorService.getDatasetAdaptor(datasetAdapterId);
		return ResponseUtil.makeResponseEntity(datasetAdaptorVo);
	}
	

	
	/**
	 * <pre>데이터셋 어댑터 출력정보 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetAdapterId
	 * @param srchDatasetAdaptorVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/datasetAdaptor/{datasetAdapterId}/columns")
	public ResponseEntity<Object> getDatasetAdaptorFieldList(@PathVariable String datasetAdapterId, SrchDatasetAdaptorFieldVo srchDatasetAdaptorFieldVo) throws Exception {
		StringUtil.compareUniqueId(datasetAdapterId, srchDatasetAdaptorFieldVo.getId());
		
		List<DatasetAdaptorFieldVo> datasetAdaptorFieldList = datasetAdaptorService.getDatasetAdaptorFieldList(srchDatasetAdaptorFieldVo);
		return ResponseUtil.makeResponseEntity(datasetAdaptorFieldList);
	}
	
	/**
	 * <pre>데이터셋 어댑터 출력정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetAdapterId
	 * @param srchDatasetAdaptorVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/datasetAdaptor/{datasetAdapterId}/columns")
	public ResponseEntity<Object> createDatasetAdaptorField(@PathVariable String datasetAdapterId, SrchDatasetAdaptorFieldVo srchDatasetAdaptorFieldVo) throws Exception {
		StringUtil.compareUniqueId(datasetAdapterId , srchDatasetAdaptorFieldVo.getId());
		
		String resultKey = datasetAdaptorService.createDatasetAdaptorField(srchDatasetAdaptorFieldVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/datasetAdaptor/"+datasetAdapterId+"/columns/"+resultKey);
	}
	
	/**
	 * <pre>데이터셋 어댑터 출력정보 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 8.
	 * @param datasetAdapterId
	 * @param columnId
	 * @param srchDatasetAdaptorFieldVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/datasetAdaptor/{datasetAdapterId}/columns/{columnId}")
	public ResponseEntity<Object> getDatasetAdaptorField(@PathVariable String datasetAdapterId, @PathVariable String columnId) throws Exception {
		DatasetAdaptorFieldVo datasetAdaptorFieldVo = datasetAdaptorService.getDatasetAdaptorField(datasetAdapterId, columnId);
		return ResponseUtil.makeResponseEntity(datasetAdaptorFieldVo);
	}
	
	/**
	 * <pre>데이터셋 어댑터 출력정보 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 8.
	 * @param datasetAdapterId
	 * @param columnId
	 * @param srchDatasetAdaptorFieldVo
	 * @return
	 * @throws Exception
	 * @throws NotFoundException 
	 */
	@PutMapping(value="/dataservice/datasetAdaptor/{datasetAdapterId}/columns/{columnId}")
	public ResponseEntity<Object> modifyDatasetAdaptorField(@PathVariable String datasetAdapterId, @PathVariable String columnId, SrchDatasetAdaptorFieldVo srchDatasetAdaptorFieldVo) throws Exception {
		StringUtil.compareUniqueId(columnId        , srchDatasetAdaptorFieldVo.getId());
		StringUtil.compareUniqueId(datasetAdapterId, srchDatasetAdaptorFieldVo.getAdaptorId());
		
		datasetAdaptorService.modifyDatasetAdaptorField(srchDatasetAdaptorFieldVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터셋 어댑터 출력정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 8.
	 * @param datasetAdapterId
	 * @param columnId
	 * @param srchDatasetAdaptorFieldVo
	 * @return
	 * @throws Exception
	 * @throws NotFoundException 
	 */
	@DeleteMapping(value="/dataservice/datasetAdaptor/{datasetAdapterId}/columns/{columnId}")
	public ResponseEntity<Object> deleteDatasetAdaptorField(@PathVariable String datasetAdapterId, @PathVariable String columnId, SrchDatasetAdaptorFieldVo srchDatasetAdaptorFieldVo) throws Exception {
		StringUtil.compareUniqueId(columnId        , srchDatasetAdaptorFieldVo.getId());
		StringUtil.compareUniqueId(datasetAdapterId, srchDatasetAdaptorFieldVo.getAdaptorId());
		
		datasetAdaptorService.deleteDatasetAdaptorField(srchDatasetAdaptorFieldVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * <pre>데이터셋 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param loginUserId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset")
	public ResponseEntity<Object> getDatasetList(@RequestHeader(value="UserId", required=false) String loginUserId, SrchDatasetVo srchDatasetVo) throws Exception {
		srchDatasetVo.setLoginUserId(loginUserId);
		if (StringUtils.equals("review", srchDatasetVo.getReqType())) srchDatasetVo.setPaging(false);
		
		Map<String, Object> resMap = datasetService.getDatasetList(srchDatasetVo);
		return ResponseUtil.makeResponseEntity(resMap);
	}

	/**
	 * <pre>데이터셋 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param loginUserId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset")
	public ResponseEntity<Object> createDataset(@RequestHeader("UserId") String loginUserId, @RequestBody SrchDatasetVo srchDatasetVo) throws Exception {
		srchDatasetVo.setLoginUserId(loginUserId);
		
		String resultKey = datasetService.createDataset(srchDatasetVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/" + resultKey);
	}
	
	/**
	 * <pre>데이터셋 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param loginUserId
	 * @param datasetId
	 * @param nohit
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}")
	public ResponseEntity<Object> getDataset(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, String nohit) throws Exception {
		DatasetVo datasetVo = datasetService.getDataset(datasetId, "yes".equalsIgnoreCase(nohit));
		return ResponseUtil.makeResponseEntity(datasetVo);
	}
	
	/**
	 * <pre>데이터셋 수정(전체수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param loginUserId
	 * @param datasetId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value="/dataservice/dataset/{datasetId}")
	public ResponseEntity<Object> modifyDataset(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @RequestBody SrchDatasetVo srchDatasetVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetVo.getId());
		srchDatasetVo.setLoginUserId(loginUserId);
		
		datasetService.modifyDataset(srchDatasetVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터셋 수정(부분수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param loginUserId
	 * @param datasetId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@PatchMapping(value="/dataservice/dataset/{datasetId}")
	public ResponseEntity<Object> patchDataset(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @RequestBody SrchDatasetVo srchDatasetVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetVo.getId());
		srchDatasetVo.setLoginUserId(loginUserId);

		datasetService.patchDataset(srchDatasetVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터셋 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param loginUserId
	 * @param datasetId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}")
	public ResponseEntity<Object> deleteDataset(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @RequestBody SrchDatasetVo srchDatasetVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetVo.getId());
		
		datasetService.deleteDataset(srchDatasetVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	
	/**
	 * <pre>데이터셋 출력정보 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param srchDatasetCollaborationVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/columns")
	public ResponseEntity<Object> getDatasetOutputList(@PathVariable String datasetId, SrchDatasetOutputVo srchDatasetOutputVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetOutputVo.getDatasetId());
		
		List<DatasetOutputVo> datasetOutputList = datasetColumnsService.getDatasetOutputList(srchDatasetOutputVo); 
		return ResponseUtil.makeResponseEntity(datasetOutputList);
	}
	

	/**
	 * <pre>데이터셋 출력정보 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 15.
	 * @param datasetId
	 * @param columnId
	 * @param srchDatasetOutputVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/columns/{columnId}")
	public ResponseEntity<Object> getDatasetOutput(@PathVariable String datasetId, @PathVariable String columnId, SrchDatasetOutputVo srchDatasetOutputVo) throws Exception {
		DatasetOutputVo datasetOutputVo = datasetColumnsService.getDatasetOutput(datasetId, columnId); 
		return ResponseUtil.makeResponseEntity(datasetOutputVo);
	}
	
	/**
	 * <pre>데이터셋 출력정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/columns")
	public ResponseEntity<Object> createDatasetOutput(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @RequestBody SrchDatasetOutputVo srchDatasetOutputVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetOutputVo.getDatasetId());
		
		datasetColumnsService.createDatasetOutput(srchDatasetOutputVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/"+datasetId+"/columns");
	}

	/**
	 * <pre>데이터셋 출력정보 수정(전체수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 14.
	 * @param applicationId
	 * @param srchDatasetUseRequestVo
	 * @return
	 * @throws Exception
	 * @throws NotFoundException 
	 */
	@PutMapping(value="/dataservice/dataset/{datasetId}/columns/{columnId}")
	public ResponseEntity<Object> modifyDatasetOutput(@PathVariable String datasetId, @PathVariable String columnId, @RequestBody SrchDatasetOutputVo srchDatasetOutputVo) throws Exception {
		StringUtil.compareUniqueId(columnId       , srchDatasetOutputVo.getId());
		StringUtil.compareUniqueId(datasetId      , srchDatasetOutputVo.getDatasetId());
		
		datasetColumnsService.modifyDatasetOutput(srchDatasetOutputVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터셋 출력정보 수정(부분수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 14.
	 * @param applicationId
	 * @param srchDatasetUseRequestVo
	 * @return
	 * @throws Exception
	 * @throws NotFoundException 
	 */
	@PatchMapping(value="/dataservice/dataset/{datasetId}/columns/{columnId}")
	public ResponseEntity<Object> patchDatasetOutput(@PathVariable String datasetId, @PathVariable String columnId, @RequestBody SrchDatasetOutputVo srchDatasetOutputVo) throws Exception {
		StringUtil.compareUniqueId(columnId       , srchDatasetOutputVo.getId());
		StringUtil.compareUniqueId(datasetId      , srchDatasetOutputVo.getDatasetId());
		
		datasetColumnsService.patchDatasetOutput(srchDatasetOutputVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터셋 출력정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param collaboratorId
	 * @param srchDatasetCollaborationVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/columns/{columnId}")
	public ResponseEntity<Object> deleteDatasetOutput(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @PathVariable String columnId, @RequestBody SrchDatasetOutputVo srchDatasetOutputVo) throws Exception {
		StringUtil.compareUniqueId(columnId       , srchDatasetOutputVo.getId());
		StringUtil.compareUniqueId(datasetId      , srchDatasetOutputVo.getDatasetId());
		
		datasetColumnsService.deleteDatasetOutput(srchDatasetOutputVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * <pre>데이터셋 인스턴스 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param srchDatasetCollaborationVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/instance")
	public ResponseEntity<Object> getDatasetInstanceList(@PathVariable String datasetId, SrchDatasetInstanceVo srchDatasetInstanceVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetInstanceVo.getDatasetId());
		
		List<DatasetInstanceVo> datasetInstanceList = datasetInstanceService.getDatasetInstanceList(srchDatasetInstanceVo); 
		return ResponseUtil.makeResponseEntity(datasetInstanceList);
	}
	
	/**
	 * <pre>데이터셋 인스턴스 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/instance")
	public ResponseEntity<Object> createDatasetInstance(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @RequestBody SrchDatasetInstanceVo srchDatasetInstanceVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetInstanceVo.getDatasetId());
		
		datasetInstanceService.createDatasetInstance(srchDatasetInstanceVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/"+datasetId+"/instance");
	}
	
	/**
	 * <pre>데이터셋 인스턴스 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param collaboratorId
	 * @param srchDatasetCollaborationVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/instance/{instanceId}")
	public ResponseEntity<Object> deleteDatasetInstance(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @PathVariable String instanceId, @RequestBody SrchDatasetInstanceVo srchDatasetInstanceVo) throws Exception {
		StringUtil.compareUniqueId(datasetId      , srchDatasetInstanceVo.getDatasetId());
		StringUtil.compareUniqueId(instanceId     , srchDatasetInstanceVo.getId());
		
		datasetInstanceService.deleteDatasetInstance(srchDatasetInstanceVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * <pre>데이터셋 조회조건 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param srchDatasetCollaborationVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/searchCondition")
	public ResponseEntity<Object> getDatasetSearchInfoList(@PathVariable String datasetId, SrchDatasetSearchInfoVo srchDatasetSearchInfoVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetSearchInfoVo.getDatasetId());
		
		List<DatasetSearchInfoVo> datasetSearchInfoList = datasetSearchConditionService.getDatasetSearchInfoList(srchDatasetSearchInfoVo); 
		return ResponseUtil.makeResponseEntity(datasetSearchInfoList);
	}
	
	/**
	 * <pre>데이터셋 조회조건 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/searchCondition")
	public ResponseEntity<Object> createDatasetSearchInfo(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @RequestBody SrchDatasetSearchInfoVo srchDatasetSearchInfoVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetSearchInfoVo.getDatasetId());
		
		datasetSearchConditionService.createDatasetSearchInfo(srchDatasetSearchInfoVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/"+datasetId+"/searchCondition");
	}
	
	/**
	 * <pre>데이터셋 조회조건 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param collaboratorId
	 * @param srchDatasetCollaborationVo
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value="/dataservice/dataset/{datasetId}/searchCondition/{searchConditionId}")
	public ResponseEntity<Object> updateDatasetSearchInfo(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @PathVariable String searchConditionId, @RequestBody SrchDatasetSearchInfoVo srchDatasetSearchInfoVo) throws Exception {
		StringUtil.compareUniqueId(searchConditionId      , srchDatasetSearchInfoVo.getId());
		StringUtil.compareUniqueId(datasetId              , srchDatasetSearchInfoVo.getDatasetId());
		
		datasetSearchConditionService.updateDatasetSearchInfo(srchDatasetSearchInfoVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * <pre>데이터셋 조회조건 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param collaboratorId
	 * @param srchDatasetCollaborationVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/searchCondition/{searchConditionId}")
	public ResponseEntity<Object> deleteDatasetSearchInfo(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @PathVariable String searchConditionId, @RequestBody SrchDatasetSearchInfoVo srchDatasetSearchInfoVo) throws Exception {
		StringUtil.compareUniqueId(searchConditionId      , srchDatasetSearchInfoVo.getId());
		StringUtil.compareUniqueId(datasetId              , srchDatasetSearchInfoVo.getDatasetId());
		
		datasetSearchConditionService.deleteDatasetSearchInfo(srchDatasetSearchInfoVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * <pre>데이터셋 파일 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param srchDatasetCollaborationVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/attachFiles")
	public ResponseEntity<Object> getDatasetFileList(@PathVariable String datasetId, SrchDatasetFileVo srchDatasetFileVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetFileVo.getDatasetId());
		
		List<DatasetFileVo> datasetFileList = datasetAttachFilesService.getDatasetFileList(srchDatasetFileVo); 
		return ResponseUtil.makeResponseEntity(datasetFileList);
	}
	
	/**
	 * <pre>데이터셋 파일 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/attachFiles")
	public ResponseEntity<Object> createDatasetFile(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, HttpServletRequest request) throws Exception {
		datasetAttachFilesService.createDatasetFile(datasetId, request);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/"+datasetId+"/attachFiles");
	}
	
	/**
	 * <pre>데이터셋 파일 다운로드</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param collaboratorId
	 * @param srchDatasetCollaborationVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/attachFiles/{fileId}")
	public FileSystemResource downloadDatasetFile(@PathVariable String datasetId, @PathVariable long fileId, HttpServletResponse response) throws Exception {
		return datasetAttachFilesService.downloadDatasetFile(datasetId, fileId, response);
	}
	
	/**
	 * <pre>데이터셋 파일 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param collaboratorId
	 * @param srchDatasetCollaborationVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/attachFiles/{fileId}")
	public ResponseEntity<Object> deleteDatasetFile(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @PathVariable long fileId, @RequestBody SrchDatasetFileVo srchDatasetFileVo) throws Exception {
		StringUtil.compareUniqueId(datasetId      , srchDatasetFileVo.getDatasetId());
		StringUtil.compareUniqueId(fileId         , srchDatasetFileVo.getId());
		
		datasetAttachFilesService.deleteDatasetFile(srchDatasetFileVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}

	/**
	 * <pre>데이터셋 문의 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param srchDatasetInquiryVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/inquiry")
	public ResponseEntity<Object> getDatasetInquiryList(@PathVariable String datasetId, SrchDatasetInquiryVo srchDatasetInquiryVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetInquiryVo.getDatasetId());
		
		List<DatasetInquiryVo> datasetInquiryList = datasetInquiryService.getDatasetInquiryList(srchDatasetInquiryVo);
		return ResponseUtil.makeResponseEntity(datasetInquiryList);
	}
	
	/**
	 * <pre>데이터셋 문의 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param datasetId
	 * @param srchDatasetInquiryVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/inquiry")
	public ResponseEntity<Object> createDatasetInquiry(@PathVariable String datasetId, @RequestBody SrchDatasetInquiryVo srchDatasetInquiryVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetInquiryVo.getDatasetId());
		
		String resultKey = datasetInquiryService.createDatasetInquiry(srchDatasetInquiryVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/" + datasetId + "/inquiry/" + resultKey);
	}
	
	/**
	 * <pre>데이터셋 문의 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param datasetId
	 * @param inquiryId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/inquiry/{inquiryId}")
	public ResponseEntity<Object> getDatasetInquiry(@PathVariable String datasetId, @PathVariable long inquiryId) throws Exception {
		DatasetInquiryVo datasetInquiryVo = datasetInquiryService.getDatasetInquiry(datasetId, inquiryId);
		return ResponseUtil.makeResponseEntity(datasetInquiryVo);
	}
	
	/**
	 * <pre>데이터셋 문의 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param datasetId
	 * @param inquiryId
	 * @param srchDatasetInquiryVo
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value="/dataservice/dataset/{datasetId}/inquiry/{inquiryId}")
	public ResponseEntity<Object> modifyDatasetInquiry(@PathVariable String datasetId, @PathVariable long inquiryId, @RequestBody SrchDatasetInquiryVo srchDatasetInquiryVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetInquiryVo.getDatasetId());
		StringUtil.compareUniqueId(String.valueOf(inquiryId), String.valueOf(srchDatasetInquiryVo.getId()));
		
		datasetInquiryService.modifyDatasetInquiry(srchDatasetInquiryVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터셋 문의 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param datasetId
	 * @param inquiryId
	 * @param srchDatasetInquiryVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/inquiry/{inquiryId}")
	public ResponseEntity<Object> deleteDatasetInquiry(@PathVariable String datasetId, @PathVariable long inquiryId, SrchDatasetInquiryVo srchDatasetInquiryVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetInquiryVo.getDatasetId());
		StringUtil.compareUniqueId(String.valueOf(inquiryId), String.valueOf(srchDatasetInquiryVo.getId()));
		
		datasetInquiryService.deleteDatasetInquiry(srchDatasetInquiryVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	
	/**
	 * <pre>데이터셋 관심상품 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param loginUserId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/wishlist")
	  public ResponseEntity<Object> getDatasetWishlistList(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, SrchDatasetInterestVo srchDatasetInterestVo) throws Exception {
		srchDatasetInterestVo.setLoginUserId(loginUserId);
		
		Map<String, Object> resMap = datasetWishlistService.getDatasetWishlistList(srchDatasetInterestVo);
	    return ResponseUtil.makeResponseEntity(resMap);
	}
	
	
	/**
	 * <pre>데이터셋 관심상품 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param loginUserId
	 * @param srchDatasetInterestVo
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/wishlist")
	public ResponseEntity<Object> createDatasetWishlist(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @RequestBody SrchDatasetInterestVo srchDatasetInterestVo) throws Exception {
		StringUtil.compareUniqueId(datasetId  , srchDatasetInterestVo.getDatasetId());
		srchDatasetInterestVo.setLoginUserId(loginUserId);
		
		datasetWishlistService.createDatasetWishlist(srchDatasetInterestVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED);
	}

	/**
	 * <pre>데이터셋 관심상품 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 8.
	 * @param datasetId
	 * @param wishlistId
	 * @param srchDatasetInterestVo
	 * @return
	 * @throws Exception
	 * @throws NotFoundException 
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/wishlist/{wishlistId}")
	public ResponseEntity<Object> deleteDatasetWishlist(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @PathVariable String wishlistId, @RequestBody SrchDatasetInterestVo srchDatasetInterestVo) throws Exception {
		StringUtil.compareUniqueId(wishlistId , srchDatasetInterestVo.getId());
		StringUtil.compareUniqueId(datasetId  , srchDatasetInterestVo.getDatasetId());
		
		datasetWishlistService.deleteDatasetWishlist(srchDatasetInterestVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * <pre>데이터셋 이용신청 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param loginUserId
	 * @param datasetId
	 * @param srchDatasetUseRequestVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/requestUsage")
	public ResponseEntity<Object> getDatasetUsageList(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, SrchDatasetUseRequestVo srchDatasetUseRequestVo) throws Exception {
		srchDatasetUseRequestVo.setLoginUserId(loginUserId);
	    
	    Map<String, Object> resMap = datasetRequestUsageService.getDatasetUsageList(srchDatasetUseRequestVo);
	    return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * <pre>데이터셋 이용신청 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param loginUserId
	 * @param srchDatasetUseRequestVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/requestUsage")
	public ResponseEntity<Object> createDatasetUsage(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @RequestBody SrchDatasetUseRequestVo srchDatasetUseRequestVo) throws Exception {
		StringUtil.compareUniqueId(datasetId  , srchDatasetUseRequestVo.getDatasetId());
		srchDatasetUseRequestVo.setLoginUserId(loginUserId);
		
		
		
		String resultKey = datasetRequestUsageService.createDatasetUsage(srchDatasetUseRequestVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/"+srchDatasetUseRequestVo.getDatasetId()+"/requestUsage/"+resultKey);
	}
	
	/**
	 * <pre>구독 이용신청 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/subscription/{datasetId} ")
	public ResponseEntity<Object> createDatasetSubscriptionUsage(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @RequestBody SrchDatasetUseRequestVo srchDatasetUseRequestVo) throws Exception {
		
		String resultKey = datasetRequestUsageService.createDatasetUsage(srchDatasetUseRequestVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/"+srchDatasetUseRequestVo.getDatasetId()+"/requestUsage/"+resultKey);
	}
	
	
	
	/**
	 * <pre>데이터셋 이용신청 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 8.
	 * @param datasetId
	 * @param requestUsageId
	 * @param srchDatasetUseRequestVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}")
	public ResponseEntity<Object> getDatasetUsage(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @PathVariable long requestUsageId) throws Exception {
		DatasetUseRequestVo datasetUseRequestVo = datasetRequestUsageService.getDatasetUsage(datasetId, requestUsageId);
	    return ResponseUtil.makeResponseEntity(datasetUseRequestVo);
	}
	
	/**
	 * <pre>데이터셋 이용신청 수정(전체수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param applicationId
	 * @param srchDatasetUseRequestVo
	 * @return
	 * @throws Exception
	 * @throws NotFoundException 
	 */
	@PutMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}")
	public ResponseEntity<Object> modifyDatasetUsage(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @PathVariable long requestUsageId, @RequestBody SrchDatasetUseRequestVo srchDatasetUseRequestVo) throws Exception {
		StringUtil.compareUniqueId(requestUsageId , srchDatasetUseRequestVo.getId());
		StringUtil.compareUniqueId(datasetId      , srchDatasetUseRequestVo.getDatasetId());
		srchDatasetUseRequestVo.setLoginUserId(loginUserId);
		
		datasetRequestUsageService.modifyDatasetUsage(srchDatasetUseRequestVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터셋 이용신청 수정(부분수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param applicationId
	 * @param srchDatasetUseRequestVo
	 * @return
	 * @throws Exception
	 * @throws NotFoundException 
	 */
	@PatchMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}")
	public ResponseEntity<Object> patchDatasetUsage(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @PathVariable long requestUsageId, @RequestBody SrchDatasetUseRequestVo srchDatasetUseRequestVo) throws Exception {
		StringUtil.compareUniqueId(requestUsageId , srchDatasetUseRequestVo.getId());
		StringUtil.compareUniqueId(datasetId      , srchDatasetUseRequestVo.getDatasetId());
		srchDatasetUseRequestVo.setLoginUserId(loginUserId);
		
		datasetRequestUsageService.patchDatasetUsage(srchDatasetUseRequestVo);
		return ResponseUtil.makeResponseEntity();
	}

	
	/**
	 * <pre>데이터셋 이용신청 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param applicationId
	 * @param srchDatasetUseRequestVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}")
	public ResponseEntity<Object> deleteDatasetUsage(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @PathVariable long requestUsageId, @RequestBody SrchDatasetUseRequestVo srchDatasetUseRequestVo) throws Exception {
		StringUtil.compareUniqueId(requestUsageId , srchDatasetUseRequestVo.getId());
		StringUtil.compareUniqueId(datasetId      , srchDatasetUseRequestVo.getDatasetId());
		
		datasetRequestUsageService.deleteDatasetUsage(srchDatasetUseRequestVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	//==========================================================================================================

	/**
	 * <pre>데이터셋 이용신청 실시간 수신정보 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 5.
	 * @param srchDatasetUsageReceptionVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/receptions")
	public ResponseEntity<Object> getDatasetUsageReceptionList(@PathVariable String datasetId, @PathVariable long requestUsageId, SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo) throws Exception {
		Map<String, Object> datasetUseRequestReceptionList =  datasetRequestUsageService.getDatasetUsageReceptionList(srchDatasetUseRequestReceptionVo);
		return ResponseUtil.makeResponseEntity(datasetUseRequestReceptionList);
	}
	
	/**
	 * <pre>데이터셋 이용신청 실시간 수신정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 5.
	 * @param srchDatasetUsageReceptionVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/receptions")
	public ResponseEntity<Object> createDatasetUsageReception(@PathVariable String datasetId, @PathVariable long requestUsageId, @RequestBody SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo) throws Exception {
		String resultKey = datasetRequestUsageService.createDatasetUsageReception(srchDatasetUseRequestReceptionVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/" + datasetId + "/requestUsage/" + requestUsageId + "/receptions/" + resultKey);
	}

	
	/**
	 * <pre>데이터셋 이용신청 실시간 수신정보 상세조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 5.
	 * @param srchDatasetUsageReceptionVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/receptions/{recvId}")
	public ResponseEntity<Object> getDatasetUsageReception(@PathVariable String datasetId, @PathVariable long requestUsageId, @PathVariable long recvId) throws Exception {
		DatasetUseRequestReceptionVo datasetUseRequestReceptionVo =  datasetRequestUsageService.getDatasetUsageReception(datasetId, requestUsageId, recvId);
		return ResponseUtil.makeResponseEntity(datasetUseRequestReceptionVo);
	}
	
	/**
	 * <pre>데이터셋 이용신청 실시간 수신정보 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param srchDatasetUsageReceptionVo
	 * @return
	 * @throws Exception
	 */
	@PatchMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/receptions/{recvId}")
	public ResponseEntity<Object> modifyDatasetUsageReception(@PathVariable String datasetId, @PathVariable long requestUsageId, @PathVariable long recvId, @RequestBody SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo) throws Exception {
		datasetRequestUsageService.modifyDatasetUsageReception(srchDatasetUseRequestReceptionVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터셋 이용신청 실시간 수신정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param srchDatasetUsageReceptionVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/receptions/{recvId}")
	public ResponseEntity<Object> deleteDatasetUsageReception(@PathVariable String datasetId, @PathVariable long requestUsageId, @PathVariable long recvId, @RequestBody SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo) throws Exception {
		datasetRequestUsageService.deleteDatasetUsageReception(srchDatasetUseRequestReceptionVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	//==========================================================================================================
	
	/**
	 * <pre>데이터셋 이용신청 유료결제정보 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param requestUsageId
	 * @param srchDatasetUseRequestPaymentVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/payments")
	public ResponseEntity<Object> getDatasetUsagePaymentList(@PathVariable String datasetId, @PathVariable String requestUsageId, SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo) throws Exception {
		Map<String, Object> datasetUsagePaymentList =  datasetRequestUsageService.getDatasetUsagePaymentList(srchDatasetUseRequestPaymentVo);
		return ResponseUtil.makeResponseEntity(datasetUsagePaymentList);
	}
	
	/**
	 * <pre>데이터셋 이용신청 유료결제정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param requestUsageId
	 * @param srchDatasetUseRequestPaymentVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/payments")
	public ResponseEntity<Object> createDatasetUsagePayment(@PathVariable String datasetId, @PathVariable long requestUsageId, @RequestBody SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo) throws Exception {
		String resultKey = datasetRequestUsageService.createDatasetUsagePayment(srchDatasetUseRequestPaymentVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/" + datasetId + "/requestUsage/" + requestUsageId + "/payments/" + resultKey);
	}

	
	/**
	 * <pre>데이터셋 이용신청 유료결제정보 상세조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param requestUsageId
	 * @param payId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/payments/{payId}")
	public ResponseEntity<Object> getDatasetUsagePayment(@PathVariable String datasetId, @PathVariable long requestUsageId, @PathVariable long payId) throws Exception {
		DatasetUseRequestPaymentVo datasetUseRequestPaymentVo =  datasetRequestUsageService.getDatasetUsagePayment(payId, requestUsageId);
		return ResponseUtil.makeResponseEntity(datasetUseRequestPaymentVo);
	}
	
	/**
	 * <pre>데이터셋 이용신청 유료결제정보 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param requestUsageId
	 * @param payId
	 * @param srchDatasetUseRequestPaymentVo
	 * @return
	 * @throws Exception
	 */
	@PatchMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/payments/{payId}")
	public ResponseEntity<Object> modifyDatasetUsagePayment(@PathVariable String datasetId, @PathVariable long requestUsageId, @PathVariable long payId, @RequestBody SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo) throws Exception {
		datasetRequestUsageService.modifyDatasetUsagePayment(srchDatasetUseRequestPaymentVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터셋 이용신청 유료결제정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param requestUsageId
	 * @param payId
	 * @param srchDatasetUseRequestPaymentVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/payments/{payId}")
	public ResponseEntity<Object> deleteDatasetUsagePayment(@PathVariable String datasetId, @PathVariable long requestUsageId, @PathVariable long payId, @RequestBody SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo) throws Exception {
		datasetRequestUsageService.deleteDatasetUsagePayment(srchDatasetUseRequestPaymentVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	//==========================================================================================================
	
	/**
	 * <pre>데이터셋 이용신청 환불 신청 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param requestUsageId
	 * @param srchDatasetUseRequestRefundVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/refunds")
	public ResponseEntity<Object> getDatasetUsageRefundList(@PathVariable String datasetId, @PathVariable String requestUsageId, SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo) throws Exception {
		Map<String, Object> datasetUsageRefundList = datasetRequestUsageService.getDatasetUsageRefundList(srchDatasetUseRequestRefundVo);
		return ResponseUtil.makeResponseEntity(datasetUsageRefundList);
	}
	
	/**
	 * <pre>데이터셋 이용신청 환불 신청 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param requestUsageId
	 * @param srchDatasetUseRequestRefundVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/refunds")
	public ResponseEntity<Object> createDatasetUsageRefund(@PathVariable String datasetId, @PathVariable long requestUsageId, @RequestBody SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo) throws Exception {
		String resultKey = datasetRequestUsageService.createDatasetUsageRefund(srchDatasetUseRequestRefundVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/" + datasetId +"/requestUsage/" + requestUsageId +"/refunds/" + resultKey);
	}
	
	/**
	 * <pre>데이터셋 이용신청 환불 신청 상세조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param requestUsageId
	 * @param refundId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/refunds/{refundId}")
	public ResponseEntity<Object> getDatasetUsageRefund(@PathVariable String datasetId, @PathVariable String requestUsageId, @PathVariable String refundId) throws Exception {
		DatasetUseRequestRefundVo datasetUseRequestRefundVo =  datasetRequestUsageService.getDatasetUsageRefund(datasetId, requestUsageId, refundId);
		return ResponseUtil.makeResponseEntity(datasetUseRequestRefundVo);
	}
	
	/**
	 * <pre>데이터셋 이용신청 환불 신청 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param requestUsageId
	 * @param refundId
	 * @param srchDatasetUseRequestRefundVo
	 * @return
	 * @throws Exception
	 */
	@PatchMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/refunds/{refundId}")
	public ResponseEntity<Object> modifyDatasetUsageRefund(@PathVariable String datasetId, @PathVariable String requestUsageId, @PathVariable String refundId, @RequestBody SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo) throws Exception {
		datasetRequestUsageService.modifyDatasetUsageRefund(srchDatasetUseRequestRefundVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터셋 이용신청 환불 신청 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param requestUsageId
	 * @param refundId
	 * @param srchDatasetUseRequestRefundVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/refunds/{refundId}")
	public ResponseEntity<Object> deleteDatasetUsageRefund(@PathVariable String datasetId, @PathVariable String requestUsageId, @PathVariable String refundId, @RequestBody SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo) throws Exception {
		datasetRequestUsageService.deleteDatasetUsageRefund(srchDatasetUseRequestRefundVo);
		return ResponseUtil.makeResponseEntity(null, null, HttpStatus.NO_CONTENT);
	}
	//==========================================================================================================
	
//	/**
//	 * <pre>데이터셋 이용신청현황 내역 목록 조회</pre>
//	 * @Author      : hk-lee
//	 * @Date        : 2020. 11. 6.
//	 * @param datasetId
//	 * @param requestUsageId
//	 * @param srchDatasetUseRequestLogVo
//	 * @return
//	 * @throws Exception
//	 */
//	@GetMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/logs")
//	public ResponseEntity<Object> getDatasetUsageLogList(@PathVariable String datasetId, @PathVariable String requestUsageId, SrchDatasetUseRequestLogVo srchDatasetUseRequestLogVo) throws Exception {
//		List<DatasetUseRequestLogVo> datasetUsageLogList =  datasetRequestUsageService.getDatasetUsageLogList(srchDatasetUseRequestLogVo);
//		return ResponseUtil.makeResponseEntity(datasetUsageLogList);
//	}
//	
//	/**
//	 * <pre>데이터셋 이용신청현황 내역 등록</pre>
//	 * @Author      : hk-lee
//	 * @Date        : 2020. 11. 6.
//	 * @param datasetId
//	 * @param requestUsageId
//	 * @param srchDatasetUseRequestLogVo
//	 * @return
//	 * @throws Exception
//	 */
//	@PostMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/logs")
//	public ResponseEntity<Object> createDatasetUsageLog(@PathVariable String datasetId, @PathVariable String requestUsageId, @RequestBody SrchDatasetUseRequestLogVo srchDatasetUseRequestLogVo) throws Exception {
//		String resultKey = datasetRequestUsageService.createDatasetUsageLog(srchDatasetUseRequestLogVo);
//		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/log");
//	}
//	
//	
//	/**
//	 * <pre>데이터셋 이용신청현황 내역 상세조회</pre>
//	 * @Author      : hk-lee
//	 * @Date        : 2020. 11. 6.
//	 * @param datasetId
//	 * @param requestUsageId
//	 * @return
//	 * @throws Exception
//	 */
//	@GetMapping(value="/dataservice/dataset/{datasetId}/requestUsage/{requestUsageId}/log")
//	public ResponseEntity<Object> getDatasetUsageLog(@PathVariable String datasetId, @PathVariable String requestUsageId) throws Exception {
//		DatasetUseRequestLogVo datasetUseRequestLogVo =  datasetRequestUsageService.getDatasetUsageLog(datasetId, requestUsageId);
//		return ResponseUtil.makeResponseEntity(datasetUseRequestLogVo);
//	}
	
	//==========================================================================================================
	
	/**
	 * <pre>데이터셋 가격정책 연결정보 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param srchDatasetPricePoliciesVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/pricePolicies")
	public ResponseEntity<Object> getDatasetPricePoliciesList(@PathVariable String datasetId, SrchDatasetPricePoliciesVo srchDatasetPricePoliciesVo) throws Exception {
		List<DatasetPricePoliciesVo> datasetPricePoliciesList =  datasetPricePoliciesService.getDatasetPricePoliciesList(srchDatasetPricePoliciesVo);
		return ResponseUtil.makeResponseEntity(datasetPricePoliciesList);
	}
	
	/**
	 * <pre>데이터셋 가격정책 연결정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param srchDatasetPricePoliciesVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/pricePolicies")
	public ResponseEntity<Object> createDatasetPricePolicies(@PathVariable String datasetId, @RequestBody SrchDatasetPricePoliciesVo srchDatasetPricePoliciesVo) throws Exception {
		String resultKey = datasetPricePoliciesService.createDatasetPricePolicies(srchDatasetPricePoliciesVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/" + datasetId + "/pricePolicies/" + resultKey);
	}

	/**
	 * <pre>데이터셋 가격정책 연결정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param policyId
	 * @param srchDatasetPricePoliciesVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/pricePolicies/{policyId}")
	public ResponseEntity<Object> deleteDatasetPricePolicies(@PathVariable String datasetId, @PathVariable String policyId, @RequestBody SrchDatasetPricePoliciesVo srchDatasetPricePoliciesVo) throws Exception {
		datasetPricePoliciesService.deleteDatasetPricePolicies(srchDatasetPricePoliciesVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	//==========================================================================================================
	/**
	 * <pre>데이터셋 가격정책 금액정보 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param policyId
	 * @param srchDatasetPricePoliciesPeriodPriceVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/pricePolicies/{priceId}/price")
	public ResponseEntity<Object> getDatasetPricePoliciesPeriodPriceList(@PathVariable String datasetId, @PathVariable long priceId, SrchDatasetPricePoliciesPeriodPriceVo srchDatasetPricePoliciesPeriodPriceVo) throws Exception {
		List<DatasetPricePoliciesPeriodPriceVo> datasetPricePoliciesPeriodPriceList =  datasetPricePoliciesService.getDatasetPricePoliciesPeriodPriceList(srchDatasetPricePoliciesPeriodPriceVo);
		return ResponseUtil.makeResponseEntity(datasetPricePoliciesPeriodPriceList);
	}
	
	/**
	 * <pre>데이터셋 가격정책 금액정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param policyId
	 * @param srchDatasetPricePoliciesPeriodPriceVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/pricePolicies/{priceId}/price")
	public ResponseEntity<Object> createDatasetPricePoliciesPeriodPrice(@PathVariable String datasetId, @PathVariable long priceId, @RequestBody SrchDatasetPricePoliciesPeriodPriceVo srchDatasetPricePoliciesPeriodPriceVo) throws Exception {
		String resultKey = datasetPricePoliciesService.createDatasetPricePoliciesPeriodPrice(srchDatasetPricePoliciesPeriodPriceVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/" + datasetId + "/pricePolicies/" + priceId + "/price/" + resultKey);
	}
	
	/**
	 * <pre>데이터셋 가격정책 금액정보 상세조회</pre> 
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param policyId
	 * @param priceId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/pricePolicies/{priceId}/price/{periodId}")
	public ResponseEntity<Object> getDatasetPricePoliciesPeriodPrice(@PathVariable String datasetId, @PathVariable long priceId, @PathVariable long periodId) throws Exception {
		DatasetPricePoliciesPeriodPriceVo datasetPricePoliciesPeriodPriceVo =  datasetPricePoliciesService.getDatasetPricePoliciesPeriodPrice(priceId, periodId);
		return ResponseUtil.makeResponseEntity(datasetPricePoliciesPeriodPriceVo);
	}
	
	/**
	 * <pre>데이터셋 가격정책 금액정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param datasetId
	 * @param policyId
	 * @param priceId
	 * @param srchDatasetPricePoliciesPeriodPriceVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/pricePolicies/{priceId}/price/{periodId}")
	public ResponseEntity<Object> deleteDatasetPricePoliciesPeriodPrice(@PathVariable String datasetId, @PathVariable long priceId, @PathVariable long periodId, @RequestBody SrchDatasetPricePoliciesPeriodPriceVo srchDatasetPricePoliciesPeriodPriceVo) throws Exception {
		datasetPricePoliciesService.deleteDatasetPricePoliciesPeriodPrice(srchDatasetPricePoliciesPeriodPriceVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	//==========================================================================================================
	
	/**
	 * <pre>데이터셋 활용내역 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 8.
	 * @param datasetId
	 * @param srchDatasetUseHistoryVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/usageHistory")
	public ResponseEntity<Object> getDatasetUsageHistoryList(@PathVariable String datasetId, SrchDatasetUseHistoryVo srchDatasetUseHistoryVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetUseHistoryVo.getDatasetId());
		
		Map<String, Object> resMap = datasetUsageHistoryService.getDatasetUsageHistoryList(srchDatasetUseHistoryVo); 
		return ResponseUtil.makeResponseEntity(resMap);
	}

	/**
	 * <pre>데이터 분석가승인 요청목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/analysisApprovalRequests")
	public ResponseEntity<Object> getDataAnalystRequestList(SrchDataAnalystRequestVo srchDataAnalystRequestVo) throws Exception {
		Map<String, Object> resMap = datasetAnalysisApprovalRequestsService.getDataAnalystRequestList(srchDataAnalystRequestVo);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * <pre>데이터 분석가승인요청 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param requestId
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/analysisApprovalRequests/{requestId}")
	public ResponseEntity<Object> getDataAnalystRequest(@PathVariable long requestId, SrchDataAnalystRequestVo srchDataAnalystRequestVo) throws Exception {
		DataAnalystRequestVo dataAnalystRequestVo = datasetAnalysisApprovalRequestsService.getDataAnalystRequest(requestId);
		return ResponseUtil.makeResponseEntity(dataAnalystRequestVo);
	}
	
	/**
	 * <pre>데이터 분석가승인 요청</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param loginUserId
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/analysisApprovalRequests")
	public ResponseEntity<Object> createDataAnalystRequest(@RequestHeader("UserId") String loginUserId, @RequestBody SrchDataAnalystRequestVo srchDataAnalystRequestVo) throws Exception {
		srchDataAnalystRequestVo.setLoginUserId(loginUserId);
		
		String resultKey = datasetAnalysisApprovalRequestsService.createDataAnalystRequest(srchDataAnalystRequestVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/analysisApprovalRequests/" + resultKey);
	}
	
	/**
	 * <pre>데이터 분석가승인 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 * @throws Exception
	 * @throws NotFoundException 
	 */
	@PutMapping(value="/dataservice/analysisApprovalRequests/{requestId}")
	public ResponseEntity<Object> modifyDataAnalystRequest(@PathVariable String requestId, @RequestHeader("UserId") String loginUserId, @RequestBody SrchDataAnalystRequestVo srchDataAnalystRequestVo) throws Exception {
		srchDataAnalystRequestVo.setLoginUserId(loginUserId);
		srchDataAnalystRequestVo.setId(Long.parseLong(requestId));
		
		datasetAnalysisApprovalRequestsService.modifyDataAnalystRequest(srchDataAnalystRequestVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터 분석가승인 삭제</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 10. 6.
	 * @param requestId
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/analysisApprovalRequests/{requestId}")
	public ResponseEntity<Object> removeDataAnalystRequest(@PathVariable long requestId) throws Exception {
		datasetAnalysisApprovalRequestsService.removeDataAnalystRequest(requestId);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터 분석가 승인 모델 목록 조회 (요청자 기준) - 인터페이스 전용</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 10. 6.
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/analysisApprovalRequests/users/{userId}/dataModel")
	public ResponseEntity<Object> getInfDataAnalystRequestModelList(@PathVariable String userId) throws Exception {
		List<Map<String, Object>> dataAnalystRequestModelList =  datasetAnalysisApprovalRequestsService.getInfDataAnalystRequestModelList(userId);
		return ResponseUtil.makeResponseEntity(dataAnalystRequestModelList);
	}
	
	/**
	 * <pre>데이터 분석 모델 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param requestId
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/analysisApprovalRequests/{requestId}/dataModel")
	public ResponseEntity<Object> getDataAnalystRequestModelList(@PathVariable String requestId, SrchDataAnalystRequestModelVo srchDataAnalystRequestModelVo) throws Exception {
//		StringUtil.compareUniqueId(requestId, srchDataAnalystRequestModelVo.getId());
		srchDataAnalystRequestModelVo.setAnalystId(Long.parseLong(requestId));
		
		List<DataAnalystRequestModelVo> dataAnalystRequestModelList =  datasetAnalysisApprovalRequestsService.getDataAnalystRequestModelList(srchDataAnalystRequestModelVo);
		return ResponseUtil.makeResponseEntity(dataAnalystRequestModelList);
	}
	
	/**
	 * <pre>데이터 분석 모델 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param loginUserId
	 * @param srchDatasetUseRequestVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/analysisApprovalRequests/{requestId}/dataModel")
	public ResponseEntity<Object> createDataAnalystRequestModel(@RequestHeader("UserId") String loginUserId, @PathVariable String requestId, @RequestBody SrchDataAnalystRequestModelVo srchDataAnalystRequestModelVo) throws Exception {
		StringUtil.compareUniqueId(requestId      , srchDataAnalystRequestModelVo.getAnalystId());
		
		String resultKey = datasetAnalysisApprovalRequestsService.createDataAnalystRequestModel(srchDataAnalystRequestModelVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/analysisApprovalRequests/"+requestId+"/dataModel/"+resultKey);
	}
	/**
	 * <pre>데이터 분석 모델 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param requestId
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/analysisApprovalRequests/{requestId}/dataModel/{modelId}")
	public ResponseEntity<Object> getDataAnalystRequestModel(@PathVariable String requestId, @PathVariable String modelId, SrchDataAnalystRequestModelVo srchDataAnalystRequestModelVo) throws Exception {
		DataAnalystRequestModelVo dataAnalystRequestModelVo =  datasetAnalysisApprovalRequestsService.getDataAnalystRequestModel(requestId, modelId);
		return ResponseUtil.makeResponseEntity(dataAnalystRequestModelVo);
	}
	/**
	 * <pre>데이터 분석 모델 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param applicationId
	 * @param srchDatasetUseRequestVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/analysisApprovalRequests/{requestId}/dataModel/{modelId}")
	public ResponseEntity<Object> deleteDataAnalystRequestModel(@RequestHeader("UserId") String loginUserId, @PathVariable String requestId, @PathVariable String modelId, @RequestBody SrchDataAnalystRequestModelVo srchDataAnalystRequestModelVo) throws Exception {
		StringUtil.compareUniqueId(requestId      , srchDataAnalystRequestModelVo.getAnalystId());
		StringUtil.compareUniqueId(modelId        , srchDataAnalystRequestModelVo.getId());
		
		datasetAnalysisApprovalRequestsService.deleteDataAnalystRequestModel(srchDataAnalystRequestModelVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}

	/**
	 * <pre>데이터셋 만족도평가 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param datasetId
	 * @param ratingId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/rating")
	public ResponseEntity<Object> getDatasetSatisfactionList(@PathVariable String datasetId, SrchDatasetSatisfactionRatingVo srchDatasetSatisfactionRatingVo) throws Exception {
		StringUtil.compareUniqueId(datasetId      , srchDatasetSatisfactionRatingVo.getDatasetId());
		List<DatasetSatisfactionRatingVo> datasetSatisfactionRatingList =  datasetRatingService.getDatasetSatisfactionList(srchDatasetSatisfactionRatingVo);
		return ResponseUtil.makeResponseEntity(datasetSatisfactionRatingList);
	}
	
	/**
	 * <pre>데이터셋 만족도평가 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param loginUserId
	 * @param datasetId
	 * @param srchDatasetSatisfactionRatingVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/rating")
	public ResponseEntity<Object> createDatasetSatisfaction(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @RequestBody SrchDatasetSatisfactionRatingVo srchDatasetSatisfactionRatingVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetSatisfactionRatingVo.getDatasetId());
		srchDatasetSatisfactionRatingVo.setLoginUserId(loginUserId);
		
		String resultKey = datasetRatingService.createDatasetSatisfaction(srchDatasetSatisfactionRatingVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/"+datasetId+"/rating/"+resultKey);
	}
	
	
	/**
	 * <pre>데이터셋 만족도평가 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param datasetId
	 * @param ratingId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/rating/{ratingId}")
	public ResponseEntity<Object> getDatasetSatisfaction(@PathVariable String datasetId, @PathVariable String ratingId) throws Exception {
		DatasetSatisfactionRatingVo datasetSatisfactionRatingVo =  datasetRatingService.getDatasetSatisfaction(datasetId, ratingId);
		return ResponseUtil.makeResponseEntity(datasetSatisfactionRatingVo);
	}
	
	/**
	 * <pre>데이터셋 만족도평가 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param loginUserId
	 * @param datasetId
	 * @param ratingId
	 * @param srchDatasetSatisfactionRatingVo
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value="/dataservice/dataset/{datasetId}/rating/{ratingId}")
	public ResponseEntity<Object> modifyDatasetSatisfaction(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @PathVariable String ratingId, @RequestBody SrchDatasetSatisfactionRatingVo srchDatasetSatisfactionRatingVo) throws Exception {
		StringUtil.compareUniqueId(ratingId       , srchDatasetSatisfactionRatingVo.getId());
		StringUtil.compareUniqueId(datasetId      , srchDatasetSatisfactionRatingVo.getDatasetId());
		srchDatasetSatisfactionRatingVo.setLoginUserId(loginUserId);
		
		datasetRatingService.modifyDatasetSatisfaction(srchDatasetSatisfactionRatingVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터셋 만족도평가 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param loginUserId
	 * @param datasetId
	 * @param ratingId
	 * @param srchDatasetSatisfactionRatingVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/rating/{ratingId}")
	public ResponseEntity<Object> deleteDatasetSatisfaction(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @PathVariable String ratingId, @RequestBody SrchDatasetSatisfactionRatingVo srchDatasetSatisfactionRatingVo) throws Exception {
		StringUtil.compareUniqueId(ratingId       , srchDatasetSatisfactionRatingVo.getId());
		StringUtil.compareUniqueId(datasetId      , srchDatasetSatisfactionRatingVo.getDatasetId());
		
		datasetRatingService.deleteDatasetSatisfaction(srchDatasetSatisfactionRatingVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * <pre>데이터셋 Origin 연결 정보 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 30.
	 * @param srchDatasetOriginVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/datasetOrigin")
	public ResponseEntity<Object> getDatasetOriginList(@PathVariable SrchDatasetOriginVo srchDatasetOriginVo) throws Exception {
		List<DatasetOriginVo> datasetOriginList =  datasetOriginService.getDatasetOriginList(srchDatasetOriginVo);
		return ResponseUtil.makeResponseEntity(datasetOriginList);
	}
	
	/**
	 * <pre>데이터셋 Origin 연결 정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 30.
	 * @param loginUserId
	 * @param datasetId
	 * @param srchDatasetOriginVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/datasetOrigin")
	public ResponseEntity<Object> createDatasetOrigin(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @RequestBody SrchDatasetOriginVo srchDatasetOriginVo) throws Exception {
		StringUtil.compareUniqueId(datasetId, srchDatasetOriginVo.getDatasetId());
		srchDatasetOriginVo.setLoginUserId(loginUserId);
		
		String resultKey = datasetOriginService.createDatasetOrigin(srchDatasetOriginVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/dataset/"+datasetId+"/datasetOrigin/"+resultKey);
	}
	
	
	/**
	 * <pre>데이터셋 Origin 연결 정보 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 30.
	 * @param datasetId
	 * @param datasetOriginId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/datasetOrigin/{datasetOriginId}")
	public ResponseEntity<Object> getDatasetOrigin(@PathVariable String datasetId, @PathVariable String datasetOriginId) throws Exception {
		DatasetOriginVo datasetOriginVo =  datasetOriginService.getDatasetOrigin(datasetId, datasetOriginId);
		return ResponseUtil.makeResponseEntity(datasetOriginVo);
	}
	
	/**
	 * <pre>데이터셋 Origin 연결 정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 30.
	 * @param loginUserId
	 * @param datasetId
	 * @param datasetOriginId
	 * @param srchDatasetOriginVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/datasetOrigin/{datasetOriginId}")
	public ResponseEntity<Object> deleteDatasetOrigin(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @PathVariable String datasetOriginId, @RequestBody SrchDatasetOriginVo srchDatasetOriginVo) throws Exception {
		StringUtil.compareUniqueId(datasetOriginId , srchDatasetOriginVo.getId());
		StringUtil.compareUniqueId(datasetId       , srchDatasetOriginVo.getDatasetId());
		
		datasetOriginService.deleteDatasetOrigin(srchDatasetOriginVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}

	/**
	 * <pre>데이터셋 - 디바이스 연결 API 목록 조회 (타 시스템 연계 인터페이스)</pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 16.
	 * @param datasetId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/devices")
	public ResponseEntity<Object> getDatasetDeviceInfo(@PathVariable String datasetId) throws Exception {
		List<DatasetInfDeviceVo> devices =  datasetInfDeviceService.getDatasetDeviceInfo(datasetId);
		return ResponseUtil.makeResponseEntity(devices);
	}
	
	/**
	 * <pre>데이터셋 - 디바이스 연결 API 등록 (타 시스템 연계 인터페이스, Callback 호출)</pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 16.
	 * @param loginUserId
	 * @param datasetId
	 * @param contents
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/dataset/{datasetId}/devices")
	public ResponseEntity<Object> createDatasetDevice(@PathVariable String datasetId, @RequestBody(required = false) List<DatasetInfDeviceVo> devices) throws Exception {
		datasetInfDeviceService.createDatasetDevice(datasetId, devices);
		
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED);
	}
	
	/**
	 * <pre>데이터셋 - 디바이스 연결 API 삭제 (타 시스템 연계 인터페이스, Callback 호출)</pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 19.
	 * @param datasetId
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/dataset/{datasetId}/devices")
	public ResponseEntity<Object> removeDatasetDevice(@PathVariable String datasetId) throws Exception {
		datasetInfDeviceService.removeDatasetDevice(datasetId);
		
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}

}
