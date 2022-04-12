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

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.DatasetOutputVo;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class RetrofitService {

	private final Logger logger = (Logger) LoggerFactory.getLogger(RetrofitService.class);

	@Autowired
	@Qualifier("customHttpClient")
	private OkHttpClient client;
	
	@Value("${smartcity.datacore.server}")
	private String dataCoreApiUrl;

	private final String[] arrIgnoreKey = {};
	private final int THREAD_COUNT = 10;

	public String getConvertData(List<DatasetOutputVo> outputList, Map<String, String> mParam) throws Exception {
        // 3. Call URL
        Map<String, String> lastParams = null;

        String[] arrDefaultKey = new String[] {"id" ,"type" ,"createdAt" ,"modifiedAt"};
        String sUri = "";

		// datatype 있을때
		if (mParam.containsKey("datatype")) {
			String datatype = mParam.get("datatype");
			if (StringUtil.equals(datatype, "lastdata")) {
				lastParams = new HashMap<String, String>();
				if(mParam.get("id") != null) {
					lastParams.put("id", mParam.get("id"));
				}
				lastParams.put("type", mParam.get("type"));
				if (StringUtil.isNotEmpty(mParam.get("q"))) lastParams.put("q", mParam.get("q"));
				String options = mParam.getOrDefault("options", "");
				if (!StringUtil.isEmpty(options)) {
					lastParams.put("options", options);
				}
				arrDefaultKey = new String[] {"id" ,"type" ,"createdAt" ,"modifiedAt"};

				sUri = "entities";
			}
			else if (StringUtil.equals(datatype, "historydata")) {
				arrDefaultKey = new String[] {"modifiedAt"};

				sUri = "temporal/entities";
			} else {
				// datatype이 lastdata나 historydata가 아닐때 예외처리
				throw new GlobalException(5201, "invalid datatype : " + datatype);
			}
			// datatype 없을때(기본값)
		}
		else {
			arrDefaultKey = new String[] {"modifiedAt"};

			sUri = "temporal/entities";
		}

        // 4. Parse Data
        JsonArray responseData = null;
        String strResultData = null;
        try {
        	// 호출 Full URL
        	logger.debug("1. >> Call Start.");

        	HttpUrl.Builder urlBuild = HttpUrl.parse(dataCoreApiUrl + "/" + sUri).newBuilder();
        	if (lastParams != null) {
        		for (Map.Entry<String, String> entry : lastParams.entrySet()) {
        			urlBuild.addQueryParameter(entry.getKey(), entry.getValue());	
        		}
        	}
        	else {
        		for (Map.Entry<String, String> entry : mParam.entrySet()) {
        			urlBuild.addQueryParameter(entry.getKey(), entry.getValue());	
        		}
        	}

        	Request request = new Request.Builder()
        			.url(urlBuild.build().toString())
        			.addHeader("Accept", "application/json")
        			.build();

        	try ( Response response = client.newCall(request).execute(); ) {

            	if ( response.code() != HttpStatus.OK.value() ) {
            		throw new GlobalException(5202, "failed request : " + urlBuild.build().toString());
            	}

            	logger.debug("2. >> Call End.");

            	logger.debug("2. >> Print Header.");

            	responseData = this.procResponseBody(response);
            	if (responseData == null) {
            		throw new GlobalException(5202, "failed request : " + urlBuild.build().toString());
            	}
        	}

        	logger.debug("3. >> Get Data Count: " + responseData.size() + " (" + (responseData.toString()).getBytes().length + " byte)");

        	strResultData = this.convertJson(outputList, responseData, arrDefaultKey);

        	logger.debug("4. >> Parse Comp.");

		} catch (Exception e) {
			throw e;
		}

        return strResultData;
	}

	private JsonArray procResponseBody(Response response) {
		JsonArray returnJsonArray = null;

		try ( StringWriter sw = new StringWriter(); ) {
			IOUtils.copy(response.body().byteStream(), sw, "UTF-8");

			returnJsonArray = new JsonParser().parse(sw.toString()).getAsJsonArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnJsonArray;
	}

	private String convertJson(List<DatasetOutputVo> outputList, JsonArray procJsonArray, String[] arrDefaultKey) throws ExecutionException, InterruptedException {
		int iDataLength = procJsonArray.size();
		
		JsonArray rtnJsonArray = new JsonArray();

		JsonObject jObjOrg = null;
		JsonObject jObjTmp = null;

		List<String> listJsonKeySet = null;

		// 2. JsonData 처리
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

	// ----------------------------------------------------------------------------------------------------

	public String convertJsonByThread(List<DatasetOutputVo> outputList, JsonArray procJsonArray) throws ExecutionException, InterruptedException {
		int iDataLength = procJsonArray.size();
		int iProcCount = iDataLength / THREAD_COUNT;
		int iModCount  = iDataLength % THREAD_COUNT;
		int iStIndex = 0;
		int iEdIndex = 0;

		// 2. Thread 구성 및 할당
		ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
		Callable<JsonArray> callable = null;

		List<Future<JsonArray>> futures = new ArrayList<>();
		if (iDataLength > THREAD_COUNT) {
			for (int i = 0; i < THREAD_COUNT; i++) {
				if (10 - 1 == i) { iEdIndex -= 1; }

				iEdIndex = iEdIndex + iProcCount;

				if (i < iModCount) { iEdIndex += 1; }

				callable = this.callThread(procJsonArray, outputList, iStIndex, iEdIndex);
				
				// 생성된 callable들을 threadpool에서 수행시키고 결과는 Future 목록에 담는다.
				futures.add(threadPool.submit(callable));

				iStIndex = iEdIndex + 1;

				if (iEdIndex == iDataLength) break;
			}
		}
		else {
			for (int i = 0; i < iDataLength; i++) {
				callable = this.callThread(procJsonArray, outputList, i, i);

				// 생성된 callable들을 threadpool에서 수행시키고 결과는 Future 목록에 담는다.
				futures.add(threadPool.submit(callable));
			}
		}
		// 수행중인 callable들이 다 끝나면 threadpool을 종료시킨다.(반드시 해야함)
		threadPool.shutdown();

		JsonArray rtnJsonArray = new JsonArray();
		for (Future<JsonArray> future : futures) { rtnJsonArray.addAll(future.get()); }

		return rtnJsonArray.toString();
	}

	// Thread
	private Callable<JsonArray> callThread(JsonArray pocJsonArray, List<DatasetOutputVo> outputList, final int iStIndex, final int iEdIndex) {
		Callable<JsonArray> callable = new Callable<JsonArray>() {

			@Override
			public JsonArray call() throws Exception {
				JsonArray rtnJsonArray = new JsonArray();

				JsonObject jObjOrg = null;
				JsonObject jObjTmp = null;

				List<String> listJsonKeySet = null;

				for (int i = iStIndex; i <= iEdIndex; i++) {
					jObjOrg = (JsonObject) pocJsonArray.get(i);
					jObjTmp = new JsonObject();

					listJsonKeySet = new ArrayList<>(jObjOrg.keySet());
					for (String strKey : listJsonKeySet) {
						if (Arrays.stream(arrIgnoreKey).anyMatch(key -> key.equals(strKey))) continue;

						for (DatasetOutputVo vo : outputList) {
							if (!vo.isOpen())  continue;
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

				return rtnJsonArray;
			}
		};
		
		return callable;
	}
}
