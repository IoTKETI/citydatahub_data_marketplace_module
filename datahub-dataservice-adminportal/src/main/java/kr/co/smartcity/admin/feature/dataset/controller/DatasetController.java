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
package kr.co.smartcity.admin.feature.dataset.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smartcity.admin.common.CommonConst;
import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.feature.dataset.service.DatasetService;
import kr.co.smartcity.admin.feature.dataset.vo.DatasetSatisfactionRatingVo;
import kr.co.smartcity.admin.feature.dataset.vo.DatasetUseRequestVo;
import kr.co.smartcity.admin.feature.dataset.vo.DatasetVo;

@Controller
@RequestMapping("/dataset")
public class DatasetController {

	@Resource(name="datasetService")
	private DatasetService datasetService;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setAutoGrowNestedPaths(true); // insert this line
        binder.setAutoGrowCollectionLimit(5000); // insert thist line
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	
	/**
	 * ???????????? ?????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/pageList.do", produces="text/plain;charset=UTF-8")
	public String goPageDatasetList(Model model) throws Exception {
		model.addAttribute("searchInfo", HttpComponent.getSessionAttribute(CommonConst.SEARCH_PREFIX, "DATASET_LIST"));
		model.addAttribute("endpoint", datasetService.getDatasetEndPoint());
		return "dataset/datasetList";
	}
	
	/**
	 * ???????????? ?????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/pageDetail.do", produces="text/plain;charset=UTF-8")
	public String goPageDatasetDetail(DatasetVo datasetVo, Model model) throws Exception {
		model.addAttribute("endpoint", datasetService.getDatasetEndPoint());
		return "dataset/datasetDetail";
	}
	
	/**
	 * ???????????? ?????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @return
	 */
	@RequestMapping(value="/pageEdit.do", produces="text/plain;charset=UTF-8")
	public String goPageDatasetRegist() {
		return "dataset/datasetRegist";
	}
	
	/**
	 * ????????? ????????? ?????? ?????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 17.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageModify.do", produces="text/plain;charset=UTF-8")
	public String datasetPageModify(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "dataset/datasetModify";
	}
	
	/**
	 * ???????????? ?????? ????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String  getList(@RequestParam Map<String, Object> params) throws Exception {
		return datasetService.getDatasetList(params);
	}
	
	/**
	 * ???????????? ????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 8.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/get.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getDataset(@RequestParam Map<String, Object> params) throws Exception {
		return datasetService.getDataset(params);
	}
	
	/**
	 * ???????????? ????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@PostMapping(value="/create.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String createDataset(DatasetVo datasetVo, HttpServletRequest req) throws Exception {
		return datasetService.createDataset(datasetVo, req);
	}
	
	/**
	 * ???????????? ????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String modifyDataset(DatasetVo datasetVo, HttpServletRequest req) throws Exception {
		return datasetService.modifyDataset(datasetVo, req);
	}
	
	/**
	 * <pre>???????????? ????????? ????????????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 29.
	 * @param datasetVo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@PatchMapping(value = "/modify.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String patchDataset(@RequestBody DatasetVo datasetVo) throws Exception {
		return datasetService.patchDataset(datasetVo);
	}
	
	/**
	 * ???????????? ???????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value="/wishlist/create.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String wishlistCreate(@RequestBody  Map<String, Object> params, HttpServletRequest request) throws Exception {
		return datasetService.createDatasetWishlist(params, request);
	}
	
	/**
	 * ???????????? ???????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping(value="/wishlist/remove.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String wishlistRemove(@RequestBody  Map<String, Object> params, HttpServletRequest request) throws Exception {
		return datasetService.deleteDatasetWishlist(params, request);
	}
	
	/**
	 * ???????????? ???????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value="/qna/create.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String qnaCreate(@RequestBody Map<String, Object> params) throws Exception {
		return datasetService.createQna(params);
	}
	
	/**
	 * ???????????? ???????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@PutMapping(value="/qna/modify.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String qnaModify(@RequestBody Map<String, Object> params) throws Exception {
		return datasetService.modifyQna(params);
	}
	
	/**
	 * <pre>???????????? ?????? ??????</pre>
	 * @Author      : thlee
	 * @Date        : 2019. 10. 17.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getCategoryList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getCategoryList(@RequestParam Map<String, Object> params) throws Exception {
		return datasetService.getCategoryList(params);
	}
	
	/**
	 * ????????? ?????? ????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 1.
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="/model/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getDataModelList(DatasetVo datasetVo) throws Exception {
		return datasetService.getDataModelList(datasetVo); 
	}
	
	/**
	 * ????????? ???????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 1.
	 * @param dsvModelId
	 * @param datasetVo
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="/instance/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getDataInstanceList(DatasetVo datasetVo) throws Exception {
		return datasetService.getDataInstanceList(datasetVo); 
	}
	
	/**
	 * ???????????? ???????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value="/use/create.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String useCreate(@RequestBody Map<String, Object> params, HttpServletRequest req) throws Exception {
		return datasetService.createDatasetUsage(params, req);
	}
	
	/**
	 * ???????????? ???????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 23.
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping(value="/use/remove.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String useRemove(@RequestBody DatasetUseRequestVo DatasetUseRequestVo) throws Exception {
		return datasetService.deleteDatasetUsage(DatasetUseRequestVo);
	}
	
	/**
	 * <pre>????????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 7.
	 * @param datasetVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/satisfaction/create.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String createDatasetSatisfaction(@RequestBody DatasetSatisfactionRatingVo datasetSatisfactionRatingVo) throws Exception {
		return datasetService.createDatasetSatisfaction(datasetSatisfactionRatingVo);
	}
	
	/**
	 * <pre>????????? ?????? ??????</pre>
	 * @Author      : thlee
	 * @Date        : 2019. 10. 30.
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/sample/get.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getSampleData(@RequestParam Map<String, Object> params) throws Exception {
		return datasetService.getSampleData(params);
	}
	
	/**
	 * <pre>?????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2019. 11. 6.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/user/getList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String getUserList(@RequestParam Map<String, Object> params) throws Exception {
		return datasetService.getUserList(params);
	}
	
	/**
	 * <pre>???????????? ????????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 10. 27.
	 * @param datasetVo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/datasetorigin/getList.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getDatasetOriginList(DatasetVo datasetVo) throws Exception {
		return datasetService.getDatasetOriginList(datasetVo); 
	}
}
