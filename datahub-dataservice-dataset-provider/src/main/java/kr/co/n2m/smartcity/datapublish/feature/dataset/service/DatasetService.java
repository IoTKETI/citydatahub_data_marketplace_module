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
package kr.co.n2m.smartcity.datapublish.feature.dataset.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.common.vo.PageVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.DatasetVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.SrchDatasetVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.sample.SimpleDatasetVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.usageHistory.SrchDatasetUseHistoryVo;

@Service
public class DatasetService {

	@Autowired 
	private DatasetMapper datasetMapper;
	
	/**
	 * 데이터셋 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 10.
	 * @param datasetProdVo
	 * @return
	 */
	public Map<String, Object> getDatasetList(SrchDatasetVo srchDatasetVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			int totalListSize = datasetMapper.selectDatasetCount(srchDatasetVo);
			srchDatasetVo.setTotalListSize(totalListSize);
			List<DatasetVo> list = datasetMapper.selectDatasetList(srchDatasetVo);
			
			if(srchDatasetVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchDatasetVo);
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
	 * <pre>데이터셋 상세</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 8.
	 * @param datasetId
	 * @param nohit
	 * @return
	 * @throws Exception 
	 */
	public DatasetVo getDataset(String datasetId, boolean nohit) throws Exception {
		DatasetVo datasetVo = null;
		try {
			if(!nohit) {
				int iRet = datasetMapper.updateDatasetHit(datasetId);
				if(iRet <= 0) throw new NotFoundException();
			}
			datasetVo = datasetMapper.selectDataset(datasetId);
			
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetVo;
	}
	
	/**
	 * <pre>데이터셋(출시) 기본 정보만 조회</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 5. 21.
	 * @param datasetId
	 * @return
	 */
	public SimpleDatasetVo getSimpleDatasetProd(String datasetId) {
		try {
			return datasetMapper.selectSimpleDataset(datasetId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * <pre>실시간 데이터 제공 대상자 목록 조회</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 11. 6.
	 * @param diiId
	 * @return
	 */
	public List<DatasetUseRequestVo> selectDatasetUseRequestPushList(String diiId) {
		return datasetMapper.selectDatasetUseRequestPushList(diiId);
	}
	
	/**
	 * <pre>데이터셋 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	public String createDataset(SrchDatasetVo srchDatasetVo) throws Exception {
		String resultKey = StringUtil.generateDatasetKey();
		try {
			srchDatasetVo.setId(resultKey);
			datasetMapper.insertDataset(srchDatasetVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}

	/**
	 * <pre>데이터셋 수정(전체수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	public void modifyDataset(SrchDatasetVo srchDatasetVo) throws Exception {
		try {
			int iRet = datasetMapper.updateDataset(srchDatasetVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		
	}

	
	/**
	 * <pre>데이터셋 수정(부분수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	public void patchDataset(SrchDatasetVo srchDatasetVo) throws Exception {
		try {
			int iRet = datasetMapper.updateDatasetPart(srchDatasetVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

	/**
	 * <pre>데이터셋 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	public void deleteDataset(SrchDatasetVo srchDatasetVo) throws Exception {
		try {
			datasetMapper.deleteDataset(srchDatasetVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * <pre>데이터셋 승인이력 저장</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param srchDatasetVo
	 * @throws Exception
	 */
	public void createDatasetApprovalHistory(SrchDatasetVo srchDatasetVo) throws Exception {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("id"         , StringUtil.generateKey());
			params.put("status"     , srchDatasetVo.getStatus());
			params.put("datasetId"  , srchDatasetVo.getId());
			params.put("loginUserId", srchDatasetVo.getLoginUserId());
			datasetMapper.insertDatasetApprovalHistory(params);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

	/**
	 * <pre>데이터셋 활용내역 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 24.
	 * @param srchDatasetUseHistoryVo
	 */
	public void createDatasetUseHistory(SrchDatasetUseHistoryVo srchDatasetUseHistoryVo) {
		datasetMapper.insertDatasetUseHistory(srchDatasetUseHistoryVo);
	}
	
	/**
	 * <pre>데이터셋 oid 실시간 데이터 제공 대상자 목록 조회</pre>
	 * @Author		: sw-kim
	 * @Date		: 2021. 10. 5.
	 * @param dsOid
	 * @return
	 */
	public List<DatasetUseRequestVo> selectDsOidDatasetUseRequestPushList(String dsOid) {
		return datasetMapper.selectDsOidDatasetUseRequestPushList(dsOid);
	}
	
	/**
	 * <pre>데이터셋 oid 실시간 데이터 제공 대상자 목록 조회</pre>
	 * @Author		: sw-kim
	 * @Date		: 2021. 10. 5.
	 * @param dsOid
	 * @return
	 */
	public String selectDatasetSubscriptionType(SrchDatasetUseRequestVo srchDatasetUseRequestVo) {
		return datasetMapper.selectDatasetSubscriptionType(srchDatasetUseRequestVo);
		
	}
	
	public void deleteModelAboutInfo(String dsOid) {
		datasetMapper.deleteDatasetModelDsByDsId(dsOid);
		datasetMapper.deleteDatasetInstanceInfoByDsId(dsOid);
		datasetMapper.deleteDatasetOutputInfoByDsId(dsOid);
		datasetMapper.deleteDatasetSearchInfoByDsId(dsOid);
	}
}
