<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">Q&A수정</h3>
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
            <input type="hidden" name="qnaOid" value="${param.qnaOid}" />
            <input type="hidden" name="deleteFileOids" v-model="qna.deleteFileOids" />
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
              <th class="icon__require">질문유형</th>
              <td>
                <select class="select" name="qnaQuestionTypeCd" title="질문유형" v-model="qna.qnaQuestionTypeCd" ref="qnaQuestionTypeCd" required>
                  <option value="" disabled>-선택-</option>
                  <c:forEach var="code" items="${n2m:getCodeList('CG_ID_QNA_TYPE')}">
                      <option value="${code.codeId}">${code.codeName}</option>
                  </c:forEach>
                </select>
              </td>
              <th>조회수</th>
              <td colspan="1">
                {{qna.qnaCnt}}
              </td>
            </tr>
            <tr>
              <th class="icon__require">제목</th>
              <td colspan="3">
                <input class="input__text" name="qnaTitle" title="제목" type="text" v-model="qna.qnaTitle" ref="qnaTitle" required/>
              </td>
            </tr>
            <tr>
              <th class="icon__require">내용</th>
              <td colspan="3">
                <textarea rows="10" cols="130" name="qnaQuestion" title="내용" v-model="qna.qnaQuestion" ref="qnaQuestion" required></textarea>
              </td>
            </tr>
            <tr>
              <th>첨부파일</th>
              <td colspan="3">
                <n2m-file-uploader :saved-files="qna.fileList" @select-file="selectFile" @delete-file="deleteFile"/>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
    <!-- //section-default -->
    <div class="button__group">
      <c:if test="${modifiedYn eq 'Y'}">
        <button class="button__primary" type="button"  @click="modifyQna">저장</button>
      </c:if>
      <c:if test="${deleteYn eq 'Y'}">
        <button class="button__secondary" type="button" @click="deleteQna">삭제</button>
      </c:if>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/qna/pageDetail.do?qnaOid=${param.qnaOid}"/>'">취소</button>
    </div>
  </fieldset>
</form>
</div>
<script>
    $(function(){
    	Vue.use(N2MFileUploader);
        vm = new Vue({
            el: '#content',
            data: function(){
                return {
                    qna: {
                        qnaOid: "${param.qnaOid}",
                        fileList : [],
                        deleteFileOids : [],
                    },
                    fileList : [],
                    deleteFileOids : [],
                    isDisabled : true
                }
            },
            methods: {
                getQna: function(nohit){
                    var request = $.ajax({
                        url: N2M_CTX + "/qna/get.do",
                        method: "GET",
                        dataType: "json",
                        data: {
                        	qnaOid: "${param.qnaOid}",
                            nohit : "yes"
                        }
                    });
                    request.done(function(data){
                        vm.qna = data;
                        var fileList = vm.qna.fileList;
                        if(fileList && fileList.length > 0){
                            vm.qna.fileList = fileList.map(function(file){
                                return {oid: file.qnafileOid, name: file.qnafileOrgNm, size: file.qnafileSize};
                            });
                        }
                    });
                },
                selectFile: function(files){
                    vm.fileList = files;
                },
//                 download: function(qnafileOid){
//                     location.href = N2M_CTX + "/downloadFile.do?menu=qna&oid="+qnafileOid;
//                 },
                deleteFile: function(index, oid){
                    // 삭제버튼 누른 파일 oid 를 vm.qna.deleteFileOids 리스트에 넣기
                    vm.deleteFileOids.push(vm.qna.fileList.splice(index, 1)[0].oid);
                    vm.qna.deleteFileOids = vm.deleteFileOids;
                },
                deleteQna: function(){
                    if(Modal.DELETE()) return;
                    
                    var request = $.ajax({
                        url: N2M_CTX + "/qna/remove.do",
                        method: "DELETE",
                        contentType: "application/json",
//                         dataType: "json",
                        data: JSON.stringify(vm.qna)
                    });
                    request.done(function(data){
                        Modal.OK();
                        location.href = N2M_CTX + "/qna/pageList.do";
                    });
                },
                modifyQna : function() {
                    Valid.init(vm, vm.qna);
                    var constraints = {};
                    constraints = Valid.getConstraints();
                    if(Valid.validate(constraints)) return;
                    
                    var formData = new FormData(document.getElementById('qnaForm'));
                    for(var i=0; i<vm.fileList.length; i++){
                        formData.append("files"+i, vm.fileList[i]);
                    }
                    
                    if(Modal.MODIFY()) return;
                    
                    var request = $.ajax({
                        method : "PUT",
                        url : N2M_CTX + "/qna/modify.do",
                        enctype: "multipart/form-data",
                        processData: false,
                        contentType : false,
                        data : formData
                    });
                    request.done(function(data) {
                    	Modal.OK();
                        location.href = N2M_CTX + "/qna/pageDetail.do?qnaOid="+vm.qna.qnaOid;
                    });
                }
            }
        })
        vm.getQna();
    });
</script>
