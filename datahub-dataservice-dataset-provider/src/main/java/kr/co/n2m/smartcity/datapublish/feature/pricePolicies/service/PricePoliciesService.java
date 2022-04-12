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
package kr.co.n2m.smartcity.datapublish.feature.pricePolicies.service;

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
import kr.co.n2m.smartcity.datapublish.feature.pricePolicies.mapper.PricePoliciesMapper;
import kr.co.n2m.smartcity.datapublish.feature.pricePolicies.vo.PricePoliciesPeriodVo;
import kr.co.n2m.smartcity.datapublish.feature.pricePolicies.vo.PricePoliciesVo;
import kr.co.n2m.smartcity.datapublish.feature.pricePolicies.vo.SrchPricePoliciesPeriodVo;
import kr.co.n2m.smartcity.datapublish.feature.pricePolicies.vo.SrchPricePoliciesVo;

@Service
public class PricePoliciesService {

	@Autowired 
	private PricePoliciesMapper pricePoliciesMapper;

	/**
	 * <pre>가격정책 기본 목록조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @param srchPricePoliciesVo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPricePoliciesList(SrchPricePoliciesVo srchPricePoliciesVo) throws Exception{
		Map<String, Object> resMap = new HashMap<>();
		try {
			int totalListSize = pricePoliciesMapper.selectPricePoliciesCount(srchPricePoliciesVo);
			srchPricePoliciesVo.setTotalListSize(totalListSize);
			List<PricePoliciesVo> list = pricePoliciesMapper.selectPricePoliciesList(srchPricePoliciesVo);
			
			if(srchPricePoliciesVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchPricePoliciesVo);
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
	 * <pre>가격정책 기본 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @param srchPricePoliciesVo
	 * @return
	 */
	public long createPricePolicies(SrchPricePoliciesVo srchPricePoliciesVo) {
		long resultKey = StringUtil.generateKey();
		try {
			srchPricePoliciesVo.setId(resultKey);
			pricePoliciesMapper.insertPricePolicies(srchPricePoliciesVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}

	/**
	 * <pre>가격정책 기본 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @param policyId
	 * @return
	 * @throws Exception
	 */
	public PricePoliciesVo getPricePolicies(long policyId) throws Exception {
		PricePoliciesVo pricePoliciesVo = null;
		try {
			pricePoliciesVo = pricePoliciesMapper.selectPricePolicies(policyId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return pricePoliciesVo;
	}

	/**
	 * <pre>가격정책 기본 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @param srchPricePoliciesVo
	 * @throws Exception
	 */
	public void modifyPricePolicies(SrchPricePoliciesVo srchPricePoliciesVo) throws Exception {
		try {
			int iRet = pricePoliciesMapper.updatePricePolicies(srchPricePoliciesVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

	/**
	 * <pre>가격정책 기본 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @param srchPricePoliciesVo
	 */
	public void deletePricePolicies(SrchPricePoliciesVo srchPricePoliciesVo) {
		try {
			pricePoliciesMapper.deletePricePolicies(srchPricePoliciesVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		
	}

	/**
	 * <pre>가격정책 제공기간 목록조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 10.
	 * @param srchPricePoliciesPeriodVo
	 * @return
	 */
	public List<PricePoliciesPeriodVo> getPricePoliciesPeriodList(SrchPricePoliciesPeriodVo srchPricePoliciesPeriodVo) {
		return pricePoliciesMapper.selectPricePoliciesPeriodList(srchPricePoliciesPeriodVo);
	}

	/**
	 * <pre>가격정책 제공기간 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 10.
	 * @param srchPricePoliciesPeriodVo
	 * @return
	 */
	public long createPricePoliciesPeriod(SrchPricePoliciesPeriodVo srchPricePoliciesPeriodVo) {
		long resultKey = StringUtil.generateKey();
		try {
			srchPricePoliciesPeriodVo.setId(resultKey);
			pricePoliciesMapper.insertPricePoliciesPeriod(srchPricePoliciesPeriodVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}

	/**
	 * <pre>가격정책 제공기간 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 10.
	 * @param srchPricePoliciesPeriodVo
	 */
	public void deletePricePoliciesPeriod(SrchPricePoliciesPeriodVo srchPricePoliciesPeriodVo) {
		try {
			pricePoliciesMapper.deletePricePoliciesPeriod(srchPricePoliciesPeriodVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		
	}
}
