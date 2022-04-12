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
package kr.co.smartcity.admin.feature.dataset.vo;

import org.apache.ibatis.type.Alias;

@Alias("datasetInquiryVo")
public class DatasetInquiryVo {
	/**
	 * 문의하기
	 */
	private String dsOid;               //데이터셋 고유번호
	private long inqOid;              //문의고유번호
	private String inqQuestionContent;  //문의내용
	private String inqAnswerContent;    //답변내용
	private String inqSecretYn;         //비밀글여부
	private String inqStatus;           //문의글상태(코드)
	private String inqStatusNm;         //문의글상태(라벨)
	private String inqCreateUserId;     //등록자
	private String inqCreated;          //등록일시
	private String inqAnswerUserId;     //답변자
	private String inqAnswerDate;       //답변등록일시
	
	public String getDsOid() {
		return dsOid;
	}
	public void setDsOid(String dsOid) {
		this.dsOid = dsOid;
	}
	public long getInqOid() {
		return inqOid;
	}
	public void setInqOid(long inqOid) {
		this.inqOid = inqOid;
	}
	public String getInqQuestionContent() {
		return inqQuestionContent;
	}
	public void setInqQuestionContent(String inqQuestionContent) {
		this.inqQuestionContent = inqQuestionContent;
	}
	public String getInqAnswerContent() {
		return inqAnswerContent;
	}
	public void setInqAnswerContent(String inqAnswerContent) {
		this.inqAnswerContent = inqAnswerContent;
	}
	public String getInqSecretYn() {
		return inqSecretYn;
	}
	public void setInqSecretYn(String inqSecretYn) {
		this.inqSecretYn = inqSecretYn;
	}
	public String getInqStatus() {
		return inqStatus;
	}
	public void setInqStatus(String inqStatus) {
		this.inqStatus = inqStatus;
	}
	public String getInqCreateUserId() {
		return inqCreateUserId;
	}
	public void setInqCreateUserId(String inqCreateUserId) {
		this.inqCreateUserId = inqCreateUserId;
	}
	public String getInqCreated() {
		return inqCreated;
	}
	public void setInqCreated(String inqCreated) {
		this.inqCreated = inqCreated;
	}
	public String getInqAnswerUserId() {
		return inqAnswerUserId;
	}
	public void setInqAnswerUserId(String inqAnswerUserId) {
		this.inqAnswerUserId = inqAnswerUserId;
	}
	public String getInqAnswerDate() {
		return inqAnswerDate;
	}
	public void setInqAnswerDate(String inqAnswerDate) {
		this.inqAnswerDate = inqAnswerDate;
	}
	public String getInqStatusNm() {
		return inqStatusNm;
	}
	public void setInqStatusNm(String inqStatusNm) {
		this.inqStatusNm = inqStatusNm;
	}
	
}
