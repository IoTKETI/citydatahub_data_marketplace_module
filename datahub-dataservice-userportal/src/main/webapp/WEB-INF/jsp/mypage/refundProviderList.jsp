<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
      <h2 class="hidden">본문</h2>
      <!-- sub-cover -->
      <section class="sub-cover">
        <h3 class="sub-cover__title sub-cover__title--data material-icons">데이터<small class="sub-cover__title--small">Smart City Data Hub</small></h3>
      </section>
      <hr>
      <!-- sub-nav -->
	<%@ include file="../tiles/tiles_sub_nav.jspf" %>
      <!-- sub-nav -->
      <hr>
      <div class="sub__container">
      	<!-- breadcrumb -->
        <%@ include file="../tiles/tiles_breadcrumb.jspf" %>
        <!-- //breadcrumb -->
        <h3 class="sub__heading1">데이터 환불 내역</h3>
        <div class="sub__content">
            <form class="search-form" action="">
                <fieldset>
                    <legend>데이터 검색 폼</legend>
                    <table class="form-table refund">
                        <caption>데이터 검색 테이블</caption>
                        <colgroup>
                            <col width="3%">
                            <col width="20%">
                            <col width="35%">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th scope="row">기간</th>
                                <td>
                                    <div class="datepicker">
                                        <label class="label__datepicker material-icons"><input class="input input__datepicker" id="fromDate" type="text" autocomplete="off" v-model="pageInfo.fromDate"></label><span class="period">~</span><label class="label__datepicker material-icons"><input class="input input__datepicker" id="toDate" type="text" autocomplete="off" v-model="pageInfo.toDate"></label>
                                    </div>
                                </td>
                                <td><button class="button__submit" type="button" @click="getDatasetUsageRefundListByProvider(1)">검색</button></td>
                            </tr>
                        </tbody>
                    </table>
                </fieldset>
            </form>
        </div>
        <ul class="tab__list">
            <li class="tab__item" :class="{'is-active': pageInfo.srchRefundStatus == 'pay_request'}" @click="pageInfo.srchRefundStatus = 'pay_request'">
                <a class="tab__button refund__button" href="#none">환불 신청 대기
                   <span class="tab__count">
                       <p v-if="datasetUsageRefundRequestCount">{{datasetUsageRefundRequestCount}}</p>
                   </span>
                </a>
                <div class="sub__content tab__panel data-list__filter">
                    <div class="data-list" v-if="list.length === 0">
                        <div class="data-list__item refund-list">
                            <p>데이터가 없습니다.</p>
                        </div>
                    </div>
                    <div class="data-list" v-for="data in list">
                        <div class="data-list__item refund-list">
                            <form class="filter-form refund" action="">
                                <fieldset>
                                    <legend>활용사례 목록 필터 폼</legend>
                                    <div class="data-unit__info nav-refund">
                                        <span class="data-unit__url">요청자 : {{data.requestUserId}}</span>
                                        <span class="data-unit__date">취소접수일 : {{data.refundCreatedAt | date}}</span>
                                        <span class="data-unit__date">결제일 : {{data.completedAt | date}}</span>
                                        <span class="data-unit__date">결제번호 : {{data.payId}}</span>
                                    </div>
                                </fieldset>
                            </form>
                            <div class="data-unit data-unit--none-bgc">
                                <a class="data-unit__link" href="#none">
                                    <dl class="data-unit__caption data-unit__title">
                                        <dt class="data-unit__title main-title">{{data.datasetTitle}}</dt>
                                        <dd class="data-unit__category-top">
                                        	<span class="data-unit__status">[{{data.trafficType == 'limit' ? '제한' : '무제한'}}]</span><!-- 
                                          --><template v-if="data.trafficType == 'limit'">{{data.limit | comma}} 트래픽 / {{data.expiredAt | date}} 까지</template><!-- 
                                          --><template v-else>{{data.expiredAt | date}} 까지</template>
                                        </dd>
                                        <dd class="data-unit__text-bottom">환불 사유 : {{data.refundReason}}</dd>
                                    </dl>
                                </a>
                                <div class="data-unit__info data-sub-title">
                                	<div>
                                        <span class="data-unit__url-pay">{{data.price | comma}}원</span>
                                	</div>
                                	<div>
                                        <span class="data-unit__refund">
                                            {{data.refundStatusNm}}
                                        </span>
                                        <a class="button button__refund" @click="completeRefund(data)">승인</a>
                                	</div>
                                </div>
                            </div>
                            <div>
                                <div class="refund-box">
                                    <input class="refund-box__input" type="text" placeholder="반려 사유를 입력 해 주세요." :ref="'refund_' + data.refundId" v-model="data.refuseReason">
                                    <button class="button button__application" type="button" @click="cancelRefund(data)">반려</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="sub__bottom">
                        <ul class="pagination__list">
                            <n2m-pagination :pager="pageInfo" @pagemove="getDatasetUsageRefundListByProvider"/>
                        </ul>
                    </div>
                </div>
            </li>
            <li class="tab__item" :class="{'is-active': pageInfo.srchRefundStatus == 'pay_complete'}" @click="pageInfo.srchRefundStatus = 'pay_complete'">
                <a class="tab__button refund__button" href="#none">환불 신청 완료</a>
                <div class="sub__content tab__panel data-list__filter">
                    <div class="data-unit data-unit--none-bgc" v-if="list.length === 0">데이터가 없습니다.</div>
                    <div class="data-list" v-for="data in list">
                        <div class="data-list__item refund-list">
                            <form class="filter-form refund" action="">
                                <fieldset>
                                    <legend>활용사례 목록 필터 폼</legend>
                                    <div class="data-unit__info nav-refund">
                                        <span class="data-unit__url">요청자 : {{data.requestUserId}}</span>
                                        <span class="data-unit__date">취소접수일 : {{data.refundCreatedAt | date}}</span>
                                        <span class="data-unit__date">결제일 : {{data.completedAt | date}}</span>
                                        <span class="data-unit__date">결제번호 : {{data.payId}}</span>
                                    </div>
                                </fieldset>
                            </form>
                            <div class="data-unit data-unit--none-bgc">
                                <a class="data-unit__link" href="#none">
                                    <dl class="data-unit__caption data-unit__title">
                                        <dt class="data-unit__title main-title">{{data.datasetTitle}}</dt>
                                        <dd class="data-unit__category-top">
                                        	<span class="data-unit__status">[{{data.trafficType == 'limit' ? '제한' : '무제한'}}]</span><!-- 
                                          --><template v-if="data.trafficType == 'limit'">{{data.limit | comma}} 트래픽 / {{data.expiredAt | date}} 까지</template><!-- 
                                          --><template v-else>{{data.expiredAt | date}} 까지</template>
                                        </dd>
                                        <dd class="data-unit__text-bottom">환불 사유 : {{data.refundReason}}</dd>
                                    </dl>
                                </a>
                                <div class="data-unit__info">
                                	<div>
                                        <span class="data-unit__url-pay">{{data.price | comma}}원</span>
                                    </div>
                                    <div>
                                        <span class="data-unit__refund">
                                            {{data.refundStatusNm}}
                                        </span>
                                        <p class="sub-cover__title--date">{{data.refundUpdateAt | date}}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="sub__bottom">
                        <ul class="pagination__list">
                            <n2m-pagination :pager="pageInfo" @pagemove="getDatasetUsageRefundListByProvider"/>
                        </ul>
                    </div>
                </div>
            </li>
        </ul>
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
					pageInfo: {
						srchRefundStatus: "pay_request",
						fromDate : moment().add(-1, 'month').format("YYYY-MM-DD"),
						toDate   : moment().format("YYYY-MM-DD"),
					},
					datasetUsageRefundRequestCount: 0
				}
			},
			watch: {
				"pageInfo.srchRefundStatus": function(){
					vm.getDatasetUsageRefundListByProvider(1);
				}
			},
			mounted: function(){
				$("#fromDate").datepicker({
					dateFormat: 'yy-mm-dd',
					onSelect: function(date){
						$("#toDate").datepicker('option', 'minDate', new Date(date));
						vm.pageInfo.fromDate = date; 
					}
				});
				$("#toDate").datepicker({
					dateFormat: 'yy-mm-dd',
					onSelect: function(date){
						$("#fromDate").datepicker('option', 'maxDate', new Date(date));
						vm.pageInfo.toDate = date;
					}
				});
			},
			methods: {
				getDatasetUsageRefundListByProvider : function(curPage) {
					var request = $.ajax({
						url : SMC_CTX + "/dataset/use/refundProvider/getList.do",
						method : "GET",
						dataType: "json",
						data: {
							curPage          : vm.pageInfo.curPage,
							srchRefundStatus : vm.pageInfo.srchRefundStatus,
							fromDate         : vm.pageInfo.fromDate,
							toDate           : vm.pageInfo.toDate,
						}
					});
					
					request.done(function(data) {
						vm.list = data.list;
						vm.pageInfo = data.page;
						vm.datasetUsageRefundRequestCount = data.datasetUsageRefundRequestCount;
					});
					
				},
				completeRefund: function(data){
					if(!confirm("환불 요청을 승인하시겠습니까?")) return;
					var request = $.ajax({
						url : SMC_CTX + "/dataset/use/refund/modify.do",
						method : "PATCH",
						contentType: "application/json",
						data: JSON.stringify({
							id        : data.refundId,
							requestId : data.requestId,
							status    : "pay_complete",
						})
					});
					
					request.done(function(data) {
						Modal.OK();
						vm.getDatasetUsageRefundListByProvider(1);
					});
					
				},
				cancelRefund: function(data){
					if(!data.refuseReason){
						alert("반려 사유를 입력 해주세요.");
						vm.$refs['refund_' + data.refundId][0].focus();
						return;
					}
					if(!confirm("환불 요청을 반려하시겠습니까?")) return;
					var request = $.ajax({
						url : SMC_CTX + "/dataset/use/refund/modify.do",
						method : "PATCH",
						contentType: "application/json",
						data: JSON.stringify({
							id           : data.refundId,
							requestId    : data.requestId,
							refuseReason : data.refuseReason,
							status       : "pay_error",
						})
					});
					
					request.done(function(data) {
						Modal.OK();
						vm.getDatasetUsageRefundListByProvider(1);
					});
					
				},
				
			}
		});
		vm.getDatasetUsageRefundListByProvider(1);
	});
</script>
