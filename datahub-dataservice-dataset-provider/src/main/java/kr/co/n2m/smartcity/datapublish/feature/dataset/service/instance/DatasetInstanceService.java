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
package kr.co.n2m.smartcity.datapublish.feature.dataset.service.instance;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.n2m.smartcity.datapublish.common.component.CommonComponent;
import kr.co.n2m.smartcity.datapublish.common.exceptions.ConflictException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.datapublish.common.kafka.KafkaConst;
import kr.co.n2m.smartcity.datapublish.common.kafka.Producer;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.common.vo.PageVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.DatasetVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.SrchDatasetVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.analysisApprovalRequests.DataAnalystRequestModelVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.analysisApprovalRequests.DataAnalystRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.analysisApprovalRequests.SrchDataAnalystRequestModelVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.analysisApprovalRequests.SrchDataAnalystRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.attachFiles.DatasetFileVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.attachFiles.SrchDatasetFileVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.DatasetOutputVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.SrchDatasetOutputVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor.DatasetAdaptorFieldVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor.DatasetAdaptorVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor.SrchDatasetAdaptorFieldVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetAdaptor.SrchDatasetAdaptorVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetOrigin.DatasetOriginVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetOrigin.SrchDatasetOriginVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.inquiry.DatasetInquiryVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.inquiry.SrchDatasetInquiryVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.instance.DatasetInstanceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.instance.SrchDatasetInstanceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.rating.DatasetSatisfactionRatingVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.rating.SrchDatasetSatisfactionRatingVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.sample.SimpleDatasetVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.DatasetSearchInfoVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.SrchDatasetSearchInfoVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.usageHistory.DatasetUseHistoryVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.usageHistory.SrchDatasetUseHistoryVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.wishlist.DatasetInterestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.wishlist.SrchDatasetInterestVo;
import kr.co.n2m.smartcity.datapublish.feature.file.service.FileService;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class DatasetInstanceService {
	@Autowired 
	private DatasetMapper datasetMapper;
	
	/**
	 * <pre>데이터셋 인스턴스 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetInstanceVo
	 * @return
	 */
	public List<DatasetInstanceVo> getDatasetInstanceList(SrchDatasetInstanceVo srchDatasetInstanceVo) {
		try {
			return datasetMapper.selectDatasetInstanceList(srchDatasetInstanceVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * <pre>데이터셋 인스턴스 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetInstanceVo
	 */
	public void createDatasetInstance(SrchDatasetInstanceVo srchDatasetInstanceVo) {
		try {
			srchDatasetInstanceVo.setId(String.valueOf(StringUtil.generateKey()));
			datasetMapper.insertDatasetInstance(srchDatasetInstanceVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * <pre>데이터셋 인스턴스 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetInstanceVo
	 * @throws Exception 
	 */
	public void deleteDatasetInstance(SrchDatasetInstanceVo srchDatasetInstanceVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDatasetInstance(srchDatasetInstanceVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
}
