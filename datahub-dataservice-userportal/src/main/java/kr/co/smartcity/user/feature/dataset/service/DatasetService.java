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
package kr.co.smartcity.user.feature.dataset.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.user.common.CommonConst;
import kr.co.smartcity.user.common.component.HttpComponent;
import kr.co.smartcity.user.common.component.Properties;
import kr.co.smartcity.user.common.util.StringUtil;
import kr.co.smartcity.user.feature.dataset.vo.DatasetUseRequestVo;
import kr.co.smartcity.user.feature.dataset.vo.DatasetVo;
import kr.co.smartcity.user.feature.mypage.service.MypageService;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service("datasetService")
public class DatasetService extends HttpComponent{
	
	@Autowired
	private MypageService mypageService;
	
	@Autowired Properties properties;
	
	/**
	 * 데이터셋 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 21.
	 * @return
	 * @throws Exception 
	 */
	public String getDatasetList(Map<String, String> params) throws Exception {
		setSessionAttribute(CommonConst.SEARCH_PREFIX, "DATASET_LIST", params);
		
		String datasetListJson = get(props.getDatapublishMsApiUrl() + "/integrated/dataset", params);
		Map<String, Object> datasetListMap = toMap(datasetListJson);
		Map<String, String> datasetListPageInfoMap = (Map<String, String>) datasetListMap.get("page");
		Map<String, Object> mqttIp = new HashMap<String, Object>();
		Map<String, Object> webSocketIp = new HashMap<String, Object>();
		mqttIp.put("mqttIp", properties.getMqttUrl());
		webSocketIp.put("webSocketIp", properties.getWebsocketUrl());
		datasetListMap.put("mqttIp", properties.getMqttUrl());
		datasetListMap.put("webSocketIp", properties.getWebsocketUrl());
		datasetListPageInfoMap.putAll(params);
		return toJson(datasetListMap);
	}
	
	/**
	 * 데이터셋 상세 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 10.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getDataset(Map<String, String> params) throws Exception {
		String datasetJson = get(props.getDatapublishMsApiUrl() + "/integrated/dataset/" + params.get("id"), params);

		Map<String, Object> datasetMap = toMap(datasetJson);
		
		List<Map<String, Object>> datasetOriginList = (List<Map<String, Object>>) datasetMap.get("datasetOriginList");
		List<Map<String, Object>> newDatasetOriginList = new ArrayList<>();
		for(Map<String, Object> datasetOrigin : datasetOriginList) {
			String datasetOriginId = (String) datasetOrigin.get("datasetOriginId");
			String datasetOriginJson = getDatasetOrigin(datasetOriginId);
			Map<String, Object> datasetOriginMap = toMap(datasetOriginJson);
			datasetOriginMap.put("key", datasetOrigin.get("id"));
			newDatasetOriginList.add(datasetOriginMap);
		}
		datasetMap.put("datasetOriginList", newDatasetOriginList);
		

		//데이터셋 유료화 확장기능 여부
		boolean enabledDatasetPayments = false;
		String featuresListJson = get(props.getPortalMsApiUrl() + "/dataserviceUi/features");
		List<Map<String, Object>> featuresList = toList(featuresListJson);
		for(Map<String, Object> featuresMap : featuresList) {
			String featureCd = (String) featuresMap.get("featureCd");
			if(StringUtil.equals(featureCd, "datasetPayments")) {
				enabledDatasetPayments = (boolean) featuresMap.get("enabledTf");
			}
		}
		datasetMap.put("enabledDatasetPayments", enabledDatasetPayments);
		
		return toJson(datasetMap);
	}
	
	/**
	 * 데이터셋 데이터 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 21.
	 * @return
	 * @throws Exception 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public String createDataset(DatasetVo datasetVo, HttpServletRequest request) throws Exception{
		Map<String, Object> params = toMap(toJson(datasetVo));
		return this.strBody(method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() + "/integrated/dataset", params, request));
	}
	
	/**
	 * 데이터셋 데이터 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 10.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String modifyDataset(DatasetVo datasetVo, HttpServletRequest request) throws Exception {
		Map<String, Object> params = toMap(toJson(datasetVo));
		return this.strBody(method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() + "/integrated/dataset/" + params.get("id"), params, request));
		
	}
	

	/**
	 * <pre>데이터셋 부분 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 29.
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	public String patchDataset(Map<String, Object> params) throws Exception {
		if("paidRequest".equals(params.get("status"))){
			String datasetId = (String) params.get("id");
			List<Map<String, Object>> pricePoliciesList = (List<Map<String, Object>>) params.get("pricePoliciesList");
			for(Map<String, Object> pricePolicies : pricePoliciesList) {
				Response response = method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/pricePolicies", pricePolicies);
				String location = response.header("location");
				long priceId = Long.parseLong(location.replace("/dataservice/dataset/" + datasetId + "/pricePolicies/", ""));
				List<Map<String, Object>> pricePoliciesPeriodList = (List<Map<String, Object>>) pricePolicies.get("pricePoliciesPeriodList");
				for(Map<String, Object> pricePoliciesPeriod : pricePoliciesPeriodList) {
					pricePoliciesPeriod.put("priceId", priceId);
					method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/pricePolicies/" + priceId + "/price", pricePoliciesPeriod);
				}
				
			}
		}
		
		return this.strBody(method(HttpMethod.PATCH.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + params.get("id"), params));
	}
	
	/**
	 * 데이터셋 관심상품 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 23.
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public String createDatasetWishlist(Map<String, Object> params, HttpServletRequest request) throws Exception {
		return this.strBody(method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/"+params.get("datasetId")+"/wishlist", params));
	}
	
	/**
	 * 데이터셋 관심상품 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 23.
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public String deleteDatasetWishlist(Map<String, Object> params, HttpServletRequest request) throws Exception {
		return this.strBody(method(HttpMethod.DELETE.toString(), props.getDatapublishMsApiUrl() +"/dataservice/dataset/"+params.get("datasetId")+"/wishlist/"+params.get("id"), params));
	}
	
	/**
	 * <pre>데이터셋 이용신청 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 20.
	 * @param params
	 * @return 
	 * @return
	 * @throws Exception
	 */
	public String createDatasetUsage(Map<String, Object> params) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		
		//STEP 1. 이용신청 등록
		String datasetId = (String) params.get("datasetId");
		Response response = method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/requestUsage", params);
		String location = response.header("location");
		long requestId = Long.parseLong(location.replace("/dataservice/dataset/" + datasetId + "/requestUsage/", ""));
		resMap.put("requestId", requestId);
		
		
		//STEP 2. 이용신청 유료결제 정보 등록(조건부)
		Map<String, Object> datasetUsagePaymentMap = (Map<String, Object>) params.get("datasetUsagePayment");
		if(datasetUsagePaymentMap != null) {
			datasetUsagePaymentMap.put("requestId", requestId);
			response = method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId + "/payments", (Map<String, Object>) params.get("datasetUsagePayment"));
			location = response.header("location");
			long payId = Long.parseLong(location.replace("/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId + "/payments/", ""));
			resMap.put("payId"    , payId);
		}
		
		//STEP 3. 이용신청 수신정보 등록(조건부)
		Map<String, Object> datasetUsageReceptionMap = (Map<String, Object>) params.get("datasetUsageReception");
		if(datasetUsageReceptionMap != null) {
			datasetUsageReceptionMap.put("requestId", requestId);
			response = method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId + "/receptions", (Map<String, Object>) params.get("datasetUsageReception"));
			location = response.header("location");
			long recvId = Long.parseLong(location.replace("/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId + "/receptions/", ""));
			resMap.put("recvId"    , recvId);
		}
		//STEP 4. 데이터셋 등록 구독
		String requestType = (String) params.get("requestType");
		if (StringUtil.isNotEmpty(requestType)) {
			subscriptionMethod(props.getDatapublishMsApiUrl()+"/subscriptionRegister/"+datasetId, datasetId);
		}
		
		return toJson(resMap);
	}
	/**
	 * <pre>데이터셋 이용신청 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 17.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void modifyDatasetUsage(Map<String, Object> params) throws Exception {
		//STEP 1. 이용신청 수정
		String datasetId = (String) params.get("datasetId");
		long requestId = (long) params.get("id");
		method(HttpMethod.PATCH.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId, params);
		
		
		//STEP 2. 이용신청 유료결제 정보 수정(조건부)
		Map<String, Object> datasetUsagePaymentMap = (Map<String, Object>)params.get("datasetUsagePayment");
		if(datasetUsagePaymentMap != null) {
			long payId = (long) datasetUsagePaymentMap.get("id");
			method(HttpMethod.PATCH.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId + "/payments/" + payId, (Map<String, Object>) params.get("datasetUsagePayment"));
		}
		
		//STEP 3. 이용신청 수신정보 수정(조건부)
		Map<String, Object> datasetUsageReceptionMap = (Map<String, Object>) params.get("datasetUsageReception");
		if(datasetUsageReceptionMap != null) {
			if(datasetUsageReceptionMap.get("id") != null) {
				long recvId = (long) datasetUsageReceptionMap.get("id");
				method(HttpMethod.PATCH.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId + "/receptions/" + recvId, (Map<String, Object>) params.get("datasetUsageReception"));
			}else {
				method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId + "/receptions", (Map<String, Object>) params.get("datasetUsageReception"));
			}
		}
	}
	
	/**
	 * <pre>데이터셋 이용신청 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 20.
	 * @param DatasetUseRequestVo
	 * @return
	 * @throws Exception
	 */
	public String deleteDatasetUsage(DatasetUseRequestVo DatasetUseRequestVo) throws Exception {
		Map<String, Object> params = toMap(toJson(DatasetUseRequestVo));
		return this.strBody(method(HttpMethod.DELETE.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/"+params.get("datasetId")+"/requestUsage/" + DatasetUseRequestVo.getId(), params));
		
	}

	/**
	 * 데이터 모델 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 1.
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	public String getDataModelList(DatasetVo datasetVo) throws Exception {
		Map<String, String> params = new HashMap<>();
//		params.put("name", datasetVo.getModelName());
//		params.put("level", "000");
		return get(props.getDataCoreServerUrl() + "/datamodels", params);
	}

	/**
	 * 데이터 모델 상세 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 21.
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	public String getDataModel(DatasetVo datasetVo) throws Exception {
		return get(props.getDataCoreServerUrl() + "/datamodels/" + datasetVo.getModelId());
	}
	
	/**
	 * <pre>데이터 코어 데이터셋 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 27.
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	public String getDatasetOriginList(DatasetVo datasetVo) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("dataModelId"     , datasetVo.getModelId());
		return get(props.getDataCoreServerUrl() + "/datasets", params);
	}
	
	/**
	 * <pre>데이터 코어 데이터셋 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 27.
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	public String getDatasetOrigin(String datasetOriginId) throws Exception {
		return get(props.getDataCoreServerUrl() + "/datasets/" + datasetOriginId);
	}
	
	/**
	 * 데이터 인스턴스 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 21.
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	public String getDataInstanceList(DatasetVo datasetVo) throws Exception {
		Map<String, String> params = new HashMap<>();
		String typeUri = datasetVo.getModelTypeUri();
		
		String searchType = datasetVo.getSearchType();
		if(StringUtil.isNotEmpty(searchType)) {
			searchType = searchType.replaceAll("&quot;", "");
		}
		params.put("datasetId", searchType);
		params.put("type", typeUri);

		return get(props.getDataEntitiesServerUrl() + "/entities", params);
	}

	/**
	 * 데이터 인스턴스 상세 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 21.
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	public String getDataInstance(DatasetVo datasetVo) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("type", datasetVo.getModelId());
		return get(props.getDataEntitiesServerUrl() + "/entities/"+ datasetVo.getDsInstanceId(), params);
	}

	/**
	 * 카테고리 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 21.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getCategoryList(Map<String, String> params) throws Exception {
		return get( props.getDatapublishMsApiUrl()  + "/dataservice/datasetCategories", params);
		
	}

	/**
	 * 
	 * <pre>카테고리 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 29.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getCategory(DatasetVo datasetVo) throws Exception {
		return get(props.getDatapublishMsApiUrl() +"/dataservice/datasetCategories/"+datasetVo.getCategoryId());
	}
	
	/**
	 * 
	 * <pre>데이터 샘플 조회</pre>
	 * @Author      : thlee
	 * @Date        : 2019. 10. 30.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getSampleData(Map<String, String> params) throws Exception {
		if (StringUtil.isNotEmpty(params.get("time")) && StringUtil.isNotEmpty(params.get("endtime")) ) {
			
			if (params.get("datatype").equals("historydata")) {
				params.put("time", params.get("time"));
				params.put("endtime", params.get("endtime"));
			} else {
				params.put("time", "");
				params.put("endtime", "");
			}
		}
		
		String id = params.get("datasetId");
		return get(props.getDatapublishMsApiUrl()  + "/dataservice/dataset/" + id + "/sampleData", params);
	}

	/**
	 * <pre>유저 리스트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 7.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getUserList(Map<String, String> params) throws Exception {
		return get(props.getNormalUserInfoUrl());
	}

	/**
	 * <pre>만족도 평가 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 7.
	 * @param datasetVo
	 * @return
	 * @throws Exception
	 */
	public String createDatasetSatisfaction(Map<String, Object> params) throws Exception {
		if ("Y".equals(props.getBlockChainRequest())) {
			mypageService.transferIncentiveToken(params);
		}
		return this.strBody(method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() +"/dataservice/dataset/" + params.get("datasetId") + "/rating", params));
	}

	/**
	 * <pre>문의하기 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 14.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getQnaList(Map<String, String> params) throws Exception {
		return get(props.getDatapublishMsApiUrl() +"/dataservice/dataset/" + params.get("datasetId") + "/inquiry", params);
	}

	/**
	 * <pre>문의하기 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 14.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String createQna(Map<String, Object> params) throws Exception {
		return this.strBody(method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() +"/dataservice/dataset/" + params.get("datasetId") + "/inquiry", params));
	}

	/**
	 * 
	 * <pre>문의하기 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 14.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String modifyQna(Map<String, Object> params) throws Exception {
		return this.strBody(method(HttpMethod.PUT.toString(), props.getDatapublishMsApiUrl() +"/dataservice/dataset/" + params.get("datasetId") + "/inquiry/" + params.get("id"), params));
	}

	/**
	 * <pre>문의하기 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 14.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String deleteQna(Map<String, Object> params) throws Exception {
		return this.strBody(method(HttpMethod.DELETE.toString(), props.getDatapublishMsApiUrl() +"/dataservice/dataset/" + params.get("datasetId") + "/inquiry/" + params.get("id"), params));
	}
	
	/**
	 * <pre>이용신청 후 데이터조회를 위한 대상 서버 주소</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 4. 28.
	 * @return
	 * @throws Exception
	 */
	public String getDatasetEndPoint() throws Exception {
		return props.getGatewayPublicUrl();
	}

	/**
	 * <pre>가격 정책 기본 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 13.
	 * @return
	 * @throws Exception
	 */
	public String getPricePoliciesList() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("paging"     , "false");
		params.put("searchType" , "use");
		params.put("searchUseTf", "true");
		Map<String, Object> pricePoliciesListMap = toMap(get(props.getDatapublishMsApiUrl() + "/dataservice/pricePolicies", params));
		List<Map<String, Object>> pricePoliciesList = (List<Map<String, Object>>) pricePoliciesListMap.get("list");
		for(Map<String, Object> policyMap : pricePoliciesList) {
			long policyId = (long) policyMap.get("id");
			params.put("policyId", String.valueOf(policyId));
			String policyPeriodListJson = get(props.getDatapublishMsApiUrl() + "/dataservice/pricePolicies/" + policyId + "/period", params);
			List<Map<String, Object>> periodList = toList(policyPeriodListJson);
			policyMap.put("pricePoliciesPeriodList", periodList);
		}
		return toJson(pricePoliciesList);
	}
	/**
	 * <pre>데이터셋 가격 정책 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 13.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getDatasetPricePoliciesList(Map<String, String> params) throws Exception {
		String datasetId = params.get("datasetId");
		Map<String, Object> mqttIp = new HashMap<String, Object>();
		Map<String, Object> webSocketIp = new HashMap<String, Object>();
		mqttIp.put("mqttIp", properties.getMqttUrl());
		webSocketIp.put("webSocketIp", properties.getWebsocketUrl());
		List<Map<String, Object>> pricePoliciesList = toList(get(props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/pricePolicies", params));
		for(Map<String, Object> policyMap : pricePoliciesList) {
			long policyId = (long) policyMap.get("id");
			params.put("policyId", String.valueOf(policyId));
			String policyPeriodListJson = get(props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/pricePolicies/" + policyId + "/price", params);
			List<Map<String, Object>> periodList = toList(policyPeriodListJson);
			policyMap.put("pricePoliciesPeriodList", periodList);
		}
		pricePoliciesList.add(mqttIp);
		pricePoliciesList.add(webSocketIp);
		
		return toJson(pricePoliciesList);
	}

	public void modifyDatasetUsagePay(Map<String, Object> params) throws Exception {
		method(HttpMethod.PATCH.toString()
				, props.getDatapublishMsApiUrl() +"/dataservice/dataset/" + params.get("datasetId") + "/requestUsage/" + params.get("requestId") + "/payments/" + params.get("id"), params);
	}

	/**
	 * <pre>데이터셋 이용신청 환불 신청 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String createDatasetUsageRefund(Map<String, Object> params) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		Response response = method(HttpMethod.POST.toString()
				, props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + params.get("datasetId") 
				+ "/requestUsage/" + params.get("requestId") + "/refunds", params);
		
		long refundId = Long.parseLong(
				response.header("location").replace("/dataservice/dataset/" 
						+ params.get("datasetId") + "/requestUsage/" + params.get("requestId") + "/refunds/", ""));
		resMap.put("refundId", refundId);
		
		
		return toJson(resMap);
	}

	/**
	 * <pre>데이터셋 이용신청 환불 신청 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param params
	 * @return
	 * @throws Exception
	 */

	public void modifyDatasetUsageRefund(Map<String, Object> params) throws Exception {
		method(HttpMethod.PATCH.toString()
				, props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + params.get("datasetId") 
				+ "/requestUsage/" + params.get("requestId") + "/refunds/" + params.get("id"), params);
	}
	
	/**
	 * <pre>데이터셋 이용신청 환불 내역 목록 조회(제공자)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getDatasetUsageRefundListByProvider(Map<String, String> params) throws Exception {
		return get(props.getDatapublishMsApiUrl() + "/integrated/datasetUsageRefundsProvider", params);
	}
	
	/**
	 * <pre>데이터셋 이용신청 환불 내역 목록 조회(활용자)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getDatasetUsageRefundListByUser(Map<String, String> params) throws Exception {
		return get(props.getDatapublishMsApiUrl() + "/integrated/datasetUsageRefundsUser", params);
	}
	
	/**
	 * <pre>플랫폼관리시스템 Alive 체크</pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 8.
	 * @return
	 */
	public boolean isAlivePlatformServer() {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder()
					.connectTimeout(2, TimeUnit.SECONDS).build();
			
			Request.Builder builder = new Request.Builder();
			Request request = builder.url(HttpUrl.parse(props.getPlatformServerUrl() + "/alive").newBuilder().build())
					.get()
					.build();
			
			Response response = client.newCall(request).execute();
			
			return response.isSuccessful();
			
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * <pre>데이터셋 디바이스 연결 설정 목록 조회 (인터페이스) </pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 16.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getDatasetDeviceMappingList(Map<String, String> params) throws Exception {
		String datasetId = params.get("datasetId");
		return this.get(String.format("%s/dataservice/dataset/%s/devices", props.getDatapublishMsApiUrl(), datasetId));
	}
	
	/**
	 * <pre>데이터셋 디바이스 연결 설정 삭제 (인터페이스)</pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 19.
	 * @param params
	 * @throws Exception
	 */
	public String removeDatasetDeviceMappingList(Map<String, String> params) throws Exception {
		String datasetId = params.get("datasetId");
		
		return this.strBody(method(HttpMethod.DELETE.toString(), String.format("%s/dataservice/dataset/%s/devices", props.getDatapublishMsApiUrl(), datasetId)));
	}
}
