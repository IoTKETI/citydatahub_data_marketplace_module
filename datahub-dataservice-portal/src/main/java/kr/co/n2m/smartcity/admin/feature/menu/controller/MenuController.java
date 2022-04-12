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
package kr.co.n2m.smartcity.admin.feature.menu.controller;

import java.util.HashMap;
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
import kr.co.n2m.smartcity.admin.common.exceptions.ClientException;
import kr.co.n2m.smartcity.admin.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;
import kr.co.n2m.smartcity.admin.feature.menu.service.MenuService;
import kr.co.n2m.smartcity.admin.feature.menu.vo.MenuVo;
import kr.co.n2m.smartcity.admin.feature.menu.vo.SrchMenuVo;

@RestController
public class MenuController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MenuService menuService;
	
	/**
	 * 메뉴 목록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 16.
	 * @param userVo
	 * @return
	 */
	@GetMapping("/dataserviceUi/menu")
	public ResponseEntity<Object> getMenuList(SrchMenuVo srchMenuVo,@RequestParam HashMap<String, String> params) {
		Map<String, Object> map = menuService.getMenuList(srchMenuVo,params);
		return ResponseUtil.makeResponseEntity(map);
	}
	
	/**
	 * 메뉴 상세
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param menuOid
	 * @param srchMenuVo
	 * @return
	 * @throws ClientException 
	 */
	@GetMapping("/dataserviceUi/menu/{menuOid}")
	public ResponseEntity<Object> getMenu(@PathVariable long menuOid) throws ClientException {
		MenuVo menuVo = menuService.getMenu(menuOid);
		return ResponseUtil.makeResponseEntity(menuVo);
	}
	
	/**
	 * 메뉴 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param srchMenuVo
	 * @param userId
	 * @return
	 */
	@PostMapping("/dataserviceUi/menu")
	public ResponseEntity<Object> createMenu(@RequestBody SrchMenuVo srchMenuVo, @RequestHeader("UserId") String userId) {
		srchMenuVo.setMenuCreUsrId(userId);
		String id = menuService.createMenu(srchMenuVo);
		return ResponseUtil.makeResponseEntity(id, HttpStatus.CREATED,"/dataserviceUi/menu"+id);
	}
	
	/**
	 * 메뉴 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param menuOid
	 * @param srchMenuVo
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	@PutMapping("/dataserviceUi/menu/{menuOid}")
	public ResponseEntity<Object> modifyMenu(@PathVariable String menuOid, @RequestBody SrchMenuVo srchMenuVo, @RequestHeader("UserId") String userId) throws Exception {
		StringUtil.compareUniqueId(menuOid, String.valueOf(srchMenuVo.getMenuOid()));
		srchMenuVo.setMenuUptUsrId(userId);
		menuService.modifyMenu(srchMenuVo);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	/**
	 * 메뉴 삭제
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 17.
	 * @param menuOid
	 * @param srchMenuVo
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping("/dataserviceUi/menu/{menuOid}")
	public ResponseEntity<Object> deleteMenu(@PathVariable String menuOid, @RequestBody SrchMenuVo srchMenuVo, @RequestHeader("UserId") String userId) throws Exception {
		srchMenuVo.setMenuUptUsrId(userId);
		menuService.deleteMenu(srchMenuVo);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}
	
	/**
	 * <pre>사용자 권한 메뉴 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 23.
	 * @param userId
	 * @param srchMenuVo
	 * @param menuCreUsrId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/dataserviceUi/menu/authMenu")
	public ResponseEntity<Object> getAuthMenuList(@RequestHeader(value="UserId", required = false) String userId, SrchMenuVo srchMenuVo) throws Exception {
		srchMenuVo.setMenuCreUsrId(userId);
		List<MenuVo> list = menuService.getAuthMenuList(srchMenuVo);
		
		return ResponseUtil.makeResponseEntity(list);
	}
}
