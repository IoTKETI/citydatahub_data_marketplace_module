<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">코드그룹목록</h3>
	<form>
	  <fieldset>
	    <legend>필드셋 제목</legend>
	    <section class="section">
	      <div class="section__header">
		    <h4 class="section__title">코드그룹 검색</h4>
		  </div>
	      <div class="section__content">
	        <table class="table--row">
	          <caption>테이블 제목</caption>
	          <colgroup>
	            <col style="width:150px">
	            <col style="width:auto">
	          </colgroup>
	          <tbody>
	            <tr>
	              <th>검색어</th>
	              <td>
	                <select class="select" v-model="pageInfo.schCondition">
	                  <option disabled value="">--선택--</option>
	                  <option value="groupid">코드그룹ID</option>
	                  <option value="groupnm">코드그룹명</option>
	                </select>
	                <input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue" @keypress.enter.prevent="getCodeGroupList(1)">
	              </td>
	            </tr>
	          </tbody>
	        </table>
	      </div>
	      <div class="button__group">
        	<button class="button__primary" type="button" @click="getCodeGroupList(1)">검색</button>
	      </div>
	    </section>
	  </fieldset>
	</form>
	
	<section class="section">
		<div class="section__header">
			<h4 class="section__title">코드그룹 목록</h4>
		</div>
		<div class="section__content">
			<el-table :data="list" border style="width: 100%" empty-text="검색 결과가 존재하지 않습니다." v-loading="loading" @row-click="onRowClick" @sort-change="onSortChange">
				<el-table-column prop="codeGroupId" label="코드그룹ID" width="160" sortable="custom"></el-table-column>
				<el-table-column prop="codeGroupName" label="코드그룹명" width="" align="left" sortable="custom"></el-table-column>
				<el-table-column prop="codeGroupDesc" label="코드그룹설명" width="" align="left" sortable="custom"></el-table-column>
				<el-table-column prop="codeGroupCol" label="순서" width="100" sortable="custom"></el-table-column>
				<el-table-column prop="codeGroupUseFl" label="사용여부" width="100" sortable="custom">
					<template slot-scope="scope">
						{{scope.row.codeGroupUseFl | flag}}
					</template>
				</el-table-column>
				<el-table-column prop="codeGroupCreDt" label="등록일시" width="240" sortable="custom">
				    <template slot-scope="scope">
                        {{scope.row.codeGroupCreDt | date}}
                    </template>
				</el-table-column>
			</el-table>	  
        	<n2m-pagination :pager="pageInfo" @pagemove="getCodeGroupList"/>
	  </div>
	</section>
	<div class="button__group">
		<c:if test="${writeYn eq 'Y'}">
			<button class="button__primary" type="button" onclick="location.href='<c:url value="/codegroup/pageEdit.do"/>'">등록</button>
		</c:if>
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
				loading: true
			}
		},
		methods: {
			getCodeGroupList: function(curPage){
				vm.loading = true;
				var request = $.ajax({
					url: N2M_CTX + "/codegroup/getList.do",
					method: "GET",
					dataType: "json",
					data: {
						schCondition : vm.pageInfo.schCondition,
						schValue     : vm.pageInfo.schValue,
						curPage      : curPage,
						sortField    : vm.pageInfo.sortField,
						sortOrder    : vm.pageInfo.sortOrder,
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
				location.href = N2M_CTX + "/codegroup/pageDetail.do?codeGroupId="+row.codeGroupId;
			},
			onSortChange: function(column){
				vm.pageInfo.sortField=column.prop;
				vm.pageInfo.sortOrder=column.order;
				vm.keepPageInfo(function(curPage){
					vm.getCodeGroupList(curPage);
				});
			}
		}
	});

	vm.keepPageInfo(function(curPage){
		if(!vm.pageInfo.sortField && !vm.pageInfo.sortOrder){
			vm.getCodeGroupList(curPage);
		}
	});
});
</script>