<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal" :class="datasetRefundPopupVisible ? 'js-modal-show':''">
  <div class="modal__outer">
     <div class="modal__inner">
         <div class="modal__header">
             <h3 class="hidden">모달 타이틀</h3>
             <button class="modal__button--close js-modal-close material-icons" type="button" @click="closeDatasetRefundPopup"><span class="hidden">모달 닫기</span></button>
         </div>
         <div class="modal__body">
             <h4 class="modal__heading3">환불 신청 팝업</h4>
         </div>
         <div class="sub__content">
             <div>
                 <h3 class="sub__heading6">데이터셋 결제 정보<small class="sub__heading6--small">결제번호 : {{datasetRefund.datasetUseRequestPayment.id}}</small></h3>
                 <ul>
                     <li class="data-list__item refund-top-menu">
                         <div class="data-unit">
                             <a class="data-unit__link" href="#none">
                                 <dl class="data-unit__caption data-unit__title">
                                     <dt class="data-unit__title main-title">{{datasetRefund.title}}</dt>
                                     <dd class="data-unit__category-top"><!-- 
                                      --><span class="data-unit__status">[{{datasetRefund.datasetUseRequestPolicies.trafficType == 'limit' ? '제한' : '무제한'}}]</span><!-- 
                                      -->{{datasetRefund.datasetUseRequestPolicies.limit | comma}} 트래픽 / {{datasetRefund.datasetUseRequestPayment.expiredAt | date}} 까지
                                     </dd>
                                 </dl>
                             </a>
                             <div class="data-unit__info data-sub-title">
                                 <div>
                                     <span class="data-unit__refund" style="padding-left: 19px">{{datasetRefund.datasetUseRequestPrice.price | comma}}원</span>
                                     <p class="sub-cover__title--date">결제 일자 : {{datasetRefund.datasetUseRequestPayment.completedAt | dateYMD}}</p>
                                 </div>
                             </div>
                         </div>
                     </li>
                 </ul>
             </div>
         </div>
         
         <div class="sub__content">
           <h5 class="sub__heading6">환불 산정 방식</h5>
           <div class="attach-file__why">
             <p class="attach-file__checked material-icons" href="#none">1일 기준 산정 = 결제 금액 / 전체 일수</p>
             <p class="attach-file__checked material-icons" href="#none">환불 총 금액 = 1일 기준 산정 * 잔여 일수</p>
           </div>
         </div>
         <div class="sub__content">
           <h5 class="sub__heading6">환불 신청 사유</h5>
           <textarea class="textarea" ref="refundReason" v-model="datasetRefund.reason" placeholder="환불 신청 사유를 입력해주세요."></textarea>
         </div>
         <div class="sub__content">
           <table class="payback">
             <colgroup>
               <col width="70%">
               <col width="">
               <col width="">
             </colgroup>
             <tbody>
               <tr>
                 <th scope="row"></th>
                 <td class="text--right">총 결제 금액 :</td>
                 <td class="text--right">{{datasetRefund.datasetUseRequestPrice.price | comma}} 원</td>
               </tr>
               <tr>
                 <th scope="row"></th>
                 <td class="text--right">사용 금액 :</td>
                 <td class="text--right">- {{usagePrice | comma}} 원</td>
               </tr>
               <tr>
                 <th scope="row"></th>
                 <td class="text--right">총 환불 금액 :</td>
                 <td class="text--right">{{datasetRefund.datasetUseRequestPrice.price - usagePrice | comma}} 원</td>
               </tr>
             </tbody>
           </table>
         </div>
         <div class="modal__footer">
             <div class="button__group">
                 <a class="button button__secondary" href="#none" @click="refundDatasetUsage">환불신청</a>
                 <a class="button button__outline--secondary" href="#none" @click="closeDatasetRefundPopup">닫기</a>
             </div>
         </div>
     </div>
  </div>
</div>
<script>
var datasetRefundPopupMixin={
	data: function(){
		return {
			datasetRefund: {
				datasetUseRequestPayment: {},
				datasetUseRequestPrice: {},
				datasetUseRequestPolicies: {},
			},
			datasetRefundPopupVisible: false,
			closeDatasetRefundPopupCallback: function(){},
		}
	},
	computed: {
		usagePrice: function(){
			if(!this.datasetRefund.datasetUseRequestPrice) return;
			if(!this.datasetRefund.datasetUseRequestPayment) return;
			var today = moment();
			var expiredDay = moment(this.datasetRefund.datasetUseRequestPayment.expiredAt);
			var expiredDays = expiredDay.diff(today, 'days');
			var dayPayPrice = this.datasetRefund.datasetUseRequestPrice.price / this.datasetRefund.datasetUseRequestPrice.periodCd;
			var totalPayPrice = this.datasetRefund.datasetUseRequestPrice.price - dayPayPrice * expiredDays;
			return Math.floor(totalPayPrice);
		}
	},
	methods: {
		refundDatasetUsage: function(){
			if(!vm.datasetRefund.reason){
				alert("환불 신청 사유는 필수 값입니다.");
				vm.$refs.refundReason.focus();
				return;
			}
			if(!confirm("환불 신청하시겠습니까?")) return;
			var request = $.ajax({
                url : SMC_CTX + "/dataset/use/refund/create.do",
                method : "POST",
                contentType: "application/json",
                data : JSON.stringify({
                    datasetId : vm.datasetRefund.id,
                    requestId : vm.datasetRefund.datasetUseRequestId,
                    reason    : vm.datasetRefund.reason,
                    status    : "pay_request",
                })
            });
            request.done(function(data) {
                Modal.OK();
                if($.isFunction(vm.closeDatasetRefundPopupCallback)){
                	vm.closeDatasetRefundPopupCallback();
                }
				vm.closeDatasetRefundPopup();
            });
            request.fail(function(){
				vm.closeDatasetRefundPopup();
            });
			
		},
		openDatasetRefundPopup: function(datasetRefund, callback){
			vm.datasetRefundPopupVisible       = true;
			vm.closeDatasetRefundPopupCallback = callback;
			vm.datasetRefund = datasetRefund;
			if(!vm.datasetRefund.datasetUseRequestPayment){
				vm.datasetRefund.datasetUseRequestPayment = {};
			}
			if(!vm.datasetRefund.datasetUseRequestPrice){
				vm.datasetRefund.datasetUseRequestPrice = {};
			}
			if(!vm.datasetRefund.datasetUseRequestPolicies){
				vm.datasetRefund.datasetUseRequestPolicies = {};
			}
			$("html").addClass("is-scroll-blocking");
		},
		closeDatasetRefundPopup: function(){
			vm.datasetRefundPopupVisible = false;
			$("html").removeClass("is-scroll-blocking");
		},
	}
}
</script>