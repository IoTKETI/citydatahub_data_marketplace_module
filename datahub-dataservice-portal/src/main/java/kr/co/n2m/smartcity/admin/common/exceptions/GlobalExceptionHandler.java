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
package kr.co.n2m.smartcity.admin.common.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.admin.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	
	/**
	 * <pre>인증/인가 오류 발생 시 401 관련</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 10.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AuthException.class)
	public ResponseEntity<Object> handleAuthException(HttpServletRequest request, AuthException ex) {
		return getResponseEntity(HttpStatus.UNAUTHORIZED, ex);
	}
	
	/**
	 * <pre>내부 로직 처리 오류 발생 시 500 관련</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 10.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<Object> handleTokenException(HttpServletRequest request, GlobalException ex) {
		return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}
	
	/**
	 * <pre>내부 로직 처리 오류 발생 시 500 관련</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 31.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleTokenException(HttpServletRequest request, Exception ex) {
		return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}
	
	/**
	 * <pre>Method Not Allowed 오류 처리</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 9. 28.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Object> handleTokenException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
		return getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED, ex);
	}
	
	/**
	 * <pre>POST 요청 시 ID 충돌</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 6. 30.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<Object> handleTokenException(HttpServletRequest request, ConflictException ex) {
		return getResponseEntity(HttpStatus.CONFLICT, ex);
	}
	
	/**
	 * <pre>내부적인 상황에 따라 404 리턴을 하게 되는 경우</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 6. 30.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleTokenException(HttpServletRequest request, NotFoundException ex) {
		return getResponseEntity(HttpStatus.NOT_FOUND, ex);
	}
	
	/**
	 * <pre>사용자 요청 정보가 잘못 된 경우</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 6. 30.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ClientException.class)
	public ResponseEntity<Object> handleTokenException(HttpServletRequest request, ClientException ex) {
		return getResponseEntity(HttpStatus.BAD_REQUEST, ex);
	}

	/**
	 * <pre>@RequestHeader 바인딩 Exception 처리</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 21.
	 * @param userId
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ServletRequestBindingException.class)
	public ResponseEntity<Object> handleBindingException(HttpServletRequest request, ServletRequestBindingException ex) {
	    if(StringUtil.isEmpty(request.getHeader("UserId"))) {
	    	return getResponseEntity(HttpStatus.UNAUTHORIZED, ex);
	    } else {
	    	return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	    }
	}
	
	/**
	 * <pre>리턴메시지 반환</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 11. 8.
	 * @param type
	 * @param title
	 * @param detail
	 * @return
	 */
	public String getResponseMessage(String type, String title, String detail) {
		return String.format("{\"type\":\"%s\",\"title\":\"%s\",\"detail\": \"%s\"}", type, title, detail);
	}
	
	/**
	 * <pre>ResponseEntity 객체 반환</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 20.
	 * @param status
	 * @param e
	 * @return
	 */
	public ResponseEntity<Object> getResponseEntity(HttpStatus status, Exception e){
		
		logger.error(String.format("The following error occurred (%d) => ", status.value()), e);
		
		String type 	= "";
		String title 	= "";
		
		if(status == HttpStatus.UNAUTHORIZED){
			type 	= "http://citydatahub.kr/errors/Unauthorized";
			title 	= "Unauthorized";
		} else if(status == HttpStatus.METHOD_NOT_ALLOWED) {
			type 	= "http://citydatahub.kr/errors/MethodNotAllowed";
			title 	= "Requested API is not defined";
		} else if(status == HttpStatus.BAD_REQUEST) {
			type 	= "http://citydatahub.kr/errors/BadRequestData";
			title 	= "Bad Request Data";
		} else if(status == HttpStatus.NOT_FOUND) {
			type 	= "http://citydatahub.kr/errors/ResourceNotFound";
			title 	= "Resource Not Found";
		} else if(status == HttpStatus.CONFLICT) {
			type 	= "http://citydatahub.kr/errors/AlreadyExists";
			title 	= "Already Exists";
		} else {
			type 	= "https://uri.city-hub.kr/errors/InternalError";
			title 	= "Internal Error";	
		}
		
		String body = getResponseMessage(type, title, e.getMessage());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return ResponseUtil.makeResponseEntity(body, headers, status);
	}
}
