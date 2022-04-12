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
package kr.co.n2m.smartcity.datapublish.common.component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class HttpComponent extends CommonComponent{
	@Autowired
	public Gson gson;	
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
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
	
	public String subscriptionCancelMethod(String uri, String params) throws Exception {
		String inputLine = null; 
		StringBuffer outResult = new StringBuffer();
		try {
			URL url = new URL(uri); 
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
			conn.setDoOutput(true); 
			conn.setRequestMethod("DELETE"); 
			conn.setRequestProperty("Content-Type", "application/json"); 
			conn.setRequestProperty("Accept-Charset", "UTF-8"); 
			conn.setConnectTimeout(1000); 
			conn.setReadTimeout(1000);
			conn.getOutputStream();
			
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
