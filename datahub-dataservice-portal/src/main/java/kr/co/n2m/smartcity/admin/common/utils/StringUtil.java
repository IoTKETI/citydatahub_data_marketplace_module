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
package kr.co.n2m.smartcity.admin.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.WebRequest;

import kr.co.n2m.smartcity.admin.common.exceptions.ClientException;

public class StringUtil {
	public static final String EMPTY = "";
	
	public static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}
	
	public static boolean isNotEmpty(String str) {
		return StringUtils.isNotEmpty(str);
	}
	
	public static boolean equals(String str1, String str2) {
		return StringUtils.equals(str1, str2);
	}
	
	public static boolean notEquals(String str1, String str2) {
		return !StringUtils.equals(str1, str2);
	}
	
	public static String avoidNull(Object object) {
		return avoidNull(object, "");
	}
	
	public static String avoidNull(Object object, String string) {
		if (object != null) {
			string = object.toString().trim();
		}		
		return string;
	}
	
	public static long generateKey(){
		long prefix = Calendar.getInstance().getTimeInMillis();
		String key = String.format("%d%03d", prefix, 1);
		return Long.parseLong(key);
	}
	public static List<Long> generateKeys(int count){
		List<Long> keyList = new ArrayList<>();
		long prefix = Calendar.getInstance().getTimeInMillis();
		
		for(int suffix=1; suffix<count+1; suffix++) {
			String key = String.format("%d%03d", prefix, suffix);
			keyList.add(Long.parseLong(key));
		}
		return keyList;
	}
	
	public static boolean isAjaxRequest(WebRequest webRequest) {
		String requestedWith = webRequest.getHeader("X-Requested-With");
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}

	public static boolean isAjaxUploadRequest(WebRequest webRequest) {
		return webRequest.getParameter("ajaxUpload") != null;
	}
	
	/**
	 * <pre>로컬호스트명 조회</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 11. 7.
	 * @return
	 */
	public static String getLocalHostName(String defaultName) {
		String hostName = "";
		try {
			InetAddress local = InetAddress.getLocalHost();
			hostName = local.getHostName();
		} catch (UnknownHostException e) {
			hostName = defaultName;
		}
		return hostName;
	}

	/**
	 * <pre>PUT 요청의 PathVariable ID 와 RequestBody의 요청 ID의 일치 여부 확인</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 6. 30.
	 * @param pathId	: @PathVariable 고유ID값
	 * @param bodyId	: @RequestBody 내의 고유 ID값
	 * @throws ClientException : pathId 와 bodyId가 일치하지 않는 경우 발생
	 */
	public static void compareUniqueId(String pathId, String bodyId) throws ClientException {
		if(!StringUtil.equals(pathId, bodyId)) {
			throw new ClientException(String.format("요청 한 Path (%s) 정보와 Body (%s) 는 일치하지 않습니다.", pathId, bodyId));
		}
	}
	
	public static void compareUniqueId(Object pathId, Object bodyId) throws ClientException {
		String strPathId = String.valueOf(pathId);
		String strBodyId = String.valueOf(bodyId);
		if(!StringUtil.equals(strPathId, strBodyId)) {
			throw new ClientException(String.format("요청 한 Path (%s) 정보와 Body (%s) 는 일치하지 않습니다.", strPathId, strBodyId));
		}
	}
}
