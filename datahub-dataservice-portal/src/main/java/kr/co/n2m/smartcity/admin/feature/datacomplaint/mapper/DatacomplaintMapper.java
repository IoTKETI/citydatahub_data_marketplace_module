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
package kr.co.n2m.smartcity.admin.feature.datacomplaint.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import kr.co.n2m.smartcity.admin.feature.datacomplaint.vo.DatacomplaintVo;
import kr.co.n2m.smartcity.admin.feature.datacomplaint.vo.SrchDatacomplaintVo;

@Mapper
public interface DatacomplaintMapper {
	/**
	 * <pre>고유번호로 데이터 민원 목록 카운트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param datacomplaintVo
	 * @return
	 */
	public int selectDatacomplaintCountById(SrchDatacomplaintVo srchDatacomplaintVo);
	/**
	 * <pre>데이터 민원 목록 카운트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param datacomplaintVo
	 * @return
	 */
	public int selectDatacomplaintCount(SrchDatacomplaintVo srchDatacomplaintVo);
	/**
	 * <pre>데이터 민원 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param datacomplaintVo
	 * @return
	 */
	public List<DatacomplaintVo> selectDatacomplaintList(SrchDatacomplaintVo srchDatacomplaintVo);
	/**
	 * <pre>데이터 민원 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param datacomplaintVo
	 * @return
	 */
	public int insertDatacomplaint(SrchDatacomplaintVo srchDatacomplaintVo);
	/**
	 * <pre>데이터 민원 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param datacomplaintVo
	 * @return
	 */
	public DatacomplaintVo selectDatacomplaint(long datacomplainOid);
	/**
	 * <pre>데이터 민원 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param datacomplaintVo
	 * @return
	 */
	public int deleteDatacomplaint(SrchDatacomplaintVo srchDatacomplaintVo);
	/**
	 * <pre>데이터 민원 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param datacomplaintVo
	 * @return
	 */
	public int updateDatacomplaintCnt(long datacomplainOid);
	
	/**
	 * <pre>데이터 민원 질문 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param datacomplaintVo
	 * @return
	 */
	public int updateDatacomplaint(SrchDatacomplaintVo srchDatacomplaintVo);
	/**
	 * <pre>데이터 민원 답변 등록 및 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param datacomplaintVo
	 * @return
	 */
	public int updateDatacomplaintPart(SrchDatacomplaintVo srchDatacomplaintVo);
	
}
