<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">Q&A상세</h3>
    <form>
      <fieldset>
        <legend>필드셋 제목</legend>
        <!-- section-write -->
        <section class="section">
          <div class="section__content">
            <table class="table--row">
              <caption>테이블 제목</caption>
              <colgroup>
                <col style="width:150px">
                <col style="width:auto">
                <col style="width:150px">
                <col style="width:auto">
              </colgroup>
              <tbody>
                <input type="hidden" name="qnaOid" v-model="qna.qnaOid" />
                <tr>
                  <th>번호</th>
                  <td colspan="3">
                    {{qna.qnaId}}
                  </td>
                </tr>
                <tr>
                  <th>등록자</th>
                  <td colspan="1">
                    {{qna.qnaQuestionUsrNm}}
                  </td>
                  <th>등록일시</th>
                  <td colspan="1">
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
                  <th>첨부파일</th>
                  <td class="file__group" colspan="3">
                    <ul class="file__list">
                      <li class="file__item file__item--none" v-if="qna.fileList.length === 0">첨부파일이 없습니다.</li>
                      <li class="file__item" v-for="(file, index) in qna.fileList">
                          <a @click="download(file.qnafileOid)">{{file.qnafileOrgNm}} ({{file.qnafileSize | size}})</a>
                      </li>
                    </ul>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>
        <!-- //section-default -->
        <div class="button__group">
          <c:if test="${modifiedYn eq 'Y'}">
            <button class="button__primary" type="button"  onclick="location.href='<c:url value="/qna/pageModify.do?qnaOid=${param.qnaOid}"/>'">수정</button>
          </c:if>
          <button class="button__secondary" type="button" onclick="location.href='<c:url value="/qna/pageList.do"/>'">목록</button>
        </div>
      </fieldset>
    </form>
    
    <section class="section">
        <div class="section__header">
            <h4 class="section__title">Q&A 답변</h4>
        </div>
        <div class="section__content">
            <table class="table--row">
                <caption>테이블 제목</caption>
                <colgroup>
                    <col style="width: 150px">
                    <col style="width: auto">
                    <col style="width: 150px">
                    <col style="width: auto">
                </colgroup>
                <tbody>
                    <tr>
                        <th>답변자</th>
                        <td>{{qna.qnaAnswerUsrNm}}</td>
                        <th>답변일시</th>
                        <td v-if="qna.qnaAnswerDt != null">
                            {{qna.qnaAnswerDt | date}}
                        </td>
                    </tr>
                    <tr>
                        <th>답변 내용</th>
                        <!-- 답변 내용 없을 때 -->
                        <td colspan="3" v-if="qna.qnaAnswerDt == null && showAnswerButton">
                                                답변을 등록해주세요.
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
    </section>
    <div class="button__group">
        <button class="button__secondary" type="button" v-if="showAnswerButton && qna.qnaAnswer == null" @click="openTextarea">답변등록</button>
        <button class="button__primary" type="button" v-if="!showAnswerButton" @click="saveAnswer">저장</button>
        <button class="button__secondary" type="button" v-if="!showAnswerButton" @click="cancelAnswer">취소</button>
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
                showAnswerButton : true,
                isReadonly : false
            }
        },
        methods : {
            getQna : function(nohit) {
                var request = $.ajax({
                    url : N2M_CTX + "/qna/get.do",
                    method : "GET",
                    dataType : "json",
                    data : {
                    		qnaOid : "${param.qnaOid}",
                    	    nohit  : nohit
                    }
                });
                request.done(function(data) {
                    vm.qna = data;
                });
            },
            cancelAnswer : function() {
                location.href = N2M_CTX + "/qna/pageDetail.do?qnaOid=" + vm.qna.qnaOid;
            },
            saveAnswer : function() {
                if(Modal.REGIST()) return;
                var request = $.ajax({
                    url : N2M_CTX + "/qna/reply.do",
                    method : "PUT",
                    contentType : "application/json",
//                     dataType : "json",
                    data : JSON.stringify(vm.qna)
                });
                request.done(function(data) {
                    Modal.OK();
                    vm.isAnswered = true;
                    location.href = N2M_CTX + "/qna/pageDetail.do?qnaOid=" + vm.qna.qnaOid;
                });
            },
            openTextarea : function() {
                vm.showAnswerButton = false;
            },
            download: function(qnafileId){
                location.href = N2M_CTX + "/qna/downloadFile.do?qnaOid="+vm.qna.qnaOid+"&fileId="+qnafileId;
            }
        }
    })
    vm.getQna();
});
</script>
