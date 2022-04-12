<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">가격정책관리목록</h3>
	<form>
	  <fieldset>
	    <legend>필드셋 제목</legend>
	    <section class="section">
	      <div class="section__header">
		    <h4 class="section__title">가격정책관리 검색</h4>
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
                  <th>구분</th>
                  <td>
                    <select class="select" v-model="pageInfo.searchType">
                      <option value="">선택</option>
                      <option value="title">제목</option>
                      <option value="traffic">트래픽유형</option>
                      <option value="use">사용여부</option>
                    </select>
                    <select class="select" v-model="pageInfo.searchTrafficType" v-if="pageInfo.searchType === 'traffic'">
                      <c:forEach var="code" items="${n2m:getCodeList('CG_ID_TRAFFIC_TYPE')}">
                        <option value="${code.codeId}">${code.codeName}</option>
                      </c:forEach>
                    </select>
                    <select class="select" v-model="pageInfo.searchUseTf" v-if="pageInfo.searchType === 'use'">
                        <option value="true">예</option>
                        <option value="false">아니오</option>
                    </select>
                    <input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.searchValue" v-if="pageInfo.searchType !== 'traffic' && pageInfo.searchType !== 'use'" @keypress.enter="getPricePoliciesList(1)">
                  </td>
                  <th>등록일</th>
                  <td>
                    <div class="picker__group">
                      <label class="label__picker"><input class="input__picker" autocomplete="off" id="fromDate" type="text"></label><span class="picker__period">~</span><label class="label__picker"><input class="input__picker" autocomplete="off" id="toDate" type="text"></label>
                    </div>
                  </td>
                </tr>
	          </tbody>
	        </table>
	      </div>
	      <div class="button__group">
        	<button class="button__primary" type="button" @click="getPricePoliciesList(1)">검색</button>
	      </div>
	    </section>
	  </fieldset>
	</form>
	
	<section class="section">
		<div class="section__header">
			<h4 class="section__title">가격정책관리 목록</h4>
		</div>
		<div class="section__content">
			<el-table :data="list" border style="width: 100%" empty-text="검색 결과가 존재하지 않습니다." v-loading="loading" @row-click="onRowClick">
				<el-table-column prop="title" label="제목" width="" align="center"></el-table-column>
				<el-table-column prop="trafficTypeName" label="트래픽유형" width="" align="center"></el-table-column>
				<el-table-column prop="useTf" label="사용여부" width="100">
				    <template slot-scope="scope">
                        {{scope.row.useTf | flag}}
                    </template>
				</el-table-column>
				<el-table-column prop="creatorNm" label="등록자" width="100"></el-table-column>
				<el-table-column prop="createdAt" label="등록일자" width="240">
				    <template slot-scope="scope">
                        {{scope.row.createdAt | date}}
                    </template>
				</el-table-column>
			</el-table>	  
        	<n2m-pagination :pager="pageInfo" @pagemove="getPricePoliciesList"/>
	  </div>
	</section>
	<div class="button__group">
		<c:if test="${writeYn eq 'Y'}">
			<button class="button__primary" type="button" onclick="location.href='<c:url value="/pricePolicies/pageRegist.do"/>'">등록</button>
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
				loading: true,
				pageInfo: {
					searchType        : "",
					searchTrafficType : "limit",
					searchUseTf       : "true",
					searchValue       : "",
				}
			}
		},
		methods: {
			getPricePoliciesList: function(curPage){
				vm.loading = true;
				var request = $.ajax({
					url: N2M_CTX + "/pricePolicies/getList.do",
					method: "GET",
					dataType: "json",
					data: {
						searchType        : vm.pageInfo.searchType,
						searchTrafficType : vm.pageInfo.searchTrafficType,
						searchUseTf       : vm.pageInfo.searchUseTf,
						searchValue       : vm.pageInfo.searchValue,
						curPage           : curPage,
						toDate            : vm.pageInfo.toDate,
						fromDate          : vm.pageInfo.fromDate,
					}
				});
				request.done(function(data){
					vm.loading = false;
					vm.list = data.list;
					vm.pageInfo = $.extend(vm.pageInfo, data.page);
				});
				request.fail(function(data){
					vm.loading = false;
				});
			},
			onRowClick: function(row){
				location.href = N2M_CTX + "/pricePolicies/pageDetail.do?id="+row.id;
			},
		}
	});

	vm.keepPageInfo(function(curPage){
		if(!vm.pageInfo.sortField && !vm.pageInfo.sortOrder){
			vm.getPricePoliciesList(curPage);
		}
	});
});
</script>