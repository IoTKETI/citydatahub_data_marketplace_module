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
package kr.co.n2m.smartcity.admin.feature.faq.service;

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
import kr.co.n2m.smartcity.admin.feature.faq.mapper.FaqFileMapper;
import kr.co.n2m.smartcity.admin.feature.faq.mapper.FaqMapper;
import kr.co.n2m.smartcity.admin.feature.faq.vo.FaqFileVo;
import kr.co.n2m.smartcity.admin.feature.faq.vo.FaqVo;
import kr.co.n2m.smartcity.admin.feature.faq.vo.SrchFaqFileVo;
import kr.co.n2m.smartcity.admin.feature.faq.vo.SrchFaqVo;

@Service
public class FaqService extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FaqMapper faqMapper;
	
	@Autowired
	private FileService fileService;
	@Autowired
	private FaqFileMapper faqFileMapper;
	/**
	 * 
	 * 목록 리스트
	 * @Author      : kyunga
	 * @Date        :  2019. 7. 12.
	 * @param srchFaqVo
	 * @return
	 */
	@Transactional
	public Map<String,Object> getFaqList(SrchFaqVo srchFaqVo,HashMap<String, String> params) {
		Map<String, Object> resMap = new HashMap<>();
		Gson gson = new Gson();
		String mainYN ="";
		mainYN = gson.fromJson(params.get("main"), String.class);
		if(mainYN == null) {
			try {
				int totalListSize = faqMapper.selectFaqCount(srchFaqVo);
				srchFaqVo.setTotalListSize(totalListSize);
				List<FaqVo> list = faqMapper.selectFaqList(srchFaqVo);
				if(srchFaqVo.isPaging()) {
					PageVo pageVo = new PageVo();
					BeanUtils.copyProperties(pageVo, srchFaqVo);
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
		}else {
			try {
				List<FaqVo> list = faqMapper.selectFaqListMain(srchFaqVo);
				resMap.put("page", null);
				resMap.put("list", list);
			}catch(Exception e) {
				throw new GlobalException(e);
			}
			return resMap;	
		}
		
	}
	/**
	 * 
	 * 메인 목록 리스트
	 * @Author      : kyunga
	 * @Date        :  2019. 7. 12.
	 * @param srchFaqVo
	 * @return
	 */
	@Transactional
	public Map<String,Object> getFaqListMain(SrchFaqVo srchFaqVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			List<FaqVo> list = faqMapper.selectFaqListMain(srchFaqVo);
			resMap.put("page", null);
			resMap.put("list", list);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resMap;	
	}
	/**
	 * 
	 * 목록 카운트
	 * @Author      : kyunga
	 * @Date        :  2019. 7. 12.
	 * @param srchFaqVo
	 * @return
	 */
	@Transactional
	public int getFaqCount(SrchFaqVo srchFaqVo) {
		int totalListSize;
		try {
			totalListSize = faqMapper.selectFaqCount(srchFaqVo);
			srchFaqVo.setTotalListSize(totalListSize);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return totalListSize;
	}
	/**
	 * 
	 * FAQ등록
	 * @Author      : kyunga
	 * @Date        :  2019. 7. 12.
	 * @param srchFaqVo
	 * @return
	 */
	@Transactional
	public String createFaq(SrchFaqVo srchFaqVo, HttpServletRequest request) {
		long id;
		try {
			srchFaqVo.setFaqOid(StringUtil.generateKey());
			id = srchFaqVo.getFaqOid();
			int count = faqMapper.insertFaq(srchFaqVo);
			if(count<=0) throw new GlobalException();

			this.createFaqFile(id, request);
		}catch(Exception e) {
			throw e;
		}
		return String.valueOf(id); 
	}
	/**
	 * 
	 * FAQ 상세조회
	 * @Author      : kyunga
	 * @Date        :  2019. 7. 12.
	 * @param srchFaqVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public FaqVo getFaq(long faqOid, boolean nohit) throws Exception {
		FaqVo faqVo = null;
		try {
			if(!nohit) {
				int cnt = faqMapper.updateFaqCnt(faqOid);
				if(cnt <= 0) throw new NotFoundException();
			}
			faqVo = faqMapper.selectFaq(faqOid);
		}catch(Exception e) {
			throw e;
		}
		return faqVo;
	}
	/**
	 * 
	 * FAQ 조회수 증가
	 * @Author      : kyunga
	 * @Date        :  2019. 7. 12.
	 * @param srchFaqVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyFaqCnt(long faqOid) throws Exception {
		
		try {
			int count = faqMapper.updateFaqCnt(faqOid);
			if(count<=0) throw new NotFoundException();
		}catch(Exception e) {
			throw e;
		}
	}
	/**
	 * 
	 * FAQ 수정
	 * @Author      : kyunga
	 * @Date        :  2019. 7. 12.
	 * @param srchFaqVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyFaq(SrchFaqVo srchFaqVo,HttpServletRequest request) throws Exception {
		try {
			int count = faqMapper.updateFaq(srchFaqVo);
			if(count<=0) throw new NotFoundException();
			this.updateFaqFiles(srchFaqVo, request);
		}catch(Exception e) {
			throw e;
		}
	}
	/**
	 * 
	 * FAQ 삭제
	 * @Author      : kyunga
	 * @Date        :  2019. 7. 12.
	 * @param srchFaqVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void deleteFaq(SrchFaqVo srchFaqVo) throws Exception {
		try {
			this.deleteFaqFile(srchFaqVo.getFaqOid());
			int count = faqMapper.deleteFaq(srchFaqVo);
			if(count<=0) throw new NotFoundException();
		}catch(Exception e) {
			throw e;
		}
	}
	/**
	 * 
	 * FAQ 파일 조회
	 * @Author      : kyunga
	 * @Date        :  2019. 7. 12.
	 * @param faqVo
	 * @return
	 */
	@Transactional
	public FaqFileVo getFaqFile(long faqFileOid) {
		FaqFileVo faqFileVo = new FaqFileVo();
		
		try {
			faqFileVo = faqFileMapper.selectFaqFile(faqFileOid);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return faqFileVo;
	}
	
	/***
	 * 
	 * <pre>FAQ 첨부파일 등록</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param faqOid
	 * @param request
	 */
	public void createFaqFile(long faqOid, HttpServletRequest request) {
		try {
			Map<String, Object> fileMap = fileService.uploadFile(request);
			List<Map<String, Object>> fileInfoList = (List<Map<String, Object>>) fileMap.get("fileInfoList");
			
			for(Map<String, Object> fileInfo : fileInfoList) {
				SrchFaqFileVo srchFaqFileVo = new SrchFaqFileVo();
				srchFaqFileVo.setFaqfileOrgNm((String) fileInfo.get("originalFileName"));
				srchFaqFileVo.setFaqfileSavedNm((String) fileInfo.get("saveFileName"));
				srchFaqFileVo.setFaqfileSavePath((String) fileInfo.get("savePath"));
				srchFaqFileVo.setFaqfileSizeByte((int)(long)fileInfo.get("fileSize"));
				srchFaqFileVo.setFaqfileOid(StringUtil.generateKey());
				srchFaqFileVo.setFaqOid(faqOid);
				
				int count = faqFileMapper.insertFaqFile(srchFaqFileVo);
				if(count<=0) throw new GlobalException();
			}
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 
	 * <pre>FAQ 첨부파일 목록 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param faqOid
	 * @return
	 */
	public List<FaqFileVo> getfaqFileList(long faqOid) {
		List<FaqFileVo> faqFileList = new ArrayList<FaqFileVo>();
		try {
			faqFileList = faqFileMapper.selectFaqFileList(faqOid);
		} catch (Exception e) {
		}
		return faqFileList;
	}
	/**
	 * 
	 * <pre>FAQ 첨부파일 수정</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param srchFaqVo
	 * @param request
	 */
	public void updateFaqFiles(SrchFaqVo srchFaqVo, HttpServletRequest request) {
		try {
			if(srchFaqVo.getDeleteFileOids()!= null && !srchFaqVo.getDeleteFileOids().isEmpty()) {
				for(int i=0; i<srchFaqVo.getDeleteFileOids().size(); i++) {
					long deleteFileOid = srchFaqVo.getDeleteFileOids().get(i);
					FaqFileVo faqFileVo = faqFileMapper.selectFaqFile(deleteFileOid);
					if(faqFileMapper.deleteFaqFile(deleteFileOid) == 1) {
						Path filePath = Paths.get(faqFileVo.getFaqfileSavePath(), faqFileVo.getFaqfileSavedNm());
						fileService.deleteFile(filePath.normalize().toString());
					}
				}
			}
			this.createFaqFile(srchFaqVo.getFaqOid(), request);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
	}
	/**
	 * 
	 * <pre>FAQ 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param faqOid
	 * @param fileId
	 * @param response
	 * @return
	 * @throws FileNotFoundException 
	 */
	public FileSystemResource downloadFaqFile(long faqOid, long fileId, HttpServletResponse response) throws FileNotFoundException {
		FaqFileVo faqFileVo = faqFileMapper.selectFaqFile(fileId);
		String saveFileName = faqFileVo.getFaqfileSavedNm();
		String orgFileName = faqFileVo.getFaqfileOrgNm();
		String filePath = faqFileVo.getFaqfileSavePath();
		
		Path downloadPath = Paths.get(filePath).resolve(saveFileName);
		return fileService.downloadFile(orgFileName, downloadPath != null ? downloadPath.toString() : "", response);
	}
	/**
	 * 
	 * <pre>FAQ 첨부파일 삭제</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param fileId
	 * @throws FileNotFoundException 
	 */
	public void deleteFaqFile(long faqOid) throws FileNotFoundException {
		try {
			FaqFileVo faqFileVo = new FaqFileVo();
			FaqVo faqVo = faqMapper.selectFaq(faqOid);
			if(faqVo.getFileList().size() > 0) {
				List<FaqFileVo> fileList = faqVo.getFileList();
				for(FaqFileVo faqFile : fileList) {
					long deleteFileOid = faqFile.getFaqfileOid();
					
					faqFileVo = faqFileMapper.selectFaqFile(deleteFileOid);
					if(faqFileMapper.deleteFaqFile(deleteFileOid)==1) {
						Path filePath = Paths.get(faqFileVo.getFaqfileSavePath(), faqFileVo.getFaqfileSavedNm());
						fileService.deleteFile(filePath.normalize().toString());
					}
				}
			}
			
		} catch (Exception e) {
			throw new GlobalException(e);
		}
	}
	
	

	
}
