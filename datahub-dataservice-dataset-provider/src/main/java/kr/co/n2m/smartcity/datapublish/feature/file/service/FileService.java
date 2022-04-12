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
package kr.co.n2m.smartcity.datapublish.feature.file.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import kr.co.n2m.smartcity.datapublish.common.component.CommonComponent;
import kr.co.n2m.smartcity.datapublish.common.config.FileConfigProperties;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;

@Service
public class FileService extends CommonComponent{
	
	private final Path fileUploadPath;
	
	public FileService(FileConfigProperties prop) {
		this.fileUploadPath = Paths.get(prop.getUploadPath());
		
		try {
			Files.createDirectories(this.fileUploadPath);
		}catch(Exception e) {
			//TODO: 업로드 디렉토리 생성 에러처리
		}
	}
	/**
	 * 파일 업로드
	 * <pre></pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 30.
	 * @param req
	 * 
	 * @return
	 */
	public Map<String, Object> uploadFile(HttpServletRequest req) throws IOException{
		Map<String, Object> resMap = new HashMap<>();
		List<Map<String, Object>> fileInfoList = new ArrayList<>();
		
		if(ServletFileUpload.isMultipartContent(req)) {
			MultipartRequest multi = (MultipartRequest)	req;
			Iterator<String> itr = multi.getFileNames();
			while(itr.hasNext()) {
				String fileName = itr.next();
				List<MultipartFile> files = multi.getFiles(fileName);
				for(MultipartFile file : files) {
					Map<String, Object> fileInfo = saveFile(file);
					fileInfoList.add(fileInfo);
				}
			}
		}
		
		resMap.put("fileInfoList", fileInfoList);
		return resMap;
	}
	
	public FileSystemResource downloadFile(String orgFileName, String filePath, HttpServletResponse response) {
		FileSystemResource fsr = null;
		try {
			File file = getFile(filePath);
			fsr = new FileSystemResource(file);
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(orgFileName, "utf-8"));
			response.setHeader("Content-Length", String.valueOf(file.length()));
		} catch (UnsupportedEncodingException e) {
			
		}
		return fsr;
	}
	
	private Map<String, Object> saveFile(MultipartFile file) {
		Map<String, Object> resMap = new HashMap<>();
		
		String orgFileName = StringUtils.cleanPath(file.getOriginalFilename());
		int extIndex = orgFileName.lastIndexOf(".") + 1;
		String fileExt = orgFileName.substring(extIndex);
		String saveFileName = UUID.randomUUID().toString();
		String fieldName = file.getName();
		Path savePath = this.fileUploadPath;
		try {
			Files.copy(file.getInputStream(), savePath.resolve(saveFileName), StandardCopyOption.REPLACE_EXISTING);
		}catch(Exception e) {
			
		}
		
		//파일타입코드 (대표이미지[image], 일반파일[file])
		if(StringUtil.equals(fieldName, "files0")) {
			resMap.put("fileTypeFlag", "image");
		}else {
			resMap.put("fileTypeFlag", "file");
			
		}
		
		resMap.put("originalFileName" , orgFileName);
		resMap.put("saveFileName"     , saveFileName);
		resMap.put("savePath"         , savePath.toString());
		resMap.put("fileExt"          , fileExt);
		resMap.put("fileSize"         , file.getSize());
		
		return resMap;
	}
	public void deleteFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()){
			logger("file with path: " + filePath + " was not found.");
			//throw new FileNotFoundException("file with path: " + filePath + " was not found.");
		}
		file.delete();
	}
    private File getFile(String filePath) {
    	File file = new File(filePath);
    	if (!file.exists()){
    		logger("file with path: " + filePath + " was not found.");
    		//throw new FileNotFoundException("file with path: " + filePath + " was not found.");
    	}
    	return file;
    }
}
