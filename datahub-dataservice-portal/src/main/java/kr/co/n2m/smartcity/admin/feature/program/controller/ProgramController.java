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
package kr.co.n2m.smartcity.admin.feature.program.controller;

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.admin.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;
import kr.co.n2m.smartcity.admin.feature.program.service.ProgramService;
import kr.co.n2m.smartcity.admin.feature.program.vo.ProgramGroupVo;
import kr.co.n2m.smartcity.admin.feature.program.vo.ProgramVo;
import kr.co.n2m.smartcity.admin.feature.program.vo.SrchProgramGroupVo;
import kr.co.n2m.smartcity.admin.feature.program.vo.SrchProgramVo;

@RestController
public class ProgramController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProgramService programService;
	
	/**
	 * 프로그램 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param srchProgramVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/programGroup/{programGroupOid}/programs")
	public ResponseEntity<Object> getProgramList(@PathVariable String programGroupOid,SrchProgramVo srchProgramVo, @RequestHeader("UserId") String programCreUsrId) {
		srchProgramVo.setProgramCreUsrId(programCreUsrId);
		if(StringUtil.equals("Y", srchProgramVo.getAuthYn())) {
			Map<String,Object> map = programService.getAuthProgramList(srchProgramVo);
			return ResponseUtil.makeResponseEntity(map);
		}
		Map<String,Object> map = programService.getProgramList(srchProgramVo);
		return ResponseUtil.makeResponseEntity(map);
	}
	
	/**
	 * 프로그램 상세 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param programOid
	 * @param srchProgramVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/programGroup/{programGroupOid}/programs/{programOid}")
	public ResponseEntity<Object> getProgramList(@PathVariable long programGroupOid, @PathVariable long programOid) {
		ProgramVo programVo = programService.getProgram(programOid,programGroupOid);
		return ResponseUtil.makeResponseEntity(programVo);
	}
	
	/**
	 * 프로그램 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param srchProgramVo
	 * @param userId
	 * @return
	 */
	@PostMapping("/dataserviceUi/programGroup/{programGroupOid}/programs")
	public ResponseEntity<Object> createProgram(@PathVariable String programGroupOid,@RequestBody SrchProgramVo srchProgramVo, @RequestHeader("UserId") String userId) {
		srchProgramVo.setProgramCreUsrId(userId);
		String vo = programService.createProgram(srchProgramVo);
		return ResponseUtil.makeResponseEntity(vo,HttpStatus.CREATED,"/dataserviceUi/programGroup/"+vo+"/programs");
	}
	
	/**
	 * 프로그램 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param programOid
	 * @param srchProgramVo
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	@PutMapping("/dataserviceUi/programGroup/{programGroupOid}/programs/{programOid}")
	public ResponseEntity<Object> modifyProgram(@PathVariable String programOid, @PathVariable String programGroupOid, @RequestBody SrchProgramVo srchProgramVo, @RequestHeader("UserId") String userId) throws Exception {
		StringUtil.compareUniqueId(programGroupOid, srchProgramVo.getProgramgroupOid());
		StringUtil.compareUniqueId(programOid, String.valueOf(srchProgramVo.getProgramOid()));
		srchProgramVo.setProgramUptUsrId(userId);
		programService.modifyProgram(srchProgramVo);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	/**
	 * 프로그램 삭제
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param programOid
	 * @param programVo
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping("/dataserviceUi/programGroup/{programGroupOid}/programs/{programOid}")
	public ResponseEntity<Object> deleteProgram(@PathVariable String programOid, @RequestBody SrchProgramVo srchProgramVo, @RequestHeader("UserId") String userId) throws Exception {
		srchProgramVo.setProgramUptUsrId(userId);
		programService.deleteProgram(srchProgramVo);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}
	
	/**
	 * 프로그램 그룹 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param srchProgramGroupVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/programGroup")
	public ResponseEntity<Object> getProgramGroupList(SrchProgramGroupVo srchProgramGroupVo) {
		Map<String,Object> resMap = programService.getProgramGroupList(srchProgramGroupVo);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * 프로그램 그룹 상세 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param programGroupId
	 * @param srchProgramGroupVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/programGroup/{programGroupOid}")
	public ResponseEntity<Object> getProgramGroup(@PathVariable long programGroupOid) {
		ProgramGroupVo programGroupVo = programService.getProgramGroup(programGroupOid);
		return ResponseUtil.makeResponseEntity(programGroupVo);
	}
	
	/**
	 * 프로그램 그룹 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param srchProgramGroupVo
	 * @param userId
	 * @return
	 */
	@PostMapping("/dataserviceUi/programGroup")
	public ResponseEntity<Object> createProgramGroup(@RequestBody SrchProgramGroupVo srchProgramGroupVo, @RequestHeader("UserId") String userId) {
		srchProgramGroupVo.setProgramgroupCreUsrId(userId);
		String vo = programService.createProgramGroup(srchProgramGroupVo);
		return ResponseUtil.makeResponseEntity(vo,HttpStatus.NO_CONTENT,"/dataserviceUi/programGroup"+vo);
	}
	
	/**
	 * 프로그램 그룹 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param programGroupId
	 * @param srchProgramGroupVo
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	@PutMapping("/dataserviceUi/programGroup/{programGroupOid}")
	public ResponseEntity<Object> modifyProgramGroup(@PathVariable long programGroupOid, @RequestBody SrchProgramGroupVo srchProgramGroupVo, @RequestHeader("UserId") String userId) throws Exception {
		StringUtil.compareUniqueId(programGroupOid, srchProgramGroupVo.getProgramgroupOid());
		srchProgramGroupVo.setProgramgroupUptUsrId(userId);
		programService.modifyProgramGroup(srchProgramGroupVo);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	/**
	 * 프로그램 그룹 삭제
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param programGroupId
	 * @param srchProgramGroupVo
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping("/dataserviceUi/programGroup/{programGroupOid}")
	public ResponseEntity<Object> deleteProgramGroup(@PathVariable long programGroupOid, @RequestBody SrchProgramGroupVo srchProgramGroupVo, @RequestHeader("UserId") String userId) throws Exception {
		srchProgramGroupVo.setProgramgroupOid(programGroupOid);
		programService.deleteProgramGroup(srchProgramGroupVo);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}
	
}
