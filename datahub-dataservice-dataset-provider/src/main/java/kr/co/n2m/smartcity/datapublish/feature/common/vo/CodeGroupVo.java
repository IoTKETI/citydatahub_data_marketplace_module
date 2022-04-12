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
package kr.co.n2m.smartcity.datapublish.feature.common.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import kr.co.n2m.smartcity.datapublish.common.vo.CommonVo;

@Alias("codeGroupVo")
public class CodeGroupVo extends CommonVo{
	
	private String codeGroupId;		//코드그룹ID
	private String codeGroupName;	//코드그룹명
	private String codeGroupUseFl;	//사용여부
	private int codeGroupCol;		//순서
	private String codeGroupDesc;	//설명
	private String codeGroupCreDt;
	private String codeGroupCreUsrId;
	private String codeGroupUptDt;
	private String codeGroupUptUsrId;
	private List<CodeVo> codeList;
	
	public String getCodeGroupId() {
		return codeGroupId;
	}
	public void setCodeGroupId(String codeGroupId) {
		this.codeGroupId = codeGroupId;
	}
	public String getCodeGroupName() {
		return codeGroupName;
	}
	public void setCodeGroupName(String codeGroupName) {
		this.codeGroupName = codeGroupName;
	}
	public String getCodeGroupUseFl() {
		return codeGroupUseFl;
	}
	public void setCodeGroupUseFl(String codeGroupUseFl) {
		this.codeGroupUseFl = codeGroupUseFl;
	}
	public int getCodeGroupCol() {
		return codeGroupCol;
	}
	public void setCodeGroupCol(int codeGroupCol) {
		this.codeGroupCol = codeGroupCol;
	}
	public String getCodeGroupDesc() {
		return codeGroupDesc;
	}
	public void setCodeGroupDesc(String codeGroupDesc) {
		this.codeGroupDesc = codeGroupDesc;
	}
	public String getCodeGroupCreDt() {
		return codeGroupCreDt;
	}
	public void setCodeGroupCreDt(String codeGroupCreDt) {
		this.codeGroupCreDt = codeGroupCreDt;
	}
	public String getCodeGroupCreUsrId() {
		return codeGroupCreUsrId;
	}
	public void setCodeGroupCreUsrId(String codeGroupCreUsrId) {
		this.codeGroupCreUsrId = codeGroupCreUsrId;
	}
	public String getCodeGroupUptDt() {
		return codeGroupUptDt;
	}
	public void setCodeGroupUptDt(String codeGroupUptDt) {
		this.codeGroupUptDt = codeGroupUptDt;
	}
	public String getCodeGroupUptUsrId() {
		return codeGroupUptUsrId;
	}
	public void setCodeGroupUptUsrId(String codeGroupUptUsrId) {
		this.codeGroupUptUsrId = codeGroupUptUsrId;
	}
	public List<CodeVo> getCodeList() {
		return codeList;
	}
	public void setCodeList(List<CodeVo> codeList) {
		this.codeList = codeList;
	}
	
	
	
}
