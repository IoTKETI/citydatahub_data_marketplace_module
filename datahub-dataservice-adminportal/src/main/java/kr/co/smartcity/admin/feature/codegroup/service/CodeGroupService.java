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
package kr.co.smartcity.admin.feature.codegroup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.common.util.CodeUtil;

@Service("codeGroupService")
public class CodeGroupService extends HttpComponent{
	/**
	 * 코드그룹 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String createCodeGroup(Map<String, Object> params) throws Exception {
		String uri = method(HttpMethod.POST, props.getPortalMsApiUrl() +  "/dataservice/codeGroup", params);
		refresh();
		return uri;
	}
	
	/**
	 * 코드그룹 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String modifyCodeGroup(Map<String, Object> params) throws Exception {
		String uri = method(HttpMethod.PUT, props.getPortalMsApiUrl() +  "/dataservice/codeGroup/"+params.get("codeGroupId"), params); 
		refresh();
		return uri;
	}
	
	/**
	 * 코드그룹 목록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getCodeGroupList(Map<String, Object> params) throws Exception {
		setSessionAttribute(CommonConst.SEARCH_PREFIX, "CODEGROUP_LIST", params);
		String data = get( props.getPortalMsApiUrl() + "/dataservice/codeGroup", params);
		Map<String,Object> resMap = toMap(data);
		Map<String, Object> noticeListPageInfoMap = (Map<String, Object>) resMap.get("page");
		noticeListPageInfoMap.putAll(params);
		return toJson(resMap);
	}

	/**
	 * 코드그룹 상세
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getCodeGroup(Map<String, Object> params) throws Exception {
		return get( props.getPortalMsApiUrl() + "/dataservice/codeGroup/"+params.get("codeGroupId"));
	}

	/**
	 * 코드 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String createCode(Map<String, Object> params) throws Exception {
		String uri = method(HttpMethod.POST, props.getPortalMsApiUrl() +  "/dataservice/codeGroup/"+params.get("codeGroupId")+"/codes", params);
		refresh();
		return uri;
	}

	
	/**
	 * 코드 목록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	
	public String getCodeList(Map<String, Object> params) throws Exception {
		return get( props.getPortalMsApiUrl() + "/dataservice/codeGroup/"+ params.get("codeGroupId") + "/codes", params);
	}
	
	/**
	 * 코드 상세
	 * @Author      : hk-lee
	 * @Date        : 2019. 8. 5.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getCode(Map<String, Object> params) throws Exception {
		return get( props.getPortalMsApiUrl() + "/dataservice/codeGroup/" + params.get("codeGroupId") + "/codes/" + params.get("codeId"));
	}

	/**
	 * 코드 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String modifyCode(Map<String, Object> params) throws Exception {
		String uri = method(HttpMethod.PUT, props.getPortalMsApiUrl() +  "/dataservice/codeGroup/" + params.get("codeGroupId") + "/codes/" + params.get("codeId"), params);
		refresh();
		return uri;
	}
	
	/**
	 * 전체 코드 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 16.
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAllCodeGroupList() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("all", "Y");
		
		String codeJson = get( props.getPortalMsApiUrl() + "/dataservice/codeGroup",params);
		
		Map<String, Object> codeMap = customGson().fromJson(codeJson, Map.class);
		return (List<Map<String, Object>>) codeMap.get("codeGroupList");
		
	}
	
	
	/**
	 * 코드 메모리 갱신
	 * @throws Exception 
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 16.
	 */
	public void refresh() throws Exception {
		CodeUtil.setCode(this.getAllCodeGroupList());
	}

	
	/**
	 * 코드그룹ID 중복체크
	 * @Author      : hk-lee
	 * @Date        : 2019. 8. 14.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String checkCodeGroupId(Map<String, Object> params) throws Exception {
		return get( props.getPortalMsApiUrl() + "/dataservice/codeGroup/check", params);
	}
	
	/**
	 * 코드ID 중복체크
	 * @Author      : hk-lee
	 * @Date        : 2019. 8. 14.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String checkCodeId(Map<String, Object> params) throws Exception {
		return get( props.getPortalMsApiUrl() + "/dataservice/codeGroup/" + params.get("codeGroupId") + "/codes/check", params);
	}
}
