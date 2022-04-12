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
    <h3 class="sub__heading1">묻고답하기 수정<small class="sub__heading1--small">내용을 입력하세요.</small></h3>
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
                            <n2m-file-uploader :saved-files="qna.fileList" @select-file="selectFile" @delete-file="deleteFile"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
                </fieldset>
            </div>
        </form>
        <div class="sub__bottom">
            <div class="button__group">
              <a class="button button__primary" @click="modifyQna">저장</a>
              <a class="button button__danger" v-if ="backUrl == 'mypage'" href="<c:url value="/mypage/dataset/qna/pageList.do?nav=MNU042"/>">목록</a>
        	  <a class="button button__danger" v-if ="backUrl != 'mypage'" href="<c:url value="/cusvc/qna/pageList.do"/>">목록</a>
            </div>
        </div>
    </div>
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
                        deleteFileOids : []
                    },
                    fileList : [],
                    deleteFileOids : [],
                    isDisabled : true,
                    backUrl : "${param.backUrl}"
                }
            },
            methods: {
            	getQna : function() {
                    vm.loadingFlag = true;
                    var request = $.ajax({
                        url : SMC_CTX + "/cusvc/qna/get.do",
                        method : "GET",
                        dataType : "json",
                        data : vm.qna
                    });
                    request.done(function(data) {
                        vm.loadingFlag = false;
                        vm.qna = data;
                        
                        var fileList = vm.qna.fileList;
                        if(fileList && fileList.length > 0){
                            vm.qna.fileList = fileList.map(function(file){
                                return {id: file.qnafileOid, name: file.qnafileOrgNm, size: file.qnafileSize};
                            });
                        }
                    });
                },
                deleteFile: function(index, oid){
                    vm.deleteFileOids.push(vm.qna.fileList.splice(index, 1)[0].oid);
                    vm.qna.deleteFileOids = vm.deleteFileOids;
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
                download: function(qnafileOid){
                    location.href = SMC_CTX + "/downloadFile.do?type=T&menu=qna&oid="+qnafileOid;
                },
                modifyQna : function() {
                	var deleteFileOids = vm.qna.deleteFileOids;
                    Valid.init(vm, vm.qna);
                    var constraints = {};
                    constraints = Valid.getConstraints();
                    if(Valid.validate(constraints)) return;
                    if(Modal.MODIFY()) return;
                    
                    var formData = new FormData(document.getElementById('qnaForm'));
                    for(var i=0; i<vm.fileList.length; i++){
                        formData.append("files"+i, vm.fileList[i]);
                    }
                    if(deleteFileOids != null){
						for(var j=0; j<deleteFileOids.length; j++){
	                        formData.append("deleteFileOid["+j+"]" , deleteFileOids[j]);
	                    }
					}
                    
                    var request = $.ajax({
                        method : "PUT",
                        url : SMC_CTX + "/cusvc/qna/modify.do?qnaOid="+vm.qna.qnaOid,
                        enctype: "multipart/form-data",
                        processData: false,
                        contentType : false,
                        data : formData
                    });
                    request.done(function(data) {
                        Modal.OK();
                        if(vm.backUrl == 'mypage'){
                        	location.href = SMC_CTX + "/cusvc/qna/pageDetail.do?backUrl=mypage&qnaOid="+vm.qna.qnaOid;
                        }else{
                        	location.href = SMC_CTX + "/cusvc/qna/pageDetail.do?qnaOid="+vm.qna.qnaOid;
                        }
                    });
                    request.fail(function(){
                    	alert("실패");
                    });
                }
            }
        })
        vm.getQna();
    });
</script>
