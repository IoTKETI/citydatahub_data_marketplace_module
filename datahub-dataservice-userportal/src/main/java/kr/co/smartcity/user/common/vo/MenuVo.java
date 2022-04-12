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
package kr.co.smartcity.user.common.vo;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MenuVo implements Comparable<MenuVo> {
	
	private long menuOid;				//메뉴OID
	private String menuId;				//메뉴ID
	private String menuNm;				//메뉴명
	private String menuUrl;				//메뉴URL
	private int menuOrd;				//메뉴순서
	private long level;					//트리 레벨
	private long menuParentId;			//부모메뉴
	private String menuParentNm;		//부모메뉴명
	private String menuGbCd;			//메뉴구분코드
	private String menuParam;			//메뉴파라미터
	private String menuUseFl;			//메뉴사용여부
	
	private List<MenuVo> childrens;		//하위 레벨 메뉴

	@Override
	public int compareTo(MenuVo o) {
		if (this.menuOrd < o.getMenuOrd()) {
            return -1;
        } else if (this.menuOrd > o.getMenuOrd()) {
            return 1;
        }
		// 정렬 (sort) 값이 같을 경우 메뉴명 오름차순(asc) 정렬
		return o.getMenuNm().compareTo(this.menuNm);
	}
}
