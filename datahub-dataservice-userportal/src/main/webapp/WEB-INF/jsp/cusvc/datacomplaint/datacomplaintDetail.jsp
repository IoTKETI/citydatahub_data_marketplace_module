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
    <h3 class="sub__heading1">신고하기</h3>
        <div class="sub__content">
          <h4 class="sub__heading2">신고하기 질문</h4>
          <table class="detail-table">
            <caption>해당 테이블에 대한 캡션</caption>
            <colgroup>
              <col width="10%">
              <col width="*">
              <col width="10%">
              <col width="*">
            </colgroup>
            <tbody>
             <tr>
                <th scope="row">번호</th>
                <td colspan="3"> {{datacomplaint.datacomplainId}}</td>
              </tr>
              <tr>
                <th scope="row">등록자</th>
                <td>{{datacomplaint.datacomplainCreUsrNm}}</td>
                <th scope="row">등록일시</th>
                <td>{{datacomplaint.datacomplainCreDt|date}}</td>
              </tr>
              <tr>
                <th scope="row">유형</th>
                <td>{{datacomplaint.datacomplainGbCdNm}}</td>
                <th scope="row">조회수</th>
                <td>{{datacomplaint.datacomplainCnt}}</td>
              </tr>
              <th scope="row">진행상태</th>
                <td colspan="3">{{datacomplaint.datacomplainStatCdNm}}</td>
              <tr>
                <th scope="row">제목</th>
                <td colspan="3"> {{datacomplaint.datacomplainTitle}}</td>
              </tr>
              <tr>
                <th scope="row">신고하기 내용</th>
                <td colspan="3" v-html="$options.filters.enter(datacomplaint.datacomplainContent)"></td>
              </tr>
              <tr>
                <th scope="row">첨부파일</th>
                <td colspan="3">
                  <ul class="attach-file__list">
                    <li class="attach-file__item--none" v-if="datacomplaint.fileList.length === 0">첨부파일이 없습니다.</li>
                    <li class="attach-file__item material-icons"  v-for="(file, index) in datacomplaint.fileList">
                    <a @click="download(file.dcpFileOid)">
                        {{file.dcpFileOrgNm}}<span class="attach-file__item--size">({{file.dcpFileSizeByte | size}})</span>
                        <button type="button" class="attach-file__button--delete material-icons" title="파일 삭제"><span class="hidden">파일 삭제</span></button>
                    </a>
                    </li>
                  </ul>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="sub__bottom">
          <div class="button__group">
            <a class="button button__secondary" v-if ="LoginUserId == datacomplaint.datacomplainCreUsrId && datacomplaint.datacomplainStatCd == 'answerWait'" href="<c:url value="/cusvc/datacomplaint/pageModify.do?nav=MNU022&datacomplainOid=${param.datacomplainOid}"/>">수정</a>
            <a class="button button__danger" href="<c:url value="/cusvc/datacomplaint/pageList.do?nav=MNU022"/>">목록</a>
          </div>
        </div>
        <div class="sub__content">
          <h4 class="sub__heading2">신고하기 답변</h4>
          
          <div v-if="AnswerRegistshow == false && datacomplaint.datacomplainAnswer != null">
          <table class="detail-table">
            <caption>해당 테이블에 대한 캡션</caption>
            <colgroup>
              <col width="10%">
          		<col width="">
          		<col width="10%">
          		<col width="">
            </colgroup>
            <tbody>
              <tr>
                <th scope="row">답변자</th>
                <td>{{datacomplaint.datacomplainAnswerUsrNm}}</td>
                <th scope="row">답변일시</th>
                <td>{{datacomplaint.datacomplainAnswerDt|date}}</td>
              </tr>
              <tr>
                <th scope="row">답변</th>
                <td class="text--left" colspan="3" v-html="$options.filters.enter(datacomplaint.datacomplainAnswer)"></td>
              </tr>
              <tr>
                <th scope="row">첨부파일</th>
                <td colspan="3" class="text--left">
                  <ul class="attach-file__list">
                    <li class="attach-file__item--none" v-if="datacomplaintAnswer.AnswerfileList.length === 0">첨부파일이 없습니다.</li>
                    <li class="attach-file__item material-icons"  v-for="(file, index) in datacomplaintAnswer.AnswerfileList">
                    <a @click="download(file.dcpFileOid)">
                        {{file.dcpFileOrgNm}}<span class="attach-file__item--size">({{file.dcpFileSizeByte | size}})</span>
                    </a>
                    </li>
                  </ul>
                </td>
              </tr>
            </tbody>
          </table>
          </div>
          <div v-if="AnswerRegistshow == false && datacomplaint.datacomplainAnswer == null">
          <table class="detail-table">
            <caption>해당 테이블에 대한 캡션</caption>
            <colgroup>
             	<col width="10%">
          		<col width="">
          		<col width="10%">
          		<col width="">
            </colgroup>
            <tbody>
              <tr>
                <th scope="row">답변자</th>
                <td></td>
                <th scope="row">답변일시</th>
                <td></td>
              </tr>
              <tr>
                <th scope="row">답변</th>
                <td colspan="3" class="text--left">  </td>
              </tr>
            </tbody>
          </table>
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
					fileList : []
				},
				datacomplaintAnswer: {
					datacomplainOid: null,
					AnswerfileList : [],
					datacomplainAnswer:""
				},
	        	AnswerRegistshow : false,
	        	LoginUserId : "${sessionScope.userInfo.userId}",
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
						url: SMC_CTX + "/cusvc/datacomplaint/get.do",
						method: "GET",
						dataType: "json",
						data: vm.datacomplaint,
							  nohit : nohit
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
						url: SMC_CTX + "/cusvc/datacomplaint/remove.do",
						method: "DELETE",
						contentType: "application/json",
						dataType: "json",
						data: JSON.stringify(vm.datacomplaint)
					});
					request.done(function(data){
						Modal.OK();
						location.href = SMC_CTX + "/cusvc/datacomplaint/pageList.do";
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
						url: SMC_CTX + "/cusvc/datacomplaint/answer/modify.do",
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
				},
				download: function(dcpFileOid){
					location.href = SMC_CTX + "/cusvc//datacomplaint/downloadFile.do?datacomplainOid="+vm.datacomplaint.datacomplainOid +"&fileId="+dcpFileOid;
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


