<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="modalDefault" class="modal" :class="datasetRequestPaidPopupVisible ? 'js-modal-show':''">
	<div class="modal__outer">
		<div class="modal__inner">
			<div class="modal__header">
				<h3 class="hidden">모달 타이틀</h3>
				<button class="modal__button--close js-modal-close material-icons" type="button" @click="closeDatasetRequestPaidPopup"><span class="hidden">모달 닫기</span></button>
			</div>
			<div class="modal__body">
				<section class="modal__service-request">
					<h4 class="modal__heading1">데이터셋 유료 비용 정보 입력</h4>
					<div>
						<ul class="modal__select-result__info">
							<li class="modal-cover__title--info material-icons">
								<p>아래는 가격정책 설정 정보가 보여집니다.</p>
								<p>가격 정책은 최소 1개 이상 선택 해야 하며, 선택 된 가격 정책만 데이터셋을 이용신청 하려는 대상자에게 구매 정보로 표시하게 됩니다.</p>
								<p>가격 정책의 기간 및 비용 정보도 최소 1개 이상 선택을 해야 합니다.</p>
							</li>
						</ul>
					</div>
				</section>
				<section class="modal__select-pay" :class="{ checked: containsPricePolicies(pricePolicies) }" v-for="pricePolicies in pricePoliciesList">
					<div class="select-result__pay">
						<table class="select-result__table">
                            <input :id="'checkbox' + pricePolicies.id" class="checkbox hidden" type="checkbox" v-model="selectedPricePoliciesList" :value="pricePolicies">
                            <label :for="'checkbox' + pricePolicies.id" class="label__check-top label__checkbox js-all-checkbox material-icons"></label>
							<tbody>
								<tr>
									<td>
										<div class="fixed-table fixed-table-color">
											<div class="table-text">
												<table>
													<thead>
														<tr>
															<th scope="col" style="font-size: 15px;">{{pricePolicies.title}}</th>
														</tr>
													</thead>
												</table>
												<h5 class="sub__heading4" v-if="pricePolicies.trafficType === 'limit'">일일 제한 트래픽<br>{{pricePolicies.limit|comma}}</h5>
												<h5 class="sub__heading4" v-if="pricePolicies.trafficType === 'unlimit'">무제한 트래픽</h5>
											</div>
											<div class="table-text-pay" style="height:220px">
												<table>
													<caption>해당 테이블에 대한 캡션</caption>
													<colgroup>
                                                    	<col width="5%">
                                                    	<col width="40%">
                                                    	<col width="50%">
                                                    	<col width="5%">
                                                    	<col width="17px">
													</colgroup>
													<tbody>
														<tr v-for="pricePoliciesPeriod in pricePolicies.pricePoliciesPeriodList" :class="{ checked: pricePoliciesPeriod.checked && containsPricePolicies(pricePolicies)}">
															<th scope="row">
																<input :id="'checkbox' + pricePolicies.id + '-' + pricePoliciesPeriod.id" v-model="pricePoliciesPeriod.checked" class="checkbox hidden" type="checkbox" :disabled="!containsPricePolicies(pricePolicies)">
																<label :for="'checkbox' + pricePolicies.id + '-' + pricePoliciesPeriod.id" class="label__checkbox material-icons"></label>
															</th>
															<td>{{pricePoliciesPeriod.periodNm}}</td>
															<td><input class="input-text" type="text" v-model="pricePoliciesPeriod.priceTxt" @input="inputCurrency(pricePoliciesPeriod)" maxlength="11" :disabled="!pricePoliciesPeriod.checked || !containsPricePolicies(pricePolicies)"></td>
															<td>₩</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</section>
			</div>

			<div class="modal__footer">
				<div class="button__group">
					<a class="button button__secondary" href="#none" @click="paidRequestDataset">전환 요청</a>
					<a class="button button__outline--secondary js-modal-close" href="#none" @click="closeDatasetRequestPaidPopup">닫기</a>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
var datasetRequestPaidPopupMixin={
	data: function(){
		return {
			datasetRequestPaidPopupVisible: false,
			datasetRequestPaid: {
				datasetId		: "",
			},
			pricePoliciesList: [],
			selectedPricePoliciesList: [],
			closeDatasetRequestPaidPopupCallback: function(){},
		}
	},
	methods: {
		getPricePoliciesList: function(){
			var request = $.ajax({
				url : SMC_CTX + "/dataset/pricePolicies/getList.do",
				method : "GET",
				dataType: "json",
			});
			request.done(function(data) {
				vm.pricePoliciesList = data;
			});
			request.fail(function(data) {
				console.log(data);
			});
		},
        paidRequestDataset: function(){
            var pricePoliciesList = vm.selectedPricePoliciesList.map(function(pricePolicies){
            	return {
            		datasetId               : vm.datasetRequestPaid.datasetId,
            		title                   : pricePolicies.title,
            		trafficType             : pricePolicies.trafficType,
            		limit                   : pricePolicies.limit,
            		pricePoliciesPeriodList : pricePolicies.pricePoliciesPeriodList.map(function(pricePoliciesPeriod){
            			if(pricePoliciesPeriod.checked && pricePoliciesPeriod.price){
	            			return {
	            				periodCd : pricePoliciesPeriod.periodCd,
		            			price    : pricePoliciesPeriod.price,
	            			}
            			}
            		}).filter(function(item){return typeof item !== "undefined"})
            	}
            });

        	if(pricePoliciesList.length === 0){
        		alert("가격 정책은 최소 1개 이상 선택을 해야 합니다.");
        		return;
        	}
        	
        	var bRet = false;
        	for(var i=0; i<pricePoliciesList.length; i++){
        		var pricePolicies = pricePoliciesList[i];
        		if(pricePolicies.pricePoliciesPeriodList.length === 0){
        			bRet = true;
        		}
        	}
        	if(bRet){
        		alert("가격 정책의 기간 및 비용 정보는 최소 1개 이상 선택을 해야 합니다.");
        		return;
        	}

            if(!confirm("데이터셋을 유료화 요청하시겠습니까?")) return;
            
        	vm.loadingFlag = true;
            var request = $.ajax({
                method : "PATCH",
                url : SMC_CTX + "/dataset/modify.do",
                contentType: "application/json",
                data : JSON.stringify({
                    id                : vm.datasetRequestPaid.datasetId,
                    status            : "paidRequest",
                    pricePoliciesList : pricePoliciesList,
                })
            });
            request.done(function(data) {
            	Modal.OK();
            	location.href = SMC_CTX + "/dataset/pageDetail.do?id=${param.id}&backUrl=${param.backUrl}";
                vm.loadingFlag = false;
            });
            request.fail(function(e){
            	Modal.ERROR();
                vm.loadingFlag = false;
            });
        },
        containsPricePolicies: function(pricePolicies){
        	return vm.selectedPricePoliciesList.indexOf(pricePolicies) !== -1;
        },
		inputCurrency: function(pricePoliciesPeriod){
			pricePoliciesPeriod.price = pricePoliciesPeriod.priceTxt.replace(/[^0-9]/g, '');
			pricePoliciesPeriod.priceTxt = vm.$options.filters.comma(pricePoliciesPeriod.priceTxt.replace(/[^0-9]/g, ''));
		},
		openDatasetRequestPaidPopup: function(datasetId, callback){
			vm.datasetRequestPaidPopupVisible	    = true;
			vm.datasetRequestPaid.datasetId		    = datasetId;
			vm.closeDatasetRequestPaidPopupCallback = callback;
			$("html").addClass("is-scroll-blocking");
			vm.getPricePoliciesList();
		},
		closeDatasetRequestPaidPopup: function(e, success){
			vm.datasetRequestPaidPopupVisible	 = false;
			vm.datasetRequestPaid.datasetId	   = "";
			if($.isFunction(vm.closeDatasetRequestPaidPopupCallback)){
				vm.closeDatasetRequestPaidPopupCallback(success);
			}
			$("html").removeClass("is-scroll-blocking");
		},
	}
}
</script>