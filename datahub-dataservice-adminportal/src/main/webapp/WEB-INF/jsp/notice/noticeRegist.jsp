<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">공지사항등록</h3>
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
            <tr>
              <th>등록자</th>
              <td colspan="3">
                ${sessionScope.user.userNm}
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
                <input id="startPopupDate" name="noticeMainStartDt" title="팝업기간 시작일" class="input__text" type="text" style="width:48%" v-model="notice.noticeMainStartDt" :disabled="isDisabled" title="팝업기간 시작일"/> - 
                <input id="endPopupDate" name="noticeMainEndDt" title="팝업기간 종료일" class="input__text" type="text" style="width:48%" v-model="notice.noticeMainEndDt" :disabled="isDisabled" title="팝업기간 종료일"/>
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
                <input name="noticeTitle" class="input__text" title="제목" type="text" ref="noticeTitle" v-model="notice.noticeTitle" title="제목" required/>
              </td>
            </tr>
            <tr>
              <th class="icon__require">내용</th>
              <td colspan="3">
                <n2m-editor :id="'ir1'" :name="'noticeContent'" title="내용" />
              </td>
            </tr>
            <tr>
              <th>첨부파일</th>
              <td class="file__group" colspan="3">
                <n2m-file-uploader :saved-files="notice.fileList" @select-file="selectFile"/>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
    <div class="button__group">
      <button class="button__primary" type="button" @click="createNotice">저장</button>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/notice/pageList.do"/>'">취소</button>
    </div>
  </fieldset>
</form>
</div>
<script>
$(function() {
    Vue.use(N2MFileUploader);
    Vue.use(N2MEditor);
    vm = new Vue({
        el : '#content',
        data : function() {
            return {
                notice : {
                    noticeTitle : "",
                    noticeContent : "",
                    noticeFirstFl : "N",
                    noticeMainPopFl : "N",
                    noticeMainStartDt : "",
                    noticeMainEndDt : "",
                    noticeCreUsrId : "${sessionScope.user.userId}",
                    fileList : []
                },
                fileList: [],
                isDisabled : true
            }
        },
        mounted: function(){
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
        methods : {
            selectFile: function(files){
                vm.fileList = files;
            },
            createNotice : function() {
            	
				validate.extend(validate.validators.datetime, {
					parse: function(value, options) {
					  return +moment.utc(value);
					},
					format: function(value, options) {
					  var format = options.dateOnly ? "YYYY-MM-DD" : "YYYY-MM-DD hh:mm:ss";
					  return moment.utc(value).format(format);
					}
				});
				
                Valid.init(vm, vm.notice);
                var constraints = {};
                
                constraints = Valid.getConstraints();
                
            	oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
            	vm.notice.noticeContent = document.getElementById("ir1").value;
                constraints.noticeContent.custom = {targetId: "ir1", label:"내용", message: "필수 입력 값 입니다."};
                
                if(vm.notice.noticeMainPopFl == 'Y'){
                    constraints.noticeMainStartDt.datetime = {datetime : true, message: MSG_VALID_INVALID};
                    constraints.noticeMainEndDt.datetime = {datetime : true, message: MSG_VALID_INVALID};
                }
                
                if(Valid.validate(constraints)) return;

                if(Modal.REGIST()) return;
                
                var formData = new FormData(document.getElementById('noticeForm'));
                for(var i=0; i<vm.fileList.length; i++){
                    formData.append("files"+(i+1), vm.fileList[i]);
                }
                var request = $.ajax({
                    method: 'POST',
                    url : N2M_CTX + "/notice/create.do",
                    enctype: "multipart/form-data",
                    processData: false,
                    contentType: false,
                    data: formData
                });
                request.done(function(data){
                	Modal.OK();
                    location.href = N2M_CTX + "/notice/pageList.do";
                });
            },
            openPopupInput: function(){
                if(vm.notice.noticeMainPopFl == 'Y'){
                    vm.isDisabled = true;
                    vm.notice.noticeMainStartDt = "";
                    vm.notice.noticeMainEndDt = "";
                } else {
                    vm.isDisabled = false;
                }
            }
        }
    });
    
});
</script>