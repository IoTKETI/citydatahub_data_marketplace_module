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
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service("datasetService")
public class DatasetService extends HttpComponent{
	
	@Autowired Properties properties;
	/**
	 * ???????????? ?????? ??????
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
		mqttIp.put("mqttIp", properties.getMqttIp());
		webSocketIp.put("webSocketIp", properties.getWebsocketIp());
		datasetListMap.put("mqttIp", properties.getMqttIp());
		datasetListMap.put("webSocketIp", properties.getWebsocketIp());
		datasetListPageInfoMap.putAll(params);
		return toJson(datasetListMap);
	}
	
	/**
	 * ???????????? ?????? ??????
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
		

		//???????????? ????????? ???????????? ??????
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
		
		
		/**
		 * ????????? ?????? ????????????
		 */
//		UserVo modelOwnerInfo = this.getUserByType((String)datasetMap.get("modelOwnerId"), UserType.NORMAL);
//		
//		if(modelOwnerInfo != null) {
//			String phone = modelOwnerInfo.getPhone();
//			String email = modelOwnerInfo.getEmail();
//			
//			if(StringUtil.isNotEmpty(phone)) {
//				phone = phone.replaceAll("^(01(?:0|1|[6-9]))[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", "$1-****-$3");
//			}
//			
//			if(StringUtil.isNotEmpty(email)) {
//				String[] emailSplit = email.split("@");
//				String emailId = emailSplit[0];
//				String emailHost = emailSplit[1];
//				String emailIdFront = emailId.substring(0, emailId.length()/2);
//				String emailIdBack = emailId.substring(emailId.length()/2, emailId.length());
//				emailIdFront = emailIdFront.replaceAll("[a-zA-Z]{1}", "*");
//				email = String.format("%s%s@%s", emailIdFront, emailIdBack, emailHost);
//			}
//			
//			datasetMap.put("dsModelOwnerNm"   , modelOwnerInfo.getName());
//			datasetMap.put("dsModelOwnerPhone", phone);
//			datasetMap.put("dsModelOwnerEmail", email);
//		}
		
		
		/**
		 * ?????????????????? ????????????( DataLake )
		 */
//		try {
//			Map<String, Object> modelMap = this.toMap(this.getDataModel(datasetVo));
//			if(modelMap != null) {
//				datasetVo.setDsModelNm(modelMap != null ? (String) modelMap.get("name") : "");
//			}
//		} catch (Exception ex) {
//			datasetVo.setDsModelNm("");
//		}
			
		
		return toJson(datasetMap);
	}
	
	/**
	 * ???????????? ????????? ??????
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
	 * ???????????? ????????? ??????
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
	 * <pre>???????????? ?????? ??????</pre>
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
	 * ???????????? ???????????? ??????
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
	 * ???????????? ???????????? ??????
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
	 * <pre>???????????? ???????????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 20.
	 * @param params
	 * @return 
	 * @return
	 * @throws Exception
	 */
	public String createDatasetUsage(Map<String, Object> params) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		
		//STEP 1. ???????????? ??????
		String datasetId = (String) params.get("datasetId");
		Response response = method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/requestUsage", params);
		String location = response.header("location");
		long requestId = Long.parseLong(location.replace("/dataservice/dataset/" + datasetId + "/requestUsage/", ""));
		resMap.put("requestId", requestId);
		
		
		//STEP 2. ???????????? ???????????? ?????? ??????(?????????)
		Map<String, Object> datasetUsagePaymentMap = (Map<String, Object>) params.get("datasetUsagePayment");
		if(datasetUsagePaymentMap != null) {
			datasetUsagePaymentMap.put("requestId", requestId);
			response = method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId + "/payments", (Map<String, Object>) params.get("datasetUsagePayment"));
			location = response.header("location");
			long payId = Long.parseLong(location.replace("/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId + "/payments/", ""));
			resMap.put("payId"    , payId);
		}
		
		//STEP 3. ???????????? ???????????? ??????(?????????)
		Map<String, Object> datasetUsageReceptionMap = (Map<String, Object>) params.get("datasetUsageReception");
		if(datasetUsageReceptionMap != null) {
			datasetUsageReceptionMap.put("requestId", requestId);
			response = method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId + "/receptions", (Map<String, Object>) params.get("datasetUsageReception"));
			location = response.header("location");
			long recvId = Long.parseLong(location.replace("/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId + "/receptions/", ""));
			resMap.put("recvId"    , recvId);
		}
		//STEP 4. ???????????? ?????? ??????
		String requestType = (String) params.get("requestType");
		if (StringUtil.isNotEmpty(requestType)) {
			subscriptionMethod(props.getDatapublishMsApiUrl()+"/subscriptionRegister/"+datasetId, datasetId);
		}
		
		return toJson(resMap);
	}
	/**
	 * <pre>???????????? ???????????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 17.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void modifyDatasetUsage(Map<String, Object> params) throws Exception {
		//STEP 1. ???????????? ??????
		String datasetId = (String) params.get("datasetId");
		long requestId = (long) params.get("id");
		method(HttpMethod.PATCH.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId, params);
		
		
		//STEP 2. ???????????? ???????????? ?????? ??????(?????????)
		Map<String, Object> datasetUsagePaymentMap = (Map<String, Object>)params.get("datasetUsagePayment");
		if(datasetUsagePaymentMap != null) {
			long payId = (long) datasetUsagePaymentMap.get("id");
			method(HttpMethod.PATCH.toString(), props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + datasetId + "/requestUsage/" + requestId + "/payments/" + payId, (Map<String, Object>) params.get("datasetUsagePayment"));
		}
		
		//STEP 3. ???????????? ???????????? ??????(?????????)
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
	 * <pre>???????????? ???????????? ??????</pre>
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
	 * ????????? ?????? ?????? ??????
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
	 * ????????? ?????? ?????? ??????
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
	 * <pre>????????? ?????? ???????????? ?????? ??????</pre>
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
	 * <pre>????????? ?????? ???????????? ?????? ??????</pre>
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
	 * ????????? ???????????? ?????? ??????
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
	 * ????????? ???????????? ?????? ??????
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
	 * ???????????? ?????? ??????
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
	 * <pre>???????????? ?????? ??????</pre>
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
	 * <pre>????????? ?????? ??????</pre>
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
	 * <pre>?????? ????????? ??????</pre>
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
	 * <pre>????????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 7.
	 * @param datasetVo
	 * @return
	 * @throws Exception
	 */
	public String createDatasetSatisfaction(Map<String, Object> params) throws Exception {
		return this.strBody(method(HttpMethod.POST.toString(), props.getDatapublishMsApiUrl() +"/dataservice/dataset/" + params.get("datasetId") + "/rating", params));
	}

	/**
	 * <pre>???????????? ?????? ??????</pre>
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
	 * <pre>???????????? ??????</pre>
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
	 * <pre>???????????? ??????</pre>
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
	 * <pre>???????????? ??????</pre>
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
	 * <pre>???????????? ??? ?????????????????? ?????? ?????? ?????? ??????</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 4. 28.
	 * @return
	 * @throws Exception
	 */
	public String getDatasetEndPoint() throws Exception {
		return props.getGatewayPublicIp();
	}

	/**
	 * <pre>?????? ?????? ?????? ?????? ??????</pre>
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
	 * <pre>???????????? ?????? ?????? ?????? ??????</pre>
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
		mqttIp.put("mqttIp", properties.getMqttIp());
		webSocketIp.put("webSocketIp", properties.getWebsocketIp());
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
	 * <pre>???????????? ???????????? ?????? ?????? ??????</pre>
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
	 * <pre>???????????? ???????????? ?????? ?????? ??????</pre>
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
	 * <pre>???????????? ???????????? ?????? ?????? ?????? ??????(?????????)</pre>
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
	 * <pre>???????????? ???????????? ?????? ?????? ?????? ??????(?????????)</pre>
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
	 * <pre>???????????????????????? Alive ??????</pre>
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
	 * <pre>???????????? ???????????? ?????? ?????? ?????? ?????? (???????????????) </pre>
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
	 * <pre>???????????? ???????????? ?????? ?????? ?????? (???????????????)</pre>
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
