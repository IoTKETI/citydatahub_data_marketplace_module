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
package kr.co.n2m.smartcity.datapublish.feature.dataset.service.wishlist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.n2m.smartcity.datapublish.common.exceptions.ConflictException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.common.vo.PageVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.wishlist.DatasetInterestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.wishlist.SrchDatasetInterestVo;

@Service
public class DatasetWishlistService {
	
	@Autowired 
	private DatasetMapper datasetMapper;

	/**
	 * 데이터셋 관심상품 목록 조회
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 19.
	 * @param srchDatasetVo
	 * @return
	 */
	@Transactional
	public Map<String, Object> getDatasetWishlistList(SrchDatasetInterestVo srchDatasetInterestVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			int totalListSize = datasetMapper.selectDatasetWishlistCount(srchDatasetInterestVo);
			srchDatasetInterestVo.setTotalListSize(totalListSize);
			List<DatasetInterestVo> datasetInterestList = datasetMapper.selectDatasetWishlistList(srchDatasetInterestVo);

			if(srchDatasetInterestVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchDatasetInterestVo);
				resMap.put("page", pageVo);
			}else{
				resMap.put("page", null);
			}
			
			resMap.put("list", datasetInterestList);
			
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resMap;
	}

	/**
	 * 데이터셋 관심상품 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 19.
	 * @param srchDatasetInterestVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void createDatasetWishlist(SrchDatasetInterestVo srchDatasetInterestVo) throws Exception {
		try {
			int datasetWishlistCount = datasetMapper.selectDatasetWishlistCount(srchDatasetInterestVo);
			if(datasetWishlistCount > 0) {
				throw new ConflictException();
			}
			srchDatasetInterestVo.setId(StringUtil.generateKey());
			datasetMapper.insertDatasetWishlist(srchDatasetInterestVo);
		}catch(ConflictException ce) {
			throw ce;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * 데이터셋 관심상품 삭제
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 19.
	 * @param srchDatasetInterestVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void deleteDatasetWishlist(SrchDatasetInterestVo srchDatasetInterestVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDatasetWishlist(srchDatasetInterestVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

}
