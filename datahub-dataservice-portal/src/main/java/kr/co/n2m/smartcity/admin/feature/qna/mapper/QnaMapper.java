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
package kr.co.n2m.smartcity.admin.feature.qna.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.n2m.smartcity.admin.feature.qna.vo.QnaVo;
import kr.co.n2m.smartcity.admin.feature.qna.vo.SrchQnaVo;


@Mapper
public interface QnaMapper {
	/**
	 * <pre>QNA 목록 카운트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param qnaVo
	 * @return
	 */
	public int selectQnaCount(SrchQnaVo srchQnaVo);
	/**
	 * <pre>QNA 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param qnaVo
	 * @return
	 */
	public List<QnaVo> selectQnaList(SrchQnaVo srchQnaVo);
	/**
	 * <pre>QNA 메인 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param qnaVo
	 * @return
	 */
	public List<QnaVo> selectQnaListMain(SrchQnaVo srchQnaVo);
	/**
	 * <pre>QNA 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param qnaVo
	 * @return
	 */
	public int insertQna(SrchQnaVo srchQnaVo);
	/**
	 * <pre>QNA 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param qnaVo
	 * @return
	 */
	public QnaVo selectQna(long qnaOid);
	
	/**
	 * <pre>QNA 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param qnaVo
	 * @return
	 */
	public int updateQna(SrchQnaVo srchQnaVo);
	/**
	 * <pre>QNA 부분수정</pre>
	 * @Author      : areum
	 * @Date        : 2020. 7. 14.
	 * @param qnaVo
	 * @return
	 */
	public int updateQnaPart(SrchQnaVo srchQnaVo);
	/**
	 * <pre>QNA 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param qnaVo
	 * @return
	 */
	public int deleteQna(SrchQnaVo srchQnaVo);
	/**
	 * <pre>QNA 답변 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param qnaVo
	 * @return
	 */
//	public int createQnaAnswer(SrchQnaVo srchQnaVo);
	/**
	 * <pre>QNA 조회수 업데이트</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param qnaVo
	 * @return
	 */
	public int updateQnaCnt(long qnaOid);
}
