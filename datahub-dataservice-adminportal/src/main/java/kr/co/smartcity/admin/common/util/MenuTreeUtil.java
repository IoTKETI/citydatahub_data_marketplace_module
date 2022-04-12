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
package kr.co.smartcity.admin.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.co.smartcity.admin.feature.menu.vo.MenuVo;

public class MenuTreeUtil {
	
	/**
	 * <pre>HTML 최종 반환</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 8. 3.
	 * @param menuList
	 * @param ctxPth
	 * @return
	 */
	public static String getLnbHtml(List<MenuVo> menuList, String ctxPath) {
		
		List<MenuVo> remakeList = remakeMenuList(menuList, 0).get(0).getChildrens();
		
		return makeLnbHtml(remakeList, ctxPath);
	}
	
	/**
	 * <pre>LNB HTML 생성</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 8. 3.
	 * @param menuList
	 * @param ctxPath
	 * @return
	 */
	protected static String makeLnbHtml(List<MenuVo> menuList, String ctxPath) {
		String html = "";
		
		for (MenuVo menu : menuList) {
			html += "<li class=\"nav__depth1 _" + menu.getMenuId() + "\">";
			
			List<MenuVo> childrens = menu.getChildrens();
			if (childrens != null && childrens.size() > 0) {
				html += "<button class=\"nav__link nav__button\" type=\"button\">" + menu.getMenuNm() + "</button>";
				html += recursionHtml(childrens, ctxPath);
			} else {
				html += "<a class=\"nav__link\" href=\"" + ctxPath + menu.getMenuUrl() + "?nav=" + menu.getMenuId() +"\">" + menu.getMenuNm() + "</a>";
			}
		}
		return html;
	}
	
	/**
	 * <pre>LNB HTML 생성 (하위 레벨 처리)</pre>
	 * @Author		: junyl
	 * @Date		: 2020. 7. 27.
	 * @param menuList
	 * @param ctxPath
	 * @return
	 */
	protected static String recursionHtml(List<MenuVo> menuList, String ctxPath) {
		String html = "<ul class=\"nav__depth2\">";
		for(MenuVo menu : menuList) {
			String url = ctxPath + menu.getMenuUrl();
			
			List<MenuVo> childrens = menu.getChildrens();
			if(childrens != null && childrens.size() > 0) {
				html += recursionHtml(childrens, ctxPath);
			} else {
				html += "<li class=\"nav__item _" + menu.getMenuId() + "\"><a class=\"nav__link\" href=\"" + url + "?nav=" + menu.getMenuId() + "\">" + menu.getMenuNm() + "</a></li>";
			}
		}
		html += "</ul>";
		
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
	
	
}
