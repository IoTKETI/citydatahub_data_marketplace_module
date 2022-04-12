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
package kr.co.n2m.smartcity.datapublish.feature.kafka.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import kr.co.n2m.smartcity.datapublish.common.component.CommonComponent;
import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.kafka.KafkaConst;
import kr.co.n2m.smartcity.datapublish.common.kafka.KafkaMessageCode;
import kr.co.n2m.smartcity.datapublish.common.kafka.Producer;
import kr.co.n2m.smartcity.datapublish.common.retrofit.ThreadRetrofitService;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.common.service.TableService;
import kr.co.n2m.smartcity.datapublish.feature.common.vo.CodeGroupVo;
import kr.co.n2m.smartcity.datapublish.feature.common.vo.CodeVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.DatasetService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.columns.DatasetColumnsService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.datasetAdaptor.DatasetAdaptorService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.requestUsage.DatasetRequestUsageService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.DatasetOutputVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.SrchDatasetOutputVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor.SrchDatasetAdaptorVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestVo;

@Component
public class ThreadService extends CommonComponent {
	
	private static final Logger LOG = LoggerFactory.getLogger(ThreadService.class);
	
	@Autowired Producer kafkaProducer;
	@Autowired DatasetService datasetService;
	@Autowired DatasetAdaptorService datasetAdaptorService;
	@Autowired TableService tableService;
	@Autowired DatasetRequestUsageService datasetRequestUsageService;
	@Autowired DatasetColumnsService datasetColumnsService;
	@Autowired ThreadRetrofitService threadRetrofitService;
	
	/**
	 * Kafka 메시지 토픽 처리
	 * 1) 데이터셋 활용자 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 24.
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@Async("threadPoolTaskReceiver")
	public Future<String> process(String message) throws Exception {
		
		Map<String, Object> params = toMap(message);
		
		String code = (String) params.get("code");
		KafkaMessageCode messageCode = KafkaMessageCode.getMessageCode(code);
		
		Map<String, Object> sendDataMap = new HashMap<>();
		
		switch(messageCode) {
		case INGESTLAYER_TO_DATA_PUBLISH:
			{
				try {
					String data = (String) params.get("data");
					Map<String, Object> mapData = toMap(data);
					
					
					String eventReference = (String) mapData.getOrDefault("eventReference", "");
					Map<String, Object> representation = (Map<String, Object>) mapData.get("representation"); 
				
					logger(eventReference + " ==> " + representation);
					
					if (!StringUtil.isEmpty(eventReference)) {
						// 1. eventReference (인스턴스ID) 기준으로 실시간 데이터 제공 대상자 목록 조회
						List<DatasetUseRequestVo> targetList = datasetService.selectDatasetUseRequestPushList(eventReference);
						
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
					}
				} catch (Exception ex) {
				}
				break;
			}
		case PROC_DATASET_ADAPTOR_MOD:
			{
				try {
					String data = (String) params.get("data");
					Map<String, Object> mapData = toMap(data);
					//TODO: 어댑터 연결 및 폐기 완료 이후 처리
					
					SrchDatasetAdaptorVo srchDatasetAdaptorVo = new SrchDatasetAdaptorVo();
					srchDatasetAdaptorVo.setId(Double.valueOf((double) mapData.get("adSrId")).longValue());
					srchDatasetAdaptorVo.setStatus((String)mapData.get("adSrStatus"));
					srchDatasetAdaptorVo.setModifiedAt((String)mapData.get("modifiedAt"));
					
					datasetAdaptorService.modifyDatasetAdaptor(srchDatasetAdaptorVo);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				break;
			}
		case CODEGROUP_DATA_REG_SYNC:
			{
				Map<String, Object> tableData = (Map<String, Object>) params.get("data");
				CodeGroupVo codeGroupVo = new CodeGroupVo();
				BeanUtils.populate(codeGroupVo, tableData);
				tableService.createCodeGroup(codeGroupVo);
			}
			break;
		case CODE_DATA_REG_SYNC:
			{
				Map<String, Object> tableData = (Map<String, Object>) params.get("data");
				CodeVo codeVo = new CodeVo();
				BeanUtils.populate(codeVo, tableData);
				tableService.createCode(codeVo);
			}
			break;
		case CODEGROUP_DATA_MOD_SYNC:
			{
				Map<String, Object> tableData = (Map<String, Object>) params.get("data");
				CodeGroupVo codeGroupVo = new CodeGroupVo();
				BeanUtils.populate(codeGroupVo, tableData);
				tableService.modifyCodeGroup(codeGroupVo);
			}
		case CODE_DATA_MOD_SYNC:
			{
				Map<String, Object> tableData = (Map<String, Object>) params.get("data");
				CodeVo codeVo = new CodeVo();
				BeanUtils.populate(codeVo, tableData);
				tableService.modifyCode(codeVo);
			}
		default:
			break;
		}
		return new AsyncResult<String>("Success");
	}
}
