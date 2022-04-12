<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">데이터민원 상세</h3>
<form id="datacomplaintForm" name="datacomplaintForm">
  <fieldset>
    <legend>필드셋 제목</legend>
    <!-- section-write -->
    <section class="section">
     <div class="section__header">
            <h4 class="section__title">민원 질문</h4>
          </div>
      <div class="section__content">
        <table class="table--row">
          <caption>테이블 제목</caption>
          <colgroup>
            <col style="width:150px">
            <col style="width:auto">
            <col style="width:150px">
            <col style="width:auto">
          </colgroup>
          <input type="hidden" name="datacomplainOid" value="${param.datacomplainOid}" />
          <tbody>
          	<tr>
              <th>번호</th>
              <td colspan="3">
                {{datacomplaint.datacomplainId}}
              </td>
            </tr>
            <tr>
              <th>등록자</th>
              <td>
                {{datacomplaint.datacomplainCreUsrNm}}
              </td>
              <th>등록일시</th>
              <td>
                {{datacomplaint.datacomplainCreDt|date}}
              </td>
            </tr>
            <tr>
              <th>유형</th>
              <td>
                {{datacomplaint.datacomplainGbCdNm}}
              </td>
              <th>조회수</th>
              <td>
                {{datacomplaint.datacomplainCnt}}
              </td>
            </tr>
            <tr>
              <th>진행상태</th>
              <td colspan="3">
                {{datacomplaint.datacomplainStatCdNm}}
              </td>
            </tr>
            <tr>
              <th>제목</th>
              <td colspan="3">
                {{datacomplaint.datacomplainTitle}}
              </td>
            </tr>
            <tr class="va-t">
              <th>민원내용</th>
              <td colspan="3" v-html="$options.filters.enter(datacomplaint.datacomplainContent)">
              </td>
            </tr>
            <tr>
              <th>첨부파일</th>
			    <td class="file__group" colspan="3">
			    	<ul class="file__list">
		            	<li class="file__item file__item--none" v-if="datacomplaint.fileList.length === 0">첨부파일이 없습니다.</li>
		            	<li class="file__item" v-for="(file, index) in datacomplaint.fileList">
		                	<a @click="download(file.dcpFileOid)">{{file.dcpFileOrgNm}} ({{file.dcpFileSizeByte | size}})</a>
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
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/datacomplaint/pageList.do"/>'">목록</button>
    </div>
    
    <section class="section">
          <div class="section__header">
            <h4 class="section__title">민원 답변</h4>
          </div>
          
          <div v-if="AnswerRegistshow == false && datacomplaint.datacomplainAnswer != null">
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
                        <th>답변자</th>
                        <td>{{datacomplaint.datacomplainAnswerUsrNm}}</td>
                        <th>답변일시</th>
                        <td>
                            {{datacomplaint.datacomplainAnswerDt|date}}
                        </td>
                    </tr>
                    <tr class="va-t">
                        <th>답변 내용</th>
                        <td colspan="3" v-html="$options.filters.enter(datacomplaint.datacomplainAnswer)">
                        </td>
                    </tr>
                    <tr>
                  		<th>첨부파일</th>
			    			<td class="file__group" colspan="3">
			    				<ul class="file__list">
		            				<li class="file__item file__item--none" v-if="datacomplaintAnswer.AnswerfileList.length === 0">첨부파일이 없습니다.</li>
		            				<li class="file__item" v-for="(file, index) in datacomplaintAnswer.AnswerfileList">
		                		<a @click="download(file.dcpFileOid)">{{file.dcpFileOrgNm}} ({{file.dcpFileSizeByte | size}})</a>
		           		 	</li>
		           		 </ul>
		        		</td>
                	</tr>
	              </tbody>
	            </table>
	          </div>
          </div>
          <div v-if="AnswerRegistshow == false && datacomplaint.datacomplainAnswer == null">
	          <div class="section__content">
	            <table class="table--column">
	              <caption>테이블 제목</caption>
	             <colgroup>
		            <col style="width:auto">
	         	 </colgroup>
	              <tbody>
	                <tr>
	                  <th>등록된 답변이 없습니다.</th>
	                </tr>
	              </tbody>
	            </table>
	          </div>
          </div>
	       <div class="section__content" v-if="AnswerRegistshow == true">
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
	              <th class="icon__require">답변</th>
	              <td colspan="3"><textarea name="datacomplainAnswer" ref="datacomplainAnswer" class="textarea" v-model="datacomplaintAnswer.datacomplainAnswer" title="답변" required></textarea></td>
	            </tr>
	            <tr>
	              <th>첨부파일</th>
	     		  <td class="file__group" colspan="3">
				    <n2m-file-uploader :saved-files="datacomplaintAnswer.AnswerfileList" @select-file="selectAnswerFile" @delete-file="deleteAnswerFile"/>
	     		  </td>
            	</tr>
	          </tbody>
	        </table>
	      </div>
       </section>
        <c:if test="${modifiedYn eq 'Y'}">
        <div class="button__group" v-if="AnswerRegistshow == false && datacomplaint.datacomplainAnswer == null">
  			<!-- <button class="button__primary" type="button" onclick="location.href='<c:url value="/datacomplaint/pageEdit.do"/>'">등록</button> -->
  			<button class="button__primary" type="button" v-on:click ="AnswerRegistshowtoggle">등록</button>
		</div>
		<!-- 답변 최 --> 
		<!--<div class="button__group" v-if="datacomplaint.datacomplainAnswer != '' && AnswerRegistshow == false">
		  	<button class="button__primary" type="button" v-on:click ="AnswerRegistshowtoggle">수정</button>
		</div>-->
		<div class="button__group" v-if="AnswerRegistshow == true">
  			<!-- <button class="button__primary" type="button" onclick="location.href='<c:url value="/datacomplaint/pageEdit.do"/>'">등록</button> -->
  			<button class="button__primary" type="button" @click="modifyDatacomplaint">저장</button>
  			<button class="button__secondary" type="button" v-on:click ="AnswerRegistshowtoggle">취소</button>
		</div>
		</c:if>
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
				  datacomplaint: {
					datacomplainOid: "${param.datacomplainOid}",
					fileList : []
				},
				datacomplaintAnswer: {
					datacomplainOid: null,
					AnswerfileList : [],
					datacomplainAnswer:""
				},
	        	AnswerRegistshow : false,
	        	Answerdata :null,
	        	fileList : [],
				}
			},
			methods: {
				AnswerRegistshowtoggle: function(){
					if(this.AnswerRegistshow){
						this.AnswerRegistshow = false;
					}
					else{
						this.AnswerRegistshow = true;
					}
				},
				Answerdatasave :function(){
					this.AnswerRegistshow = false;
					//답변 업데이트 ajax
					
				},
				getdatacomplaint: function(nohit){
					var request = $.ajax({
						url: N2M_CTX + "/datacomplaint/get.do",
						method: "GET",
						dataType: "json",
						data: {
							    datacomplainOid: "${param.datacomplainOid}",
							    nohit          : nohit
						}
					});
					request.done(function(data){
						vm.datacomplaint = data;
						vm.datacomplaintAnswer.datacomplainOid = data.datacomplainOid;
						vm.divisionFile(data.fileList);
					});
				},
				deleteDatacomplaint: function(){
					if(Modal.DELETE()) return;
					
					var request = $.ajax({
						url: N2M_CTX + "/datacomplaint/remove.do",
						method: "DELETE",
						contentType: "application/json",
// 						dataType: "json",
						data: JSON.stringify(vm.datacomplaint)
					});
					request.done(function(data){
						Modal.OK();
						location.href = N2M_CTX + "/datacomplaint/pageList.do";
					});
				},
				modifyDatacomplaint: function(){ //댓글 등록
					Valid.init(vm, vm.datacomplaintAnswer);
		        	var constraints = {};
					constraints = Valid.getConstraints();

					if(Valid.validate(constraints)) return;
					if(Modal.REGIST()) return;
					
					var formData = new FormData(document.getElementById('datacomplaintForm'));
					for(var i=0; i<vm.fileList.length; i++){
						formData.append("files"+i, vm.fileList[i]);
					}
					
					var request = $.ajax({
						url: N2M_CTX + "/datacomplaint/part/modify.do",
						method: "PUT",
						enctype: "multipart/form-data",
						processData: false,
						contentType: false,
						data: formData
					});
					request.done(function(data){
						Modal.OK();
						location.href = N2M_CTX + "/datacomplaint/pageDetail.do?datacomplainOid=" + vm.datacomplaint.datacomplainOid;
					});
				},
				download: function(dcpFileOid){
					location.href = N2M_CTX + "/datacomplaint/downloadFile.do?datacomplainOid="+vm.datacomplaint.datacomplainOid +"&fileId="+dcpFileOid;
				},
				selectAnswerFile: function(files){
		            vm.fileList = files;
		        },
		        deleteAnswerFile: function(index, oid){
					if(oid){
						this.$emit('delete-file', index, oid);
					}else{
						this.files.splice(index, 1);
						this.$emit('select-file', this.files);
					}
					this.clear();
				},
				divisionFile : function(files){
					var AnswerfileList =[];
					var fileList =[];
					
					for(var i=0;i<files.length;i++){
						if(files[i].dcpFileTypeAnswer == "Y"){
							AnswerfileList.push(files[i]);
						}else{
							fileList.push(files[i]);
						}
					}
					vm.datacomplaintAnswer.AnswerfileList = AnswerfileList;
					vm.datacomplaint.fileList = fileList;
				}
			}
		})
	  vm.getdatacomplaint();
	});
</script>


