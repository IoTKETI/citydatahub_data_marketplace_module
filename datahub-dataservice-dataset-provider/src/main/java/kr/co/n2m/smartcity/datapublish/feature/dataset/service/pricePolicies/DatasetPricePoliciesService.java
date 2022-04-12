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
package kr.co.n2m.smartcity.datapublish.feature.dataset.service.pricePolicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.n2m.smartcity.datapublish.common.exceptions.ConflictException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetOrigin.DatasetOriginVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.pricePolicies.DatasetPricePoliciesPeriodPriceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.pricePolicies.DatasetPricePoliciesVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.pricePolicies.SrchDatasetPricePoliciesPeriodPriceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.pricePolicies.SrchDatasetPricePoliciesVo;

@Service
public class DatasetPricePoliciesService {

	@Autowired private DatasetMapper datasetMapper;
	

	/**
	 * <pre>가격정책 연결정보 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesVo
	 * @return
	 */
	public List<DatasetPricePoliciesVo> getDatasetPricePoliciesList(SrchDatasetPricePoliciesVo srchDatasetPricePoliciesVo) {
		List<DatasetPricePoliciesVo> datasetPricePoliciesList = null;
		try {
			datasetPricePoliciesList = datasetMapper.selectDatasetPricePoliciesList(srchDatasetPricePoliciesVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetPricePoliciesList;
	}

	/**
	 * <pre>가격정책 연결정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesVo
	 * @return
	 */
	public String createDatasetPricePolicies(SrchDatasetPricePoliciesVo srchDatasetPricePoliciesVo) {
		String resultKey = "";
		try {
			srchDatasetPricePoliciesVo.setId(StringUtil.generateKey());
			datasetMapper.insertDatasetPricePolicies(srchDatasetPricePoliciesVo);
			resultKey = String.valueOf(srchDatasetPricePoliciesVo.getId());
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}

	/**
	 * <pre>가격정책 연결정보 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 11.
	 * @param datasetId
	 * @param priceId
	 * @return
	 */
	public DatasetPricePoliciesVo getDatasetPricePolicies(long priceId) {
		DatasetPricePoliciesVo datasetPricePoliciesVo = null;
		try {
			datasetPricePoliciesVo = datasetMapper.selectDatasetPricePolicies(priceId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetPricePoliciesVo;
	}

	/**
	 * <pre>가격정책 연결정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesVo
	 * @throws Exception
	 */
	public void deleteDatasetPricePolicies(SrchDatasetPricePoliciesVo srchDatasetPricePoliciesVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDatasetPricePolicies(srchDatasetPricePoliciesVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	/**
	 * <pre>가격정책 금액정보 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesVo
	 * @return
	 */
	public List<DatasetPricePoliciesPeriodPriceVo> getDatasetPricePoliciesPeriodPriceList(SrchDatasetPricePoliciesPeriodPriceVo srchDatasetPricePoliciesPeriodPriceVo) {
		List<DatasetPricePoliciesPeriodPriceVo> datasetPricePoliciesList = null;
		try {
			datasetPricePoliciesList = datasetMapper.selectDatasetPricePoliciesPeriodPriceList(srchDatasetPricePoliciesPeriodPriceVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetPricePoliciesList;
	}
	/**
	 * <pre>가격정책 금액정보 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesVo
	 * @return
	 */
	public String createDatasetPricePoliciesPeriodPrice(SrchDatasetPricePoliciesPeriodPriceVo srchDatasetPricePoliciesPeriodPriceVo) {
		String resultKey = "";
		try {
			srchDatasetPricePoliciesPeriodPriceVo.setId(StringUtil.generateKey());
			datasetMapper.insertDatasetPricePoliciesPeriodPrice(srchDatasetPricePoliciesPeriodPriceVo);
			resultKey = String.valueOf(srchDatasetPricePoliciesPeriodPriceVo.getId());
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}

	/**
	 * <pre>가격정책 금액정보 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesVo
	 * @return
	 */
	public DatasetPricePoliciesPeriodPriceVo getDatasetPricePoliciesPeriodPrice(long priceId, long periodId) {
		DatasetPricePoliciesPeriodPriceVo datasetPricePoliciesPeriodPriceVo = null;
		try {
			datasetPricePoliciesPeriodPriceVo = datasetMapper.selectDatasetPricePoliciesPeriodPrice(priceId, periodId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetPricePoliciesPeriodPriceVo;
	}

	/**
	 * <pre>가격정책 금액정보 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 12.
	 * @param srchDatasetPricePoliciesVo
	 * @return
	 * @throws Exception 
	 */
	public void deleteDatasetPricePoliciesPeriodPrice(SrchDatasetPricePoliciesPeriodPriceVo srchDatasetPricePoliciesPeriodPriceVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDatasetPricePoliciesPeriodPrice(srchDatasetPricePoliciesPeriodPriceVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

}
