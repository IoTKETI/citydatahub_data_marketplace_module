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
package kr.co.n2m.smartcity.datapublish.feature.dataset.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.sample.SimpleDatasetVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.DatasetSearchInfoVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.SrchDatasetSearchInfoVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.usageHistory.DatasetUseHistoryVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.usageHistory.SrchDatasetUseHistoryVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.wishlist.DatasetInterestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.wishlist.SrchDatasetInterestVo;

@Mapper
public interface DatasetMapper {

	/**
	 * <pre>데이터셋 이용신청 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchDatasetUseRequestVo
	 * @return
	 */
	int deleteDatasetUsage(SrchDatasetUseRequestVo srchDatasetUseRequestVo);

	/**
	 * <pre>실시간 데이터 제공 대상자 목록 조회</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 11. 6.
	 * @param diiId
	 * @return
	 */
	public List<DatasetUseRequestVo> selectDatasetUseRequestPushList(String instanceId);
	
	/**
	 * <pre>데이터셋 어댑터 목록 카운트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchDatasetAdaptorVo
	 * @return
	 */
	int selectDatasetAdaptorCount(SrchDatasetAdaptorVo srchDatasetAdaptorVo);
	
	/**
	 * <pre>데이터셋 어댑터 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchDatasetAdaptorVo
	 * @return
	 */
	List<DatasetAdaptorVo> selectDatasetAdaptorList(SrchDatasetAdaptorVo srchDatasetAdaptorVo);
	
	/**
	 * <pre>데이터셋 어댑터 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param datasetAdapterId
	 * @return
	 */
	DatasetAdaptorVo selectDatasetAdaptor(String datasetAdapterId);

	/**
	 * <pre>데이터셋 어댑터 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchDatasetAdaptorVo
	 * @return
	 */
	int insertDatasetAdaptor(SrchDatasetAdaptorVo srchDatasetAdaptorVo);

	/**
	 * <pre>데이터셋 어댑터 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchDatasetAdaptorVo
	 * @return
	 */
	int updateDatasetAdaptor(SrchDatasetAdaptorVo srchDatasetAdaptorVo);

	/**
	 * <pre>데이터셋 어댑터 필드 정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchDatasetAdaptorVo
	 * @return
	 */
	int insertDatasetAdaptorField(SrchDatasetAdaptorFieldVo srchDatasetAdaptorFieldVo);

	/**
	 * <pre>상태로 데이터셋 어댑터 상세조회 </pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchDatasetAdaptorVo
	 * @return
	 */
	int selectDatasetAdaptorByStatus(SrchDatasetAdaptorVo srchDatasetAdaptorVo);
	
	/**
	 * <pre>데이터셋 이용신청 목록 건수</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetVo
	 * @return
	 */
	int selectDatasetUsageCount(SrchDatasetVo srchDatasetVo);

	/**
	 * <pre>데이터셋 이용신청 목록</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetUseRequestVo
	 * @return
	 */
	List<DatasetUseRequestVo> selectDatasetUsageList(SrchDatasetUseRequestVo srchDatasetUseRequestVo);

	/**
	 * <pre>데이터셋 이용신청 수정</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetUseRequestVo
	 * @return
	 */
	int updateDatasetUsage(SrchDatasetUseRequestVo srchDatasetUseRequestVo);
	
	/**
	 * <pre>데이터셋 이용신청 부분수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 25.
	 * @param srchDatasetUseRequestVo
	 * @return
	 */
	int updateDatasetUsagePart(SrchDatasetUseRequestVo srchDatasetUseRequestVo);
	
	/**
	 * <pre>데이터셋 관심상품 목록 건수</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetInterestVo
	 * @return
	 */
	int selectDatasetWishlistCount(SrchDatasetInterestVo srchDatasetInterestVo);

	/**
	 * <pre>데이터셋 관심상품 목록</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetInterestVo
	 * @return
	 */
	List<DatasetInterestVo> selectDatasetWishlistList(SrchDatasetInterestVo srchDatasetInterestVo);

	/**
	 * <pre>데이터셋 관심상품 등록</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetInterestVo
	 * @return
	 */
	int insertDatasetWishlist(SrchDatasetInterestVo srchDatasetInterestVo);

	/**
	 * <pre>데이터셋 관심상품 삭제</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetInterestVo
	 * @return
	 */
	int deleteDatasetWishlist(SrchDatasetInterestVo srchDatasetInterestVo);

	/**
	 * <pre>데이터셋 문의 목록 건수</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetInquiryVo
	 * @return
	 */
	int selectDatasetInquiryCount(SrchDatasetInquiryVo srchDatasetInquiryVo);

	/**
	 * <pre>데이터셋 문의 목록</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetInquiryVo
	 * @return
	 */
	List<DatasetInquiryVo> selectDatasetInquiryList(SrchDatasetInquiryVo srchDatasetInquiryVo);

	/**
	 * <pre>데이터셋 문의 상세</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetInquiryVo
	 * @return
	@Param("datasetId")
	@Param("qnaId")
	 */ 
	DatasetInquiryVo selectDatasetInquiry(String datasetId, long qnaId);

	/**
	 * <pre>데이터셋 문의 등록</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetInquiryVo
	 * @return
	 */
	int insertDatasetInquiry(SrchDatasetInquiryVo srchDatasetInquiryVo);
	
	/**
	 * <pre>데이터셋 문의 수정</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetInquiryVo
	 * @return
	 */
	int updateDatasetInquiry(SrchDatasetInquiryVo srchDatasetInquiryVo);

	/**
	 * <pre>데이터셋 문의 삭제</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetInquiryVo
	 * @return
	 */
	int deleteDatasetInquiry(SrchDatasetInquiryVo srchDatasetInquiryVo);

	/**
	 * <pre>데이터 분석가승인 요청</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 */
	int insertDataAnalystRequest(SrchDataAnalystRequestVo srchDataAnalystRequestVo);

	/**
	 * <pre>데이터 분석가승인 요청 목록 건수</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 */
	int selectDataAnalystRequestCount(SrchDataAnalystRequestVo srchDataAnalystRequestVo);

	/**
	 * <pre>데이터 분석가승인 요청 목록</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 */
	List<DataAnalystRequestVo> selectDataAnalystRequestList(SrchDataAnalystRequestVo srchDataAnalystRequestVo);
	
	/**
	 * <pre>데이터 분석가승인 상세</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 */
	DataAnalystRequestVo selectDataAnalystRequest(@Param("requestId") long requestId);
	/**
	 * <pre>데이터 분석가승인 완료</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 */
	int updateDataAnalystRequest(SrchDataAnalystRequestVo srchDataAnalystRequestVo);
	
	/**
	 * <pre>데이터 분석가승인 삭제</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 10. 6.
	 * @param requestId
	 * @return
	 */
	int deleteDataAnalystRequest(long requestId);
	
	/**
	 * <pre>데이터 분석가 승인 모델 목록 조회 (요청자 기준) - 인터페이스 전용</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 10. 6.
	 * @param userId
	 * @return
	 */
	List<String> selectInfDataAnalystRequestModelList(String userId);

	/**
	 * <pre>데이터 분석 모델 목록</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 */
	List<DataAnalystRequestModelVo> selectDataAnalystRequestModelList(SrchDataAnalystRequestModelVo srchDataAnalystRequestModelVo);
	
	/**
	 * <pre>데이터 분석 모델 등록</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 */
	int insertDataAnalystRequestModel(SrchDataAnalystRequestModelVo srchDataAnalystRequestModelVo);

	/**
	 * <pre>데이터 분석 모델 상세 조회</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 */
	DataAnalystRequestModelVo selectDataAnalystRequestModel(String requestId, String modelId);
	

	/**
	 * <pre>데이터 분석 모델 삭제</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 */
	int deleteDataAnalystRequestModel(SrchDataAnalystRequestModelVo srchDataAnalystRequestModelVo);

	/**
	 * <pre>데이터셋 카테고리별 건수</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param categoryOid
	 * @return
	 */
	int selectCategoryDatasetCount(List<Long> categoryOid);

	/**
	 * <pre>데이터셋 활용내역 등록</pre>
	 * @Author      : thlee
	 * @Date        : 2020. 2. 13.
	 * @param srchDatasetUseHistoryVo
	 * @return
	 */
	int insertDatasetUseHistory(SrchDatasetUseHistoryVo srchDatasetUseHistoryVo);

	/**
	 * <pre>데이터셋 출력정보 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetCollaborationVo
	 * @return
	 */
	List<DatasetOutputVo> selectDatasetOutputList(SrchDatasetOutputVo srchDatasetOutputVo);
	
	/**
	 * <pre>데이터셋 출력정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetCollaborationVo
	 */
	int insertDatasetOutput(SrchDatasetOutputVo srchDatasetOutputVo);
	
	/**
	 * <pre>데이터셋 출력정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetCollaborationVo
	 */
	int deleteDatasetOutput(SrchDatasetOutputVo srchDatasetOutputVo);
	
	/**
	 * <pre>데이터셋 인스턴스 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetCollaborationVo
	 * @return
	 */
	List<DatasetInstanceVo> selectDatasetInstanceList(SrchDatasetInstanceVo srchDatasetInstanceVo);
	
	/**
	 * <pre>데이터셋 인스턴스 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetCollaborationVo
	 */
	int insertDatasetInstance(SrchDatasetInstanceVo srchDatasetInstanceVo);
	
	/**
	 * <pre>데이터셋 인스턴스 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetCollaborationVo
	 */
	int deleteDatasetInstance(SrchDatasetInstanceVo srchDatasetInstanceVo);
	
	/**
	 * <pre>데이터셋 조회조건 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetSearchInfoVo
	 * @return
	 */
	List<DatasetSearchInfoVo> selectDatasetSearchInfoList(SrchDatasetSearchInfoVo srchDatasetSearchInfoVo);
	
	/**
	 * <pre>데이터셋 조회조건 상세조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 14.
	 * @param srchDatasetSearchInfoVo
	 * @return
	 */
	DatasetSearchInfoVo selectDatasetSearchInfo(SrchDatasetSearchInfoVo srchDatasetSearchInfoVo);
	
	/**
	 * <pre>데이터셋 조회조건 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetSearchInfoVo
	 */
	int insertDatasetSearchInfo(SrchDatasetSearchInfoVo srchDatasetSearchInfoVo);
	
	/**
	 * <pre>데이터셋 조회조건 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetSearchInfoVo
	 */
	int updateDatasetSearchInfo(SrchDatasetSearchInfoVo srchDatasetSearchInfoVo);
	
	/**
	 * <pre>데이터셋 조회조건 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetSearchInfoVo
	 */
	int deleteDatasetSearchInfo(SrchDatasetSearchInfoVo srchDatasetSearchInfoVo);

	/**
	 * <pre>데이터셋 파일 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetFileVo
	 * @return
	 */
	List<DatasetFileVo> selectDatasetFileList(SrchDatasetFileVo srchDatasetFileVo);
	
	/**
	 * <pre>데이터셋 파일 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetFileVo
	 * @return
	 */
	DatasetFileVo selectDatasetFile(SrchDatasetFileVo srchDatasetFileVo);
	
	/**
	 * <pre>데이터셋 파일 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetFileVo
	 * @return
	 */
	int insertDatasetFile(SrchDatasetFileVo srchDatasetFileVo);
	
	/**
	 * <pre>데이터셋 파일 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetFileVo
	 * @return
	 */
	int deleteDatasetFile(SrchDatasetFileVo srchDatasetFileVo);

	/**
	 * <pre>데이터셋 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param srchDatasetVo
	 * @return
	 */
	int insertDataset(SrchDatasetVo srchDatasetVo);

	/**
	 * <pre>데이터셋 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param datasetId
	 * @return
	 */
	DatasetVo selectDataset(String datasetId);
	
	/**
	 * <pre>데이터셋 수정(전체수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDatasetVo
	 * @return
	 */
	int updateDataset(SrchDatasetVo srchDatasetVo);

	/**
	 * <pre>데이터셋 수정(부분수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDatasetVo
	 * @return
	 */
	int updateDatasetPart(SrchDatasetVo srchDatasetVo);

	/**
	 * <pre>데이터셋 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDatasetVo
	 * @return
	 */
	int deleteDataset(SrchDatasetVo srchDatasetVo);
	

	/**
	 * <pre>데이터셋 인스턴스 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param datasetId
	 * @return
	 */
	List<DatasetInstanceVo> selectDatasetInstanceList(String datasetId);

	/**
	 * <pre></pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param datasetId
	 * @return
	 */
	SimpleDatasetVo selectSimpleDataset(String datasetId);

	/**
	 * <pre>데이터셋 목록 카운트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param srchDatasetVo
	 * @return
	 */
	int selectDatasetCount(SrchDatasetVo srchDatasetVo);

	/**
	 * <pre>데이터셋 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param srchDatasetVo
	 * @return
	 */
	List<DatasetVo> selectDatasetList(SrchDatasetVo srchDatasetVo);

	/**
	 * <pre>데이터셋 이용신청 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param srchDatasetUseRequestVo
	 * @return
	 */
	int insertDatasetUsage(SrchDatasetUseRequestVo srchDatasetUseRequestVo);

	/**
	 * <pre>데이터셋 이용신청 카운트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param srchDatasetUseRequestVo
	 * @return
	 */
	int selectDatasetUsageCount(SrchDatasetUseRequestVo srchDatasetUseRequestVo);
	
	/**
	 * <pre>데이터셋 어댑터 필드 정보 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param srchDatasetAdaptorFieldVo
	 * @return
	 */
	List<DatasetAdaptorFieldVo> selectDatasetAdaptorFieldList(SrchDatasetAdaptorFieldVo srchDatasetAdaptorFieldVo);

	/**
	 * <pre>데이터셋 어댑터 필드 정보 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param datasetAdapterId
	 * @param columnId
	 * @return
	 */
	DatasetAdaptorFieldVo selectDatasetAdaptorField(String datasetAdapterId, String columnId);

	/**
	 * <pre>데이터셋 어댑터 필드 정보 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param srchDatasetAdaptorFieldVo
	 * @return
	 */
	int updateDatasetAdaptorField(SrchDatasetAdaptorFieldVo srchDatasetAdaptorFieldVo);

	/**
	 * <pre>데이터셋 어댑터 필드 정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param srchDatasetAdaptorFieldVo
	 * @return
	 */
	int deleteDatasetAdaptorField(SrchDatasetAdaptorFieldVo srchDatasetAdaptorFieldVo);

	/**
	 * <pre>데이터셋 이용신청 내역 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param datasetId
	 * @param requestUsageId
	 * @return
	 */
	DatasetUseRequestVo selectDatasetUsage(String datasetId, long requestUsageId);

	/**
	 * <pre>데이터셋 조회수 증가</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param datasetId
	 * @return
	 */
	int updateDatasetHit(@Param("datasetId") String datasetId);

	/**
	 * <pre>데이터셋 이용내역 목록 카운트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param srchDatasetUseHistoryVo
	 * @return
	 */
	int selectDatasetUseHistoryCount(SrchDatasetUseHistoryVo srchDatasetUseHistoryVo);
	
	/**
	 * <pre>데이터셋 이용내역 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 9.
	 * @param srchDatasetUseHistoryVo
	 * @return
	 */
	List<DatasetUseHistoryVo> selectDatasetUseHistoryList(SrchDatasetUseHistoryVo srchDatasetUseHistoryVo);
	/**
	 * <pre>데이터셋 출력정보 수정(전체수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 14.
	 * @param srchDatasetOutputVo
	 */
	int updateDatasetOutput(SrchDatasetOutputVo srchDatasetOutputVo);
	
	/**
	 * <pre>데이터셋 출력정보 수정(부분수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 14.
	 * @param srchDatasetOutputVo
	 */
	int updateDatasetOutputPart(SrchDatasetOutputVo srchDatasetOutputVo);

	/**
	 * <pre>데이터셋 출력정보 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 15.
	 * @param datasetId
	 * @param columnId
	 * @return
	 */
	DatasetOutputVo selectDatasetOutput(String datasetId, String columnId);

	/**
	 * <pre>데이터셋 만족도 평가 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDatasetSatisfactionRatingVo
	 * @return
	 */
	List<DatasetSatisfactionRatingVo> selectDatasetSatisfactionList(SrchDatasetSatisfactionRatingVo srchDatasetSatisfactionRatingVo);
	
	/**
	 * <pre>데이터셋 만족도 평가 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param datasetId
	 * @param ratingId
	 * @return
	 */
	DatasetSatisfactionRatingVo selectDatasetSatisfaction(String datasetId, String ratingId);
	
	/**
	 * <pre>데이터셋 만족도 평가 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDatasetSatisfactionRatingVo
	 * @return
	 */
	int insertDatasetSatisfaction(SrchDatasetSatisfactionRatingVo srchDatasetSatisfactionRatingVo);
	
	/**
	 * <pre>데이터셋 만족도 평가 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDatasetSatisfactionRatingVo
	 * @return
	 */
	int updateDatasetSatisfaction(SrchDatasetSatisfactionRatingVo srchDatasetSatisfactionRatingVo);
	
	/**
	 * <pre>데이터셋 만족도 평가 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDatasetSatisfactionRatingVo
	 * @return
	 */
	int deleteDatasetSatisfaction(SrchDatasetSatisfactionRatingVo srchDatasetSatisfactionRatingVo);

	void insertDatasetApprovalHistory(Map<String, Object> params);
	/**
	 * <pre>데이터셋 ORIGIN 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 28.
	 * @param srchDatasetOriginVo
	 */
	void insertDatasetOrigin(SrchDatasetOriginVo srchDatasetOriginVo);
	/**
	 * <pre>데이터셋 ORIGIN 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 28.
	 * @param srchDatasetOriginVo
	 */
	List<DatasetOriginVo> selectDatasetOriginList(SrchDatasetOriginVo srchDatasetOriginVo);
	/**
	 * <pre>데이터셋 ORIGIN 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 28.
	 * @param srchDatasetOriginVo
	 */
	DatasetOriginVo selectDatasetOrigin(String datasetId, String datasetOriginId);
	/**
	 * <pre>데이터셋 ORIGIN 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 28.
	 * @param srchDatasetOriginVo
	 */
	int deleteDatasetOrigin(SrchDatasetOriginVo srchDatasetOriginVo);

	/**
	 * <pre>데이터셋 가격정책 연결정보 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesVo
	 * @return
	 */
	List<DatasetPricePoliciesVo> selectDatasetPricePoliciesList(SrchDatasetPricePoliciesVo srchDatasetPricePoliciesVo);

	/**
	 * <pre>데이터셋 가격정책 연결정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesVo
	 */
	void insertDatasetPricePolicies(SrchDatasetPricePoliciesVo srchDatasetPricePoliciesVo);

	/**
	 * <pre>가격정책 연결정보 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 11.
	 * @param datasetId
	 * @param priceId
	 * @return
	 */
	DatasetPricePoliciesVo selectDatasetPricePolicies(long priceId);
	
	/**
	 * <pre>데이터셋 가격정책 연결정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesVo
	 * @return
	 */
	int deleteDatasetPricePolicies(SrchDatasetPricePoliciesVo srchDatasetPricePoliciesVo);

	/**
	 * <pre>데이터셋 가격정책 금액정보 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesPeriodPriceVo
	 * @return
	 */
	List<DatasetPricePoliciesPeriodPriceVo> selectDatasetPricePoliciesPeriodPriceList(SrchDatasetPricePoliciesPeriodPriceVo srchDatasetPricePoliciesPeriodPriceVo);

	/**
	 * <pre>데이터셋 가격정책 금액정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesPeriodPriceVo
	 * @return
	 */
	void insertDatasetPricePoliciesPeriodPrice(SrchDatasetPricePoliciesPeriodPriceVo srchDatasetPricePoliciesPeriodPriceVo);

	/**
	 * <pre>데이터셋 가격정책 금액정보 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesPeriodPriceVo
	 * @return
	 */
	DatasetPricePoliciesPeriodPriceVo selectDatasetPricePoliciesPeriodPrice(@Param("priceId") long priceId, @Param("periodId") long periodId);

	/**
	 * <pre>데이터셋 가격정책 금액정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesPeriodPriceVo
	 * @return
	 */
	int deleteDatasetPricePoliciesPeriodPrice(SrchDatasetPricePoliciesPeriodPriceVo srchDatasetPricePoliciesPeriodPriceVo);


	/**
	 * <pre>데이터셋 이용신청 유료결제정보 목록 카운트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 13.
	 * @param srchDatasetUseRequestPaymentVo
	 * @return
	 */
	
	int selectDatasetUsagePaymentCount(SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo);


	/**
	 * <pre>데이터셋 이용신청 유료결제정보 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 13.
	 * @param srchDatasetUseRequestPaymentVo
	 * @return
	 */
	List<DatasetUseRequestPaymentVo> selectDatasetUsagePaymentList(SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo);


	/**
	 * <pre>데이터셋 이용신청 유료결제정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 13.
	 * @param srchDatasetUseRequestPaymentVo
	 * @return
	 */
	void insertDatasetUsagePayment(SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo);


	/**
	 * <pre>데이터셋 이용신청 유료결제정보 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 13.
	 * @param datasetId
	 * @param requestUsageId
	 * @param payId
	 * @return
	 */
	DatasetUseRequestPaymentVo selectDatasetUsagePayment(@Param("id") long id, @Param("requestId") long requestId);


	/**
	 * <pre>데이터셋 이용신청 유료결제정보 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 13.
	 * @param srchDatasetUseRequestPaymentVo
	 */
	int updateDatasetUsagePayment(SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo);


	/**
	 * <pre>데이터셋 이용신청 유료결제정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 13.
	 * @param srchDatasetUseRequestPaymentVo
	 */
	int deleteDatasetUsagePayment(SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo);

	int selectDatasetUsageReceptionCount(SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo);

	List<DatasetUseRequestReceptionVo> selectDatasetUsageReceptionList(SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo);

	void insertDatasetUsageReception(SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo);

	DatasetUseRequestReceptionVo selectDatasetUsageReception(String datasetId, long requestUsageId, long recvId);

	int updateDatasetUsageReception(SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo);

	int deleteDatasetUsageReception(SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo);

	/**
	 * <pre>데이터셋 이용신청 환불 신청 목록 카운트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 7.
	 * @param srchDatasetUseRequestRefundVo
	 * @return
	 */
	int selectDatasetUsageRefundCount(SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo);

	/**
	 * <pre>데이터셋 이용신청 환불 신청 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 7.
	 * @param srchDatasetUseRequestRefundVo
	 * @return
	 */
	List<DatasetUseRequestRefundVo> selectDatasetUsageRefundList(SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo);

	/**
	 * <pre>데이터셋 이용신청 환불 신청 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 7.
	 * @param srchDatasetUseRequestRefundVo
	 */
	void insertDatasetUsageRefund(SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo);

	/**
	 * <pre>데이터셋 이용신청 환불 신청 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 7.
	 * @param datasetId
	 * @param requestUsageId
	 * @param refundId
	 * @return
	 */
	DatasetUseRequestRefundVo selectDatasetUsageRefund(String datasetId, String requestUsageId, String refundId);

	/**
	 * <pre>데이터셋 이용신청 환불 신청 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 7.
	 * @param srchDatasetUseRequestRefundVo
	 * @return
	 */
	int updateDatasetUsageRefund(SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo);

	/**
	 * <pre>데이터셋 이용신청 환불 신청 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 7.
	 * @param srchDatasetUseRequestRefundVo
	 * @return
	 */
	int deleteDatasetUsageRefund(SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo);

	/**
	 * <pre>데이터셋 - 디바이스 연결 API 목록 조회 (타 시스템 연계 인터페이스)</pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 16.
	 * @param datasetId
	 * @return
	 */
	List<DatasetInfDeviceVo> selectDatasetDeviceInfo(String datasetId);
	
	/**
	 * <pre>데이터셋 - 디바이스 연결 API 등록 (타 시스템 연계 인터페이스)</pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 16.
	 * @param datasetInfDeviceVo
	 */
	void createDatasetDevice(DatasetInfDeviceVo datasetInfDeviceVo);
	
	/**
	 * <pre>데이터셋 - 디바이스 연결 API 삭제 (타 시스템 연계 인터페이스)</pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 19.
	 * @param datasetId
	 */
	void deleteDatasetDevice(String datasetId);
	
	/**
	 * <pre>구독 이용신청 카운트 조회</pre>
	 * @Author      : sw-kim
	 * @Date        : 2021. 9. 29.
	 * @param srchDatasetUseRequestVo
	 * @return
	 */
	int selectDatasetSubscriptionCount(SrchDatasetUseRequestVo srchDatasetUseRequestVo);
	
	/**
	 * <pre>구독 이용신청db 카운트 조회</pre>
	 * @Author      : sw-kim
	 * @Date        : 2021. 9. 29.
	 * @param srchDatasetUseRequestVo
	 * @return
	 */
	int selectDatasetSubscriptionDbCount(SrchDatasetUseRequestVo srchDatasetUseRequestVo);
	
	/**
	 * <pre>구독 subcriptionId</pre>
	 * @Author      : sw-kim
	 * @Date        : 2021. 9. 29.
	 * @param srchDatasetUseRequestVo
	 * @return
	 */
	String selectSubscriptionId(SrchDatasetUseRequestVo srchDatasetUseRequestVo);

	
	/**
	 * <pre>구독 이용신청 type 조회</pre>
	 * @Author      : sw-kim
	 * @Date        : 2021. 9. 29.
	 * @param srchDatasetUseRequestVo
	 * @return
	 */
	String selectDatasetSubscriptionType(SrchDatasetUseRequestVo srchDatasetUseRequestVo);
	
	/**
	 * <pre>구독 이용신청 type 조회</pre>
	 * @Author      : sw-kim
	 * @Date        : 2021. 9. 29.
	 * @param srchDatasetUseRequestVo
	 * @return
	 */
	List<DatasetUseRequestVo> selectDatasetSubscriptionId(SrchDatasetUseRequestVo srchDatasetUseRequestVo);
	
	/**
	 * <pre>구독 이용신청 등록</pre>
	 * @Author      : sw-kim
	 * @Date        : 2021. 9. 29.
	 * @param srchDatasetUseRequestVo
	 * @return
	 */
	int insertDataseSubScriptiontUsage(SrchDatasetUseRequestVo srchDatasetUseRequestVo);
	
	/**
	 * <pre>구독 이용신청 삭제</pre>
	 * @Author      : sw-kim
	 * @Date        : 2021. 9. 29.
	 * @param srchDatasetUseRequestVo
	 * @return
	 */
	int deleteDataseSubScriptiontUsage(SrchDatasetUseRequestVo srchDatasetUseRequestVo);

	/**
	 * <pre>데이터셋 oid 실시간 데이터 제공 대상자 목록 조회</pre>
	 * @Author		: sw-kim
	 * @Date		: 2021. 10. 5.
	 * @param dsOid
	 * @return
	 */
	public List<DatasetUseRequestVo> selectDsOidDatasetUseRequestPushList(String dsOid);
	/**
	 * 
	 * <pre>데이터셋 oid 실시간 데이터 제공 대상자 목록 조회</pre>
	 * @Author		: sw-kim
	 * @Date		: 2021. 10. 5.
	 * @param dsOid
	 * @return
	 */
	public List<String> selectDsOriginId(String dsOid);
	
	public void deleteDatasetModelDsByDsId(String dsOid);
	public void deleteDatasetInstanceInfoByDsId(String dsOid);
	public void deleteDatasetOutputInfoByDsId(String dsOid);
	public void deleteDatasetSearchInfoByDsId(String dsOid);
}
