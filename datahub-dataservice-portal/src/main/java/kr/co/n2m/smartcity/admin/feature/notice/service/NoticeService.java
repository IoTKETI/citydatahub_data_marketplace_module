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
package kr.co.n2m.smartcity.admin.feature.notice.service;

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
import kr.co.n2m.smartcity.admin.feature.notice.mapper.NoticeFileMapper;
import kr.co.n2m.smartcity.admin.feature.notice.mapper.NoticeMapper;
import kr.co.n2m.smartcity.admin.feature.notice.vo.NoticeFileVo;
import kr.co.n2m.smartcity.admin.feature.notice.vo.NoticeVo;
import kr.co.n2m.smartcity.admin.feature.notice.vo.SrchNoticeFileVo;
import kr.co.n2m.smartcity.admin.feature.notice.vo.SrchNoticeVo;

@Service
public class NoticeService extends CommonComponent{
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private FileService fileService;
	@Autowired
	private NoticeFileMapper noticeFileMapper;
	
	/**
	 * 공지사항 목록
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchNoticeVo
	 * @return
	 */
	@Transactional
	public Map<String,Object> getNoticeList(SrchNoticeVo srchNoticeVo,HashMap<String, String> params) {
		Map<String, Object> resMap = new HashMap<>();
		Gson gson = new Gson();
		String mainYN ="";
		mainYN = gson.fromJson(params.get("main"), String.class);
		if(mainYN == null) {
			try {
				int totalListSize = noticeMapper.selectNoticeCount(srchNoticeVo);
				srchNoticeVo.setTotalListSize(totalListSize);
				
				List<NoticeVo> list = noticeMapper.selectNoticeList(srchNoticeVo);
				if(srchNoticeVo.isPaging()) {
					PageVo pageVo = new PageVo();
					BeanUtils.copyProperties(pageVo, srchNoticeVo);
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
				List<NoticeVo> list = noticeMapper.selectNoticeListMain(srchNoticeVo);
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
	 * 메인 공지사항 목록
	 * @Author      : kyunga
	 * @Date        : 2019. 10. 7.
	 * @param srchNoticeVo
	 * @return
	 */
	@Transactional
	public Map<String,Object> getNoticeListMain(SrchNoticeVo srchNoticeVo) {
		Map<String, Object> resMap = new HashMap<>();
		List<NoticeVo> list = new ArrayList<>();
		try {
			list = noticeMapper.selectNoticeListMain(srchNoticeVo);
			resMap.put("page", null);
			resMap.put("list", list);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return resMap;
	}
	
	/**
	 * 공지사항 목록 카운트
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchNoticeVo
	 * @return
	 */
	@Transactional
	public int getNoticeCount(SrchNoticeVo srchNoticeVo) {
		int totalListSize;
		try {
			totalListSize = noticeMapper.selectNoticeCount(srchNoticeVo);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return totalListSize;
	}
	
	/**
	 * 공지사항 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchNoticeVo
	 * @return
	 */
	@Transactional
	public String createNotice(SrchNoticeVo srchNoticeVo, HttpServletRequest request) {
		long id;
		try {
			srchNoticeVo.setNoticeOid(StringUtil.generateKey());
			id = srchNoticeVo.getNoticeOid();
			int count = noticeMapper.insertNotice(srchNoticeVo);
			if(count<=0) throw new GlobalException();

			this.createNoticeFile(id, request);
			
		}catch (Exception e) {
			throw e;
		}
		return String.valueOf(id);
	}
	
	/**
	 * 공지사항 상세
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchNoticeVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public NoticeVo getNotice(long noticeOid, boolean nohit) throws Exception {
		NoticeVo noticeVo = null;
		try {
			if(!nohit) {
				int cnt = noticeMapper.updateCnt(noticeOid);
				if(cnt <= 0) throw new NotFoundException();
			}
			noticeVo = noticeMapper.selectNotice(noticeOid);
		} catch (Exception e) {
			throw e;
		}
		return noticeVo;
	}
	
	/**
	 * 공지사항 수정
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchNoticeVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyNotice(SrchNoticeVo srchNoticeVo, HttpServletRequest request) throws Exception {
		try {
			
			int count = noticeMapper.updateNotice(srchNoticeVo);
			if(count<=0) throw new NotFoundException();

			this.updateNoticeFiles(srchNoticeVo, request);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	/**
	 * 공지사항 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchNoticeVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void deleteNotice(SrchNoticeVo srchNoticeVo) throws Exception {
		
		try {
			this.deleteNoticeFile(srchNoticeVo.getNoticeOid());
			
			int count = noticeMapper.deleteNotice(srchNoticeVo);
			if(count<=0) throw new NotFoundException();

		}catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 공지사항 파일 조회
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param oid
	 * @return
	 */
	@Transactional
	public NoticeFileVo getNoticeFile(long oid) {
		NoticeFileVo noticeFileVo = new NoticeFileVo();
		
		try {
			noticeFileVo = noticeFileMapper.selectNoticeFile(oid);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return noticeFileVo;
	}

	public void updateCnt(long noticeOid) throws Exception {
		
		try {
			int count = noticeMapper.updateCnt(noticeOid);
			if(count<=0) throw new NotFoundException();

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * <pre>공지사항 첨부파일 등록</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param noticeOid
	 * @param request
	 */
	public void createNoticeFile(long noticeOid, HttpServletRequest request) {
		SrchNoticeFileVo srchNoticeFileVo = new SrchNoticeFileVo();
		try {
			Map<String, Object> fileMap = fileService.uploadFile(request);
			
			List<Map<String, Object>> fileInfoList = (List<Map<String, Object>>) fileMap.get("fileInfoList");
			for(Map<String, Object> fileInfo : fileInfoList) {
				srchNoticeFileVo.setNoticefileOid(StringUtil.generateKey());
				srchNoticeFileVo.setNoticefileOrgNm((String) fileInfo.get("originalFileName"));
				srchNoticeFileVo.setNoticefileSavedNm((String) fileInfo.get("saveFileName"));
				srchNoticeFileVo.setNoticefileSavePath((String) fileInfo.get("savePath"));
				srchNoticeFileVo.setNoticefileSizeByte((int)(long)fileInfo.get("fileSize"));
				
				srchNoticeFileVo.setNoticeOid(noticeOid);
				int count = noticeFileMapper.insertNoticeFile(srchNoticeFileVo);
				if(count<=0) throw new GlobalException();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * <pre>공지사항 첨부파일 목록 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param noticeOid
	 * @return
	 */
	public List<NoticeFileVo> getNoticeFileList(long noticeOid) {
		List<NoticeFileVo> noticeFileList= new ArrayList<NoticeFileVo>();
		try {
			noticeFileList = noticeFileMapper.selectNoticeFileList(noticeOid);
		} catch (Exception e) {
		}
		return noticeFileList;
	}

	/**
	 * 
	 * <pre>공지사항 첨부파일 수정</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param srchNoticeVo
	 * @param request
	 * @param param
	 */
	public void updateNoticeFiles(SrchNoticeVo srchNoticeVo, HttpServletRequest request) {
		try {
			
			if(srchNoticeVo.getDeleteFileOids() != null && !srchNoticeVo.getDeleteFileOids().isEmpty()) {
				for(int i=0; i<srchNoticeVo.getDeleteFileOids().size(); i++) {
					long deleteFileOid = srchNoticeVo.getDeleteFileOids().get(i);
					NoticeFileVo noticeFileVo = noticeFileMapper.selectNoticeFile(deleteFileOid);
					
					if(noticeFileMapper.deleteNoticeFile(deleteFileOid) == 1) {
						Path filePath = Paths.get(noticeFileVo.getNoticefileSavePath(), noticeFileVo.getNoticefileSavedNm());
						fileService.deleteFile(filePath.normalize().toString());
					}
				}
			}
			
			this.createNoticeFile(srchNoticeVo.getNoticeOid(), request);
			
		} catch (Exception e) {
			throw new GlobalException(e);
		}
	}

	/**
	 * 
	 * <pre>공지사항 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param noticeOid
	 * @param fileId
	 * @param response
	 * @return
	 * @throws FileNotFoundException 
	 */
	public FileSystemResource downloadNoticeFile(long noticeOid, long fileId, HttpServletResponse response) throws FileNotFoundException {
		NoticeFileVo noticeFileVo = noticeFileMapper.selectNoticeFile(fileId);
		String saveFileName = noticeFileVo.getNoticefileSavedNm();
		String orgFileName = noticeFileVo.getNoticefileOrgNm();
		String filePath = noticeFileVo.getNoticefileSavePath();
		
		Path downloadPath = Paths.get(filePath).resolve(saveFileName);
		return fileService.downloadFile(orgFileName, downloadPath != null ? downloadPath.toString() : "", response);
	}

	/**
	 * 
	 * <pre>공지사항 첨부파일 삭제</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param fileId
	 */
	public void deleteNoticeFile(long noticeOid) {
		try {
			NoticeVo noticeVo = noticeMapper.selectNotice(noticeOid);
			
			if(noticeVo.getFileList().size() > 0) {
				List<NoticeFileVo> fileList = noticeVo.getFileList();
				for(NoticeFileVo noticeFile : fileList) {
					long deleteFileOid = noticeFile.getNoticefileOid();
					
					NoticeFileVo noticeFileVo= noticeFileMapper.selectNoticeFile(deleteFileOid);
					if(noticeFileMapper.deleteNoticeFile(deleteFileOid) == 1) {
						// 파일삭제
						Path filePath = Paths.get(noticeFileVo.getNoticefileSavePath(), noticeFileVo.getNoticefileSavedNm());
						fileService.deleteFile(filePath.normalize().toString());
					}
				}
			}
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		
	}
	
}
