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
package kr.co.n2m.smartcity.admin.feature.faq.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import kr.co.n2m.smartcity.admin.feature.faq.vo.FaqVo;
import kr.co.n2m.smartcity.admin.feature.faq.vo.SrchFaqVo;

@Mapper
public interface FaqMapper {
	/**
	 * <pre>고유번호로 FAQ 목록 카운트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchFaqVo
	 * @return
	 */
	public int selectFaqCountById(SrchFaqVo srchFaqVo);
	/**
	 * <pre>FAQ 목록 카운트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchFaqVo
	 * @return
	 */
	public int selectFaqCount(SrchFaqVo srchFaqVo);
	/**
	 * <pre>FAQ 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchFaqVo
	 * @return
	 */
	public List<FaqVo> selectFaqList(SrchFaqVo srchFaqVo);
	/**
	 * <pre>FAQ 메인 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchFaqVo
	 * @return
	 */
	public List<FaqVo> selectFaqListMain(SrchFaqVo srchFaqVo);
	/**
	 * <pre>FAQ 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchFaqVo
	 * @return
	 */
	public int insertFaq(SrchFaqVo srchFaqVo);
	/**
	 * <pre>FAQ 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchFaqVo
	 * @return
	 */
	public FaqVo selectFaq(long faqOid);
	/**
	 * <pre>FAQ 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchFaqVo
	 * @return
	 */
	public int updateFaq(SrchFaqVo srchFaqVo);
	/**
	 * <pre>FAQ 조회수 업데이트</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchFaqVo
	 * @return
	 */
	public int updateFaqCnt(long faqOid);
	/**
	 * <pre>FAQ 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param srchFaqVo
	 * @return
	 */
	public int deleteFaq(SrchFaqVo srchFaqVo);
}
