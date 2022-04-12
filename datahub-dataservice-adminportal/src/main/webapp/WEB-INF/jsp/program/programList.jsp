<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">프로그램그룹 목록</h3>
	<form>
	  <fieldset>
	    <legend>필드셋 제목</legend>
	    <section class="section">
	      <div class="section__header">
		    <h4 class="section__title">프로그램 그룹검색</h4>
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
	              <td colspan="3">
	                <select class="select" v-model="pageInfo.schCondition">
	                  <option disabled value="">-선택-</option>
	                  <option value="programGroupNm">프로그램그룹명</option>
	                </select>
	                <input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue" @keypress.enter.prevent="getProgramList(1)"/>
	              </td>
	            </tr>
	          </tbody>
	        </table>
	      </div>
	      <div class="button__group">
	        <button class="button__primary" type="button" @click="getProgramList(1)">검색</button>
	      </div>
	    </section>
	  </fieldset>
	</form>
	
	<section class="section">
		<div class="section__header">
            <h4 class="section__title">프로그램 그룹목록</h4>
		</div>
		<div class="section__content">
		<el-table :data="list" border style="width:100%" empty-text="검색 결과가 존재하지 않습니다." v-loading="loading" @row-click="onRowClick" @sort-change="onSortChange">
			<el-table-column prop="programgroupId" label="번호" width="120" sortable="custom"> </el-table-column>
		    <el-table-column prop="programgroupNm" label="프로그램그룹명" align="left" width="200" sortable="custom"></el-table-column>
		    <el-table-column prop="programgroupDesc" label="프로그램그룹 설명" align="left" sortable="custom"></el-table-column>
		    <el-table-column prop="programgroupUseFl" label="사용여부" width="120" sortable="custom">
		        <template slot-scope="scope">
		            {{ scope.row.programgroupUseFl | flag }}
		        </template>
		    </el-table-column>
		    <el-table-column prop="programgroupCreDt" label="등록일시" width="240" sortable="custom">
		        <template slot-scope="scope">
                    {{ scope.row.programgroupCreDt | date }}
                </template>
		    </el-table-column>
		</el-table>
        <n2m-pagination :pager="pageInfo" @pagemove="getProgramList"/>
	  </div>
	</section>
	<div class="button__group">
	  <c:if test="${writeYn eq 'Y'}">
	       <button class="button__primary" type="button" onclick="location.href='<c:url value="/programgroup/pageEdit.do"/>'">등록</button>
	  </c:if>
	</div>
</div>
<script>
	$(function() {
		Vue.use(N2MPagination);
		vm = new Vue({
			el : '#content',
			data : function() {
				return {
					list : [],
					loading: true
				}
			},
			methods : {
				getProgramList : function(curPage) {
					
					vm.loading = true;
					vm.pageInfo.curPage = curPage;
					var request = $.ajax({
						url : N2M_CTX + "/programgroup/getList.do",
						method : "GET",
						dataType : "json",
						data : {
	                        schCondition : vm.pageInfo.schCondition,
	                        schValue     : vm.pageInfo.schValue,
	                        curPage      : vm.pageInfo.curPage,
	                        sortField     : vm.pageInfo.sortField,
	                        sortOrder     : vm.pageInfo.sortOrder,
	                    }
					});
					request.done(function(data) {
						vm.loading = false;
						vm.list = data.list;
						vm.pageInfo = data.page;
						console.log(data.page);
					});
	                request.fail(function(data){
	                    vm.loading = false;
	                });
				},
                onRowClick: function(row){
                    location.href = N2M_CTX + "/programgroup/pageDetail.do?programGroupOid="+row.programgroupOid;
                },
                onSortChange: function(column){
                    vm.pageInfo.sortField=column.prop;
                    vm.pageInfo.sortOrder=column.order;
                    
                    vm.keepPageInfo(function(curPage){
                        vm.getProgramList(curPage);
                    });
                }
			}
		});
		
		vm.keepPageInfo(function(curPage){
	        if(!vm.pageInfo.sortField && !vm.pageInfo.sortOrder){
	            vm.getProgramList(curPage);
	        }
	    });
	});
</script>
