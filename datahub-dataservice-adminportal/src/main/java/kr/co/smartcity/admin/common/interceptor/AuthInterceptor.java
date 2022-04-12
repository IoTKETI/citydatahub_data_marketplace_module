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
package kr.co.smartcity.admin.common.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import com.google.gson.Gson;

import kr.co.smartcity.admin.common.exceptions.HttpForbiddenException;
import kr.co.smartcity.admin.common.util.StringUtil;
import kr.co.smartcity.admin.feature.program.service.ProgramService;

public class AuthInterceptor extends WebContentInterceptor {
	
	@Autowired
	private ProgramService programService;
	
	@Autowired 
	private Gson customGson;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {

		String requestUri = request.getRequestURI();
		
		logger.debug("::AuthInterceptor RequestURI : " + requestUri);
		
		try {
			if(!this.verifyToken(request)) {
				this.redirectToLogin(request, response);
				return false;
			}

			if(!this.verifyAuthorization(request)) {
				throw new HttpForbiddenException("Forbidden Program-url => " + requestUri);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new ServletException(e);
		}
		return super.preHandle(request, response, handler);
	}
	
	/**
	 * <pre>토큰 유효성 검사 (현재는 토큰 존재 유무만 판단)</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 22.
	 * @param request
	 * @return
	 */
	private boolean verifyToken(HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("authToken");
		
		return !StringUtil.isEmpty(token);
	}
	
	/**
	 * <pre>로그인 페이지 Redirection</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 22.
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				cookies[i].setValue(null);
				cookies[i].setMaxAge(0);
				cookies[i].setPath("/");
				response.addCookie(cookies[i]);
			}
		}
		response.sendRedirect(request.getContextPath() + "/login/oauthlogin.do");
	}
	
	/**
	 * <pre>호출 URL에 대한 프로그램 권한 체크</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 22.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private boolean verifyAuthorization(HttpServletRequest request) throws Exception {
		
		String requestUri = request.getRequestURI();
		if ("/pageIndex.do".equals(requestUri)) return true;
		
		Map<String, Object> param = new HashMap<>();
		param.put("authYn", "Y");
		param.put("programUrl", requestUri);
		String programUrlJson = programService.getProgramList(param);
		List<Map<String, Object>> authProgramList = (List<Map<String, Object>>) customGson.fromJson(programUrlJson, Map.class).get("list");
		
		if (authProgramList == null || authProgramList.size() <= 0) return false;
		
		// TODO 프로그램 목록에 동일 한 URL이 존재 할 경우 ?? 처리 방안 필요..
		Map<String, Object> map = authProgramList.get(0);
		
		request.setAttribute("menuNm"     , map.get("menuNm"));
		request.setAttribute("readYn"     , map.get("readYn"));
		request.setAttribute("writeYn"    , map.get("writeYn"));
		request.setAttribute("modifiedYn" , map.get("modifiedYn"));
		request.setAttribute("deleteYn"   , map.get("deleteYn"));
		
		return true;
	}
}
