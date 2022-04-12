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
package kr.co.n2m.smartcity.datapublish.feature.category.controller;

import java.io.FileNotFoundException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.datapublish.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.category.service.CategoryService;
import kr.co.n2m.smartcity.datapublish.feature.category.vo.CategoryVo;
import kr.co.n2m.smartcity.datapublish.feature.category.vo.SrchCategoryVo;

@RestController
public class CategoryController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 카테고리 목록
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchCategoryVo
	 * @return
	 */
	@GetMapping("/dataservice/datasetCategories")
	public ResponseEntity<Object> getCategoryList(SrchCategoryVo srchCategoryVo) {
		Map<String,Object> resMap = categoryService.getCategoryList(srchCategoryVo);
		return ResponseUtil.makeResponseEntity(resMap);
	}
	
	/**
	 * 카테고리 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchCategoryVo
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/dataservice/datasetCategories")
	public ResponseEntity<Object> createCategory(SrchCategoryVo srchCategoryVo, HttpServletRequest req, @RequestHeader("UserId") String categoryCreUsrId) throws Exception {
		srchCategoryVo.setCategoryCreUsrId(categoryCreUsrId);
		String vo = categoryService.createCategory(srchCategoryVo, req);
		return ResponseUtil.makeResponseEntity(vo,HttpStatus.CREATED,"/dataservice/datasetCategories"+vo);
	}
	
	/**
	 * 카테고리 상세
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param categoryOid
	 * @param srchCategoryVo
	 * @return
	 */
	@GetMapping("/dataservice/datasetCategories/{categoryOid}")
	public ResponseEntity<Object> getCategory(@PathVariable long categoryOid) {
		CategoryVo categoryVo = categoryService.getCategory(categoryOid);
		return ResponseUtil.makeResponseEntity(categoryVo);
	}
	
	/**
	 * 카테고리 수정
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchCategoryVo
	 * @return
	 * @throws Exception 
	 */
	@PutMapping("/dataservice/datasetCategories/{categoryOid}")
	public ResponseEntity<Object> modifyCategory(@PathVariable long categoryOid, SrchCategoryVo srchCategoryVo, HttpServletRequest req, @RequestHeader("UserId") String categoryUptUsrId) throws Exception {
		StringUtil.compareUniqueId(categoryOid, srchCategoryVo.getCategoryOid());
		srchCategoryVo.setCategoryUptUsrId(categoryUptUsrId);
		categoryService.modifyCategory(srchCategoryVo, req);
		return ResponseUtil.makeResponseEntity(null);
	}
	/**
	 * 카테고리 부분수정
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchCategoryVo
	 * @return
	 * @throws Exception 
	 */
	@PatchMapping("/dataservice/datasetCategories/{categoryOid}")
	public ResponseEntity<Object> patchCategory(@PathVariable long categoryOid,SrchCategoryVo srchCategoryVo, HttpServletRequest req, @RequestHeader("UserId") String categoryUptUsrId) throws Exception {
		StringUtil.compareUniqueId(categoryOid, srchCategoryVo.getCategoryOid());
		srchCategoryVo.setCategoryUptUsrId(categoryUptUsrId);
		categoryService.patchCategory(srchCategoryVo, req);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	/**
	 * 카테고리 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 7. 24.
	 * @param srchCategoryVo
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping("/dataservice/datasetCategories/{categoryOid}")
	public ResponseEntity<Object> deleteCategory(@PathVariable String categoryOid,@RequestBody SrchCategoryVo srchCategoryVo) throws Exception {
		categoryService.deleteCategory(srchCategoryVo);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}
	
	
	/**
	 * 
	 * <pre>카테고리 이미지 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 22.
	 * @param categoryOid
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 */
	@GetMapping("/dataserviceUi/datasetCategories/{categoryOid}/image")
	public FileSystemResource downloadCategoryFile(@PathVariable long categoryOid, HttpServletResponse response) throws FileNotFoundException {
		return categoryService.downloadCategoryFile(categoryOid,response);
	}
	
	
}
