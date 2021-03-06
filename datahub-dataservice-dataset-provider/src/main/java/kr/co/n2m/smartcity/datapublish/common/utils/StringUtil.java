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
	 * ??????????????? 13?????? ??? ??????(??????)
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
	 * ??????????????? 13?????? ??? ??????(??????)
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
	 * <pre>???????????? ID ?????? ??????</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @return
	 */
	public static String generateDatasetKey() {
		return String.format("DST%d", generateKey());
	}
	
	/**
	 * <pre>Ajax ?????? ??????</pre>
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
	 * <pre>yyyy-MM-dd HH:mm:ss.SSS ???????????? ?????? ??????</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @return
	 */
	public static String getCurrentDateString() {
		return getCurrentDateString("yyyy-MM-dd HH:mm:ss.SSS");
	}
	/**
	 * <pre>yyyy-MM-dd HH:mm:ss.SSS ???????????? ?????? ??????</pre>
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
	 * <pre>?????? ???????????? ?????? ??????</pre>
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
	 * <pre>?????? ???????????? ?????? ??????</pre>
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
	 * <pre>?????? ???????????? ?????? ??????</pre>
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
	 * <pre>?????? ???????????? ?????? ??????</pre>
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
	 * <pre>?????? key ??? ?????? Cookie ?????? ?????? </pre>
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
	 * <pre>NGSI_LD ?????? ?????? ???????????? ?????? ??????</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 14.
	 * @param params
	 * @return
	 */
	public static Map<String, String> validateRequestParams(Map<String, String> params) throws Exception {
		/* -----------------------------------------------------
		 * HTTP Parameter ?????? (??????/?????? ??????)
		 * -----------------------------------------------------
		 * datatype (??????)
		 * 	-> lastdata
		 *  -> historydata	
		 * 
		 * options (??????)
		 *  -> normalValues
		 *  -> keyValuesHistory
		 *  
		 * 
		 * [datatype = lastdata] ??? ???????????? ???????????? ??????
		 * 
		 * timeAt (??????)
		 *  -> before
		 *  -> after
		 *  -> between 
		 * 
		 * timeproperty (??????)
		 *  -> modifiedAt
		 * 
		 * time (timeAt ?????? ?????? ?????? or ??????)
		 *  -> ???????????? (?????? ?????? : 2019-10-01T00:20:00Z)
		 * 
		 * endtime (timeAt ?????? ?????? ?????? or ??????)
		 *  -> ???????????? (?????? ?????? : 2019-10-01T00:20:00Z)
		 */
		try {
			/* ---------------------------------------------------------------------
			 * ???????????? ?????? ?????? ??????
			 *---------------------------------------------------------------------*/
			if ( params == null || params.size() <= 0 ) {
				throw new GlobalException(5100, "?????? ??????????????? ???????????? ????????????.");
			}
			
			/* ---------------------------------------------------------------------
			 * ?????? ????????????(datatype) KEY ??????
			 *---------------------------------------------------------------------*/
			if ( !params.containsKey("datatype")) {
				throw new GlobalException(5101, "?????? ????????????(datatype) ??? ???????????????.");
			}
			
			/* ---------------------------------------------------------------------
			 * ?????? ????????????(datatype) VALUE ??????
			 *---------------------------------------------------------------------*/
			String datatypeValue = (String) params.getOrDefault("datatype", "");
			if ( StringUtil.notEquals(datatypeValue, "lastdata") && StringUtil.notEquals(datatypeValue, "historydata")) {
				throw new GlobalException(5102, "?????? ????????????(datatype) ?????? ???????????? ????????????.");
			}
			
			/* ---------------------------------------------------------------------
			 * ?????? ????????????(options) VALUE ?????? (key ???????????????????????? ??????)
			 *---------------------------------------------------------------------*/
			if ( params.containsKey("options")) {
				String optionsValue = (String) params.getOrDefault("options", "");
				if ( StringUtil.notEquals(optionsValue, "normalValues") && StringUtil.notEquals(optionsValue, "keyValuesHistory")) {
					throw new GlobalException(5103, "?????? ????????????(options) ?????? ???????????? ????????????.");
				}
				if ( StringUtil.equals(optionsValue, "normalValues")) {
					params.remove("options");
				}
			} else {
				params.put("options", "normalizedHistory");
			}
			
			// [datatype = historydata] ??? ??????
			if ( StringUtil.equals(datatypeValue, "historydata")) {
				/* ---------------------------------------------------------------------
				 * ?????? ????????????(timeAt, timeproperty, time, endtime) KEY ??????
				 *---------------------------------------------------------------------*/
				String[] required = {"timeAt","timeproperty","time","endtime"};
				for ( String requiredKey : required ) {
					if ( !params.containsKey(requiredKey) ) {
						throw new GlobalException(5104, String.format("?????? ????????????(%s) ??? ???????????????.", requiredKey));
					}
				}
				/* ---------------------------------------------------------------------
				 * ?????? ????????????(timeAt, timeproperty, time, endtime) VALUE ??????
				 *---------------------------------------------------------------------*/
				String timeAtValue = (String) params.getOrDefault("timeAt", "");
				if ( StringUtil.notEquals(timeAtValue, "before") && StringUtil.notEquals(timeAtValue, "after") && StringUtil.notEquals(timeAtValue, "between")) {
					throw new GlobalException(5105, "?????? ????????????(timeAt) ?????? ???????????? ????????????.");
				}
				
				String timepropertyValue = (String) params.getOrDefault("timeproperty", "");
				if ( StringUtil.notEquals(timepropertyValue, "modifiedAt")) {
					throw new GlobalException(5106, "?????? ????????????(timepropertyValue) ?????? ???????????? ????????????.");
				}
				
				String time = (String) params.getOrDefault("time", "");
				if ( StringUtil.isEmpty(time) || !validationDate("yyyy-MM-dd'T'HH:mm:ss'Z'", time)) {
					throw new GlobalException(5107, "?????? ????????????(time) ?????? ??? ????????? ???????????? ????????????.");
				}
				
				String endtime = (String) params.getOrDefault("endtime", "");
				if ( StringUtil.isEmpty(time) || !validationDate("yyyy-MM-dd'T'HH:mm:ss'Z'", endtime)) {
					throw new GlobalException(5108, "?????? ????????????(endtime) ?????? ??? ????????? ???????????? ????????????.");
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
	 * <pre>??????????????? ??? ?????? ??????</pre>
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
	 * <pre>?????????????????? ??????</pre>
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
	 * <pre>PUT ????????? PathVariable ID ??? RequestBody??? ?????? ID??? ?????? ?????? ??????</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 6. 30.
	 * @param pathId	: @PathVariable ??????ID???
	 * @param bodyId	: @RequestBody ?????? ?????? ID???
	 * @throws ClientException : pathId ??? bodyId??? ???????????? ?????? ?????? ??????
	 */
	public static void compareUniqueId(String pathId, String bodyId) throws ClientException {
		if(!StringUtil.equals(pathId, bodyId)) {
			throw new ClientException(String.format("?????? ??? Path (%s) ????????? Body (%s) ??? ???????????? ????????????.", pathId, bodyId));
		}
	}
	public static void compareUniqueId(Object pathId, Object bodyId) throws ClientException {
		String strPathId = String.valueOf(pathId);
		String strBodyId = String.valueOf(bodyId);
		if(!StringUtil.equals(strPathId, strBodyId)) {
			throw new ClientException(String.format("?????? ??? Path (%s) ????????? Body (%s) ??? ???????????? ????????????.", strPathId, strBodyId));
		}
	}
	
	/**
	 * <pre>NGSI-LD ???????????? JSON MAP?????? ??????</pre>
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
