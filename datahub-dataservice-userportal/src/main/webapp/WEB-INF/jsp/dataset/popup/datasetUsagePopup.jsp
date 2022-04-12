<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="modalDefault" class="modal" :class="datasetUsagePopupVisible ? 'js-modal-show':''">
  <div class="modal__outer">
	<div class="modal__inner">
	  <div class="modal__header">
		<h3 class="hidden">데이터셋 이용신청 팝업 - Http</h3>
		<button class="modal__button--close js-modal-close material-icons" type="button" @click="closeDatasetUsagePopup"><span class="hidden">모달 닫기</span></button>
	  </div>
	  <div class="modal__body">
		<section class="modal__service-request">
		  <h4 class="modal__heading1">데이터 이용 신청</h4>
		  <div class="modal__box">
			  <p>데이터 허브 "데이터셋 이용가이드" 문서를 참조하여 API 인증/인가 절차에 따라 데이터를 수집할 수 있습니다.<br>데이터 이용 신청이 완료되면 "Data Polling BaseURI"를 통해 데이터를 수집 할 수 있으며, 자세한 사용 방법은 "데이터셋 이용가이드" 문서를 참조하시기 바랍니다.</p>
		  </div>
		  <div class="modal__buttons">
			  <a class="button button__danger" href="#none">이용가이드 바로가기</a>
			  <a class="button button__secondary" href="#none">이용가이드 다운로드</a>
		  </div>
		</section>
		

		<section class="modal__service-request">
			<h4 class="modal__heading1"><input id="checkbox" class="checkbox hidden" type="checkbox" @click="expanded = !expanded" v-model="expanded">
			<label for="checkbox" class="label__check label__checkbox js-all-checkbox material-icons"></label>데이터 제공(Endpoint 주소)</h4>
			<form v-if="expanded">
				<fieldset>
					<legend>데이터 검색 폼</legend>
					<table class="form-table_select">
						<caption>데이터 검색 테이블</caption>
						<colgroup>
							<col width="11%">
						</colgroup>
						<tbody> 
							<tr>
								<th scope="row">수신 방식</th>
								<td><select class="select" v-model="datasetUsageReception.protocol" ref="requestType">
										<option value="">선택</option>
										<c:forEach var="code" items="${n2m:getCodeList('CG_ID_PROTOCOL_TYPE')}">
											<option value="${code.codeId}">${code.codeName}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</fieldset>
				<div class="select-result__middle" v-if="datasetUsageReception.protocol === 'http'">
					<table class="select-result__table">
						<tbody class="list1">
							<tr>
								<th>서버 주소</th>
								<td><input class="input" type="text" value="" v-model="datasetUsageReception.url" ref="url"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="select-result__middle" v-if="datasetUsageReception.protocol === 'mqtt'">
					<table class="select-result__table">
						<tbody class="list1">
							<tr>
								<th>서버 주소</th>
								<td><input class="input" type="text" value="{{mqttIp}}" v-model="mqttIp" disabled></td>
							</tr>
							<tr>
								<th>토픽</th>
								<td><input class="input" type="text" value="/datahub/datasets/4e7daud8249198dua89s(20자리 랜덤 문자열)" v-model="datasetUsageReception.url" disabled></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="select-result__middle" v-if="datasetUsageReception.protocol === 'websocket'">
					<table class="select-result__table">
						<tbody class="list1">
							<tr>
								<th>서버 주소</th>
								<td><input class="input" type="text" value="{{webSocketIp}}" v-model="webSocketIp" disabled></td>
							</tr>
							<tr>
								<th>메시지</th>
								<td><input class="input" type="text" value="/datahub/datasets/4e7daud8249198dua89s(20자리 랜덤 문자열)" v-model="datasetUsageReception.url" disabled></td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
		</section>

		<section class="modal__service-request" v-if="datasetStatus === 'paidMode'">
			<h4 class="modal__heading1">결제 정보</h4>
			
			<div class="service-request__form">
				<div class="table-radio" v-for="datasetPricePolicies in datasetPricePoliciesList">
					<table>
						<input :id="'checkbox' + datasetPricePolicies.id" class="radio hidden" type="radio" name="datasetPricePolicies" v-model="datasetUsagePayment.priceId" :value="datasetPricePolicies.id" @click="selectDatasetPricePolicies(datasetPricePolicies)">
						<label :for="'checkbox' + datasetPricePolicies.id" class="label__radio-pay material-icons"></label>
						<tr>
							<th>{{datasetPricePolicies.title}}</th>
						</tr>
					</table>
					<h5 class="sub__heading5" v-if="datasetPricePolicies.trafficType === 'limit'">일일 제한 트래픽<br>{{datasetPricePolicies.limit|comma}}</h5>
					<h5 class="sub__heading5" v-if="datasetPricePolicies.trafficType === 'unlimit'">무제한 트래픽</h5>
				</div>
			</div>
		
			<form v-if="datasetUsagePayment.priceId">
				<fieldset>
					<legend>데이터 검색 폼</legend>
					<table class="form-table_select">
						<caption>데이터 검색 테이블</caption>
						<colgroup>
							<col width="10%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">결제 요금</th>
								<td>
									<select class="select" v-model="datasetUsagePayment.periodId" ref="period">
										<option value="">선택</option>
										<option :value="pricePoliciesPeriod.id" v-for="pricePoliciesPeriod in (selectedDatasetPricePolicies.pricePoliciesPeriodList)">{{pricePoliciesPeriod.periodCd}}일 / {{pricePoliciesPeriod.price|comma}}₩</option>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</fieldset>
			</form>
		</section>
	  </div>
	  <div class="modal__footer">
		<div class="button__group">
		  <a class="button button__secondary" href="#none" @click="createDatasetUsage" v-if="!hasDatasetUseRequest">신청</a>
		  <a class="button button__secondary" href="#none" @click="cancelDatasetUsage" v-if="hasDatasetUseRequest">이용신청해제</a>
		  <a class="button button__secondary" href="#none" @click="payDatasetUsage({payId: datasetUsagePayment.id, requestId: datasetUsagePayment.requestId})"    v-if="hasDatasetUseRequest && datasetStatus === 'paidMode' && datasetUsagePayment.payStatus !== 'pay_complete' && datasetUsagePayment.payStatus !== 'pay_error'">결제신청</a> <!-- 결제상태 대기 -->
		  <a class="button button__secondary" href="#none" @click="modifyDatasetUsage" v-if="hasDatasetUseRequest && (datasetStatus !== 'paidMode' || datasetUsagePayment.payStatus === 'pay_complete')">수정</a> <!-- 결제상태 완료 -->
		  <a class="button button__outline--secondary js-modal-close" href="#none" @click="closeDatasetUsagePopup">닫기</a>
		</div>
	  </div>
	</div>
  </div>
</div>
<script>
var datasetUsagePopupMixin={
	data: function(){
		return {
			datasetUsagePopupVisible: false,
			datasetUsage: {
				datasetId		: "",
				notificationUrl : "",
				requestType     : "",
			},
			datasetUsageReception: {
				protocol : "",
				url      : "",
			},
			datasetUsageRefund: {
				status      : "",
			},
			datasetUsagePayment: {
				priceId  : "",
				periodId : "",
			},
			datasetStatus: "",
			expanded: false,
			hasDatasetUseRequest: false,
			selectedDatasetPricePolicies: {},
			datasetPricePoliciesList: [],
			closeDatasetUsagePopupCallback: function(){},
		}
	},
	watch: {
		"datasetUsageReception.protocol": function(){
		    var chars = '4e7daud8249198dua89s'
	        var stringLength = 20
	        var randomstring = ''
	        for (let i = 0; i < stringLength; i++) {
	          var rnum = Math.floor(Math.random() * chars.length);
	          if(vm.datasetUsageReception.protocol === "mqtt" || vm.datasetUsageReception.protocol === "websocket"){
		          randomstring += chars.substring(rnum, rnum + 1);
	          }else{
	        	  randomstring = "";
	          }
	        }
		    
			if(vm.datasetUsageReception.protocol === "http"){
				if (vm.datasetUsageReception.url.match('http') == null) {
					vm.datasetUsageReception.url = "";
				}
				vm.datasetUsage.notificationUrl = vm.datasetUsageReception.url;					
				
			}else if(vm.datasetUsageReception.protocol === "mqtt"){
				if (vm.datasetUsageReception.url == "" || vm.datasetUsageReception.url.match('http') != null){ vm.datasetUsageReception.url = randomstring; }
				vm.datasetUsage.notificationUrl = vm.mqttIp+"/"+randomstring;
				
			}else if(vm.datasetUsageReception.protocol === "websocket"){
				if (vm.datasetUsageReception.url == "" || vm.datasetUsageReception.url.match('http') != null){ vm.datasetUsageReception.url = randomstring; }
				vm.datasetUsage.notificationUrl = vm.webSocketIp+"/"+randomstring;
				
			}
		}
	},
	methods: {
		selectDatasetPricePolicies: function(datasetPricePolicies){
			vm.selectedDatasetPricePolicies = datasetPricePolicies;
			vm.datasetUsagePayment.periodId = "";
		},
		getDatasetPricePoliciesList: function(datasetId){
			var request = $.ajax({
				url : SMC_CTX + "/dataset/datasetPricePolicies/getList.do",
				method : "GET",
				data: {
					datasetId: datasetId
				},
				dataType: "json",
			});
			request.done(function(data) {
				for (var i = 0; i < data.length; i++) {
					if(data[i].mqttIp != undefined){ vm.mqttIp = data[i].mqttIp }
					if(data[i].webSocketIp != undefined){ vm.webSocketIp = data[i].webSocketIp }
				}
				vm.datasetPricePoliciesList = data;
				vm.datasetPricePoliciesList.forEach(function(datasetPricePolicies){
					if(datasetPricePolicies.id == vm.datasetUsagePayment.priceId)
					vm.selectedDatasetPricePolicies = datasetPricePolicies;
				});
			});
			request.fail(function(data) {
				console.log(data);
				Modal.ERROR();
			});
		},
		validateDatasetUsage: function(){
			if(vm.expanded && !vm.datasetUsageReception.protocol){
				alert("수신 방식은 필수 값입니다.");
				vm.$refs['protocol'].focus();
				return false;
			}
			
			if(vm.expanded && !vm.datasetUsageReception.url){
				alert("서버 주소는 필수 값입니다.");
				vm.$refs['url'].focus();
				return false;
			}
			
			if(vm.datasetStatus === "paidMode" && !vm.datasetUsagePayment.priceId){
				alert("가격 정책은 필수 값입니다.");
				return false;
			}
			
			if(vm.datasetStatus === "paidMode" && !vm.datasetUsagePayment.periodId){
				alert("결제 요금은 필수 값입니다.");
				vm.$refs['period'].focus();
				return false;
			}
			
			return true;
		},
		createDatasetUsage: function(){
			if(!vm.validateDatasetUsage()) return;
			if(Modal.REGIST()) return;
			if(vm.datasetUsageReception.protocol === "http"){
				var ip = vm.datasetUsageReception.url;
				if (vm.datasetUsageReception.url.match('http://') == null) {
					vm.datasetUsageReception.url = "http://"+vm.datasetUsageReception.url	
				}
				vm.datasetUsage.notificationUrl = vm.datasetUsageReception.url;
			}
			
			var sendData = {
				datasetId		    : vm.datasetUsage.datasetId,
				notificationUrl     : vm.datasetUsage.notificationUrl,
				requestType         : vm.datasetUsageReception.protocol,
			};
			
			if(vm.expanded){
				$.extend(sendData, {
					datasetUsageReception : {
						protocol : vm.datasetUsageReception.protocol,
						url      : vm.datasetUsageReception.url,
					}
				});
			}

			if(vm.datasetStatus === "paidMode"){
				$.extend(sendData, {
					datasetUsagePayment : {
						payStatus   : "pay_request",
						priceId     : vm.datasetUsagePayment.priceId,
						periodId    : vm.datasetUsagePayment.periodId,
					}
				});
			}
			//step 1. 데이터셋 이용신청
			var request = $.ajax({
				url : SMC_CTX + "/dataset/use/create.do",
				method : "POST",
				contentType: "application/json",
				async: false,
				data : JSON.stringify(sendData),
				dataType: "json",
				success: function(data) { 
					//step 2. 유료일 결우 결제 로직
					if(vm.datasetStatus === "paidMode"){
						vm.payDatasetUsage(data);
					}else{
						Modal.OK();
						vm.closeDatasetUsagePopup({}, true);
					} 
				}, 
				error: function(err) { 
					Modal.ERROR(); 
				}
			});
		},
		modifyDatasetUsage: function(){
			if(!vm.validateDatasetUsage()) return;
			if(Modal.MODIFY()) return;
			//step 1. 데이터셋 이용신청
			var sendData = {
				id                  : vm.datasetUsage.id,
				datasetId		    : vm.datasetUsage.datasetId,
				notificationUrl     : vm.datasetUsage.notificationUrl,
			};
			
			if(vm.expanded){
				$.extend(sendData, {
					datasetUsageReception : {
						requestId : vm.datasetUsage.id,
						id        : vm.datasetUsageReception.id,
						protocol  : vm.datasetUsageReception.protocol,
						url       : vm.datasetUsageReception.url,
					}
				});
			}
			
			if(vm.datasetStatus === "paidMode"){
				$.extend(sendData, {
					datasetUsagePayment : {
						requestId   : vm.datasetUsage.id,
						id          : vm.datasetUsagePayment.id,
						priceId     : vm.datasetUsagePayment.priceId,
						periodId    : vm.datasetUsagePayment.periodId,
					}
				});
			}
			var request = $.ajax({
				url : SMC_CTX + "/dataset/use/modify.do",
				method : "PATCH",
				contentType: "application/json",
				async: false,
				data : JSON.stringify(sendData)
			});
			request.done(function(data) {
				Modal.OK();
				vm.closeDatasetUsagePopup({}, true);
			});
			request.fail(function(data) {
				console.log(data);
				Modal.ERROR();
			});
			
		},
		cancelDatasetUsage: function(){
			if(vm.datasetUsageRefund && vm.datasetUsageRefund.status && vm.datasetUsageRefund.status !== 'pay_complete'){
				alert("유료 이용신청의 경우 환불신청을 먼저 진행해주세요.");
				return;
			}
            if(!confirm("이용신청을 취소하시겠습니까?")) return;
            var request = $.ajax({
                url : SMC_CTX + "/dataset/use/remove.do",
                method : "DELETE",
                contentType: "application/json",
                data : JSON.stringify({
                    id        : vm.datasetUsage.id,
                    datasetId : vm.datasetUsage.datasetId,
                })
            });
            request.done(function(data) {
                Modal.OK();
    			vm.closeDatasetUsagePopup({}, true);
            });
		},
		payDatasetUsage: function(data){
			data = data;
			var w = 450;
			var h = 300;
			var xPos = (document.body.offsetWidth/2) - (w/2);
			var yPos = (document.body.offsetHeight/2) - (h/2);
			var params = {};
			params.id        = data.payId;		//유료결제정보 고유번호
			params.requestId = data.requestId;	//이용신청 고유번호
			params.datasetId = vm.datasetUsage.datasetId;			//데이터셋 고유번호
			var popWin = window.open(SMC_CTX + "/dataset/pay.do?" + $.param(params), "_blank", "width="+w+", height="+h+", left="+xPos+", top="+yPos+", menubar=yes, status=yes, titlebar=yes, resizable=yes");
			popWin.callback = function(){
				vm.closeDatasetUsagePopup({}, true);
			}
		},
		openDatasetUsagePopup: function(datasetObj, callback){
			var refreshOpen = datasetObj.datasetUseRequestList.length;
			if (refreshOpen == 0) {
				vm.datasetUsageReception.url = "";
				vm.datasetUsage.notificationUrl = "";
			}
			
			vm.datasetUsagePopupVisible       = true;
			vm.datasetUsage.id                = datasetObj.datasetUseRequestId;
			vm.datasetUsage.datasetId         = datasetObj.id;
			vm.hasDatasetUseRequest           = datasetObj.hasDatasetUseRequest;
			vm.datasetStatus                  = datasetObj.status;
			if(datasetObj.datasetUseRequestPayment){
				vm.datasetUsagePayment        = datasetObj.datasetUseRequestPayment;
			}
			if(datasetObj.datasetUseRequestReception){
				vm.expanded = true;
				vm.datasetUsageReception      = datasetObj.datasetUseRequestReception;
			}
			if(datasetObj.datasetUseRequestRefund){
				vm.datasetUsageRefund         = datasetObj.datasetUseRequestRefund;
			}
			vm.closeDatasetUsagePopupCallback = callback;
			$("html").addClass("is-scroll-blocking");
			if(datasetObj.datasetUseRequestReception === undefined){
				vm.datasetUsageReception.protocol = "";	
			}else{
				if(datasetObj.datasetUseRequestReception.protocol != "" && vm.datasetUsageReception.protocol != ""){
					vm.datasetUsageReception.protocol = datasetObj.datasetUseRequestReception.protocol;
				}				
			}				
			
			vm.getDatasetPricePoliciesList(datasetObj.id);
		},
		closeDatasetUsagePopup: function(e, success){
			vm.datasetUsagePopupVisible = false;
			vm.expanded = false;
			vm.datasetUsage.datasetId	   = "";
			vm.datasetUsage.notificationUrl = "";
			vm.datasetStatus = "";
			vm.datasetUsageReception.protocol = "";
			if($.isFunction(vm.closeDatasetUsagePopupCallback)){
				vm.closeDatasetUsagePopupCallback(success);
			}
			$("html").removeClass("is-scroll-blocking");
		},
	}
}
</script>