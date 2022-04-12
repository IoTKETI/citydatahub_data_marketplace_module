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
package kr.co.n2m.smartcity.admin.feature.review.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.n2m.smartcity.admin.feature.review.vo.ReviewDatasetVo;
import kr.co.n2m.smartcity.admin.feature.review.vo.ReviewVo;
import kr.co.n2m.smartcity.admin.feature.review.vo.SrchReviewVo;

@Mapper
public interface ReviewMapper {
	/**
	 * <pre>활용후기 목록 카운트 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param ReviewVo
	 * @return
	 */
	public int selectReviewCount(SrchReviewVo srchReviewVo);
	/**
	 * <pre>활용후기 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param ReviewVo
	 * @return
	 */
	public List<ReviewVo> selectReviewList(SrchReviewVo srchReviewVo);
	/**
	 * <pre>활용후기 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param ReviewVo
	 * @return
	 */
	public int insertReview(SrchReviewVo srchReviewVo);
	/**
	 * <pre>활용후기 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param ReviewVo
	 * @return
	 */
	public ReviewVo selectReview(long reviewOid);
	/**
	 * <pre>활용후기 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param ReviewVo
	 * @return
	 */
	public int updateReview(SrchReviewVo srchReviewVo);
	/**
	 * <pre>활용후기 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param ReviewVo
	 * @return
	 */
	public int deleteReview(SrchReviewVo srchReviewVo);
	/**
	 * <pre>활용후기 데이터셋 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param ReviewVo
	 */
	public int insertReviewDatasetList(SrchReviewVo srchReviewVo);
	/**
	 * <pre>활용후기 데이터셋 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param reviewVo
	 * @return
	 */
	public List<ReviewDatasetVo> selectReviewDatasetList(long reviewOid);
	/**
	 * <pre>활용후기 데이터셋 목록 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param reviewVo
	 */
	public int deleteReviewDatasetList(SrchReviewVo srchReviewVo);
}
