<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
      <h2 class="hidden">본문</h2>
      <!-- sub-cover -->
      <section class="sub-cover">
        <h3 class="sub-cover__title material-icons">고객 서비스 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
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
        <h3 class="sub__heading1">데이터 허브 소개</h3>
<!--         <div class="sub__content"> -->
<!--             <form class="search-form" action=""> -->
<!--               <fieldset> -->
<!--                 <legend>데이터 검색 폼</legend> -->
<!--                 <table class="form-table"> -->
<!--                   <caption>데이터 검색 테이블</caption> -->
<!--                   <colgroup> -->
<!--                     <col width="6%"> -->
<!--                     <col width="*"> -->
<!--                     <col width="6%"> -->
<!--                     <col width="*"> -->
<!--                     <col width="14%"> -->
<!--                   </colgroup> -->
<!--                   <tbody> -->
<!--                     <tr> -->
<!--                       <th scope="row">제목</th> -->
<!--                       <td> -->
<!--                         <input class="input input__search" type="search" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue"> -->
<!--                       </td> -->
<!--                       <th scope="row">등록일</th> -->
<!--                       <td> -->
<!--                         <div class="datepicker"> -->
<!--                           <label class="label__datepicker material-icons"><input class="input input__datepicker" id="fromDate" v-model="pageInfo.fromDate" type="text"></label><span class="period">~</span><label class="label__datepicker material-icons"><input class="input input__datepicker" id="toDate" v-model="pageInfo.toDate" type="text"></label> -->
<!--                         </div> -->
<!--                       </td> -->
<!--                       <td rowspan="2"><button class="button__submit" type="button" @click="getFaqList(1)">검색</button></td> -->
<!--                     </tr> -->
<!--                   </tbody> -->
<!--                 </table> -->
<!--               </fieldset> -->
<!--             </form> -->
<!--         </div> -->
<!--         <div class="sub__content"> -->
<!--           <h4 class="sub__heading2">FAQ 목록</h4> -->
<!--           <table class="board-table"> -->
<!--             <caption>해당 테이블에 대한 캡션</caption> -->
<!--             <colgroup> -->
<!--               <col width="10%"> -->
<!--               <col width="40%"> -->
<!--               <col width="20%"> -->
<!--               <col width="30%"> -->
<!--             </colgroup> -->
<!--             <thead> -->
<!--               <tr> -->
<!--                 <th scope="col">번호</th> -->
<!--                 <th scope="col">제목</th> -->
<!--                 <th scope="col">등록자</th> -->
<!--                 <th scope="col">등록일자</th> -->
<!--               </tr> -->
<!--             </thead> -->
<!--             <tbody> -->
<!--               <tr v-for="data in list" @click="onRowClick(data)"> -->
<!--                 <th class="text--center" scope="row">{{data.faqId}}</th> -->
<!--                 <th class="text--left" scope="row">{{data.faqTitle}}</th> -->
<!--                 <td class="text--center">{{data.faqCreUsrNm}}</td> -->
<!--                 <td class="text--center">{{data.faqCreDt|date}}</td> -->
<!--               </tr> -->
<!--             </tbody> -->
<!--           </table> -->
<!--         </div> -->
<!--         <div class="sub__bottom"> -->
<!--           <div> -->
<!--               <n2m-pagination :pager="pageInfo" @pagemove="getFaqList"/> -->
<!--           </div> -->
<!--         </div> -->
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
                    vm.pageInfo.schCondition = "faqTitle";
                    vm.pageInfo.schCondition2 = "faqCreDt";
                    vm.loading = true;
                    vm.pageInfo.curPage = curPage;
                    
                  var request = $.ajax({
                      url: SMC_CTX + "/cusvc/faq/getList.do",
                      method: "GET",
                      dataType: "json",
                      data: {
                          schCondition  : vm.pageInfo.schCondition,
                          schValue      : vm.pageInfo.schValue,
                          schCondition2 : vm.pageInfo.schCondition2,
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
                	location.href = SMC_CTX + "/cusvc/faq/pageDetail.do?nav=MNU020&faqOid="+row.faqOid;
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
