<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">FAQ목록</h3>
	<form>
	  <fieldset>
	    <legend>필드셋 제목</legend>
	    <section class="section">
	      <div class="section__header">
			    <h4 class="section__title">FAQ 검색</h4>
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
	             <th>제목</th>
                  <td>
                  	<input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue" @keypress.enter.prevent="getFaqList(1)">
                  </td>
	              <th>등록일</th>
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
	        <button class="button__primary" type="button" @click="getFaqList(1)">검색</button>
	      </div>
	    </section>
	  </fieldset>
	</form>
	
	<section class="section">
	  <div class="section__header">
	    <h4 class="section__title">FAQ 목록</h4>
	  </div>
	  <div class="section__content">
	  
	    <el-table :data="list" border style="width: 100%"  empty-text="검색 결과가 존재하지 않습니다." @row-click="onRowClick" v-loading="loading" @sort-change="onSortChange" :default-sort = "{prop: pageInfo.sortField, order: pageInfo.sortOrder}">
	    	<el-table-column prop="faqId" label="번호" width="120" sortable="custom"></el-table-column>
		    <el-table-column prop="faqTitle" label="제목" align="left" header-align="center" sortable="custom"></el-table-column>
            <el-table-column prop="faqCreUsrNm" label="등록자ID" width="240" align="left" header-align="center" sortable="custom"></el-table-column>
		    <el-table-column prop="faqCreDt" label="등록일시" width="240" align="center" header-align="center" sortable="custom">
			    <template slot-scope="scope">
			  	    {{scope.row.faqCreDt|date}}
				</template>
		    </el-table-column>
		  </el-table>	  
		  <n2m-pagination :pager="pageInfo" @pagemove="getFaqList"/>
	  </div>
	</section>
	<div class="button__group">
		<c:if test="${writeYn eq 'Y'}">
	  		<button class="button__primary" type="button" onclick="location.href='<c:url value="/faq/pageEdit.do"/>'">등록</button>
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
				getFaqList: function(curPage){
					vm.loading = true;
					vm.pageInfo.schCondition = "faqTitle";
					vm.pageInfo.schCondition2 = "faqCreDt";
					vm.pageInfo.curPage = curPage;
				  var request = $.ajax({
					  url: N2M_CTX + "/faq/getList.do",
					  method: "GET",
					  dataType: "json",
					  data: {
						  schCondition 	: vm.pageInfo.schCondition,
                          schValue     	: vm.pageInfo.schValue,
                          schCondition2 : vm.pageInfo.schCondition2,
                          fromDate 		: vm.pageInfo.fromDate,
                          toDate 		: vm.pageInfo.toDate,
                          sortField     : vm.pageInfo.sortField,
  						  sortOrder     : vm.pageInfo.sortOrder,
                          curPage      	: vm.pageInfo.curPage
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
					location.href = N2M_CTX + "/faq/pageDetail.do?faqOid="+row.faqOid;					
				},
				onSortChange: function(column){
					vm.pageInfo.sortField=column.prop;
					vm.pageInfo.sortOrder=column.order;
					
					//최초 1번 실행시 여기서 실행(검색조건유지용)
					vm.keepPageInfo(function(curPage){
						vm.getFaqList(curPage);
					});
				}
			}
		});
	  vm.keepPageInfo(function(curPage){
			if(!vm.pageInfo.sortField && !vm.pageInfo.sortOrder){
				vm.getFaqList(curPage);
			}
		});
	});
</script>
