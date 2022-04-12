<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
    <!-- breadcrumb -->
   <%@ include file="/WEB-INF/jsp/tiles/tiles_breadcrumb.jspf" %>
    <!-- //breadcrumb -->
    <h3 class="sub__heading1">묻고답하기 등록<small class="sub__heading1--small">내용을 입력하세요.</small></h3>
    <div class="page-write">
        <form id="qnaForm" class="form">
            <div class="sub__content">
                <fieldset>
                <legend>묻고답하기 등록 폼</legend>
                
                <table class="form-table">
                    <caption>묻고답하기 등록 폼 테이블</caption>
                    <colgroup>
                    <col width="10%">
                    <col width="*">
                    <col width="10%">
                    <col width="*">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th scope="row">작성자</th>
                        <td colspan="3">${sessionScope.user.nickname}</td>
                    </tr>
                    <tr>
		              <th scope="row"><i class="icon__require">*</i>질문유형</th>
		              <td>
		                <select class="select select--full" name="qnaQuestionTypeCd" title="질문유형" v-model="qna.qnaQuestionTypeCd" ref="qnaQuestionTypeCd" required>
		                  <option name="qnaQuestionTypeCd" value="" disabled>-선택-</option>
		                  <c:forEach var="code" items="${n2m:getCodeList('CG_ID_QNA_TYPE')}">
		                      <option value="${code.codeId}">${code.codeName}</option>
		                  </c:forEach>
		                </select>
		              </td>
		            </tr>
                    <tr>
		              <th scope="row"><i class="icon__require">*</i>제목</th>
		              <td colspan="3">
		                <input name="qnaTitle" class="input" title="제목" type="text" v-model="qna.qnaTitle" ref="qnaTitle" required/>
		              </td>
		            </tr>
		            <tr>
		              <th scope="row"><i class="icon__require">*</i>질문내용</th>
		              <td colspan="3">
		               <textarea class="textarea" name="qnaQuestion" title="질문내용" rows="15" cols="168" v-model="qna.qnaQuestion" ref="qnaQuestion" required></textarea>
		              </td>
		            </tr>
                    <tr>
                        <th scope="row">첨부파일</th>
                        <td colspan="3">
                            <n2m-file-uploader :saved-files="qna.fileList" @select-file="selectFile"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
                </fieldset>
            </div>
        </form>
        <div class="sub__bottom">
			<div class="button__group">
			  <a class="button button__primary" @click="createQna">저장</a>
                <a class="button button__outline button__outline--primary" v-if ="backUrl == 'mypage'" href="<c:url value="/mypage/dataset/qna/pageList.do?nav=MNU042"/>">목록</a>
                <a class="button button__outline button__outline--primary" v-if ="backUrl != 'mypage'" href="<c:url value="/cusvc/qna/pageList.do"/>">목록</a>
			</div>
        </div>
</div>
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
                fileList : [],
                backUrl : "${param.backUrl}"
            }
        },
        methods : {
        	deleteFile: function(index, oid){
                if(oid){
                    this.$emit('delete-file', index, oid);
                }else{
                    this.files.splice(index, 1);
                    this.$emit('select-file', this.files);
                }
                this.clear();
            },
            selectFile: function(files, append){
                if(append){
                    for(var i=0; i<files.length; i++){
                        vm.fileList.push(files[i]);
                    }
                }else{
                    vm.fileList = files;
                }
            },
            createQna : function() {
                Valid.init(vm, vm.qna);
                var constraints = {};
                constraints = Valid.getConstraints();
                if(Valid.validate(constraints)) return;
                
                if(Modal.REGIST()) return;
                
                var formData = new FormData(document.getElementById('qnaForm'));
                for(var i=0; i<vm.fileList.length; i++){
                    formData.append("files", vm.fileList[i]);
                }
                var request = $.ajax({
                    method : "POST",
                    url : SMC_CTX + "/cusvc/qna/create.do",
                    enctype: "multipart/form-data",
                    processData: false,
                    contentType: false,
                    data : formData
                });
                request.done(function(data) {
                	Modal.OK();
                	if(vm.backUrl == 'mypage'){
                    	location.href = SMC_CTX + "/mypage/dataset/qna/pageList.do?nav=MNU042";
                    }else{
                    	location.href = SMC_CTX + "/cusvc/qna/pageList.do";
                    }
                });
                request.fail(function(data) {
                	alert("실패");
                });
            }
        }
    });
});
</script>
