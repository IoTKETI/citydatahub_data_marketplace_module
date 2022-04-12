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
package kr.co.smartcity.user.feature.common.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.smartcity.user.common.component.HttpComponent;
import kr.co.smartcity.user.common.util.StringUtil;
import kr.co.smartcity.user.feature.dataset.service.DatasetService;

@Service("fileService")
public class FileService extends HttpComponent {

	@Autowired
	DatasetService datasetService;
	
	@Autowired
	ExcelDownloadService excelDownloadService;
	
	/**
	 * 파일 다운로드
	 * @Author      : yebrwe
	 * @Date        : 2019. 9. 16.
	 * @param params
	 * @param res
	 */
	public void download(Map<String, Object> params, HttpServletResponse response){
		String type = (String) params.get("type");
		String url = props.getDatapublishMsApiUrl()  + "/dataservice/dataset/" + params.get("oid") + "/attachFiles/" + params.get("fileOid");
		
		if(StringUtil.equalsIgnoreCase(type, "T")) {
			url = props.getPortalMsApiUrl()  + "/dataserviceUi/attachFiles/"+params.get("menu")+"/"+params.get("oid");
		} else if (StringUtil.equalsIgnoreCase(type, "PT")) {
			url = props.getPortalMsApiUrl()  + "/dataserviceUi/review/"+params.get("oid")+"/attachFiles/"+params.get("fileOid");
		}
		downloadFile(url, params, response);
	}

	/**
	 * <pre>엑셀 다운로드 유효성 </pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 3.
	 * @param params
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	public void validateDownloadExcel(String datasetId, String queryString, HttpServletResponse response) throws Exception {
		try {
			excelDownloadService.validateDownloadExcel(datasetId, queryString, response);
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * <pre>엑셀 다운로드 </pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 3.
	 * @param params
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	public void downloadExcel(String datasetId, String queryString, HttpServletResponse response) throws Exception {
		try {
			excelDownloadService.downloadExcel(datasetId, queryString, response);
		} catch (Exception e) {
			throw e;
		}
	}
	
}
