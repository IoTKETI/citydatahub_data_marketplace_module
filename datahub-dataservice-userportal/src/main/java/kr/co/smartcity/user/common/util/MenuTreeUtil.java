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
package kr.co.smartcity.user.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.smartcity.user.common.vo.MenuVo;

public class MenuTreeUtil {
	
	/**
	 * 
	 * <pre>HTML 최종 반환</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 8. 3.
	 * @param menuList
	 * @param ctxPth
	 * @return
	 */
	public static String getGnbHtml(List<MenuVo> menuList, String contextPath) {
		return makeGnbHtml(menuList, null, contextPath);
	}
	
	public static String getGnbHtml(List<MenuVo> menuList, List<String> excludeIds, String contextPath) {
		return makeGnbHtml(menuList, excludeIds, contextPath);
	}
	
	/**
	 * <pre>LNB 메뉴 HTML 정보를 모두 생성, 대 메뉴ID를 키로 저장 한 뒤 맵 정보를 반환한다.</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 8. 3.
	 * @param menuList
	 * @param contextPath
	 * @return
	 */
	public static Map<String, Object> getLnbHtml(List<MenuVo> menuList, String contextPath) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		for ( MenuVo menu : menuList ) {
			result.put(String.valueOf(menu.getMenuOid()), makeLnbHtml(menu, contextPath));
		}
		return result;
	}
	
	
	/**
	 * <pre>GNB HTML 생성</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 8. 3.
	 * @param menuList
	 * @param excludeIds	: GNB 제외 대상 메뉴ID 리스트
	 * @param ctxPath
	 * @return
	 */
	protected static String makeGnbHtml(List<MenuVo> menuList, List<String> excludeIds, String ctxPath) {
		String html = "<nav class=\"nav\"><ul class=\"nav-depth1__list clearfix\">";
		
		for (MenuVo menu : menuList) {

			if (excludeIds != null && excludeIds.contains(menu.getMenuId())) continue;
			
			html += "<li id=\"" + menu.getMenuId() + "\" class=\"nav-depth1__item\"><a href=\"" + menu.getMenuUrl() + "\" class=\"nav-depth1__link\">" + menu.getMenuNm() + "</a>";
			
			List<MenuVo> childrens = menu.getChildrens();
			if (childrens != null && childrens.size() > 0) {
				html += recursionGnbHtml(childrens, ctxPath);
			} else {
				html += "<ul class=\"nav-depth2__list\"></ul>";
			}
		}
		html += "<div class=\"nav__bg\"></div></nav>";
		return html;
	}
	
	/**
	 * <pre>GNB HTML 생성 (하위 레벨 처리)</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 27.
	 * @param menuList
	 * @param ctxPath
	 * @return
	 */
	protected static String recursionGnbHtml(List<MenuVo> menuList, String ctxPath) {
		String html = "<ul class=\"nav-depth2__list\">";
		for(MenuVo menu : menuList) {
			String url = ctxPath + menu.getMenuUrl();
			
			List<MenuVo> childrens = menu.getChildrens();
			if(childrens != null && childrens.size() > 0) {
				html += recursionGnbHtml(childrens, ctxPath);
			} else {
				html += "<li class=\"nav-depth2__item\"><a href=\"" + url + "\" class=\"nav-depth2__link\">"+ menu.getMenuNm() +"</a></li>";
			}
		}
		html += "</ul>";
		
		return html;
	}
	
	/**
	 * 
	 * <pre>LNB HTML 생성</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 31.
	 * @param menuList
	 * @param ctxPath
	 * @return
	 */
	protected static String makeLnbHtml(MenuVo rootMenu, String ctxPath) {
		List<MenuVo> menuList = rootMenu.getChildrens();
		if(menuList == null || menuList.size() <= 0) return "";
		
		String html = "<nav class=\"sub-nav\"><ul class=\"sub-nav-depth1__list\">";
		
		for (MenuVo menu : menuList) {
			
			if(StringUtil.isEmpty(menu.getMenuUrl())) {
				html += "<li id=\"" + menu.getMenuId() + "\" class=\"sub-nav-depth1__item\"><a class=\"sub-nav-depth1__link\">" + menu.getMenuNm() + "</a>";
			} else {
				html += "<li id=\"" + menu.getMenuId() + "\" class=\"sub-nav-depth1__item\"><a href=\"" + ctxPath + menu.getMenuUrl() + "\" class=\"sub-nav-depth1__link\">" + menu.getMenuNm() + "</a>";
			}

			List<MenuVo> childrens = menu.getChildrens();
			if (childrens != null && childrens.size() > 0) {
				html += "<ul class=\"sub-nav-depth2__list\">";	
				
				for (MenuVo child : childrens) {
					html += "<li id=\"" + child.getMenuId() + "\" class=\"sub-nav-depth2__item\"><a href=\"" + ctxPath + child.getMenuUrl() + "\" class=\"sub-nav-depth2__link\">"+ child.getMenuNm() +"</a></li>";
				}
				html += "</ul>";
			}
		}
		html += "</ul></nav>";
		return html;
	}
	
	
	/**
	 * <pre>메뉴 리스트 재 구성(상/하위 노드)</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 27.
	 * @param menuList	: 원본 메뉴 리스트
	 * @param rootId	: 최상위 노드 ID
	 * @return
	 */
	public static List<MenuVo> remakeMenuList(List<MenuVo> menuList, long rootId) {
		
		List<MenuVo> nodes = new ArrayList<MenuVo>();
		while(menuList.size() > 0) {
			for (int index = menuList.size()-1; index >= 0; index--) {
				
				MenuVo menu = menuList.get(index);
				if(menu.getMenuParentId() == rootId) {
					nodes.add(menu);	menuList.remove(index);
					continue;
				}
				recursionData(menuList, nodes, menu, index);
			}
		}
		Collections.sort(nodes);
		
		return nodes;
	}
	
	/**
	 * <pre>메뉴 리스트 재 구성(상/하위 노드)</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 27.
	 * @param menuList
	 * @param nodes
	 * @param child
	 * @param index
	 */
	protected static void recursionData(List<MenuVo> menuList, List<MenuVo> nodes, MenuVo child, int index) {
		if(menuList.size() == 0) return;
		if(nodes == null || nodes.size() <= 0) return;
		
		for (MenuVo parent : nodes) {
			if(parent.getMenuOid() == child.getMenuParentId()) {
				List<MenuVo> childrens = parent.getChildrens();
				if (childrens == null) childrens = new ArrayList<MenuVo>();
				
				childrens.add(child);	menuList.remove(index);
				
				Collections.sort(childrens);
				parent.setChildrens(childrens);
				
				break;
			}
			recursionData(menuList, parent.getChildrens(), child, index);
		}
	}
	
	/**
	 * <pre>순차적으로 이동 한 메뉴 흐름 정보를 배열에 담아 반환한다. index 순서로는 [대메뉴 -> 중메뉴 -> 소메뉴] 이다.</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 8. 3.
	 * @param menuList
	 * @param url
	 * @return
	 */
	public static List<MenuVo> findNavigationByUrl(List<MenuVo> menuList, String url) {
		List<MenuVo> navigation = new ArrayList<MenuVo>();
		
		for (MenuVo menu : menuList) {
			if(StringUtil.equals(menu.getMenuUrl(), url)) {
				navigation.add(menu);	break;
			}
		
			List<MenuVo> childrens = menu.getChildrens();
			if (childrens == null || childrens.size() <= 0) continue;
				
			if (recallNavigation(navigation, childrens, url)) {
				navigation.add(menu);	break;
			}
		}
		Collections.reverse(navigation);
		return navigation;
	}
	
	/**
	 * <pre>findNavigationByUrl 함수의 하위 레벨 처리</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 8. 3.
	 * @param navigation
	 * @param menuList
	 * @param url
	 * @return
	 */
	protected static boolean recallNavigation(List<MenuVo> navigation, List<MenuVo> menuList, String url) {
		for (MenuVo menu : menuList) {
			if(StringUtil.equals(menu.getMenuUrl(), url)) {
				navigation.add(menu);	return true;
			}
		
			List<MenuVo> childrens = menu.getChildrens();
			if (childrens != null && childrens.size() > 0) {
				
				if (recallNavigation(navigation, childrens, url)) {
					navigation.add(menu);	return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * <pre>마이페이지의 호출 URL 정보 조회</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 8. 3.
	 * @param menuList
	 * @param menuId
	 * @return
	 */
	public static String findMypageFirstUrl(List<MenuVo> menuList, String menuId) {
		MenuVo mypage = null;
		for (MenuVo menu : menuList) {
			if(StringUtil.equals(menu.getMenuId(), menuId)) {
				mypage = menu;	break;
			}
		}
		return getMypageFirstUrl(mypage.getChildrens());
	}

	protected static String getMypageFirstUrl(List<MenuVo> menuList) {
		if (menuList == null || menuList.size() <= 0) return "";
		
		MenuVo menu = menuList.get(0);
		if (StringUtil.isEmpty(menu.getMenuUrl())) {
			return getMypageFirstUrl(menu.getChildrens());
		} else {
			return menu.getMenuUrl();
		}
	}
}
