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
package kr.co.n2m.smartcity.notification.common.component;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import kr.co.n2m.smartcity.notification.common.exceptions.HttpInternalServerErrorException;
import kr.co.n2m.smartcity.notification.common.exceptions.HttpNotFoundException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class HttpComponent extends CommonComponent {
	
	public final static MediaType JSON = MediaType.get("application/json; charset=utf-8");
	
	@Autowired
	private OkHttpClient client;
	
	@Autowired
	public Gson gson;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	public Map<String, Object> convertMap(String json){
		return gson.fromJson(json, Map.class);
	}
	
	public Response method(String method, String uri, Map<String, Object> params) throws Exception {
		Response response = null;
		
		try {
			String json = gson.toJson(params);
			RequestBody body = RequestBody.create(JSON, json);
			
			Request.Builder builder = new Request.Builder();
			
			Request request = 	builder
					.url(uri)
				    .method(method, body)
				    .build();
			
			response = client.newCall(request).execute();
			
			if ( !response.isSuccessful()) {
				Exception ee = null;
				if (response.code() == HttpStatus.NOT_FOUND.value()) {
					ee = new HttpNotFoundException(response.code());
				} else {
					ee = new HttpInternalServerErrorException(response.code());
				}
				throw ee;
			}
		} catch (Exception ex) {
			throw ex;
		}
		return response;
	}

}
