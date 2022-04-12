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
package kr.co.n2m.smartcity.admin.feature.datacomplaint.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Alias("datacomplaintFileVo")
public class DatacomplaintFileVo {
	private long dcpFileOid;
	private long datacomplainOid;
	private String dcpFileOrgNm;
	private String dcpFileSavedNm;
	private String dcpFileSavePath;
	private int dcpFileSizeByte;
	private String dcpFileTypeAnswer; // 답변  첨부파일 : Y 질문 첨부파일 :N
	private String dcpFileId;
	
	public long getDcpFileOid() {
		return dcpFileOid;
	}
	public void setDcpFileOid(long dcpFileOid) {
		this.dcpFileOid = dcpFileOid;
	}
	public long getDatacomplainOid() {
		return datacomplainOid;
	}
	public void setDatacomplainOid(long datacomplainOid) {
		this.datacomplainOid = datacomplainOid;
	}
	public String getDcpFileOrgNm() {
		return dcpFileOrgNm;
	}
	public void setDcpFileOrgNm(String dcpFileOrgNm) {
		this.dcpFileOrgNm = dcpFileOrgNm;
	}
	public String getDcpFileSavedNm() {
		return dcpFileSavedNm;
	}
	public void setDcpFileSavedNm(String dcpFileSavedNm) {
		this.dcpFileSavedNm = dcpFileSavedNm;
	}
	public String getDcpFileSavePath() {
		return dcpFileSavePath;
	}
	public void setDcpFileSavePath(String dcpFileSavePath) {
		this.dcpFileSavePath = dcpFileSavePath;
	}
	public int getDcpFileSizeByte() {
		return dcpFileSizeByte;
	}
	public void setDcpFileSizeByte(int dcpFileSizeByte) {
		this.dcpFileSizeByte = dcpFileSizeByte;
	}
	public String getDcpFileTypeAnswer() {
		return dcpFileTypeAnswer;
	}
	public void setDcpFileTypeAnswer(String dcpFileTypeAnswer) {
		this.dcpFileTypeAnswer = dcpFileTypeAnswer;
	}
	public String getDcpFileId() {
		return dcpFileId;
	}
	public void setDcpFileId(String dcpFileId) {
		this.dcpFileId = dcpFileId;
	} 
	
	
}
