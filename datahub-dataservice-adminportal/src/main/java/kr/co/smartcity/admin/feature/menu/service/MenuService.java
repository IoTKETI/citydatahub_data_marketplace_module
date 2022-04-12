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
package kr.co.smartcity.admin.feature.menu.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;

import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.feature.menu.vo.MenuVo;
	
@Service("menuService")
public class MenuService extends HttpComponent {
	
	/**
	 * <pre>메뉴 생성</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String createMenu(Map<String, Object> params) throws Exception {
		return method(HttpMethod.POST, props.getPortalMsApiUrl() +  "/dataserviceUi/menu", params);
	}
	/**
	 * <pre>메뉴 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getMenuList(Map<String, Object> params) throws Exception {
		return get( props.getPortalMsApiUrl() + "/dataserviceUi/menu", params);
	}
	
	/**
	 * <pre>메뉴 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getMenu(Map<String, Object> params) throws Exception {
		return get( props.getPortalMsApiUrl() + "/dataserviceUi/menu/"+params.get("menuOid"));
	}
	/**
	 * <pre>메뉴 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String modifyMenu(Map<String, Object> params) throws Exception {
		return method(HttpMethod.PUT, props.getPortalMsApiUrl() +  "/dataserviceUi/menu/"+params.get("menuOid"), params);
	}
	/**
	 * <pre>메뉴 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String removeMenu(Map<String, Object> params) throws Exception {
		return method(HttpMethod.DELETE, props.getPortalMsApiUrl() +  "/dataserviceUi/menu/"+params.get("menuOid"), params);
	}

	/**
	 * 
	 * <pre>사용자 권한 메뉴 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 23.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<MenuVo> getAuthMenuList(Map<String, Object> params) throws Exception {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("UserId", params.get("userId"));
		
		String menuTreeJson = get( props.getPortalMsApiUrl() + "/dataserviceUi/menu/authMenu", headers, params);
		Type listType = new TypeToken<ArrayList<MenuVo>>(){}.getType();
		
		List<MenuVo> menu = gson().fromJson(menuTreeJson, listType); 
		Collections.sort(menu);
		return menu; 
	}
}
