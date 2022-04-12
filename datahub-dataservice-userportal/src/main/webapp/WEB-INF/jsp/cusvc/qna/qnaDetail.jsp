<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
    <div id="content" v-cloak>
    <h2 class="hidden">본문</h2>
      <!-- sub-cover -->
      <section class="sub-cover">
        <h3 v-if ="backUrl != 'mypage'" class="sub-cover__title material-icons">고객 서비스 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
      	<h3 v-if ="backUrl == 'mypage'" class="sub-cover__title sub-cover__title--data material-icons">마이페이지 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
      </section>
      <hr>
       <!-- sub-nav -->
     ${lnbHtml}
        <!-- sub-nav -->
    <hr>
    <div class="sub__container">
   <%@ include file="/WEB-INF/jsp/tiles/tiles_breadcrumb.jspf" %>
    <h3 class="sub__heading1">묻고답하기</h3>
  <div class="sub__content">
    <form>
      <fieldset>
      <h4 class="sub__heading2">묻고답하기 상세</h4>
      <table class="detail-table">
          <caption>데이터 분석가 요청 테이블</caption>
          <colgroup>
            <col width="10%">
            <col width="*">
            <col width="10%">
            <col width="*">
          </colgroup>
          <tbody>
                <tr>
                  <th>번호</th>
                  <td colspan="3">
                    {{qna.qnaId}}
                  </td>
                </tr>
                <tr>
                  <th>등록자(ID)</th>
                  <td>
                    {{qna.qnaQuestionUsrId}}
                  </td>
                  <th>등록일시</th>
                  <td>
                    {{qna.qnaQuestionDt|date}}
                  </td>
                </tr>
                <tr>
                  <th>질문유형</th>
                  <td colspan="1">
                    {{qna.qnaQuestionTypeNm}}
                  </td>
                  <th>조회수</th>
                  <td colspan="1">
                    {{qna.qnaCnt}}
                  </td>
                </tr>
                <tr>
                  <th>진행상태</th>
                  <td colspan="2">
                    {{qna.qnaStatusNm}}
                  </td>
                </tr>
                <tr>
                  <th>제목</th>
                  <td colspan="3">
                    {{qna.qnaTitle}}
                  </td>
                </tr>
                <tr>
                  <th>내용</th>
                  <td colspan="3" v-html="$options.filters.enter(qna.qnaQuestion)">
                  </td>
                </tr>
                <tr>
                <th scope="row">첨부파일</th>
                <td colspan="3">
                  <ul class="attach-file__list">
                    <li class="attach-file__item--none" v-if="qna.fileList.length === 0">첨부파일이 없습니다.</li>
                    <li class="attach-file__item material-icons"  v-for="(file, index) in qna.fileList">
                    <a @click="download(file.qnafileOid)">
                        {{file.qnafileOrgNm}}<span class="attach-file__item--size">({{file.qnafileSize | size}})</span>
                        <button type="button" class="attach-file__button--delete material-icons" title="파일 삭제"><span class="hidden">파일 삭제</span></button>
                    </a>
                    </li>
                  </ul>
                </td>
              </tr>
              </tbody>
        </table>
      </fieldset>
    </form>
    </div>
    
    <div class="sub__bottom">
      <div class="button__group">
        <a class="button button__secondary" v-if ="loginUserId == qna.qnaQuestionUsrId && qna.qnaStatus == 'answerWait' && backUrl != 'mypage'" href="<c:url value="/cusvc/qna/pageModify.do?nav=MNU021&qnaOid=${param.qnaOid}"/>">수정</a>
        <a class="button button__secondary" v-if ="loginUserId == qna.qnaQuestionUsrId && qna.qnaStatus == 'answerWait' && backUrl == 'mypage'" href="<c:url value="/cusvc/qna/pageModify.do?backUrl=mypage&nav=MNU042&qnaOid=${param.qnaOid}"/>">수정</a>
        <a class="button button__danger" v-if ="backUrl == 'mypage'" href="<c:url value="/mypage/dataset/qna/pageList.do?nav=MNU042"/>">목록</a>
        <a class="button button__danger" v-if ="backUrl != 'mypage'" href="<c:url value="/cusvc/qna/pageList.do"/>">목록</a>
      </div>
    </div>    
    
    <div class="sub__content">
      <h4 class="sub__heading2">묻고답하기 답변</h4>
      <table class="detail-table">
        <caption>해당 테이블에 대한 캡션</caption>
        <colgroup>
          <col width="10%">
          <col width="">
          <col width="10%">
          <col width="">
        </colgroup>
        <tbody>
            <tr>
                <th scope="row">답변자</th>
                <td>{{qna.qnaAnswerUsrNm}}</td>
                <th scope="row">답변일시</th>
                <td v-if="qna.qnaAnswerDt != null">
                    {{qna.qnaAnswerDt | date}}
                </td>
            </tr>
            <tr>
                <th scope="row">답변 내용</th>
                <!-- 답변 내용 없을 때 -->
                <td colspan="3" v-if="qna.qnaAnswerDt == null && showAnswerButton">
                    
                </td>
                <!-- 답변 등록 눌렀을 때 -->
                <td colspan="3" v-if="!showAnswerButton">
                  <textarea rows="7" cols="130" v-model="qna.qnaAnswer" :readonly="isReadonly">
                  </textarea>
                </td>
                <!-- 답변 내용 있을 때  -->
                <td colspan="3" v-if="qna.qnaAnswerDt != null" v-html="$options.filters.enter(qna.qnaAnswer)">
                </td>
            </tr>
        </tbody>
      </table>
    </div>   
    
  </div>
  </div>

<script>
$(function() {
    vm = new Vue({
        el : '#content',
        data : function() {
            return {
                qna : {
                    qnaOid : "${param.qnaOid}",
                    fileList : []
                },
                loginUserId : "${sessionScope.userInfo.userId}",
                showAnswerButton : true,
                isReadonly : false,
                backUrl : "${param.backUrl}"
            }
        },
        methods : {
            getQna : function(nohit) {
            	vm.loadingFlag = true;
                var request = $.ajax({
                    url : SMC_CTX + "/cusvc/qna/get.do",
                    method : "GET",
                    dataType : "json",
                    data : vm.qna,
                    	   nohit : nohit
                });
                request.done(function(data) {
                	vm.loadingFlag = false;
                    vm.qna = data;
                });
            },
            download: function(qnafileOid){
                location.href = SMC_CTX + "/cusvc/qna/downloadFile.do?qnaOid="+vm.qna.qnaOid+"&fileId="+qnafileOid;
            }
        }
    })
    vm.getQna();
});
</script>
