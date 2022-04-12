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
package kr.co.n2m.smartcity.admin.feature.authgroup.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.admin.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;
import kr.co.n2m.smartcity.admin.feature.authgroup.service.AuthgroupService;
import kr.co.n2m.smartcity.admin.feature.authgroup.vo.AuthgroupMenuMapVo;
import kr.co.n2m.smartcity.admin.feature.authgroup.vo.AuthgroupVo;
import kr.co.n2m.smartcity.admin.feature.authgroup.vo.SrchAuthgroupMenuMapVo;
import kr.co.n2m.smartcity.admin.feature.authgroup.vo.SrchAuthgroupUserMapVo;
import kr.co.n2m.smartcity.admin.feature.authgroup.vo.SrchAuthgroupVo;


@RestController
public class AuthgroupController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthgroupService authgroupService;

	/**
	 * 
	 * 권한그룹 목록
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 23.
	 * @param srchAuthgroupVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/permissionGroup")
	public ResponseEntity<Object> getAuthgroupList(SrchAuthgroupVo srchAuthgroupVo) {
		Map<String,Object> resMap = authgroupService.getAuthgroupList(srchAuthgroupVo);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	/**
	 * 
	 * 권한그룹 생성
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 23.
	 * @param authgroupCreUsrId
	 * @param srchAuthgroupVo
	 * @return
	 */
	@PostMapping("/dataserviceUi/permissionGroup")
	public ResponseEntity<Object> createAuthgroup(@RequestHeader("UserId") String authgroupCreUsrId,@RequestBody SrchAuthgroupVo srchAuthgroupVo) {
		srchAuthgroupVo.setAuthgroupCreUsrId(authgroupCreUsrId);
		String vo = authgroupService.createAuthgroup(srchAuthgroupVo);
		return ResponseUtil.makeResponseEntity(vo,HttpStatus.CREATED,"/dataserviceUi/permissionGroup"+vo);
	}
	/**
	 * 권한그룹 상세 정보 조회
	 * @param srchAuthgroupVo
	 * @return
	 */
	
	@GetMapping("/dataserviceUi/permissionGroup/{authgroupOid}")
	public ResponseEntity<Object> getAuthgroupList(@PathVariable long authgroupOid) {
		AuthgroupVo authgroup = authgroupService.getAuthgroup(authgroupOid);
		return ResponseUtil.makeResponseEntity(authgroup);
	}
	
	/**
	 * 권한그룹 수정
	 * @param srchAuthgroupVo
	 * @return
	 * @throws Exception 
	 */
	
	@PutMapping("/dataserviceUi/permissionGroup/{authgroupOid}")
	public ResponseEntity<Object> modifyAuthgroup(@PathVariable String authgroupOid,@RequestHeader("UserId") String authgroupUptUsrId,@RequestBody SrchAuthgroupVo srchAuthgroupVo) throws Exception {
		StringUtil.compareUniqueId(authgroupUptUsrId, srchAuthgroupVo.getAuthgroupUptUsrId());
		srchAuthgroupVo.setAuthgroupUptUsrId(authgroupUptUsrId);
		authgroupService.modifyAuthgroup(srchAuthgroupVo);
		return ResponseUtil.makeResponseEntity(null);
	}
	/**
	 * 권한그룹 삭제
	 * @param srchAuthgroupVo
	 * @return
	 * @throws Exception 
	 */
	
	@DeleteMapping("/dataserviceUi/permissionGroup/{authgroupOid}")
	public ResponseEntity<Object> deleteAuthgroup(@PathVariable String authgroupOid, @RequestBody SrchAuthgroupVo srchAuthgroupVo) throws Exception {
		authgroupService.deleteAuthgroup(srchAuthgroupVo);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}
	/**
	 * 
	 * 권한그룹 관리자 생성
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 15.
	 * @param srchAuthgroupVo
	 * @return
	 * @throws Exception 
	 */
	
	@PostMapping("/dataserviceUi/permissionGroup/{authgroupOid}/contact")
	public ResponseEntity<Object> createAuthgroupuser(@PathVariable String authgroupOid,@RequestBody Map<String, Object> params) throws Exception {
		String id = authgroupService.createAuthgroupUser(params);
		return ResponseUtil.makeResponseEntity(id,HttpStatus.CREATED,null);
	}
	/**
	 * 
	 * 권한그룹 관리자 리스트
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 15.
	 * @param srchAuthgroupUserMapVo
	 * @return
	 */
	
	@GetMapping("/dataserviceUi/permissionGroup/{authgroupOid}/contact")
	public ResponseEntity<Object> getAuthgroupUserList(@PathVariable long authgroupOid, SrchAuthgroupUserMapVo srchAuthgroupUserMapVo) {
		Map<String,Object> map = authgroupService.getAuthgroupUserList(srchAuthgroupUserMapVo);
		return ResponseUtil.makeResponseEntity(map);
	}
	/**
	 * 
	 * 권한그룹 메뉴 리스트
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @param authgroupOid
	 * @param srchAuthgroupMenuMapVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/permissionGroup/{authgroupOid}/menu")
	public ResponseEntity<Object> getAuthgroupMenuList(@PathVariable String authgroupOid, SrchAuthgroupMenuMapVo srchAuthgroupMenuMapVo) {
		List<AuthgroupMenuMapVo> list = authgroupService.getAuthgroupMenuList(srchAuthgroupMenuMapVo);
		return ResponseUtil.makeResponseEntity(list);
	}
	/**
	 * 
	 * 권한그룹 메뉴 업데이트
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 15.
	 * @param srchAuthgroupMenuMapVo
	 * @return
	 * @throws Exception 
	 */
	
	@PostMapping("/dataserviceUi/permissionGroup/{authgroupOid}/menu")
	public ResponseEntity<Object> updateAuthgroupMenu(@PathVariable String authgroupOid, @RequestBody Map<String, Object> params, SrchAuthgroupMenuMapVo srchAuthgroupMenuMapVo ) throws Exception {
		StringUtil.compareUniqueId(authgroupOid, srchAuthgroupMenuMapVo.getAuthgroupOid());
		authgroupService.updateAuthgroupMenu(params, srchAuthgroupMenuMapVo);
		return ResponseUtil.makeResponseEntity(null);
	}

	
	
}
