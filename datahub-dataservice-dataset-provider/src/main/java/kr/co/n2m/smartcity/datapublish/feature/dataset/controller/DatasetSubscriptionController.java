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
package kr.co.n2m.smartcity.datapublish.feature.dataset.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.co.n2m.smartcity.datapublish.common.component.HttpComponent;
import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.kafka.KafkaConst;
import kr.co.n2m.smartcity.datapublish.common.kafka.KafkaMessageCode;
import kr.co.n2m.smartcity.datapublish.common.kafka.Producer;
import kr.co.n2m.smartcity.datapublish.common.retrofit.ThreadRetrofitService;
import kr.co.n2m.smartcity.datapublish.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.DatasetService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.columns.DatasetColumnsService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.requestUsage.DatasetRequestUsageService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.searchCondition.DatasetSearchConditionService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.EndpointVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.EntityVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.NotificationVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.SubScriptionVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.DatasetOutputVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.SrchDatasetOutputVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.DatasetSearchInfoVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.SrchDatasetSearchInfoVo;

@RestController
public class DatasetSubscriptionController extends HttpComponent{
	@Autowired private DatasetMapper datasetMapper;
	@Autowired Producer kafkaProducer;
	@Autowired DatasetService datasetService;
	@Autowired DatasetColumnsService datasetColumnsService;
	@Autowired ThreadRetrofitService threadRetrofitService;
	@Autowired DatasetRequestUsageService datasetRequestUsageService;
	@Autowired DatasetSearchConditionService datasetSearchConditionService;
	
	@Value("${smartcity.datacore.server}")
	private String dataCoreApiUrl;
	
	@Value("${spring.serverIp}")
	private String serverIp;
	
	@PostMapping(value="/notification/{datasetId}", produces = "application/json; charset=utf8")
	public ResponseEntity<Object> subscription(@RequestBody String param,@PathVariable String datasetId) throws Exception {
		Map<String, Object> params = toMap(param);
		Map<String, Object> sendDataMap = new HashMap<>();
		try {
			String dsOid = datasetId;
			Map<String, Object> representation = new HashMap<>();
			
			Gson gsonParam = new Gson();
			System.out.println(gsonParam.toJson(params.get("data")));
			String convertDataMap = gsonParam.toJson(params.get("data"));
			JsonParser parser = new JsonParser();
			JsonArray jsonArray = (JsonArray) parser.parse(convertDataMap);
			for (int i = 0; i < jsonArray.size(); i++) {
				System.out.println(jsonArray.get(i));
				representation = toMap(jsonArray.get(i).toString());
			}
			
			// 1. eventReference (인스턴스ID) 기준으로 실시간 데이터 제공 대상자 목록 조회
			List<DatasetUseRequestVo> targetList = datasetService.selectDsOidDatasetUseRequestPushList(dsOid);
			
			if ( targetList != null && targetList.size() > 0 ) {
				for ( DatasetUseRequestVo target : targetList) {
					String resultRepresentation = "";
					
					//=====================================================================================================================
					//이용신청 대상자 유효성체크
					Map<String, String> validParams = new HashMap<>();
					validParams.put("datasetId"  , target.getDatasetId());
					validParams.put("loginUserId", target.getUserId());
					
					//1. datasetId를 이용하여, 해당 데이터셋 출력정보를 조회한다.
					SrchDatasetOutputVo srchDatasetOutputVo = new SrchDatasetOutputVo();
					srchDatasetOutputVo.setDatasetId(target.getDatasetId());
					List<DatasetOutputVo> outputList = datasetColumnsService.getDatasetOutputList(srchDatasetOutputVo);
					if (outputList == null || outputList.size() == 0) {
						throw new GlobalException(5005, "데이터셋의 출력정보가 존재하지 않습니다. : " + target.getDatasetId());
					}
					
					//registration 일 경우
					if ("registration".equals(target.getStatus())) {
						//false일 경우에는 조건을 체크한 후 map remove을 진행 
						SrchDatasetSearchInfoVo         srchDatasetSearchInfoVo         = new SrchDatasetSearchInfoVo();
						srchDatasetSearchInfoVo.setDatasetId(target.getDatasetId());
						List<DatasetSearchInfoVo> datasetSearchInfoList = datasetSearchConditionService.getDatasetSearchInfoList(srchDatasetSearchInfoVo);
						
						//실 데이터값 추출
						if ( datasetSearchInfoList != null && datasetSearchInfoList.size() > 0 ) {
							for ( DatasetSearchInfoVo targetInfo : datasetSearchInfoList) {
								String choiceAttribute = targetInfo.getMainAttribute();
								String attribute = "";
								Map<String, Object> mapAttribute = new HashMap<>();
								if (!"".equals(targetInfo.getSubAttribute())) {
									Gson gson = new Gson();
									if ("".equals(representation.getOrDefault(choiceAttribute, ""))) { continue; }
									String convertMap = gson.toJson(representation.getOrDefault(choiceAttribute, ""));
									mapAttribute = toMap(convertMap.toString());
									String converMapAttribute = gson.toJson(mapAttribute.getOrDefault("value", ""));
									Map<String, Object> representationConvert = toMap(converMapAttribute);
									mapAttribute = representationConvert;
									attribute = (String) representationConvert.getOrDefault(targetInfo.getSubAttribute(), "").toString();
								}else {
									Map<String, Object> representationConvert = toMap((String) representation.getOrDefault(choiceAttribute, "").toString());
									attribute = (String) representationConvert.getOrDefault("value", "").toString();
								}
								//찾을 데이터가 없을 경우 continue~
								if ("".equals(attribute)) {	continue; }
								
								switch (targetInfo.getSymbolName()) {
								case "==":
									if (attribute.equals(targetInfo.getValue())) {
										break;
									}else {
										if (!"".equals(targetInfo.getSubAttribute())) {
											mapAttribute.remove(targetInfo.getSubAttribute());
											representation.put(choiceAttribute,mapAttribute);
										}else {
											representation.remove(choiceAttribute);
										}
									}
									break;
								case "!=":
									
									if (!attribute.equals(targetInfo.getValue())) {
										break;
									}else {
										if (!"".equals(targetInfo.getSubAttribute())) {
											mapAttribute.remove(targetInfo.getSubAttribute());
											representation.put(choiceAttribute,mapAttribute);
										}else {
											representation.remove(choiceAttribute);
										}
									}
									break;
								case ">":
									
									if (Integer.parseInt((attribute)) > Integer.parseInt(targetInfo.getValue())) {
										break;
									}else {
										if (!"".equals(targetInfo.getSubAttribute())) {
											mapAttribute.remove(targetInfo.getSubAttribute());
											representation.put(choiceAttribute,mapAttribute);
										}else {
											representation.remove(choiceAttribute);
										}
									}
									break;
								case ">=":
									
									if (Integer.parseInt((attribute)) >= Integer.parseInt(targetInfo.getValue())) {
										break;
									}else {
										if (!"".equals(targetInfo.getSubAttribute())) {
											mapAttribute.remove(targetInfo.getSubAttribute());
											representation.put(choiceAttribute,mapAttribute);
										}else {
											representation.remove(choiceAttribute);
										}
									}
									break;
								case "<":
									
									if (Integer.parseInt((attribute)) < Integer.parseInt(targetInfo.getValue())) {
										break;
									}else {
										if (!"".equals(targetInfo.getSubAttribute())) {
											mapAttribute.remove(targetInfo.getSubAttribute());
											representation.put(choiceAttribute,mapAttribute);
										}else {
											representation.remove(choiceAttribute);
										}
									}
									break;
								case "<=":
									
									if (Integer.parseInt((attribute)) <= Integer.parseInt(targetInfo.getValue())) {
										break;
									}else {
										if (!"".equals(targetInfo.getSubAttribute())) {
											mapAttribute.remove(targetInfo.getSubAttribute());
											representation.put(choiceAttribute,mapAttribute);
										}else {
											representation.remove(choiceAttribute);
										}
									}
									break;
								case "~=":
									
									if (attribute.contains(targetInfo.getValue())) {
										break;
									}else {
										if (!"".equals(targetInfo.getSubAttribute())) {
											mapAttribute.remove(targetInfo.getSubAttribute());
											representation.put(choiceAttribute,mapAttribute);
										}else {
											representation.remove(choiceAttribute);
										}
									}
									break;
								case "!~=":
									
									if (!attribute.contains(targetInfo.getValue())) {
										break;
									}else {
										if (!"".equals(targetInfo.getSubAttribute())) {
											mapAttribute.remove(targetInfo.getSubAttribute());
											representation.put(choiceAttribute,mapAttribute);
										}else {
											representation.remove(choiceAttribute);
										}
									}
									break;
								}
								
							}
						}
					}
					
					resultRepresentation = threadRetrofitService.getConvertData(outputList, representation);
					
					//2. 각 사용자마다 설정된 별칭컬럼명으로 보여주기 위하여 clear후 put한다. 
					sendDataMap.clear();
					sendDataMap.put("startMs"        , KafkaConst.DATAPUBLISH.ms());
					sendDataMap.put("destinationMs"  , KafkaConst.NOTIFICATION.ms());
					sendDataMap.put("code"           , KafkaMessageCode.INGESTLAYER_TO_DATA_PUBLISH.message());
					sendDataMap.put("status"         , params.getOrDefault("status", ""));
					sendDataMap.put("timestamp"      , StringUtil.getCurrentDateString());
					sendDataMap.put("dsOid"          , params.getOrDefault("dsOid", ""));
					sendDataMap.put("data"           , resultRepresentation);
					sendDataMap.put("message"        , params.getOrDefault("message", ""));
					
					//유효성 체크 후
					if (datasetRequestUsageService.isMatchedDatasetUsage(validParams)) { 
						// 3. 이용신청 대상자로 topic 전송
						sendDataMap.put("sendUrl", target.getNotificationUrl());
						kafkaProducer.send(KafkaConst.NOTIFICATION.topic(), sendDataMap);
					}
					//=====================================================================================================================
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtil.makeResponseEntity();
		
	}
	
	@PostMapping(value="/subscriptionRegister/{datasetId}", produces = "application/json; charset=utf8")
	@Transactional
	public ResponseEntity<Object> subscriptionDatasetId(@RequestBody String param,@PathVariable String datasetId,SrchDatasetUseRequestVo srchDatasetUseRequestVo) throws Exception {
		SubScriptionVo subScriptionVo = new SubScriptionVo();
		EndpointVo endpointVo = new EndpointVo();
		NotificationVo notificationVo = new NotificationVo();
		
		List<String> reDatasetId = datasetMapper.selectDsOriginId(datasetId);
		List<EntityVo> entityVos = new ArrayList<>();
		String type = datasetMapper.selectDatasetSubscriptionType(srchDatasetUseRequestVo);
		List<DatasetUseRequestVo> id = datasetMapper.selectDatasetSubscriptionId(srchDatasetUseRequestVo);
		
		if (id.size() > 0) {
			for (DatasetUseRequestVo subscriptionId : id) {
				EntityVo entityVo = new EntityVo();
				entityVo.setId(subscriptionId.getIds());
				entityVo.setType(type);
				entityVos.add(entityVo);
			}
		}else {
			EntityVo entityVo = new EntityVo();
			entityVo.setType(type);
			entityVos.add(entityVo);
		}
		
		endpointVo.setAccept("application/json");
		endpointVo.setUri("http://"+serverIp+"/notification/"+datasetId);
		
		notificationVo.setEndpoint(endpointVo);
		notificationVo.setFormat("normalized");
		
		
		subScriptionVo.setType("Subscription");
		subScriptionVo.setEntities(entityVos);
		subScriptionVo.setDatasetIds(reDatasetId);
		subScriptionVo.setNotification(notificationVo);
		subScriptionVo.setExpires("2050-11-15T20:10:00.000+09:00");
		
		Gson gson = new Gson();
		Map<String, Object> headers = new HashMap<>();
		
		headers.put("Content-Type", "application/json");
		int datasetSubscriptionCount = datasetMapper.selectDatasetSubscriptionCount(srchDatasetUseRequestVo);
		
		//subcription 구독자가 한명이라도 있으면 return 구독자는 계속 1명
		if(datasetSubscriptionCount <= 0) {
			String subscription = subscriptionMethod(dataCoreApiUrl+"/subscriptions", gson.toJson(subScriptionVo));
			
			JsonObject jobj = new Gson().fromJson(subscription, JsonObject.class);
			String setSubscriptionId = jobj.get("id").getAsString();
			
			srchDatasetUseRequestVo.setDatasetId(datasetId);
			srchDatasetUseRequestVo.setSubscriptionId(setSubscriptionId);
			
			datasetMapper.insertDataseSubScriptiontUsage(srchDatasetUseRequestVo);
		}
		return ResponseUtil.makeResponseEntity();
	}
}
