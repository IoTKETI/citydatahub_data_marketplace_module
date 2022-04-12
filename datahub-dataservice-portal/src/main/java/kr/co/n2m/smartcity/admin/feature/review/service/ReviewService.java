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
package kr.co.n2m.smartcity.admin.feature.review.service;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.n2m.smartcity.admin.common.component.CommonComponent;
import kr.co.n2m.smartcity.admin.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.admin.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.admin.common.service.FileService;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;
import kr.co.n2m.smartcity.admin.feature.review.mapper.ReviewFileMapper;
import kr.co.n2m.smartcity.admin.feature.review.mapper.ReviewMapper;
import kr.co.n2m.smartcity.admin.feature.review.vo.ReviewDatasetVo;
import kr.co.n2m.smartcity.admin.feature.review.vo.ReviewFileVo;
import kr.co.n2m.smartcity.admin.feature.review.vo.ReviewVo;
import kr.co.n2m.smartcity.admin.feature.review.vo.SrchReviewFileVo;
import kr.co.n2m.smartcity.admin.feature.review.vo.SrchReviewVo;

@Service
public class ReviewService extends CommonComponent {
	
	@Autowired
	private ReviewMapper reviewMapper;
	
	@Autowired
	private ReviewFileMapper reviewFileMapper;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 활용후기 목록
	 * @Author      : junheecho
	 * @Date        : 2019. 11. 04.
	 * @param srchReviewVo
	 * @return
	 */
	@Transactional
	public Map<String,Object> getReviewList(SrchReviewVo srchReviewVo) {
		Map<String, Object> resMap = new HashMap<>();
		
		try {
			int totalListSize = reviewMapper.selectReviewCount(srchReviewVo);
			srchReviewVo.setTotalListSize(totalListSize);
			
			List<ReviewVo> list = reviewMapper.selectReviewList(srchReviewVo);
			resMap.put("list", list);
			resMap.put("page", srchReviewVo);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return resMap;
	}
	
	/**
	 * 활용후기 목록 카운트
	 * @Author      : junheecho
	 * @Date        : 2019. 11. 04.
	 * @param srchReviewVo
	 * @return
	 */
	@Transactional
	public int getReviewCount(SrchReviewVo srchReviewVo) {
		int totalListSize;
		try {
			totalListSize = reviewMapper.selectReviewCount(srchReviewVo);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return totalListSize;
	}
	
	/**
	 * 활용후기 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 11. 04.
	 * @param srchReviewVo
	 * @param request
	 * @return
	 */
	@Transactional
	public String createReview(SrchReviewVo srchReviewVo, HttpServletRequest request) {
		long id;
		try {
			srchReviewVo.setReviewOid(StringUtil.generateKey());
			id = srchReviewVo.getReviewOid();
			
			int count = reviewMapper.insertReview(srchReviewVo);
			if(count<=0) throw new GlobalException();

			count = reviewMapper.insertReviewDatasetList(srchReviewVo);
			if(count<=0) throw new GlobalException();
			
			this.createReviewFile(id, request);
			
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return String.valueOf(id);
	}
	
	/**
	 * 활용후기 상세 조회 
	 * @Author      : junheecho
	 * @Date        : 2019. 11. 04.
	 * @param srchReviewVo
	 * @return
	 */
	@Transactional
	public Map<String, Object> getReview(long reviewOid) {
		Map<String, Object> resMap = new HashMap<>();
		
		try {
			ReviewVo reviewVo = reviewMapper.selectReview(reviewOid);
			
			List<ReviewDatasetVo> reviewDatasetList = reviewMapper.selectReviewDatasetList(reviewOid);
			
			resMap.put("review", reviewVo);
			resMap.put("reviewDatasetList", reviewDatasetList);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		
		return resMap;
	}
	
	/**
	 * 활용후기 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 11. 04.
	 * @param srchReviewVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void deleteReview(SrchReviewVo srchReviewVo) throws Exception {
		
		try {
			
			this.deleteReviewFile(srchReviewVo.getReviewOid());
			
			int count = reviewMapper.deleteReviewDatasetList(srchReviewVo);
			if(count<=0) throw new NotFoundException();
			
			count  = reviewMapper.deleteReview(srchReviewVo);
			if(count<=0) throw new NotFoundException();

			
		}catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 활용후기 수정 
	 * @Author      : junheecho
	 * @Date        : 2019. 11. 04.
	 * @param srchReviewVo
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyReview(SrchReviewVo srchReviewVo, HttpServletRequest request) throws Exception {
		
		try {
			
			int count = reviewMapper.updateReview(srchReviewVo);
			if(count<=0) throw new NotFoundException();
			
			count = reviewMapper.deleteReviewDatasetList(srchReviewVo);
			if(count<=0) throw new NotFoundException();
			
			count = reviewMapper.insertReviewDatasetList(srchReviewVo);
			if (count<=0) throw new GlobalException();
			
			this.updateReviewFiles(srchReviewVo, request);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	

	/**
	 * 활용후기 파일 조회
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchReviewFileVo
	 * @return
	 */
	public ReviewFileVo getReviewFile(long reviewFileOid) {
		ReviewFileVo fileVo = new ReviewFileVo();
		
		try {
			fileVo = reviewFileMapper.selectReviewFile(reviewFileOid);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		
		return fileVo;
	}

	/**
	 * 
	 * <pre>활용후기 첨부파일 등록</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param reviewOid
	 * @param request
	 */
	@Transactional
	public void createReviewFile(long reviewOid, HttpServletRequest request) {
		SrchReviewFileVo srchReviewFileVo = new SrchReviewFileVo();
		try {
			Map<String, Object> fileMap = fileService.uploadFile(request);
			List<Map<String, Object>> fileInfoList = (List<Map<String, Object>>) fileMap.get("fileInfoList");
			
			for(Map<String,Object> fileInfo : fileInfoList) {
				
				srchReviewFileVo.setReviewfileOid(StringUtil.generateKey());
				srchReviewFileVo.setReviewOid(reviewOid);
				srchReviewFileVo.setReviewfileFlag((String) fileInfo.get("fileTypeFlag"));
				srchReviewFileVo.setReviewfileLscName((String) fileInfo.get("saveFileName")); 
				srchReviewFileVo.setReviewfilePscName((String) fileInfo.get("originalFileName"));
				srchReviewFileVo.setReviewfileFilePath((String) fileInfo.get("savePath"));
				srchReviewFileVo.setReviewfileFileSize((int)(long)fileInfo.get("fileSize"));
				int count = reviewFileMapper.insertReviewFile(srchReviewFileVo);
				if(count<=0) throw new GlobalException();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 
	 * <pre>활용후기 첨부파일 목록 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param reviewOid
	 * @return
	 */
	public List<ReviewFileVo> getReviewFileList(long reviewOid) {
		List<ReviewFileVo> reviewFileList = new ArrayList<ReviewFileVo>();
		try {
			reviewFileList = reviewFileMapper.selectReviewFileList(reviewOid);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return reviewFileList;
	}

	/**
	 * 
	 * <pre>활용후기 첨부파일 수정</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param srchReviewVo
	 * @param request
	 * @param param
	 */
	@Transactional
	public void updateReviewFiles(SrchReviewVo srchReviewVo, HttpServletRequest request) {

		try {
			
			if(srchReviewVo.getDeleteFileOids()!= null && !srchReviewVo.getDeleteFileOids().isEmpty()) {
				
				for(int i=0; i <srchReviewVo.getDeleteFileOids().size(); i++) {
					
					long deleteFileOid = srchReviewVo.getDeleteFileOids().get(i);
					ReviewFileVo reviewFileVo = reviewFileMapper.selectReviewFile(deleteFileOid);
					if(reviewFileMapper.deleteReviewFile(deleteFileOid) == 1) {
						// 파일삭제
						Path filePath = Paths.get(reviewFileVo.getReviewfileFilePath(), reviewFileVo.getReviewfileLscName());
						fileService.deleteFile(filePath.normalize().toString());
					}
				}
			}
			
			this.createReviewFile(srchReviewVo.getReviewOid(), request);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new GlobalException(e);
		}
	}

	/**
	 * 
	 * <pre>활용후기 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param reviewOid
	 * @param fileId
	 * @param response
	 * @return
	 * @throws FileNotFoundException 
	 */
	public FileSystemResource downloadReviewFile(long reviewOid, long fileId, HttpServletResponse response) throws FileNotFoundException {
		
		ReviewFileVo reviewFileVo = new ReviewFileVo();
		reviewFileVo.setReviewOid(reviewOid);
		reviewFileVo.setReviewfileOid(fileId);
		reviewFileVo = reviewFileMapper.selectReviewFile(fileId);
		
		String saveFileName = reviewFileVo.getReviewfileLscName();
		String orgFileName = reviewFileVo.getReviewfilePscName();
		String filePath = reviewFileVo.getReviewfileFilePath();
		Path downloadPath = Paths.get(filePath).resolve(saveFileName);
		
		return fileService.downloadFile(orgFileName, downloadPath != null ? downloadPath.toString() : "", response);
	}
	

	/**
	 * 
	 * <pre>활용후기 첨부파일 삭제</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param reviewOid
	 * @throws Exception 
	 */
	@Transactional
	public void deleteReviewFile(long reviewOid) throws Exception {
		try {
			
			ReviewFileVo reviewFileVo = new ReviewFileVo();
			ReviewVo reviewVo = reviewMapper.selectReview(reviewOid);
			
			long id = reviewVo.getReviewfileOid();
			
			if(reviewVo.getFileList().size() > 0) {

				ReviewFileVo rvo = new ReviewFileVo();
				rvo.setReviewfileOid(id);
				
				List<ReviewFileVo> fileList = reviewVo.getFileList();
				fileList.add(rvo);
				
				for(ReviewFileVo reviewFile : fileList) {
					long deleteFileOid = reviewFile.getReviewfileOid();
					
					reviewFileVo = reviewFileMapper.selectReviewFile(deleteFileOid);
					if(reviewFileMapper.deleteReviewFile(deleteFileOid) == 1) {
						// 파일삭제
						Path filePath = Paths.get(reviewFileVo.getReviewfileFilePath(), reviewFileVo.getReviewfileLscName());
						fileService.deleteFile(filePath.normalize().toString());
					}
				}
			}

		} catch (Exception e) {
			throw e;
		}
		
	}
}
