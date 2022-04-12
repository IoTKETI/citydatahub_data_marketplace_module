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
package kr.co.n2m.smartcity.datapublish.feature.dataset.vo.sample;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("simpleDatasetVo")
public class SimpleDatasetVo {
	private String dsProductOid;       //데이터셋 고유번호(출시용필수)
	private String dsOid;              //데이터셋 고유번호(필수)
	private String dsTitle;            //데이터셋 제목(필수)
	private String dsCreateUserId;     //등록자(필수)
	private String dsCreated;          //등록일시(필수)
	private String dsStatus;           //데이터셋 상태코드(코드)
	private String dsStatusNm;         //데이터셋 상태코드(라벨)
	private String dsOfferType;        //제공유형(코드)
	private String dsOfferTypeNm;      //제공유형(라벨)
	private String dsExtension;        //확장자(코드)
	private String dsExtensionNm;      //확장자(라벨)
	private String dsDataType;         //데이터유형(코드)
	private String dsDataTypeNm;       //데이터유형(라벨)
	private long dsCategoryOid;        //카테고리고유번호
	private String dsCategoryNm;        //카테고리명
	private long[] dsCategoryOidList;  //카테고리고유번호리스트
	private String dsDesc;             //데이터셋 설명
	private String dsProviderId;       //데이터소유자
	private String dsUpdateUserId;     //수정자
	private String dsUpdated;          //수정일시
	private String dsUpdateCycle;      //갱신주기
	private String dsProvider;         //제공기관
	private String dsProvideSystem;    //제공시스템
	private String dsKeyword;          //키워드
	private String dsLicense;          //라이선스
	private String dsConstraints;      //제약사항
	private String dsOwnership;        //소유권
	private int dsHits;                //조회수
//	private String dsModelType;
//	private String dsModelVersion;
//	private String dsModelNamespace;
	
	private String dsModelId;			// 모델ID
	private String dsModelName;			// 모델명
	
	/**
	 * 데이터셋 소유자 상세정보
	 */
	private String dsModelOwnerNm;
	private String dsModelOwnerPhone;
	private String dsModelOwnerEmail;
	
	
}
