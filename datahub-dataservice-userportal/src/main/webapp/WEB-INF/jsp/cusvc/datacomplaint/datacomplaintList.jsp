<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
      <h2 class="hidden">본문</h2>
      <!-- sub-cover -->
      <section class="sub-cover">
        <h3 class="sub-cover__title material-icons">고객 서비스 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
      </section>
      <hr>
      ${lnbHtml}
	<hr>
      <div class="sub__container">
        <!-- breadcrumb -->
        <%@ include file="/WEB-INF/jsp/tiles/tiles_breadcrumb.jspf" %>
        <!-- //breadcrumb -->
        <h3 class="sub__heading1">신고하기</h3>
        <div class="sub__content">
	        <form class="search-form" action="">
	          <fieldset>
	            <legend>데이터 검색 폼</legend>
	            <table class="form-table">
	              <caption>데이터 검색 테이블</caption>
	              <colgroup>
	                <col width="6%">
	                <col width="*">
	                <col width="6%">
	                <col width="*">
	                <col width="14%">
	              </colgroup>
	              <tbody>
	                <tr>
	                  <th scope="row">검색어</th>
	                  <td><select class="select" v-model="pageInfo.schCondition">
	                      <option disabled value="">--선택--</option>
	                    <option value="datacomplainTitle">제목</option>
	                    <option value="datacomplainCreUsrNm">작성자</option>
	                    </select>
	                    <input class="input input__search" type="search" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue">
	                  </td>
	                  <th scope="row">등록일</th>
	                  <td>
	                    <div class="datepicker">
	                      <label class="label__datepicker material-icons"><input class="input input__datepicker" id="fromDate" v-model="pageInfo.fromDate" type="text"></label><span class="period">~</span><label class="label__datepicker material-icons"><input class="input input__datepicker" id="toDate" v-model="pageInfo.toDate" type="text"></label>
	                    </div>
	                  </td>
	                </tr>
	                <tr>
		             <th scope="row">유형</th>
	                  <td>
		                <select class="select" v-model="pageInfo.schValue3">
		                  <option value="">--전체--</option>
		                  <c:forEach var="code" items="${n2m:getCodeList('CG_ID_QNA_TYPE')}">
			                  <option value="${code.codeId}">${code.codeName}</option>
		                  </c:forEach>
		                </select>
		              </td>
		              <th scope="row">진행상태</th>
		              <td>
		                <select class="select" v-model="pageInfo.schValue4" >
		                  <option value="">--전체--</option>
		                  <c:forEach var="code" items="${n2m:getCodeList('CG_ID_QNA_STATUS')}">
			                  <option value="${code.codeId}">${code.codeName}</option>
		                  </c:forEach>
		                </select>
		              </td>
	                  <td rowspan="2"><button class="button__submit" type="button" @click="getDatacomplaintList(1)">검색</button></td>
		            </tr>
	              </tbody>
	            </table>
	          </fieldset>
	        </form>
        </div>
        <div class="sub__content">
        <h4 class="sub__heading2">신고하기 목록</h4>
          <table class="board-table">
            <caption>해당 테이블에 대한 캡션</caption>
            <colgroup>
              <col width="10%">
              <col width="30%">
              <col width="10%">
              <col width="20%">
              <col width="10%">
              <col width="20%">
            </colgroup>
            <thead>
              <tr>
                <th scope="col">번호</th>
                <th scope="col">제목</th>
                <th scope="col">유형</th>
                <th scope="col">작성자</th>
                <th scope="col">진행상태</th>
                <th scope="col">등록일시</th>
              </tr>
            </thead>
            <tbody>
            <tr v-if="list.length === 0">
            <td colspan="6">데이터가 없습니다.</td>
        	</tr>
              <tr v-for="data in list" @click="onRowClick(data)">
                <th class="text--center" scope="row">{{data.datacomplainId}}</th>
                <th class="text--left" scope="row">{{data.datacomplainTitle}}</th>
                <td class="text--center">{{data.datacomplainGbCdNm}}</td>
                <td class="text--center">{{data.datacomplainCreUsrNm}}</td>
                <td class="text--center">{{data.datacomplainStatCdNm}}</td>
                <td class="text--center">{{data.datacomplainCreDt|date}}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="sub__bottom">
          <div>
	          <n2m-pagination :pager="pageInfo" @pagemove="getDatacomplaintList"/>
          </div>
          <div class="button__group">
            <a class="button button__primary" href="<c:url value="/cusvc/datacomplaint/pageEdit.do?nav=MNU022"/>">등록</a>
          </div>
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
			created: function() {
                this.pageInfo.schValue = "${param.schValue}";
                this.pageInfo.schCondition = "${param.schCondition}";
            },
			methods: {
				getDatacomplaintList: function(curPage){
					vm.loadingFlag = true;
					vm.loading = true;
					vm.pageInfo.schCondition2 = "datacomplainCreDt";
					vm.pageInfo.schCondition3 = "datacomplainGbCd";
					vm.pageInfo.schCondition4 = "datacomplainStatCd";
					vm.pageInfo.curPage = curPage;
				  var request = $.ajax({
					  url: SMC_CTX + "/cusvc/datacomplaint/getList.do",
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
					  vm.loadingFlag = false;
					  vm.list = data.list;
					  vm.pageInfo = data.page;
					});
				  request.fail(function(data){
						vm.loading = false;
						vm.loadingFlag = false;
					});
				},
				onRowClick: function(row){
						location.href = SMC_CTX + "/cusvc/datacomplaint/pageDetail.do?nav=MNU022&datacomplainOid="+row.datacomplainOid;
				},
				onSortChange: function(column){
					vm.pageInfo.sortField=column.prop;
					vm.pageInfo.sortOrder=column.order;
					vm.keepPageInfo(function(curPage){
						vm.getDatacomplaintList(curPage);
					});
				},
				goPageEdit: function(){
					alert("로그인이 필요한 서비스 입니다.");
					location.href = SMC_CTX + "/cusvc/datacomplaint/pageEdit.do";
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
