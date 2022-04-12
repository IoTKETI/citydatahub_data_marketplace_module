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
package kr.co.n2m.smartcity.admin.feature.faq.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("faqVo")
public class FaqVo{
	private String faqCreUsrNm; //joined 작성자 이름
	
	private long faqOid;
	private int faqCnt;
	private String faqId;
	private String faqTitle;
	private String faqCreDt;
	private String faqUptDt;
	private String faqAnswer;
	private String faqQuestion;
	private String faqCreUsrId;
	private String faqUptUsrId;
	
	private List<FaqFileVo> fileList;
	
	public long getFaqOid() {
		return faqOid;
	}
	public void setFaqOid(long faqOid) {
		this.faqOid = faqOid;
	}
	public String getFaqId() {
		return faqId;
	}
	public void setFaqId(String faqId) {
		this.faqId = faqId;
	}
	public List<FaqFileVo> getFileList() {
		return fileList;
	}
	public void setFileList(List<FaqFileVo> fileList) {
		this.fileList = fileList;
	}
	public String getFaqCreUsrNm() {
		return faqCreUsrNm;
	}
	public void setFaqCreUsrNm(String faqCreUsrNm) {
		this.faqCreUsrNm = faqCreUsrNm;
	}
	public int getFaqCnt() {
		return faqCnt;
	}
	public void setFaqCnt(int faqCnt) {
		this.faqCnt = faqCnt;
	}
	public String getFaqTitle() {
		return faqTitle;
	}
	public void setFaqTitle(String faqTitle) {
		this.faqTitle = faqTitle;
	}
	public String getFaqCreDt() {
		return faqCreDt;
	}
	public void setFaqCreDt(String faqCreDt) {
		this.faqCreDt = faqCreDt;
	}
	public String getFaqUptDt() {
		return faqUptDt;
	}
	public void setFaqUptDt(String faqUptDt) {
		this.faqUptDt = faqUptDt;
	}
	public String getFaqAnswer() {
		return faqAnswer;
	}
	public void setFaqAnswer(String faqAnswer) {
		this.faqAnswer = faqAnswer;
	}
	public String getFaqQuestion() {
		return faqQuestion;
	}
	public void setFaqQuestion(String faqQuestion) {
		this.faqQuestion = faqQuestion;
	}
	public String getFaqCreUsrId() {
		return faqCreUsrId;
	}
	public void setFaqCreUsrId(String faqCreUsrId) {
		this.faqCreUsrId = faqCreUsrId;
	}
	public String getFaqUptUsrId() {
		return faqUptUsrId;
	}
	public void setFaqUptUsrId(String faqUptUsrId) {
		this.faqUptUsrId = faqUptUsrId;
	}
	
}
