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
package kr.co.smartcity.user.feature.datacomplaint.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.user.common.CommonConst;
import kr.co.smartcity.user.common.component.HttpComponent;

@Service("datacomplaintService")
public class DatacomplaintService extends HttpComponent {

	/**
	 * 
	 * 신고하기 목록 데이터 호출
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getDatacomplaintList(Map<String, String> params) throws Exception {
		Map<String, Object> resMap = null;
		try {
			setSessionAttribute(CommonConst.SEARCH_PREFIX, "DATACOMPLAINT_LIST", params);
			resMap = toMap(get(props.getPortalMsApiUrl() +"/dataserviceUi/complaints", params));
			
			Map<String, String> keyPairMap = new HashMap<>();
			keyPairMap.put("datacomplainCreUsrId"    , "datacomplainCreUsrNm");
			putUserNameForMap(resMap, keyPairMap);
		}catch(Exception e) {
			throw e;
		}
		return toJson(resMap);
	}
	
	/**
	 * 
	 * 신고하기 상세 데이터 호출
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getDatacomplaint(Map<String, String> params) throws Exception {
		Map<String, Object> resMap = null;
		try {
			resMap = toMap(get(props.getPortalMsApiUrl() +"/dataserviceUi/complaints/"+ params.get("datacomplainOid"), params));

			Map<String, String> keyPairMap = new HashMap<>();
			keyPairMap.put("datacomplainCreUsrId"      , "datacomplainCreUsrNm");
			keyPairMap.put("datacomplainAnswerUsrId"   , "datacomplainAnswerUsrNm");
			putUserNameForMap(resMap, keyPairMap);
			
		}catch(Exception e) {
			throw e;
		}
		return toJson(resMap);
	}
	
	/**
	 * 
	 * 신고하기 데이터 등록
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String createDatacomplaint(Map<String, Object> params, HttpServletRequest request) throws Exception {
		return this.strBody(method(HttpMethod.POST.toString(),props.getPortalMsApiUrl() +"/dataserviceUi/complaints", params , request));
	}
	/**
	 * 
	 * 신고하기 답변 수정
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String modifyDatacomplaintanswer(Map<String, Object> params, HttpServletRequest request) throws Exception {
		return this.strBody(method(HttpMethod.PATCH.toString(),props.getPortalMsApiUrl() +"/dataserviceUi/complaints/"+ params.get("datacomplainOid"), params, request));
	}
	/**
	 * 
	 * 신고하기 삭제
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String deleteDatacomplaint(Map<String, Object> params) throws Exception {
		return this.strBody(method(HttpMethod.DELETE.toString(),props.getPortalMsApiUrl() +"/dataserviceUi/complaints/"+ params.get("datacomplainOid"), params));
	}
	/**
	 * 
	 * 신고하기 질문수정
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String modifyDatacomplaintquestion(Map<String, Object> params, HttpServletRequest request) throws Exception {
		return this.strBody(method(HttpMethod.PUT.toString(),props.getPortalMsApiUrl() +"/dataserviceUi/complaints/"+ params.get("datacomplainOid")+"/question", params, request));
	}

	/**
	 * 
	 * <pre>신고하기 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 21.
	 * @param params
	 * @param res
	 */
	public void downloadDatacomplaintFile(Map<String, Object> params, HttpServletResponse res) {
		String url = props.getPortalMsApiUrl() + "/dataserviceUi/complaints/"+ params.get("datacomplainOid") +"/attachFiles/"+params.get("fileId");
		this.downloadFile(url, params, res);
	}

}
