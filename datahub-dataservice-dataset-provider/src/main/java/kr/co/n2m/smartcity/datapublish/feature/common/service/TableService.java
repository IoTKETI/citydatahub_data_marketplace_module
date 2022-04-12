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
package kr.co.n2m.smartcity.datapublish.feature.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.n2m.smartcity.datapublish.common.component.CommonComponent;
import kr.co.n2m.smartcity.datapublish.feature.common.mapper.CodeGroupMapper;
import kr.co.n2m.smartcity.datapublish.feature.common.mapper.CodeMapper;
import kr.co.n2m.smartcity.datapublish.feature.common.vo.CodeGroupVo;
import kr.co.n2m.smartcity.datapublish.feature.common.vo.CodeVo;

@Service
public class TableService extends CommonComponent{
	@Autowired CodeGroupMapper codeGroupMapper;
	@Autowired CodeMapper codeMapper;

	/**
	 * <pre>코드그룹 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param codeGroupVo
	 * @throws Exception
	 */
	public void createCodeGroup(CodeGroupVo codeGroupVo) throws Exception {
		codeGroupMapper.insertCodeGroup(codeGroupVo);
		
	}

	/**
	 * <pre>코드 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param codeVo
	 * @throws Exception
	 */
	public void createCode(CodeVo codeVo) throws Exception {
		codeMapper.insertCode(codeVo);
		
	}

	/**
	 * <pre>코드그룹 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param codeGroupVo
	 * @throws Exception
	 */
	public void modifyCodeGroup(CodeGroupVo codeGroupVo) throws Exception {
		codeGroupMapper.updateCodeGroup(codeGroupVo);
		
	}

	/**
	 * <pre>코드 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param codeVo
	 * @throws Exception
	 */
	public void modifyCode(CodeVo codeVo) throws Exception {
		codeMapper.updateCode(codeVo);
		
	}

	/**
	 * <pre>코드그룹 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param codeGroupVo
	 * @throws Exception
	 */
	public void deleteCodeGroup(CodeGroupVo codeGroupVo) throws Exception {
		codeGroupMapper.deleteCodeGroup(codeGroupVo);
		
	}

	/**
	 * <pre>코드 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param codeVo
	 * @throws Exception
	 */
	public void deleteCode(CodeVo codeVo) throws Exception {
		codeMapper.deleteCode(codeVo);
		
	}

}
