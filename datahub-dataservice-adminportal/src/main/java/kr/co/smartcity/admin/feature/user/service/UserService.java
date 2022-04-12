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
package kr.co.smartcity.admin.feature.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.common.vo.CommonVo;
import kr.co.smartcity.admin.feature.user.vo.UserVo;
	
@Service("userService")
public class UserService extends HttpComponent {
	
	/**
	 * <pre>사용자 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public UserVo getUser(Map<String, String> params) throws Exception {
		return this.getUserByType(params.get("userId"), UserType.ADMIN);
	}
	
	/**
	 * <pre>사용자 전체 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */

	public String getAllUserList(Map<String, String> params) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		int pageListSize = Integer.parseInt(params.get("pageListSize"));
		int curPage = Integer.parseInt(params.get("curPage"));
		int fromIndex,toIndex;
		List<UserVo> resultListresult =  new ArrayList<UserVo>();
		setSessionAttribute(CommonConst.SEARCH_PREFIX, "USER_LIST", params);
		
		List<UserVo> resultList = this.getUserListAll();
		
		List<UserVo> resultListSearch = userSearch(resultList, params);
		fromIndex = (curPage -1) * pageListSize;
		toIndex =  curPage * pageListSize;
		if(toIndex > resultListSearch.size()) {
			toIndex = resultListSearch.size() ;
		}
		//페이징 처리 마친 List Data
		resultListresult = resultListSearch.subList(fromIndex, toIndex);
		
		CommonVo commonVo = new CommonVo();
		commonVo.setPageListSize(pageListSize);
		commonVo.setTotalListSize(resultListSearch.size());
		commonVo.setSchCondition2(params.get("schCondition2"));
		commonVo.setSchCondition(params.get("schCondition"));
		commonVo.setSchValue(params.get("schCondition"));
		commonVo.setCurPage(curPage);
		resultMap.put("page", commonVo);
		resultMap.put("userList", resultListresult);
		
		return toJson(resultMap);
	}
	
	/**
	 * <pre>일반사용자목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getNormalUserList(Map<String, String> params) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		int pageListSize = Integer.parseInt(params.get("pageListSize"));
		int curPage = Integer.parseInt(params.get("curPage"));
		int fromIndex,toIndex;
		List<UserVo> resultListresult =  new ArrayList<UserVo>();
		try {
			setSessionAttribute(CommonConst.SEARCH_PREFIX, "USER_LIST", params);
			
			List<UserVo> resultList = this.getUserListByType(UserType.NORMAL);
			
			List<UserVo> resultListSearch = userSearch(resultList, params);
			fromIndex = (curPage -1) * pageListSize;
			toIndex =  curPage * pageListSize;
			if(toIndex > resultListSearch.size()) {
				toIndex = resultListSearch.size() ;
			}
			//페이징 처리 마친 List Data
			resultListresult = resultListSearch.subList(fromIndex, toIndex);
			
			CommonVo commonVo = new CommonVo();
			commonVo.setPageListSize(pageListSize);
			commonVo.setTotalListSize(resultListSearch.size());
			commonVo.setSchCondition2(params.get("schCondition2"));
			commonVo.setSchCondition(params.get("schCondition"));
			commonVo.setSchValue(params.get("schCondition"));
			commonVo.setCurPage(curPage);
			
			resultMap.put("page", commonVo);
			resultMap.put("userList", resultListresult);
		} catch (Exception e) {
			throw e;
		}
		
		return toJson(resultMap);
	}
	/**
	 * <pre>관리자 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getAdminUserList(Map<String, String> params) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		int pageListSize = Integer.parseInt(params.get("pageListSize"));
		int curPage = Integer.parseInt(params.get("curPage"));
		int fromIndex,toIndex;
		List<UserVo> resultListresult =  new ArrayList<UserVo>();
		List<UserVo> resultList = this.getUserListByType(UserType.ADMIN);
		
		List<UserVo> resultListSearch = userSearch(resultList, params);
		fromIndex = (curPage -1) * pageListSize;
		toIndex =  curPage * pageListSize;
		if(toIndex > resultListSearch.size()) {
			toIndex = resultListSearch.size() ;
		}
		//페이징 처리 마친 List Data
		resultListresult = resultListSearch.subList(fromIndex, toIndex);
		
		CommonVo commonVo = new CommonVo();
		commonVo.setPageListSize(pageListSize);
		commonVo.setTotalListSize(resultListSearch.size());
		commonVo.setSchCondition2(params.get("schCondition2"));
		commonVo.setSchCondition(params.get("schCondition"));
		commonVo.setSchValue(params.get("schCondition"));
		commonVo.setCurPage(curPage);
		
		resultMap.put("page", commonVo);
		resultMap.put("list", resultListresult);
		
		return toJson(resultMap);
	}
	
	/**
	 * <pre>로그인</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String login(Map<String, Object> params) throws Exception {
		return get( props.getPortalMsApiUrl() + "/login", params);
	}
	
	/**
	 * 사용자 검색조건
	 * @return 
	 */

	@SuppressWarnings("null")
	public List<UserVo> userSearch(List<UserVo> resultList,Map<String, String> params) {
		List<UserVo> resultListFilter =  new ArrayList<UserVo>();
		if(params.get("userid") != null) {
			String searchId = (String) params.get("userid");
			for(int i=0; i<resultList.size(); i++) {
				String resultId = (String) resultList.get(i).getUserId();
					if(resultId.indexOf(searchId) != -1) {
						resultListFilter.add(resultList.get(i));
					}
			}
		}else if(params.get("usernm") != null){
			String searchNm = (String) params.get("usernm");
			for(int i=0; i<resultList.size(); i++) {
				String resultNm = (String) resultList.get(i).getName();
					if(resultNm.indexOf(searchNm) != -1) {
						resultListFilter.add(resultList.get(i));
					}
			}
			
		}else {
			resultListFilter = resultList;
		}
		return resultListFilter;
	}
}
