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
package kr.co.smartcity.admin.feature.category.vo;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("categoryVo")
public class CategoryVo {
	private long categoryOid;
	private String categoryId;
	private long categoryParentOid;
	private String categoryNm;			//부모카테고리OID
	private String categoryParentNm;	//부모카테고리명
	private String categoryDesc;
	private LocalDateTime categoryCreDt;
	private String categoryCreUsrId;
	private String categoryCreUsrNm;
	private String categoryImgOrgNm;
	private String categoryImgSaveNm;
	private String categoryImgSavePath;
	private int categoryImgSize;
	private String categoryUptUsrId;
	private LocalDateTime categoryUptDt;
	private List<Long> deleteFileOids;
	
	public long getCategoryOid() {
		return categoryOid;
	}
	public void setCategoryOid(long categoryOid) {
		this.categoryOid = categoryOid;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public long getCategoryParentOid() {
		return categoryParentOid;
	}
	public void setCategoryParentOid(long categoryParentOid) {
		this.categoryParentOid = categoryParentOid;
	}
	public String getCategoryNm() {
		return categoryNm;
	}
	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}
	public String getCategoryParentNm() {
		return categoryParentNm;
	}
	public void setCategoryParentNm(String categoryParentNm) {
		this.categoryParentNm = categoryParentNm;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	public LocalDateTime getCategoryCreDt() {
		return categoryCreDt;
	}
	public void setCategoryCreDt(LocalDateTime categoryCreDt) {
		this.categoryCreDt = categoryCreDt;
	}
	public String getCategoryCreUsrId() {
		return categoryCreUsrId;
	}
	public void setCategoryCreUsrId(String categoryCreUsrId) {
		this.categoryCreUsrId = categoryCreUsrId;
	}
	public String getCategoryCreUsrNm() {
		return categoryCreUsrNm;
	}
	public void setCategoryCreUsrNm(String categoryCreUsrNm) {
		this.categoryCreUsrNm = categoryCreUsrNm;
	}
	public String getCategoryImgOrgNm() {
		return categoryImgOrgNm;
	}
	public void setCategoryImgOrgNm(String categoryImgOrgNm) {
		this.categoryImgOrgNm = categoryImgOrgNm;
	}
	public String getCategoryImgSaveNm() {
		return categoryImgSaveNm;
	}
	public void setCategoryImgSaveNm(String categoryImgSaveNm) {
		this.categoryImgSaveNm = categoryImgSaveNm;
	}
	public String getCategoryImgSavePath() {
		return categoryImgSavePath;
	}
	public void setCategoryImgSavePath(String categoryImgSavePath) {
		this.categoryImgSavePath = categoryImgSavePath;
	}
	public int getCategoryImgSize() {
		return categoryImgSize;
	}
	public void setCategoryImgSize(int categoryImgSize) {
		this.categoryImgSize = categoryImgSize;
	}
	public String getCategoryUptUsrId() {
		return categoryUptUsrId;
	}
	public void setCategoryUptUsrId(String categoryUptUsrId) {
		this.categoryUptUsrId = categoryUptUsrId;
	}
	public LocalDateTime getCategoryUptDt() {
		return categoryUptDt;
	}
	public void setCategoryUptDt(LocalDateTime categoryUptDt) {
		this.categoryUptDt = categoryUptDt;
	}
	public List<Long> getDeleteFileOids() {
		return deleteFileOids;
	}
	public void setDeleteFileOids(List<Long> deleteFileOids) {
		this.deleteFileOids = deleteFileOids;
	}
	@Override
	public String toString() {
		return "CategoryVo [categoryOid=" + categoryOid + ", categoryParentOid=" + categoryParentOid + ", categoryNm="
				+ categoryNm + ", categoryParentNm=" + categoryParentNm + ", categoryDesc=" + categoryDesc
				+ ", categoryCreDt=" + categoryCreDt + ", categoryCreUsrId=" + categoryCreUsrId + ", categoryCreUsrNm="
				+ categoryCreUsrNm + ", categoryImgOrgNm=" + categoryImgOrgNm + ", categoryImgSaveNm="
				+ categoryImgSaveNm + ", categoryImgSavePath=" + categoryImgSavePath + ", categoryImgSize="
				+ categoryImgSize + ", categoryUptUsrId=" + categoryUptUsrId + ", categoryUptDt=" + categoryUptDt
				+ ", deleteFileOids=" + deleteFileOids + "]";
	}
		
}
