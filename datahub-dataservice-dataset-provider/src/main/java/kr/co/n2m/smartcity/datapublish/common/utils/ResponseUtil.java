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

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
	/**
	 * 
	 * makeResponseEntity()
	 * 
	 * REST API HTTP 결과 객체 생성 메소드
	 * 
	 * @Author      : hk-lee
	 * @Date        : 2020. 06. 29
	 * @param obj
	 * @return
	 */
	public static ResponseEntity<Object> makeResponseEntity(){
		return makeResponseEntity(null, null, HttpStatus.OK); 
	}
	public static ResponseEntity<Object> makeResponseEntity(Object obj){
		return makeResponseEntity(obj, null, HttpStatus.OK); 
	}
	public static ResponseEntity<Object> makeResponseEntity(Object obj, HttpStatus status){
		if (status == HttpStatus.CREATED) {
			return makeResponseEntity(obj, status, null);
		}
		return makeResponseEntity(obj, null, status); 
	}
	
	/**
	 * <pre>HttpStatus.CREATED(201) 의 경우 생성 되는 고유 ID를 헤더(Location) 포함하여 반환</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 6. 30.
	 * @param obj
	 * @param status
	 * @param location
	 * @return
	 */
	public static ResponseEntity<Object> makeResponseEntity(Object obj, HttpStatus status, String location){
		HttpHeaders headers = new HttpHeaders();
		if (!StringUtil.isEmpty(location)) {
			headers.add("Location", location);
		}
		return makeResponseEntity(obj, headers, HttpStatus.CREATED); 
	}
	public static ResponseEntity<Object> makeResponseEntity(Object obj, HttpHeaders headers, HttpStatus status){
		return new ResponseEntity<Object>(obj, headers, status); 
	}
}
