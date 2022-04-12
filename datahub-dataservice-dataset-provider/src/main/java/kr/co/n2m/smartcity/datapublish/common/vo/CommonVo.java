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
package kr.co.n2m.smartcity.datapublish.common.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonVo extends PageVo {
	private String schValue;
	private String schValue2;
	private String schValue3;
	private String schValue4;
	private String schCondition;
	private String schCondition2;
	private String schCondition3;
	private String schCondition4;
	private String sortField;
	private String sortOrder;
	private String fromDate;
	private String toDate;	
	private String loginUserId;
	
	public String getSchValue() {
		return schValue;
	}
	public void setSchValue(String schValue) {
		this.schValue = schValue;
	}
	public String getSchValue2() {
		return schValue2;
	}
	public void setSchValue2(String schValue2) {
		this.schValue2 = schValue2;
	}
	public String getSchValue3() {
		return schValue3;
	}
	public void setSchValue3(String schValue3) {
		this.schValue3 = schValue3;
	}
	public String getSchValue4() {
		return schValue4;
	}
	public void setSchValue4(String schValue4) {
		this.schValue4 = schValue4;
	}
	public String getSchCondition2() {
		return schCondition2;
	}
	public void setSchCondition2(String schCondition2) {
		this.schCondition2 = schCondition2;
	}
	public String getSchCondition3() {
		return schCondition3;
	}
	public void setSchCondition3(String schCondition3) {
		this.schCondition3 = schCondition3;
	}
	public String getSchCondition4() {
		return schCondition4;
	}
	public void setSchCondition4(String schCondition4) {
		this.schCondition4 = schCondition4;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSchCondition() {
		return schCondition;
	}
	public void setSchCondition(String schCondition) {
		this.schCondition = schCondition;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
}
