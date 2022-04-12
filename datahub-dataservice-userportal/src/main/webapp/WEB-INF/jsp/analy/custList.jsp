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
        <h3 class="sub__heading1">맞춤형</h3>
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
