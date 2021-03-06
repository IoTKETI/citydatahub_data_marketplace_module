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
package kr.co.n2m.smartcity.datapublish.feature.datasetUi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.n2m.smartcity.datapublish.common.component.CommonComponent;
import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.common.vo.PageVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.DatasetService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.analysisApprovalRequests.DatasetAnalysisApprovalRequestsService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.attachFiles.DatasetAttachFilesService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.columns.DatasetColumnsService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.datasetAdaptor.DatasetAdaptorService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.datasetOrigin.DatasetOriginService;
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
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.attachFiles.DatasetFileVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.attachFiles.SrchDatasetFileVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.DatasetOutputVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.SrchDatasetOutputVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetOrigin.DatasetOriginVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetOrigin.SrchDatasetOriginVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.inquiry.DatasetInquiryVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.inquiry.SrchDatasetInquiryVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.instance.DatasetInstanceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.instance.SrchDatasetInstanceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.rating.DatasetSatisfactionRatingVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.rating.SrchDatasetSatisfactionRatingVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestPaymentVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestRefundVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestPaymentVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestReceptionVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestRefundVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.DatasetSearchInfoVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.SrchDatasetSearchInfoVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.wishlist.DatasetInterestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.wishlist.SrchDatasetInterestVo;
import kr.co.n2m.smartcity.datapublish.feature.datasetUi.mapper.IntegratedMapper;
import kr.co.n2m.smartcity.datapublish.feature.datasetUi.vo.SrchIntegratedVo;

@Service
public class IntegratedService extends CommonComponent{
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
	
	@Autowired IntegratedMapper integratedMapper;
	@Autowired DatasetMapper datasetMapper;

	/**
	 * <pre>???????????? ????????? ??????(??????, ??????, API)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 21.
	 * @param srchDatasetVo
	 * @return
	 */
	public Map<String, Object> getDatasetCount(SrchDatasetVo srchDatasetVo) {
		Map<String, Object> resMap = new HashMap<>();
		
		Map<String, Object> datasetMap = datasetService.getDatasetList(srchDatasetVo);
		PageVo datasetPageVo           = (PageVo) datasetMap.get("page");
		int datasetTotalCount          = datasetPageVo.getTotalListSize();
		
		srchDatasetVo.setDataOfferType("api");
		Map<String, Object> datasetTypeApiMap = datasetService.getDatasetList(srchDatasetVo);
		PageVo datasetTypeApiPageVo           = (PageVo) datasetTypeApiMap.get("page");
		int datasetTypeApiCount               = datasetTypeApiPageVo.getTotalListSize();
		
		srchDatasetVo.setDataOfferType("file");
		Map<String, Object> datasetTypeFileMap = datasetService.getDatasetList(srchDatasetVo);
		PageVo datasetTypeFilePageVo           = (PageVo) datasetTypeFileMap.get("page");
		int datasetTypeFileCount               = datasetTypeFilePageVo.getTotalListSize();
		
		resMap.put("datasetTotalCount"    , datasetTotalCount);
		resMap.put("datasetTypeApiCount"  , datasetTypeApiCount);
		resMap.put("datasetTypeFileCount" , datasetTypeFileCount);
		return resMap;
	}
	
	
	/**
	 * <pre>????????? ???????????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 17.
	 * @param srchDatasetVo
	 * @return
	 */
	public Map<String, Object> getDatasetList(SrchDatasetVo srchDatasetVo) {
		Map<String, Object> resMap = new HashMap<>();
		Map<String, Object> datasetListMap = datasetService.getDatasetList(srchDatasetVo);
		List<DatasetVo> datasetList = (List<DatasetVo>) datasetListMap.get("list");
		
		
		String loginUserId = srchDatasetVo.getLoginUserId();
		SrchDatasetInterestVo      srchDatasetInterestVo       = new SrchDatasetInterestVo();
		SrchDatasetUseRequestVo    srchDatasetUseRequestVo     = new SrchDatasetUseRequestVo();
		SrchDatasetFileVo          srchDatasetFileVo           = new SrchDatasetFileVo();
		
		List<Map<String, Object>> newDatasetList = new ArrayList<>();
		resMap.put("list", newDatasetList);
		resMap.put("page", datasetListMap.get("page"));
		
		
		for(DatasetVo datasetVo : datasetList) {
			/**
			 * ???????????? ????????????
			 */
			String datasetJson = toJson(datasetVo);
			Map<String, Object> datasetMap = toMap(datasetJson);
			
			String datasetId = datasetVo.getId();
			
			/**
			 * ???????????? ????????????
			 */
			srchDatasetInterestVo.setDatasetId(datasetId);
			srchDatasetInterestVo.setLoginUserId(loginUserId);
			srchDatasetInterestVo.setPaging(true);
			Map<String, Object> datasetUsageListMap = datasetWishlistService.getDatasetWishlistList(srchDatasetInterestVo);
			List<DatasetInterestVo> datasetUsageList = (List<DatasetInterestVo>) datasetUsageListMap.get("list");
			
			srchDatasetInterestVo.setLoginUserId(null);
			srchDatasetInterestVo.setPaging(false);
			Map<String, Object> datasetUsageTotalListMap = datasetWishlistService.getDatasetWishlistList(srchDatasetInterestVo);
			List<DatasetInterestVo> datasetUsageTotalList = (List<DatasetInterestVo>) datasetUsageTotalListMap.get("list");
			boolean hasDatasetWishlist = datasetUsageList != null && datasetUsageList.size() > 0;
			if(hasDatasetWishlist) {
				datasetMap.put("datasetWishlistId", datasetUsageList.get(0).getId());
			}

			datasetMap.put("datasetWishlistList"      , datasetUsageList);
			datasetMap.put("hasDatasetWishlist"       , hasDatasetWishlist);
			datasetMap.put("datasetWishlistTotalCount", datasetUsageTotalList.size());
			/**
			 * ???????????? ????????????
			 */
			srchDatasetUseRequestVo.setDatasetId(datasetId);
			srchDatasetUseRequestVo.setLoginUserId(loginUserId);
			Map<String, Object> datasetUseRequestListMap = datasetRequestUsageService.getDatasetUsageList(srchDatasetUseRequestVo);
			List<DatasetUseRequestVo> datasetUseRequestList = (List<DatasetUseRequestVo>) datasetUseRequestListMap.get("list");
			boolean hasDatasetUseRequest = datasetUseRequestList != null && datasetUseRequestList.size() > 0;
			if(hasDatasetUseRequest) {
				long datasetuseRequestId = datasetUseRequestList.get(0).getId();
				datasetMap.put("datasetUseRequestId", datasetuseRequestId);
				/** ???????????? ???????????? ?????????????????? **/
				SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo = new SrchDatasetUseRequestPaymentVo();
				srchDatasetUseRequestPaymentVo.setRequestId(datasetuseRequestId);
				Map<String, Object> datasetUsagePaymentListMap = datasetRequestUsageService.getDatasetUsagePaymentList(srchDatasetUseRequestPaymentVo);
				List<DatasetUseRequestPaymentVo> datasetUsagePaymentList = (List<DatasetUseRequestPaymentVo>) datasetUsagePaymentListMap.get("list");
				if(datasetUsagePaymentList != null && datasetUsagePaymentList.size() > 0) {
					DatasetUseRequestPaymentVo datasetUseRequestPaymentVo = datasetUsagePaymentList.get(0);
					datasetMap.put("datasetUseRequestPayment", datasetUseRequestPaymentVo);
				}
				/** ???????????? ???????????? ???????????? **/
				SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo = new SrchDatasetUseRequestReceptionVo();
				srchDatasetUseRequestReceptionVo.setRequestId(datasetuseRequestId);
				Map<String, Object> datasetUsageReceptionListMap = datasetRequestUsageService.getDatasetUsageReceptionList(srchDatasetUseRequestReceptionVo);
				List<DatasetUseRequestPaymentVo> datasetUsageReceptionList = (List<DatasetUseRequestPaymentVo>) datasetUsageReceptionListMap.get("list");
				if(datasetUsageReceptionList != null && datasetUsageReceptionList.size() > 0) {
					datasetMap.put("datasetUseRequestReception", datasetUsageReceptionList.get(0));
				}
			}
			datasetMap.put("datasetUseRequestList", datasetUseRequestList);
			datasetMap.put("hasDatasetUseRequest" , hasDatasetUseRequest);
			
			/**
			 * ???????????? ????????????
			 */
			srchDatasetFileVo.setDatasetId(datasetId);
			List<DatasetFileVo> datasetFileList = datasetAttachFilesService.getDatasetFileList(srchDatasetFileVo);
			DatasetFileVo datasetImageFileVo          = new DatasetFileVo();
			for(DatasetFileVo datasetFileVo : datasetFileList) {
				String type = datasetFileVo.getType();
				if("image".equals(type)) {
					datasetImageFileVo = datasetFileVo;
					break;
				}
			}
			datasetMap.put("datasetImageFileId"   , datasetImageFileVo.getId());
			
			
			newDatasetList.add(datasetMap);
		}
		
		return resMap;
	}
	
	/**
	 * <pre>????????? ???????????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 17.
	 * @param datasetId
	 * @param loginUserId
	 * @param nohit
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDataset(String datasetId, String loginUserId, boolean nohit) throws Exception {
		DatasetVo datasetVo = datasetService.getDataset(datasetId, nohit);
		
		SrchDatasetOriginVo             srchDatasetOriginVo             = new SrchDatasetOriginVo();
		SrchDatasetInterestVo           srchDatasetInterestVo           = new SrchDatasetInterestVo();
		SrchDatasetFileVo               srchDatasetFileVo               = new SrchDatasetFileVo();
		SrchDatasetSearchInfoVo         srchDatasetSearchInfoVo         = new SrchDatasetSearchInfoVo();
		SrchDatasetOutputVo             srchDatasetOutputVo             = new SrchDatasetOutputVo();
		SrchDatasetInstanceVo           srchDatasetInstanceVo           = new SrchDatasetInstanceVo();
		SrchDatasetUseRequestVo         srchDatasetUseRequestVo         = new SrchDatasetUseRequestVo();
		SrchDatasetInquiryVo            srchDatasetInquiryVo            = new SrchDatasetInquiryVo();
		SrchDatasetSatisfactionRatingVo srchDatasetSatisfactionRatingVo = new SrchDatasetSatisfactionRatingVo();
		
		/**
		 * ???????????? ????????????
		 */
		String datasetJson = toJson(datasetVo);
		Map<String, Object> datasetMap = toMap(datasetJson);
		
		/**
		 * ???????????? ORIGIN
		 */
		srchDatasetOriginVo.setDatasetId(datasetId);
		List<DatasetOriginVo> datasetOriginList = datasetOriginService.getDatasetOriginList(srchDatasetOriginVo);
		datasetMap.put("datasetOriginList", datasetOriginList);
		
		/**
		 * ???????????? ????????????
		 */
		srchDatasetInstanceVo.setDatasetId(datasetId);
		List<DatasetInstanceVo> datasetInstanceList = datasetInstanceService.getDatasetInstanceList(srchDatasetInstanceVo);
		datasetMap.put("datasetInstanceList", datasetInstanceList);
		/**
		 * ???????????? ????????????
		 */
		srchDatasetOutputVo.setDatasetId(datasetId);
		List<DatasetOutputVo> datasetOutputList = datasetColumnsService.getDatasetOutputList(srchDatasetOutputVo);
		datasetMap.put("datasetOutputList", datasetOutputList);
		/**
		 * ???????????? ????????????
		 */
		srchDatasetSearchInfoVo.setDatasetId(datasetId);
		List<DatasetSearchInfoVo> datasetSearchInfoList = datasetSearchConditionService.getDatasetSearchInfoList(srchDatasetSearchInfoVo);
		datasetMap.put("datasetSearchInfoList", datasetSearchInfoList);
		/**
		 * ???????????? ????????????
		 */
		srchDatasetInquiryVo.setDatasetId(datasetId);
		List<DatasetInquiryVo> datasetInquiryList = datasetInquiryService.getDatasetInquiryList(srchDatasetInquiryVo);
		datasetMap.put("datasetInquiryList", datasetInquiryList);
		
		/**
		 * ???????????? ????????????
		 */
		srchDatasetFileVo.setDatasetId(datasetId);
		List<DatasetFileVo> datasetFileList = datasetAttachFilesService.getDatasetFileList(srchDatasetFileVo);
		DatasetFileVo datasetImageFileVo          = new DatasetFileVo();
		List<DatasetFileVo> datasetUploadFileList = new ArrayList<>();
		for(DatasetFileVo datasetFileVo : datasetFileList) {
			String type = datasetFileVo.getType();
			if("file".equals(type)) {
				datasetUploadFileList.add(datasetFileVo);
			}else if("image".equals(type)) {
				datasetImageFileVo = datasetFileVo;
			}
		}
		datasetMap.put("datasetFileList"      , datasetUploadFileList);
		datasetMap.put("datasetImageFileId"   , datasetImageFileVo.getId());
		
		/**
		 * ???????????? ????????????
		 */
		srchDatasetInterestVo.setDatasetId(datasetId);
		srchDatasetInterestVo.setLoginUserId(loginUserId);
		srchDatasetInterestVo.setPaging(true);
		Map<String, Object> datasetUsageListMap = datasetWishlistService.getDatasetWishlistList(srchDatasetInterestVo);
		List<DatasetInterestVo> datasetUsageList = (List<DatasetInterestVo>) datasetUsageListMap.get("list");
		
		srchDatasetInterestVo.setLoginUserId(null);
		srchDatasetInterestVo.setPaging(false);
		Map<String, Object> datasetUsageTotalListMap = datasetWishlistService.getDatasetWishlistList(srchDatasetInterestVo);
		List<DatasetInterestVo> datasetUsageTotalList = (List<DatasetInterestVo>) datasetUsageTotalListMap.get("list");
		boolean hasDatasetWishlist = datasetUsageList != null && datasetUsageList.size() > 0;
		if(hasDatasetWishlist) {
			datasetMap.put("datasetWishlistId", datasetUsageList.get(0).getId());
		}
		
		datasetMap.put("datasetWishlistList"      , datasetUsageList);
		datasetMap.put("hasDatasetWishlist"       , hasDatasetWishlist);
		datasetMap.put("datasetWishlistTotalCount", datasetUsageTotalList.size());
		/**
		 * ???????????? ????????????
		 */
		srchDatasetUseRequestVo.setDatasetId(datasetId);
		srchDatasetUseRequestVo.setLoginUserId(loginUserId);
		Map<String, Object> datasetUseRequestListMap = datasetRequestUsageService.getDatasetUsageList(srchDatasetUseRequestVo);
		List<DatasetUseRequestVo> datasetUseRequestList = (List<DatasetUseRequestVo>) datasetUseRequestListMap.get("list");
		boolean hasDatasetUseRequest = datasetUseRequestList != null && datasetUseRequestList.size() > 0;
		if(hasDatasetUseRequest) {
			long datasetuseRequestId = datasetUseRequestList.get(0).getId();
			datasetMap.put("datasetUseRequestId", datasetuseRequestId);
			/** ???????????? ???????????? ?????????????????? **/
			SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo = new SrchDatasetUseRequestPaymentVo();
			srchDatasetUseRequestPaymentVo.setRequestId(datasetuseRequestId);
			Map<String, Object> datasetUsagePaymentListMap = datasetRequestUsageService.getDatasetUsagePaymentList(srchDatasetUseRequestPaymentVo);
			List<DatasetUseRequestPaymentVo> datasetUsagePaymentList = (List<DatasetUseRequestPaymentVo>) datasetUsagePaymentListMap.get("list");
			if(datasetUsagePaymentList != null && datasetUsagePaymentList.size() > 0) {
				DatasetUseRequestPaymentVo datasetUseRequestPaymentVo = datasetUsagePaymentList.get(0);
				datasetMap.put("datasetUseRequestPayment", datasetUseRequestPaymentVo);
				datasetMap.put("datasetUseRequestPrice"
						, datasetPricePoliciesService.getDatasetPricePoliciesPeriodPrice(datasetUseRequestPaymentVo.getPriceId(), datasetUseRequestPaymentVo.getPeriodId()));
				datasetMap.put("datasetUseRequestPolicies"
						, datasetPricePoliciesService.getDatasetPricePolicies(datasetUseRequestPaymentVo.getPriceId()));
			}
			/** ???????????? ???????????? ???????????? **/
			SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo = new SrchDatasetUseRequestReceptionVo();
			srchDatasetUseRequestReceptionVo.setRequestId(datasetuseRequestId);
			Map<String, Object> datasetUsageReceptionListMap = datasetRequestUsageService.getDatasetUsageReceptionList(srchDatasetUseRequestReceptionVo);
			List<DatasetUseRequestPaymentVo> datasetUsageReceptionList = (List<DatasetUseRequestPaymentVo>) datasetUsageReceptionListMap.get("list");
			if(datasetUsageReceptionList != null && datasetUsageReceptionList.size() > 0) {
				datasetMap.put("datasetUseRequestReception", datasetUsageReceptionList.get(0));
			}
			/** ???????????? ???????????? ???????????? **/
			SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo = new SrchDatasetUseRequestRefundVo();
			srchDatasetUseRequestRefundVo.setRequestId(datasetuseRequestId);
			Map<String, Object> datasetUsageRefundListMap = datasetRequestUsageService.getDatasetUsageRefundList(srchDatasetUseRequestRefundVo);
			List<DatasetUseRequestRefundVo> datasetUsageRefundList = (List<DatasetUseRequestRefundVo>) datasetUsageRefundListMap.get("list");
			if(datasetUsageRefundList != null && datasetUsageRefundList.size() > 0) {
				datasetMap.put("datasetUseRequestRefund", datasetUsageRefundList.get(0));
			}
			
		}
		datasetMap.put("datasetUseRequestList", datasetUseRequestList);
		datasetMap.put("hasDatasetUseRequest" , hasDatasetUseRequest);
		
		
		
		/**
		 * ???????????? ????????? 
		 */
		srchDatasetSatisfactionRatingVo.setDatasetId(datasetId);
		List<DatasetSatisfactionRatingVo> datasetSatisfactionRatingList = datasetRatingService.getDatasetSatisfactionList(srchDatasetSatisfactionRatingVo);
		
		int datasetSatisfactionRatingCount         = datasetSatisfactionRatingList != null ? datasetSatisfactionRatingList.size() : 0;
		double datasetSatisfactionRatingScoreAverage = 0;
		boolean hasDatasetSatisfactionRating = false;
		
		if(datasetSatisfactionRatingCount > 0) {
			long datasetSatisfactionRatingScoreSum = 0;
			for(DatasetSatisfactionRatingVo datasetSatisfactionRatingVo : datasetSatisfactionRatingList) {
				String userId  = datasetSatisfactionRatingVo.getUserId();
				if(StringUtil.equals(userId, loginUserId)) {
					hasDatasetSatisfactionRating = true;
				}
				long score = datasetSatisfactionRatingVo.getScore();
				datasetSatisfactionRatingScoreSum += score;
			}
			datasetSatisfactionRatingScoreAverage = datasetSatisfactionRatingScoreSum / datasetSatisfactionRatingCount;
		}
		datasetMap.put("datasetSatisfactionRatingCount"       , datasetSatisfactionRatingCount);
		datasetMap.put("datasetSatisfactionRatingScoreAverage", datasetSatisfactionRatingScoreAverage);
		datasetMap.put("hasDatasetSatisfactionRating"         , hasDatasetSatisfactionRating);
		
		return datasetMap;
	}
	
	/**
	 * <pre>????????? ???????????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 17.
	 * @param srchDatasetVo
	 * @param request
	 * @return 
	 * @throws Exception
	 */
	@Transactional
	public String createDataset(SrchDatasetVo srchDatasetVo, HttpServletRequest request) throws Exception {
		String resultKey = "";
		try {
			/**
			 * ???????????? ??????
			 */
			srchDatasetVo.setStatus("registration");
			String datasetKey = datasetService.createDataset(srchDatasetVo);
			
			
			/**
			 * ???????????? Origin
			 */
			List<SrchDatasetOriginVo> datasetOriginList = srchDatasetVo.getDatasetOriginList();
			if(datasetOriginList != null) {
				for(SrchDatasetOriginVo srchDatasetOriginVo : datasetOriginList) {
					srchDatasetOriginVo.setDatasetId(datasetKey);
					datasetOriginService.createDatasetOrigin(srchDatasetOriginVo);
				}
			}
			
			
			/**
			 * ???????????? ????????????
			 */
			List<SrchDatasetInstanceVo> datasetInstanceList = srchDatasetVo.getDatasetInstanceList();
			if(datasetInstanceList != null) {
				for(SrchDatasetInstanceVo srchDatasetInstanceVo : datasetInstanceList) {
					srchDatasetInstanceVo.setDatasetId(datasetKey);
					datasetInstanceService.createDatasetInstance(srchDatasetInstanceVo);
					
				}
			}
			
			/**
			 * ???????????? ????????????
			 */
			List<SrchDatasetOutputVo> datasetOutputList = srchDatasetVo.getDatasetOutputList();
			if(datasetOutputList != null) {
				for(SrchDatasetOutputVo srchDatasetOutputVo : datasetOutputList) {
					srchDatasetOutputVo.setDatasetId(datasetKey);
					datasetColumnsService.createDatasetOutput(srchDatasetOutputVo);
					
				}
			}
			
			/**
			 * ???????????? ????????????
			 */
			List<SrchDatasetSearchInfoVo> datasetSearchInfoList = srchDatasetVo.getDatasetSearchInfoList();
			if(datasetSearchInfoList != null) {
				for(SrchDatasetSearchInfoVo srchDatasetSearchInfoVo : datasetSearchInfoList) {
					srchDatasetSearchInfoVo.setDatasetId(datasetKey);
					datasetSearchConditionService.createDatasetSearchInfo(srchDatasetSearchInfoVo);
				}
			}
			
			/**
			 * ???????????? ????????????
			 */
			datasetAttachFilesService.createDatasetFile(datasetKey, request);
			
			resultKey = datasetKey;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		
		return resultKey;
	}
	
	/**
	 * <pre>????????? ???????????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 17.
	 * @param srchDatasetVo
	 * @param request
	 * @throws Exception
	 */
	@Transactional
	public void modifyDataset(SrchDatasetVo srchDatasetVo, HttpServletRequest request) throws Exception {
		String datasetId = srchDatasetVo.getId();
		try {
			/**
			 * ???????????? ???????????? ??????
			 */
			datasetService.modifyDataset(srchDatasetVo);
			
			datasetService.deleteModelAboutInfo(datasetId);
			
			/**
			 * ???????????? Origin
			 */
			List<SrchDatasetOriginVo> datasetOriginList = srchDatasetVo.getDatasetOriginList();
			if(datasetOriginList != null) {
				for(SrchDatasetOriginVo srchDatasetOriginVo : datasetOriginList) {
					srchDatasetOriginVo.setDatasetId(datasetId);
					if(StringUtil.isNotEmpty(srchDatasetOriginVo.getId())) {
						datasetOriginService.deleteDatasetOrigin(srchDatasetOriginVo);
					}
					datasetOriginService.createDatasetOrigin(srchDatasetOriginVo);
				}
			}
			
			/**
			 * ???????????? ???????????? ??????
			 */
			List<SrchDatasetInstanceVo> datasetInstanceList = srchDatasetVo.getDatasetInstanceList();
			if(datasetInstanceList != null) {
				for(SrchDatasetInstanceVo srchDatasetInstanceVo : datasetInstanceList) {
					srchDatasetInstanceVo.setDatasetId(datasetId);
					if(StringUtil.isNotEmpty(srchDatasetInstanceVo.getId())) {
						datasetInstanceService.deleteDatasetInstance(srchDatasetInstanceVo);
					}
					datasetInstanceService.createDatasetInstance(srchDatasetInstanceVo);
				}
			}
			/**
			 * ???????????? ???????????? ??????
			 */
			List<SrchDatasetOutputVo> datasetOutputList = srchDatasetVo.getDatasetOutputList();
			if(datasetOutputList != null) {
				for(SrchDatasetOutputVo srchDatasetOutputVo : datasetOutputList) {
					srchDatasetOutputVo.setDatasetId(datasetId);
					if(StringUtil.isNotEmpty(srchDatasetOutputVo.getId())) {
						datasetColumnsService.deleteDatasetOutput(srchDatasetOutputVo);
					}
					datasetColumnsService.createDatasetOutput(srchDatasetOutputVo);
					
				}
			}
			
			/**
			 * ?????? ?????? ??????
			 */
			List<SrchDatasetSearchInfoVo> datasetSearchInfoList = srchDatasetVo.getDatasetSearchInfoList();
			if(datasetSearchInfoList != null) {
				for(SrchDatasetSearchInfoVo srchDatasetSearchInfoVo : datasetSearchInfoList) {
					srchDatasetSearchInfoVo.setDatasetId(datasetId);
					if(StringUtil.isNotEmpty(srchDatasetSearchInfoVo.getId())) {
						datasetSearchConditionService.deleteDatasetSearchInfo(srchDatasetSearchInfoVo);
					}
					datasetSearchConditionService.createDatasetSearchInfo(srchDatasetSearchInfoVo);
				}
			}
			
			/**
			 * ???????????? ???????????? ??????
			 */
			long[] deleteFileOids = srchDatasetVo.getDeleteFileOids();
			if(deleteFileOids != null) {
				for(long fileOid : deleteFileOids) {
					SrchDatasetFileVo srchDatasetFileVo = new SrchDatasetFileVo();
					srchDatasetFileVo.setDatasetId(datasetId);
					srchDatasetFileVo.setId(fileOid);
					datasetAttachFilesService.deleteDatasetFile(srchDatasetFileVo);
				}
			}
			
			/**
			 * ???????????? ???????????? ??????
			 */
			datasetAttachFilesService.createDatasetFile(datasetId, request);
		}catch(GlobalException e) {
			e.printStackTrace();
		}catch(Exception e) {
			throw e;
		}
		
	}
	
	/**
	 * <pre>????????? ???????????? ????????????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 28.
	 * @param srchDatasetVo
	 * @param request
	 * @throws Exception
	 */
	@Transactional
	public void patchDataset(SrchDatasetVo srchDatasetVo) throws Exception {
//		switch(dsvStatus) {
//			case "devMode":			//??????
//			case "releaseRequest":	//????????????
//			{
//				if("releaseRequest".equals(dsvStatus)) {
//					datasetService.patchDataset(srchDatasetVo);
//					datasetService.createDatasetApprovalHistory(srchDatasetVo);
//				}
//				break;
//			}
//			case "releaseReject":		//??????????????????
//			case "prodMode":			//??????????????????(????????????)
//			{
//				datasetService.patchDataset(srchDatasetVo);
//				datasetService.createDatasetApprovalHistory(srchDatasetVo);
//				break;
//			}
//		}
	}

	/**
	 * <pre>????????? ???????????? ???????????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 29.
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDatasetWishlistList(SrchDatasetVo srchDatasetVo) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		List<Map<String, Object>> datasetList = new ArrayList<>(); 
		String loginUserId = srchDatasetVo.getLoginUserId();
		
		SrchDatasetInterestVo srchDatasetInterestVo = new SrchDatasetInterestVo();
		srchDatasetInterestVo.setLoginUserId(loginUserId);
		
		Map<String, Object> datasetUsageListMap = datasetWishlistService.getDatasetWishlistList(srchDatasetInterestVo);
		List<DatasetInterestVo> datasetUsageList = (List<DatasetInterestVo>)datasetUsageListMap.get("list");
		for(DatasetInterestVo datasetInterestVo : datasetUsageList) {
			String productDatasetId = datasetInterestVo.getDatasetId();
			Map<String, Object> datasetMap = this.getDataset(productDatasetId, loginUserId, true);
			datasetList.add(datasetMap);
		}
		resMap.put("page", datasetUsageListMap.get("page"));
		resMap.put("list", datasetList);
		return resMap;
	}

	/**
	 * <pre>????????? ???????????? ???????????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 29.
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDatasetUsageList(SrchDatasetVo srchDatasetVo) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		List<Map<String, Object>> datasetList = new ArrayList<>(); 
		String loginUserId = srchDatasetVo.getLoginUserId();
		
		SrchDatasetUseRequestVo srchDatasetUseRequestVo = new SrchDatasetUseRequestVo();
		srchDatasetUseRequestVo.setLoginUserId(loginUserId);
		
		Map<String, Object> datasetUsageListMap = datasetRequestUsageService.getDatasetUsageList(srchDatasetUseRequestVo);
		List<DatasetUseRequestVo> datasetUsageList = (List<DatasetUseRequestVo>)datasetUsageListMap.get("list");
		for(DatasetUseRequestVo datasetUseRequestVo : datasetUsageList) {
			String productDatasetId = datasetUseRequestVo.getDatasetId();
			Map<String, Object> datasetMap = this.getDataset(productDatasetId, loginUserId, true);
			datasetList.add(datasetMap);
		}
		resMap.put("page", datasetUsageListMap.get("page"));
		resMap.put("list", datasetList);
		return resMap;
	}

	/**
	 * <pre>????????? ???????????? ???????????????  ????????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 29.
	 * @param srchDatasetVo
	 * @return
	 */
	public Map<String, Object> getDatasetByCategoryCount(SrchDatasetVo srchDatasetVo) {
		Map<String, Object> resMap = new HashMap<>();
		List<Integer> categoryCountList = new ArrayList<>();
		for(long categoryId : srchDatasetVo.getCategoryOidList()) {
			srchDatasetVo.setCategoryId(categoryId);
			Map<String, Object> datasetListMap = datasetService.getDatasetList(srchDatasetVo);
			PageVo pageVo = (PageVo)datasetListMap.get("page");
			int categoryTotalListSize = pageVo.getTotalListSize();
			categoryCountList.add(categoryTotalListSize);
		}
		resMap.put("count", categoryCountList);
		return resMap;
	}

	/**
	 * <pre>???????????? ???????????? ?????? ?????? ?????? ??????(?????????, ?????????)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param srchIntegratedVo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDatasetUsageRefundsListByUser(SrchIntegratedVo srchIntegratedVo) throws Exception {
		Map<String, Object> resMap = new HashMap<>();

		try {
			int totalListSize = integratedMapper.selectDatasetUsageRefundsCountByUser(srchIntegratedVo);
			srchIntegratedVo.setTotalListSize(totalListSize);
			List<Map<String, Object>> datasetUsageRefundsList = integratedMapper.selectDatasetUsageRefundsListByUser(srchIntegratedVo);
			
			for(Map<String, Object> datasetUsageRefundsMap : datasetUsageRefundsList) {
				SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo = new SrchDatasetUseRequestRefundVo();
				srchDatasetUseRequestRefundVo.setRequestId((long)datasetUsageRefundsMap.get("requestId"));
				datasetUsageRefundsMap.put("dataset_usage_refunds_history", datasetMapper.selectDatasetUsageRefundList(srchDatasetUseRequestRefundVo));
			}
			
			if(srchIntegratedVo.isPaging()) {
				resMap.put("page", srchIntegratedVo);
			}else{
				resMap.put("page", null);
			}
			resMap.put("list", datasetUsageRefundsList);
		}catch(Exception e) {
			e.printStackTrace();
			new GlobalException(e);
		}
		return resMap;
	}
	
	/**
	 * <pre>???????????? ???????????? ?????? ?????? ?????? ??????(?????????, ?????????)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param srchIntegratedVo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDatasetUsageRefundsListByProvider(SrchIntegratedVo srchIntegratedVo) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		
		try {
			int totalListSize = integratedMapper.selectDatasetUsageRefundsCountByProvider(srchIntegratedVo);
			srchIntegratedVo.setTotalListSize(totalListSize);
			List<Map<String, Object>> datasetUsageRefundsList = integratedMapper.selectDatasetUsageRefundsListByProvider(srchIntegratedVo);
			
			if(srchIntegratedVo.isPaging()) {
				resMap.put("page", srchIntegratedVo);
				SrchIntegratedVo countSrchIntegratedVo = new SrchIntegratedVo();
				countSrchIntegratedVo.setLoginUserId(srchIntegratedVo.getLoginUserId());
				countSrchIntegratedVo.setSrchRefundStatus("pay_request");
				resMap.put("datasetUsageRefundRequestCount", integratedMapper.selectDatasetUsageRefundsCountByProvider(countSrchIntegratedVo));
			}else{
				resMap.put("page", null);
			}
			resMap.put("list", datasetUsageRefundsList);
		}catch(Exception e) {
			e.printStackTrace();
			new GlobalException(e);
		}
		return resMap;
	}
	
}
