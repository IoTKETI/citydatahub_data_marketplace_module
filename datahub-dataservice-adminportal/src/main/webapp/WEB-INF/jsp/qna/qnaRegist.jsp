<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">Q&A등록</h3>
<form id="qnaForm">
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
            <tr>
              <th>작성자</th>
              <td colspan="3">
                ${sessionScope.user.userNm}
              </td>
            </tr>
            <tr>
              <th class="icon__require">질문유형</th>
              <td>
                <select class="select" name="qnaQuestionTypeCd" title="질문유형" v-model="qna.qnaQuestionTypeCd" ref="qnaQuestionTypeCd" required>
                  <option name="qnaQuestionTypeCd" value="" disabled>-선택-</option>
                  <c:forEach var="code" items="${n2m:getCodeList('CG_ID_QNA_TYPE')}">
                      <option value="${code.codeId}">${code.codeName}</option>
                  </c:forEach>
                </select>
              </td>
            </tr>
            <tr>
              <th class="icon__require">제목</th>
              <td colspan="3">
                <input name="qnaTitle" class="input__text" title="제목" type="text" v-model="qna.qnaTitle" ref="qnaTitle" required/>
              </td>
            </tr>
            <tr>
              <th class="icon__require">질문내용</th>
              <td colspan="3">
               <textarea name="qnaQuestion" title="질문내용" rows="15" cols="168" v-model="qna.qnaQuestion" ref="qnaQuestion" required></textarea>
              </td>
            </tr>
            <tr>
              <th>첨부파일</th>
              <td class="file__group" colspan="3">
                <n2m-file-uploader :saved-files="qna.fileList" @select-file="selectFile"/>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
    <!-- //section-default -->
    <div class="button__group">
      <button class="button__primary" type="button" @click="createQna">저장</button>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/qna/pageList.do"/>'">취소</button>
    </div>
  </fieldset>
</form>
</div>

<script>
$(function() {
    Vue.use(N2MFileUploader);
    vm = new Vue({
        el : '#content',
        data : function() {
            return {
                qna : {
                    qnaTitle : "",
                    qnaQuestion : "",
                    qnaQuestionTypeCd : "",
                    qnaQuestionUsrId : "${sessionScope.user.userId}",
                    fileList : []
                },
                fileList : []
            }
        },
        methods : {
            selectFile: function(files){
                vm.fileList = files;
            },
            createQna : function() {
                Valid.init(vm, vm.qna);
                var constraints = {};
                constraints = Valid.getConstraints();
                if(Valid.validate(constraints)) return;
                
                if(Modal.REGIST()) return;
                
                var formData = new FormData(document.getElementById('qnaForm'));
                for(var i=0; i<vm.fileList.length; i++){
                    formData.append("files"+(i+1), vm.fileList[i]);
                }
                var request = $.ajax({
                    method : "POST",
                    url : N2M_CTX + "/qna/create.do",
                    enctype: "multipart/form-data",
                    processData: false,
                    contentType: false,
                    data : formData
                });
                request.done(function(data) {
                	Modal.OK();
                    location.href = N2M_CTX + "/qna/pageList.do";
                });
            }
        }
    });
});
</script>
