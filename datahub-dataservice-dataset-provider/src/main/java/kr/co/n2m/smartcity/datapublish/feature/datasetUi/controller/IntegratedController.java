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
package kr.co.n2m.smartcity.datapublish.feature.datasetUi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.n2m.smartcity.datapublish.common.component.CommonComponent;
import kr.co.n2m.smartcity.datapublish.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.SrchDatasetVo;
import kr.co.n2m.smartcity.datapublish.feature.datasetUi.service.IntegratedService;
import kr.co.n2m.smartcity.datapublish.feature.datasetUi.vo.SrchIntegratedVo;

@RestController
public class IntegratedController extends CommonComponent{
//	Logger logger = (Logger) LoggerFactory.getLogger(IntegratedController.class);
	@Autowired IntegratedService integratedService;
	
	/**
	 * <pre>데이터셋 카운트 조회(전체, 파일, API)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 21.
	 * @param loginUserId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/integrated/dataset/count")
	public ResponseEntity<Object> getDatasetCount(@RequestHeader(value="UserId", required=false) String loginUserId, SrchDatasetVo srchDatasetVo) throws Exception {
		srchDatasetVo.setLoginUserId(loginUserId);
		
		Map<String, Object> resMap = integratedService.getDatasetCount(srchDatasetVo);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	/**
	 * <pre>데이터셋 카테고리별 카운트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 28.
	 * @param loginUserId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/integrated/datasetByCategory/count")
	public ResponseEntity<Object> getDatasetByCategoryCount(SrchDatasetVo srchDatasetVo) throws Exception {
		Map<String, Object> resMap = integratedService.getDatasetByCategoryCount(srchDatasetVo);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * <pre>데이터셋 목록 조회(비표준)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 15.
	 * @param loginUserId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/integrated/dataset")
	public ResponseEntity<Object> getDatasetList(@RequestHeader(value="UserId", required=false) String loginUserId, SrchDatasetVo srchDatasetVo, HttpServletRequest request) throws Exception {
		srchDatasetVo.setLoginUserId(loginUserId);
		
		Map<String, Object> resMap = integratedService.getDatasetList(srchDatasetVo);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * <pre>데이터셋 상세 조회(비표준)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 15.
	 * @param loginUserId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/integrated/dataset/{datasetId}")
	public ResponseEntity<Object> getDatasetList(@RequestHeader(value="UserId", required=false) String loginUserId, @PathVariable String datasetId, SrchDatasetVo srchDatasetVo) throws Exception {
		srchDatasetVo.setLoginUserId(loginUserId);
		
		Map<String, Object> resMap = integratedService.getDataset(datasetId, loginUserId, "yes".equals(srchDatasetVo.getNohit()));
		return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * <pre>개발 데이터셋 등록(비표준)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 17.
	 * @param userId
	 * @param datasetMap
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value="/integrated/dataset")
	public Object createDataset(@RequestHeader("UserId") String loginUserId, @RequestParam Map<String, Object> datasetMap, HttpServletRequest request) throws Exception {
		try {
			datasetMap.put("datasetOriginList"       , gson.fromJson((String)datasetMap.get("datasetOriginList")    , List.class));
			datasetMap.put("datasetOutputList"       , gson.fromJson((String)datasetMap.get("datasetOutputList")    , List.class));
			datasetMap.put("datasetInstanceList"     , gson.fromJson((String)datasetMap.get("datasetInstanceList")  , List.class));
			datasetMap.put("datasetSearchInfoList"   , gson.fromJson((String)datasetMap.get("datasetSearchInfoList"), List.class));
		}catch(Exception e) { }
		
		String datasetStr = toJson(datasetMap);
		SrchDatasetVo srchDatasetVo = gson.fromJson(datasetStr, SrchDatasetVo.class);
		srchDatasetVo.setLoginUserId(loginUserId);
		String resultKey = integratedService.createDataset(srchDatasetVo, request);

		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED, "/integrated/dataset/" + resultKey);
	}
	
	/**
	 * <pre>개발 데이터셋 수정(비표준)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 17.
	 * @param loginUserId
	 * @param devDatasetId
	 * @param datasetMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/integrated/dataset/{datasetId}")
	public ResponseEntity<Object> modifyDataset(@RequestHeader("UserId") String loginUserId, @PathVariable String datasetId, @RequestParam Map<String, Object> datasetMap, HttpServletRequest request) throws Exception {
		try {
			datasetMap.put("datasetOriginList"        , gson.fromJson((String)datasetMap.get("datasetOriginList")        , List.class));
			datasetMap.put("datasetOutputList"        , gson.fromJson((String)datasetMap.get("datasetOutputList")        , List.class));
			datasetMap.put("datasetInstanceList"      , gson.fromJson((String)datasetMap.get("datasetInstanceList")      , List.class));
			datasetMap.put("datasetSearchInfoList"    , gson.fromJson((String)datasetMap.get("datasetSearchInfoList")    , List.class));
			datasetMap.put("deleteFileOids"           , gson.fromJson((String)datasetMap.get("deleteFileOids")           , List.class));
		}catch(Exception e) { }
		
		String datasetStr = toJson(datasetMap);
		SrchDatasetVo srchDatasetVo = gson.fromJson(datasetStr, SrchDatasetVo.class);
		
		StringUtil.compareUniqueId(datasetId, srchDatasetVo.getId());
		srchDatasetVo.setLoginUserId(loginUserId);
		
		integratedService.modifyDataset(srchDatasetVo, request);
		return ResponseUtil.makeResponseEntity();
	}
	
	/**
	 * <pre>데이터셋 관심상품 목록 조회(비표준)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 15.
	 * @param loginUserId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/integrated/datasetWishlist")
	public ResponseEntity<Object> getDatasetWishlistList(@RequestHeader("UserId") String loginUserId, SrchDatasetVo srchDatasetVo) throws Exception {
		srchDatasetVo.setLoginUserId(loginUserId);
		
		Map<String, Object> resMap = integratedService.getDatasetWishlistList(srchDatasetVo);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * <pre>데이터셋 이용신청 목록 조회(비표준)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 15.
	 * @param loginUserId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/integrated/datasetApplications")
	public ResponseEntity<Object> getDatasetUsageList(@RequestHeader("UserId") String loginUserId, SrchDatasetVo srchDatasetVo) throws Exception {
		srchDatasetVo.setLoginUserId(loginUserId);
	
		Map<String, Object> resMap = integratedService.getDatasetUsageList(srchDatasetVo);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * <pre>데이터셋 이용신청 환불 내역 목록 조회(비표준)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 15.
	 * @param loginUserId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/integrated/datasetUsageRefundsUser")
	public ResponseEntity<Object> getDatasetUsageRefundsListByUser(@RequestHeader("UserId") String loginUserId, SrchIntegratedVo srchIntegratedVo) throws Exception {
		srchIntegratedVo.setLoginUserId(loginUserId);
		return ResponseUtil.makeResponseEntity(integratedService.getDatasetUsageRefundsListByUser(srchIntegratedVo));
	}
	/**
	 * <pre>데이터셋 이용신청 환불 내역 목록 조회(비표준)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 15.
	 * @param loginUserId
	 * @param srchDatasetVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/integrated/datasetUsageRefundsProvider")
	public ResponseEntity<Object> getDatasetUsageRefundsListByProvider(@RequestHeader("UserId") String loginUserId, SrchIntegratedVo srchIntegratedVo) throws Exception {
		srchIntegratedVo.setLoginUserId(loginUserId);
		return ResponseUtil.makeResponseEntity(integratedService.getDatasetUsageRefundsListByProvider(srchIntegratedVo));
	}
}
