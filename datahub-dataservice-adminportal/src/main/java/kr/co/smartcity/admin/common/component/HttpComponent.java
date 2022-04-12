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
package kr.co.smartcity.admin.common.component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import kr.co.smartcity.admin.common.entity.UserInfo;
import kr.co.smartcity.admin.common.exceptions.HttpForbiddenException;
import kr.co.smartcity.admin.common.exceptions.HttpInternalServerErrorException;
import kr.co.smartcity.admin.common.exceptions.HttpNotFoundException;
import kr.co.smartcity.admin.common.exceptions.HttpUnauthorizedExcpetion;
import kr.co.smartcity.admin.common.util.StringUtil;
import kr.co.smartcity.admin.feature.user.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.HttpUrl.Builder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Slf4j
@Component
public class HttpComponent extends CommonComponent{
	
	public final static MediaType JSON = MediaType.get("application/json; charset=utf-8");
	
	@Autowired
	private OkHttpClient client;
	
	@Autowired
	private Gson gson;	
	
	public String toJson(Object obj) {
		return gson.toJson(obj);
	}
	
	public Object toObject(String jsonStr) {
		return gson.fromJson(jsonStr, Object.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> toMap(String jsonStr) {
		return (Map<String, Object>) gson.fromJson(jsonStr, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> toList(String jsonStr) {
		return (List<Map<String, Object>>) gson.fromJson(jsonStr, List.class);
	}
	
	public String get(String uri) throws Exception{
		return get(uri, null, null);
	}
	
	public String get(String uri, Map<String, Object> params) throws Exception{
		return get(uri, null, params);
	}
	
	public String get(String uri, Object params) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return get(uri, null, objectMapper.convertValue(params, Map.class));
	}
	
	public String get(String uri, Map<String, Object> headers, Map<String, Object> params) throws Exception{
		String resultStr = "";
		try {
			if (headers == null || headers.isEmpty()) {
				headers = this.makeAuthHeaders();
			}
			Response response = this.execute(HttpMethod.GET, uri, headers, params, null);

			resultStr = response.body().string();
			
		} catch (Exception ex) {
			log.error("==> ", ex);
			
			throw ex;
		}
		log.debug(String.format("%s ====> %s", uri, resultStr));
		
		return resultStr;
	}
	
	public String method(HttpMethod method, String uri) throws Exception{
		return method(method, uri, null, null);
	}
	
	public String method(HttpMethod method, String uri, Object body) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> params = objectMapper.convertValue(body, Map.class);
		return method(method, uri, params, null);
	}
	
	public String method(HttpMethod method, String uri, Map<String, Object> body) throws Exception{
		return method(method, uri, body, null);
	}
	
	public String method(HttpMethod method, String uri, Map<String, Object> body, Map<String, Object> headers) throws Exception{
		String resultStr = "";
		
		try {
			if (headers == null) {
				headers = this.makeAuthHeaders();
			}
			RequestBody requestBody = RequestBody.create(JSON, this.toJson(body));
			Response response = this.execute(method, uri, headers, null, requestBody);
			
			resultStr = response.body().string();
		} catch (Exception ex) {
			log.error("==> ", ex);
			
			throw ex;
		}
		return resultStr;
	}
	/**
	 * 
	 * <pre>[POST,PUT,PATCH,DELETE...] + 파라미터</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 9. 24.
	 * @param method
	 * @param uri
	 * @param params
	 * @return
	 * @throws Exception : ResponseEntity<Object>
	 */
	public Response method(HttpMethod method, String uri, Object bodyMap, Map<String, Object> params) throws Exception{
		Response response = null;
		try {
			Builder urlBuilder = HttpUrl.parse(uri).newBuilder();
			if(params != null) {
				for(Entry<String, Object> param : params.entrySet()) {
					urlBuilder.addQueryParameter(param.getKey(), String.valueOf(param.getValue()));
				}
			}

			String json = gson.toJson(bodyMap);
			RequestBody body = RequestBody.create(JSON, json);
			response = this.execute(method, uri, this.makeAuthHeaders(), null, body);
		} catch (Exception e) {
			throw e;
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public String multipartMethod(HttpMethod method, String uri, Map<String, Object> params, HttpServletRequest request) throws Exception{
		String resultStr = "";
		
		try {
			Map<String, Object> headers = this.makeAuthHeaders();
			
			MultipartBody.Builder reqBuilder = new MultipartBody.Builder();
			reqBuilder.setType(MultipartBody.FORM);
			
			Iterator<String> it = ((MultipartRequest) request).getFileNames();
			
			while(it.hasNext()) {
				String fileName = it.next();
				MultipartFile file = ((MultipartRequest) request).getFile(fileName);
				String fieldName = file.getName();
				File f = File.createTempFile(UUID.randomUUID().toString(), ".tmp");
				IOUtils.copy(file.getInputStream(), new FileOutputStream(f));
				
				reqBuilder.addFormDataPart(fieldName, file.getOriginalFilename(), RequestBody.create(MultipartBody.FORM, f));
			}
			
			Iterator<Entry<String, Object>> paramsIt = params.entrySet().iterator();
			while(paramsIt.hasNext()) {
				Entry<String, Object> entry = paramsIt.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				value = this.toObject(toJson(value));
				
				if(value instanceof String) {
					reqBuilder.addFormDataPart(entry.getKey(), (String) value);
				}else if(value instanceof Double){
					reqBuilder.addFormDataPart(key, this.toJson(value));
				}else if(value instanceof ArrayList){
					List<Map<String, Object>> arr = (List<Map<String, Object>>) value;
					reqBuilder.addFormDataPart(key, this.toJson(arr));
				}
			}
			
			RequestBody requestBody = reqBuilder.build();
			Response response = this.execute(method, uri, headers, null, requestBody);
			
			resultStr = response.body().string();
			
		} catch(Exception ex) {
			log.error("==> ", ex);
			
			throw ex;
		}
		return resultStr;
	}
	
	public String authMethod(HttpMethod method, String uri, Map<String, Object> body) throws Exception{
		String resultStr = "";
		
		try {
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("Authorization", "Bearer " + this.requestClientCredentialsGrant(UserType.NORMAL));
			
			RequestBody requestBody = RequestBody.create(JSON, this.toJson(body));
			Response response = this.execute(method, uri, headers, null, requestBody);
			
			resultStr = response.body().string();
		} catch (Exception ex) {
			log.error("==> ", ex);
			
			throw ex;
		}
		return resultStr;
	}
	
	public void downloadFile(String uri, HttpServletResponse res) {
		
		try {
			Map<String, Object> headers = this.makeAuthHeaders();

			Response response = this.execute(HttpMethod.GET, uri, headers, null, null);
			
			ResponseBody responseBody = response.body();
			
			res.setHeader("Content-Disposition", response.header("Content-Disposition"));
			res.setHeader("Content-Length", response.header("Content-Length"));
			byte[] bytes = responseBody.bytes();
			
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			IOUtils.copy(bis, res.getOutputStream());
			
		} catch(Exception e) {
			log.error("==> ", e);
			
		}
	}
	
	private Response execute(HttpMethod method, String uri, Map<String, Object> headers, Map<String, Object> params, RequestBody requestBody) throws Exception {
		try {
			// set params.
			Builder urlBuilder = HttpUrl.parse(uri).newBuilder();
			if(params != null) {
				for(Entry<String, Object> param : params.entrySet()) {
					urlBuilder.addQueryParameter(param.getKey(), String.valueOf(param.getValue()));
				}
			}

			// set headers.
			Request.Builder builder = new Request.Builder();
			if (headers != null) {
				for(Entry<String, Object> header : headers.entrySet()) {
					builder.addHeader(header.getKey(), String.valueOf(header.getValue()));
				}
			}
			builder.addHeader("Accept", "application/json");
			
			Request request = null;
			
			if ( method == HttpMethod.GET) {
				request = builder.url(urlBuilder.build()).get().build();
			} else {
				request = builder.url(urlBuilder.build()).method(method.toString(), requestBody).build();
			}
			
			Response response = client.newCall(request).execute();
			if ( !response.isSuccessful()) {
				
				String err = response.body().string();
						
				Exception ee = null;
				
				if (response.code() == HttpStatus.UNAUTHORIZED.value()) {
					ee = new HttpUnauthorizedExcpetion(err);
				} else if (response.code() == HttpStatus.NOT_FOUND.value()) {
					ee = new HttpNotFoundException(err);
				} else if (response.code() == HttpStatus.FORBIDDEN.value()) {
					ee = new HttpForbiddenException(err);
				} else if (response.code() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
					ee = new HttpInternalServerErrorException(err);
				} else {
					ee = new HttpInternalServerErrorException(err);
				}
				throw ee;
			}
			return response;
			
		} catch (Exception ex) {
			log.error("==> ", ex);
			
			throw ex;
		}
	}
	
	private Map<String, Object> makeAuthHeaders () {
		Map<String, Object> headers = new HashMap<String, Object>();
		
		HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		String token = (String) session.getAttribute("authToken");
		
		if (StringUtil.isNotEmpty(token)) {
			headers.put("Authorization", "Bearer " + token);
		}
		headers.put("Accept", "application/json");
			
		UserInfo userInfo = (UserInfo) session.getAttribute("user");
		String userId = "";
		if (userInfo != null) {
			userId = (String) userInfo.getUserId();
		}
		
		if (!StringUtil.isEmpty(userId)) {
			headers.put("dh-accesstoken", String.format("{\"userId\":\"%s\",\"token\":\"%s\"}", userId, token));
		}
		return headers;
	}
	
	public static Object getSessionAttribute(String prefix, String name) {
		ServletRequestAttributes servletContainer = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		HttpServletRequest req = servletContainer.getRequest();
		HttpSession session = req.getSession();
		Object result = session.getAttribute(prefix + name);
		removeSessionAttribute(prefix, name);
		return result;
	}
	
	public static void setSessionAttribute(String prefix, String name, Object object) {
		ServletRequestAttributes servletContainer = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		HttpServletRequest req = servletContainer.getRequest();
		HttpSession session = req.getSession();
		removeSessionAttributeWithout(prefix, name);
		session.setAttribute(prefix + name, object);
	}
	
	public static void removeSessionAttribute(String prefix, String name) {
		ServletRequestAttributes servletContainer = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		HttpServletRequest req = servletContainer.getRequest();
		HttpSession session = req.getSession();
		session.removeAttribute(prefix+name);
	}
	
	public static void removeSessionAttributeWithout(String prefix, String name) {
		ServletRequestAttributes servletContainer = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		HttpServletRequest req = servletContainer.getRequest();
		HttpSession session = req.getSession();
		Enumeration<String> attributeNames = session.getAttributeNames();
		while(attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			if(attributeName.contains(prefix)) {
				if(StringUtil.notEquals(attributeName, prefix+name)) {
					session.removeAttribute(attributeName);
				}
			}
		}
	}

	public String postAccessToken(HttpMethod method, String uri, String code) throws Exception {
		String resultStr = "";
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("grant_type", "authorization_code");
			map.put("code", code);
			map.put("redirect_uri", props.getOauthRedirectUri());
			map.put("client_id",props.getAdminClientId());
			map.put("client_secret",props.getAdminClientSecretId() );
			
			resultStr = this.method(method, uri, map);
		} catch (Exception ex) {
			
			ex.printStackTrace();
			
			throw ex;
		}
		return resultStr;
	}
	
	public UserVo getUserByType(String userId, UserType type) throws Exception {

		try {
			String strUserInfo = "";
			
			Map<String, Object> headers = new HashMap<>();
			
			if(type == UserType.NORMAL) {
				headers.put("Authorization", "Bearer " + this.requestClientCredentialsGrant(type));
				strUserInfo = this.get(props.getNormalUserInfoUrl() + "/" + userId, headers, null);
				
			} else if (type == UserType.ADMIN) {
				headers.put("Authorization", "Bearer " + this.requestClientCredentialsGrant(type));
				strUserInfo = this.get(props.getAdminUserInfoUrl() + "/" + userId, headers, null);
				
			}
			return gson.fromJson(strUserInfo, UserVo.class);
			
		} catch (HttpNotFoundException e) {
			log.debug("==> Not found in getUserByType : " + e.getMessage());
		} catch (Exception ex) {
			log.error("==> fault error in getUserByType : " + ex);
			
			throw ex;
		}
		return null;
	}
	
	public List<UserVo> getUserListByType(UserType type) throws Exception {
		List<UserVo> userList = new ArrayList<UserVo>();
		
		try {
			Map<String, Object> headers = new HashMap<>();
			headers.put("Authorization", "Bearer " + this.requestClientCredentialsGrant(type));
			
			List<Map<String, Object>> userListMap = null;
			
			if(type == UserType.NORMAL) {
				userListMap = toList((String) this.get(props.getNormalUserInfoUrl(), headers, null));
			} else if (type == UserType.ADMIN) {
				userListMap = toList((String) this.get(props.getAdminUserInfoUrl(), headers, null));
			}
			
			for(Map<String, Object> userMap : userListMap) {
				UserVo userVo = new UserVo();
				BeanUtils.populate(userVo, userMap);
				
				userList.add(userVo);
			}
		} catch (Exception ex) {
			log.error("==> fault error in getUserList : " + ex);
			
			throw ex;
		}
		return userList;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserVo> getUserListAll() throws Exception {
		try {
			List<UserVo> userList = ListUtils.union(this.getUserListByType(UserType.NORMAL),this.getUserListByType(UserType.ADMIN));
			
			Collections.sort(userList, new Comparator<UserVo>() {

				@Override
				public int compare(UserVo u1, UserVo u2) {
					return u1.getUserId().compareToIgnoreCase(u2.getUserId());
				}
			});
			return userList;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public String requestClientCredentialsGrant(UserType type) throws Exception {
		String accessToken = "";
		
		try {
			// default (UserType.ADMIN)
			String clientId 	= props.getAdminClientId();
			String clientSecret = props.getAdminClientSecretId();
			String tokenUrl 	= props.getAdminAccessTokenUrl();
			
			if (type == UserType.NORMAL) {
				clientId 		= props.getNormalClientId();
				clientSecret 	= props.getNormalClientSecretId();
				tokenUrl 		= props.getNormalAccessTokenUrl();	
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("grant_type"    , "client_credentials");
			map.put("client_id"     , clientId);
			map.put("client_secret" , clientSecret);
			
			Map<String, Object> resMap = this.toMap(this.method(HttpMethod.POST, tokenUrl, map, new HashMap<String, Object>()));
			accessToken = (String) resMap.get("access_token");
		} catch (Exception ex) {
			throw ex;
		}
		return accessToken;
	}
	
	public String getClientAccessToken(UserType type){
		String accessToken = "";
		
		try {

			String clientId 	= props.getAdminClientId();
			String clientSecret = props.getAdminClientSecretId();
			String tokenUrl 	= props.getAdminAccessTokenUrl();
			
			if (type == UserType.NORMAL) {
				clientId 		= props.getNormalClientId();
				clientSecret 	= props.getNormalClientSecretId();
				tokenUrl 		= props.getNormalAccessTokenUrl();	
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("grant_type"    , "client_credentials");
			map.put("client_id"     , clientId);
			map.put("client_secret" , clientSecret);
			
			String body = this.method(HttpMethod.POST, tokenUrl, map);
			Map<String, Object> resMap = toMap(body);
			accessToken = (String) resMap.get("access_token");
			
			
		} catch (Exception ex) {
		   ex.printStackTrace();
		}
		return accessToken;
	}
	
	public Map<String, String> getUserNameMap() {
		Map<String, String> resMap = new HashMap<>();
		try {
			String adminAccessToken = getClientAccessToken(UserType.ADMIN);
			Map<String, Object> adminHeaders = new HashMap<>();
			adminHeaders.put("Authorization", "Bearer "+adminAccessToken);
			
			String adminUserListBody = this.get( props.getAdminUserInfoUrl(), adminHeaders, null);
			Map<String, Object> adminUserListMap = toMap(adminUserListBody);
			Set<Map.Entry<String, Object>> adminUserEntrySet = adminUserListMap.entrySet();
			for(Map.Entry<String, Object> adminUserEntry: adminUserEntrySet) {
				if(StringUtil.equals("totalCount", adminUserEntry.getKey())) {
					continue;
				}
				Map<String, String> adminUserMap = (Map<String, String>) adminUserEntry.getValue();
				resMap.put(adminUserMap.get("userId"), adminUserMap.get("name"));
			}

			String normalAccessToken = getClientAccessToken(UserType.NORMAL);
			Map<String, Object> normalHeaders = new HashMap<>();
			adminHeaders.put("Authorization", "Bearer "+normalAccessToken);
			String normalUserListBody = this.get( props.getNormalUserInfoUrl(), normalHeaders, null);
			Map<String, Object> normalUserListMap = toMap(normalUserListBody);
			Set<Map.Entry<String, Object>> normalUserEntrySet = normalUserListMap.entrySet();
			for(Map.Entry<String, Object> normalUserEntry: normalUserEntrySet) {
				if(StringUtil.equals("totalCount", normalUserEntry.getKey())) {
					continue;
				}
				Map<String, String> normalUserMap = (Map<String, String>) normalUserEntry.getValue();
				resMap.put(normalUserMap.get("userId"), normalUserMap.get("name"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}
	
	public void putUserNameForMap(Map<String, Object> targetMap, Map<String, String> keyPairMap) {
		Queue<Map<String, Object>> queue = new LinkedList<Map<String, Object>>();
		Map<String, String> userNameMap = getUserNameMap();
		
		for(Map.Entry<String, String> keyPair: keyPairMap.entrySet()) {
			String orgKey = keyPair.getKey();
			String addKey = keyPair.getValue();
			//맵 전체 순회
			queue.add(targetMap);
			while(!queue.isEmpty()) {
				Map<String, Object> child = queue.poll();
				Set<Map.Entry<String, Object>> childEntrySet = new HashMap<String, Object>(child).entrySet();
				for(Map.Entry<String, Object> childEntry : childEntrySet) {
					String key = childEntry.getKey();
					Object value = childEntry.getValue();
					if(StringUtil.equals(key, orgKey)) {
						String childUserId = (String) value;
						if(StringUtil.isNotEmpty(childUserId)) {
							String userName = userNameMap.get(childUserId);
							if(StringUtil.isNotEmpty(userName)) {
								child.put(addKey, userName);
							}
							else {
								child.put(addKey, ""); // null 말고 "" 값으로 설정
							}
						}
					}else if(value instanceof Map) {
						queue.add((Map<String, Object>)value);
					}else if(value instanceof List) {
						List<Map<String, Object>> list = (List<Map<String, Object>>) value;
						for(Map<String, Object> row : list) {
							queue.add(row);
						}
					}
				}
			}
		}
	}
	
	public String subscriptionMethod(String uri, String params) throws Exception {
		String inputLine = null; 
		StringBuffer outResult = new StringBuffer();
		try {
			URL url = new URL(uri); 
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
			conn.setDoOutput(true); 
			conn.setRequestMethod("POST"); 
			conn.setRequestProperty("Content-Type", "application/json"); 
			conn.setRequestProperty("Accept-Charset", "UTF-8"); 
			conn.setConnectTimeout(1000); 
			conn.setReadTimeout(1000);
				
			OutputStream os = conn.getOutputStream();
			os.write(params.getBytes("UTF-8")); 
			os.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine); 
			} 
			conn.disconnect(); 
		} catch (Exception ex) {
			throw ex;
		}
		return outResult.toString();
	}
}
