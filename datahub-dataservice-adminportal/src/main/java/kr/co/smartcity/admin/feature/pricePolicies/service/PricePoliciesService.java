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
package kr.co.smartcity.admin.feature.pricePolicies.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.feature.pricePolicies.vo.SrchPricePoliciesPeriodVo;
import kr.co.smartcity.admin.feature.pricePolicies.vo.SrchPricePoliciesVo;
import okhttp3.Response;

@Service
public class PricePoliciesService extends HttpComponent {

	/**
	 * <pre>가격정책 기본 목록조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @param srchPricePoliciesVo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPricePoliciesList(SrchPricePoliciesVo srchPricePoliciesVo) throws Exception{
		Map<String, Object> resMap = toMap(get(props.getDatapublishMsApiUrl() + "/dataservice/pricePolicies", srchPricePoliciesVo));

		Map<String, String> keyPairMap = new HashMap<>();
		keyPairMap.put("creatorId"   , "creatorNm");
		putUserNameForMap(resMap, keyPairMap);

		return resMap;
	}

	/**
	 * <pre>가격정책 기본 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @param policyId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPricePolicies(long policyId) throws Exception {
		SrchPricePoliciesPeriodVo srchPricePoliciesPeriodVo = new SrchPricePoliciesPeriodVo();
		srchPricePoliciesPeriodVo.setPolicyId(policyId);
		
		String policyJson = get(props.getDatapublishMsApiUrl() + "/dataservice/pricePolicies/" + policyId);
		String policyPeriodListJson = get(props.getDatapublishMsApiUrl() + "/dataservice/pricePolicies/" + policyId + "/period", srchPricePoliciesPeriodVo);
		
		Map<String, Object> policyMap = toMap(policyJson);
		List<Map<String, Object>> periodList = toList(policyPeriodListJson);
		policyMap.put("pricePoliciesPeriodList", periodList);
		return policyMap;
	}

	/**
	 * <pre>가격정책 기본 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @param srchPricePoliciesVo
	 * @return
	 * @throws Exception 
	 */
	public void createPricePolicies(SrchPricePoliciesVo srchPricePoliciesVo) throws Exception {
		Response response = method(HttpMethod.POST, props.getDatapublishMsApiUrl() + "/dataservice/pricePolicies", srchPricePoliciesVo, null);
		String location = response.header("location");
		long policyId = Long.parseLong(location.replace("/dataservice/pricePolicies/", ""));
		
		String[] periodCdArr = srchPricePoliciesVo.getPeriodCdArr();
		for(String periodCd : periodCdArr) {
			SrchPricePoliciesPeriodVo srchPricePoliciesPeriodVo = new SrchPricePoliciesPeriodVo();
			srchPricePoliciesPeriodVo.setPolicyId(policyId);
			srchPricePoliciesPeriodVo.setPeriodCd(periodCd);
			method(HttpMethod.POST, props.getDatapublishMsApiUrl() + "/dataservice/pricePolicies/" + policyId + "/period", srchPricePoliciesPeriodVo);
		}
	}

	/**
	 * <pre>가격정책 기본 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @param srchPricePoliciesVo
	 * @return 
	 * @throws Exception
	 */
	public void modifyPricePolicies(SrchPricePoliciesVo srchPricePoliciesVo) throws Exception {
		//step 1. 가격정책 수정
		long policyId = srchPricePoliciesVo.getId();
		method(HttpMethod.PATCH, props.getDatapublishMsApiUrl() + "/dataservice/pricePolicies/" + policyId, srchPricePoliciesVo);
		
		//step 2. 가격정책 제공기간 삭제
		List<SrchPricePoliciesPeriodVo> pricePoliciesPeriodList = srchPricePoliciesVo.getPricePoliciesPeriodList();
		for(SrchPricePoliciesPeriodVo srchPricePoliciesPeriodVo : pricePoliciesPeriodList) {
			long periodId = srchPricePoliciesPeriodVo.getId();
			method(HttpMethod.DELETE, props.getDatapublishMsApiUrl() + "/dataservice/pricePolicies/" + policyId + "/period/" + periodId, srchPricePoliciesPeriodVo);
		}
		
		//step 3. 가격정책 제공기간 등록
		String[] periodCdArr = srchPricePoliciesVo.getPeriodCdArr();
		for(String periodCd : periodCdArr) {
			SrchPricePoliciesPeriodVo srchPricePoliciesPeriodVo = new SrchPricePoliciesPeriodVo();
			srchPricePoliciesPeriodVo.setPolicyId(policyId);
			srchPricePoliciesPeriodVo.setPeriodCd(periodCd);
			method(HttpMethod.POST, props.getDatapublishMsApiUrl() + "/dataservice/pricePolicies/" + policyId + "/period", srchPricePoliciesPeriodVo);
		}
		
	}

	/**
	 * <pre>가격정책 기본 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @param srchPricePoliciesVo
	 * @return 
	 * @throws Exception 
	 */
	public void deletePricePolicies(SrchPricePoliciesVo srchPricePoliciesVo) throws Exception {
		//step 1. 가격정책 삭제
		long policyId = srchPricePoliciesVo.getId();
		method(HttpMethod.DELETE, props.getDatapublishMsApiUrl() + "/dataservice/pricePolicies/" + srchPricePoliciesVo.getId(), srchPricePoliciesVo);
		
		//step 2. 가격정책 제공기간 삭제
		List<SrchPricePoliciesPeriodVo> pricePoliciesPeriodList = srchPricePoliciesVo.getPricePoliciesPeriodList();
		for(SrchPricePoliciesPeriodVo srchPricePoliciesPeriodVo : pricePoliciesPeriodList) {
			long periodId = srchPricePoliciesPeriodVo.getId();
			method(HttpMethod.DELETE, props.getDatapublishMsApiUrl() + "/dataservice/pricePolicies/" + policyId + "/period/" + periodId, srchPricePoliciesPeriodVo);
		}
		
	}
}
