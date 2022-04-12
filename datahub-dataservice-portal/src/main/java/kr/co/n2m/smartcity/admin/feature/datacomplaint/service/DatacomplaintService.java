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
package kr.co.n2m.smartcity.admin.feature.datacomplaint.service;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.admin.common.component.CommonComponent;
import kr.co.n2m.smartcity.admin.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.admin.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.admin.common.service.FileService;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;
import kr.co.n2m.smartcity.admin.common.vo.PageVo;
import kr.co.n2m.smartcity.admin.feature.datacomplaint.mapper.DatacomplaintFileMapper;
import kr.co.n2m.smartcity.admin.feature.datacomplaint.mapper.DatacomplaintMapper;
import kr.co.n2m.smartcity.admin.feature.datacomplaint.vo.DatacomplaintFileVo;
import kr.co.n2m.smartcity.admin.feature.datacomplaint.vo.DatacomplaintVo;
import kr.co.n2m.smartcity.admin.feature.datacomplaint.vo.SrchDatacomplaintFileVo;
import kr.co.n2m.smartcity.admin.feature.datacomplaint.vo.SrchDatacomplaintVo;

@Service
public class DatacomplaintService extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DatacomplaintMapper datacomplaintMapper;
	@Autowired
	private DatacomplaintFileMapper datacomplaintFileMapper;
	@Autowired
	private FileService fileService;
	/**
	 * 
	 * 데이터민원 목록 조회
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 23.
	 * @param srchDatacomplaintVo
	 * @return
	 */
	@Transactional
	public Map<String,Object> getDatacomplaintList(SrchDatacomplaintVo srchDatacomplaintVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			int totalListSize = datacomplaintMapper.selectDatacomplaintCount(srchDatacomplaintVo);
			srchDatacomplaintVo.setTotalListSize(totalListSize);
			List<DatacomplaintVo> list = datacomplaintMapper.selectDatacomplaintList(srchDatacomplaintVo);
			
			if(srchDatacomplaintVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchDatacomplaintVo);
				pageVo.setTotalListSize(totalListSize);
				resMap.put("page", pageVo);
			}else{
				resMap.put("page", null);
			}
			
			resMap.put("list", list);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resMap;	
	}
	/**
	 * 
	 * 데이터민원 카운트
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 23.
	 * @param srchDatacomplaintVo
	 * @return
	 */
	@Transactional
	public int getDatacomplaintCount(SrchDatacomplaintVo srchDatacomplaintVo) {
		int totalListSize;
		try {
			totalListSize = datacomplaintMapper.selectDatacomplaintCount(srchDatacomplaintVo);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return totalListSize;
	}
	/**
	 * 
	 * 데이터민원 등록
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 23.
	 * @param srchDatacomplaintVo
	 * @return
	 */
	@Transactional
	public String createDatacomplaint(SrchDatacomplaintVo srchDatacomplaintVo,HttpServletRequest request) {
		long id;
		try {
			srchDatacomplaintVo.setDatacomplainOid(StringUtil.generateKey());
			id = srchDatacomplaintVo.getDatacomplainOid();
			int count = datacomplaintMapper.insertDatacomplaint(srchDatacomplaintVo);
			if(count<=0) throw new GlobalException();
			this.createDatacomplaintFile(id, request);
		}catch(Exception e) {
			throw e;
		}
		return String.valueOf(id);
	}
	/**
	 * 
	 * 데이터민원 상세 조회
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 23.
	 * @param srchDatacomplaintVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public DatacomplaintVo getDatacomplaint(long datacomplainOid, boolean nohit) throws Exception {
		DatacomplaintVo datacomplaintVo = null;
		try {
			if(!nohit) {
				int cnt = datacomplaintMapper.updateDatacomplaintCnt(datacomplainOid);
				if(cnt <= 0) throw new NotFoundException();
			}
			datacomplaintVo = datacomplaintMapper.selectDatacomplaint(datacomplainOid);
		}catch(Exception e) {
			throw e;
		}
		return datacomplaintVo;
	}
	/**
	 * 
	 * 데이터민원 삭제
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param srchDatacomplaintVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void deleteDatacomplaint(SrchDatacomplaintVo srchDatacomplaintVo) throws Exception {
		try {
			this.deleteDatacomplaintFile(srchDatacomplaintVo.getDatacomplainOid());
			int count = datacomplaintMapper.deleteDatacomplaint(srchDatacomplaintVo);
			if(count<=0) throw new NotFoundException();
			
		}catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * 데이터민원 조회수 수정
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param srchDatacomplaintVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyDatacomplaintCnt(long datacomplainOid) throws Exception {
		try {
			int count = datacomplaintMapper.updateDatacomplaintCnt(datacomplainOid);
			if(count<=0) throw new NotFoundException();
		}catch(Exception e) {
			throw e;
		}
	}
	/**
	 * 
	 * 데이터민원 수정
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param srchDatacomplaintVo
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyDatacomplaint(SrchDatacomplaintVo srchDatacomplaintVo,HttpServletRequest request) throws Exception {
		try {
			
			int count = datacomplaintMapper.updateDatacomplaint(srchDatacomplaintVo);
			if(count<=0) throw new NotFoundException();
			this.updateDatacomplaintFiles(srchDatacomplaintVo, request);
		}catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * 데이터 민원 수정(부분수정) - 답변 등록
	 * @Author      : kyunga
	 * @Date        : 2019. 7. 17.
	 * @param srchDatacomplaintVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyDatacomplaintPart(SrchDatacomplaintVo srchDatacomplaintVo,HttpServletRequest request) throws Exception {
		try {
			int count = datacomplaintMapper.updateDatacomplaintPart(srchDatacomplaintVo);
			if(count<=0) throw new NotFoundException();
			
			Map<String, Object> fileMap = fileService.uploadFile(request);
			List<Map<String, Object>> fileInfoList = (List<Map<String, Object>>) fileMap.get("fileInfoList");
			
			for(Map<String, Object> fileInfo : fileInfoList) {
				SrchDatacomplaintFileVo srchDatacomplaintFileVo = new SrchDatacomplaintFileVo();
				srchDatacomplaintFileVo.setDcpFileOrgNm((String) fileInfo.get("originalFileName"));
				srchDatacomplaintFileVo.setDcpFileSavedNm((String) fileInfo.get("saveFileName"));
				srchDatacomplaintFileVo.setDcpFileSavePath((String) fileInfo.get("savePath"));
				srchDatacomplaintFileVo.setDcpFileSizeByte((int)(long)fileInfo.get("fileSize"));
				srchDatacomplaintFileVo.setDcpFileOid(StringUtil.generateKey());
				srchDatacomplaintFileVo.setDatacomplainOid(srchDatacomplaintVo.getDatacomplainOid());
				srchDatacomplaintFileVo.setDcpFileTypeAnswer("N");
				
				count = datacomplaintFileMapper.insertDcpFileAnswer(srchDatacomplaintFileVo);
				if(count<=0) throw new GlobalException();
			}
			
		}catch(Exception e) {
			throw e;
		}
	}

	
	
	/**
	 * 
	 * 첨부파일 조회
	 * @Author      : kyunga
	 * @Date        : 2019. 11. 7.
	 * @param oid
	 * @return
	 */
	@Transactional
	public DatacomplaintFileVo getDatacomplaintFile(long oid) {
		DatacomplaintFileVo datacomplaintFileVo = new DatacomplaintFileVo();
		try {
			datacomplaintFileVo = datacomplaintFileMapper.selectDcpFile(oid);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return datacomplaintFileVo;
	}
	
	/**
	 * 
	 * <pre>데이터 민원 첨부파일 등록</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param datacomplainOid
	 * @param request
	 */
	public void createDatacomplaintFile(long datacomplainOid, HttpServletRequest request) {
		try {
			Map<String, Object> fileMap = fileService.uploadFile(request);
			List<Map<String, Object>> fileInfoList = (List<Map<String, Object>>) fileMap.get("fileInfoList");
			
			for(Map<String, Object> fileInfo : fileInfoList) {
				SrchDatacomplaintFileVo srchDatacomplaintFileVo = new SrchDatacomplaintFileVo();
				srchDatacomplaintFileVo.setDcpFileOrgNm((String) fileInfo.get("originalFileName"));
				srchDatacomplaintFileVo.setDcpFileSavedNm((String) fileInfo.get("saveFileName"));
				srchDatacomplaintFileVo.setDcpFileSavePath((String) fileInfo.get("savePath"));
				srchDatacomplaintFileVo.setDcpFileSizeByte((int)(long)fileInfo.get("fileSize"));
				srchDatacomplaintFileVo.setDcpFileOid(StringUtil.generateKey());
				srchDatacomplaintFileVo.setDatacomplainOid(datacomplainOid);
				srchDatacomplaintFileVo.setDcpFileTypeAnswer("N");
				
				int count = datacomplaintFileMapper.insertDcpFile(srchDatacomplaintFileVo);
				if(count<=0) throw new GlobalException();
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * <pre>데이터 민원 첨부파일 목록 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param datacomplainOid
	 * @return
	 */
	public List<DatacomplaintFileVo> getDatacomplaintFileList(long datacomplainOid) {
		List<DatacomplaintFileVo> getDatacomplaintFileList = new ArrayList<DatacomplaintFileVo>();
		try {
			getDatacomplaintFileList = datacomplaintFileMapper.selectDcpFileList(datacomplainOid);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return getDatacomplaintFileList;
	}
	
	/**
	 * 
	 * <pre>데이터 민원 첨부파일 수정</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param srchDatacomplaintVo
	 * @param request
	 * @param param
	 */
	public void updateDatacomplaintFiles(SrchDatacomplaintVo srchDatacomplaintVo, HttpServletRequest request) {
		try {
			if(srchDatacomplaintVo.getDeleteFileOids() != null && !srchDatacomplaintVo.getDeleteFileOids().isEmpty()) {
				for(int i=0; i<srchDatacomplaintVo.getDeleteFileOids().size(); i++) {
					long deleteFileOid = srchDatacomplaintVo.getDeleteFileOids().get(i);
					DatacomplaintFileVo datacomplaintVo = datacomplaintFileMapper.selectDcpFile(deleteFileOid);
					
					if(datacomplaintFileMapper.deleteDcpFile(deleteFileOid) == 1) {
						Path filePath = Paths.get(datacomplaintVo.getDcpFileSavedNm(), datacomplaintVo.getDcpFileSavedNm());
						fileService.deleteFile(filePath.normalize().toString());
					}
				}
			}
			
			this.createDatacomplaintFile(srchDatacomplaintVo.getDatacomplainOid(), request);
			
		} catch (Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * 
	 * <pre>데이터 민원 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param datacomplainOid
	 * @param fileId
	 * @param response
	 * @return
	 * @throws FileNotFoundException 
	 */
	public FileSystemResource downloadDatacomplaintFile(long datacomplainOid, long fileId,HttpServletResponse response) throws FileNotFoundException {
		DatacomplaintFileVo datacomplaintFileVo = datacomplaintFileMapper.selectDcpFile(fileId);
		String saveFileName = datacomplaintFileVo.getDcpFileSavedNm();
		String orgFileName = datacomplaintFileVo.getDcpFileOrgNm();
		String filePath = datacomplaintFileVo.getDcpFileSavePath();
		
		Path downloadPath = Paths.get(filePath).resolve(saveFileName);
		return fileService.downloadFile(orgFileName, downloadPath != null ? downloadPath.toString() : "", response);
	}
	
	/**
	 * 
	 * <pre>데이터 민원 첨부파일 삭제</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param fileId
	 */
	public void deleteDatacomplaintFile(long datacomplainOid) {
		try {
			DatacomplaintFileVo datacomplaintFileVo = new DatacomplaintFileVo();
			DatacomplaintVo datacomplaintVo = datacomplaintMapper.selectDatacomplaint(datacomplainOid);
			
			if(datacomplaintVo.getFileList().size() > 0) {
				List<DatacomplaintFileVo> fileList = datacomplaintVo.getFileList();
				for(DatacomplaintFileVo dcpFile : fileList) {
					long deleteFileOid = dcpFile.getDcpFileOid();
					
					datacomplaintFileVo = datacomplaintFileMapper.selectDcpFile(deleteFileOid);
					if(datacomplaintFileMapper.deleteDcpFile(deleteFileOid) == 1) {
						// 파일삭제
						Path filePath = Paths.get(datacomplaintFileVo.getDcpFileSavePath(), datacomplaintFileVo.getDcpFileSavedNm());
						fileService.deleteFile(filePath.normalize().toString());
					}
				}
			}
			
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		
	}
}
