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
package kr.co.smartcity.admin.feature.authgroup.vo;

import java.util.List;

import lombok.Data;

@Data
public class AuthgroupMenuMapVo implements Comparable<AuthgroupMenuMapVo>{
	private long authgroupOid;	
	private long menuOid;	
	private String readYn;	
	private String writeYn;
	private String modifiedYn;
	private String deleteYn;
	
	private String menuNm;
	private long menuParentId;
	private String menuUseFl;
	private String programgroupUseFl;
	private String menuOrd;
	private String menuGbCd;
	
	private List<AuthgroupMenuMapVo> childrens;

	@Override
	public int compareTo(AuthgroupMenuMapVo vo) {
		if (Integer.parseInt(this.menuOrd) < Integer.parseInt(vo.getMenuOrd())) {
            return -1;
        } else if (Integer.parseInt(this.menuOrd) > Integer.parseInt(vo.getMenuOrd())) {
            return 1;
        }
		// 정렬 (sort) 값이 같을 경우 메뉴명 오름차순(asc) 정렬
		return vo.getMenuNm().compareTo(this.menuNm);
	}
	
}

