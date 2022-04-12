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
package kr.co.smartcity.user.feature.search.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.co.smartcity.user.common.component.HttpComponent;

@Service("searchService")
public class SearchService extends HttpComponent{

	/**
	 * 통합검색 목록 조회
	 * @Author      : thlee
	 * @Date        : 2019. 11. 11.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getSearchList(Map<String, String> params) throws Exception{
		Map<String, Object> resMap = new HashMap<>();
		
		try {
			/* 데이터셋 (제목, 설명, 키워드) */
			String datasetJson = get(props.getDatapublishMsApiUrl()  + "/dataservice/dataset", params);
			
			resMap.put("dataset", toMap(datasetJson));
			
			/* FAQ (제목, 질문내용, 답변내용) */
			String faqJson = get(props.getPortalMsApiUrl()  + "/dataserviceUi/faq", params);
			
			resMap.put("faq", toMap(faqJson));
			
			/* 묻고답하기 (제목, 질문내용, 답변내용) */
			String qnaJson = get(props.getPortalMsApiUrl()  + "/dataserviceUi/qna", params);
			
			resMap.put("qna", toMap(qnaJson));
			
			/* 신고하기 (제목, 민원내용, 답변내용) */
			String datacomplainJson = get(props.getPortalMsApiUrl()  + "/dataserviceUi/complaints", params);
			
			resMap.put("datacomplain", toMap(datacomplainJson));
			
			/* 공지사항 (제목, 내용) */
			String noticeJson = get(props.getPortalMsApiUrl()  + "/dataserviceUi/notices", params);
			
			resMap.put("notice", toMap(noticeJson));
		} catch (Exception e) {
			throw e;
		}
		
		return toJson(resMap);
	}
	
}
