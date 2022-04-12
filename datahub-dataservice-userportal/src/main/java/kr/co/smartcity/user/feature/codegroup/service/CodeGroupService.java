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
package kr.co.smartcity.user.feature.codegroup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.smartcity.user.common.component.HttpComponent;
import kr.co.smartcity.user.common.util.CodeUtil;

@Service("codeGroupService")
public class CodeGroupService extends HttpComponent{
	
	/**
	 * 전체 코드 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 16.
	 * @return
	 * @throws Exception 
	 */
	public String getAllCodeGroupList() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("all", "Y");
		return get( props.getPortalMsApiUrl() + "/dataservice/codeGroup",params);
	}
	
	/**
	 * 코드 메모리 갱신
	 * @throws Exception 
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 16.
	 */
	@SuppressWarnings("unchecked")
	public void refresh() throws Exception {
		String codeJson = getAllCodeGroupList();
		Map<String, Object> codeMap = toMap(codeJson);
		List<Map<String, Object>> codes = (List<Map<String, Object>>) codeMap.get("codeGroupList");
		CodeUtil.setCode(codes);
	}

	/**
	 * 코드 목록 조회
	 * @Author      : thlee
	 * @Date        : 2019. 10. 14.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getCodeList(Map<String, String> params) throws Exception {
		return get( props.getPortalMsApiUrl() + "/code", params);
	}

	
}
