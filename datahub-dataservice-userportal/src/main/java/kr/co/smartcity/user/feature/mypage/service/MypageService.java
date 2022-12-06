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
package kr.co.smartcity.user.feature.mypage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hsqldb.lib.StringUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.user.common.CommonConst;
import kr.co.smartcity.user.common.component.HttpComponent;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

@Slf4j
@Service("mypageService")
public class MypageService extends HttpComponent{

	/**
	 * 데이터 분석가요청 목록 호출
	 * @Author      : 
	 * @Date        : 2019. 10. 08.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getAnalyList(Map<String, String> params) throws Exception {
		setSessionAttribute(CommonConst.SEARCH_PREFIX, "ANALY_LIST", params);
		String result = this.get(props.getDatapublishMsApiUrl()  + "/dataservice/analysisApprovalRequests", params);
		
		Map<String, Object> resultMap = this.toMap(result);
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("list");
		
		for ( Map<String, Object> map : list) {
			long requestId = (long) map.get("id");
			
			List<Map<String, Object>> modelList = this.toList(this.get(props.getDatapublishMsApiUrl() + "/dataservice/analysisApprovalRequests/" + String.valueOf(requestId) +"/dataModel"));
			
			map.put("modelCount", modelList.size());
		}
		return resultMap;
	}

	/**
	 * 데이터분석가요청 등록
	 * @Author      : thlee
	 * @Date        : 2019. 10. 08.
	 * @param datamodelAnalystRequestVo
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String createAnaly(Map<String, Object> params) throws Exception {
		// 분석가요청 기본정보 등록
		Response response = method( HttpMethod.POST.toString(), props.getDatapublishMsApiUrl()  + "/dataservice/analysisApprovalRequests", params);

		String locationUrl = response.header("Location");
		// 하위 데이터모델 목록 저장
		try {
			// datamodelList=[{type=OffStreetParking, namespace=kr.citydatahub, version=1.0, context=[http://uri.etsi.org/ngsi-ld/core-context.jsonld, http://cityhub.kr/ngsi-ld/parking.jsonld], description=OffStreetParking Description, indexAttributeNames=[location], attributes=[{name=name, isRequired=true, maxLength=50, valueType=String, attributeType=Property}, {name=location, isRequired=true, valueType=GeoJson, attributeType=GeoProperty}, {name=address, valueType=Object, objectMembers=[{name=addressCountry, valueType=String}, {name=addressRegion, valueType=String}, {name=addressLocality, valueType=String}, {name=streetAddress, valueType=String}, {name=addressTown, valueType=String}], attributeType=Property}, {name=locationTag, valueType=String, attributeType=Property}, {name=category, valueType=ArrayString, attributeType=Property}, {name=inAccident, valueType=String, attributeType=Relationship, childAttributes=[{name=location, valueType=GeoJson}, {name=providedBy}]}, {name=paymentAccepted, valueType=ArrayString, attributeType=Property}, {name=priceRate, valueType=String, attributeType=Property, childAttributes=[{name=unit, valueType=String}]}, {name=priceCurrency, valueType=ArrayString, attributeType=Property}, {name=image, valueType=String, attributeType=Property}, {name=totalSpotNumber, valueType=Integer, attributeType=Property}, {name=availableSpotNumber, valueType=Integer, attributeType=Property, hasObservedAt=true}, {name=maximumAllowedHeight, valueType=Double, attributeType=Property}, {name=openingHours, valueType=ArrayString, attributeType=Property}, {name=contactPoint, isRequired=false, valueType=Object, objectMembers=[{name=telephone, valueType=String}, {name=email, valueType=String}, {name=contactType, valueType=String}], attributeType=Property}, {name=status, valueType=ArrayString, attributeType=Property}, {name=refParkingSpots, valueType=ArrayString, attributeType=Property}, {name=congestionIndexPrediction, valueType=ArrayObject, objectMembers=[{name=index, valueType=Integer}, {name=predictedAt, valueType=Date}], attributeType=Property, hasObservedAt=true}]}]}
			
			List<Map<String, Object>> modelList = (List<Map<String, Object>>) params.get("datamodelList");
			
			String[] splitUrl = locationUrl.split("/");
			String analystId = splitUrl[splitUrl.length - 1];
			
			for ( Map<String, Object> model : modelList) {
				
				Map<String, Object> newParam = new HashMap<String, Object>();
				newParam.put("modelId", model.get("id"));
				newParam.put("analystId", analystId);

				this.method( HttpMethod.POST.toString(), props.getDatapublishMsApiUrl()  + locationUrl + "/dataModel", newParam);
			}
			
		} catch (Exception ex) {
			// rollback (분석가요청 기본정보 롤백).
			method( HttpMethod.DELETE.toString(), props.getDatapublishMsApiUrl()  + locationUrl);
			
			throw ex;
		}
		
		return locationUrl;
		
	}

	/**
	 * 데이터분석가요청 상세
	 * @Author      : thlee
	 * @Date        : 2019. 10. 11.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getAnaly(Map<String, String> params) throws Exception {
 		Map<String, Object> resultMap = new HashMap<String, Object>();
 		
		try {
			// 데이터분석가요청 상세 데이터(분석가요청 데이터 + 등록된 데이터 모델)
			Map<String, Object> analyMap = this.toMap(this.get(props.getDatapublishMsApiUrl()  + "/dataservice/analysisApprovalRequests/" + params.get("id"), params));
			List<Map<String, Object>> registerdModelList = this.toList(this.get(props.getDatapublishMsApiUrl()  + "/dataservice/analysisApprovalRequests/" + params.get("id") +"/dataModel"));
			
			
			// 데이터 모델 리스트
			List<Map<String, Object>> datacoreModelList = this.toList(this.get(props.getDataModelApiUrl()));
			
			if (!analyMap.isEmpty() && !registerdModelList.isEmpty()) {
				for (Map<String, Object> analyDatamodel : registerdModelList) {
					for (Map<String, Object> datamodel : datacoreModelList) {
						if (analyDatamodel.get("modelId").equals(datamodel.get("id"))) {
							analyDatamodel.put("modelName", datamodel.get("typeUri"));
						}
					}
				}
			}
			resultMap.put("analyData", analyMap);
			resultMap.put("datamodelList", registerdModelList);
		}catch(Exception e) {
			throw e;
		}
		return resultMap;
	}

 	
 	/**
 	 * <pre>이용신청목록 조회</pre>
 	 * @Author      : hk-lee
 	 * @Date        : 2020. 7. 20.
 	 * @param params
 	 * @return
 	 * @throws Exception
 	 */
	public String getUsageList(Map<String, String> params) throws Exception {
		setSessionAttribute(CommonConst.SEARCH_PREFIX, "USAGE_LIST", params);
		
		String datasetListJson = get(props.getDatapublishMsApiUrl()  + "/integrated/datasetApplications", params);
		Map<String, Object> datasetListMap = toMap(datasetListJson);
		Map<String, String> datasetListPageInfoMap = (Map<String, String>) datasetListMap.get("page");
		datasetListPageInfoMap.putAll(params);
		return toJson(datasetListMap);
	}
	
	/**
	 * <pre>관심데이터 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 20.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getDatasetWishlList(Map<String, String> params) throws Exception {
		setSessionAttribute(CommonConst.SEARCH_PREFIX, "WISH_LIST", params);
		
		String datasetListJson = get(props.getDatapublishMsApiUrl()  + "/integrated/datasetWishlist", params);
		Map<String, Object> datasetListMap = toMap(datasetListJson);
		Map<String, String> datasetListPageInfoMap = (Map<String, String>) datasetListMap.get("page");
		datasetListPageInfoMap.putAll(params);
		return toJson(datasetListMap);
	}

	/**
	 * <pre>데이터셋 어댑터 신청 내역 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 4.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getDatasetAdaptorList(Map<String, String> params) throws Exception {
		setSessionAttribute(CommonConst.SEARCH_PREFIX, "ADAPTOR_LIST", params);
		return get(props.getDatapublishMsApiUrl()  + "/dataservice/datasetAdaptor", params);
	}
	
	/**
	 * <pre>데이터셋 어댑터 신청</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 4.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String createDatasetAdaptor(Map<String, Object> params) throws Exception {
		return this.strBody(method( HttpMethod.POST.toString(), props.getDatapublishMsApiUrl()  + "/dataservice/datasetAdaptor", params));
	}

	/**
	 * 
	 * <pre>데이터셋 어댑터 신청 상세 내역</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 4.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getDatasetAdaptor(Map<String, String> params) throws Exception {
		Map<String, Object> adaptorMap = null;
		
		try {
			String adaptorJson = get(props.getDatapublishMsApiUrl()  + "/dataservice/datasetAdaptor/"+params.get("id"));
			String adaptorFieldListJson = get(props.getDatapublishMsApiUrl()  + "/dataservice/datasetAdaptor/"+params.get("id")+"/columns");
			adaptorMap = toMap(adaptorJson);
			List<Map<String, Object>> adaptorFieldList = toList(adaptorFieldListJson);
			adaptorMap.put("fieldList", adaptorFieldList);
			
			// 데이터 모델 리스트
			List<Map<String, Object>> dataModelList = null;
			
			String dataModelJson = this.get(props.getDataModelApiUrl());
			dataModelList = toList(dataModelJson);
			
			if (!adaptorMap.isEmpty() && !dataModelList.isEmpty()) {
				for (Map<String, Object> dataModel : dataModelList) {
					if (adaptorMap.get("modelId").equals(dataModel.get("id"))) {
						adaptorMap.put("modelName", dataModel.get("name"));
					}
				}
			}
			
		}catch(Exception e) {
			throw e;
		}
		return toJson(adaptorMap);
	}

	
	/**
	 * <pre>인센티브 토큰 전송</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 29.
	 * @return
	 * @throws Exception
	 */
	public String transferIncentiveToken(Map<String, Object> params) throws Exception {
		
		// 평점 발급 시 시스템관리자 (cityhub08) 에서 인센티브 토큰 발급.
		params.put("fromId", "cityhub08");
		
		// 인센티브 토큰 발급 시 헤더 토큰은 블록체인에서 제공 해 준 값으로 하드코딩 (개발계/품질계 모두 해당)
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXBlIjoiYWRtaW5TeXN0ZW0iLCJ1c2VySWQiOiJlRFV3T1RvNlEwNDlRV1J0YVc1QWNHNTFMbU5wZEhsb2RXSXViM0puTEU5VlBXRmtiV2x1TEV3OVUyRnVJRVp5WVc1amFYTmpieXhUVkQxRFlXeHBabTl5Ym1saExFTTlWVk02T2tOT1BXTmhMbkJ1ZFM1amFYUjVhSFZpTG05eVp5eFBQWEJ1ZFM1amFYUjVhSFZpTG05eVp5eE1QVk5oYmlCR2NtRnVZMmx6WTI4c1UxUTlRMkZzYVdadmNtNXBZU3hEUFZWVCIsIm5pY2tuYW1lIjoicG51QWRtaW4iLCJlbWFpbCI6ImhvbmdnaWxkb25nQHBudS5hYy5rciIsInJvbGUiOiJhZG1pbiIsImlhdCI6MTU3MjE2NzU4NCwiZXhwIjoxOTk5OTk5OTk5LCJhdWQiOiJQTlVNU1AiLCJpc3MiOiJ1cm46ZGF0YWh1YjpjaXR5aHViOnNlcmN1cml0eSJ9.p8I_6YXjNikfWqkFMtqKDfbXp_Bs68UmAWVtLQ4k9sKGhbSlLr9N3SQ7S8Iuo1n2I14zkuVNwXOm7a8l0i3m3krSvGaphoAlrzGpTqkTD5rrOhZfdKUnhZCzuUnWBNeYV-pVTHqZnnehT7eD_hPW0RUJ8E1Zo6GpG6j5Fl7Sx7xaJmf3_ufg3GnjilQuigTD3ji7o-qGckCBZTWkCYgFNK6LXs6UmAAiBUvmCoqedvDOsWeDm5Jugu8gmKb-Z1UoFzxaeBq76ZBazPAPq4vLTLXh2e0GpTNkXKiOy6uvgqyd6jnDNDM22f3hD5STDZ0i8Agolkq_Nk-7OXERz0f5pg");
		
		Response response = method(HttpMethod.POST.toString(), props.getBlockChainServerUrl() + "/incentive/transfer", params, headers);
		String resultStr = response.body().string();
		
		log.debug("====> " + resultStr);
		
		return resultStr;
	}
	
	/**
	 * <pre>사용자 별 인센티브 발급 정보 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 29.
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getIncentiveTokenLog(Map<String, Object> params) throws Exception {
		if ("Y".equals(props.getBlockChainRequest())) {
			String userId    = (String)params.get("userId");
			String termStart = (String)params.get("termStart");
			String termEnd   = (String)params.get("termEnd");
			params.put("userId"    , userId);
			params.put("termStart" , StringUtil.isEmpty(termStart) ? "*" : termStart);
			params.put("termEnd"   , StringUtil.isEmpty(termEnd)   ? "*" : termEnd);
			
			// 인센티브 토큰 발급 시 헤더 토큰은 블록체인에서 제공 해 준 값으로 하드코딩 (개발계/품질계 모두 해당)
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXBlIjoiYWRtaW5TeXN0ZW0iLCJ1c2VySWQiOiJlRFV3T1RvNlEwNDlRV1J0YVc1QWNHNTFMbU5wZEhsb2RXSXViM0puTEU5VlBXRmtiV2x1TEV3OVUyRnVJRVp5WVc1amFYTmpieXhUVkQxRFlXeHBabTl5Ym1saExFTTlWVk02T2tOT1BXTmhMbkJ1ZFM1amFYUjVhSFZpTG05eVp5eFBQWEJ1ZFM1amFYUjVhSFZpTG05eVp5eE1QVk5oYmlCR2NtRnVZMmx6WTI4c1UxUTlRMkZzYVdadmNtNXBZU3hEUFZWVCIsIm5pY2tuYW1lIjoicG51QWRtaW4iLCJlbWFpbCI6ImhvbmdnaWxkb25nQHBudS5hYy5rciIsInJvbGUiOiJhZG1pbiIsImlhdCI6MTU3MjE2NzU4NCwiZXhwIjoxOTk5OTk5OTk5LCJhdWQiOiJQTlVNU1AiLCJpc3MiOiJ1cm46ZGF0YWh1YjpjaXR5aHViOnNlcmN1cml0eSJ9.p8I_6YXjNikfWqkFMtqKDfbXp_Bs68UmAWVtLQ4k9sKGhbSlLr9N3SQ7S8Iuo1n2I14zkuVNwXOm7a8l0i3m3krSvGaphoAlrzGpTqkTD5rrOhZfdKUnhZCzuUnWBNeYV-pVTHqZnnehT7eD_hPW0RUJ8E1Zo6GpG6j5Fl7Sx7xaJmf3_ufg3GnjilQuigTD3ji7o-qGckCBZTWkCYgFNK6LXs6UmAAiBUvmCoqedvDOsWeDm5Jugu8gmKb-Z1UoFzxaeBq76ZBazPAPq4vLTLXh2e0GpTNkXKiOy6uvgqyd6jnDNDM22f3hD5STDZ0i8Agolkq_Nk-7OXERz0f5pg");
			
			Response response = method(HttpMethod.POST.toString(), props.getBlockChainServerUrl() + "/incentive/query/user", params, headers);
			
			String resultStr = response.body().string();
			
			log.debug("====> " + resultStr);
			
			
			Map<String, Object> resultMap = gson.fromJson(resultStr, Map.class);
			
			String totalBalance = get(props.getBlockChainServerUrl() + "/token/balanceOf/"+userId);
			resultMap.put("balance", totalBalance);
			return gson.toJson(resultMap);
		} else {
			return null;
		}
		
	}
}
