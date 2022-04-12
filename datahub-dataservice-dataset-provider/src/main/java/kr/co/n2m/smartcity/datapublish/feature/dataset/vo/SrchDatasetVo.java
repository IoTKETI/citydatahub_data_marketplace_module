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
package kr.co.n2m.smartcity.datapublish.feature.dataset.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import kr.co.n2m.smartcity.datapublish.common.vo.CommonVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.attachFiles.SrchDatasetFileVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.SrchDatasetOutputVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetOrigin.SrchDatasetOriginVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.instance.SrchDatasetInstanceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.SrchDatasetSearchInfoVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("srchDatasetVo")
public class SrchDatasetVo extends CommonVo{
	private String id;                     //데이터셋 고유번호
	private String title;                  //데이터셋 제목
	private String status;                 //데이터셋 상태코드
	private String statusName;             //데이터셋 상태코드명
	private long categoryId;               //카테고리고유번호
	private String categoryName;           //카테고리명
	private String description;            //데이터셋 설명
	private String modelId;                //데이터모델ID
	private String modelName;              //데이터모델명
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
	private String retrievalCount;         //조회수

	private List<Long> categoryOidList;  //카테고리고유번호리스트
	
	/**
	 * 데이터셋 소유자 상세정보
	 */
	private String dsModelOwnerNm;
	private String dsModelOwnerPhone;
	private String dsModelOwnerEmail;
	
	/**
	 * 이용신청
	 */
	private String pushUri;             //PUSH URI주소
	private String reqUserId;           //신청자
	private String reqStatus;           //신청상태
	private String reqUseMethodCode;    //활용방법코드
	private String reqCreated;          //등록일시
	
	/**
	 * 관심상품
	 */
	private String itrtReqUserId;       //신청자
	private String itrtCreated;         //등록일시
	private int itrtTotalCount;      //관심상품등록수
	
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
	
	private List<SrchDatasetOriginVo> datasetOriginList;         // 데이터셋ORIGIN 리스트
	private List<SrchDatasetInstanceVo> datasetInstanceList;     // 인스턴스정보 리스트
	private List<SrchDatasetOutputVo> datasetOutputList;         // 출력정보 리스트
	private List<SrchDatasetFileVo> datasetFileList;             // 파일정보 리스트
	private List<SrchDatasetSearchInfoVo> datasetSearchInfoList; // 조회조건 리스트
	
	private String reqType;			//출시요청구분자
	private String portalType;		//포탈구분자
	private String nohit;			//조회수 증가여부
	
	private String searchType;
	private String searchTitle;
	private String searchStatus;
	
}
