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
package kr.co.n2m.smartcity.admin.feature.notice.vo;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.type.Alias;

import kr.co.n2m.smartcity.admin.feature.review.vo.ReviewFileVo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("noticeVo")
public class NoticeVo{
	private long noticeOid;
	private String noticeId;
	private String noticeTitle;
	private String noticeContent;
	private int noticeCnt; // 조회수
	private String noticeFirstFl; // 우선공지여부
	private String noticeAdminFl; // 관리자공지여부
	private String noticeMainPopFl; // 메인팝업여부
	private String noticeMainStartDt;
	private String noticeMainEndDt;
	private int noticeMainPopWidth;
	private int noticeMainPopHeight;
	private String noticeMainPopNotOpenFl; // 금일미공지여부
	private String noticeCreUsrId;
	private String noticeCreUsrNm; //joined 작성자 이름
	private LocalDateTime noticeCreDt; // 등록일시
	private String noticeUptUsrId;
	private LocalDateTime noticeUptDt; // 수정일시
	
	private int fileCnt; // 파일 갯수 (noticeList의 파일 유무 확인용)
	private List<NoticeFileVo> fileList;
	
}
