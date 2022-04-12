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
package kr.co.n2m.smartcity.datapublish.feature.category.service;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartRequest;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.datapublish.common.component.CommonComponent;
import kr.co.n2m.smartcity.datapublish.common.exceptions.ConflictException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.common.vo.PageVo;
import kr.co.n2m.smartcity.datapublish.feature.category.mapper.CategoryMapper;
import kr.co.n2m.smartcity.datapublish.feature.category.vo.CategoryVo;
import kr.co.n2m.smartcity.datapublish.feature.category.vo.SrchCategoryVo;
import kr.co.n2m.smartcity.datapublish.feature.file.service.FileService;

@Service
public class CategoryService extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private FileService fileService;
	
	/**
	 * 카테고리 목록
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchCategoryVo
	 * @return
	 */
	@Transactional
	public Map<String,Object> getCategoryList(SrchCategoryVo srchCategoryVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			int totalListSize = categoryMapper.selectCategoryCount(srchCategoryVo);
			srchCategoryVo.setTotalListSize(totalListSize);
			
			List<CategoryVo> list = categoryMapper.selectCategoryList(srchCategoryVo);
			if (srchCategoryVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchCategoryVo);
				resMap.put("page", pageVo);
			} else {
				resMap.put("page", null);
			}
			resMap.put("list", list);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return resMap;
	}
	
	/**
	 * 카테고리 목록 카운트
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchCategoryVo
	 * @return
	 */
	@Transactional
	public int getCategoryCount(SrchCategoryVo srchCategoryVo) {
		int totalListSize;
		try {
			totalListSize = categoryMapper.selectCategoryCount(srchCategoryVo);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return totalListSize;
	}
	
	/**
	 * 카테고리 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchCategoryVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public String createCategory(SrchCategoryVo srchCategoryVo, HttpServletRequest request) {
		long id;
		try {
			srchCategoryVo.setCategoryOid(StringUtil.generateKey());
			id = srchCategoryVo.getCategoryOid();
			MultipartRequest multipart = (MultipartRequest) request;
			Iterator<String> it = multipart.getFileNames();
			
			if(it.hasNext()) {
				Map<String, Object> fileMap = fileService.uploadFile(request);
				List<Map<String, Object>> fileInfoList = (List<Map<String, Object>>) fileMap.get("fileInfoList");
				
				Map<String, Object> fileInfo = fileInfoList.get(0);
				srchCategoryVo.setCategoryImgOrgNm((String) fileInfo.get("originalFileName"));
				srchCategoryVo.setCategoryImgSaveNm((String) fileInfo.get("saveFileName"));
				srchCategoryVo.setCategoryImgSavePath((String) fileInfo.get("savePath"));
				srchCategoryVo.setCategoryImgSize((int)(long)fileInfo.get("fileSize"));
			}
			
			categoryMapper.insertCategory(srchCategoryVo);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return String.valueOf(id);
	}
	
	/**
	 * 카테고리 상세
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchCategoryVo
	 * @return
	 */
	public CategoryVo getCategory(long categoryOid) {
		return categoryMapper.selectCategory(categoryOid);
		
	}
	
	/**
	 * 카테고리 수정
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchCategoryVo
	 * @param req 
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyCategory(SrchCategoryVo srchCategoryVo, HttpServletRequest request) throws Exception {

		try {
			if(srchCategoryVo.getDeleteFileOids() != null && srchCategoryVo.getDeleteFileOids().size() > 0) {
				
				CategoryVo newCategoryVo = categoryMapper.selectCategory(srchCategoryVo.getCategoryOid());
				
				Path filePath = Paths.get(newCategoryVo.getCategoryImgSavePath(), newCategoryVo.getCategoryImgSaveNm());
				fileService.deleteFile(filePath.normalize().toString());
				
				srchCategoryVo.setCategoryImgOrgNm(null);
				srchCategoryVo.setCategoryImgSaveNm(null);
				srchCategoryVo.setCategoryImgSavePath(null);
				srchCategoryVo.setCategoryImgSize(0);
			}
			
			
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart) {
				MultipartRequest multipart = (MultipartRequest) request;
				Iterator<String> it = multipart.getFileNames();
				
				if(it.hasNext()) {
					Map<String, Object> fileMap = fileService.uploadFile(request);
					List<Map<String, Object>> fileInfoList = (List<Map<String, Object>>) fileMap.get("fileInfoList");
					
					for(Map<String, Object> fileInfo : fileInfoList) {
						srchCategoryVo.setCategoryImgOrgNm((String) fileInfo.get("originalFileName"));
						srchCategoryVo.setCategoryImgSaveNm((String) fileInfo.get("saveFileName"));
						srchCategoryVo.setCategoryImgSavePath((String) fileInfo.get("savePath"));
						srchCategoryVo.setCategoryImgSize((int)(long)fileInfo.get("fileSize"));
					}
				}
			}
			
			int count = categoryMapper.updateCategory(srchCategoryVo);
			if(count<=0) throw new NotFoundException();
		} catch (Exception e) {
			throw e;
		}
		
	}
	/**
	 * 카테고리 부분수정
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchCategoryVo
	 * @param req 
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void patchCategory(SrchCategoryVo srchCategoryVo, HttpServletRequest request) throws Exception {
		
		try {
			if(srchCategoryVo.getDeleteFileOids() != null && srchCategoryVo.getDeleteFileOids().size() > 0) {
				
				CategoryVo newCategoryVo = categoryMapper.selectCategory(srchCategoryVo.getCategoryOid());
				
				Path filePath = Paths.get(newCategoryVo.getCategoryImgSavePath(), newCategoryVo.getCategoryImgSaveNm());
				fileService.deleteFile(filePath.normalize().toString());
				
				srchCategoryVo.setCategoryImgOrgNm(null);
				srchCategoryVo.setCategoryImgSaveNm(null);
				srchCategoryVo.setCategoryImgSavePath(null);
				srchCategoryVo.setCategoryImgSize(0);
			}
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart) {
				MultipartRequest multipart = (MultipartRequest) request;
				Iterator<String> it = multipart.getFileNames();
				
				if(it.hasNext()) {
					Map<String, Object> fileMap = fileService.uploadFile(request);
					List<Map<String, Object>> fileInfoList = (List<Map<String, Object>>) fileMap.get("fileInfoList");
					
					for(Map<String, Object> fileInfo : fileInfoList) {
						srchCategoryVo.setCategoryImgOrgNm((String) fileInfo.get("originalFileName"));
						srchCategoryVo.setCategoryImgSaveNm((String) fileInfo.get("saveFileName"));
						srchCategoryVo.setCategoryImgSavePath((String) fileInfo.get("savePath"));
						srchCategoryVo.setCategoryImgSize((int)(long)fileInfo.get("fileSize"));
					}
				}
			}
			
			int count = categoryMapper.updateCategoryPart(srchCategoryVo);
			if(count<=0) throw new NotFoundException();
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	/**
	 * 카테고리 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchCategoryVo
	 * @return
	 * @throws Exception 
	 */
	public void deleteCategory(SrchCategoryVo srchCategoryVo) throws Exception {
		
		try {
			if(srchCategoryVo.getCategoryImgSize() > 0) {
				Path filePath = Paths.get(srchCategoryVo.getCategoryImgSavePath(), srchCategoryVo.getCategoryImgSaveNm());
				fileService.deleteFile(filePath.normalize().toString());
			}
			int count = categoryMapper.deleteCategory(srchCategoryVo);
			if(count > 0) throw new ConflictException();
		}catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 카테고리 대표이미지 조회
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 14.
	 * @param categoryOid
	 * @return
	 */
	public CategoryVo getCategoryImage(long categoryOid) {
		CategoryVo categoryVo = new CategoryVo();
		try {
			categoryVo = categoryMapper.selectCategory(categoryVo.getCategoryOid());
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return categoryVo;
		
	}

	

	/**
	 * 
	 * <pre>카테고리 대표이미지 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 22.
	 * @param categoryOid
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 */
	public FileSystemResource downloadCategoryFile(long categoryOid, HttpServletResponse response) throws FileNotFoundException {
		CategoryVo categoryVo = categoryMapper.selectCategory(categoryOid);
		String saveFileName = categoryVo.getCategoryImgSaveNm();
		String orgFileName = categoryVo.getCategoryImgOrgNm();
		String filePath = categoryVo.getCategoryImgSavePath();
		Path downloadPath = Paths.get(filePath).resolve(saveFileName);
		return fileService.downloadFile(orgFileName, downloadPath != null ? downloadPath.toString() : "", response);
	}

	
}
