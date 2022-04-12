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
package kr.co.n2m.smartcity.admin.common.kafka;

public enum KafkaMessageCode {
	USER_DATA_REG_TO_DATAPUBLISH("user_data_reg_dpM"),
	USER_DATA_REG_TO_PRODUCT("user_data_reg_pdM"),
	USER_DATA_DEL_TO_DATAPUBLISH("user_data_del_dpM"),
	USER_DATA_DEL_TO_PRODUCT("user_data_del_pdM"),
	USER_DATA_REG_COMPLETE("user_data_reg_complete_mg"),
	PROVIDER_DATA_PUBLISH_REG_TO_DATAPUBLISH("provider_data_publish_reg_pdM"),
	PROVIDER_DATA_PUBLISH_REG_TO_PRODUCT("provider_data_publish_reg_pdM"),
	CODEGROUP_DATA_REG_SYNC("codegroup_data_reg_sync"),
	CODEGROUP_DATA_MOD_SYNC("codegroup_data_mod_sync"),
	CODE_DATA_REG_SYNC("code_data_reg_sync"),
	CODE_DATA_MOD_SYNC("code_data_mod_sync"),
	EMPTY("")
	;
	
	private String message;
	KafkaMessageCode(String message){
		this.message = message;
	}
	public String message() {
		return message;
	}
	public static KafkaMessageCode getMessageCode(String code) {
		KafkaMessageCode result = null;
		for(KafkaMessageCode kmc : KafkaMessageCode.values()) {
			if(kmc.message().equals(code)) {
				result = kmc;
			}
		}
		return result;
	}
}
