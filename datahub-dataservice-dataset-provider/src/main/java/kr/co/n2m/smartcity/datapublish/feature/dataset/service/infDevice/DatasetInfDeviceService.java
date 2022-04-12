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
package kr.co.n2m.smartcity.datapublish.feature.dataset.service.infDevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.infDevice.DatasetInfDeviceVo;

@Service
public class DatasetInfDeviceService {

	@Autowired 
	private DatasetMapper datasetMapper;
	
	/**
	 * <pre>데이터셋 - 디바이스 연결 API 목록 조회 (타 시스템 연계 인터페이스)</pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 16.
	 * @param datasetId
	 * @return
	 * @throws Exception
	 */
	public List<DatasetInfDeviceVo> getDatasetDeviceInfo(String datasetId) throws Exception {
		List<DatasetInfDeviceVo> deviceList = null;
		try {
			deviceList = datasetMapper.selectDatasetDeviceInfo(datasetId);
		}catch(Exception e) {
			throw e;
		}
		return deviceList;
	}

	/**
	 * <pre>데이터셋 - 디바이스 연결 API 등록 (타 시스템 연계 인터페이스, Callback 호출)</pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 16.
	 * @param content
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createDatasetDevice(String datasetId, List<DatasetInfDeviceVo> devices) throws Exception {
		try {
			this.removeDatasetDevice(datasetId);
			
			if (devices != null && devices.size() > 0) {
				for (DatasetInfDeviceVo device : devices) {
					device.setId(StringUtil.generateKey());
					device.setDsOid(datasetId);
					
					datasetMapper.createDatasetDevice(device);
				}
			}
		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * <pre>데이터셋 - 디바이스 연결 API 삭제 (타 시스템 연계 인터페이스, Callback 호출)</pre>
	 * @Author		: junyl
	 * @Date		: 2021. 7. 19.
	 * @param datasetId
	 * @throws Exception
	 */
	public void removeDatasetDevice(String datasetId) throws Exception {
		try {
			if (StringUtil.isEmpty(datasetId)) throw new GlobalException("Empty datasetId");
			datasetMapper.deleteDatasetDevice(datasetId);
			
		}catch(Exception e) {
			throw e;
		}
	}

}
