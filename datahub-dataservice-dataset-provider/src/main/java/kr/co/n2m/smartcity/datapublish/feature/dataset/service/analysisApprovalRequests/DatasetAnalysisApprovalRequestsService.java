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
package kr.co.n2m.smartcity.datapublish.feature.dataset.service.analysisApprovalRequests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.n2m.smartcity.datapublish.common.component.CommonComponent;
import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.common.vo.PageVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.analysisApprovalRequests.DataAnalystRequestModelVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.analysisApprovalRequests.DataAnalystRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.analysisApprovalRequests.SrchDataAnalystRequestModelVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.analysisApprovalRequests.SrchDataAnalystRequestVo;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class DatasetAnalysisApprovalRequestsService extends CommonComponent{

	@Autowired 
	private DatasetMapper datasetMapper;
	
	@Value("${smartcity.datamanager.server}")
	private String datamanagerServerUrl;
	
	@Autowired
	@Qualifier("customHttpClient")
	private OkHttpClient client;
	
	/**
	 * <pre>데이터 분석가승인요청 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDataAnalystRequestVo
	 * @return
	 */
	@Transactional
	public String createDataAnalystRequest(SrchDataAnalystRequestVo srchDataAnalystRequestVo) {
		String resultKey = "";
		try {
			srchDataAnalystRequestVo.setId(StringUtil.generateKey());
			datasetMapper.insertDataAnalystRequest(srchDataAnalystRequestVo);
			
			resultKey = String.valueOf(srchDataAnalystRequestVo.getId());
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}

	/**
	 * <pre>데이터 분석가승인요청 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 15.
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 */
	public Map<String, Object> getDataAnalystRequestList(SrchDataAnalystRequestVo srchDataAnalystRequestVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			int totalListSize = datasetMapper.selectDataAnalystRequestCount(srchDataAnalystRequestVo);
			srchDataAnalystRequestVo.setTotalListSize(totalListSize);
			List<DataAnalystRequestVo> dataAnalystRequestList = datasetMapper.selectDataAnalystRequestList(srchDataAnalystRequestVo);

			if(srchDataAnalystRequestVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchDataAnalystRequestVo);
				resMap.put("page", pageVo);
			}else{
				resMap.put("page", null);
			}
			
			resMap.put("list", dataAnalystRequestList);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resMap;
	}

	/**
	 * <pre>데이터 분석가승인요청 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 15.
	 * @param srchDatamodelAnalystRequestVo
	 * @return
	 */
	public DataAnalystRequestVo getDataAnalystRequest(long requestId) {
		DataAnalystRequestVo dataAnalystRequestVo = null;
		try {
			dataAnalystRequestVo = datasetMapper.selectDataAnalystRequest(requestId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return dataAnalystRequestVo;
	}

	/**
	 * <pre>데이터 분석가승인요청 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDataAnalystRequestVo
	 * @throws Exception
	 */
	public void modifyDataAnalystRequest(SrchDataAnalystRequestVo srchDataAnalystRequestVo) throws Exception {
		try {
			int iRet = datasetMapper.updateDataAnalystRequest(srchDataAnalystRequestVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

	/**
	 * <pre>데이터 분석가승인 삭제</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 10. 6.
	 * @param reqeustId
	 * @throws Exception
	 */
	public void removeDataAnalystRequest(long reqeustId) throws Exception {
		try {
			int iRet = datasetMapper.deleteDataAnalystRequest(reqeustId);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * <pre>데이터 분석가 승인 모델 목록 조회 (요청자 기준) - 인터페이스 전용</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 10. 6.
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getInfDataAnalystRequestModelList(String userId) {
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			List<String> modelList = datasetMapper.selectInfDataAnalystRequestModelList(userId);
			
			// 데이터 모델 리스트 
			Request.Builder builder = new Request.Builder();
			Request request = builder
					.addHeader("Accept", "application/json")
					.url(HttpUrl.parse(datamanagerServerUrl + "/datamodels").newBuilder().build())
					.get()
					.build();
			
			Response response = client.newCall(request).execute();
			List<Map<String, Object>> dataModels = toList(response.body().string());
		
			for (String model : modelList) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("id", model);
				map.put("name", "");
				
				for (Map<String, Object> m : dataModels) {
					
					String id = (String) m.get("id");
					if (model.equals(id) && StringUtil.isNotEmpty((String) m.get("typeUri"))) {
						map.put("name", m.get("typeUri"));
						break;
					}
				}
				resultList.add(map);
			}
			
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultList;
	}

	/**
	 * <pre>데이터 분석 모델 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDataAnalystRequestModelVo
	 * @return
	 */
	public List<DataAnalystRequestModelVo> getDataAnalystRequestModelList(SrchDataAnalystRequestModelVo srchDataAnalystRequestModelVo) {
		List<DataAnalystRequestModelVo> dataAnalystRequestModelList = null;
		try {
			dataAnalystRequestModelList = datasetMapper.selectDataAnalystRequestModelList(srchDataAnalystRequestModelVo);
			
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return dataAnalystRequestModelList;
	}

	/**
	 * <pre>데이터 분석 모델 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDataAnalystRequestModelVo
	 * @return
	 */
	public DataAnalystRequestModelVo getDataAnalystRequestModel(String requestId, String modelId) {
		DataAnalystRequestModelVo dataAnalystRequestModelVo = null;
		try {
			dataAnalystRequestModelVo = datasetMapper.selectDataAnalystRequestModel(requestId, modelId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return dataAnalystRequestModelVo;
	}

	/**
	 * <pre>데이터 분석 모델 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDataAnalystRequestModelVo
	 * @return
	 */
	@Transactional
	public String createDataAnalystRequestModel(SrchDataAnalystRequestModelVo srchDataAnalystRequestModelVo) {
		String resultKey = "";
		try {
			srchDataAnalystRequestModelVo.setId(StringUtil.generateKey());
			datasetMapper.insertDataAnalystRequestModel(srchDataAnalystRequestModelVo);
			resultKey = String.valueOf(srchDataAnalystRequestModelVo.getId());
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}

	/**
	 * <pre>데이터 분석 모델 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDataAnalystRequestModelVo
	 * @throws Exception
	 */
	@Transactional
	public void deleteDataAnalystRequestModel(SrchDataAnalystRequestModelVo srchDataAnalystRequestModelVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDataAnalystRequestModel(srchDataAnalystRequestModelVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

}
