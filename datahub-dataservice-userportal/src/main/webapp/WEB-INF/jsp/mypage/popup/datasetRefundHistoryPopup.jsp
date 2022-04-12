<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal modal--dataset-select" :class="datasetRefundHistoryPopupVisible ? 'js-modal-show':''">
    <div class="modal__outer">
        <div class="modal__inner">
            <div class="modal__header">
                <h3 class="hidden">모달 타이틀</h3>
                <button class="modal__button--close js-modal-close material-icons" type="button" @click="closeDatasetRefundHistoryPopup"><span class="hidden">모달 닫기</span></button>
            </div>
            <div class="modal__body">
                <h4 class="modal__heading3">환불 반려 내역</h4>
            </div>
            <div class="sub__content">
                <div class="data-list__filter">
                    <h3 class="sub__heading6">데이터셋 결제 정보</h3>
                    <ul class="data-list">
                        <li class="data-list__item data-menu-refund">
                            <div class="data-unit">
                                <a class="data-unit__link" href="#none">
                                    <dl class="data-unit__caption data-unit__title">
                                        <dt class="data-unit__title main-title">{{datasetUsageRefunds.datasetTitle}}</dt>
                                        <dd class="data-unit__category-top"><!-- 
                                          --><span class="data-unit__status">[{{datasetUsageRefunds.trafficType == 'limit' ? '제한' : '무제한'}}]</span><!-- 
                                          --><template v-if="datasetUsageRefunds.trafficType == 'limit'">{{datasetUsageRefunds.limit | comma}} 트래픽 / {{datasetUsageRefunds.expiredAt | date}} 까지</template><!-- 
                                          --><template v-else>{{datasetUsageRefunds.expiredAt | date}} 까지</template></dd>
                                    </dl>
                                </a>
                                <div class="data-unit__info data-sub-title">
                                    <span class="data-unit__url-pay">{{datasetUsageRefunds.price | comma}}원</span>
                                    <div>
                                        <span class="data-unit__refund">{{datasetUsageRefunds.refundStatusNm}}</span>
                                        <p class="sub-cover__title--date">{{datasetUsageRefunds.refundRefuseAt | date}}</p>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="sub__content">
                <h3 class="sub__heading6">반려 내역</h3>
                <ul>
                    <div class="data-list__filter">
                        <ul class="data-list refund-pop">
                            <li class="data-list__item refund-list" v-for="(data, index) in datasetUsageRefundsHistory">
                                <div class="data-unit">
                                    <a class="data-unit__link" href="#none" @click="data.expanded = !data.expanded">
                                        <dl class="data-unit__caption data-unit__title">
                                            <dt class="data-unit__title main-title">{{data.reason}} {{'#'+data.degree}}</dt>
                                        </dl>
                                    </a>
                                    <div class="data-unit__info data-sub-title">
                                        <div>
                                            <span class="data-unit__url">{{data.createdAt | date}}</span>
                                        </div>
                                    </div>
                                </div>
                                <div :class="{'hidden': data.expanded}">
                                    <div class="refund-box">
                                        <p class="refund-box__not info material-icons">{{data.refuseReason}}</p>
                                        <span class="data-unit__url">{{data.refuseAt | date}}</span>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </ul>
            </div>
            <div class="modal__footer">
                <div class="button__group">
                    <a class="button button__outline--secondary js-modal-close" href="#none" @click="closeDatasetRefundHistoryPopup">닫기</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
var datasetRefundHistoryPopupMixin={
	data: function(){
		return {
			datasetUsageRefunds: {},
			datasetUsageRefundsHistory: [],
			datasetRefundHistoryPopupVisible: false,
		}
	},
	methods: {
		openDatasetRefundHistoryPopup: function(refundsObj){
			vm.datasetRefundHistoryPopupVisible       = true;
			vm.datasetUsageRefunds = refundsObj;
			vm.datasetUsageRefundsHistory = refundsObj.datasetUsageRefundsHistory.map(function(data){
				data.expanded = false;
				return data;
			});
			$("html").addClass("is-scroll-blocking");
		},
		closeDatasetRefundHistoryPopup: function(e, success){
			vm.datasetRefundHistoryPopupVisible = false;
			vm.datasetUsageRefunds = {};
			$("html").removeClass("is-scroll-blocking");
		},
	}
}
</script>