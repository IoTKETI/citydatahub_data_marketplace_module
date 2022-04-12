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
package kr.co.n2m.smartcity.datapublish.feature.dataset.service.searchCondition;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.n2m.smartcity.datapublish.common.exceptions.ConflictException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.DatasetSearchInfoVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.SrchDatasetSearchInfoVo;

@Service
public class DatasetSearchConditionService {
	@Autowired 
	private DatasetMapper datasetMapper;
	
	/**
	 * <pre>데이터셋 조회조건 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetSearchInfoVo
	 * @return
	 */
	public List<DatasetSearchInfoVo> getDatasetSearchInfoList(SrchDatasetSearchInfoVo srchDatasetSearchInfoVo) {
		List<DatasetSearchInfoVo> datasetSearchInfoList = null;
		try {
			datasetSearchInfoList = datasetMapper.selectDatasetSearchInfoList(srchDatasetSearchInfoVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetSearchInfoList;
	}
	
	/**
	 * <pre>데이터셋 조회조건 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetSearchInfoVo
	 * @throws ConflictException 
	 */
	@Transactional
	public void createDatasetSearchInfo(SrchDatasetSearchInfoVo srchDatasetSearchInfoVo) throws Exception {
		
		try {
			DatasetSearchInfoVo datasetSearchInfoVo = datasetMapper.selectDatasetSearchInfo(srchDatasetSearchInfoVo);
			if(datasetSearchInfoVo != null) throw new ConflictException();
			srchDatasetSearchInfoVo.setId(String.valueOf(StringUtil.generateKey()));
			datasetMapper.insertDatasetSearchInfo(srchDatasetSearchInfoVo);
		}catch(ConflictException ce) {
			throw ce;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

	/**
	 * <pre>데이터셋 조회조건 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetSearchInfoVo
	 * @throws Exception 
	 */
	@Transactional
	public void updateDatasetSearchInfo(SrchDatasetSearchInfoVo srchDatasetSearchInfoVo) throws Exception {
		try {
			int iRet = datasetMapper.updateDatasetSearchInfo(srchDatasetSearchInfoVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * <pre>데이터셋 조회조건 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetSearchInfoVo
	 * @throws Exception 
	 */
	@Transactional
	public void deleteDatasetSearchInfo(SrchDatasetSearchInfoVo srchDatasetSearchInfoVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDatasetSearchInfo(srchDatasetSearchInfoVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
}
