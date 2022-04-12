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
package kr.co.n2m.smartcity.datapublish.feature.dataset.service.datasetAdaptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.datapublish.common.kafka.KafkaConst;
import kr.co.n2m.smartcity.datapublish.common.kafka.Producer;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.common.vo.PageVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor.DatasetAdaptorFieldVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor.DatasetAdaptorVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor.SrchDatasetAdaptorFieldVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor.SrchDatasetAdaptorVo;

@Service
public class DatasetAdaptorService {

	@Autowired 
	private Producer kafkaProducer;
	
	@Autowired 
	private DatasetMapper datasetMapper;
	
	/**
	 * <pre>데이터셋 어댑터 신청 내역 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 4.
	 * @param datasetAdaptorVo
	 * @return
	 */
	public Map<String, Object> getDatasetAdaptorList(SrchDatasetAdaptorVo srchDatasetAdaptorVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			int totalListSize = datasetMapper.selectDatasetAdaptorCount(srchDatasetAdaptorVo);
			srchDatasetAdaptorVo.setTotalListSize(totalListSize);
			List<DatasetAdaptorVo> list = datasetMapper.selectDatasetAdaptorList(srchDatasetAdaptorVo);
			
			if(srchDatasetAdaptorVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchDatasetAdaptorVo);
				resMap.put("page", pageVo);
			}else{
				resMap.put("page", null);
			}
			
			resMap.put("list", list);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resMap;
	}

	/**
	 * 
	 * <pre>데이터셋 어댑터 신청 내역 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 11.
	 * @param datasetAdaptorVo
	 * @return
	 */
	public DatasetAdaptorVo getDatasetAdaptor(String datasetAdapterId) {
		DatasetAdaptorVo datasetAdaptorVo = null;
		try {
			datasetAdaptorVo = datasetMapper.selectDatasetAdaptor(datasetAdapterId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetAdaptorVo;
	}
	
	/**
	 * <pre>데이터셋 어댑터 신청</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 4.
	 * @param datasetAdaptorVo
	 * @return
	 */
	@Transactional
	public String createDatasetAdaptor(SrchDatasetAdaptorVo srchDatasetAdaptorVo) {
		String resultKey = "";
		try{
			srchDatasetAdaptorVo.setId(StringUtil.generateKey());
			datasetMapper.insertDatasetAdaptor(srchDatasetAdaptorVo);
			
			
			/**
			 * 유저정보
			 */
			Map<String, Object> userMap = new HashMap<>();
			userMap.put("id"    , srchDatasetAdaptorVo.getLoginUserId());
			userMap.put("name"  , "");
			userMap.put("phone" , "");
			userMap.put("email" , "");
			
			/**
			 * 전송정보
			 */
			Map<String, Object> dataTransferMap = new HashMap<>();
			dataTransferMap.put("type"        , srchDatasetAdaptorVo.getTransferType());
			dataTransferMap.put("description" , srchDatasetAdaptorVo.getDescription());
			
			/**
			 * 필드정보
			 */
			List<Map<String, Object>> dataFieldList = new ArrayList<>();
			for(DatasetAdaptorFieldVo fieldVo : srchDatasetAdaptorVo.getFieldList()) {
				Map<String, Object> fieldMap = new HashMap<>();
				fieldMap.put("sequence"    , fieldVo.getOrder());
				fieldMap.put("fieldKrName" , fieldVo.getFieldKrName());
				fieldMap.put("fieldEnName" , fieldVo.getFieldEnName());
				fieldMap.put("type"        , fieldVo.getDataType());
				fieldMap.put("description" , fieldVo.getDescription());
				dataFieldList.add(fieldMap);
			}
			
			
			Map<String, Object> sendDataMap = new HashMap<>();
			sendDataMap.put("adSrId"         , srchDatasetAdaptorVo.getId());
			sendDataMap.put("adSrStatus"     , "AD_SR_CD_001");
			sendDataMap.put("adSrRegistrant" , userMap);
			sendDataMap.put("dataTransfer"   , dataTransferMap);
			sendDataMap.put("dataModel"      , srchDatasetAdaptorVo.getModelId());
			sendDataMap.put("dataFields"     , dataFieldList);
			sendDataMap.put("createdAt"      , StringUtil.getCurrentDate("yyyy-MM-dd'T'HH:mm:ss"));
			
			kafkaProducer.send(KafkaConst.DATASET_ADAPTOR_USER_EVENT.topic(), sendDataMap);
			
			resultKey = String.valueOf(srchDatasetAdaptorVo.getId());
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}
	
	/**
	 * <pre>데이터셋 어댑터 삭제요청</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 4.
	 * @param datasetAdaptorVo
	 * @return
	 */
	public void deleteDatasetAdaptor(SrchDatasetAdaptorVo srchDatasetAdaptorVo) {
		try{
			Map<String, Object> sendDataMap = new HashMap<>();
			
			sendDataMap.put("adSrId"         , srchDatasetAdaptorVo.getId());
			sendDataMap.put("adSrStatus"     , "AD_SR_CD_004");
			sendDataMap.put("modifiedAt"     , StringUtil.getCurrentDate("yyyy-MM-dd'T'HH:mm:ss"));
			
			kafkaProducer.send(KafkaConst.DATASET_ADAPTOR_USER_EVENT.topic(), sendDataMap);
			
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	/**
	 * <pre>데이터셋 어댑터 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 12. 4.
	 * @param datasetAdaptorVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyDatasetAdaptor(SrchDatasetAdaptorVo srchDatasetAdaptorVo) throws Exception {
		try{
			if(StringUtil.equals(srchDatasetAdaptorVo.getStatus(), "AR_SR_CD_002")) {
				int iRet = datasetMapper.selectDatasetAdaptorByStatus(srchDatasetAdaptorVo);
				if(iRet == 0) {
					iRet = datasetMapper.updateDatasetAdaptor(srchDatasetAdaptorVo);
					if(iRet <= 0) throw new NotFoundException();
				}
			}else {
				int iRet = datasetMapper.updateDatasetAdaptor(srchDatasetAdaptorVo);
				if(iRet <= 0) throw new NotFoundException();
			}
			
			
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * <pre>데이터셋 어댑터 출력정보 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 8.
	 * @param srchDatasetAdaptorFieldVo
	 * @return
	 */
	public List<DatasetAdaptorFieldVo> getDatasetAdaptorFieldList(SrchDatasetAdaptorFieldVo srchDatasetAdaptorFieldVo) {
		List<DatasetAdaptorFieldVo> datasetAdaptorFieldList = null;
		try {
			datasetAdaptorFieldList = datasetMapper.selectDatasetAdaptorFieldList(srchDatasetAdaptorFieldVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetAdaptorFieldList;
	}

	/**
	 * <pre>데이터셋 어댑터 출력정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 8.
	 * @param srchDatasetAdaptorFieldVo
	 * @return
	 */
	@Transactional
	public String createDatasetAdaptorField(SrchDatasetAdaptorFieldVo srchDatasetAdaptorFieldVo) {
		String resultKey = "";
		try {
			srchDatasetAdaptorFieldVo.setId(StringUtil.generateKey());
			datasetMapper.insertDatasetAdaptorField(srchDatasetAdaptorFieldVo);
			resultKey = String.valueOf(srchDatasetAdaptorFieldVo.getId());
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}

	/**
	 * <pre>데이터셋 어댑터 출력정보 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 8.
	 * @param srchDatasetAdaptorFieldVo
	 * @return
	 */
	public DatasetAdaptorFieldVo getDatasetAdaptorField(String datasetAdapterId, String columnId) {
		DatasetAdaptorFieldVo datasetAdaptorFieldVo = null;
		try {
			datasetAdaptorFieldVo = datasetMapper.selectDatasetAdaptorField(datasetAdapterId, columnId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetAdaptorFieldVo;
	}

	/**
	 * <pre>데이터셋 어댑터 출력정보 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 8.
	 * @param srchDatasetAdaptorFieldVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyDatasetAdaptorField(SrchDatasetAdaptorFieldVo srchDatasetAdaptorFieldVo) throws Exception {
		try {
			int iRet = datasetMapper.updateDatasetAdaptorField(srchDatasetAdaptorFieldVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

	/**
	 * <pre>데이터셋 어댑터 출력정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 8.
	 * @param srchDatasetAdaptorFieldVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void deleteDatasetAdaptorField(SrchDatasetAdaptorFieldVo srchDatasetAdaptorFieldVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDatasetAdaptorField(srchDatasetAdaptorFieldVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

}
