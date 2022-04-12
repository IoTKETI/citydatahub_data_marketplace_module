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
package kr.co.n2m.smartcity.datapublish.feature.dataset.service.attachFiles;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.attachFiles.DatasetFileVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.attachFiles.SrchDatasetFileVo;
import kr.co.n2m.smartcity.datapublish.feature.file.service.FileService;

@Service
public class DatasetAttachFilesService {
	@Autowired DatasetMapper datasetMapper;
	@Autowired FileService fileService;
	
	/**
	 * <pre>데이터셋 파일 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetFileVo
	 * @return
	 */
	public List<DatasetFileVo> getDatasetFileList(SrchDatasetFileVo srchDatasetFileVo) {
		List<DatasetFileVo> datasetFileList = null;
		try {
			datasetFileList = datasetMapper.selectDatasetFileList(srchDatasetFileVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetFileList;
	}

	/**
	 * <pre>데이터셋 파일 등록</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param devDatasetId
	 * @param request
	 */
	@Transactional
	public void createDatasetFile(String devDatasetId, HttpServletRequest request)  {
		try {
			Map<String, Object> fileMap = fileService.uploadFile(request);
			
			List<Map<String, Object>> fileInfoList = (List<Map<String, Object>>) fileMap.get("fileInfoList");
			int fileListSize = fileInfoList.size();
			List<Long> fileKeyList = StringUtil.generateKeys(fileListSize);
			for(int i=0; i<fileListSize; i++) {
				Map<String, Object> fileInfo = fileInfoList.get(i);
				SrchDatasetFileVo srchDatasetFileVo = new SrchDatasetFileVo();
				srchDatasetFileVo.setId(fileKeyList.get(i));
				srchDatasetFileVo.setDatasetId(devDatasetId);
				srchDatasetFileVo.setType((String) fileInfo.get("fileTypeFlag")); //일반파일 (대표이미지[image], 일반파일[file])
				srchDatasetFileVo.setPhysicalName((String) fileInfo.get("originalFileName"));
				srchDatasetFileVo.setLogicalName((String) fileInfo.get("saveFileName"));
				srchDatasetFileVo.setPath((String) fileInfo.get("savePath"));
				srchDatasetFileVo.setSize((int)(long)fileInfo.get("fileSize"));
				datasetMapper.insertDatasetFile(srchDatasetFileVo);
			}
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	@Transactional
	public void createDatasetFile(SrchDatasetFileVo srchDatasetFileVo)  {
		try {
			srchDatasetFileVo.setId(StringUtil.generateKey());
			datasetMapper.insertDatasetFile(srchDatasetFileVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * <pre>데이터셋 파일 다운로드</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param devDatasetId
	 * @param fileId
	 * @param response
	 * @return
	 */
	public FileSystemResource downloadDatasetFile(String devDatasetId, long fileId, HttpServletResponse response) {
		Path downloadPath = null;
		String saveFileName = "";
		String orgFileName = "";
		String filePath = "";
		SrchDatasetFileVo srchDatasetFileVo = new SrchDatasetFileVo();
		srchDatasetFileVo.setDatasetId(devDatasetId);
		srchDatasetFileVo.setId(fileId);
		DatasetFileVo datasetFileVo= datasetMapper.selectDatasetFile(srchDatasetFileVo);
		if(datasetFileVo != null) {
			saveFileName = datasetFileVo.getLogicalName();
			orgFileName  = datasetFileVo.getPhysicalName();
			filePath     = datasetFileVo.getPath();
			downloadPath = Paths.get(filePath).resolve(saveFileName);
		}
		return fileService.downloadFile(orgFileName, downloadPath != null ? downloadPath.toString() : "", response);
		
	}

	/**
	 * <pre>데이터셋 파일 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 7.
	 * @param srchDatasetFileVo
	 * @throws Exception 
	 */
	@Transactional
	public void deleteDatasetFile(SrchDatasetFileVo srchDatasetFileVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDatasetFile(srchDatasetFileVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
}
