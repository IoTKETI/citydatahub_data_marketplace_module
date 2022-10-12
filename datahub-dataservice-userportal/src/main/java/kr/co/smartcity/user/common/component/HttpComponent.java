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
package kr.co.smartcity.user.common.component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import org.apache.commons.collections.ListUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.gson.Gson;

import kr.co.smartcity.user.common.entity.UserInfo;
import kr.co.smartcity.user.common.exceptions.HttpForbiddenException;
import kr.co.smartcity.user.common.exceptions.HttpInternalServerErrorException;
import kr.co.smartcity.user.common.exceptions.HttpNotFoundException;
import kr.co.smartcity.user.common.exceptions.HttpUnauthorizedExcpetion;
import kr.co.smartcity.user.common.util.StringUtil;
import kr.co.smartcity.user.common.vo.UserVo;
import okhttp3.HttpUrl;
import okhttp3.HttpUrl.Builder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Component
public class HttpComponent extends CommonComponent{
	
	public final static MediaType JSON = MediaType.get("application/json; charset=utf-8");
	
	@Autowired
	OkHttpClient client;
	
	@Autowired
	public Gson gson;	
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
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
	
	public Map<String, Object> toMap(ResponseEntity<Object> entity) {
		return toMap((String) entity.getBody());
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> toList(String jsonStr) {
		return (List<Map<String, Object>>) gson.fromJson(jsonStr, List.class);
	}
	
	public String strBody(Response response) throws Exception {
		return response.body().string();
	}
	
	public Map<String, Object> mapBody(Response response) throws Exception {
		return this.toMap(response.body().string());
	}
	
	public String get(String uri) throws Exception {
		return this.get(uri, null);
	}
	
	public String get(String uri, Map<String, String> params) throws Exception{
		return this.get(uri, null, params);
	}
	
	public String get(String uri, Map<String, String> headers, Map<String, String> params) throws Exception {
		String responseJson = "";
		
		Response response = null;
		ResponseBody responseBody = null;
		
		try {
			
			Builder urlBuilder = HttpUrl.parse(uri).newBuilder();
			
			Request.Builder builder = new Request.Builder();
			
			if(params != null) {
				if (params.containsKey("contentType")) {
					builder.addHeader("Content-Type", params.get("contentType"));
					params.remove("contentType");
				}
				
				for(Map.Entry<String, String> param : params.entrySet()) {
					urlBuilder.addQueryParameter(param.getKey(), param.getValue());
				}
			}
			
			if (headers == null || headers.isEmpty()) {
				setTokenInfo(builder);				
			} else {
				for(Map.Entry<String, String> header : headers.entrySet()) {
					builder.addHeader(header.getKey(), header.getValue());
				}				
			}
			
			Request request = 	builder
					.addHeader("Accept", "application/json")
					.url(urlBuilder.build())
					.get()
					.build();
			
			response = client.newCall(request).execute();

			responseBody = response.body();
			responseJson = responseBody.string();
			
			if ( !response.isSuccessful()) {
				Exception ee = null;

				if (response.code() == HttpStatus.UNAUTHORIZED.value() || response.code() == HttpStatus.BAD_REQUEST.value()) {
					ee = new HttpUnauthorizedExcpetion(responseJson);
				} else if (response.code() == HttpStatus.NOT_FOUND.value()) {
					ee = new HttpNotFoundException(responseJson);
				} else if (response.code() == HttpStatus.FORBIDDEN.value()) {
					ee = new HttpForbiddenException(responseJson);
				} else if (response.code() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
					ee = new HttpInternalServerErrorException(responseJson);
				}
				throw ee;
			}
		} catch (Exception ex) {
			LOG.error("==> ", ex);
			
			throw ex;
		} finally {
			if (responseBody !=null) responseBody.close();
			if (response != null) response.close();
		}
		
		return responseJson;
	}
	
	
	public Response method(String method, String uri) throws Exception {
		return this.method(method, uri, null);
	}
	
	public Response method(String method, String uri, Map<String, Object> params) throws Exception {
		return method(method, uri, params, new HashMap<String, Object>());
	}
	
	public Response method(String method, String uri, Map<String, Object> params, Map<String, Object> headers) throws Exception {
		String responseJson = "";
		
		Response response = null;
		
		try {
			String json = toJson(params);
			RequestBody body = RequestBody.create(JSON, json);
			
			LOG.debug("RequestBody = > " + json);
			
			Request.Builder builder = new Request.Builder();
			
			if (headers != null && headers.keySet().size() > 0) {
				for(Entry<String, Object> header : headers.entrySet()) {
					builder.addHeader(header.getKey(), String.valueOf(header.getValue()));
				}
			} else {
				setTokenInfo(builder);
			}
			
			Request request = 	builder
					.url(uri)
				    .method(method, body)
				    .build();
			
			response = client.newCall(request).execute();
			
			if ( !response.isSuccessful()) {
				Exception ee = null;
				
				if (response.code() == HttpStatus.UNAUTHORIZED.value()) {
					ee = new HttpUnauthorizedExcpetion(responseJson);
				} else if (response.code() == HttpStatus.NOT_FOUND.value()) {
					ee = new HttpNotFoundException(responseJson);
				} else if (response.code() == HttpStatus.FORBIDDEN.value()) {
					ee = new HttpForbiddenException(responseJson);
				} else if (response.code() == HttpStatus.CONFLICT.value()) {
					ee = new HttpInternalServerErrorException(responseJson);
				} else if (response.code() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
					ee = new HttpInternalServerErrorException(responseJson);
				}
				throw ee;
			}
		} catch (Exception ex) {
			throw ex;
		}
		return response;
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
	
	@SuppressWarnings("unchecked")
	public Response method(String method, String uri, Map<String, Object> params, HttpServletRequest req) throws Exception {
		String responseJson = "";
		
		MultipartRequest mReq = null;
		Iterator<String> it = null;
		
		String reqMethod = req.getMethod();
		if(ServletFileUpload.isMultipartContent(req) || HttpMethod.PUT.toString().equalsIgnoreCase(reqMethod) || HttpMethod.PATCH.toString().equalsIgnoreCase(reqMethod)) {
			mReq = (MultipartRequest) req;
			it = mReq.getFileNames();
			
		}
		
		okhttp3.MultipartBody.Builder reqBuilder = new MultipartBody.Builder();
		reqBuilder.setType(MultipartBody.FORM);
		
		Response response = null;
		
		try {
			Request.Builder builder = new Request.Builder();
			setTokenInfo(builder);
			
			while(it != null && it.hasNext()) {
				String fileName = it.next();
				MultipartFile file = mReq.getFile(fileName);
				String fieldName = file.getName();
				File f = File.createTempFile(UUID.randomUUID().toString(), ".tmp");
				IOUtils.copy(file.getInputStream(), new FileOutputStream(f));
				
				// req key 순회하면서 addFormDataPart에 추가
				reqBuilder.addFormDataPart(fieldName, file.getOriginalFilename(), RequestBody.create(MultipartBody.FORM, f));
				
			}
			
			Iterator<Entry<String, Object>> paramsIt = params.entrySet().iterator();
			while(paramsIt.hasNext()) {
				Entry<String, Object> entry = paramsIt.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				value = toObject(toJson(value));
				
				if(value instanceof String) {
					reqBuilder.addFormDataPart(entry.getKey(), (String) value);
				}else if(value instanceof Double){
					reqBuilder.addFormDataPart(key, toJson(value));
				}else if(value instanceof ArrayList){
					List<Map<String, Object>> arr = (List<Map<String, Object>>) value;
					reqBuilder.addFormDataPart(key, toJson(arr));
				}
			}
			
			RequestBody body = reqBuilder.build();
			
			Request request = 	builder
					.url(uri)
					.method(method, body)
					.build();
			
			response = client.newCall(request).execute();
			
			if ( !response.isSuccessful()) {
				Exception ee = null;
				
				if (response.code() == HttpStatus.UNAUTHORIZED.value()) {
					ee = new HttpUnauthorizedExcpetion(responseJson);
				} else if (response.code() == HttpStatus.NOT_FOUND.value()) {
					ee = new HttpNotFoundException(responseJson);
				} else if (response.code() == HttpStatus.FORBIDDEN.value()) {
					ee = new HttpForbiddenException(responseJson);
				} else if (response.code() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
					ee = new HttpInternalServerErrorException(responseJson);
				}
				throw ee;
			}
		} catch (Exception ex) {
			throw ex;
		}
		return response;
	}
	
	public void downloadFile(String url, Map<String, Object> params, HttpServletResponse res) {
		
		byte[] bytes = null;
		
		Response response = null;
		ResponseBody responseBody = null;
		
		try { 
			 
			Request.Builder builder = new Request.Builder(); 
			setTokenInfo(builder);
			
			Request request = builder
					.url(url)
					.get()
					.build();
			
			response = client.newCall(request).execute();
			responseBody = response.body();
			
			res.setHeader("Content-Disposition", response.header("Content-Disposition"));
			res.setHeader("Content-Length", response.header("Content-Length"));
			bytes = responseBody.bytes();
			
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			IOUtils.copy(bis, res.getOutputStream());
			
		}catch(IOException e) {
			e.printStackTrace();
		} finally {
			if (responseBody !=null) responseBody.close();
			if (response != null) response.close();
		}
		
	}
	
	protected String getLoginUserId() {
		String userId = "";
		ServletRequestAttributes servletContainer = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		HttpServletRequest req = servletContainer.getRequest();
		HttpSession session = req.getSession();
		String token = (String) session.getAttribute("authToken");
		if(token != null) {
			UserInfo userInfo =  (UserInfo) session.getAttribute("user");
			if(userInfo != null) {
				userId = (String) userInfo.getUserId();
			}
		}
		return userId;
	}
	
	private void setTokenInfo(Request.Builder builder) {
		String token = "";
		String userId = "";
		ServletRequestAttributes servletContainer = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		HttpServletRequest req = servletContainer.getRequest();
		HttpSession session = req.getSession();
		token = (String) session.getAttribute("authToken");
		
		if(token != null) {
			UserInfo userInfo =  (UserInfo) session.getAttribute("user");
			if(userInfo != null) {
				userId = (String) userInfo.getUserId();
			}
			builder.removeHeader("Authorization");
			builder.addHeader("Authorization", "Bearer " + token);
			
			builder.removeHeader("dh-accesstoken");
			builder.addHeader("dh-accesstoken", String.format("{\"userId\":\"%s\",\"token\":\"%s\"}", userId, token));
		}
	}
	
	/**
	 * 접두어를 포함한 세션 정보 가져오기
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 22.
	 * @param prefix
	 * @param name
	 * @return
	 */
	public static Object getSessionAttribute(String prefix, String name) {
		ServletRequestAttributes servletContainer = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		HttpServletRequest req = servletContainer.getRequest();
		HttpSession session = req.getSession();
		Object result = session.getAttribute(prefix + name);
		removeSessionAttribute(prefix, name);
		return result;
	}
	
	/**
	 * 접두어를 포함한 세션 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 22.
	 * @param prefix
	 * @param name
	 * @param object
	 */
	public static void setSessionAttribute(String prefix, String name, Object object) {
		ServletRequestAttributes servletContainer = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		HttpServletRequest req = servletContainer.getRequest();
		HttpSession session = req.getSession();
		removeSessionAttributeWithout(prefix, name);
		session.setAttribute(prefix + name, object);
	}
	/**
	 * 접두어를 포함한 세션 삭제
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 22.
	 * @param prefix
	 * @param name
	 */
	public static void removeSessionAttribute(String prefix, String name) {
		ServletRequestAttributes servletContainer = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		HttpServletRequest req = servletContainer.getRequest();
		HttpSession session = req.getSession();
		session.removeAttribute(prefix+name);
	}
	/**
	 * 접두어를 제외한 세션 삭제
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 22.
	 * @param prefix
	 * @param name
	 */
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
	/**
	 * 
	 * 토큰발급
	 * @Author      : kyunga
	 * @Date        : 2019. 10. 10.
	 * @param method
	 * @param uri
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	public ResponseEntity<Object> postAccessToken(String method, String uri, String code) throws Exception {
		ResponseEntity<Object> responseEntity = null;
		
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("grant_type", "authorization_code");
			map.put("code", code);
			map.put("redirect_uri",props.getOauthRedirectUri());
			map.put("client_id",props.getNormalClientId());
			map.put("client_secret", props.getNormalClientSecretId());
			
			String responseStr = this.strBody(this.method(method, uri, map));
			responseEntity = new ResponseEntity<Object>(responseStr, HttpStatus.OK);
			
		} catch (Exception ex) {
			throw ex;
		}
		return responseEntity;
	}
	
	
	
	public UserVo getUserByType(String userId, UserType type) throws Exception {

		try {
			String strUserInfo = "";
			
			Map<String, String> headers = new HashMap<>();
			
			if(type == UserType.NORMAL) {
				headers.put("Authorization", "Bearer " + this.requestClientCredentialsGrant(type));
				strUserInfo = this.get(props.getNormalUserInfoUrl() + "/" + userId, headers, null);
				
			} else if (type == UserType.ADMIN) {
				headers.put("Authorization", "Bearer " + this.requestClientCredentialsGrant(type));
				strUserInfo = this.get(props.getAdminUserInfoUrl() + "/" + userId, headers, null);
				
			}
			return gson.fromJson(strUserInfo, UserVo.class);
			
		} catch (HttpNotFoundException e) {
			LOG.debug("==> Not found in getUserByType : " + e.getMessage());
		} catch (Exception ex) {
			LOG.error("==> fault error in getUserByType : " + ex);
			
			throw ex;
		}
		return null;
	}
	
	public List<UserVo> getUserListByType(UserType type) throws Exception {
		List<UserVo> userList = new ArrayList<UserVo>();
		
		try {
			Map<String, String> headers = new HashMap<>();
			headers.put("Authorization", "Bearer " + this.requestClientCredentialsGrant(type));
			
			List<Map<String, Object>> _userList = null;
			
			if(type == UserType.NORMAL) {
				_userList = this.toList(this.get(props.getNormalUserInfoUrl(), headers, null));
			} else if (type == UserType.ADMIN) {
				_userList = this.toList(this.get(props.getAdminUserInfoUrl(), headers, null));
			}
			
			for(Map<String, Object> userMap : _userList) {
				String userId 	= (String) userMap.get("userid");
				String nickname = (String) userMap.get("nickname");
				String name		= (String) userMap.get("name");
				String email 	= (String) userMap.get("email");
				String phone 	= (String) userMap.get("phone");
				
				
				UserVo userVo = new UserVo();
				userVo.setUserId(userId);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setPhone(phone);
				userVo.setNickname(nickname);
				
				userList.add(userVo);
			}
		} catch (Exception ex) {
			LOG.error("==> fault error in getUserList : " + ex);
			
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
			// default (UserType.NORMAL)
			String clientId 		= props.getNormalClientId();
			String clientSecret 	= props.getNormalClientSecretId();
			String tokenUrl 		= props.getNormalAccessTokenUrl();
			
			if (type == UserType.ADMIN) {
				clientId 		= props.getAdminClientId();
				clientSecret 	= props.getAdminClientSecretId();
				tokenUrl 		= props.getAdminAccessTokenUrl();
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("grant_type"    , "client_credentials");
			map.put("client_id"     , clientId);
			map.put("client_secret" , clientSecret);
			
			Map<String, Object> resMap = this.mapBody(this.method(HttpMethod.POST.toString(), tokenUrl, map));
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
			
			Map<String, Object> resMap = this.mapBody(this.method(HttpMethod.POST.toString(), tokenUrl, map));
			accessToken = (String) resMap.get("access_token");
			
			
		} catch (Exception ex) {
		   ex.printStackTrace();
		}
		return accessToken;
	}
	
	public Map<String, String> getUserNameMap() {
		Map<String, String> resMap = new HashMap<>();
		try {
			String adminAccessToken = this.getClientAccessToken(UserType.ADMIN);
			Map<String, String> adminHeaders = new HashMap<>();
			adminHeaders.put("Authorization", "Bearer "+adminAccessToken);
			
			String adminUserListBody = this.get( props.getAdminUserInfoUrl(), adminHeaders, null);
			List<Map<String, Object>> adminUserList = this.toList(adminUserListBody);
			for(Map<String, Object> userMap : adminUserList) {
				resMap.put((String) userMap.get("userid"), (String) userMap.get("name"));
			}

			String normalAccessToken = this.getClientAccessToken(UserType.NORMAL);
			Map<String, String> normalHeaders = new HashMap<>();
			normalHeaders.put("Authorization", "Bearer "+normalAccessToken);
			String normalUserListBody = this.get( props.getNormalUserInfoUrl(), normalHeaders, null);
			List<Map<String, Object>> normalUserList = this.toList(normalUserListBody);
			for(Map<String, Object> userMap : normalUserList) {
				resMap.put((String) userMap.get("userid"), (String) userMap.get("name"));
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
	
}
