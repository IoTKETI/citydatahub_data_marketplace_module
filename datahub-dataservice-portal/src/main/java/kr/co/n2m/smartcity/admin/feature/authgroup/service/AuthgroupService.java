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
package kr.co.n2m.smartcity.admin.feature.authgroup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.admin.common.component.CommonComponent;
import kr.co.n2m.smartcity.admin.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.admin.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;
import kr.co.n2m.smartcity.admin.common.vo.PageVo;
import kr.co.n2m.smartcity.admin.feature.authgroup.mapper.AuthgroupMapper;
import kr.co.n2m.smartcity.admin.feature.authgroup.vo.AuthgroupMenuMapVo;
import kr.co.n2m.smartcity.admin.feature.authgroup.vo.AuthgroupUserMapVo;
import kr.co.n2m.smartcity.admin.feature.authgroup.vo.AuthgroupVo;
import kr.co.n2m.smartcity.admin.feature.authgroup.vo.SrchAuthgroupMenuMapVo;
import kr.co.n2m.smartcity.admin.feature.authgroup.vo.SrchAuthgroupUserMapVo;
import kr.co.n2m.smartcity.admin.feature.authgroup.vo.SrchAuthgroupVo;

@Service
public class AuthgroupService extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthgroupMapper authgroupMapper;
	/**
	 * 
	 * 권한그룹 목록 조회
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 23.
	 * @param srchAuthgroupVo
	 * @return
	 */
	@Transactional
	public Map<String, Object> getAuthgroupList(SrchAuthgroupVo srchAuthgroupVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			int totalListSize = authgroupMapper.selectAuthgroupCount(srchAuthgroupVo);
			srchAuthgroupVo.setTotalListSize(totalListSize);
			List<AuthgroupVo> list = authgroupMapper.selectAuthgroupList(srchAuthgroupVo);
			if(srchAuthgroupVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchAuthgroupVo);
				resMap.put("page", pageVo);
			}else{
				resMap.put("page", null);
			}
			resMap.put("list", list);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resMap;	
	}
	/**
	 * 
	 * 권한그룹 목록 카운트 조회
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 23.
	 * @param srchAuthgroupVo
	 * @return
	 */
	@Transactional
	public int getAuthgroupCount(SrchAuthgroupVo srchAuthgroupVo) {
		int totalListSize;
		try {
			totalListSize = authgroupMapper.selectAuthgroupCount(srchAuthgroupVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return totalListSize;
	}
	
	/**
	 * 
	 * 등록
	 * @Author      : kyunga
	 * @Date        : 2019. 8. 5.
	 * @param srchAuthgroupVo
	 * @return
	 */
	@Transactional
	public String createAuthgroup(SrchAuthgroupVo srchAuthgroupVo) {
		long id;
		try {
			srchAuthgroupVo.setAuthgroupOid(StringUtil.generateKey());
			id = srchAuthgroupVo.getAuthgroupOid();
			authgroupMapper.insertAuthgroup(srchAuthgroupVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return String.valueOf(id); 
	}
	
	/**
	 * 
	 * 상세정보
	 * @Author      : kyunga
	 * @Date        : 2019. 8. 5.
	 * @param srchAuthgroupVo
	 * @return
	 */
	@Transactional
	public AuthgroupVo getAuthgroup(long authgroupOid) {
		AuthgroupVo authgroup = null;
		try {
			authgroup = authgroupMapper.selectAuthgroup(authgroupOid);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return authgroup;
	}
	/**
	 * 
	 * 수정
	 * @Author      : kyunga
	 * @Date        : 2019. 8. 5.
	 * @param srchAuthgroupVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyAuthgroup(SrchAuthgroupVo srchAuthgroupVo) throws Exception {
		try {
			int count = authgroupMapper.updateAuthgroup(srchAuthgroupVo);
			if(count<=0) throw new NotFoundException();
		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * 삭제
	 * @Author      : kyunga
	 * @Date        : 2019. 8. 5.
	 * @param srchAuthgroupVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void deleteAuthgroup(SrchAuthgroupVo srchAuthgroupVo) throws Exception {
		try {
			int count = authgroupMapper.deleteAuthgroup(srchAuthgroupVo);
			if(count<=0) throw new NotFoundException();

			count = authgroupMapper.deleteAuthgroupUser(srchAuthgroupVo.getAuthgroupOid());
			if(count<=0) throw new NotFoundException();

			count = authgroupMapper.deleteAuthgroupMenu(srchAuthgroupVo.getAuthgroupOid());
			if(count<=0) throw new NotFoundException();
		}catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * 권한관리 권한관리 등록
	 * @Author      : kyunga
	 * @Date        : 2019. 8. 5.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public String createAuthgroupUser(Map<String, Object> params) throws Exception {
		long oid;
		
		try {
			List<Map<String, String>> userIDList = (List<Map<String, String>>) params.get("userList");
			oid = (long) params.get("authgroupOid");
			int count = authgroupMapper.deleteAuthgroupUser(oid);
			if(count<=0) throw new NotFoundException();
			for(int i=0;i<userIDList.size();i++) {
				count = authgroupMapper.insertAuthgroupUser(oid, userIDList.get(i).get("userId"));
				if(count<=0) throw new GlobalException();
			}
		}catch(Exception e) {
			throw e;
		}
		return String.valueOf(oid); 
	}
	
	/**
	 * 
	 * 권한관리 리스트
	 * @Author      : kyunga
	 * @Date        : 2019. 8. 5.
	 * @param authgroupUserMapVo
	 * @return
	 */
	@Transactional
	public Map<String,Object> getAuthgroupUserList(SrchAuthgroupUserMapVo srchAuthgroupUserMapVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			List<AuthgroupUserMapVo> list = authgroupMapper.selectAuthgroupUserList(srchAuthgroupUserMapVo);
			resMap.put("list", list);
			resMap.put("page", null);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resMap;	
	}
	
	/**
	 * 
	 * 메뉴권한 리스트
	 * @Author      : kyunga
	 * @Date        : 2019. 8. 5.
	 * @param authgroupMenuMapVo
	 * @param menuVo
	 * @return
	 */
	@Transactional
	public List<AuthgroupMenuMapVo> getAuthgroupMenuList(SrchAuthgroupMenuMapVo srchAuthgroupMenuMapVo) {
		try {
			return authgroupMapper.selectAuthgroupMenuList(srchAuthgroupMenuMapVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * 
	 * 메뉴권한 업데이트
	 * @Author      : kyunga
	 * @Date        : 2019. 8. 5.
	 * @param params
	 * @param authgroupMenuMapVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void updateAuthgroupMenu(Map<String, Object> params, SrchAuthgroupMenuMapVo srchAuthgroupMenuMapVo) throws Exception {
		try {
			List<Map<String, Object>> menuOidList = (List<Map<String, Object>>) params.get("menuList");
			Long oid = (Long) params.get("authgroupOid");
			int count = authgroupMapper.deleteAuthgroupMenu(oid);
			if(count<=0) throw new NotFoundException();
			
			
			for(int i=0;i<menuOidList.size();i++) {
				Map<String, Object> menu1dept = menuOidList.get(i);
				
				if(menu1dept.containsKey("childrens")) {
					List<Map<String, Object>> childrens = (List<Map<String, Object>>) menu1dept.get("childrens");
					menuOidList.addAll(childrens);
				}
				
				long menuoid =(long) menu1dept.get("menuOid");
				if(menu1dept.get("readYn").equals("Y")) {
					srchAuthgroupMenuMapVo.setReadYn("Y");
				}else {
					srchAuthgroupMenuMapVo.setReadYn("N");
				}
				
				if(menu1dept.get("writeYn").equals("Y")) {
					srchAuthgroupMenuMapVo.setWriteYn("Y");
				}else {
					srchAuthgroupMenuMapVo.setWriteYn("N");
				}
				
				if(menu1dept.get("modifiedYn").equals("Y")) {
					srchAuthgroupMenuMapVo.setModifiedYn("Y");
				}else {
					srchAuthgroupMenuMapVo.setModifiedYn("N");
				}
				
				if(menu1dept.get("deleteYn").equals("Y")) {
					srchAuthgroupMenuMapVo.setDeleteYn("Y");
				}else {
					srchAuthgroupMenuMapVo.setDeleteYn("N");
				}
				
				srchAuthgroupMenuMapVo.setMenuOid(menuoid);
				srchAuthgroupMenuMapVo.setAuthgroupOid(oid);
				count= authgroupMapper.insertAuthgroupMenu(srchAuthgroupMenuMapVo);
				if(count<=0) throw new GlobalException();
			}
		}catch(Exception e) {
			throw e;
		}
	}

	
}
