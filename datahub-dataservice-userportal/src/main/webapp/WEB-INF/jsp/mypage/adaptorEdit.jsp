<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- sub-cover -->
<section class="sub-cover">
  <h3 class="sub-cover__title sub-cover__title--data material-icons">마이페이지 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
</section>
<!-- sub-nav -->
<%@ include file="../tiles/tiles_sub_nav.jspf" %>
<!-- sub-nav -->
<hr>
    
<div class="sub__container">
    <!-- breadcrumb -->
  <%@ include file="../tiles/tiles_breadcrumb.jspf" %>
  <!-- //breadcrumb -->      
  <h3 class="sub__heading1">어댑터 신청 등록<small class="sub__heading1--small">카테고리별 다양한 데이터를 확인하여 보시기 바랍니다.</small></h3>
  <div class="sub__content">
    <form class="form" action="">
      <fieldset>
        <legend>어댑터 신청 등록 폼</legend>
        <table class="form-table">
          <caption>어댑터 신청 테이블</caption>
          <colgroup>
            <col width="10%">
            <col width="*">
            <col width="10%">
            <col width="*">
          </colgroup>
          <tbody>
            <tr>
              <th scope="row"><i class="icon__require">*</i>제목</th>
              <td colspan="3"><input class="input" title="제목" ref="title" v-model="adaptor.title" required></textarea></td>
            </tr>
            <tr>
              <th scope="row">요청상태</th>
              <td>
                <select class="select" disabled>
                  <option value="">신청</option>
                </select>
              </td>
            </tr>
            <tr>
              <th scope="row"><i class="icon__require">*</i>내용</th>
              <td colspan="3"><textarea class="textarea" title="내용" ref="content" v-model="adaptor.content" required></textarea></td>
            </tr>
            <tr>
              <th scope="row"><i class="icon__require">*</i>전송방식</th>
              <td colspan="3">
                <select class="select" name="transferType" ref="transferType" v-model="adaptor.transferType" title="전송방식" required>
                    <option value="" disabled>선택</option>
                    <c:forEach var="code" items="${n2m:getCodeList('CG_ID_SUBSCRIBE_TYPE')}">
                        <option value="${code.codeId}">${code.codeName}</option>
                    </c:forEach>
                </select>
              </td>
            </tr>
            <tr>
              <th scope="row"><i class="icon__require">*</i>전송방식설명</th>
              <td colspan="3"><input class="input" title="전송방식설명" ref="description" v-model="adaptor.description" required></textarea></td>
            </tr>
            <tr>
              <th scope="row"><i class="icon__require">*</i>데이터모델</th>
              <td colspan="3">
	            <div class="filter-form__group">
	              <input type="hidden" ref="modelId" v-model="adaptor.modelId" title="데이터모델" required/>
	              <input class="input" type="search" v-model="adaptor.modelName" readonly/>
	              <button class="button button__secondary" type="button" @click="openPopup('OPEN_POPUP_SELECT_DATA_MODEL')">찾기</button>
	            </div>
              </td>
            </tr>
            <tr v-if="adaptor.modelName">
              <th scope="row"><i class="icon__require">*</i>데이터필드</th>
              <td colspan="3">
                <table class="data-model-table">
                  <caption>데이터필드 목록</caption>
                  <colgroup>
                    <col width="*">
                    <col width="*">
                    <col width="*">
                    <col width="*">
                    <col width="30px">
                  </colgroup>
                  <thead>
                    <tr>
                      <th scope="col">필드명(한글)</th>
                      <th scope="col">필드명(영문)</th>
                      <th scope="col">데이터타입</th>
                      <th scope="col">설명</th>
                      <th scope="col"><button class="button__data-model--add material-icons" type="button" @click="addFieldRow()"><span class="hidden">데이터 필드 추가하기</span></button></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(field, index) in adaptor.fieldList">
                      <td><textarea class="input" v-model="adaptor.fieldList[index].fieldKrName" @keyup="onKeyUp('COPY_FIELD_DATA', index)"></textarea></td>
                      <td><input class="input" type="text" v-model="adaptor.fieldList[index].fieldEnName"></td>
                      <td>
                        <select class="select" v-model="adaptor.fieldList[index].dataType">
                            <option value="String">String</option>
                            <option value="Int">Int</option>
                        </select>
                      </td>
                      <td><input class="input" type="text" v-model="adaptor.fieldList[index].description"></td>
                      <td>
                        <button class="button__data-model--remove material-icons" type="button" @click="onRowClick('DATA_MODEL_ROW_DELETE', index)">
                          <span class="hidden">삭제</span>
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </td>
            </tr>
          </tbody>
        </table>
      </fieldset>
    </form>
  </div>            
  <div class="sub__bottom">
    <div class="button__group">
      <button class="button button__primary" type="button" @click="createAdaptor">저장</button>
      <button class="button button__outline button__outline--primary" type="button" onclick="location.href='<c:url value="/mypage/adaptor/pageList.do"/>'">목록</button>
    </div>
  </div>
  <!-- 코드 등록 팝업 -->
  <jsp:include page="/WEB-INF/jsp/mypage/popup/analyDatamodelPopup.jsp"/>
</div>
      

<script>
$(function(){
    vm = new Vue({
        el: '#content',
        mixins: [analyDatamodelPopupMixin],
        data: function(){
            return {
                adaptor: {
                	id                  : "",
                    title               : "",
                    content             : "",
                	status              : "",
                	statusName          : "",
                    transferType        : "",
                    description         : "",
                    createdAt           : "",
                    creatorId           : "",
                    modelId             : "",
                    modelName           : "",
                    fieldList           : [],
                },
                selectedDataModel: {}
            }
        },
        methods: {
        	isNotEmpty: function(obj){
                return Object.keys(obj).length > 0;
            },
            isEmpty: function(obj){
                return Object.keys(obj).length === 0;
            },
            createAdaptor: function() {
            	Valid.init(vm, vm.adaptor);
                var constraints = {};
                
                constraints = Valid.getConstraints();
                if(Valid.validate(constraints)) return;
            	
                if(vm.adaptor.fieldList.length === 0){
                	alert("데이터 필드를 입력해주세요.");
                	return;
                }
                var request = $.ajax({
                    method: "POST",
                    url: SMC_CTX + "/mypage/adaptor/create.do",
                    contentType: "application/json",
                    data: JSON.stringify(vm.adaptor),
                    dataType: "json"
                });
                request.done(function(data){
                    Modal.OK();
                    location.href = SMC_CTX + "/mypage/adaptor/pageList.do";
                });
                request.fail(function(data) {
                	Modal.ERROR();
                });
            },
            addFieldRow: function(){
            	vm.adaptor.fieldList.push({
            		order            : vm.adaptor.fieldList.length,
            		fieldKrName      : "",
            		fieldEnName      : "",
            		dataType         : "",
            		description      : "",
            	});
            },
            onKeyUp: function(code, val){
            	switch(code){
                case "COPY_FIELD_DATA":
                    var index = val;
                    var fieldKrName = vm.adaptor.fieldList[index].fieldKrName;
                    if(fieldKrName.indexOf('\n') === -1){
                        if(fieldKrName.indexOf('\t') !== -1){
                            var colArr = fieldKrName.split('\t');
                            vm.adaptor.fieldList[index].order         = index;
                            vm.adaptor.fieldList[index].fieldKrName   = colArr[0];
                            vm.adaptor.fieldList[index].fieldEnName   = colArr[1];
                            vm.adaptor.fieldList[index].dataType      = colArr[2];
                            vm.adaptor.fieldList[index].description   = colArr[3];
                        }
                    }else{
                    	if(index !== 0) return;
                        var rowArr = fieldKrName.split('\n');
                        vm.adaptor.fieldList = [];
                        rowArr.forEach(function(row, i){
                            var colArr = row.split('\t');
                            vm.adaptor.fieldList.push({
	                            order       : vm.adaptor.fieldList.length,
	                            fieldKrName : colArr[0],
	                            fieldEnName : colArr[1],
	                            dataType    : colArr[2],
	                            description : colArr[3] 
                            });
                        });
                    }

                    break;
                }
            },
            onRowClick: function(code, val){
            	switch(code){
            	case "DATA_MODEL_ROW_DELETE":
            		vm.adaptor.fieldList.splice(val, 1);
            		break;
            	}
            },
            openPopup: function(code) {
            	switch(code){
            	case "OPEN_POPUP_SELECT_DATA_MODEL":
	                vm.openAnalyDatamodelPopup(function(selectedDataModel) {
	                    vm.adaptor.modelName = selectedDataModel.name;
	                    vm.adaptor.modelId   = selectedDataModel.id;
	                }, true);
            		break;
            	}
            }
        }
    });
});

</script>      
