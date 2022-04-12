<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">공지사항수정</h3>
<form id="noticeForm">
  
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
            <input type="hidden" name="noticeOid" value="${param.noticeOid}" />
            <input type="hidden" name="deleteFileOids" v-model="notice.deleteFileOids" />
            <tr>
              <th>번호</th>
              <td colspan="1">{{notice.noticeId}}</td>
              <th>등록자</th>
              <td colspan="1">
                {{notice.noticeCreUsrNm}}
              </td>
            </tr>
            <tr>
              <th>팝업유무</th>
              <td colspan="1">
                <input type="hidden" name="noticeMainPopFl" v-model="notice.noticeMainPopFl" />
                <input id="checkboxPopup" class="input__checkbox" title="팝업유무" type="checkbox" v-model="notice.noticeMainPopFl" true-value="Y" false-value="N" @click="openPopupInput"/>
                <label class="label__checkbox" for="checkboxPopup">메인화면 팝업 설정</label>
              </td>
              <th>팝업기간</th>
              <td colspan="1">
                <input id="startPopupDate" name="noticeMainStartDt" class="input__text" title="팝업기간 시작일" type="text" style="width:48%" v-model="notice.noticeMainStartDt" :disabled="isDisabled"/> - 
                <input id="endPopupDate" name="noticeMainEndDt" class="input__text" title="팝업기간 종료일" type="text" style="width:48%" v-model="notice.noticeMainEndDt" :disabled="isDisabled"/>
              </td>
            </tr>
            <tr>
              <th>우선공지여부</th>
              <td colspan="3">
                <input type="hidden" name="noticeFirstFl" v-model="notice.noticeFirstFl" />
                <input id="checkboxFirstFl" class="input__checkbox" title="우선공지여부" type="checkbox" v-model="notice.noticeFirstFl" true-value="Y" false-value="N"/>
                <label class="label__checkbox" for="checkboxFirstFl">공지사항 목록의 최상위에 표시됩니다.</label>
              </td>
            </tr>
            <tr>
              <th class="icon__require">제목</th>
              <td colspan="3">
                <input name="noticeTitle" class="input__text" title="제목" type="text" ref="noticeTitle" v-model="notice.noticeTitle" required/>
              </td>
            </tr>
            <tr>
              <th class="icon__require">내용</th>
              <td colspan="3">
                <n2m-editor :id="'ir1'" :name="'noticeContent'" title="내용" @onappload="getNotice"/>
              </td>
            </tr>
            <tr>
              <th>첨부파일</th>
              <td class="file__group" colspan="3">
                <n2m-file-uploader :saved-files="notice.fileList"  @select-file="selectFile" @delete-file="deleteFile"/>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
    <!-- //section-default -->
    <div class="button__group">
      <c:if test="${modifiedYn eq 'Y'}">
        <button class="button__primary" type="button" @click="modifyNotice">저장</button>
      </c:if>
      <c:if test="${deleteYn eq 'Y'}">
        <button class="button__secondary" type="button" @click="deleteNotice">삭제</button>
      </c:if>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/notice/pageDetail.do?noticeOid=${param.noticeOid}"/>'">취소</button>
    </div>
  </fieldset>
</form>
</div>
<script>
  var oEditors = [];
  $(function(){
        Vue.use(N2MFileUploader);
        Vue.use(N2MEditor);
        vm = new Vue({
            el: '#content',
            data: function(){
                return {
                    notice: {
                        noticeOid: "${param.noticeOid}",
                        fileList : [],
                        deleteFileOids : []
                    },
                    fileList : [],
                    deleteFileOids: [],
                    isDisabled : true
                }
            },
            mounted: function(){
                var vm = this;
                $("#startPopupDate").datetimepicker({
                    timeFormat:'HH:mm:ss',
                    controlType:'select',
                    oneLine:true,
                    onSelect: function(date){
                        $("#endPopupDate").datetimepicker('option', 'minDate', new Date(date));
                        vm.notice.noticeMainStartDt = date;
                    }
                });
                $("#endPopupDate").datetimepicker({
                    timeFormat:'HH:mm:ss',
                    hour:23,
                    minute:59,
                    second:59,
                    controlType:'select',
                    oneLine:true,
                    onSelect: function(date){
                        $("#startPopupDate").datetimepicker('option', 'maxDate', new Date(date));
                        vm.notice.noticeMainEndDt = date;
                    }
                });
            },
            methods: {
                getNotice: function(nohit){
                    var request = $.ajax({
                        url: N2M_CTX + "/notice/get.do",
                        method: "GET",
                        dataType: "json",
                        data: {
                        		noticeOid: "${param.noticeOid}",
                        		nohit    : "yes"
                        }
                    });
                    request.done(function(data){
                    	vm.notice = data;
                    	
                    	vm.$nextTick(function(){
                    		var sHTML = vm.notice.noticeContent;
    	                    oEditors.getById["ir1"].exec("PASTE_HTML", [sHTML]);
                    	});
                    	
                        var fileList = vm.notice.fileList;
                        if(fileList && fileList.length > 0){
                            vm.notice.fileList = fileList.map(function(file){
                                return {oid: file.noticefileOid, name: file.noticefileOrgNm, size: file.noticefileSizeByte};
                            });
                        }
                        
                        if(vm.notice.noticeMainPopFl == "Y"){
                        	vm.isDisabled = false;
                        } else {
                        	vm.isDisabled = true;
                        }
                    });
                    
                },
                selectFile: function(files){
                    vm.fileList = files;
                },
                download: function(noticefileOid){
                    location.href = N2M_CTX + "/downloadFile.do?menu=notice&oid="+noticefileOid;
                },
                deleteFile: function(index, oid){
                    vm.notice.fileList.splice(index, 1);
                    vm.deleteFileOids.push(oid);
                    vm.notice.deleteFileOids = vm.deleteFileOids;
                },
                deleteNotice: function(){
                	if(Modal.DELETE()) return;
                	
                    var request = $.ajax({
                        url: N2M_CTX + "/notice/remove.do",
                        method: "DELETE",
                        contentType: "application/json",
//                         dataType: "json",
                        data: JSON.stringify(vm.notice)
                    });
                    request.done(function(data){
                        Modal.OK();
                        location.href = N2M_CTX + "/notice/pageList.do";
                    });
                    
                },
                modifyNotice : function() {
                	oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
                    vm.notice.noticeContent = document.getElementById("ir1").value;
                	
                    // Before using it we must add the parse and format functions
                    // Here is a sample implementation using moment.js
                    validate.extend(validate.validators.datetime, {
                      // The value is guaranteed not to be null or undefined but otherwise it
                      // could be anything.
                      parse: function(value, options) {
                        return +moment.utc(value);
                      },
                      // Input is a unix timestamp
                      format: function(value, options) {
                        var format = options.dateOnly ? "YYYY-MM-DD" : "YYYY-MM-DD hh:mm:ss";
                        return moment.utc(value).format(format);
                      }
                    });
                	
                    Valid.init(vm, vm.notice);
                    var constraints = {};
                    constraints = Valid.getConstraints();
                    
                    constraints.noticeContent.custom = {targetId: "ir1", label:"내용", message: "필수 입력 값 입니다."};
                    
                    if(vm.notice.noticeMainPopFl == 'Y'){
                        constraints.noticeMainStartDt.datetime = {datetime : true, message: MSG_VALID_INVALID};
                        constraints.noticeMainEndDt.datetime = {datetime : true, message: MSG_VALID_INVALID};
                    }
                    
                    if(Valid.validate(constraints)) return;
                    
                    if(vm.notice.noticeMainPopFl == 'N'){
                        vm.notice.noticeMainStartDt = "";
                        vm.notice.noticeMainEndDt = "";
                    }
                    var formData = new FormData(document.getElementById('noticeForm'));
                    
                    for(var i=0; i<vm.fileList.length; i++){
                        formData.append("files"+i, vm.fileList[i]);
                    }
                    
                    if(Modal.MODIFY()) return;
                    
                    var request = $.ajax({
                        method: 'PUT',
                        url : N2M_CTX + "/notice/modify.do",
                        enctype: "multipart/form-data",
                        processData: false,
                        contentType: false,
                        data: formData
                    });
                    request.done(function(data) {
                    	Modal.OK();
                        location.href = N2M_CTX + "/notice/pageDetail.do?noticeOid="+vm.notice.noticeOid;
                    });
                },
                openPopupInput: function(){
                    if(vm.notice.noticeMainPopFl == 'Y'){
                        vm.isDisabled = true;
                    } else {
                        vm.isDisabled = false;
                    }
                }
            }
        })
    });
</script>