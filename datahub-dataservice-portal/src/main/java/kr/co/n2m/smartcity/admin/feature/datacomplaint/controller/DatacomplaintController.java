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
package kr.co.n2m.smartcity.admin.feature.datacomplaint.controller;

import java.io.FileNotFoundException;
import java.util.List;
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
import kr.co.n2m.smartcity.admin.common.component.CommonComponent;
import kr.co.n2m.smartcity.admin.common.exceptions.ClientException;
import kr.co.n2m.smartcity.admin.common.utils.ResponseUtil;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;
import kr.co.n2m.smartcity.admin.feature.datacomplaint.service.DatacomplaintService;
import kr.co.n2m.smartcity.admin.feature.datacomplaint.vo.DatacomplaintFileVo;
import kr.co.n2m.smartcity.admin.feature.datacomplaint.vo.DatacomplaintVo;
import kr.co.n2m.smartcity.admin.feature.datacomplaint.vo.SrchDatacomplaintVo;


@RestController
public class DatacomplaintController extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DatacomplaintService datacomplaintService;
	/**
	 *데이터 민원 목록 조회
	 * @param datacomplaintVo
	 * @return
	 */
	
	@GetMapping("/dataserviceUi/complaints")
	public ResponseEntity<Object> getDatacomplaintList(SrchDatacomplaintVo srchDatacomplaintVo) {
		Map<String,Object> resMap =  datacomplaintService.getDatacomplaintList(srchDatacomplaintVo);
		return ResponseUtil.makeResponseEntity(resMap,HttpStatus.OK);
	}
	/**
	 * 데이터 민원 등록
	 * @param datacomplaintVo
	 * @return
	 */
	
	@PostMapping("/dataserviceUi/complaints")
	public ResponseEntity<Object> createDatacomplaint(@RequestHeader("UserId") String datacomplainCreUsrId,SrchDatacomplaintVo srchDatacomplaintVo,  HttpServletRequest request) {
		srchDatacomplaintVo.setDatacomplainCreUsrId(datacomplainCreUsrId);
		String vo =  datacomplaintService.createDatacomplaint(srchDatacomplaintVo, request);
		return ResponseUtil.makeResponseEntity(vo,HttpStatus.CREATED,"/dataserviceUi/complaints"+vo);
	}
	
	/**
	 * 데이터 민원 정보 조회
	 * @param datacomplaintVo
	 * @return
	 * @throws Exception 
	 */
	
	@GetMapping("/dataserviceUi/complaints/{datacomplainOid}")
	public ResponseEntity<Object> getDatacomplaintList(@PathVariable long datacomplainOid, String nohit) throws Exception {
		DatacomplaintVo datacomplaintVo = datacomplaintService.getDatacomplaint(datacomplainOid,"yes".equalsIgnoreCase(nohit));
		return ResponseUtil.makeResponseEntity(datacomplaintVo);
	}
	
	/**
	 * 
	 * <pre>데이터 민원 수정(신고)</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param datacomplainOid
	 * @param srchDatacomplaintVo
	 * @param request
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@PutMapping("/dataserviceUi/complaints/{datacomplainOid}")
	public ResponseEntity<Object> modifyDatacomplaint(@PathVariable String datacomplainOid, SrchDatacomplaintVo srchDatacomplaintVo, HttpServletRequest request) throws Exception {
		StringUtil.compareUniqueId(datacomplainOid, String.valueOf(srchDatacomplaintVo.getDatacomplainOid()));
		datacomplaintService.modifyDatacomplaint(srchDatacomplaintVo, request);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	/**
	 * 
	 * <pre>데이터 민원 수정(답글)</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 16.
	 * @param datacomplainOid
	 * @param datacomplainAnswerUsrId
	 * @param srchDatacomplaintVo
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@PatchMapping("/dataserviceUi/complaints/{datacomplainOid}")
	public ResponseEntity<Object> modifyDatacomplaintPart(@RequestHeader("UserId") String loginUserId, @PathVariable String datacomplainOid, SrchDatacomplaintVo srchDatacomplaintVo, HttpServletRequest request) throws Exception{
		StringUtil.compareUniqueId(datacomplainOid, String.valueOf(srchDatacomplaintVo.getDatacomplainOid()));
		srchDatacomplaintVo.setLoginUserId(loginUserId);
		datacomplaintService.modifyDatacomplaintPart(srchDatacomplaintVo, request);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	/**
	 * 데이터 민원 삭제
	 * @param datacomplaintVo
	 * @return
	 * @throws Exception 
	 */
	
	@DeleteMapping("/dataserviceUi/complaints/{datacomplainOid}")
	public ResponseEntity<Object> deleteDatacomplaint(@PathVariable String datacomplainOid, @RequestBody SrchDatacomplaintVo srchDatacomplaintVo) throws Exception {
		datacomplaintService.deleteDatacomplaint(srchDatacomplaintVo);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}
	
	/**
	 * 
	 * <pre>데이터 민원 첨부파일 등록</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @return
	 */
	@PostMapping("/dataserviceUi/complaints/{datacomplainOid}/attachFiles")
	public ResponseEntity<Object> createDatacomplaintFile(@PathVariable long datacomplainOid, HttpServletRequest request){
		datacomplaintService.createDatacomplaintFile(datacomplainOid,request);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.CREATED,"/dataserviceUi/complaints/"+datacomplainOid+"/attachFiles");
	}
	
	/**
	 * 
	 * <pre>데이터 민원 첨부파일 목록 조회</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param datacomplainOid
	 * @return
	 */
	@GetMapping("/dataserviceUi/complaints/{datacomplainOid}/attachFiles")
	public ResponseEntity<Object> getDatacomplaintFileList(@PathVariable long datacomplainOid){
		List<DatacomplaintFileVo> datacomplaintFileList = datacomplaintService.getDatacomplaintFileList(datacomplainOid);
		return ResponseUtil.makeResponseEntity(datacomplaintFileList);
	}
	
	/**
	 * 
	 * <pre>데이터 민원 첨부파일 수정</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @return
	 * @throws ClientException 
	 */
	@PutMapping("/dataserviceUi/complaints/{datacomplainOid}/attachFiles/{fileId}")
	public ResponseEntity<Object> updateDatacomplaintFile(@PathVariable long datacomplainOid, @PathVariable long fileId, HttpServletRequest request,SrchDatacomplaintVo srchDatacomplaintVo) throws ClientException{
		StringUtil.compareUniqueId(datacomplainOid, srchDatacomplaintVo.getDatacomplainOid());
		datacomplaintService.updateDatacomplaintFiles(srchDatacomplaintVo, request);
		return ResponseUtil.makeResponseEntity(null);
	}
	
	/**
	 * 
	 * <pre>데이터 민원 첨부파일 다운로드</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param qnaOid
	 * @param fileId
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 */
	@GetMapping("/dataserviceUi/complaints/{datacomplainOid}/attachFiles/{fileId}")
	public FileSystemResource downloadDatacomplaintFile(@PathVariable long datacomplainOid, @PathVariable long fileId,HttpServletResponse response) throws FileNotFoundException {
		return datacomplaintService.downloadDatacomplaintFile(datacomplainOid,fileId,response);
	}
	
	/**
	 * 
	 * <pre>데이터 민원 첨부파일 삭제</pre>
	 * @Author        : areum
	 * @Date        : 2020. 7. 17.
	 * @param qnaOid
	 * @param fileId
	 * @param srchQnaVo
	 * @return
	 * @throws ClientException
	 * @throws FileNotFoundException
	 */
	@DeleteMapping("/dataserviceUi/complaints/{datacomplainOid}/attachFiles/{fileId}")
	public ResponseEntity<Object> deleteDatacomplaintFile(@PathVariable long datacomplainOid, @PathVariable long fileId,@RequestBody SrchDatacomplaintVo srchDatacomplaintVo) throws ClientException, FileNotFoundException{
		datacomplaintService.deleteDatacomplaintFile(fileId);
		return ResponseUtil.makeResponseEntity(null,HttpStatus.NO_CONTENT);
	}
}
