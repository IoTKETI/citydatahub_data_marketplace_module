<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">사용자목록</h3>
	<form>
	  <fieldset>
	    <legend>필드셋 제목</legend>
	    <section class="section">
	      <div class="section__header">
			    <h4 class="section__title">사용자 검색</h4>
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
	                <select class="select select--full" v-model="pageInfo.schCondition2">
	                  <option value="">--선택--</option>
	                  <c:forEach var="code" items="${n2m:getCodeList('CG_ID_PORTAL_TYPE')}">
		                  <option value="${code.codeId}">${code.codeName}</option>
	                  </c:forEach>
	                </select>
	              </td>
	              <th>검색어</th>
	              <td>
	                <select class="select" v-model="pageInfo.schCondition">
	                  <option disabled value="">--선택--</option>
	                  <option value="userid">사용자ID</option>
	                  <option value="usernm">이름</option>
	                </select>
	                <input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue" @keypress.enter.prevent="getUserList(1)">
	              </td>
	            </tr>
	          </tbody>
	        </table>
	      </div>
	      <div class="button__group">
	        <button class="button__primary" type="button" @click="getUserList(1)">검색</button>
	      </div>
	    </section>
	  </fieldset>
	</form>
	
	<section class="section">
		<div class="section__header">
			<h4 class="section__title">사용자 목록</h4>
		</div>
		<div class="section__content">
			<el-table :data="list" border style="width: 100%" empty-text="검색 결과가 존재하지 않습니다." v-loading="loading" @row-click="onRowClick" @sort-change="onSortChange" :default-sort = "{prop: pageInfo.sortField, order: pageInfo.sortOrder}">
				<el-table-column prop="userId" label="사용자 아이디" align="left" sortable="custom"></el-table-column>
<!-- 				<el-table-column prop="userGbCd" label="사용자구분" width="180" sortable="custom"></el-table-column> -->
				<el-table-column prop="name" label="사용자이름" align="center" sortable="custom"></el-table-column>
				<el-table-column prop="email" label="이메일" align="left" sortable="custom"></el-table-column>
			</el-table>
<!-- 			<n2m-pagination :pager="pageInfo" @pagemove="getUserList"/> -->
		</div>
	</section>
	<div class="sub__bottom">
		<div>
			<n2m-pagination :pager="pageInfo" @pagemove="getUserList"/>
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
				loading: true
			}
		},
		methods: {
			getUserList: function(curPage){
				vm.pageInfo.curPage = curPage;
				var searchUserType ='';
				var searchUser ='';
				vm.loading = true;
				
				if(vm.pageInfo.schCondition2 == 'adminPortal'){
					searchUserType = "admin";
				}else if(vm.pageInfo.schCondition2 == 'normalPortal'){
					searchUserType =  "normal";
				}
				
				if(vm.pageInfo.schCondition == 'userid'){
					searchUser = "&userid="+ vm.pageInfo.schValue;
					
				}else if(vm.pageInfo.schCondition == 'usernm'){
					searchUser = "&usernm=" + vm.pageInfo.schValue;
				}
				
				
				var request = $.ajax({
					url: N2M_CTX + "/user/getList.do?user=" + searchUserType + searchUser,
					method: "GET",
					dataType: "json",
					data: {
						schCondition : vm.pageInfo.schCondition,
                        schValue     : vm.pageInfo.schValue,
                        schCondition2 : vm.pageInfo.schCondition2,
                        curPage      : vm.pageInfo.curPage,
                        pageListSize : 10
                    
					}
				});
				request.done(function(data){
					if(vm.pageInfo.schCondition2 == 'adminPortal'){
						vm.list = data.list;
					}else if(vm.pageInfo.schCondition2 == 'normalPortal'){
						vm.list = data.userList;
					}else{
						vm.list = data.userList;
					}
					vm.pageInfo = data.page;
					vm.loading = false;
					
				});
				request.fail(function(data){
					vm.loading = false;
				});
			},
			onRowClick: function(row){
				location.href = N2M_CTX + "/user/pageDetail.do?userId="+row.userId;
			},
			onSortChange: function(column){
				vm.pageInfo.sortField=column.prop;
				vm.pageInfo.sortOrder=column.order;
				vm.keepPageInfo(function(curPage){
					vm.getUserList(curPage);
				});
			}
		}
	});

	vm.keepPageInfo(function(curPage){
		if(!vm.pageInfo.sortField && !vm.pageInfo.sortOrder){
			vm.getUserList(curPage);
		}
	});
});

</script>