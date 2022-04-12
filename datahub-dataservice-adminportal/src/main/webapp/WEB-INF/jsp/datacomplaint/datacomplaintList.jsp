<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">데이터민원 목록</h3>
	<form>
	  <fieldset>
	    <legend>필드셋 제목</legend>
	    <section class="section">
	      <div class="section__header">
			    <h4 class="section__title">데이터민원 검색</h4>
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
	                  <option value="datacomplainTitle">제목</option>
	                  <option value="datacomplainCreUsrNm">작성자</option>
	                </select>
	                <input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue" @keypress.enter="getDatacomplaintList(1)">
	              </td>
	              <th>등록일</th>
	              <td>
                    <div class="picker__group">
                    	<label class="label__picker"><input class="input__picker" id="fromDate" type="text" v-model="pageInfo.fromDate"></label><span class="picker__period">~</span><label class="label__picker"><input class="input__picker" id="toDate" type="text" v-model="pageInfo.toDate"></label>
                    </div>
                   </td>
	            </tr>
	            <tr>
	             <th>유형</th>
                  <td>
	                <select class="select select--full" v-model="pageInfo.schValue3">
	                  <option value="">--전체--</option>
	                  <c:forEach var="code" items="${n2m:getCodeList('CG_ID_QNA_TYPE')}">
		                  <option value="${code.codeId}">${code.codeName}</option>
	                  </c:forEach>
	                </select>
	              </td>
	              <th>진행상태</th>
	              <td>
	                <select class="select select--full" v-model="pageInfo.schValue4" >
	                  <option value="">--전체--</option>
	                  <c:forEach var="code" items="${n2m:getCodeList('CG_ID_QNA_STATUS')}">
		                  <option value="${code.codeId}">${code.codeName}</option>
	                  </c:forEach>
	                </select>
	              </td>
	            </tr>
	          </tbody>
	        </table>
	      </div>
	      <div class="button__group">
	        <button class="button__primary" type="button" @click="getDatacomplaintList(1)">검색</button>
	      </div>
	    </section>
	  </fieldset>
	</form>
	
	<section class="section">
	  <div class="section__header">
	    <h4 class="section__title">데이터민원 목록</h4>
	  </div>
	  <div class="section__content">
	  
	    <el-table :data="list" border style="width: 100%" empty-text="검색 결과가 존재하지 않습니다." v-loading="loading" @sort-change="onSortChange" @row-click="onRowClick" :default-sort = "{prop: pageInfo.sortField, order: pageInfo.sortOrder}">
		    <el-table-column prop="datacomplainId" label="번호" width="80" sortable="custom" header-align="center"></el-table-column>
		    <el-table-column prop="datacomplainTitle" label="제목" align="left" width="300" header-align="center" sortable="custom"></el-table-column>
		    <el-table-column prop="datacomplainGbCdNm" label="유형" align="center" header-align="center" sortable="custom"></el-table-column>
		    <el-table-column prop="datacomplainCreUsrNm" label="작성자" align="left" header-align="center" sortable="custom"></el-table-column>
		    <el-table-column prop="datacomplainStatCdNm" label="진행상태" align="center" header-align="center" sortable="custom"></el-table-column>
		    <el-table-column prop="datacomplainCreDt" label="등록일시" align="center" header-align="center" sortable="custom">
		    	<template slot-scope="scope">
			  	    {{scope.row.datacomplainCreDt|date}}
				</template>
		    </el-table-column>
		  </el-table>	  
		  <n2m-pagination :pager="pageInfo" @pagemove="getDatacomplaintList"/>
	  </div>
	</section>
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
				getDatacomplaintList: function(curPage){
					vm.loading = true;
					vm.pageInfo.schCondition2 = "datacomplainCreDt";
					vm.pageInfo.schCondition3 = "datacomplainGbCd";
					vm.pageInfo.schCondition4 = "datacomplainStatCd";
					vm.pageInfo.curPage = curPage;
				  var request = $.ajax({
					  url: N2M_CTX + "/datacomplaint/getList.do",
					  method: "GET",
					  dataType: "json",
					  data: {
						  schCondition : vm.pageInfo.schCondition,
                          schValue     : vm.pageInfo.schValue,
                          schValue3    : vm.pageInfo.schValue3,
                          schValue4    : vm.pageInfo.schValue4,
                          schCondition2 : vm.pageInfo.schCondition2,
                          schCondition3 : vm.pageInfo.schCondition3,
                          schCondition4 : vm.pageInfo.schCondition4,
                          fromDate : vm.pageInfo.fromDate,
                          toDate : vm.pageInfo.toDate,
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
						location.href = N2M_CTX + "/datacomplaint/pageDetail.do?datacomplainOid="+row.datacomplainOid;
				},
				onSortChange: function(column){
					vm.pageInfo.sortField=column.prop;
					vm.pageInfo.sortOrder=column.order;
					vm.keepPageInfo(function(curPage){
						vm.getDatacomplaintList(curPage);
					});
				}
			}
		});
	  vm.keepPageInfo(function(curPage){
			if(!vm.pageInfo.sortField && !vm.pageInfo.sortOrder){
				vm.getDatacomplaintList(curPage);
			}
		});
	});
</script>
