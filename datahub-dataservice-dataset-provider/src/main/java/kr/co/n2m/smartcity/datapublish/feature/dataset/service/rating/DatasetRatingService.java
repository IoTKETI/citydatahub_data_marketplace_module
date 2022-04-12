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
package kr.co.n2m.smartcity.datapublish.feature.dataset.service.rating;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.rating.DatasetSatisfactionRatingVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.rating.SrchDatasetSatisfactionRatingVo;

@Service
public class DatasetRatingService {
	@Autowired 
	private DatasetMapper datasetMapper;
	
	/**
	 * <pre>만족도 평가 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 6.
	 * @param srchDatasetSatisfactionRatingVo
	 * @return
	 */
	
	public List<DatasetSatisfactionRatingVo> getDatasetSatisfactionList(SrchDatasetSatisfactionRatingVo srchDatasetSatisfactionRatingVo) {
		List<DatasetSatisfactionRatingVo> datasetSatisfactionRatingVoList = null;
		try {
			datasetSatisfactionRatingVoList = datasetMapper.selectDatasetSatisfactionList(srchDatasetSatisfactionRatingVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetSatisfactionRatingVoList;
	}
	
	/**
	 * <pre>만족도 평가 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 7.
	 * @param srchDatasetVo
	 * @return
	 */
	public String createDatasetSatisfaction(SrchDatasetSatisfactionRatingVo srchDatasetSatisfactionRatingVo) {
		String resultKey = "";
		try {
			srchDatasetSatisfactionRatingVo.setId(StringUtil.generateKey());
			datasetMapper.insertDatasetSatisfaction(srchDatasetSatisfactionRatingVo);
			resultKey = String.valueOf(srchDatasetSatisfactionRatingVo.getId());
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}

	/**
	 * <pre>데이터셋 만족도 평가 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param datasetId
	 * @param ratingId
	 * @return
	 */
	public DatasetSatisfactionRatingVo getDatasetSatisfaction(String datasetId, String ratingId) {
		DatasetSatisfactionRatingVo datasetSatisfactionRatingVo = null;
		try {
			datasetSatisfactionRatingVo = datasetMapper.selectDatasetSatisfaction(datasetId, ratingId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetSatisfactionRatingVo;
	}
	
	/**
	 * <pre>데이터셋 만족도 평가 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDatasetSatisfactionRatingVo
	 */
	public void modifyDatasetSatisfaction(SrchDatasetSatisfactionRatingVo srchDatasetSatisfactionRatingVo) throws Exception {

		try {
			int iRet = datasetMapper.updateDatasetSatisfaction(srchDatasetSatisfactionRatingVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		
	}

	/**
	 * <pre>데이터셋 만족도 평가 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 16.
	 * @param srchDatasetSatisfactionRatingVo
	 */
	public void deleteDatasetSatisfaction(SrchDatasetSatisfactionRatingVo srchDatasetSatisfactionRatingVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDatasetSatisfaction(srchDatasetSatisfactionRatingVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		
	}

}
