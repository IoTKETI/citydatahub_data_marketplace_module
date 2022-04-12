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
package kr.co.n2m.smartcity.datapublish.feature.dataset.service.inquiry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.inquiry.DatasetInquiryVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.inquiry.SrchDatasetInquiryVo;

@Service
public class DatasetInquiryService {

	@Autowired 
	private DatasetMapper datasetMapper;
	
	/**
	 * 데이터셋 문의 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 19.
	 * @param srchDatasetInquiryVo
	 * @return
	 */
	public List<DatasetInquiryVo> getDatasetInquiryList(SrchDatasetInquiryVo srchDatasetInquiryVo) {
		List<DatasetInquiryVo> datasetInquiryList = null;
		try {
			datasetInquiryList = datasetMapper.selectDatasetInquiryList(srchDatasetInquiryVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetInquiryList;
	}
	
	/**
	 * 데이터셋 문의 상세 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 19.
	 * @param srchDatasetInquiryVo
	 * @return
	 */
	public DatasetInquiryVo getDatasetInquiry(String datasetId, long qnaId) {
		DatasetInquiryVo datasetInquiryVo = null;
		try {
			datasetInquiryVo = datasetMapper.selectDatasetInquiry(datasetId, qnaId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetInquiryVo;
	}

	/**
	 * 데이터셋 문의 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 19.
	 * @param srchDatasetInquiryVo
	 * @return
	 */
	@Transactional
	public String createDatasetInquiry(SrchDatasetInquiryVo srchDatasetInquiryVo) {
		String resultKey = "";
		try {
			srchDatasetInquiryVo.setId(StringUtil.generateKey());
			datasetMapper.insertDatasetInquiry(srchDatasetInquiryVo);
			resultKey = String.valueOf(srchDatasetInquiryVo.getId());
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}

	/**
	 * 데이터셋 문의 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 19.
	 * @param srchDatasetInquiryVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyDatasetInquiry(SrchDatasetInquiryVo srchDatasetInquiryVo) throws Exception {
		try {
			int iRet = datasetMapper.updateDatasetInquiry(srchDatasetInquiryVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

	/**
	 * 데이터셋 문의 삭제
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 19.
	 * @param srchDatasetInquiryVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void deleteDatasetInquiry(SrchDatasetInquiryVo srchDatasetInquiryVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDatasetInquiry(srchDatasetInquiryVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
}
