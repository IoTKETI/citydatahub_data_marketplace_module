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
package kr.co.smartcity.admin.feature.analy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;

@Service("analyService")
public class AnalyService extends HttpComponent{

	/**
	 * <pre>분석가 승인 목록 조회</pre>
	 * @Author      : thlee
	 * @Date        : 2019. 10. 10.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getAnalyList(Map<String, Object> params) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		setSessionAttribute(CommonConst.SEARCH_PREFIX, "ANALY_LIST", params);
		resMap = toMap(get( props.getDatapublishMsApiUrl() + "/dataservice/analysisApprovalRequests", params));

		Map<String, String> keyPairMap = new HashMap<>();
		keyPairMap.put("creatorId", "creatorName");
		putUserNameForMap(resMap, keyPairMap);
		
		return toJson(resMap);
	}
	
	/**
	 * <pre>분석가 승인 상태 수정</pre>
	 * @Author      : thlee
	 * @Date        : 2019. 10. 10.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String modifyAnaly(Map<String, Object> params) throws Exception {
		
		String id = String.valueOf(params.get("id"));
		
		String resultStr = this.method( HttpMethod.PUT, props.getDatapublishMsApiUrl() + "/dataservice/analysisApprovalRequests/" + id, params);
		
		// 승인완료 시 분석가요청자의 분석 권한(Role) 을 부여 한다. (Analytics_User)
		Map<String, Object> requestData = new HashMap<String, Object>();
		requestData.put("userId"	, params.get("requestor"));
		requestData.put("RoleName"	, "Analytics_User");

		try {
			this.authMethod( HttpMethod.POST, props.getUserRoleApiUrl(), requestData);
		} catch (Exception ex) {
			// start rollback
			params.put("status", "apprWait");

			this.method( HttpMethod.PUT, props.getDatapublishMsApiUrl() + "/dataservice/analysisApprovalRequests/" + id, params);
			// end rollback. finally throw exception
			
			throw ex;
		}
		return resultStr;
	}

	/**
	 * <pre>분석가 승인 상세 조회</pre>
	 * @Author      : thlee
	 * @Date        : 2019. 11. 25.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getAnaly(Map<String, Object> params) throws Exception {
		Map<String, Object> analyData = null;
		List<Map<String, Object>> analyModelList = null;
		try {
			// 데이터분석가요청 상세 데이터(분석가요청 데이터 + 등록된 데이터 모델)
			analyData      = toMap(get(props.getDatapublishMsApiUrl() + "/dataservice/analysisApprovalRequests/" + params.get("id"), params));
			params.put("analystId", params.get("id"));
			analyModelList = toList(get(props.getDatapublishMsApiUrl() + "/dataservice/analysisApprovalRequests/" + params.get("id") + "/dataModel", params));
			analyData.put("dataModelList", analyModelList);
			

			Map<String, String> keyPairMap = new HashMap<>();
			keyPairMap.put("creatorId", "creatorName");
			putUserNameForMap(analyData, keyPairMap);
			
			// 데이터 모델 리스트
			List<Map<String, Object>> datacoreModelList = this.toList(this.get(props.getDataModelApiUrl()));
			
			if (!analyData.isEmpty() && !analyModelList.isEmpty()) {
				for (Map<String, Object> analyDatamodel : analyModelList) {
					for (Map<String, Object> datamodel : datacoreModelList) {
						if (analyDatamodel.get("modelId").equals(datamodel.get("id"))) {
							analyDatamodel.put("modelName", datamodel.get("typeUri"));
						}
					}
				}
			}
		}catch(Exception e) {
			throw e;
		}
		
		return toJson(analyData);
	}
}
