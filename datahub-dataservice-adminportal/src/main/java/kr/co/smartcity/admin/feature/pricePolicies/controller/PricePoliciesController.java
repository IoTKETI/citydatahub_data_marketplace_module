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
package kr.co.smartcity.admin.feature.pricePolicies.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.admin.common.component.CommonComponent;
import kr.co.smartcity.admin.feature.pricePolicies.service.PricePoliciesService;
import kr.co.smartcity.admin.feature.pricePolicies.vo.SrchPricePoliciesVo;


@Controller
@RequestMapping(value="/pricePolicies")
public class PricePoliciesController extends CommonComponent{

	@Autowired PricePoliciesService pricePoliciesService;
	
	/**
	 * <pre>가격정책 기본 목록 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @return
	 */
	@GetMapping(value="/pageList.do")
	public String pagePricePoliciesList() {
		return "pricePolicies/pricePoliciesList";
	}
	/**
	 * <pre>가격정책 기본 등록 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @return
	 */
	@GetMapping(value="/pageRegist.do")
	public String pagePricePoliciesRegist() {
		return "pricePolicies/pricePoliciesRegist";
	}
	/**
	 * <pre>가격정책 기본 상세 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @return
	 */
	@GetMapping(value="/pageDetail.do")
	public String pagePricePoliciesDetail() {
		return "pricePolicies/pricePoliciesDetail";
	}
	/**
	 * <pre>가격정책 기본 수정 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 9.
	 * @return
	 */
	@GetMapping(value="/pageModify.do")
	public String pagePricePoliciesModify() {
		return "pricePolicies/pricePoliciesModify";
	}
	
	/**
	 * <pre>가격정책 기본 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param policyId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/getList.do")
	public @ResponseBody Map<String, Object> getPricePoliciesList(SrchPricePoliciesVo srchPricePoliciesVo) throws Exception {
		return pricePoliciesService.getPricePoliciesList(srchPricePoliciesVo);
	}
	
	/**
	 * <pre>가격정책 기본 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param srchPricePoliciesVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/create.do")
	public @ResponseBody void createPricePolicies(@RequestBody SrchPricePoliciesVo srchPricePoliciesVo) throws Exception {
		pricePoliciesService.createPricePolicies(srchPricePoliciesVo);
	}
	
	/**
	 * <pre>가격정책 기본 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param policyId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/get.do")
	public @ResponseBody Map<String, Object> getPricePolicies(long policyId) throws Exception {
		return pricePoliciesService.getPricePolicies(policyId);
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
	@PatchMapping(value="/modify.do")
	public @ResponseBody void modifyPricePolicies(@RequestBody SrchPricePoliciesVo srchPricePoliciesVo) throws Exception {
		pricePoliciesService.modifyPricePolicies(srchPricePoliciesVo);
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
	@DeleteMapping(value="/remove.do")
	public @ResponseBody void deletePricePolicies(@RequestBody SrchPricePoliciesVo srchPricePoliciesVo) throws Exception {
		pricePoliciesService.deletePricePolicies(srchPricePoliciesVo);
	}
}
