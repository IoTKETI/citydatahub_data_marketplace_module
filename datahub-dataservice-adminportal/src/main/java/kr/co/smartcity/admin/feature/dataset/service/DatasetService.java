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
package kr.co.smartcity.admin.feature.dataset.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.common.util.StringUtil;
import kr.co.smartcity.admin.feature.dataset.vo.DatasetSatisfactionRatingVo;
import kr.co.smartcity.admin.feature.dataset.vo.DatasetUseRequestVo;
import kr.co.smartcity.admin.feature.dataset.vo.DatasetVo;
import kr.co.smartcity.admin.feature.user.service.UserService;

@Service("datasetService")
public class DatasetService extends HttpComponent{
	
	@Autowired UserService userService;

	/**
	 * 데이터셋 목록 조회(출시)
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 21.
	 * @return
	 * @throws Exception 
	 */
	public String getDatasetList(Map<String, Object> params) throws Exception {
		String responseJson = "";
		
		try {
			setSessionAttribute(CommonConst.SEARCH_PREFIX, "DATASET_LIST", params);
			
			params.put("portalType", "admin");
			responseJson = get(props.getDatapublishMsApiUrl() + "/integrated/dataset", params);
		}catch(Exception e) {
			throw e;
		}
		
		return responseJson;
	}
	
	/**
	 * 데이터셋 상세 조회(출시)
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 10.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getDataset(Map<String, Object> params) throws Exception {
		Map<String, Object> datasetMap = new HashMap<>();
		String responseJson = "";
		try {
			responseJson = get(props.getDatapublishMsApiUrl() + "/integrated/dataset/" + params.get("id"));
		
			datasetMap = toMap(responseJson);
			
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
			
			/**
			 * 담당자 정보 가져오기
			 */
//			UserVo modelOwnerInfo = this.getUserByType( (String) datasetMap.get("modelOwnerId"), UserType.NORMAL);
//			
//			if(modelOwnerInfo != null) {
//				String phone = modelOwnerInfo.getPhone();
//				String email = modelOwnerInfo.getEmail();
//				
//				if(StringUtil.isNotEmpty(phone)) {
//					phone = phone.replaceAll("^(01(?:0|1|[6-9]))[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", "$1-****-$3");
//				}
//				
//				if(StringUtil.isNotEmpty(email) && email.indexOf("@") > -1) {
//					String[] emailSplit = email.split("@");
//					String emailId = emailSplit[0];
//					String emailHost = emailSplit[1];
//					String emailIdFront = emailId.substring(0, emailId.length()/2);
//					String emailIdBack = emailId.substring(emailId.length()/2, emailId.length());
//					emailIdFront = emailIdFront.replaceAll("[a-zA-Z]{1}", "*");
//					email = String.format("%s%s@%s", emailIdFront, emailIdBack, emailHost);
//				}
//				
//				datasetMap.put("dsModelOwnerNm"   , modelOwnerInfo.getName());
//				datasetMap.put("dsModelOwnerPhone", phone);
//				datasetMap.put("dsModelOwnerEmail", email);
//			}
			
			/**
			 * 데이터모델명 가져오기( DataLake )
			 */
//			try {
//				Map<String, Object> modelMap = this.toMap(this.getDataModel((String) datasetMap.get("modelId")));
//				if(modelMap != null) {
//					datasetMap.put("modelName", (String) modelMap.get("name"));
//				}
//			} catch (Exception ex) {
//				datasetMap.put("modelName", "");
//			}
			
			
		} catch (Exception e) {
			throw e;
		}
		
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
		String responseJson = "";
		try {
			Map<String, Object> params = toMap(toJson(datasetVo));
			responseJson = multipartMethod(HttpMethod.POST, props.getDatapublishMsApiUrl() + "/integrated/dataset", params, request);
		}catch(Exception e) {
			throw e;
		}
		return responseJson;
	}
	
	/**
	 * 데이터 모델 상세 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 21.
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	public String getDataModel(String modelId) throws Exception {
		String responseJson = "";
		try {
			responseJson = get(props.getDataModelApiUrl() + "/" + modelId);
		}catch(Exception e) {
			throw e;
		}
		return responseJson;
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
		String responseJson = "";
		try {
			Map<String, Object> params = toMap(toJson(datasetVo));
			responseJson = multipartMethod(HttpMethod.POST, props.getDatapublishMsApiUrl() + "/integrated/dataset/" + params.get("id"), params, request);
		}catch(Exception e) {
			throw e;
		}
		return responseJson;
		
	}
	
	/**
	 * <pre>데이터셋 부분 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 29.
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	public String patchDataset(DatasetVo datasetVo) throws Exception {
		Map<String, Object> params = toMap(toJson(datasetVo));
		return method(HttpMethod.PATCH, props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + params.get("id"), params);
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
		String responseJson = "";
		try {
			responseJson = method(HttpMethod.POST, props.getDatapublishMsApiUrl() + "/dataservice/dataset/"+params.get("datasetId")+"/wishlist", params);
		}catch(Exception e) {
			throw e;
		}
		return responseJson;
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
		String responseJson = "";
		try {
			responseJson = method(HttpMethod.DELETE, props.getDatapublishMsApiUrl()+"/dataservice/dataset/"+params.get("datasetId")+"/wishlist/"+params.get("id"), params);
		}catch(Exception e) {
			throw e;
		}
		return responseJson;
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
		String responseJson = "";
		try {
			responseJson =  method(HttpMethod.POST, props.getDatapublishMsApiUrl()+"/dataservice/dataset/" + params.get("datasetId") + "/inquiry", params);
		}catch(Exception e) {
			throw e;
		}
		return responseJson;
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
		String responseJson = "";
		try {
			responseJson = method(HttpMethod.PUT, props.getDatapublishMsApiUrl()+"/dataservice/dataset/" + params.get("datasetId") + "/inquiry/" + params.get("id"), params);
		}catch(Exception e) {
			throw e;
		}
		return responseJson;
	}
	
	/**
	 * 카테고리 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 21.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getCategoryList(Map<String, Object> params) throws Exception {
		String responseJson = "";
		try {
			responseJson = get( props.getDatapublishMsApiUrl() + "/dataservice/datasetCategories", params);
		}catch(Exception e) {
			throw e;
		}
		return responseJson;
		
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
		Map<String, Object> params = new HashMap<>();
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
		Map<String, Object> params = new HashMap<>();
		params.put("dataModelId", datasetVo.getModelId());
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
		Map<String, Object> params = new HashMap<>();
		String modelTypeUri = datasetVo.getModelTypeUri();

		params.put("type", modelTypeUri);
		if (StringUtil.isNotEmpty(datasetVo.getLoginUserId())) {
			params.put("q", String.format("owner==\"%s\"", datasetVo.getLoginUserId()));
		}
		return get(props.getDataEntitiesServerUrl() + "/entities", params);
	}
	
	/**
	 * <pre>데이터셋 이용신청 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 20.
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String createDatasetUsage(Map<String, Object> params, HttpServletRequest request) throws Exception {
		String responseJson = "";
		try {
			String id = params.get("datasetId").toString();
			subscriptionMethod(props.getDatapublishMsApiUrl()+"/subscription/"+id, id);
			responseJson = method(HttpMethod.POST, props.getDatapublishMsApiUrl() + "/dataservice/dataset/"+params.get("datasetId")+"/requestUsage", params);
		}catch(Exception e) {
			throw e;
		}
		
		return responseJson;
	}
	
	/**
	 * <pre>데이터셋 이용신청 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 20.
	 * @param datasetUseRequestVo
	 * @return
	 * @throws Exception
	 */
	public String deleteDatasetUsage(DatasetUseRequestVo datasetUseRequestVo) throws Exception {
		String responseJson = "";
		
		try {
			Map<String, Object> params = toMap(toJson(datasetUseRequestVo));
			responseJson = method(HttpMethod.DELETE, props.getDatapublishMsApiUrl() + "/dataservice/dataset/"+datasetUseRequestVo.getDatasetId()+"/requestUsage/" + datasetUseRequestVo.getId(), params);
		}catch(Exception e) {
			throw e;
		}
		
		return responseJson;
	}
	
	/**
	 * <pre>만족도 평가 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 7.
	 * @param datasetVo
	 * @return
	 * @throws Exception
	 */
	public String createDatasetSatisfaction(DatasetSatisfactionRatingVo datasetSatisfactionRatingVo) throws Exception {
		String responseJson = "";
		
		try {
			responseJson =  method(HttpMethod.POST, props.getDatapublishMsApiUrl()+"/dataservice/dataset/" + datasetSatisfactionRatingVo.getDatasetId() + "/rating", toMap(toJson(datasetSatisfactionRatingVo)));
		}catch(Exception e) {
			throw e;
		}
		
		return responseJson;
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
	public String getSampleData(Map<String, Object> params) throws Exception {
		String responseJson = "";
		
		try {
			
			if (StringUtil.isNotEmpty((String) params.get("time")) && StringUtil.isNotEmpty((String) params.get("endtime")) ) {
				String[] startSplit = ((String) params.get("time")).split(" ");
				String[] endSplit = ((String) params.get("endtime")).split(" ");
				
				params.put("time", startSplit[0] + "T" + startSplit[1] + "Z");
				params.put("endtime", endSplit[0] + "T" + endSplit[1] + "Z");
			}
			
			String id = (String) params.get("id");
			responseJson = get(props.getDatapublishMsApiUrl() + "/dataservice/dataset/" + id + "/sampleData", params);
		} catch (Exception e) {
			throw e;
		}
		
		return responseJson;
	}
	
	/**
	 * <pre>유저 리스트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 7.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getUserList(Map<String, Object> params) throws Exception {
		return get(props.getAdminUserInfoUrl());
	}
	
	/**
	 * <pre>이용신청 후 데이터조회를 위한 대상 서버 주소</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 4. 28.
	 * @return
	 * @throws Exception
	 */
	public String getDatasetEndPoint() throws Exception {
		return props.getGatewayPublicIp();
	}
}
