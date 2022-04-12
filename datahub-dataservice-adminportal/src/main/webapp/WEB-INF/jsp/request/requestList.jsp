<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">데이터셋 요청 목록</h3>
	<form id="requestForm">
		<fieldset>
		<legend>필드셋 제목</legend>
		<section class="section">
			<div class="section__header">
				<h4 class="section__title">데이터셋 검색</h4>
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
						<th>구분</th>
						<td>
						<select class="select" v-model="pageInfo.searchType">
							<option value="status">상태</option>
							<option value="title">데이터셋명</option>
						</select>
						<input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.searchTitle" v-if="pageInfo.searchType === 'title'" @keypress.enter="getRequestList(1)">
						<select class="select" v-model="pageInfo.searchStatus" v-if="pageInfo.searchType === 'status'">
							<option value="request_all">전체</option>
							<c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATASET_STATUS')}">
								<c:if test="${fn:contains(code.codeId, 'Request')}">
									<option value="${code.codeId}">${code.codeName}</option>
								</c:if>
							</c:forEach>
						</select>
						</td>
						<th>등록일</th>
						<td>
						<div class="picker__group">
							<label class="label__picker"><input class="input__picker" autocomplete="off" id="fromDate" type="text"></label><!-- 
							--><span class="picker__period">~</span><!-- 
						  --><label class="label__picker"><input class="input__picker" autocomplete="off" id="toDate" type="text"></label>
						</div>
						</td>
					</tr>
				</tbody>
			</table>
			</div>
			<div class="button__group">
			<button class="button__primary" type="button" @click="getRequestList(1)">검색</button>
			</div>
		</section>
		</fieldset>
	</form>
	
	<section class="section">
		<div class="section__header">
		<h4 class="section__title">데이터셋 목록</h4>
		</div>
		<div class="section__content">
			<el-table :data="list" border style="width: 100%" empty-text="검색 결과가 존재하지 않습니다." v-loading="loading" @row-click="onRowClick">
				<!-- <el-table-column type="selection" label="" width="80"></el-table-column> -->
				<el-table-column prop="id" label="ID" align="center" header-align="center"></el-table-column>
				<el-table-column prop="title" label="데이터셋 명" align="left" width="300" header-align="center"></el-table-column>
				<el-table-column prop="statusName" label="상태" align="center" header-align="center"></el-table-column>
				<el-table-column prop="creatorName" label="등록자" align="left" header-align="center"></el-table-column>
				<el-table-column prop="createdAt" label="등록일자" align="center" header-align="center">
					<template slot-scope="scope">
						{{scope.row.createdAt|date}}
					</template>
				</el-table-column>
				<el-table-column prop="request" label="승인/반려" align="center" header-align="center">
					<template slot-scope="scope">
						<el-button class="el-button--success is-round" @click="approveDataset(scope.row)">승인</el-button>
						<el-button class="el-button--danger is-round" @click="rejectDataset(scope.row)">반려</el-button>
					</template>
				</el-table-column>
			</el-table>
			<n2m-pagination :pager="pageInfo" @pagemove="getRequestList"/>
		</div>
	</section>
	
	<!-- <div class="button__group">
		<button class="button__primary" type="button" @click="modifyRequest('prodMode')">승인</button>
		<button class="button__secondary" type="button" @click="modifyRequest('requestReject')">반려</button>
	</div> -->
</div>
 
<script>
$(function(){
	Vue.use(N2MPagination);
	vm = new Vue({
		el: '#content',
		data: function(){
			return {
				list: [],
				pageInfo: {
					searchType   : "status",
					searchStatus : "request_all",
					searchTitle  : "",
				},
				loading: true,
			}
		},
		methods: {
			getRequestList: function(curPage){
				vm.loading = true;
				vm.pageInfo.curPage = curPage;
				
				var request = $.ajax({
					method: "GET",
					url: N2M_CTX + "/request/getList.do",
					data: {
						curPage      : vm.pageInfo.curPage,
						fromDate     : vm.pageInfo.fromDate,
						toDate       : vm.pageInfo.toDate,
						searchType   : vm.pageInfo.searchType,
						searchTitle  : vm.pageInfo.searchTitle,
						searchStatus : vm.pageInfo.searchStatus,
					},
					dataType: "json",
				});
				request.done(function(data){
					vm.loading  = false;
					vm.list     = data.list;
					vm.pageInfo	= $.extend(vm.pageInfo, data.page);
				});
				request.fail(function(data){
					vm.loading = false;
				});
			},
			approveDataset: function(row){
				if(!confirm("승인하시겠습니까?")) return;
				var nextStatus = "";
				if(row.status === "releaseRequest"){
					nextStatus = "prodMode";
				}else if(row.status === "paidRequest"){
					nextStatus = "paidMode";
				}
				
				var request = $.ajax({
					method: 'PATCH',
					url : N2M_CTX + "/dataset/modify.do",
					contentType: "application/json",
					data: JSON.stringify({
						id     : row.id,
						status : nextStatus,
					})
				});
				
				request.done(function(data){
					Modal.OK();
					location.href = N2M_CTX + "/request/pageList.do";
				});
				
				request.fail(function(data) {
					vm.loading = false;
				});
			},
			rejectDataset: function(row){
				if(!confirm("반려하시겠습니까?")) return;

				var nextStatus = "";
				if(row.status === "releaseRequest"){
					nextStatus = "releaseReject";
				}else if(row.status === "paidRequest"){
					nextStatus = "paidReject";
				}
				
				var request = $.ajax({
					method: 'PATCH',
					url : N2M_CTX + "/dataset/modify.do",
					contentType: "application/json",
					data: JSON.stringify({
						id     : row.id,
						status : nextStatus,
					})
				});
				
				request.done(function(data){
					Modal.OK();
					location.href = N2M_CTX + "/request/pageList.do";
				});
				
				request.fail(function(data) {
					vm.loading = false;
				});
			},
			modifyRequest: function() {
				
			},
			onRowClick : function(row, col){
				var columnId = "request";
				if(columnId !== col.property){
					window.open("/dataset/pageDetail.do?nav=MNU056&id="+row.id+"&backUrl=/request/pageList.do?nav=MNU014");
				}
			},
		}
	});
	
	vm.keepPageInfo(function(curPage){
		if(!vm.pageInfo.sortField && !vm.pageInfo.sortOrder){
			vm.getRequestList(curPage);
		}
	});
});
</script>
