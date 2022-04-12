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
  <h3 class="sub__heading1">데이터 분석가요청 내역<small class="sub__heading1--small">카테고리별 다양한 데이터를 확인하여 보시기 바랍니다.</small></h3>
  <div class="sub__content">
    <form class="search-form" action="">
      <fieldset>
        <legend>데이터 검색 폼</legend>
        <table class="form-table">
          <caption>데이터 검색 테이블</caption>
          <colgroup>
            <col width="6%">
            <col width="*">
            <col width="6%">
            <col width="*">
            <col width="14%">
          </colgroup>
          <tbody>
            <tr>
              <th scope="row">검색유형</th>
              <td>
                <select class="select" v-model="pageInfo.schCondition2">
                  <option value="">선택</option>
                  <option value="arModelOid">분석가승인요청ID</option>
                  <option value="arReasonApply">신청사유</option>
                </select>
                <input class="input input__search" type="search" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue2" @keypress.enter.prevent="getAnalyList(1)">
              </td>
              <th scope="row">기간</th>
              <td>
                <div class="datepicker">
                  <label class="label__datepicker material-icons"><input class="input input__datepicker" id="fromDate" type="text" v-model="pageInfo.fromDate"></label><span class="period">~</span><label class="label__datepicker material-icons"><input class="input input__datepicker" id="toDate" type="text" v-model="pageInfo.toDate"></label>
                </div>
              </td>
              <td rowspan="2"><button class="button__submit" type="button" @click="getAnalyList(1)">검색</button></td>
            </tr>
          </tbody>
        </table>
      </fieldset>
    </form>
  </div>
  <div class="sub__content">
    <h4 class="sub__heading2">데이터 분석가요청 목록</h4>
    <table class="board-table">
      <caption>데이터 분석가요청 목록</caption>
      <colgroup>
        <col width="25%">
        <col width="10%">
        <col width="15%">
        <col width="30%">
        <col width="20%">
      </colgroup>
      <thead>
        <tr>
          <th scope="col">분석가승인요청ID</th>
          <th scope="col">상태</th>
          <th scope="col">데이터모델 건수</th>
          <th scope="col">신청사유</th>
          <th scope="col">등록일자</th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="list.length === 0">
            <td colspan="5">검색된 데이터가 없습니다.</td>
        </tr>
        <tr v-for="data in list" @click="onRowClick(data)">
          <th class="text--center" scope="row">{{data.id}}</th>
          <td class="text--center">{{data.requestStatusName}}</td>
          <td class="text--center">{{data.modelCount}}</td>
          <td class="text--left">{{data.reason}}</td>
          <td class="text--center">{{data.createdAt|date}}</td>
        </tr>
      </tbody>
    </table>
  </div>
  <div class="sub__bottom">
    <div>
      <n2m-pagination :pager="pageInfo" @pagemove="getAnalyList"/>
    </div>
    <div class="button__group">
      <button class="button button__primary" type="button" onclick="location.href='<c:url value="/mypage/analy/pageEdit.do"/>'">등록</button>
    </div>
  </div>
</div>

<script>
$(function(){
	Vue.use(N2MPagination);
	vm = new Vue({
		el: '#content',
		data: function(){
			return {
				list: [],
				codeList: []
			}
		},
		methods: {
			getAnalyList: function(curPage) {
				vm.pageInfo.curPage = curPage;
				
				var request = $.ajax({
					url: SMC_CTX + "/mypage/analy/getList.do",
					method: "GET",
					dataType: "json",
					data: {
						schCondition2: vm.pageInfo.schCondition2,
						schValue2    : vm.pageInfo.schValue2,
						schValue3    : vm.loginUserId,
						fromDate     : vm.pageInfo.fromDate,
						toDate       : vm.pageInfo.toDate,
						curPage      : vm.pageInfo.curPage
					}
				});
				
				request.done(function(data) {
					vm.list = data.list;
					vm.pageInfo = data.page;
				});
				
				request.fail(function(data) {
					console.log("err => " + data);
				});
			},
			onRowClick: function(row) {
				location.href = SMC_CTX + "/mypage/analy/pageDetail.do?id=" + row.id;
			}
		}
	});
	
	vm.keepPageInfo(function(curPage) {
		vm.getAnalyList(curPage);
    });
});

</script>

