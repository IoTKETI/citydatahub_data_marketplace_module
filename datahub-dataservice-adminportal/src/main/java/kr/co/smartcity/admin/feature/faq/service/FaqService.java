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
package kr.co.smartcity.admin.feature.faq.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;

@Service("faqService")
public class FaqService extends HttpComponent {

	/**
	 * 
	 * FAQ 목록 데이터 호출
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getFaqList(Map<String, Object> params) throws Exception {
		setSessionAttribute(CommonConst.SEARCH_PREFIX, "FAQ_LIST", params);
		Map<String, Object> resMap = toMap(get(props.getPortalMsApiUrl() + "/dataserviceUi/faq", params));

		Map<String, String> keyPairMap = new HashMap<>();
		keyPairMap.put("faqCreUsrId"   , "faqCreUsrNm");
		putUserNameForMap(resMap, keyPairMap);
		return toJson(resMap);
	}
	/**
	 * 
	 * FAQ 상세 데이터 호출
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getFaq(Map<String, Object> params) throws Exception {
		Map<String, Object> resMap = toMap(get( props.getPortalMsApiUrl() + "/dataserviceUi/faq/"+ params.get("faqOid"),params));

		Map<String, String> keyPairMap = new HashMap<>();
		keyPairMap.put("faqCreUsrId"   , "faqCreUsrNm");
		putUserNameForMap(resMap, keyPairMap);
		return toJson(resMap);
	}
	/**
	 * 
	 * FAQ 데이터 등록
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String createFaq(Map<String, Object> params, HttpServletRequest request) throws Exception {
		return multipartMethod(HttpMethod.POST, props.getPortalMsApiUrl() + "/dataserviceUi/faq", params , request);
	}
	/**
	 * 
	 * FAQ 상세 데이터 수정
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String modifyFaq(Map<String, Object> params , HttpServletRequest request) throws Exception {
		return multipartMethod(HttpMethod.PUT, props.getPortalMsApiUrl() +  "/dataserviceUi/faq/"+ params.get("faqOid"), params , request);
	}
	/**
	 * 
	 * FAQ 상세 데이터 삭제
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String deleteFaq(Map<String, Object> params) throws Exception {
		return method(HttpMethod.DELETE, props.getPortalMsApiUrl() + "/dataserviceUi/faq/"+ params.get("faqOid"), params);
	}
	/**
	 * 
	 * <pre> FAQ 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param params
	 * @param res
	 */
	public void downloadFaqFile(Map<String, Object> params, HttpServletResponse res) {
		String url = props.getPortalMsApiUrl() + "/dataserviceUi/faq/"+ params.get("faqOid") +"/attachFiles/"+params.get("fileId");
		this.downloadFile(url, res);
	}
}
