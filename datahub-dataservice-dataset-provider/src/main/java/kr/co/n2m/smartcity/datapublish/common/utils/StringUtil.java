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
package kr.co.n2m.smartcity.datapublish.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.WebRequest;

import kr.co.n2m.smartcity.datapublish.common.exceptions.ClientException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;

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
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return StringUtils.equalsIgnoreCase(str1, str2);
	}
	
	public static boolean notEquals(String str1, String str2) {
		return !StringUtils.equals(str1, str2);
	}
	public static boolean notEqualsIgnoreCase(String str1, String str2) {
		return !StringUtils.equalsIgnoreCase(str1, str2);
	}
	public static boolean isNumeric(String str) {
		return StringUtils.isNumeric(str);
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
		String key = String.format("%d%03d", prefix, (int)(Math.random() * 999) + 1);
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
	public static String getCurrentDateString() {
		return getCurrentDateString("yyyy-MM-dd HH:mm:ss.SSS");
	}
	/**
	 * <pre>yyyy-MM-dd HH:mm:ss.SSS 포맷으로 날짜 반환</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 26.
	 * @param date
	 * @return
	 */
	public static String getCurrentDateString(Date date) {
		return getCurrentDateString("yyyy-MM-dd HH:mm:ss.SSS", date);
	}
	
	/**
	 * 
	 * <pre>특정 포맷으로 날짜 반환</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @param format
	 * @return
	 */
	public static String getCurrentDateString(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(Calendar.getInstance().getTime());
	}
	/**
	 * <pre>특정 포맷으로 날짜 반환</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 26.
	 * @param format
	 * @param date
	 * @return
	 */
	public static String getCurrentDateString (String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	/**
	 * <pre>특정 포맷으로 날짜 반환</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 27.
	 * @param format
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static Date getCurrentDate (String date) throws ParseException {
		return getCurrentDate("yyyy-MM-dd HH:mm:ss", date);
	}
	/**
	 * <pre>특정 포맷으로 날짜 반환</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 27.
	 * @param format
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static Date getCurrentDate (String format, String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
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
	 * 
	 * <pre>NGSI_LD 규격 기반 파라미터 정보 체크</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 14.
	 * @param params
	 * @return
	 */
	public static Map<String, String> validateRequestParams(Map<String, String> params) throws Exception {
		/* -----------------------------------------------------
		 * HTTP Parameter 설명 (필수/선택 구분)
		 * -----------------------------------------------------
		 * datatype (필수)
		 * 	-> lastdata
		 *  -> historydata	
		 * 
		 * options (선택)
		 *  -> normalValues
		 *  -> keyValuesHistory
		 *  
		 * 
		 * [datatype = lastdata] 인 경우에만 아래부분 해당
		 * 
		 * timeAt (필수)
		 *  -> before
		 *  -> after
		 *  -> between 
		 * 
		 * timeproperty (필수)
		 *  -> modifiedAt
		 * 
		 * time (timeAt 값에 따라 필수 or 선택)
		 *  -> 시작날짜 (예시 포맷 : 2019-10-01T00:20:00Z)
		 * 
		 * endtime (timeAt 값에 따라 필수 or 선택)
		 *  -> 종료날짜 (예시 포맷 : 2019-10-01T00:20:00Z)
		 */
		try {
			/* ---------------------------------------------------------------------
			 * 파라미터 빈값 여부 체크
			 *---------------------------------------------------------------------*/
			if ( params == null || params.size() <= 0 ) {
				throw new GlobalException(5100, "요청 파라미터가 존재하지 않습니다.");
			}
			
			/* ---------------------------------------------------------------------
			 * 필수 파라미터(datatype) KEY 체크
			 *---------------------------------------------------------------------*/
			if ( !params.containsKey("datatype")) {
				throw new GlobalException(5101, "요청 파라미터(datatype) 는 필수입니다.");
			}
			
			/* ---------------------------------------------------------------------
			 * 필수 파라미터(datatype) VALUE 체크
			 *---------------------------------------------------------------------*/
			String datatypeValue = (String) params.getOrDefault("datatype", "");
			if ( StringUtil.notEquals(datatypeValue, "lastdata") && StringUtil.notEquals(datatypeValue, "historydata")) {
				throw new GlobalException(5102, "요청 파라미터(datatype) 값이 올바르지 않습니다.");
			}
			
			/* ---------------------------------------------------------------------
			 * 선택 파라미터(options) VALUE 체크 (key 존재하는경우에만 해당)
			 *---------------------------------------------------------------------*/
			if ( params.containsKey("options")) {
				String optionsValue = (String) params.getOrDefault("options", "");
				if ( StringUtil.notEquals(optionsValue, "normalValues") && StringUtil.notEquals(optionsValue, "keyValuesHistory")) {
					throw new GlobalException(5103, "요청 파라미터(options) 값이 올바르지 않습니다.");
				}
				if ( StringUtil.equals(optionsValue, "normalValues")) {
					params.remove("options");
				}
			} else {
				params.put("options", "normalizedHistory");
			}
			
			// [datatype = historydata] 인 경우
			if ( StringUtil.equals(datatypeValue, "historydata")) {
				/* ---------------------------------------------------------------------
				 * 필수 파라미터(timeAt, timeproperty, time, endtime) KEY 체크
				 *---------------------------------------------------------------------*/
				String[] required = {"timeAt","timeproperty","time","endtime"};
				for ( String requiredKey : required ) {
					if ( !params.containsKey(requiredKey) ) {
						throw new GlobalException(5104, String.format("요청 파라미터(%s) 는 필수입니다.", requiredKey));
					}
				}
				/* ---------------------------------------------------------------------
				 * 필수 파라미터(timeAt, timeproperty, time, endtime) VALUE 체크
				 *---------------------------------------------------------------------*/
				String timeAtValue = (String) params.getOrDefault("timeAt", "");
				if ( StringUtil.notEquals(timeAtValue, "before") && StringUtil.notEquals(timeAtValue, "after") && StringUtil.notEquals(timeAtValue, "between")) {
					throw new GlobalException(5105, "요청 파라미터(timeAt) 값이 올바르지 않습니다.");
				}
				
				String timepropertyValue = (String) params.getOrDefault("timeproperty", "");
				if ( StringUtil.notEquals(timepropertyValue, "modifiedAt")) {
					throw new GlobalException(5106, "요청 파라미터(timepropertyValue) 값이 올바르지 않습니다.");
				}
				
				String time = (String) params.getOrDefault("time", "");
				if ( StringUtil.isEmpty(time) || !validationDate("yyyy-MM-dd'T'HH:mm:ss'Z'", time)) {
					throw new GlobalException(5107, "요청 파라미터(time) 날짜 값 형식이 올바르지 않습니다.");
				}
				
				String endtime = (String) params.getOrDefault("endtime", "");
				if ( StringUtil.isEmpty(time) || !validationDate("yyyy-MM-dd'T'HH:mm:ss'Z'", endtime)) {
					throw new GlobalException(5108, "요청 파라미터(endtime) 날짜 값 형식이 올바르지 않습니다.");
				}
			}
		} catch (GlobalException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		}

		return params;
	}
	
	/**
	 * <pre>날짜형식의 값 패턴 검사</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 31.
	 * @param pattern
	 * @param checkDate
	 * @return
	 */
	public static boolean validationDate(String pattern, String checkDate){
		boolean result = false; 
	    try{
	    	SimpleDateFormat  dateFormat = new SimpleDateFormat(pattern);
	        dateFormat.setLenient(false);
	        dateFormat.parse(checkDate);
	        result = true;
	        
	    }catch (ParseException e){
	    	result = false;
	    }
	    return result;
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
	
	/**
	 * <pre>NGSI-LD 데이터를 JSON MAP으로 변환</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 3.
	 * @param data
	 * @return
	 */
	public static List<Map<String, Object>> convertNgsiLdToJsonMap(List<Map<String, Object>> list){
		List<Map<String, Object>> resultList = new ArrayList<>();
		for(Map<String, Object> data : list) {
			Map<String, Object> resultMap = new HashMap<>();
			for(Entry<String, Object> entry : data.entrySet()) {
				String key = entry.getKey();
				Object value   = entry.getValue();
				
				if(value instanceof String) {
					resultMap.put(key, value);
				}else if(value instanceof Map) {
					resultMap.put(key, parseNsgiLdSub((Map<String, Object>)value));
				}
			}
			resultList.add(resultMap);
		}
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public static Object parseNsgiLdSub(Map<String, Object> data) {
		Object resultObj = null;
		String type = (String) data.get("type");
		Object value = data.get("value");
		if("Property".equalsIgnoreCase(type)) {
			if(value instanceof String || value instanceof Map) {
				resultObj = value;
			}else if(value instanceof List) {
				resultObj = ((List<Object>) value).stream().map( obj -> String.valueOf(obj)).collect(Collectors.joining(","));
			}
		}else if("GeoProperty".equalsIgnoreCase(type)) {
			Map<String, Object> subValue = (Map<String, Object>) value;
			List<Double> list = (List<Double>) subValue.get("coordinates");
			resultObj = String.format("%f,%f", list.get(0), list.get(1));
		}
		return resultObj;
	}

}
