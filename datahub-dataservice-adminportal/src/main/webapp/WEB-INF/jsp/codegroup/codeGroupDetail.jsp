<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">코드그룹상세</h3>
	<form>
	  <fieldset>
		<legend>필드셋 제목</legend>
		<section class="section">
		  <div class="section__header">
			<h4 class="section__title">코드그룹 상세</h4>
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
				  <th>코드그룹ID</th>
				  <td>
					{{codeGroup.codeGroupId}}
				  </td>
				</tr>
				<tr>
				  <th>코드그룹명</th>
				  <td>
					{{codeGroup.codeGroupName}}
				  </td>
				</tr>
				<tr>
				  <th>순서</th>
				  <td>
					{{codeGroup.codeGroupCol}}
				  </td>
				</tr>
				<tr>
				  <th>사용여부</th>
				  <td>
				  	<input class="input__checkbox" id="checkUseFl" type="checkbox" ref="codeGroupUseFl" v-model="codeGroup.codeGroupUseFl" true-value="Y" false-value="N" disabled/><label class="label__checkbox" for="checkUseFl"></label>
				  </td>
				</tr>
				<tr>
				  <th>설명</th>
				  <td v-html="$options.filters.enter(codeGroup.codeGroupDesc)">
				  </td>
				</tr>
			  </tbody>
			</table>
		  </div>
		</section>
		<div class="button__group">
			<c:if test="${modifiedYn eq 'Y'}">
				<button class="button__primary" type="button" onclick="location.href='<c:url value="/codegroup/pageModify.do?codeGroupId=${param.codeGroupId}"/>'">수정</button>
			</c:if>
			<button class="button__primary" type="button" onclick="location.href='<c:url value="/codegroup/pageList.do"/>'">목록</button>
		</div>
	  </fieldset>
	</form>
	
	<section class="section">
		<div class="section__header">
			<h4 class="section__title">코드 목록</h4>
		</div>
		<div class="section__content">
	  
		<el-table :data="codeGroup.codeList" border style="width: 100%" empty-text="검색 결과가 존재하지 않습니다." v-loading="loading" @row-click="openPopup">
			<el-table-column prop="codeId" label="코드ID" width="160"></el-table-column>
			<el-table-column prop="codeName" label="코드명" width=""></el-table-column>
			<el-table-column prop="codeSort" label="순서" width="160"></el-table-column>
			<el-table-column prop="codeUseFl" label="사용여부" width="160">
				<template slot-scope="scope">
					{{scope.row.codeUseFl | flag}}
				</template>
			</el-table-column>
			<el-table-column prop="codeCreDt" label="등록일시" width="160"></el-table-column>
		</el-table>
		<n2m-pagination :pager="pageInfo" @pagemove="getCodeList"/>
	  </div>
	</section>
	<div class="button__group">
		<c:if test="${writeYn eq 'Y'}">
			<button class="button__primary" type="button" @click="openPopup">등록</button>
		</c:if>
	</div>
	<!-- 코드 등록 팝업 -->
	<jsp:include page="/WEB-INF/jsp/codegroup/popup/codeRegistPopup.jsp"/>
</div>
<script>
$(function(){
	Vue.use(N2MPagination);
	vm = new Vue({
		el: '#content',
		mixins: [codeRegistPopupMixin],
		data: function(){
			return {
				codeGroup: {
					codeGroupId: "${param.codeGroupId}",
					codeList: [],
				},
				loading: false
			}
		},
		methods: {
			getCodeGroup: function(){
				var request = $.ajax({
					url: N2M_CTX + "/codegroup/get.do",
					method: "GET",
					dataType: "json",
					data: vm.codeGroup
				});
				request.done(function(data){
					vm.codeGroup = data;
					vm.getCodeList();
				});
			},
			getCodeList: function(curPage){
				var request = $.ajax({
					url: N2M_CTX + "/codegroup/code/getList.do",
					method: "GET",
					dataType: "json",
					data: {
						curPage	  : curPage,
						codeGroupId  : vm.codeGroup.codeGroupId
					}
				});
				request.done(function(data){
					vm.codeGroup.codeList = data.list;
					vm.pageInfo = data.page;
				});
				vm.codeList = [];
			},
			openPopup: function(codeInfo){
				vm.openCodeRegistPopup(codeInfo, vm.codeGroup.codeGroupId, function(){
					vm.getCodeList(1);
				});
			}
		}
	})
	vm.getCodeGroup();
});
</script>
