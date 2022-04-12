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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.co.smartcity.user.common.util.CustomizedObjectTypeAdapter;
import kr.co.smartcity.user.common.util.StringUtil;
import okhttp3.ConnectionPool;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class CommonComponent implements Interceptor {
	
	@Autowired
	public Properties props;
	
	public final Logger LOG = LoggerFactory.getLogger(CommonComponent.class);
	
	public enum UserType {
		NORMAL,
		ADMIN
	}
	
	@Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
	
	@Bean
	public OkHttpClient client() {
		int maxConnections = 3;
		long keepDuration = 5;
		return new OkHttpClient().newBuilder()
				.connectionPool(new ConnectionPool(maxConnections, keepDuration, TimeUnit.MINUTES))
				.addInterceptor(this).build();
	}
	
	@Bean
	public OkHttpClient authClient() {
		int maxConnections = 3;
		long keepDuration = 5;
		return new OkHttpClient().newBuilder()
				.connectionPool(new ConnectionPool(maxConnections, keepDuration, TimeUnit.MINUTES)).build();
	}
	
	@Bean
	public Gson gson() {
		CustomizedObjectTypeAdapter adapter = new CustomizedObjectTypeAdapter();
		return new GsonBuilder().registerTypeAdapter(Map.class, adapter).registerTypeAdapter(List.class, adapter) .create();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request original 			= chain.request();
		String accept 				= original.header("Accept");
		String method				= original.method();
		RequestBody originalBody 	= original.body();
		
		
//		LOG.debug("HttpComponent request    :::: " + original +", Headers => " + original.headers().toString());
		LOG.debug("HttpComponent request    :::: " + original);
		
		// HTTP 요청 건에 대해 실행
		Response response 	= chain.proceed(original);
		
		// RestAPI 서버에서 401 (Unauthorized) 응답 시 토큰 재 발급 요청
		if (response.code() == HttpStatus.UNAUTHORIZED.value()) {
			
			// Retry process 구간만 예외처리 진행하며, retry 구간 오류발생 시 위의 Original 정상 요청 건에 대해서만 반환처리하도록 한다.
			try {
				ServletRequestAttributes attrs = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
				HttpServletRequest servletRequest 	= attrs.getRequest();
				HttpServletResponse servletResponse = attrs.getResponse();
				HttpSession servletSession	 		= servletRequest.getSession();
				
				String clientHeaderValue 	= props.getNormalClientId() + ":" + props.getNormalClientSecretId();
				String refreshToken 		= (String) servletSession.getAttribute("refreshtoken");
				
				// 토큰 재발급요청
				Map<String, Object> refreshTokenBody = new HashMap<String, Object>();
				refreshTokenBody.put("grant_type", "refresh_token");
				refreshTokenBody.put("refresh_token", refreshToken);
				
				String json = gson().toJson(refreshTokenBody);
				RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), json);
				
				Request.Builder builder = new Request.Builder();
				Request authRequest = builder
						.url(HttpUrl.parse(props.getNormalAccessTokenUrl()))
						.addHeader("Authorization", "Basic " + Base64Utils.encodeToString(clientHeaderValue.getBytes()))
						.method(HttpMethod.POST.toString(), body)
						.build();
				
				Response authResponse = authClient().newCall(authRequest).execute();
				
				// 토큰 갱신 성공 시 최초 Original request 재 요청
				if ( authResponse.code() == HttpStatus.OK.value()) {
					String tokenBodyStr = authResponse.body().string();
					
					Map<String, Object> tokenMap = gson().fromJson(tokenBodyStr, Map.class);
					String accT = (String) tokenMap.get("access_token");
					
					// Retrying to original request
					Request request = original.newBuilder()
							.header("Accept", accept)
							.header("Authorization", "Bearer " + accT)
							.method(method, originalBody)
							.build();
					
					response.close();
					
					response = chain.proceed(request);
					
					// 세션 값 재 갱신
					servletSession.setAttribute("authToken", accT);
					// 쿠키 값 재 갱신
					servletResponse.addCookie(StringUtil.createCookie("chaut", accT));
				}
				
			} catch (Exception ex) {
				LOG.error("exception to http retry process", ex);
			}
		}
        return response;
	}
}
