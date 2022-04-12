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
package kr.co.n2m.smartcity.admin.feature.codegroup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.n2m.smartcity.admin.feature.codegroup.vo.CodeGroupVo;
import kr.co.n2m.smartcity.admin.feature.codegroup.vo.SrchCodeGroupVo;

@Mapper
public interface CodeGroupMapper {

	/**
	 * 코드그룹 등록 매퍼
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param srchCodeGroupVo
	 * @return
	 */
	int insertCodeGroup(SrchCodeGroupVo srchCodeGroupVo);
	
	/**
	 * 코드그룹 수정 매퍼
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param srchCodeGroupVo
	 * @return
	 */
	int updateCodeGroup(SrchCodeGroupVo srchCodeGroupVo);
	
	/**
	 * 코드그룹 건수 조회 매퍼
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param srchCodeGroupVo
	 * @return
	 */
	int selectCodeGroupCount(SrchCodeGroupVo srchCodeGroupVo);

	/**
	 * 코드그룹 목록 조회 매퍼
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param srchCodeGroupVo
	 * @return
	 */
	List<CodeGroupVo> selectCodeGroupList(SrchCodeGroupVo srchCodeGroupVo);

	/**
	 * 코드그룹 단일 조회 매퍼
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param srchCodeGroupVo
	 * @return
	 */
	CodeGroupVo selectCodeGroup(String codeGroupId);

	/**
	 * 코드그룹 전체 목록 조회 매퍼
	 * @Author      : hk-lee
	 * @Date        : 2019. 8. 14.
	 * @param srchCodeGroupVo
	 * @return
	 */
	List<CodeGroupVo> selectAllCode(SrchCodeGroupVo srchCodeGroupVo);

	/**
	 * 코드그룹ID 중복 조회 매퍼
	 * @Author      : hk-lee
	 * @Date        : 2019. 8. 14.
	 * @param srchCodeGroupVo
	 * @return
	 */
	int selectCodeGroupCountById(SrchCodeGroupVo srchCodeGroupVo);

}
