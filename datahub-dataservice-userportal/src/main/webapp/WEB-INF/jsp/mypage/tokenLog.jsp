<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
    
<!-- sub-cover -->
<section class="sub-cover">
  <h3 class="sub-cover__title sub-cover__title--data material-icons">마이페이지 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
</section>
<!-- sub-nav -->
<%@ include file="../tiles/tiles_sub_nav.jspf" %>
<!-- sub-nav -->
<hr>

<div class="sub__container">
<!-- breadcrumb -->
	<%@ include file="../tiles/tiles_breadcrumb.jspf" %>
	<!-- //breadcrumb -->
	<h3 class="sub__heading1">인센티브 토큰 발급 내역</h3>
	<div class="sub__content">
	  <form class="search-form" action="">
	    <fieldset>
	      <legend>데이터 검색 폼</legend>
	      <table class="form-table">
	        <caption>데이터 검색 테이블</caption>
	        <colgroup>
	          <col width="15%">
	          <col width="*">
	          <col width="30%">
	        </colgroup>
	        <tbody>
	          <tr>
	            <th scope="row">기간</th>
	            <td>
	              <div class="datepicker">
	                <label class="label__datepicker material-icons"><input class="input input__datepicker" id="fromDate" type="text" v-model="pageInfo.fromDate" autocomplete="off"></label><span class="period">~</span><label class="label__datepicker material-icons"><input class="input input__datepicker" id="toDate" type="text" v-model="pageInfo.toDate" autocomplete="off"></label>
	              </div>
	            </td>
	            <td><button class="button__submit" type="button" @click="getIncentiveTokenLog(1)">검색</button></td>
	          </tr>
	        </tbody>
	      </table>
	    </fieldset>
	  </form>
	</div>
	<div class="sub__content">
	  <h4 class="sub__heading2">상세목록</h4>
	  <ul class="data-list">
	  	<li class="data-list__item" v-if="list.length === 0">
	  		<div class="data-unit data-unit--none">
	  			데이터가 없습니다.
	  		</div>
	  	</li>
	    <li class="data-list__item" v-for="data in list">
	      <div class="data-unit">
	        <a class="data-unit__link" :href="'<c:url value="/dataset/pageDetail.do?id="/>'+data.record.additional.datasetId">
              <div class="data-list__thumb">
	          	<dd class="data-unit__badge">
	          		<span class="data-unit__status">
	          			<span class="badge badge--pink" v-if="vm.loginUserId === data.record.buyerId">발신</span>
	          			<span class="badge badge--blue" v-else>수신</span>
	          		</span>
	          	</dd>
              </div>
	          <dl class="data-unit__caption">
	            <dd class="data-unit__title">
	          		{{data.record.buyerId}}　→　{{data.record.sellerId}}
	          	</dd>
	            <dd class="data-unit__text" style="overflow:visible;">
	          		{{data.record.additional.datasetId}}({{data.record.additional.datasetTitle}})
	          	</dd>
	          </dl>
	        </a>
		    <div class="data-unit__info">
		      <span class="data-unit__owner">
				<span class="data-unit__status">
					<span class="badge badge--pink" v-if="vm.loginUserId === data.record.buyerId">-{{data.record.amount|comma}} TOKEN</span>
					<span class="badge badge--blue" v-else>+{{data.record.amount|comma}} TOKEN</span>
				</span>
		      </span>
		      <span class="data-unit__date">{{data.record.time|unixdate}}</span>
		    </div>
	      </div>
	    </li>
	  	<li class="data-list__item">
	  		<div class="data-unit data-unit--none data-unit__title" style="display:block;text-align:right;">
	  			총 보유량: {{totalBalance|comma}} TOKEN
	  		</div>
	  	</li>
	  </ul>
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
				codeList: [],
				totalBalance: 0,
			}
		},
		methods: {
			getIncentiveTokenLog: function() {
				var termStart = "";
				var termEnd = "";
				if(vm.pageInfo.fromDate){
					termStart = new String(moment(vm.pageInfo.fromDate+" 00:00:00").unix());
				}
				if(vm.pageInfo.toDate){
					termEnd = new String( moment(vm.pageInfo.toDate+" 23:59:59").unix());
				}
				
				var request = $.ajax({
					method: "POST",
					url: SMC_CTX + "/mypage/token/log/getList.do",
					contentType: "application/json",
					data: JSON.stringify({
						userId    : vm.loginUserId,
						termStart : termStart,
						termEnd   : termEnd,
					}),
					dataType: "json",
				});
				
				request.done(function(data) {
					if(typeof data.result !== "string"){
						vm.list = data.result;
						if(data.balance){
							vm.totalBalance = JSON.parse(data.balance).result;
						}
					}
				});
				
				request.fail(function(data) {
					console.log("err => " + data);
				});
			}
		}
	});
	
	vm.keepPageInfo(function(curPage) {
		vm.getIncentiveTokenLog(curPage);
    });
});

</script>

