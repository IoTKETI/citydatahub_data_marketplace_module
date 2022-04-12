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
package kr.co.n2m.smartcity.notification.common.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	
	
	/**
	 * <pre>인증/인가 오류 발생 시 401 관련</pre>
	 * @Author		: hk-lee
	 * @Date		: 2019. 10. 25.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpUnauthorizedExcpetion.class)
	public ResponseEntity<String> handleServletException(HttpServletRequest request, HttpUnauthorizedExcpetion ex) {
		
		return new ResponseEntity<> (ex.getMessage(), null, HttpStatus.UNAUTHORIZED);
	}
	/**
	 * <pre> Forbidden 403</pre>
	 * @Author		: hk-lee
	 * @Date		: 2019. 10. 25.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpForbiddenException.class)
	public ResponseEntity<String> handleServletException(HttpServletRequest request, HttpForbiddenException ex) {
		
		return new ResponseEntity<> (ex.getMessage(), null, HttpStatus.FORBIDDEN);
	}
	/**
	 * <pre>Not Found 404</pre>
	 * @Author		: hk-lee
	 * @Date		: 2019. 10. 25.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpNotFoundException.class)
	public ResponseEntity<String> handleServletException(HttpServletRequest request, HttpNotFoundException ex) {
		
		return new ResponseEntity<> (ex.getMessage(), null, HttpStatus.NOT_FOUND);
	}
	/**
	 * <pre>Interanl Server Error 500</pre>
	 * @Author		: hk-lee
	 * @Date		: 2019. 10. 25.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpInternalServerErrorException.class)
	public ResponseEntity<String> handleServletException(HttpServletRequest request, HttpInternalServerErrorException ex) {
		
		return new ResponseEntity<> (ex.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
