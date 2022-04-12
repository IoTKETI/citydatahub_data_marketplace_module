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
package kr.co.n2m.smartcity.notification.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

public class StringUtil {
	public static final String EMPTY = "";
	
	public static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}
	
	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
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
	
	/**
	 * 타임스탬프 13자리 키 생성(단일)
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 8.
	 * @return
	 */
	public static long generateKey(){
		long prefix = Calendar.getInstance().getTimeInMillis();
		String key = String.format("%d%03d", prefix, 1);
		return Long.parseLong(key);
	}
	
	/**
	 * 타임스탬프 13자리 키 생성(복수)
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 8.
	 * @param count
	 * @return
	 */
	public static List<Long> generateKeys(int count){
		List<Long> keyList = new ArrayList<>();
		long prefix = Calendar.getInstance().getTimeInMillis();
		
		for(int suffix=1; suffix<count+1; suffix++) {
			String key = String.format("%d%03d", prefix, suffix);
			keyList.add(Long.parseLong(key));
		}
		return keyList;
	}
	
	/**
	 * <pre>데이터셋 ID 생성 함수</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @return
	 */
	public static String generateDatasetKey() {
		return String.format("DST%d", generateKey());
	}
	
	/**
	 * <pre>Ajax 호출 여부</pre>
	 * 
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @param webRequest
	 * @return
	 */
	public static boolean isAjaxRequest(WebRequest webRequest) {
		String requestedWith = webRequest.getHeader("X-Requested-With");
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}
	
	/**
	 * 
	 * <pre>yyyy-MM-dd HH:mm:ss.SSS 포맷으로 날짜 반환</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @return
	 */
	public static String getCurrentDate() {
		return getCurrentDate("yyyy-MM-dd HH:mm:ss.SSS");
	}
	
	/**
	 * 
	 * <pre>특정 포맷으로 날짜 반환</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * <pre>특정 key 에 대한 Cookie 정보 조회 </pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @param cookies
	 * @param cookieName
	 * @return
	 */
	public static String getStringFromCookie(Cookie[] cookies, String cookieName) {
		String str = null;
		
		if(cookies!=null) {
			for(Cookie itr:cookies) {	
				if(itr.getName().equals(cookieName)) {
					str = itr.getValue();
					break;
				}
			}
		}
		return str;
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
}
