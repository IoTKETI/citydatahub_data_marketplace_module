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

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("datasetVo")
public class DatasetVo {
	private String id;                     //데이터셋 고유번호
	private String title;                  //데이터셋 제목
	private String status;                 //데이터셋 상태코드
	private String statusName;             //데이터셋 상태코드명
	private long categoryId;               //카테고리고유번호
	private String categoryName;           //카테고리명
	private String description;            //데이터셋 설명
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
	private String license;                //라이선스코드
	private String licenseName;            //라이선스코드명
	private String constraints;            //제약사항
	private String extension;              //확장자코드
	private String extensionName;          //확장자코드명
	private String dataType;               //데이터유형코드
	private String dataTypeName;           //데이터유형코드명
	private String ownership;              //소유권
	private String retrievalCount;         //조회수
	private String searchType;             //검색조건

	private String modelId;				// 데이터모델ID
	private String modelName;				// 데이터모델명
}
