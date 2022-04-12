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
package kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor;

import java.util.List;

import org.apache.ibatis.type.Alias;

import kr.co.n2m.smartcity.datapublish.common.vo.CommonVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("srchDatasetAdaptorVo")
public class SrchDatasetAdaptorVo extends CommonVo{
	private long id;                               //어댑터신청고유번호
	private String status;                         //어댑터신청상태코드
	private String statusName;                     //어댑터신청상태코드명
	private String creatorId;                      //등록자
	private String transferType;                   //전송방식
	private String description;                    //전송방식설명
	private String createdAt;                      //등록일시
	private String modelId;                        //데이터모델ID
	private String title;                          //어댑터신청제목
	private String content;                        //어댑터신청내용
	private String modifiedAt;                     //수정일시
	private List<DatasetAdaptorFieldVo> fieldList; //어댑터 데이터필드목록
	
}
