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
package kr.co.n2m.smartcity.admin.feature.menu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.admin.common.component.CommonComponent;
import kr.co.n2m.smartcity.admin.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.admin.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;
import kr.co.n2m.smartcity.admin.feature.menu.mapper.MenuMapper;
import kr.co.n2m.smartcity.admin.feature.menu.vo.MenuVo;
import kr.co.n2m.smartcity.admin.feature.menu.vo.SrchMenuVo;

@Service
public class MenuService extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private MenuMapper menuMapper;
	
	/**
	 * 메뉴 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 8. 20.
	 * @param srchMenuVo
	 * @return
	 */
	public Map<String, Object> getMenuList(SrchMenuVo srchMenuVo, HashMap<String, String> params) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		try {
			List<MenuVo> menuList = menuMapper.selectMenuList(srchMenuVo);
			
			resMap.put("page", null);
			resMap.put("list", menuList);
			
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return resMap;
	}
	
	/**
	 * 메뉴 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 8. 20.
	 * @param srchMenuVo
	 * @return
	 */
	@Transactional
	public String createMenu(SrchMenuVo srchMenuVo) {
		long id;
		try {
			srchMenuVo.setMenuOid(StringUtil.generateKey());
			id = srchMenuVo.getMenuOid();
			int count = menuMapper.insertMenu(srchMenuVo);
			if(count<=0) throw new GlobalException();
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return String.valueOf(id); 
	}
	
	/**
	 * 메뉴 상세 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 8. 20.
	 * @param srchMenuVo
	 * @return
	 */
	public MenuVo getMenu(long menuOid) {
		MenuVo menuVo = null;
		try {
			menuVo = menuMapper.selectMenu(menuOid);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return menuVo;
	}
	
	/**
	 * 메뉴 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 8. 20.
	 * @param srchMenuVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyMenu(SrchMenuVo srchMenuVo) throws Exception {
		try {
			int count = menuMapper.updateMenu(srchMenuVo);
			if(count<=0) throw new NotFoundException();
		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * 메뉴 삭제
	 * @Author      : hk-lee
	 * @Date        : 2019. 8. 20.
	 * @param srchMenuVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void deleteMenu(SrchMenuVo srchMenuVo) throws Exception {
		try {
			int count = menuMapper.deleteMenu(srchMenuVo);
			if(count<=0) throw new NotFoundException();
			
			count = menuMapper.deleteAuthgroupMenu(srchMenuVo);
			if(count<=0) throw new NotFoundException();
		}catch(Exception e) {
			throw e;
		}
	}


	/**
	 * 
	 * <pre>사용자 권한 메뉴 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 23.
	 * @param srchMenuVo
	 * @return
	 */
	public List<MenuVo> getAuthMenuList(SrchMenuVo srchMenuVo) {
		List<MenuVo> authMenuList = new ArrayList<MenuVo>();
		try {
			authMenuList = menuMapper.selectAuthMenuList(srchMenuVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return authMenuList;
	}
	
}
