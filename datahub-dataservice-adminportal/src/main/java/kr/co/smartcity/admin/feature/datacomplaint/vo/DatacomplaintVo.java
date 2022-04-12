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
package kr.co.smartcity.admin.feature.datacomplaint.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("datacomplaintVo")
public class DatacomplaintVo {
	private String datacomplainCreUsrNm; //joined 작성자 이름
	private String datacomplainAnswerUsrNm; //joined 답변 작성자 이름
	private String datacomplainGbCdNm;//코드값
	private String datacomplainStatCdNm;
	
	private long datacomplainOid;
	private String datacomplainId;
	private int datacomplainCnt;
	private String datacomplainTitle;
	private String datacomplainGbCd;
	private String datacomplainCreDt;
	private String datacomplainAnswer;
	private String datacomplainStatCd;
	private String datacomplainContent;
	private String datacomplainAnswerDt;
	private String datacomplainCreUsrId;
	private String datacomplainAnswerUsrId;
	
	private List<Integer> deleteFileOids;
	
	
	public String getDatacomplainId() {
		return datacomplainId;
	}
	public void setDatacomplainId(String datacomplainId) {
		this.datacomplainId = datacomplainId;
	}
	public long getDatacomplainOid() {
		return datacomplainOid;
	}
	public void setDatacomplainOid(long datacomplainOid) {
		this.datacomplainOid = datacomplainOid;
	}
	public String getDatacomplainAnswerUsrNm() {
		return datacomplainAnswerUsrNm;
	}
	public void setDatacomplainAnswerUsrNm(String datacomplainAnswerUsrNm) {
		this.datacomplainAnswerUsrNm = datacomplainAnswerUsrNm;
	}
	public List<Integer> getDeleteFileOids() {
		return deleteFileOids;
	}
	public void setDeleteFileOids(List<Integer> deleteFileOids) {
		this.deleteFileOids = deleteFileOids;
	}
	public String getDatacomplainGbCdNm() {
		return datacomplainGbCdNm;
	}
	public void setDatacomplainGbCdNm(String datacomplainGbCdNm) {
		this.datacomplainGbCdNm = datacomplainGbCdNm;
	}
	public String getDatacomplainStatCdNm() {
		return datacomplainStatCdNm;
	}
	public void setDatacomplainStatCdNm(String datacomplainStatCdNm) {
		this.datacomplainStatCdNm = datacomplainStatCdNm;
	}
	public String getDatacomplainCreUsrNm() {
		return datacomplainCreUsrNm;
	}
	public void setDatacomplainCreUsrNm(String datacomplainCreUsrNm) {
		this.datacomplainCreUsrNm = datacomplainCreUsrNm;
	}
	
	public int getDatacomplainCnt() {
		return datacomplainCnt;
	}
	public void setDatacomplainCnt(int datacomplainCnt) {
		this.datacomplainCnt = datacomplainCnt;
	}
	public String getDatacomplainTitle() {
		return datacomplainTitle;
	}
	public void setDatacomplainTitle(String datacomplainTitle) {
		this.datacomplainTitle = datacomplainTitle;
	}
	public String getDatacomplainGbCd() {
		return datacomplainGbCd;
	}
	public void setDatacomplainGbCd(String datacomplainGbCd) {
		this.datacomplainGbCd = datacomplainGbCd;
	}
	public String getDatacomplainCreDt() {
		return datacomplainCreDt;
	}
	public void setDatacomplainCreDt(String datacomplainCreDt) {
		this.datacomplainCreDt = datacomplainCreDt;
	}
	public String getDatacomplainAnswer() {
		return datacomplainAnswer;
	}
	public void setDatacomplainAnswer(String datacomplainAnswer) {
		this.datacomplainAnswer = datacomplainAnswer;
	}
	public String getDatacomplainStatCd() {
		return datacomplainStatCd;
	}
	public void setDatacomplainStatCd(String datacomplainStatCd) {
		this.datacomplainStatCd = datacomplainStatCd;
	}
	public String getDatacomplainContent() {
		return datacomplainContent;
	}
	public void setDatacomplainContent(String datacomplainContent) {
		this.datacomplainContent = datacomplainContent;
	}
	public String getDatacomplainAnswerDt() {
		return datacomplainAnswerDt;
	}
	public void setDatacomplainAnswerDt(String datacomplainAnswerDt) {
		this.datacomplainAnswerDt = datacomplainAnswerDt;
	}
	public String getDatacomplainCreUsrId() {
		return datacomplainCreUsrId;
	}
	public void setDatacomplainCreUsrId(String datacomplainCreUsrId) {
		this.datacomplainCreUsrId = datacomplainCreUsrId;
	}
	public String getDatacomplainAnswerUsrId() {
		return datacomplainAnswerUsrId;
	}
	public void setDatacomplainAnswerUsrId(String datacomplainAnswerUsrId) {
		this.datacomplainAnswerUsrId = datacomplainAnswerUsrId;
	}
	
	
}
