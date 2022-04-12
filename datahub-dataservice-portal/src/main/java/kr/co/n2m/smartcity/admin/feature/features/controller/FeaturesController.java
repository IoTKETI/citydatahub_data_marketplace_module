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
package kr.co.n2m.smartcity.admin.feature.features.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.n2m.smartcity.admin.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.admin.feature.features.service.FeaturesService;
import kr.co.n2m.smartcity.admin.feature.features.vo.FeaturesVo;
import kr.co.n2m.smartcity.admin.feature.features.vo.SrchFeaturesVo;


@RestController
public class FeaturesController {
	
	@Autowired
	private FeaturesService featuresService;
	
	/**
	 * <pre>확장기능 관리 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 4.
	 * @param srchFeaturesVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/features")
	public ResponseEntity<Object> getFeaturesList(SrchFeaturesVo srchFeaturesVo) {
		List<FeaturesVo> featuresList = featuresService.getFeaturesList(srchFeaturesVo);
		return ResponseUtil.makeResponseEntity(featuresList);
	}
	
	/**
	 * <pre>확장기능 관리 등록(수정)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 5.
	 * @param srchFeaturesVo
	 * @return
	 */
	@PutMapping("/dataserviceUi/features")
	public ResponseEntity<Object> createFeatures(@RequestBody SrchFeaturesVo srchFeaturesVo) {
		featuresService.createFeatures(srchFeaturesVo);
		return ResponseUtil.makeResponseEntity(HttpStatus.OK);
	}
	
}

