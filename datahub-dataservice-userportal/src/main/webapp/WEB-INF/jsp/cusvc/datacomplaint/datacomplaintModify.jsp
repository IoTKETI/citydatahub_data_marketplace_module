<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h2 class="hidden">본문</h2>
      <!-- sub-cover -->
      <section class="sub-cover">
        <h3 class="sub-cover__title material-icons">고객 서비스 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
      </section>
      <hr>
       <!-- sub-nav -->
     ${lnbHtml}
 		<!-- sub-nav -->
<hr>
	<div class="sub__container">
	<%@ include file="/WEB-INF/jsp/tiles/tiles_breadcrumb.jspf" %>
 	 <h3 class="sub__heading1">신고하기 수정</h3>
        <div class="sub__content">
          <h4 class="sub__heading2">신고하기 질문</h4>
	<form id="datacomplaintForm" class="form">
			<div class="sub__content">
				<fieldset>
				<legend>데이터 명세서 등록 폼</legend>
				
				<table class="form-table">
					<caption>데이터 명세서 등록 폼 테이블</caption>
					<colgroup>
					<col width="10%">
					<col width="*">
					<col width="10%">
					<col width="*">
					</colgroup>
					<input type="hidden" name="datacomplainOid" value="${param.datacomplainOid}" />
					<tbody>
					<tr>
						<th scope="row">작성자</th>
						<td colspan="3">${sessionScope.user.nickname}</td>
					</tr>
					<tr>
						<th scope="row"><i class="icon__require">*</i>유형</th>
						<td>
						<select class="select select--full" title="유형" ref="datacomplainGbCd" v-model="datacomplaint.datacomplainGbCd" name="datacomplainGbCd" required >
	                  		<option name="datacomplainGbCd" value="" disabled>-선택-</option>
	                  		<c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATACOMPLAINT_TYPE')}">
		                  	<option value="${code.codeId}">${code.codeName}</option>
	                  		</c:forEach>
	                  	</select>
						</td>
					</tr>
					<tr>
						<th scope="row"><i class="icon__require">*</i>제목</th>
						<td colspan="3"><input class="input" title="제목" ref="datacomplainTitle" type="text" name="datacomplainTitle" v-model="datacomplaint.datacomplainTitle" required></td>
					</tr>
					<tr>
						<th scope="row"><i class="icon__require">*</i>신고내용</th>
						<td colspan="3"><textarea class="textarea" title="신고내용" ref="datacomplainContent" name="datacomplainContent" v-model="datacomplaint.datacomplainContent" required></textarea></td>
					</tr>
                    <tr>
                        <th scope="row">첨부파일</th>
                        <td colspan="3">
                            <n2m-file-uploader :saved-files="datacomplaint.fileList" @select-file="selectFile" @delete-file="deleteFile"/>
                        </td>
                    </tr>
					</tbody>
				</table>
				</fieldset>
			</div>
		</form>
		<div class="sub__bottom">
			<div class="button__group">
				<a class="button button__primary" @click="modifydatacomplaint">저장</a>
				<a class="button button__primary" @click="deletedatacomplaint">삭제</a>
				<a class="button button__outline button__outline--primary" href="<c:url value="/cusvc/datacomplaint/pageDetail.do?nav=MNU022&datacomplainOid=${param.datacomplainOid}"/>">취소</a>
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
					datacomplaint: {
						datacomplainOid: "${param.datacomplainOid}",
						datacomplainTitle: "",
		        		datacomplainCreUsrId : "",
		        		datacomplainGbCd : "",
		        		datacomplainContent : "",
		        		fileList : [],
                        deleteFileOids : []
					},
					 fileList : [],
	                 deleteFileOids: []
				}
			},
			methods: {
				getDatacomplaint: function(){
					var request = $.ajax({
						url: SMC_CTX + "/cusvc/datacomplaint/get.do",
						method: "GET",
						dataType: "json",
						data: {
							datacomplainOid: "${param.datacomplainOid}",
							nohit: "yes"
						}
					});
					request.done(function(data){
						vm.datacomplaint = data;
						if(vm.datacomplaint.fileList && vm.datacomplaint.fileList.length > 0){
	                    	vm.datacomplaint.fileList = vm.datacomplaint.fileList.map(function(file){
	                    		return {
	                    			oid: file.dcpFileOid,
	                    			name: file.dcpFileOrgNm,
	                    			size: file.dcpFileSizeByte,
	                    		}
	                    	});
	                    }
					});
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
                download: function(datacomplaintfileOid){
                    location.href = SMC_CTX + "/downloadFile.do?type=T&menu=datacomplaint&oid="+datacomplainfileOid;
                },
                deleteFile: function(index, oid){
                	// 삭제버튼 누른 파일 oid 를 vm.faq.deleteFileOids 리스트에 넣기
                	vm.deleteFileOids.push(vm.datacomplaint.fileList.splice(index, 1)[0].oid);
                	vm.datacomplaint.deleteFileOids = vm.deleteFileOids;
                },
				deletedatacomplaint: function(){
					if(Modal.DELETE()) return;
					
					var request = $.ajax({
						url: SMC_CTX + "/cusvc/datacomplaint/remove.do",
						method: "DELETE",
						contentType: "application/json",
						dataType: "json",
						data: JSON.stringify(vm.datacomplaint)
					});
					request.done(function(data){
						Modal.OK();
						location.href = SMC_CTX + "/cusvc/datacomplaint/pageList.do?nav=MNU022";
					});
				},
				modifydatacomplaint: function(){
					var deleteFileOids = vm.datacomplaint.deleteFileOids;
					Valid.init(vm, vm.datacomplaint);
		        	var constraints = {};
					constraints = Valid.getConstraints();

					if(Valid.validate(constraints)) return;
					if(Modal.MODIFY()) return;
					
					var formData = new FormData(document.getElementById('datacomplaintForm'));
					
					for(var i=0; i<vm.fileList.length; i++){
                        formData.append("files"+i, vm.fileList[i]);
                    }
					if(deleteFileOids != null){
						for(var j=0; j<deleteFileOids.length; j++){
	                        formData.append("deleteFileOid["+j+"]" , deleteFileOids[j]);
	                    }
					}
					var request = $.ajax({
						url: SMC_CTX + "/cusvc/datacomplaint/modify.do",
						method: "PUT",
						enctype: "multipart/form-data",
	                    processData: false,
	                    contentType: false,
	                    data: formData
					});
					request.done(function(data){
						Modal.OK();
						location.href = SMC_CTX + "/cusvc/datacomplaint/pageDetail.do?nav=MNU022&datacomplainOid=" + vm.datacomplaint.datacomplainOid;
					});
				}
			}
		})
		vm.getDatacomplaint();
	});
</script>
