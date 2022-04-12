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
  <h3 class="sub__heading1">데이터 분석가요청 등록<small class="sub__heading1--small">카테고리별 다양한 데이터를 확인하여 보시기 바랍니다.</small></h3>
  <div class="sub__content">
    <form class="form" action="">
      <fieldset>
        <legend>데이터 분석가요청 등록 폼</legend>
        <table class="form-table">
          <caption>데이터 분석가 요청 테이블</caption>
          <colgroup>
            <col width="10%">
            <col width="*">
            <col width="10%">
            <col width="*">
          </colgroup>
          <tbody>
            <tr>
              <th scope="row"><i class="icon__require">*</i>요청상태</th>
              <td>
                <select class="select" v-model="analy.requestStatus" disabled>
                  <option value="apprWait">승인대기</option>
                </select>
              </td>
              <th scope="row"></th>
              <td></td>
            </tr>
            <tr>
              <th scope="row"><i class="icon__require">*</i>신청사유</th>
              <td colspan="3"><textarea class="textarea" ref="reason" placeholder="신청사유를 입력하시오." title="신청사유" v-model="analy.reason" required></textarea></td>
            </tr>
            <tr>
              <th scope="row"><i class="icon__require">*</i>데이터모델</th>
              <td colspan="3">
                <!-- <table class="board-table"> -->
                <table class="data-model-table">
                  <caption>데이터모델 목록</caption>
                  <colgroup>
                    <col width="*">
                    <col width="*">
                    <col width="30px">
                  </colgroup>
                  <thead>
                    <tr>
                      <th scope="col">데이터모델 ID</th>
                      <th scope="col">데이터모델명</th>
                      <th scope="col"><button class="button__data-model--add material-icons" type="button" @click="openPopup()"><span class="hidden">데이터 모델 추가하기</span></button></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="list in analy.datamodelList">
		              <td><input class="input" type="text" :value="list.id" readonly></td>
		              <td><input class="input" type="text" :value="list.typeUri" readonly></td>
		              <td>
		                <button class="button__data-model--remove material-icons" type="button" @click="onRowClick(list)">
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
      <button class="button button__primary" type="button" @click="createAnaly">저장</button>
      <button class="button button__outline button__outline--primary" type="button" onclick="location.href='<c:url value="/mypage/analy/pageList.do"/>'">목록</button>
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
                list: [],
                analy: {
                	requestStatus: "apprWait",
                	reason: "",
                    datamodelList: []
                }
            }
        },
        methods: {
            createAnaly: function() {
            	Valid.init(vm, vm.analy);
                var constraints = {};
                
                constraints = Valid.getConstraints();
                
                if(Valid.validate(constraints)) return;
            	
                if (vm.analy.datamodelList.length <= 0) {
                    alert("선택된 데이터모델이 없습니다.");
                    return;
                }
                
                if(Modal.REGIST()) return;
                
                var request = $.ajax({
                    method: "POST",
                    url: SMC_CTX + "/mypage/analy/create.do",
                    contentType: "application/json",
                    data: JSON.stringify(vm.analy)
                });
                request.done(function(data){
                    Modal.OK();
                    location.href = SMC_CTX + "/mypage/analy/pageList.do";
                });
                request.fail(function(data) {
                	Modal.ERROR();
                });
            },
            openPopup: function() {
                vm.openAnalyDatamodelPopup(function(selectedDatamodel) {
                    vm.analy.datamodelList = selectedDatamodel;
                });
            },
            onRowClick: function(row) {
                for (var i = 0; i < vm.analy.datamodelList.length; i++) {
                    if (row.id === vm.analy.datamodelList[i].id) {
                        vm.analy.datamodelList.splice(i, 1);
                    }
                }
            }
        }
    });
});

</script>      
