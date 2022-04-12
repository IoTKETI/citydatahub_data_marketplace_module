<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
      <h2 class="hidden">본문</h2>
      <!-- sub-cover -->
      <section class="sub-cover">
  <h3 class="sub-cover__title sub-cover__title--data material-icons">마이페이지 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
      </section>
      <hr>
       <!-- sub-nav -->
     ${lnbHtml}
 <!-- sub-nav -->
    <hr>
      
      <div class="sub__container">
        <!-- breadcrumb -->
          <%@ include file="/WEB-INF/jsp/tiles/tiles_breadcrumb.jspf" %>
        <!-- //breadcrumb -->
        <h3 class="sub__heading1">문의사항</h3>
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
                  <th>제목</th>
                  <td>
                    <input class="input input__search" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue" @keypress.enter.prevent="getQnaList(1)" />
                  </td>
                  <th>등록일</th>
                  <td>
                    <div class="datepicker">
                      <label class="label__datepicker material-icons"><input class="input input__datepicker" id="fromDate" v-model="pageInfo.fromDate" type="text"></label><span class="period">~</span><label class="label__datepicker material-icons"><input class="input input__datepicker" id="toDate" v-model="pageInfo.toDate" type="text"></label>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>유형</th>
                  <td>
                    <select class="select select--full" v-model="pageInfo.schCondition2">
                      <option value="">전체</option>
                      <c:forEach var="code" items="${n2m:getCodeList('CG_ID_QNA_TYPE')}">
                        <option value="${code.codeId}">${code.codeName}</option>
                      </c:forEach>
                    </select>
                  </td>
                  <th>진행상태</th>
                  <td>
                    <select class="select select--full" v-model="pageInfo.schCondition3">
                      <option value="">전체</option>
                      <c:forEach var="code" items="${n2m:getCodeList('CG_ID_QNA_STATUS')}">
                        <option value="${code.codeId}">${code.codeName}</option>
                      </c:forEach>
                    </select>
                  </td>
                  <td rowspan="2"><button class="button__submit" type="button" @click="getQnaList(1)">검색</button></td>
                </tr>
              </tbody>
                </table>
              </fieldset>
            </form>
        </div>
        <div class="sub__content">
          <h4 class="sub__heading2">문의사항 목록</h4>
          <table class="board-table">
            <caption>해당 테이블에 대한 캡션</caption>
            <colgroup>
              <col width="5%">
              <col width="25%">
              <col width="15%">
              <col width="10%">
              <col width="20%">
            </colgroup>
            <thead>
              <tr>
                <th scope="col">번호</th>
                <th scope="col">제목</th>
                <th scope="col">유형</th>
                <th scope="col">진행상태</th>
                <th scope="col">등록일자</th>
              </tr>
            </thead>
            <tbody>
            <tr v-if="list.length === 0">
            <td colspan="5">데이터가 없습니다.</td>
        	</tr>
              <tr v-for="data in list" @click="onRowClick(data)">
                <th class="text--center" scope="row">{{data.qnaId}}</th>
                <th class="text--left" scope="row">{{data.qnaTitle}}</th>
                <th class="text--center" scope="row">{{data.qnaQuestionTypeNm}}</th>
                <th class="text--center" scope="row">{{data.qnaStatusNm}}</th>
                <td class="text--center">{{data.qnaQuestionDt|date}}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="sub__bottom">
          <div>
              <n2m-pagination :pager="pageInfo" @pagemove="getQnaList"/>
          </div>
          <div class="button__group">
            <a class="button button__primary" href="<c:url value="/cusvc/qna/pageEdit.do?backUrl=mypage"/>">등록</a>
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
            methods: {
            	getQnaList: function(curPage){
                    vm.pageInfo.schCondition = "title";
                    vm.loading = true;
                    vm.pageInfo.curPage = curPage;
                    
                  var request = $.ajax({
                      url: SMC_CTX + "/cusvc/qna/getList.do",
                      method: "GET",
                      dataType: "json",
                      data: {
                          schCondition  : vm.pageInfo.schCondition,
                          schValue      : vm.pageInfo.schValue,
                          schCondition2 : vm.pageInfo.schCondition2,
                          schCondition3 : vm.pageInfo.schCondition3,
                          schCondition4 : vm.loginUserId,
                          fromDate      : vm.pageInfo.fromDate,
                          toDate        : vm.pageInfo.toDate,
                          sortField     : vm.pageInfo.sortField,
                          sortOrder     : vm.pageInfo.sortOrder,
                          curPage       : vm.pageInfo.curPage
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
                	location.href = SMC_CTX + "/cusvc/qna/pageDetail.do?backUrl=mypage&qnaOid="+row.qnaOid;
                }
            }
        });
      vm.keepPageInfo(function(curPage){
            if(!vm.pageInfo.sortField && !vm.pageInfo.sortOrder){
                vm.getQnaList(curPage);
            }
        });
    });
</script>
