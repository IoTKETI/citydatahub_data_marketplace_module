<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
	<!-- breadcrumb -->
	<%@ include file="/WEB-INF/jsp/tiles/tiles_breadcrumb.jspf" %>
	<!-- //breadcrumb -->
	<h3 class="sub__heading1">신고하기 등록<small class="sub__heading1--small">내용을 입력하세요.</small></h3>
	<div class="page-write">
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
					<tbody>
					<tr>
						<th scope="row">작성자</th>
						<td colspan="3">${sessionScope.user.nickname}</td>
					</tr>
					<tr>
						<th scope="row"><i class="icon__require">*</i>유형</th>
						<td>
						<select class="select select--full" title="유형" v-model="datacomplaint.datacomplainGbCd" ref="datacomplainGbCd" name="datacomplainGbCd" required >
	                  		<option name="datacomplainGbCd" value="" disabled>-선택-</option>
	                  		<c:forEach var="code" items="${n2m:getCodeList('CG_ID_QNA_TYPE')}">
		                  	<option value="${code.codeId}">${code.codeName}</option>
	                  		</c:forEach>
	                  	</select>
						</td>
					</tr>
					<tr>
						<th scope="row"><i class="icon__require">*</i>제목</th>
						<td colspan="3"><input class="input" title="제목" type="text" ref="datacomplainTitle" name="datacomplainTitle" v-model="datacomplaint.datacomplainTitle" required></td>
					</tr>
					<tr>
						<th scope="row"><i class="icon__require">*</i>신고내용</th>
						<td colspan="3"><textarea class="textarea" title="신고내용" ref="datacomplainContent" name="datacomplainContent" v-model="datacomplaint.datacomplainContent" required></textarea></td>
					</tr>
					
					<tr>
						<th scope="row">첨부파일</th>
						<td colspan="3">
							<n2m-file-uploader :saved-files="datacomplaint.fileList" @select-file="selectFile"/>
						</td>
					</tr>
					</tbody>
				</table>
				</fieldset>
			</div>
		</form>
		<div class="sub__bottom">
			<div class="button__group">
				<a class="button button__primary" @click="createdatacomplaint">저장</a>
				<a class="button button__outline button__outline--primary" href="<c:url value="/cusvc/datacomplaint/pageList.do"/>">목록</a>
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
        	datacomplaint : {
        		datacomplainTitle: "",
        		datacomplainGbCd : "",
        		datacomplainContent : "",
        		fileList : [],
          },
          fileList : [],
        }
      },
      methods: {
        createdatacomplaint: function(){
        	Valid.init(vm, vm.datacomplaint);
            var constraints = {};
            constraints = Valid.getConstraints();
            if(Valid.validate(constraints)) return;
            
            if(Modal.REGIST()) return;
            
			var formData = new FormData(document.getElementById('datacomplaintForm'));

			for(var i=0; i<vm.fileList.length; i++){
				formData.append("files"+i, vm.fileList[i]);
			}
	          var request = $.ajax({
	            method: "POST",
	            url: SMC_CTX + "/cusvc/datacomplaint/create.do",
	            enctype: "multipart/form-data",
				processData: false,
				contentType: false,
				data: formData
	          });
	          request.done(function(data){
	        	  Modal.OK();
	        	  location.href = SMC_CTX + "/cusvc/datacomplaint/pageList.do";
	          });
        },
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
      }
    });
  });
</script>

