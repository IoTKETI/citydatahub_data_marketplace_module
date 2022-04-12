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
package kr.co.n2m.smartcity.admin.common.component;

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.Logger;

@Component
public class CommonComponent {
	private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	protected Gson gson;	
	
	public CommonComponent() {
		gson = new GsonBuilder().create();
	}
	
	public Map<String, Object> toMap(String json) {
		return gson.fromJson(json, Map.class);
	}
	public String toJson(Object obj) {
		return gson.toJson(obj);
	}
	
	/**
	 * 
	 * makeResponseEntity()
	 * 
	 * REST API HTTP 결과 객체 생성 메소드
	 * 
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param obj
	 * @return
	 */
	public ResponseEntity<Object> makeResponseEntity(Object obj){
		HttpHeaders headers = new HttpHeaders();
		return makeResponseEntity(obj, headers, HttpStatus.OK); 
	}
	public ResponseEntity<Object> makeResponseEntity(Object obj, HttpStatus status){
		HttpHeaders headers = new HttpHeaders();
		return makeResponseEntity(obj, headers, status); 
	}
	public ResponseEntity<Object> makeResponseEntity(Object obj, HttpHeaders headers){
		return makeResponseEntity(obj, headers, HttpStatus.OK); 
	}
	public ResponseEntity<Object> makeResponseEntity(Object obj, HttpHeaders headers, HttpStatus status){
		headers.add("Content-Type", "application/json; chatset=utf8");
		return new ResponseEntity<Object>(obj, headers, status); 
	}
	
	public void logger(String msg, Object obj) {
		logger.info(">>>>>>>>>>"+msg, obj);
	}
	public void logger(String msg) {
		logger.info(">>>>>>>>>>"+msg);
	}
}
