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
package kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage;

import org.apache.ibatis.type.Alias;

import kr.co.n2m.smartcity.datapublish.common.vo.CommonVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("srchDatasetUseRequestLogVo")
public class SrchDatasetUseRequestLogVo extends CommonVo {
	private long id;                   //이용신청 고유번호
	private String datasetId;          //데이터셋 고유번호
	private String userId;             //신청자ID
	private boolean realtimeTf;        //실시간 수신여부
	private String protocol;           //수신 방식(HTTP,MQTT,WEBSOCKET)
	private String url;                //수신 주소
	private String trafficType;        //트래픽유형
	private int limit;                 //일일제한량
	private String expiredAt;          //제공만료일
	private int price;                 //결제 가격
	private String payStatus;          //결제 진행상태
	private String completedAt;        //결제완료일시
	private String status;             //환불요청 상태
	private String reason;             //환불요청 사유
	private String updateAt;           //환불 완료일시
	private String cancelAt;           //해지일시
}
