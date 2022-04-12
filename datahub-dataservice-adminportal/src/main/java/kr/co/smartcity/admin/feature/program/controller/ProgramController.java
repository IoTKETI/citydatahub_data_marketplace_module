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
package kr.co.smartcity.admin.feature.program.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.feature.program.service.ProgramService;

@Controller
@RequestMapping(value = "/programgroup")
public class ProgramController {
	
	@Resource(name = "programService")
	private ProgramService programService;
	
	/**
	 * 프로그램 그룹 목록 페이지 이동
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @return
	 */
	@RequestMapping(value = "/pageList.do")
	public String goPageProgramList(Model model, @RequestParam Map<String, String> params) {
		if ( !(params.containsKey("nav") && params.get("nav") != null) ) {
			model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "PROGRAM_LIST"));
		}
		return "program/programList";
	}
	/**
	 * 프로그램 그룹 수정 페이지 이동
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @return
	 */
	@RequestMapping(value = "/pageModify.do")
	public String goPageProgramModify() {
		return "program/programModify";
	}
	
	/**
	 * 프로그램 그룹 등록 페이지 이동
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @return
	 */
	@RequestMapping(value = "/pageEdit.do")
	public String goPageProgramRegist() {
		return "program/programRegist";
	}
	/**
	 * 프로그램 그룹 상세 페이지 이동
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @return
	 */
	@RequestMapping(value = "/pageDetail.do")
	public String goPageProgramDetail() {
		return "program/programDetail";
	}
	
	/**
	 * 프로그램 그룹 상세 데이터 호출
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/get.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getProgramGroup(@RequestParam Map<String, Object> params) throws Exception {
		return programService.getProgramGroup(params);
	}
	
	/**
	 * 프로그램 그룹 목록 데이터 호출
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getProgramGroupList(@RequestParam Map<String, Object> params) throws Exception {
		return programService.getProgramGroupList(params);
	}
	
	/**
	 * 프로그램 그룹 데이터 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/create.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String createProgramGroup(@RequestBody Map<String, Object> params) throws Exception {
		return programService.createProgramGroup(params);
	}
	
	/**
	 * 프로그램 그룹 데이터 수정
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String modifyProgramGroup(@RequestBody Map<String, Object> params) throws Exception {
		return programService.modifyProgramGroup(params);
	}
	
	/**
	 * 프로그램 그룹 데이터 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/remove.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String removeProgramGroup(@RequestBody Map<String, Object> params) throws Exception {
		return programService.removeProgramGroup(params);
	}
	
	/**
	 * 프로그램 상세 데이터 호출
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/program/get.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getProgram(@RequestParam Map<String, Object> params) throws Exception {
		return programService.getProgram(params);
	}
	
	/**
	 * 프로그램 목록 데이터 호출 
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/program/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getProgramList(@RequestParam Map<String, Object> params) throws Exception {
		return programService.getProgramList(params);
	}
	
	/**
	 * 프로그램 데이터 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/program/create.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String createProgram(@RequestBody Map<String, Object> params) throws Exception {
		return programService.createProgram(params);
	}
	
	/**
	 * 프로그램 데이터 수정
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/program/modify.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String modifyProgram(@RequestBody Map<String, Object> params) throws Exception {
		return programService.modifyProgram(params);
	}
	
	/**
	 * 프로그램 데이터 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 5.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/program/remove.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String removeProgram(@RequestBody Map<String, Object> params) throws Exception {
		return programService.removeProgram(params);
	}

}
