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
package kr.co.smartcity.user.feature.dataset.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("datasetVo")
public class DatasetVo {

	private String id;                     //데이터셋 고유번호
	private String title;                  //데이터셋 제목
	private String status;                 //데이터셋 상태코드
	private String statusName;             //데이터셋 상태코드명
	private long categoryId;               //카테고리고유번호
	private String description;            //데이터셋 설명
	private String modelId;                //데이터모델id
	private String modelName;              //데이터모델id
	private String providerId;             //데이터소유자id
	private String creatorId;              //등록자id
	private String createdAt;              //등록일시
	private String modifierId;             //수정자id
	private String modifiedAt;             //수정일시
	private String updatePeriod;           //갱신주기
	private String provider;               //제공기관
	private String providerSystem;         //제공시스템
	private String dataOfferType;          //제공유형코드
	private String dataOfferTypeName;      //제공유형코드명
	private String keywords;               //키워드
	private String license;                //라이선스
	private String constraints;            //제약사항
	private String extension;              //확장자코드
	private String extensionName;          //확장자코드명
	private String dataType;               //데이터유형코드
	private String dataTypeName;           //데이터유형코드명
	private String ownership;              //소유권
	private long retrievalCount;         //조회수

	private long[] categoryIdList;  //카테고리고유번호리스트
	
	private String loginUserId;
	/**
	 * 데이터셋 소유자 상세정보
	 */
	private String dsModelOwnerNm;
	private String dsModelOwnerPhone;
	private String dsModelOwnerEmail;
	
	/**
	 * 이용신청
	 */
	private String usageYn;
	private String pushUri;             //PUSH URI주소
	private String reqUserId;           //신청자
	private String reqStatus;           //신청상태
	private String reqUseMethodCode;    //활용방법코드
	private String reqCreated;          //등록일시
	
	/**
	 * 관심상품
	 */
	
	private String wishlistYn;
	private String itrtId;             //관심상품고유번호
	private String userId;             //신청자
	private int itrtTotalCount;        //관심상품등록수
	
	/**
	 * 파일
	 */
	private long dsFileOid;             //데이터셋 대표이미지 파일 고유번호
	private long[] deleteFileOids;      //데이터셋 파일 고유번호(삭제용)
	
	/**
	 * 데이터 인스턴스(Datalake용)
	 */
	private String dsInstanceId;		//데이터 인스턴스 ID
	private String dsInstanceNm;		//데이터 인스턴스 명
	
	private List<DatasetOriginVo> datasetOriginList; // 인스턴스정보 리스트
	private List<DatasetInstanceVo> datasetInstanceList; // 인스턴스정보 리스트
	private List<DatasetOutputVo> datasetOutputList; // 출력정보 리스트
	private List<DatasetFileVo> datasetFileList; // 파일정보 리스트
	private List<DatasetSearchInfoVo> datasetSearchInfoList; // 조회조건 리스트
	private List<DatasetInquiryVo> datasetInquiryList; // 문의내역 리스트
	
	private String reqType;			//출시요청구분자
	private String portalType;		//포탈구분자
	private String nohit;			//조회수 증가여부
	
	private String searchType;             //검색조건
	private String modelType;              //유형
	private String modelNamespace;         //네임스페이스
	private String modelVersion;           //버전
	
	
	private String modelTypeUri;           //TypeUri 추가  ksw : 21.09.06

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getModifierId() {
		return modifierId;
	}

	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	public String getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(String modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getUpdatePeriod() {
		return updatePeriod;
	}

	public void setUpdatePeriod(String updatePeriod) {
		this.updatePeriod = updatePeriod;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProviderSystem() {
		return providerSystem;
	}

	public void setProviderSystem(String providerSystem) {
		this.providerSystem = providerSystem;
	}

	public String getDataOfferType() {
		return dataOfferType;
	}

	public void setDataOfferType(String dataOfferType) {
		this.dataOfferType = dataOfferType;
	}

	public String getDataOfferTypeName() {
		return dataOfferTypeName;
	}

	public void setDataOfferTypeName(String dataOfferTypeName) {
		this.dataOfferTypeName = dataOfferTypeName;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getConstraints() {
		return constraints;
	}

	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataTypeName() {
		return dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public long getRetrievalCount() {
		return retrievalCount;
	}

	public void setRetrievalCount(long retrievalCount) {
		this.retrievalCount = retrievalCount;
	}

	public long[] getDsCategoryIdList() {
		return categoryIdList;
	}

	public void setDsCategoryIdList(long[] categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

	public String getWishlistYn() {
		return wishlistYn;
	}

	public void setWishlistYn(String wishlistYn) {
		this.wishlistYn = wishlistYn;
	}

	public String getUsageYn() {
		return usageYn;
	}

	public void setUsageYn(String usageYn) {
		this.usageYn = usageYn;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getDsModelOwnerNm() {
		return dsModelOwnerNm;
	}

	public void setDsModelOwnerNm(String dsModelOwnerNm) {
		this.dsModelOwnerNm = dsModelOwnerNm;
	}

	public String getDsModelOwnerPhone() {
		return dsModelOwnerPhone;
	}

	public void setDsModelOwnerPhone(String dsModelOwnerPhone) {
		this.dsModelOwnerPhone = dsModelOwnerPhone;
	}

	public String getDsModelOwnerEmail() {
		return dsModelOwnerEmail;
	}

	public void setDsModelOwnerEmail(String dsModelOwnerEmail) {
		this.dsModelOwnerEmail = dsModelOwnerEmail;
	}

	public String getPushUri() {
		return pushUri;
	}

	public void setPushUri(String pushUri) {
		this.pushUri = pushUri;
	}

	public String getReqUserId() {
		return reqUserId;
	}

	public void setReqUserId(String reqUserId) {
		this.reqUserId = reqUserId;
	}

	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}

	public String getReqUseMethodCode() {
		return reqUseMethodCode;
	}

	public void setReqUseMethodCode(String reqUseMethodCode) {
		this.reqUseMethodCode = reqUseMethodCode;
	}

	public String getReqCreated() {
		return reqCreated;
	}

	public void setReqCreated(String reqCreated) {
		this.reqCreated = reqCreated;
	}

	public int getItrtTotalCount() {
		return itrtTotalCount;
	}

	public void setItrtTotalCount(int itrtTotalCount) {
		this.itrtTotalCount = itrtTotalCount;
	}

	public long getDsFileOid() {
		return dsFileOid;
	}

	public void setDsFileOid(long dsFileOid) {
		this.dsFileOid = dsFileOid;
	}

	public long[] getDeleteFileOids() {
		return deleteFileOids;
	}

	public void setDeleteFileOids(long[] deleteFileOids) {
		this.deleteFileOids = deleteFileOids;
	}

	public String getDsInstanceId() {
		return dsInstanceId;
	}

	public void setDsInstanceId(String dsInstanceId) {
		this.dsInstanceId = dsInstanceId;
	}

	public String getDsInstanceNm() {
		return dsInstanceNm;
	}

	public void setDsInstanceNm(String dsInstanceNm) {
		this.dsInstanceNm = dsInstanceNm;
	}

	public List<DatasetInstanceVo> getDatasetInstanceList() {
		return datasetInstanceList;
	}

	public void setDatasetInstanceList(List<DatasetInstanceVo> datasetInstanceList) {
		this.datasetInstanceList = datasetInstanceList;
	}

	public List<DatasetOutputVo> getDatasetOutputList() {
		return datasetOutputList;
	}

	public void setDatasetOutputList(List<DatasetOutputVo> datasetOutputList) {
		this.datasetOutputList = datasetOutputList;
	}

	public List<DatasetFileVo> getDatasetFileList() {
		return datasetFileList;
	}

	public void setDatasetFileList(List<DatasetFileVo> datasetFileList) {
		this.datasetFileList = datasetFileList;
	}

	public List<DatasetSearchInfoVo> getDatasetSearchInfoList() {
		return datasetSearchInfoList;
	}

	public void setDatasetSearchInfoList(List<DatasetSearchInfoVo> datasetSearchInfoList) {
		this.datasetSearchInfoList = datasetSearchInfoList;
	}

	public List<DatasetInquiryVo> getDatasetInquiryList() {
		return datasetInquiryList;
	}

	public void setDatasetInquiryList(List<DatasetInquiryVo> datasetInquiryList) {
		this.datasetInquiryList = datasetInquiryList;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getPortalType() {
		return portalType;
	}

	public void setPortalType(String portalType) {
		this.portalType = portalType;
	}

	public String getNohit() {
		return nohit;
	}

	public void setNohit(String nohit) {
		this.nohit = nohit;
	}

	public long[] getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(long[] categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItrtId() {
		return itrtId;
	}

	public void setItrtId(String itrtId) {
		this.itrtId = itrtId;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getModelNamespace() {
		return modelNamespace;
	}

	public void setModelNamespace(String modelNamespace) {
		this.modelNamespace = modelNamespace;
	}

	public String getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public List<DatasetOriginVo> getDatasetOriginList() {
		return datasetOriginList;
	}

	public void setDatasetOriginList(List<DatasetOriginVo> datasetOriginList) {
		this.datasetOriginList = datasetOriginList;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getModelTypeUri() {
		return modelTypeUri;
	}

	public void setModelTypeUri(String modelTypeUri) {
		this.modelTypeUri = modelTypeUri;
	}

	
}
