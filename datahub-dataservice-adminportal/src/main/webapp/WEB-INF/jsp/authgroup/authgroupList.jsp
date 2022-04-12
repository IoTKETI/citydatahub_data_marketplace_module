<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">권한목록</h3>
	<form>
	  <fieldset>
	    <legend>필드셋 제목</legend>
	    <section class="section">
	      <div class="section__header">
			    <h4 class="section__title">권한그룹 검색</h4>
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
	          <tbody>
	            <tr>
	             <th>검색어</th>
	              <td>
	                <select class="select" v-model="pageInfo.schCondition">
	                  <option disabled value="">--선택--</option>
	                  <option value="authgroupNm">그룹명</option>
	                  <option value="authgroupDesc">설명</option>
	                </select>
	                <input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue" @keypress.enter.prevent="getAuthgroupList(1)">
	              </td>
	              <th>사용여부</th>
	              <td>
	                <select class="select select--full"  v-model="pageInfo.schValue2">
	                  <option value="">--전체--</option>
	                  <option value="Y">예</option>
	                  <option value="N">아니오</option>
	                </select>
	              </td>
	            </tr>
	            <tr>
	            <th>구분</th>
	              <td>
	                <select class="select select--full"  v-model="pageInfo.schValue3">
	                <option value="">--전체--</option>
	                  <c:forEach var="code" items="${n2m:getCodeList('CG_ID_PORTAL_TYPE')}">
		                  <option value="${code.codeId}">${code.codeName}</option>
	                  </c:forEach>
	                </select>
	              </td>
	              <th>사용자 ID</th>
	              <td>
	                <input class="input__text" type="text" placeholder="ID를 입력하세요." v-model="pageInfo.schValue4" @keypress.enter.prevent="getAuthgroupList(1)">
	              </td>
	            </tr>
	          </tbody>
	        </table>
	      </div>
	      <div class="button__group">
	        <button class="button__primary" type="button" @click="getAuthgroupList(1)">검색</button>
	      </div>
	    </section>
	  </fieldset>
	</form>
	
	<section class="section">
	  <div class="section__header">
	    <h4 class="section__title">권한그룹 목록</h4>
	  </div>
	  <div class="section__content">
	    <el-table :data="list" border style="width: 100%" empty-text="검색 결과가 존재하지 않습니다." @row-click="onRowClick" v-loading="loading" @sort-change="onSortChange" :default-sort = "{prop: pageInfo.sortField, order: pageInfo.sortOrder}">
		    <el-table-column prop="authgroupNm" label="그룹명" align="left" header-align="center" sortable="custom"></el-table-column>
		    <el-table-column prop="authgroupDesc" label="설명" align="left" header-align="center"></el-table-column>
		    <el-table-column prop="authgroupUseFl" label="사용여부" align="left" header-align="center" sortable="custom">
		    	<template slot-scope="scope">
		    		{{scope.row.authgroupUseFl|flag}}
			     </template>
		    </el-table-column>
		    <el-table-column prop="authgroupGbCdNm" label="구분 "  align="center" header-align="center" sortable="custom"></el-table-column>
		  </el-table>	  
		  <n2m-pagination :pager="pageInfo" @pagemove="getAuthgroupList"/>
	  </div>
	</section>
	<c:if test="${writeYn eq 'Y'}">
        <div class="button__group">
 			<button class="button__primary" type="button" onclick="location.href='<c:url value="/authgroup/pageEdit.do"/>'">등록</button>
		</div>
    </c:if>
</div>

<script>
  $(function(){
	  Vue.use(N2MPagination);
	  vm = new Vue({
		  el: '#content',
		  data: function(){
			  return {
				  list: [],
				  loading: true
				}
			},
			methods: {
				getAuthgroupList: function(curPage){
					vm.pageInfo.schCondition2 = "authgroupUseFl";
					vm.pageInfo.schCondition3 = "authgroupGbCd";
					vm.pageInfo.schCondition4 = "authgroupuserid";
					vm.pageInfo.curPage = curPage;
				  var request = $.ajax({
					  url: N2M_CTX + "/authgroup/getList.do",
					  method: "GET",
					  dataType: "json",
					  data: {
						  schCondition : vm.pageInfo.schCondition,
						  schCondition2 : vm.pageInfo.schCondition2,
						  schCondition3 : vm.pageInfo.schCondition3,
						  schCondition4 : vm.pageInfo.schCondition4,
                          schValue     : vm.pageInfo.schValue,
                          schValue2     : vm.pageInfo.schValue2,
                          schValue3     : vm.pageInfo.schValue3,
                          schValue4     : vm.pageInfo.schValue4,
                          sortField     : vm.pageInfo.sortField,
  						  sortOrder     : vm.pageInfo.sortOrder,
                          curPage      : vm.pageInfo.curPage
					  }
					});
				  request.done(function(data){
					  vm.loading = false;
					  vm.list = data.list;
					  vm.pageInfo = data.page;
					});
				  request.fail(function(data){
						vm.loading = false;
					});
				},
				onRowClick: function(row){
					location.href = N2M_CTX + "/authgroup/pageDetail.do?authgroupOid="+row.authgroupOid;
				},
				onSortChange: function(column){
					vm.pageInfo.sortField=column.prop;
					vm.pageInfo.sortOrder=column.order;
					//최초 1번 실행시 여기서 실행(검색조건유지용)
					vm.keepPageInfo(function(curPage){
						vm.getAuthgroupList(curPage);
					});
				}
			}
		});
	    vm.keepPageInfo(function(curPage){
			if(!vm.pageInfo.sortField && !vm.pageInfo.sortOrder){
				vm.getAuthgroupList(curPage);
			}
		});
	});
</script>
