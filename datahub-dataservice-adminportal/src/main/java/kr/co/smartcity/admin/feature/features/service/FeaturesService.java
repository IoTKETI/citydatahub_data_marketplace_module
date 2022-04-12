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
package kr.co.smartcity.admin.feature.features.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.common.component.Properties;
import kr.co.smartcity.admin.common.util.CodeUtil;
	
@Service("featuresService")
public class FeaturesService extends HttpComponent {
	
	@Autowired Properties properties;
	
	/**
	 * <pre>확장기능 관리 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getFeaturesList() throws Exception {
		String json = get(properties.getPortalMsApiUrl() + "/dataserviceUi/features");
		List<Map<String, Object>> featuresList = toList(json);
		List<Map<String, Object>> codeList = CodeUtil.getCodeList("CG_ID_FEATURES_CODE");
		List<Map<String, Object>> newFeaturesList = new ArrayList<>();
		
		for(Map<String, Object> codeMap : codeList) {
			Map<String, Object> newFeaturesMap = new HashMap<>();
			newFeaturesMap.put("featureNm", codeMap.get("codeName"));
			newFeaturesMap.put("featureCd", codeMap.get("codeId"));
			newFeaturesMap.put("enabledTf", false);
			
			for(Map<String, Object> featuresMap : featuresList) {
				if(codeMap.get("codeId").equals(featuresMap.get("featureCd"))) {
					newFeaturesMap.put("enabledTf", featuresMap.get("enabledTf"));
				}
			}
			newFeaturesList.add(newFeaturesMap);
		}
		
		return newFeaturesList;
	}
	
	/**
	 * <pre>확장기능 관리 등록(수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void modifyFeatures(Map<String, Object> params) throws Exception {
		if(params.containsKey("features")) {
			List<Map<String, Object>> featuresList = (List<Map<String, Object>>) params.get("features");
			for(Map<String, Object> feature : featuresList) {
				method(HttpMethod.PUT, properties.getPortalMsApiUrl() + "/dataserviceUi/features", feature);
			}
		}
	}
	
}
