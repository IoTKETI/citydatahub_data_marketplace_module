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
package kr.co.smartcity.user.feature.notice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.user.common.CommonConst;
import kr.co.smartcity.user.common.component.HttpComponent;

@Service("noticeService")
public class NoticeService extends HttpComponent {
	
	/**
	 * 공지사항 목록 조회
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getNoticeList(Map<String, String> params) throws Exception {
		Map<String, Object> resMap = null;
		try {
			setSessionAttribute(CommonConst.SEARCH_PREFIX, "NOTICE_LIST", params);
			resMap = toMap(get(props.getPortalMsApiUrl() +"/dataserviceUi/notices", params));
			
			Map<String, String> keyPairMap = new HashMap<>();
			keyPairMap.put("noticeCreUsrId"    , "noticeCreUsrNm");
			putUserNameForMap(resMap, keyPairMap);
		} catch (Exception e) {
			throw e;
		}
		return toJson(resMap);
	}
	
	/**
	 * 공지사항 상세
	 * @Author      : junheecho
	 * @Date        : 2019. 9. 17.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getNotice(Map<String, String> params) throws Exception {
		Map<String, Object> resMap = null;
		try {
			resMap = toMap(get(props.getPortalMsApiUrl() +"/dataserviceUi/notices/" + params.get("noticeOid")));
			
			Map<String, String> keyPairMap = new HashMap<>();
			keyPairMap.put("noticeCreUsrId"      , "noticeCreUsrNm");
			putUserNameForMap(resMap, keyPairMap);
			
		}catch(Exception e) {
			throw e;
		}
		return toJson(resMap);
	}

	/**
	 * 
	 * <pre>공지사항 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 28.
	 * @param params
	 * @param res
	 */
	public void downloadNoticeFile(Map<String, Object> params, HttpServletResponse res) {
		String url = props.getPortalMsApiUrl() + "/dataserviceUi/notices/"+ params.get("noticeOid") +"/attachFiles/"+params.get("fileId");
		this.downloadFile(url, params, res);
	}
	
}
