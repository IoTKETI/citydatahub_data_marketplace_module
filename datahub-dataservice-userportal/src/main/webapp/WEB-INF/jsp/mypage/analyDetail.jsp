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
  <h3 class="sub__heading1">데이터 분석가요청 상세<small class="sub__heading1--small">카테고리별 다양한 데이터를 확인하여 보시기 바랍니다.</small></h3>
  <div class="sub__content">
    <form class="form">
      <fieldset>
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
              <th scope="row">요청상태</th>
              <td>
                <select class="select" v-model="resultData.analyData.requestStatus" disabled>
                  <c:forEach var="code" items="${n2m:getCodeList('CG_ID_APPR_REQUEST_STATUS')}">
                      <option value="${code.codeId}">${code.codeName}</option>
                  </c:forEach>
                </select>
              </td>
              <th scope="row"></th>
              <td></td>
            </tr>
            <tr>
              <th scope="row">신청사유</th>
              <td colspan="3"><textarea class="textarea" v-model="resultData.analyData.reason" readonly> </textarea></td>
            </tr>
            <tr>
              <th scope="row">데이터모델</th>
              <td colspan="3">
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
                      <th scope="col"></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="data in resultData.datamodelList">
                      <td><input class="input" type="text" :value="data.modelId" readonly></td>
                      <td><input class="input" type="text" :value="data.modelName" readonly></td>
                      <td></td>
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
      <button class="button button__outline button__outline--primary" type="button" onclick="location.href='<c:url value="/mypage/analy/pageList.do"/>'">목록</button>
    </div>
  </div>
</div>
      

<script>
$(function(){
    vm = new Vue({
        el: '#content',
        data: function(){
            return {
                analy: {
                	id: "${param.id}"
                },
                resultData: {
                	analyData: {},
                	datamodelList: []
                }
            }
        },
        methods: {
            getAnaly: function() {
            	vm.loadingFlag = true;
            	
                var request = $.ajax({
                    method: "GET",
                    url: SMC_CTX + "/mypage/analy/get.do",
                    contentType: "application/json",
                    dataType: "json",
                    data: vm.analy,
                });
                request.done(function(data){
                    vm.resultData = data;
                    
                    vm.loadingFlag = false;
                });
                
                request.fail(function(data) {
                	vm.loadingFlag = false;
                });
            }
        }
    });
    
    vm.getAnaly();
});

</script>      


