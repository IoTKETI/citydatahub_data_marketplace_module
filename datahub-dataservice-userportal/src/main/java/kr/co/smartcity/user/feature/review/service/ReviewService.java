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
package kr.co.smartcity.user.feature.review.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.user.common.CommonConst;
import kr.co.smartcity.user.common.component.HttpComponent;
import kr.co.smartcity.user.feature.dataset.service.DatasetService;

@Service("reviewService")
public class ReviewService extends HttpComponent {
	@Autowired
	DatasetService datasetService;
	
	/**
	 * 활용후기 목록
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getReviewList(Map<String, String> params) throws Exception {
		Map<String, Object> resMap = null;
		
		try {
			setSessionAttribute(CommonConst.SEARCH_PREFIX, "REVIEW_LIST", params);
			resMap = toMap(get(props.getPortalMsApiUrl() +"/dataserviceUi/review", params));
			
			Map<String, String> keyPairMap = new HashMap<>();
			keyPairMap.put("noticeCreUsrId"    , "noticeCreUsrNm");
			putUserNameForMap(resMap, keyPairMap);
			
		} catch (Exception e) {
			throw e;
		}
		return toJson(resMap);
		
	}
	
	/**
	 * 활용후기 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public String createReview(Map<String, Object> params, HttpServletRequest request) throws Exception {
		return this.strBody(method(HttpMethod.POST.toString(), props.getPortalMsApiUrl() +"/dataserviceUi/review", params, request));
	}
	
	/**
	 * 활용후기 상세
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getReview(Map<String, String> params) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		
		try {
			String resultJson = get(props.getPortalMsApiUrl() +"/dataserviceUi/review/" + params.get("reviewOid"));
			
			resMap = toMap(resultJson);
			
			Map<String, Object> reviewData = (Map<String, Object>) resMap.get("review");
			List<Map<String, Object>> datasetList = (List<Map<String, Object>>) resMap.get("reviewDatasetList");
			
			List<String> dsOidList = new ArrayList<>();
			for (Map<String, Object> data : datasetList) {
				dsOidList.add((String) data.get("devDatasetId"));
			}
			
			Map<String, String> schParam = new HashMap<>();
			schParam.put("dsOidList", dsOidList.toString().replaceAll("\\[|\\]",""));
			
			List<Map<String,Object>> reviewDatasetList = new ArrayList<>();
			for(String dsOid : dsOidList) {
				schParam.put("id", dsOid);
				String datasetJson = datasetService.getDataset(schParam);
				Map<String, Object> datasetMap = toMap(datasetJson);
				reviewDatasetList.add(datasetMap);
			}
			
			resultMap.put("reviewDatasetList", reviewDatasetList);
			resultMap.put("review", reviewData);
		}catch(Exception e) {
			throw e;
		}
		
		return toJson(resultMap);
	}
	
	/**
	 * 활용후기 수정
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public String modifyReview(Map<String, Object> params, HttpServletRequest request) throws Exception {
		return this.strBody(method(HttpMethod.PUT.toString(), props.getPortalMsApiUrl() +"/dataserviceUi/review/"+ params.get("reviewOid"), params, request));
	}
	
	/**
	 * 활용후기 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String removeReview(Map<String, Object> params) throws Exception {
		return this.strBody(method(HttpMethod.DELETE.toString(), props.getPortalMsApiUrl() +"/dataserviceUi/review/" + params.get("reviewOid"), params));
	}

	/**
	 * 
	 * <pre>활용후기 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param params
	 * @param res
	 */
	public void downloadReviewFile(Map<String, Object> params, HttpServletResponse res) {
		String url = props.getPortalMsApiUrl() + "/dataserviceUi/review/"+ params.get("reviewOid") +"/attachFiles/"+params.get("fileId");
		this.downloadFile(url, params, res);
	}
	
}
