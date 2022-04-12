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
package kr.co.n2m.smartcity.datapublish.common.retrofit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.DatasetOutputVo;

@Service
public class ThreadRetrofitService {
	private final String[] arrIgnoreKey = {};

	public String getConvertData(List<DatasetOutputVo> outputList, Map<String, Object> mParam) throws Exception {
        String strResultData = null;
        String[] arrDefaultKey = new String[] {"id", "type", "createdAt", "modifiedAt"};
        
        Gson gsonObj = new Gson();
        String jsonStr = gsonObj.toJson(mParam);
        JsonArray jsonArray = new JsonArray();
        
        
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(jsonStr);
        jsonArray.add(obj);
    	strResultData = this.convertJson(outputList, jsonArray, arrDefaultKey);

        return strResultData;
	}

	private String convertJson(List<DatasetOutputVo> outputList, JsonArray procJsonArray, String[] arrDefaultKey) throws ExecutionException, InterruptedException {
		int iDataLength = procJsonArray.size();
		
		JsonArray rtnJsonArray = new JsonArray();

		JsonObject jObjOrg = null;
		JsonObject jObjTmp = null;

		List<String> listJsonKeySet = null;

		//JsonData 처리
		for (int i = 0; i < iDataLength; i++) {
			jObjOrg = (JsonObject) procJsonArray.get(i);
			jObjTmp = new JsonObject();

			listJsonKeySet = new ArrayList<>(jObjOrg.keySet());
			for (String strKey : listJsonKeySet) {

				// 추가되면 안되는 Element 체크
				if (arrIgnoreKey.length > 0) { if (Arrays.stream(arrIgnoreKey).anyMatch(key -> key.equals(strKey))) continue; }

				// 기본으로 추가되는 Element 등록
				if (arrDefaultKey != null && arrDefaultKey.length > 0) {
					if (Arrays.stream(arrDefaultKey).anyMatch(key -> key.equals(strKey))) {
						jObjTmp.add(strKey, jObjOrg.get(strKey));
						continue;
					}
				}

				// 활성화된 Element 등록
				for (DatasetOutputVo vo : outputList) {
					if (!vo.isOpen()) continue;
					
					if (!strKey.equals(vo.getRealColumnName())) continue;

					if (!"".equals(vo.getAliasColumnName())) {
						jObjTmp.add(vo.getAliasColumnName(), jObjOrg.get(vo.getRealColumnName()));
					}
					else {
						jObjTmp.add(vo.getRealColumnName(), jObjOrg.get(vo.getRealColumnName()));
					}
				}
			}

			rtnJsonArray.add(jObjTmp);
		}

		return rtnJsonArray.toString();
	}

	
}
