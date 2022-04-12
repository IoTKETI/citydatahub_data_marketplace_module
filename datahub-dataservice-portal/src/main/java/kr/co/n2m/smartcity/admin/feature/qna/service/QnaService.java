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
package kr.co.n2m.smartcity.admin.feature.qna.service;

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

import com.google.gson.Gson;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.admin.common.component.CommonComponent;
import kr.co.n2m.smartcity.admin.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.admin.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.admin.common.service.FileService;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;
import kr.co.n2m.smartcity.admin.common.vo.PageVo;
import kr.co.n2m.smartcity.admin.feature.qna.mapper.QnaFileMapper;
import kr.co.n2m.smartcity.admin.feature.qna.mapper.QnaMapper;
import kr.co.n2m.smartcity.admin.feature.qna.vo.QnaFileVo;
import kr.co.n2m.smartcity.admin.feature.qna.vo.QnaVo;
import kr.co.n2m.smartcity.admin.feature.qna.vo.SrchQnaFileVo;
import kr.co.n2m.smartcity.admin.feature.qna.vo.SrchQnaVo;

@Service
public class QnaService extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private QnaMapper qnaMapper;
	@Autowired
	private QnaFileMapper qnaFileMapper;
	@Autowired
	private FileService fileService;
	
	/**
	 * Q&A 목록
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchQnaVo
	 * @return
	 */
	@Transactional
	public Map<String,Object> getQnaList(SrchQnaVo srchQnaVo,HashMap<String, String> params) {
		Map<String, Object> resMap = new HashMap<>();
		Gson gson = new Gson();
		String mainYN ="";
		mainYN = gson.fromJson(params.get("main"), String.class);
		
		if(mainYN == null) {
			try {
				int totalListSize = qnaMapper.selectQnaCount(srchQnaVo);
				srchQnaVo.setTotalListSize(totalListSize);
				List<QnaVo> list = qnaMapper.selectQnaList(srchQnaVo);
				
				if(srchQnaVo.isPaging()) {
					PageVo pageVo = new PageVo();
					BeanUtils.copyProperties(pageVo, srchQnaVo);
					
					pageVo.setTotalListSize(totalListSize);
					
					resMap.put("page", pageVo);
				}else{
					resMap.put("page", null);
				}
				resMap.put("list", list);
				
			} catch (Exception e) {
				throw new GlobalException(e);
			}
			return resMap;
		}else {
			try {
				List<QnaVo> list = qnaMapper.selectQnaListMain(srchQnaVo);
				resMap.put("page", null);
				resMap.put("list", list);
			} catch (Exception e) {
				throw new GlobalException(e);
			}
			return resMap;
		}
	}
	/**
	 * 
	 * 메인 Q&A 목록
	 * @Author      : kyunga
	 * @Date        : 2019. 10. 7.
	 * @param srchQnaVo
	 * @return
	 */
	@Transactional
	public Map<String,Object> getQnaListMain(SrchQnaVo srchQnaVo) {
		Map<String, Object> resMap = new HashMap<>();
		List<QnaVo> list = new ArrayList<>();
		try {
			list = qnaMapper.selectQnaListMain(srchQnaVo);
			resMap.put("page", null);
			resMap.put("list", list);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return resMap;
	}
	/**
	 * Q&A 목록 카운트
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchQnaVo
	 * @return
	 */
	@Transactional
	public int getQnaCount(SrchQnaVo srchQnaVo) {
		int totalListSize;
		try {
			totalListSize = qnaMapper.selectQnaCount(srchQnaVo);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return totalListSize;
	}
	
	/**
	 * Q&A 질문 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchQnaVo
	 * @param request
	 * @return
	 */
	@Transactional
	public String createQna(SrchQnaVo srchQnaVo, HttpServletRequest request) {
		long id;
		try {
			srchQnaVo.setQnaOid(StringUtil.generateKey());
			id = srchQnaVo.getQnaOid();
			int count = qnaMapper.insertQna(srchQnaVo);
			if(count<=0) throw new GlobalException();

			this.createQnaFile(id,request);
			
		} catch (Exception e) {
			throw e;
		}
		return String.valueOf(id);
	}
	
	/**
	 * Q&A 상세 조회 
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchQnaVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public QnaVo getQna(long qnaOid, boolean nohit) throws Exception {
		QnaVo qnaVo = null;
		try {
			if(!nohit) {
				int cnt = qnaMapper.updateQnaCnt(qnaOid);
				if(cnt <= 0) throw new NotFoundException();
			}
			qnaVo = qnaMapper.selectQna(qnaOid);
		} catch (Exception e) {
			throw e;
		}
		return qnaVo;
	}
	
	/**
	 * Q&A 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchQnaVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void deleteQna(SrchQnaVo srchQnaVo) throws Exception {
		
		try {
			
			this.deleteQnaFile(srchQnaVo.getQnaOid());
			int count = qnaMapper.deleteQna(srchQnaVo);
			if(count<=0) throw new NotFoundException();
		}catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * Q&A 질문 수정 
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchQnaVo
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyQna(SrchQnaVo srchQnaVo, HttpServletRequest request) throws Exception {
		try {
			
			int count = qnaMapper.updateQna(srchQnaVo);
			if(count<=0) throw new NotFoundException();

			this.updateQnaFiles(srchQnaVo, request);
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	/**
	 * Q&A 질문 부분수정 (답변등록)
	 * @Author      : areum
	 * @Date        : 2020. 07. 14.
	 * @param srchQnaVo
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyQnaPart(SrchQnaVo srchQnaVo) throws Exception {
	 	try {
			
			int count = qnaMapper.updateQnaPart(srchQnaVo);
			if(count<=0) throw new NotFoundException();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
	
	/**
	 * Q&A 조회수 증가 
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchQnaVo
	 * @return
	 */
	@Transactional
	public void updateQnaCnt(long qnaOid) {
		
		try {
			qnaMapper.updateQnaCnt(qnaOid);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
	}

	
	/**
	 * Q&A 첨부파일 조회
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param oid
	 * @return
	 */
	public QnaFileVo getQnaFile(long qnaFileOid) {
		QnaFileVo qnaFileVo = new QnaFileVo();
		
		try {
			qnaFileVo = qnaFileMapper.selectQnaFile(qnaFileOid);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		
		return qnaFileVo;
	}
	/**
	 * 
	 * <pre>Q&A 첨부파일 등록</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 15.
	 * @param qnaOid
	 * @param request
	 */
	@Transactional
	public void createQnaFile(long qnaOid, HttpServletRequest request) {
		SrchQnaFileVo srchQnaFileVo = new SrchQnaFileVo();
		try {
			Map<String, Object> fileMap = fileService.uploadFile(request);
			List<Map<String, Object>> fileInfoList = (List<Map<String, Object>>) fileMap.get("fileInfoList");
			
			for(Map<String, Object> fileInfo : fileInfoList) {
				srchQnaFileVo.setQnafileOid(StringUtil.generateKey());
				srchQnaFileVo.setQnafileOrgNm((String) fileInfo.get("originalFileName")); 
				srchQnaFileVo.setQnafileSaveNm((String) fileInfo.get("saveFileName"));
				srchQnaFileVo.setQnafileSavePath((String) fileInfo.get("savePath"));
				srchQnaFileVo.setQnafileSize((int)(long)fileInfo.get("fileSize"));
				
				srchQnaFileVo.setQnaOid(qnaOid);
				int count = qnaFileMapper.insertQnaFile(srchQnaFileVo);
				if(count<=0) throw new GlobalException();
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 
	 * <pre>Q&A 첨부파일 목록 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 15.
	 * @param qnaFileOid
	 * @return
	 */
	public List<QnaFileVo> getQnaFileList(long qnaOid) {
		List<QnaFileVo> qnaFileList = new ArrayList<QnaFileVo>();
		
		try {
			qnaFileList = qnaFileMapper.selectQnaFileList(qnaOid);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		
		return qnaFileList;
	}
	
	/**
	 * 
	 * <pre>Q&A 첨부파일 수정</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 15.
	 * @param fileId
	 * @param request
	 * @throws FileNotFoundException 
	 */
	@Transactional
	public void updateQnaFiles(SrchQnaVo srchQnaVo, HttpServletRequest request) throws FileNotFoundException {
		
		try {
			if(srchQnaVo.getDeleteFileOids() != null && !srchQnaVo.getDeleteFileOids().isEmpty()) {
				for(int i=0; i<srchQnaVo.getDeleteFileOids().size(); i++) {
					long deleteFileOid = srchQnaVo.getDeleteFileOids().get(i);
					QnaFileVo qnaFileVo = qnaFileMapper.selectQnaFile(deleteFileOid);
					
					if(qnaFileMapper.deleteQnaFile(deleteFileOid) == 1) {
						Path filePath = Paths.get(qnaFileVo.getQnafileSavePath(), qnaFileVo.getQnafileSaveNm());
						fileService.deleteFile(filePath.normalize().toString());
					}
				}
			}
			this.createQnaFile(srchQnaVo.getQnaOid(),request);
			
		} catch (Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * 
	 * <pre>Q&A 첨부파일 삭제</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 15.
	 * @param srchQnaFileVo
	 * @throws FileNotFoundException 
	 */
	@Transactional
	public void deleteQnaFile(long qnaOid) throws FileNotFoundException {
		try {
			QnaFileVo qnaFileVo = new QnaFileVo();
			QnaVo qnaVo = qnaMapper.selectQna(qnaOid);
			
			if(qnaVo.getFileList().size() > 0) {
				List<QnaFileVo> fileList = qnaVo.getFileList();
				for(QnaFileVo qnaFile : fileList) {
					long deleteFileOid = qnaFile.getQnafileOid();
					
					qnaFileVo = qnaFileMapper.selectQnaFile(deleteFileOid);
					if(qnaFileMapper.deleteQnaFile(deleteFileOid) == 1) {
						// 파일삭제
						Path filePath = Paths.get(qnaFileVo.getQnafileSavePath(), qnaFileVo.getQnafileSaveNm());
						fileService.deleteFile(filePath.normalize().toString());
					}
				}
			}
			
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		
	}
	/**
	 * Q&A 파일 다운로드
	 * @Author      : areum
	 * @Date        : 2020. 7. 14.
	 * @return : FileSystemResource
	 * @throws FileNotFoundException 
	 */
	public FileSystemResource downloadQnaFile(long qnaOid, long fileId, HttpServletResponse response) throws FileNotFoundException {
		QnaFileVo qnaFileVo = qnaFileMapper.selectQnaFile(fileId);
		String saveFileName = qnaFileVo.getQnafileSaveNm();
		String orgFileName = qnaFileVo.getQnafileOrgNm();
		String filePath = qnaFileVo.getQnafileSavePath();
		
		Path downloadPath = Paths.get(filePath).resolve(saveFileName);
		
		return fileService.downloadFile(orgFileName, downloadPath != null ? downloadPath.toString() : "", response);
	}
	
}
