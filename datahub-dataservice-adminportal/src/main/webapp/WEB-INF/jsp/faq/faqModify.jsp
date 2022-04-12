<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">FAQ수정</h3>
<form id="faqForm">
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
          	<input type="hidden" name="faqOid" value="${param.faqOid}" />
            <input type="hidden" name="deleteFileOids" v-model="faq.deleteFileOids" />
          <tbody>
            <tr>
              <th class="va-t">작성자</th>
              <td colspan="3">
               {{faq.faqCreUsrNm}}
              </td>
            </tr>
            <tr>
              <th class="icon__require">제목</th>
              <td colspan="3">
                <input class="input__text" name="faqTitle" type="text" ref="faqTitle" v-model="faq.faqTitle" title="제목" required/>
              </td>
            </tr>
            <tr>
              <th class="icon__require">질문내용</th>
              <td colspan="3"><textarea class="textarea" name="faqQuestion" ref="faqQuestion" v-model="faq.faqQuestion" title="질문내용" required></textarea></td>
            </tr>
            <tr>
              <th class="icon__require">답변</th>
              <td colspan="3"><textarea class="textarea" name="faqAnswer" ref="faqAnswer" v-model="faq.faqAnswer" title="답변" required></textarea></td>
            </tr>
            <tr>
              <th>첨부파일</th>
              <td class="file__group" colspan="3">
                <n2m-file-uploader :saved-files="faq.fileList"  @select-file="selectFile" @delete-file="deleteFile"/>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
    <!-- //section-default -->
    <div class="button__group">
      <button class="button__primary" type="button" @click="modifyFaq">저장</button>
      	<c:if test="${deleteYn eq 'Y'}">
      		<button class="button__secondary" type="button" @click="deleteFaq">삭제</button>
      	</c:if>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/faq/pageDetail.do?faqOid=${param.faqOid}"/>'">취소</button>
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
					faq: {
						faqOid: "${param.faqOid}",
						faqTitle: "",
		        		faqQuestion:"",
		        		faqAnswer:"",
		        		faqCreUsrId : null,
		        		fileList : [],
                        deleteFileOids : []
					},
					 fileList : [],
	                 deleteFileOids: []
				}
			},
			methods: {
				getFaq: function(nohit){
					var request = $.ajax({
						url: N2M_CTX + "/faq/get.do",
						method: "GET",
						dataType: "json",
						data: {
								faqOid: "${param.faqOid}",
						        nohit : "yes"
						}
					});
					request.done(function(data){
						vm.faq = data;
						var fileList = vm.faq.fileList;
                        if(fileList && fileList.length > 0){
                        	vm.faq.fileList = fileList.map(function(file){
                        		return {oid: file.faqfileOid, name: file.faqfileOrgNm, size: file.faqfileSizeByte};
                        	});
                        }
					});
				},
				selectFile: function(files){
                    vm.fileList = files;
                },
                download: function(faqfileOid){
                    location.href = N2M_CTX + "/downloadFile.do?menu=faq&oid="+faqfileOid;
                },
                deleteFile: function(index, oid){
                	// 삭제버튼 누른 파일 oid 를 vm.faq.deleteFileOids 리스트에 넣기
                	vm.deleteFileOids.push(vm.faq.fileList.splice(index, 1)[0].oid);
                	vm.faq.deleteFileOids = vm.deleteFileOids;
                },
				deleteFaq: function(){
					if(Modal.DELETE()) return;
					
					var request = $.ajax({
						url: N2M_CTX + "/faq/remove.do",
						method: "DELETE",
						contentType: "application/json",
// 						dataType: "json",
						data: JSON.stringify(vm.faq)
					});
					request.done(function(data){
						Modal.OK();
						location.href = N2M_CTX + "/faq/pageList.do";
					});
				},
				modifyFaq: function(){
					Valid.init(vm, vm.faq);
		        	var constraints = {};
					constraints = Valid.getConstraints();

					if(Valid.validate(constraints)) return;
					if(Modal.MODIFY()) return;
					
					var formData = new FormData(document.getElementById('faqForm'));
					for(var i=0; i<vm.fileList.length; i++){
                        formData.append("files"+i, vm.fileList[i]);
                    }
					var request = $.ajax({
						url: N2M_CTX + "/faq/modify.do",
						method: "PUT",
						enctype: "multipart/form-data",
	                    processData: false,
	                    contentType: false,
	                    data: formData
					});
					request.done(function(data){
						Modal.OK();
						location.href = N2M_CTX + "/faq/pageDetail.do?faqOid=" + vm.faq.faqOid;
					});
				}
			}
		})
		vm.getFaq();
	});
</script>
