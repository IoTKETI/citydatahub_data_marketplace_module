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
package kr.co.smartcity.admin.feature.program.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;
import okhttp3.MediaType;
	
@Service("programService")
public class ProgramService extends HttpComponent {
	public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
	
	/**
	 * <pre>프로그램 그룹 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getProgramGroup(Map<String, Object> params) throws Exception {
		Map<String, Object> resMap = null;
		resMap = toMap(get( props.getPortalMsApiUrl() + "/dataserviceUi/programGroup/"+ params.get("programgroupOid")));

		Map<String, String> keyPairMap = new HashMap<>();
		keyPairMap.put("programgroupCreUsrId", "programgroupCreUsrNm");
		putUserNameForMap(resMap, keyPairMap);
		return toJson(resMap);
	}
	
	/**
	 * <pre>프로그램 그룹 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String createProgramGroup(Map<String, Object> params) throws Exception {
		return method(HttpMethod.POST, props.getPortalMsApiUrl() +  "/dataserviceUi/programGroup", params);
	}
	
	/**
	 * <pre>프로그램 그룹 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getProgramGroupList(Map<String, Object> params) throws Exception {
		setSessionAttribute(CommonConst.SEARCH_PREFIX, "PROGRAM_LIST", params);
		String data = get( props.getPortalMsApiUrl() + "/dataserviceUi/programGroup", params);
		Map<String,Object> resMap = toMap(data);
		Map<String, Object> programListPageInfoMap = (Map<String, Object>) resMap.get("page");
	    programListPageInfoMap.putAll(params);
		return toJson(resMap);
	}
	
	/**
	 * <pre>프로그램 그룹 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String modifyProgramGroup(Map<String, Object> params) throws Exception {
		return method(HttpMethod.PUT, props.getPortalMsApiUrl() +  "/dataserviceUi/programGroup/"+ params.get("programgroupOid"), params);
	}
	
	/**
	 * <pre>프로그램 그룹 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String removeProgramGroup(Map<String, Object> params) throws Exception {
		return method(HttpMethod.DELETE, props.getPortalMsApiUrl() +  "/dataserviceUi/programGroup/"+ params.get("programgroupOid"), params);
	}
	
	/**
	 * <pre>프로그램 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getProgram(Map<String, Object> params) throws Exception {
		return get( props.getPortalMsApiUrl() + "/dataserviceUi/programGroup/"+ params.get("programgroupOid")+"/programs/"+ params.get("programOid"));
	}
	
	/**
	 * <pre>프로그램 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String createProgram(Map<String, Object> params) throws Exception {
		return method(HttpMethod.POST, props.getPortalMsApiUrl() +  "/dataserviceUi/programGroup/"+ params.get("programgroupOid")+"/programs", params);
	}
	
	/**
	 * <pre>프로그램 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getProgramList(Map<String, Object> params) throws Exception {
		return get( props.getPortalMsApiUrl() + "/dataserviceUi/programGroup/"+ params.get("programgroupOid")+"/programs", params);
	}
	
	/**
	 * <pre>프로그램 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String modifyProgram(Map<String, Object> params) throws Exception {
		return method(HttpMethod.PUT, props.getPortalMsApiUrl() +  "/dataserviceUi/programGroup/"+ params.get("programgroupOid")+"/programs/"+ params.get("programOid"), params);
	}
	
	/**
	 * <pre>프로그램 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String removeProgram(Map<String, Object> params) throws Exception {
		return method(HttpMethod.DELETE, props.getPortalMsApiUrl() +  "/dataserviceUi/programGroup/"+ params.get("programgroupOid")+"/programs/"+ params.get("programOid"), params);
	}
}
