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

import org.apache.ibatis.type.Alias;

import kr.co.n2m.smartcity.datapublish.common.vo.CommonVo;

@Alias("codeVo")
public class CodeVo extends CommonVo{
	private String codeGroupId;
	private String codeId;
	private String codeName;
	private String codeUseFl;
	private int codeSort;
	private String codeDesc;
	private String codeCreDt;
	private String codeCreUsrId;
	private String codeUptDt;
	private String codeUptUsrId;
	
	public String getCodeGroupId() {
		return codeGroupId;
	}
	public void setCodeGroupId(String codeGroupId) {
		this.codeGroupId = codeGroupId;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCodeUseFl() {
		return codeUseFl;
	}
	public void setCodeUseFl(String codeUseFl) {
		this.codeUseFl = codeUseFl;
	}
	public String getCodeCreDt() {
		return codeCreDt;
	}
	public void setCodeCreDt(String codeCreDt) {
		this.codeCreDt = codeCreDt;
	}
	public String getCodeCreUsrId() {
		return codeCreUsrId;
	}
	public void setCodeCreUsrId(String codeCreUsrId) {
		this.codeCreUsrId = codeCreUsrId;
	}
	public int getCodeSort() {
		return codeSort;
	}
	public void setCodeSort(int codeSort) {
		this.codeSort = codeSort;
	}
	public String getCodeDesc() {
		return codeDesc;
	}
	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}
	public String getCodeUptUsrId() {
		return codeUptUsrId;
	}
	public void setCodeUptUsrId(String codeUptUsrId) {
		this.codeUptUsrId = codeUptUsrId;
	}
	public String getCodeUptDt() {
		return codeUptDt;
	}
	public void setCodeUptDt(String codeUptDt) {
		this.codeUptDt = codeUptDt;
	}
	
	@Override
	public String toString() {
		return "CodeVo [codeGroupId=" + codeGroupId + ", codeId=" + codeId + ", codeName=" + codeName + ", codeUseFl="
				+ codeUseFl + ", codeSort=" + codeSort + ", codeDesc=" + codeDesc + ", codeCreDt=" + codeCreDt
				+ ", codeCreUsrId=" + codeCreUsrId + ", codeUptDt=" + codeUptDt + ", codeUptUsrId=" + codeUptUsrId
				+ "]";
	}
	
}
