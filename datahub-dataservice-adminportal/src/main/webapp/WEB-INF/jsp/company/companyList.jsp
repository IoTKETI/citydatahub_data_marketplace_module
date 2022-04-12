<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">업체목록</h3>
	<form>
	  <fieldset>
	    <legend>필드셋 제목</legend>
	    <section class="section">
	      <div class="section__header">
			    <h4 class="section__title">업체검색</h4>
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
	             <th>업체명</th>
                  <td>
                  	<input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue">
                  </td>
	            </tr>
	          </tbody>
	        </table>
	      </div>
	      <div class="button__group">
	        <button class="button__primary" type="button" @click="getCompanyList(1)">검색</button>
	      </div>
	    </section>
	  </fieldset>
	</form>
	
	<section class="section">
	  <div class="section__header">
	    <h4 class="section__title">업체목록</h4>
	  </div>
	  <div class="section__content">
	  
	    <el-table :data="list" border style="width: 100%" empty-text="검색 결과가 존재하지 않습니다." v-loading="loading" @sort-change="onSortChange" @row-click="onRowClick" :default-sort = "{prop: pageInfo.sortField, order: pageInfo.sortOrder}">
		    <el-table-column prop="companyNm" label="업체명" align="left" sortable="custom"></el-table-column>
		    <el-table-column prop="companyUsrNm" label="담당자이름" align="left" sortable="custom"></el-table-column>
		    <el-table-column prop="companyPhone" label="전화번호" sortable="custom"></el-table-column>
		  </el-table>	  
		  <n2m-pagination :pager="pageInfo" @pagemove="getCompanyList"/>
	  </div>
	</section>
	<div class="button__group">
		<c:if test="${writeYn eq 'Y'}">
	  		<button class="button__primary" type="button" onclick="location.href='<c:url value="/company/pageEdit.do"/>'">등록</button>
	  	</c:if>
	  <!-- <button class="button__primary" type="button"  @click="openCompanyPopup">업체 팝업</button> -->
	</div>
	<!-- <jsp:include page="/WEB-INF/jsp/company/popup/companyPopup.jsp"/>-->
	
</div>


<script>
  $(function(){
	  Vue.use(N2MPagination);
	  vm = new Vue({
		  el: '#content',
		  data: function(){
			  return {
				  list: [],
				  popupVisible: false,
				  popupEdit: false,
				  PopupInfo : {
	    	        	title: "관리자 추가"
	    		  },
	    		  loading: true
				}
			},
			methods: {
				getCompanyList: function(curPage){
					vm.loading = true;
					vm.pageInfo.schCondition = "companyNm";
					vm.pageInfo.curPage = curPage;
				  var request = $.ajax({
					  url: N2M_CTX + "/company/getList.do",
					  method: "GET",
					  dataType: "json",
					  data: {
							schCondition  : vm.pageInfo.schCondition,
							schValue      : vm.pageInfo.schValue,
							sortField     : vm.pageInfo.sortField,
							sortOrder     : vm.pageInfo.sortOrder,
							curPage       : curPage
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
					location.href = N2M_CTX + "/company/pageDetail.do?companyOid="+row.companyOid;
				},
				openCompanyPopup: function(){ //팝업 TEST
					vm.getCompanyList(1);
					vm.popupEdit = true;
					vm.popupVisible = true;
				},
				closeCompanyPopup: function(){ //팝업 TEST
					//user LIst 업데이트 
					vm.popupEdit = false;
					vm.popupVisible = false;
				},
				selectcompany : function(companyOid){ //팝업 TEST - 선택된 업체
					//user LIst 업데이트 
					vm.popupEdit = false;
					vm.popupVisible = false;
				},
				onSortChange: function(column){
					vm.pageInfo.sortField=column.prop;
					vm.pageInfo.sortOrder=column.order;
					//최초 1번 실행시 여기서 실행(검색조건유지용)
					vm.keepPageInfo(function(curPage){
						vm.getCompanyList(curPage);
					});
				}
			}
		});
	  vm.keepPageInfo(function(curPage){
			if(!vm.pageInfo.sortField && !vm.pageInfo.sortOrder){
				vm.getCompanyList(curPage);
			}
		});
	});
</script>
