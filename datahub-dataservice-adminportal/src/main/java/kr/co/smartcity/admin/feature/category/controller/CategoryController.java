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
package kr.co.smartcity.admin.feature.category.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.admin.feature.category.service.CategoryService;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {
	
	@Resource(name = "categoryService")
	private CategoryService categoryService;
	
	/**
	 * <pre>카테고리 목록 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageList.do")
	public String goPageNoticeList() {
		return "category/categoryList";
	}
	/**
	 * <pre>카테고리 수정 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageModify.do")
	public String goPageNoticeModify() {
		return "category/categoryModify";
	}
	/**
	 * <pre>카테고리 등록 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageEdit.do")
	public String goPageNoticeRegist() {
		return "category/categoryRegist";
	}
	/**
	 * <pre>카테고리 상세 페이지 이동</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @return
	 */
	@RequestMapping(value = "/pageDetail.do")
	public String goPageNoticeDetail() {
		return "category/categoryDetail";
	}
	
	/**
	 * <pre>카테고리 생성</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/create.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String createCategory(@RequestParam Map<String, Object> params, HttpServletRequest req) throws Exception {
		return categoryService.createCategory(params, req);
	}
	
	/**
	 * <pre>카테고리 목록 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getCategoryList(@RequestParam Map<String, Object> params) throws Exception {
		return categoryService.getCategoryList(params);
	}
	
	/**
	 * <pre>카테고리 상세 조회</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/get.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getCategory(@RequestParam Map<String, Object> params) throws Exception {
		return categoryService.getCategory(params);
	}
	
	/**
	 * <pre>카테고리 수정</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String modifyCategory(@RequestParam Map<String, Object> params, HttpServletRequest req) throws Exception {
		return categoryService.modifyCategory(params, req);
	}
	
	/**
	 * <pre>카테고리 삭제</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 12.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/remove.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String deleteCategory(@RequestBody Map<String, Object> params) throws Exception {
		return categoryService.deleteCategory(params);
	}
	
	/**
	 * 
	 * <pre>카테고리 이미지 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 22.
	 * @param params
	 * @param res
	 */
	@RequestMapping(value = "/downloadFile.do", produces="text/plain;charset=UTF-8")
	public void downloadQnaFile(@RequestParam Map<String, Object> params, HttpServletResponse res) {
		categoryService.downloadQnaFile(params, res);
	}
	
}
