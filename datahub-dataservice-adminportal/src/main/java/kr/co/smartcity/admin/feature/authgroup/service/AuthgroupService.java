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
package kr.co.smartcity.admin.feature.authgroup.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.feature.authgroup.vo.AuthgroupMenuMapVo;
import kr.co.smartcity.admin.feature.user.service.UserService;

@Service("authgroupService")
public class AuthgroupService extends HttpComponent {
	
	@Autowired
	UserService userService;
	
	/**
	 * <pre>권한그룹목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getAuthgroupList(Map<String, Object> params) throws Exception {
		Map<String, Object> resMap = null;
		setSessionAttribute(CommonConst.SEARCH_PREFIX, "AUTHGROUP_LIST", params);
		resMap = toMap(get( props.getPortalMsApiUrl() + "/dataserviceUi/permissionGroup", params));
		Map<String, Object> noticeListPageInfoMap = (Map<String, Object>) resMap.get("page");
		noticeListPageInfoMap.putAll(params);
		return toJson(resMap);
	}
	
	/**
	 * <pre>권한그룹상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getAuthgroup(Map<String, Object> params) throws Exception {
		return get( props.getPortalMsApiUrl() + "/dataserviceUi/permissionGroup/"+ params.get("authgroupOid"));
	}
	
	/**
	 * <pre>권한그룹생성</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String createAuthgroup(Map<String, Object> params) throws Exception {
		return method(HttpMethod.POST, props.getPortalMsApiUrl() + "/dataserviceUi/permissionGroup", params);
	}
	/**
	 * <pre>권한그룹 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String deleteAuthgroup(Map<String, Object> params) throws Exception {
		return method(HttpMethod.DELETE, props.getPortalMsApiUrl() + "/dataserviceUi/permissionGroup/"+ params.get("authgroupOid"), params); 
	}
	/**
	 * <pre>권한그룹 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String modifyAuthgroup(Map<String, Object> params) throws Exception {
		return method(HttpMethod.PUT, props.getPortalMsApiUrl() + "/dataserviceUi/permissionGroup/"+ params.get("authgroupOid"), params); 
	}
	
	/**
	 * <pre>권한그룹사용자목록조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getAuthgroupUserList(Map<String, Object> params) throws Exception {
		String data = get( props.getPortalMsApiUrl() + "/dataserviceUi/permissionGroup/"+ params.get("authgroupOid")+"/contact");
		Map<String,Object> resMap = toMap(data);

		Map<String, String> keyPairMap = new HashMap<>();
		keyPairMap.put("userId", "userNm");
		putUserNameForMap(resMap, keyPairMap);
		
		return toJson(resMap);
	}
	/**
	 * <pre>권한그룹사용자 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String updateAuthgroupUser(Map<String, Object> params) throws Exception {
		return method(HttpMethod.POST, props.getPortalMsApiUrl() + "/dataserviceUi/permissionGroup/"+ params.get("authgroupOid")+"/contact", params); 
	}
	
	/**
	 * <pre>메뉴권한목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getAuthgroupMenuList(Map<String, Object> params) throws Exception {
		String data = get( props.getPortalMsApiUrl() + "/dataserviceUi/permissionGroup/"+ params.get("authgroupOid")+"/menu", params);
		Type listType = new TypeToken<ArrayList<AuthgroupMenuMapVo>>(){}.getType();
		List<AuthgroupMenuMapVo> list = gson().fromJson(data, listType);
		return toJson(getMenuList(list));
	}
	/**
	 * 
	 * <pre>메뉴권한목록 조회 - 트리메뉴</pre>
	 * @Author        : areum
	 * @Date        : 2020. 8. 5.
	 * @param menuList
	 * @return
	 */
	protected static List<AuthgroupMenuMapVo> getMenuList(List<AuthgroupMenuMapVo> menuList) {
		
		List<AuthgroupMenuMapVo> list = new ArrayList<AuthgroupMenuMapVo>();
		
		while(menuList.size() > 0) {
			for(int index = menuList.size()-1; index >= 0; index--) {
				AuthgroupMenuMapVo menu = menuList.get(index);
				if(menu.getMenuParentId() == 0) {
					list.add(menu);
					menuList.remove(index);
					continue;
				}
				getMenuData(menuList, list, menu, index);
			}
		}
		Collections.sort(list);
		return list;
	}
	
	protected static void getMenuData(List<AuthgroupMenuMapVo> menuList,List<AuthgroupMenuMapVo> list, AuthgroupMenuMapVo child, int index ) {
		
		if(list == null || list.size() <= 0) return;
		
		for(AuthgroupMenuMapVo parent : list) {
			if(parent.getMenuOid() == child.getMenuParentId()) {
				List<AuthgroupMenuMapVo> childrens = parent.getChildrens();
				if(childrens == null) childrens = new ArrayList<AuthgroupMenuMapVo>();
				childrens.add(child);
				menuList.remove(index);
				Collections.sort(childrens);
				parent.setChildrens(childrens);
				break;
			}
			getMenuData(menuList, parent.getChildrens(), child, index);
		}
		
	}
	
	/**
	 * <pre>메뉴권한 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String updateAuthgroupMenu(Map<String, Object> params) throws Exception {
		return method(HttpMethod.POST, props.getPortalMsApiUrl() + "/dataserviceUi/permissionGroup/"+ params.get("authgroupOid")+"/menu", params);
	}

	
	
	
	
	
	
	
	
	
}
