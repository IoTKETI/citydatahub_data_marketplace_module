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
package kr.co.smartcity.user.feature.qna.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.user.common.CommonConst;
import kr.co.smartcity.user.common.component.HttpComponent;

@Service("qnaService")
public class QnaService extends HttpComponent {
	
	/**
	 * qna 목록
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @return
	 */
	public String getQnaList(Map<String, String> params) throws Exception {
		Map<String, Object> resMap = null;
		
		try {
			setSessionAttribute(CommonConst.SEARCH_PREFIX, "QNA_LIST", params);
			resMap = this.toMap(this.get(props.getPortalMsApiUrl() +"/dataserviceUi/qna", params));
			
			Map<String, String> keyPairMap = new HashMap<>();
			keyPairMap.put("qnaQuestionUsrId"      , "qnaQuestionUsrNm");
			putUserNameForMap(resMap, keyPairMap);

		}catch(Exception e) {
			throw e;
		}
		
		
		return toJson(resMap);
	}
	
	/**
	 * qna 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public String createQna(Map<String, Object> params, HttpServletRequest request) throws Exception {
		return this.strBody(method(HttpMethod.POST.toString(), props.getPortalMsApiUrl() +"/dataserviceUi/qna", params, request));
	}
	
	/**
	 * qna 상세
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQna(Map<String, String> params) throws Exception {
		Map<String, Object> resMap = null;
		try {
			resMap = toMap(get(props.getPortalMsApiUrl() +"/dataserviceUi/qna/" + params.get("qnaOid")));
			
			Map<String, String> keyPairMap = new HashMap<>();
			keyPairMap.put("qnaQuestionUsrId"    , "qnaQuestionUsrNm");
			keyPairMap.put("qnaAnswerUsrId"      , "qnaAnswerUsrNm");
			putUserNameForMap(resMap, keyPairMap);
			
		}catch(Exception e) {
			throw e;
		}
		
		return toJson(resMap);
	}
	
	/**
	 * qna 수정
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public String modifyQna(Map<String, Object> params, HttpServletRequest request) throws Exception {
		return this.strBody(method(HttpMethod.PUT.toString(), props.getPortalMsApiUrl() +"/dataserviceUi/qna/"+ params.get("qnaOid"), params, request));
	}
	
	/**
	 * qna 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public String removeQna(Map<String, Object> params) throws Exception {
		return this.strBody(method(HttpMethod.DELETE.toString(), props.getPortalMsApiUrl() +"/dataserviceUi/qna/" + params.get("qnaOid"), params));
	}
	
	/**
	 * qna 답변 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public String createQnaAnswer(Map<String, Object> params) throws Exception {
		return this.strBody(method(HttpMethod.PUT.toString(), props.getPortalMsApiUrl() +"/dataserviceUi/qna/"+ params.get("qnaOid"), params));
	}
	
	/**
	 * 
	 * <pre>Q&A 첨부파일 다운로드<</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 28.
	 * @param params
	 * @param res
	 */
	public void downloadQnaFile(Map<String, Object> params, HttpServletResponse res) {
		String url = props.getPortalMsApiUrl() + "/dataserviceUi/qna/"+ params.get("qnaOid") +"/attachFiles/"+params.get("fileId");
		this.downloadFile(url, params, res);
	}
	
}
