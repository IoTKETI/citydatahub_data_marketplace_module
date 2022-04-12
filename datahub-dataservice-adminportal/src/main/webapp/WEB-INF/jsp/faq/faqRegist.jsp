<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">FAQ등록</h3>
<form id="faqForm" name="faqForm">
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
              <th class="va-t">작성자</th>
              <td colspan="3">
               ${sessionScope.user.userNm}
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
              <td colspan="3"><textarea class="textarea"  name="faqQuestion" ref="faqQuestion" v-model="faq.faqQuestion" title="질문내용" required></textarea></td>
            </tr>
            <tr>
              <th class="icon__require">답변</th>
              <td colspan="3"><textarea class="textarea" name="faqAnswer" ref="faqAnswer" v-model="faq.faqAnswer" title="답변" required></textarea></td>
            </tr>
            <tr>
               <th>첨부파일</th>
     		  <td class="file__group" colspan="3">
			    <n2m-file-uploader :saved-files="faq.fileList" @select-file="selectFile" @delete-file="deleteFile"/>
     		  </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
    <!-- //section-default -->
    <div class="button__group">
      <button class="button__primary" type="button" @click="createFaq">저장</button>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/faq/pageList.do"/>'">취소</button>
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
        	faq : {
        		faqTitle: "",
        		faqQuestion:"",
        		faqAnswer:"",
        		faqCreUsrId : null,
        		fileList : [],
          },
          fileList : [],
        }
      },
      methods: {
        createFaq: function(){
			Valid.init(vm, vm.faq);
        	var constraints = {};
			constraints = Valid.getConstraints();

			if(Valid.validate(constraints)) return;
			if(Modal.REGIST()) return;
			
			var formData = new FormData(document.getElementById('faqForm'));
			for(var i=0; i<vm.fileList.length; i++){
				formData.append("files"+(i+1), vm.fileList[i]);
			}
	          var request = $.ajax({
	            method: "POST",
	            url: N2M_CTX + "/faq/create.do",
	            enctype: "multipart/form-data",
				processData: false,
				contentType: false,
				data: formData
	          });
	          request.done(function(data){
	        	  Modal.OK();
	        	  location.href = N2M_CTX + "/faq/pageList.do";
	          });
        },
        selectFile: function(files){
            vm.fileList = files;
        },
        deleteFile: function(index, oid){
			if(oid){
				this.$emit('delete-file', index, oid);
			}else{
				this.files.splice(index, 1);
				this.$emit('select-file', this.files);
			}
			this.clear();
		}
      }
    });
  });
</script>

