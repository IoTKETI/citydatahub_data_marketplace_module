<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">데이터 분석가요청 목록</h3>
	<form>
	  <fieldset>
	    <legend>필드셋 제목</legend>
	    <section class="section">
	      <div class="section__header">
			    <h4 class="section__title">데이터분석가요청 검색</h4>
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
	             <!-- <th>카테고리</th>
                  <td>
	                <select class="select select--full" v-model="pageInfo.schValue">
	                  <option disabled value="">--선택--</option>
	                  <option value="">1</option>
	                  <option value="">2</option>
	                </select>
	              </td> -->
	              <th>신청사유</th>
	              <td>
                    <input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue2" @keypress.enter.prevent="getAnalyList(1)">
                  </td>
                  <th>기간</th>
                  <td>
                    <div class="picker__group">
                        <label class="label__picker"><input class="input__picker" id="fromDate" type="text" v-model="pageInfo.fromDate"></label><span class="picker__period">~</span><label class="label__picker"><input class="input__picker" id="toDate" type="text" v-model="pageInfo.toDate"></label>
                    </div>
                  </td>
	            </tr>
	          </tbody>
	        </table>
	      </div>
	      <div class="button__group">
	        <button class="button__primary" type="button" @click="getAnalyList(1)">검색</button>
	      </div>
	    </section>
	  </fieldset>
	</form>
	
	<section class="section">
	  <div class="section__header">
	    <h4 class="section__title">데이터분석가요청 목록</h4>
	  </div>
	  <div class="section__content">
	  
	    <!-- <el-table ref="analyTable" :data="analyList" border style="width: 100%" empty-text="검색 결과가 존재하지 않습니다." v-loading="loading" @selection-change="handleSelectionChange" @sort-change="onSortChange" :default-sort = "{prop: pageInfo.sortField, order: pageInfo.sortOrder}"> -->
	    <el-table ref="analyTable" :data="analyList" border style="width: 100%" empty-text="검색 결과가 존재하지 않습니다." v-loading="loading" @row-click="onRowClick" @sort-change="onSortChange" :default-sort = "{prop: pageInfo.sortField, order: pageInfo.sortOrder}">
		    <!-- <el-table-column type="selection" label="" width="80"></el-table-column> -->
		    <el-table-column prop="id" label="ID" align="center" header-align="center" sortable="custom"></el-table-column>
		    <el-table-column prop="requestStatusName" label="상태" align="center" header-align="center" sortable="custom"></el-table-column>
		    <el-table-column prop="reason" label="신청사유" align="left" width="300" header-align="center" sortable="custom"></el-table-column>
		    <el-table-column prop="creatorName" label="등록자" align="left" header-align="center" sortable="custom"></el-table-column>
		    <el-table-column prop="createdAt" label="등록일자" align="center" header-align="center" sortable="custom">
		    	<template slot-scope="scope">
			  	    {{scope.row.createdAt|date}}
				</template>
		    </el-table-column>
		  </el-table>	  
		  <n2m-pagination :pager="pageInfo" @pagemove="getAnalyList"/>
	  </div>
	</section>
	
	<div class="button__group">
        <!-- <button class="button__primary" type="button" @click="modifyAnaly('apprComplete')">승인</button> -->
    </div>
</div>

<script>
  $(function(){
	  Vue.use(N2MPagination);
	  vm = new Vue({
		  el: '#content',
		  data: function(){
			  return {
				  analyList: [],
				  modifyData: {
					  id: "",
				  },
				  loading: true,
				  codeList: []
				}
			},
			methods: {
				getAnalyList: function(curPage){
					vm.loading = true;
					vm.pageInfo.curPage = curPage;
					vm.pageInfo.schValue = "apprWait";
					vm.pageInfo.schCondition2 = "arReasonApply";
				  var request = $.ajax({
					  url: N2M_CTX + "/analy/getList.do",
					  method: "GET",
					  dataType: "json",
					  data: {
						  schCondition2 : vm.pageInfo.schCondition2,
                          schValue      : vm.pageInfo.schValue,
                          schValue2     : vm.pageInfo.schValue2,
                          fromDate      : vm.pageInfo.fromDate,
                          toDate        : vm.pageInfo.toDate,
                          sortField     : vm.pageInfo.sortField,
  						  sortOrder     : vm.pageInfo.sortOrder,
                          curPage       : vm.pageInfo.curPage
					  }
					});
				  request.done(function(data){
					  vm.loading = false;
					  vm.analyList = data.list;
					  vm.pageInfo = data.page;
					});
				  request.fail(function(data){
						vm.loading = false;
					});
				},
				onSortChange: function(column){
					vm.pageInfo.sortField=column.prop;
					vm.pageInfo.sortOrder=column.order;
					vm.keepPageInfo(function(curPage){
						vm.getAnalyList(curPage);
					});
				},
				onRowClick: function(row) {
					location.href = N2M_CTX + "/analy/pageDetail.do?id="+row.id;
				}
			}
		});
	    vm.keepPageInfo(function(curPage){
			if(!vm.pageInfo.sortField && !vm.pageInfo.sortOrder){
				vm.getAnalyList(curPage);
			}
		});
	});
</script>
