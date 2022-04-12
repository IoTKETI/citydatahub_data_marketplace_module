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
package kr.co.n2m.smartcity.datapublish.feature.pricePolicies.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import kr.co.n2m.smartcity.datapublish.common.component.CommonComponent;
import kr.co.n2m.smartcity.datapublish.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.datapublish.feature.pricePolicies.service.PricePoliciesService;
import kr.co.n2m.smartcity.datapublish.feature.pricePolicies.vo.PricePoliciesPeriodVo;
import kr.co.n2m.smartcity.datapublish.feature.pricePolicies.vo.PricePoliciesVo;
import kr.co.n2m.smartcity.datapublish.feature.pricePolicies.vo.SrchPricePoliciesPeriodVo;
import kr.co.n2m.smartcity.datapublish.feature.pricePolicies.vo.SrchPricePoliciesVo;

@RestController
public class PricePoliciesController extends CommonComponent{

	@Autowired PricePoliciesService pricePoliciesService;
	
	/**
	 * <pre>가격정책 기본 목록조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param srchPricePoliciesVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/pricePolicies")
	public ResponseEntity<Object> getPricePoliciesList(SrchPricePoliciesVo srchPricePoliciesVo) throws Exception {
		Map<String, Object> pricePoliciesList =  pricePoliciesService.getPricePoliciesList(srchPricePoliciesVo);
		return ResponseUtil.makeResponseEntity(pricePoliciesList);
	}
	
	/**
	 * <pre>가격정책 기본 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param srchPricePoliciesVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/pricePolicies")
	public ResponseEntity<Object> createPricePolicies(@RequestHeader("UserId") String loginUserId, @RequestBody SrchPricePoliciesVo srchPricePoliciesVo) throws Exception {
		srchPricePoliciesVo.setLoginUserId(loginUserId);
		long resultKey = pricePoliciesService.createPricePolicies(srchPricePoliciesVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/pricePolicies/" + resultKey);
	}
	
	/**
	 * <pre>가격정책 기본 상세조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param policyId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/pricePolicies/{policyId}")
	public ResponseEntity<Object> getPricePolicies(@PathVariable long policyId) throws Exception {
		PricePoliciesVo pricePoliciesVo =  pricePoliciesService.getPricePolicies(policyId);
		return ResponseUtil.makeResponseEntity(pricePoliciesVo);
	}
	
	/**
	 * <pre>가격정책 기본 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param policyId
	 * @param srchPricePoliciesVo
	 * @return
	 * @throws Exception
	 */
	@PatchMapping(value="/dataservice/pricePolicies/{policyId}")
	public ResponseEntity<Object> modifyPricePolicies(@RequestHeader("UserId") String loginUserId, @PathVariable long policyId, @RequestBody SrchPricePoliciesVo srchPricePoliciesVo) throws Exception {
		srchPricePoliciesVo.setLoginUserId(loginUserId);
		pricePoliciesService.modifyPricePolicies(srchPricePoliciesVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>가격정책 기본 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param policyId
	 * @param srchPricePoliciesVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/pricePolicies/{policyId}")
	public ResponseEntity<Object> deletePricePolicies(@RequestHeader("UserId") String loginUserId, @PathVariable long policyId, @RequestBody SrchPricePoliciesVo srchPricePoliciesVo) throws Exception {
		pricePoliciesService.deletePricePolicies(srchPricePoliciesVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
	
	//==========================================================================================================
	
	/**
	 * <pre>가격정책 제공기간 목록조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param policyId
	 * @param srchPricePoliciesPeriodVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/dataservice/pricePolicies/{policyId}/period")
	public ResponseEntity<Object> getPricePoliciesPeriodList(@PathVariable String policyId, SrchPricePoliciesPeriodVo srchPricePoliciesPeriodVo) throws Exception {
		List<PricePoliciesPeriodVo> pricePoliciesPeriodList =  pricePoliciesService.getPricePoliciesPeriodList(srchPricePoliciesPeriodVo);
		return ResponseUtil.makeResponseEntity(pricePoliciesPeriodList);
	}
	
	/**
	 * <pre>가격정책 제공기간 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param policyId
	 * @param srchPricePoliciesPeriodVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/dataservice/pricePolicies/{policyId}/period")
	public ResponseEntity<Object> createPricePoliciesPeriod(@RequestHeader("UserId") String loginUserId, @PathVariable long policyId, @RequestBody SrchPricePoliciesPeriodVo srchPricePoliciesPeriodVo) throws Exception {
		long resultKey = pricePoliciesService.createPricePoliciesPeriod(srchPricePoliciesPeriodVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/dataservice/pricePolicies/" + policyId + "/period/" + resultKey);
	}
	
	/**
	 * <pre>가격정책 제공기간 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param policyId
	 * @param periodId
	 * @param srchPricePoliciesPeriodVo
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value="/dataservice/pricePolicies/{policyId}/period/{periodId}")
	public ResponseEntity<Object> deletePricePoliciesPeriod(@PathVariable String policyId, @PathVariable String periodId, @RequestBody SrchPricePoliciesPeriodVo srchPricePoliciesPeriodVo) throws Exception {
		pricePoliciesService.deletePricePoliciesPeriod(srchPricePoliciesPeriodVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.NO_CONTENT);
	}
}
