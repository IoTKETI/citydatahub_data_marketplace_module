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
              <th scope="row">제목</th>
              <td colspan="3"><input class="input" title="제목" ref="title" v-model="adaptor.title" disabled></td>
            </tr>
            <tr>
              <th scope="row">요청상태</th>
              <td><input class="input" title="요청상태" ref="statusName" v-model="adaptor.statusName" disabled></td>
            </tr>
            <tr>
              <th scope="row">내용</th>
              <td colspan="3"><textarea class="textarea" title="내용" ref="content" v-model="adaptor.content" disabled></textarea></td>
            </tr>
            <tr>
              <th scope="row">전송방식</th>
              <td colspan="3">
                <select class="select" name="adTransferType" ref="transferType" v-model="adaptor.transferType" title="전송방식" disabled>
                    <option value="" disabled>선택</option>
                    <c:forEach var="code" items="${n2m:getCodeList('CG_ID_SUBSCRIBE_TYPE')}">
                        <option value="${code.codeId}">${code.codeName}</option>
                    </c:forEach>
                </select>
              </td>
            </tr>
            <tr>
              <th scope="row">전송방식설명</th>
              <td colspan="3"><input class="input" title="전송방식설명" ref="description" v-model="adaptor.description" disabled></textarea></td>
            </tr>
            <tr>
              <th scope="row">데이터모델</th>
              <td colspan="3">
                <div class="filter-form__group">
                  <input type="hidden" ref="adModelId" v-model="adaptor.modelId" title="데이터모델" disabled/>
                  <input class="input" type="search" v-model="adaptor.modelName" readonly/>
                </div>
              </td>
            </tr>
            <tr>
              <th scope="row">데이터필드</th>
              <td colspan="3">
                <table class="board-table">
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
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(field, index) in adaptor.fieldList">
                      <td class="text--center">{{adaptor.fieldList[index].fieldKrName}}</td>
                      <td class="text--center">{{adaptor.fieldList[index].fieldEnName}}</td>
                      <td class="text--center">{{adaptor.fieldList[index].dataType}}</td>
                      <td class="text--center">{{adaptor.fieldList[index].description}}</td>
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
      <button class="button button__outline button__outline--primary" type="button" onclick="location.href='<c:url value="/mypage/adaptor/pageList.do"/>'">목록</button>
    </div>
  </div>
</div>
      

<script>
$(function(){
    vm = new Vue({
        el: '#content',
        data: function(){
            return {
                adaptor: {
                	id                  : "${param.id}",
                    title               : "",
                    content             : "",
                	status              : "",
                	statusName          : "",
                    transferType        : "",
                    description         : "",
                    createdAt           : "",
                    creatorId           : "",
                    modelId             : "",
                    fieldList           : [],
                },
            }
        },
        methods: {
            getAdaptor: function() {
            	vm.loadingFlag = true;
            	
                var request = $.ajax({
                    method: "GET",
                    url: SMC_CTX + "/mypage/adaptor/get.do",
                    dataType: "json",
                    data: vm.adaptor,
                });
                request.done(function(data){
                    vm.adaptor = data;
                    vm.loadingFlag = false;
                });
                
                request.fail(function(data) {
                	vm.loadingFlag = false;
                });
            }
        }
    });
    
    vm.getAdaptor();
});

</script>      


