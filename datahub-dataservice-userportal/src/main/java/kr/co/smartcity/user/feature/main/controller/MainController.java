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
package kr.co.smartcity.user.feature.main.controller;


import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.user.feature.main.service.MainService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value="/main")
public class MainController  {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private MainService mainService;

	/**
	 * 
	 * 메인 페이지 이동
	 * @Author      : kyunga
	 * @Date        : 2019. 10. 4.
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @GetMapping(value = "/pageMain.do")
    public String indexPageOld(HttpSession session, ModelMap model) throws Exception {
    	model.addAttribute("getMainData", mainService.getMainData());
    	return "main/main";
    }
    
    /**
     * 
     * 카테고리별 데이터셋 현황 데이터
     * @Author      : kyunga
     * @Date        : 2019. 10. 4.
     * @param params
     * @return
     * @throws Exception 
     */
    @RequestMapping(value="/category.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getCategory(@RequestParam Map<String, String> params) throws Exception {
    	return mainService.getCategory(params);
	}
   
}
