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
package kr.co.n2m.smartcity.datapublish.feature.data.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.n2m.smartcity.datapublish.feature.data.service.DataService;

@RestController
public class DataController {
	
	@Autowired
	private DataService dataService;
	
	/**
	 * 
	 * <pre>데이터셋 이용신청을 한 사용자에게만 데이터(Open API) Polling 방식으로 데이터를 제공 한다.</pre>
	 * 
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @param datasetId	: 데이터셋고유번호
	 * @param authToken	: 인증토큰
	 * @param params	: 요청 파라미터
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="/dataservice/dataset/{datasetId}/data")
	public ResponseEntity<Object> getDataList(@PathVariable String datasetId, @RequestHeader("UserId") String loginUserId, @RequestParam Map<String, String> params, HttpServletRequest req) throws Exception {
		return dataService.getDataList(datasetId, loginUserId, params, false);
	}
	
	@GetMapping(value="/dataservice/dataset/{datasetId}/check")
	public ResponseEntity<Object> checkDataList(@PathVariable String datasetId, @RequestHeader("UserId") String loginUserId, @RequestParam Map<String, String> params, HttpServletRequest req) throws Exception {
		return dataService.checkDataList(datasetId, loginUserId, params);
	}
	
	@GetMapping(value="/dataservice/dataset/{datasetId}/file")
	public void downloadDataListExcel(@PathVariable String datasetId, @RequestHeader("UserId") String loginUserId, @RequestParam Map<String, String> params, HttpServletResponse response) throws Exception {
		dataService.downloadDataListExcel(datasetId, loginUserId, params, response);
	}
}
