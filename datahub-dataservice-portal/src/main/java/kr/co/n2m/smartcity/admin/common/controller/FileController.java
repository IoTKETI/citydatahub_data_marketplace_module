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
package kr.co.n2m.smartcity.admin.common.controller;

import java.io.IOException;
import java.nio.file.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.admin.common.component.CommonComponent;
import kr.co.n2m.smartcity.admin.common.service.FileService;
import kr.co.n2m.smartcity.admin.feature.datacomplaint.service.DatacomplaintService;
import kr.co.n2m.smartcity.admin.feature.faq.service.FaqService;
import kr.co.n2m.smartcity.admin.feature.notice.service.NoticeService;
import kr.co.n2m.smartcity.admin.feature.qna.service.QnaService;
import kr.co.n2m.smartcity.admin.feature.review.service.ReviewService;

@RestController
public class FileController extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileService fileService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private FaqService faqService;
	@Autowired
	private DatacomplaintService datacomplaintService;
	@Autowired
	private QnaService qnaService;
	@Autowired
	private ReviewService reviewService;

	@PostMapping("/dataserviceUi/upload")
	public Object upload(HttpServletRequest request) {
		return fileService.uploadFile(request);
	}
	
//	@GetMapping("/dataserviceUi/review/{oid}/attachFiles/{fileOid}")
//	public FileSystemResource reviewDownload(@PathVariable long oid, @PathVariable long fileOid, HttpServletResponse response) throws IOException {
//		Path downloadPath = null;
//		String saveFileName = "";
//		String orgFileName = "";
//		String filePath = "";
//		
//		ReviewFileVo reviewFileVo = new ReviewFileVo();
//		SrchReviewFileVo srchReviewFileVo = new SrchReviewFileVo();
//		reviewFileVo.setReviewOid(oid);
//		reviewFileVo.setReviewfileOid(fileOid);
//		reviewFileVo = (ReviewFileVo)reviewService.getReviewFile(srchReviewFileVo);
//		
//		saveFileName = reviewFileVo.getReviewfileLscName();
//		orgFileName = reviewFileVo.getReviewfilePscName();
//		filePath = reviewFileVo.getReviewfileFilePath();
//		downloadPath = Paths.get(filePath).resolve(saveFileName);
//		
//		return fileService.downloadFile(orgFileName, downloadPath != null ? downloadPath.toString() : "", response);
//	}
	
	@GetMapping("/dataserviceUi/attachFiles/{menu}/{oid}")
	public FileSystemResource upload(@PathVariable String menu, @PathVariable long oid, HttpServletResponse response) throws IOException {
		Path downloadPath = null;
		String saveFileName = "";
		String orgFileName = "";
		String filePath = "";
		
		//TODO: 공지사항, QNA, FAQ 에 따라 다운로드할 파일 가져오기
		switch(menu) {
//		case "qna":
//			QnaFileVo qnaFileVo = (QnaFileVo) qnaService.getQnaFile(oid);
//			saveFileName = qnaFileVo.getQnafileSaveNm();
//			orgFileName = qnaFileVo.getQnafileOrgNm();
//			filePath = qnaFileVo.getQnafileSavePath();
//			downloadPath = Paths.get(filePath).resolve(saveFileName);
//			break;
//		case "faq":
//			FaqFileVo faqFileVo = (FaqFileVo)faqService.getFaqFile(oid);
//			saveFileName = faqFileVo.getFaqfileSavedNm();
//			orgFileName = faqFileVo.getFaqfileOrgNm();
//			filePath = faqFileVo.getFaqfileSavePath();
//			downloadPath = Paths.get(filePath).resolve(saveFileName);
//			break;
//		case "notice":
//			NoticeFileVo noticeFileVo = (NoticeFileVo) noticeService.getNoticeFile(oid);
//			saveFileName = noticeFileVo.getNoticefileSavedNm();
//			orgFileName = noticeFileVo.getNoticefileOrgNm();
//			filePath = noticeFileVo.getNoticefileSavePath();
//			downloadPath = Paths.get(filePath).resolve(saveFileName);
//			break;
//		case "datacomplaint":
//			DatacomplaintFileVo datacomplaintFileVo = (DatacomplaintFileVo)datacomplaintService.getDatacomplaintFile(oid);
//			saveFileName = datacomplaintFileVo.getDcpFileSavedNm();
//			orgFileName = datacomplaintFileVo.getDcpFileOrgNm();
//			filePath = datacomplaintFileVo.getDcpFileSavePath();
//			downloadPath = Paths.get(filePath).resolve(saveFileName);
//			break;
//		case "category":
//			CategoryVo categoryVo = (CategoryVo) categoryService.getCategory(oid);
//			saveFileName = categoryVo.getCategoryImgSaveNm();
//			orgFileName = categoryVo.getCategoryImgOrgNm();
//			filePath = categoryVo.getCategoryImgSavePath();
//			downloadPath = Paths.get(filePath).resolve(saveFileName);
//			break;
		}
		
		return null;
//		return fileService.downloadFile(orgFileName, downloadPath.toString(), response);
	}
	
}
