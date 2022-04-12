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
package kr.co.n2m.smartcity.datapublish.feature.datasetUi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.n2m.smartcity.datapublish.feature.datasetUi.vo.SrchIntegratedVo;

@Mapper
public interface IntegratedMapper {
	/**
	 * <pre>데이터셋 이용신청 환불 내역 목록 카운트 조회(비표준, 활용자)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param srchIntegratedVo
	 * @return
	 */
	int selectDatasetUsageRefundsCountByUser(SrchIntegratedVo srchIntegratedVo);

	/**
	 * <pre>데이터셋 이용신청 환불 내역 목록 조회(비표준, 활용자)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param srchIntegratedVo
	 * @return
	 */
	List<Map<String, Object>> selectDatasetUsageRefundsListByUser(SrchIntegratedVo srchIntegratedVo);
	
	/**
	 * <pre>데이터셋 이용신청 환불 내역 목록 카운트 조회(비표준, 제공자)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param srchIntegratedVo
	 * @return
	 */
	int selectDatasetUsageRefundsCountByProvider(SrchIntegratedVo srchIntegratedVo);
	
	/**
	 * <pre>데이터셋 이용신청 환불 내역 목록 조회(비표준, 제공자)</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 8.
	 * @param srchIntegratedVo
	 * @return
	 */
	List<Map<String, Object>> selectDatasetUsageRefundsListByProvider(SrchIntegratedVo srchIntegratedVo);
	
}
