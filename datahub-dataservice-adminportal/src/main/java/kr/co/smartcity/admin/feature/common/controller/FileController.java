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
package kr.co.smartcity.admin.feature.common.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.smartcity.admin.feature.common.service.FileService;

@Controller
public class FileController {
	@Resource(name = "fileService")
	private FileService fileService;
	
	/**
	 * <pre>파일 다운로드</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @param res
	 */
	@RequestMapping(value = "/downloadFile.do", produces="text/plain;charset=UTF-8")
	public void downloadFile(@RequestParam Map<String, Object> params, HttpServletResponse res) {
		fileService.download(params, res);
	}
	
	/**
	 * 엑셀 다운로드
	 * @Author      : junheecho
	 * @Date        : 2019. 10. 31.
	 * @param params
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadExcelFile.do", produces="text/plain;charset=UTF-8")
	public void downloadExcelFile(@RequestParam Map<String, Object> params, HttpServletResponse response, HttpServletRequest request) throws Exception {
		fileService.downloadExcel(params, response, request);
	}
}
