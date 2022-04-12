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
package kr.co.n2m.smartcity.admin.feature.codegroup.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.admin.common.exceptions.ClientException;
import kr.co.n2m.smartcity.admin.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;
import kr.co.n2m.smartcity.admin.feature.codegroup.service.CodeGroupService;
import kr.co.n2m.smartcity.admin.feature.codegroup.vo.CodeGroupVo;
import kr.co.n2m.smartcity.admin.feature.codegroup.vo.CodeVo;
import kr.co.n2m.smartcity.admin.feature.codegroup.vo.SrchCodeGroupVo;
import kr.co.n2m.smartcity.admin.feature.codegroup.vo.SrchCodeVo;


@RestController
public class CodeGroupController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CodeGroupService codeGroupService;

	/**
	 * 코드그룹 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param srchCodeGroupVo
	 * @return
	 */
	@GetMapping("/dataservice/codeGroup")
	public ResponseEntity<Object> getCodeGroup(SrchCodeGroupVo srchCodeGroupVo,@RequestParam HashMap<String, String> params) {
		Map<String, Object> resMap = codeGroupService.getCodeGroupList(srchCodeGroupVo,params);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * 코드그룹 상세 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param srchCodeGroupVo
	 * @return
	 */
	@GetMapping("/dataservice/codeGroup/{codeGroupId}")
	public ResponseEntity<Object> getCodeGroup(@PathVariable String codeGroupId) {
		CodeGroupVo codeGroupVo =  codeGroupService.getCodeGroup(codeGroupId);
		return ResponseUtil.makeResponseEntity(codeGroupVo);
	}
	
	/**
	 * 코드그룹 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param srchCodeGroupVo
	 * @return
	 */
	@PostMapping("/dataservice/codeGroup")
	public ResponseEntity<Object> createCodeGroup(@RequestBody SrchCodeGroupVo srchCodeGroupVo, @RequestHeader("UserId") String userId) {
		srchCodeGroupVo.setCodeGroupCreUsrId(userId);
		codeGroupService.createCodeGroup(srchCodeGroupVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED);
	}
	/**
	 * 코드그룹 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param srchCodeGroupVo
	 * @return
	 * @throws Exception 
	 */
	@PutMapping("/dataservice/codeGroup/{codeGroupId}")
	public ResponseEntity<Object> modifyCodeGroup(@PathVariable String codeGroupId, @RequestBody SrchCodeGroupVo srchCodeGroupVo, @RequestHeader("UserId") String userId) throws Exception {
		StringUtil.compareUniqueId(codeGroupId, srchCodeGroupVo.getCodeGroupId());
		
		srchCodeGroupVo.setCodeGroupUptUsrId(userId);
		codeGroupService.modifyCodeGroup(srchCodeGroupVo);
		return ResponseUtil.makeResponseEntity();
	}
	
	
	/**
	 * 코드 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param srchCodeVo
	 * @return
	 */
	@GetMapping("/dataservice/codeGroup/{codeGroupId}/codes")
	public ResponseEntity<Object> getCodeList(@PathVariable String codeGroupId, SrchCodeVo srchCodeVo) {
		srchCodeVo.setCodeGroupId(codeGroupId);
		Map<String,Object> resMap = codeGroupService.getCodeList(srchCodeVo);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * 코드 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param srchCodeVo
	 * @param userId
	 * @return
	 */
	@PostMapping("/dataservice/codeGroup/{codeGroupId}/codes")
	public ResponseEntity<Object> createCode(@PathVariable String codeGroupId, @RequestBody SrchCodeVo srchCodeVo, @RequestHeader("UserId") String userId) {
		srchCodeVo.setCodeCreUsrId(userId);
		srchCodeVo.setCodeGroupId(codeGroupId);
		codeGroupService.createCode(srchCodeVo);
		return ResponseUtil.makeResponseEntity(null, HttpStatus.CREATED);
	}
	
	/**
	 * 코드 상세
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param codeId
	 * @param srchCodeVo
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/dataservice/codeGroup/{codeGroupId}/codes/{codeId}")
	public ResponseEntity<Object> getCode(@PathVariable String codeGroupId, @PathVariable String codeId) {
		CodeVo codeVo = codeGroupService.getCode(codeGroupId,codeId);
		return ResponseUtil.makeResponseEntity(codeVo);
	}
	
	/**
	 * 코드 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param codeId
	 * @param srchCodeVo
	 * @param userId
	 * @return
	 * @throws ClientException 
	 */
	@PutMapping("/dataservice/codeGroup/{codeGroupId}/codes/{codeId}")
	public ResponseEntity<Object> modifyCode(@PathVariable String codeGroupId, @PathVariable String codeId, @RequestBody SrchCodeVo srchCodeVo, @RequestHeader("UserId") String userId) throws ClientException {
		StringUtil.compareUniqueId(codeGroupId, srchCodeVo.getCodeGroupId());
		StringUtil.compareUniqueId(codeId, srchCodeVo.getCodeId());
		srchCodeVo.setCodeUptUsrId(userId);
		srchCodeVo.setCodeId(codeId);
		srchCodeVo.setCodeGroupId(codeGroupId);
		codeGroupService.modifyCode(srchCodeVo);
		return ResponseUtil.makeResponseEntity(null);
	}
	

	
}
