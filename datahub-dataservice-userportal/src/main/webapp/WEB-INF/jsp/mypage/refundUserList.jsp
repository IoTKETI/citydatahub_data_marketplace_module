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
                                <th scope="row">검색</th>
                                <td>
                                    <select class="select" v-model="pageInfo.srchTextCondition">
                                        <option value="datasetTitle">데이터셋 제목</option>
                                        <option value="refundReason">환불 사유</option>
                                    </select>
                                    <input class="input input__search" type="search" placeholder="검색어를 입력하세요." v-model="pageInfo.srchText" @keypress.enter="getDatasetUsageRefundListByUser">
                                </td>
                                <th scope="row">등록일</th>
                                <td>
                                    <div class="datepicker">
                                        <label class="label__datepicker material-icons"><input class="input input__datepicker" id="fromDate" type="text" autocomplete="off" v-model="pageInfo.fromDate"></label><span class="period">~</span><label class="label__datepicker material-icons"><input class="input input__datepicker" id="toDate" type="text" autocomplete="off" v-model="pageInfo.toDate" type="text"></label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row">환불 상태</th>
                                <td>
                                    <select class="select" v-model="pageInfo.srchRefundStatus">
                                        <option value="">선택</option>
                                        <c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATASET_REFUND_STATUS')}">
                                            <option value="${code.codeId}">${code.codeName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <th scope="row">제한 유형</th>
                                <td>
                                    <select class="select" v-model="pageInfo.srchTrafficType">
                                        <option value="">선택</option>
                                        <c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATASET_TRAFFIC_TYPE')}">
                                            <option value="${code.codeId}">${code.codeName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <button class="button__submit" type="button" @click="getDatasetUsageRefundListByUser">검색</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </fieldset>
            </form>
        </div>
        <div class="sub__content">
            <ul>
                <div class="data-list__filter">
                    <ul class="data-list">
                        <li class="data-list__item refund-list" v-if="list.length === 0">
                            <div class="data-unit data-unit--none">데이터가 없습니다.</div>
                        </li>
                        <li class="data-list__item refund-list" v-for="data in list">
                            <form class="filter-form refund" action="">
                                <fieldset>
                                    <legend>활용사례 목록 필터 폼</legend>
                                    <div class="data-unit__info data-sub-title">
                                        <span class="data-unit__url">취소접수일 : {{data.refundCreatedAt | date}}</span>
                                        <span class="data-unit__date">결제일 : {{data.completedAt | date}}</span>
                                        <span class="data-unit__date">결제번호 : {{data.payId}}</span>
                                    </div>
                                </fieldset>
                            </form>
                            <div class="data-unit">
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
                                    <span class="data-unit__url-pay">{{data.price | comma}}원</span>
                                    <div>
                                        <span class="data-unit__refund">{{data.refundStatusNm}}</span>
                                        <a class="button button__refund" v-if="data.refundStatus == 'pay_error'" @click="openPopup('OPEN_POPUP_REFUND_HISTORY', data)">반려사유</a>
                                        <p class="sub-cover__title--date" v-if="data.refundStatus == 'pay_complete'">{{data.refundUpdateAt | date}}</p>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </ul>
        </div>
        <div class="sub__bottom">
            <div>
                <n2m-pagination :pager="pageInfo" @pagemove="getDatasetUsageRefundListByUser"/>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/mypage/popup/datasetRefundHistoryPopup.jsp"/>
<script>
	$(function() {
		Vue.use(N2MPagination);
		vm = new Vue({
			el : '#content',
			mixins: [datasetRefundHistoryPopupMixin],
			data : function() {
				return {
					list : [],
					pageInfo: {
						srchTextCondition  : "datasetTitle",
						srchText           : "",
						srchRefundStatus   : "",
						srchRefundStatus   : "",
						srchTrafficType    : "",
						fromDate           : moment().add(-1, 'month').format("YYYY-MM-DD"),
						toDate             : moment().format("YYYY-MM-DD"),
					}
				}
			},
			methods : {
				getDatasetUsageRefundListByUser : function(curPage) {
					var searchData = {};
					searchData.curPage         = curPage;
					
					var request = $.ajax({
						url : SMC_CTX + "/dataset/use/refundUser/getList.do",
						method : "GET",
						dataType: "json",
						data: {
							curPage           : vm.pageInfo.curPage,
							srchTextCondition : vm.pageInfo.srchTextCondition,
							srchText          : vm.pageInfo.srchText,
							srchRefundStatus  : vm.pageInfo.srchRefundStatus,
							srchTrafficType   : vm.pageInfo.srchTrafficType,
							fromDate          : vm.pageInfo.fromDate,
							toDate            : vm.pageInfo.toDate,
						}
					});
					
					request.done(function(data) {
						vm.list = data.list;
						vm.pageInfo = data.page;
					});
					
				},
				openPopup: function(code, data){
					switch(code){
					case "OPEN_POPUP_REFUND_HISTORY":
						vm.openDatasetRefundHistoryPopup(data);
						break;
					}
				}
				
			}
		});
		vm.getDatasetUsageRefundListByUser(1);
	});
</script>
