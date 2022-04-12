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
package kr.co.smartcity.user.feature.main.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.smartcity.user.common.component.HttpComponent;
import kr.co.smartcity.user.common.util.MenuTreeUtil;
import kr.co.smartcity.user.common.vo.MenuVo;

@Service("mainService")
public class MainService extends HttpComponent {
	@Autowired Gson gson;

	/**
	 * <pre>메뉴 권한 리스트</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 30.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<MenuVo> getAuthMenuList(Map<String, String> params) throws Exception {
		String menuTreeJson = get(props.getPortalMsApiUrl()  + "/dataserviceUi/menu/authMenu", params);
		Type listType = new TypeToken<ArrayList<MenuVo>>(){}.getType();
		List<MenuVo> menuList = gson().fromJson(menuTreeJson, listType);
		
		// 최상위 사용자포탈 메뉴는 제외.
		return MenuTreeUtil.remakeMenuList(menuList, 0).get(0).getChildrens();
		
	}

	/**
	 * 
	 * 카테고리 소분류 / oid , 카테고리별 데이터 셋 카운트
	 * @Author      : kyunga
	 * @Date        : 2019. 10. 22.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getCategory(Map<String, String> params) throws Exception {
		return get(props.getDatapublishMsApiUrl() + "/dataservice/datasetCategories",params);
	}
	/**
	   * 
	   * Main 페이지  Data get
	   * @Author      : kyunga
	   * @Date        : 2019. 11. 4.
	   * @param params
	   * @return
	   * @throws Exception 
	*/
	@SuppressWarnings("unchecked")
	public String getMainData() throws Exception {
	    Map<String, Object> resMap = new HashMap<>();
	    Map<String, Object> resMapTemp = null;
	    
	    Map<String, String> params = new HashMap<>();
	    try {
	    	//유형별 데이터셋 카운트
	    	String datasetCountJson = get(props.getDatapublishMsApiUrl() + "/integrated/dataset/count",params);
	    	resMap.putAll(toMap(datasetCountJson));
	    	
	    	params.put("main", "Y");
	    	String noticeJson = get(props.getPortalMsApiUrl() + "/dataserviceUi/notices",params);
	    	resMapTemp = toMap(noticeJson);
	    	List<Map<String, Object>> opernoticeList = (List<Map<String, Object>>) resMapTemp.get("list");
	    	resMap.put("listOpernotice", opernoticeList);

	    	String faqJson = get(props.getPortalMsApiUrl() + "/dataserviceUi/faq",params);
	    	resMapTemp = toMap(faqJson);
	    	List<Map<String, Object>> faqList = (List<Map<String, Object>>) resMapTemp.get("list");
	    	resMap.put("listFaq", faqList);

	    	String qnaJson = get(props.getPortalMsApiUrl() + "/dataserviceUi/qna",params);
	    	resMapTemp = toMap(qnaJson);
	    	List<Map<String, Object>> qnaList = (List<Map<String, Object>>) resMapTemp.get("list");
	 		resMap.put("listQna", qnaList);
	 		
	 		String categoryJson = get(props.getDatapublishMsApiUrl() + "/dataservice/datasetCategories",params);
	 		resMapTemp = toMap(categoryJson);
	 		List<Map<String, Object>> categoryList = (List<Map<String, Object>>) resMapTemp.get("list");
	 		List<Map<String, Object>> newCategoryList = new ArrayList<>();
	 		for(Map<String, Object> categoryMap : categoryList) {
	 			if((long) categoryMap.get("categoryParentOid") != 0) continue;
	 			long categoryOid    = (long) categoryMap.get("categoryOid"); 
	 			String categoryNm   = (String) categoryMap.get("categoryNm");  
	 			String categoryDesc = (String) categoryMap.get("categoryDesc");
	 			Map<String, Object> newCategoryMap = new HashMap<>();
	 			newCategoryMap.put("categoryOid"  , categoryOid);
	 			newCategoryMap.put("categoryNm"   , categoryNm);
	 			newCategoryMap.put("categoryDesc" , categoryDesc);
	 			

	 			Map<String, String> iParams = new HashMap<>();
	 			iParams.put("categoryOidList", String.valueOf((long)categoryOid));
	 			String datasetListMapByCategoryJson = get(props.getDatapublishMsApiUrl() + "/dataservice/dataset", iParams);
	 			resMapTemp = toMap(datasetListMapByCategoryJson);
	 			Map<String, Object> pageMap = (Map<String, Object>) resMapTemp.get("page");
	 			long datasetCount = (long) pageMap.get("totalListSize");
	 			newCategoryMap.put("datasetCount", datasetCount);
	 			newCategoryList.add(newCategoryMap);
	 		}
	 		resMap.put("listCategory", newCategoryList);
	    }catch(Exception e) {
			throw e;
	    }
	    return toJson(resMap);
	  }
}
