<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="modalDefault" class="modal" :style="datasetUsagePopupVisible ? 'display: block':''">
  <div class="modal__wrap">
    <div class="modal__content">
      <div class="modal__header">
        <h4 class="modal__title">데이터셋 이용신청 팝업</h4>
        <button class="modal__button--close js-modal-close material-icons" type="button" @click="closeDatasetUsagePopup"><span class="hidden">모달 닫기</span></button>
      </div>
      <div class="modal__body">
      	<div class="section">
	        <div class="section__content">
	          <h4 class="modal__heading1">데이터 수집 이용 신청</h4>
	          <div class="modal__box">
	              <p>데이터 허브 "데이터셋 이용가이드" 문서를 참조하여 API 인증/인가 절차에 따라 데이터를 수집할 수 있습니다.<br>데이터 이용 신청이 완료되면 "Data Polling BaseURI"를 통해 데이터를 수집 할 수 있으며, 자세한 사용 방법은 "데이터셋 이용가이드" 문서를 참조하시기 바랍니다.</p>
	          </div>
	          <div class="modal__buttons">
		          <a class="button button__danger" href="#none">이용가이드 바로가기</a>
		          <a class="button button__secondary" href="#none">이용가이드 다운로드</a>
		      </div>
	          <h5 class="modal__heading2 modal__heading2--border-none">데이터 제공(Endpoint 주소)</h5>
	          <div class="address-box">
	            <input class="input address-box__input address-box__input--full" type="text" :value="'${endpoint}/dataservice/dataset/'+ datasetUsage.datasetId + '/data'" disabled>
	          </div>
	          <h5 class="modal__heading2 modal__heading2--border-none" style="margin-top:30px">
	              <input id="receiveCheckbox" class="input__checkbox" type="checkbox" v-model="checkedReceive">
	              <label for="receiveCheckbox" class="label__checkbox"></label>
	                         실시간 데이터 수신 여부
	          </h5>
	          <div class="address-box" v-if="checkedReceive">
	              <input class="address-box__input address-box__input--full" type="text" placeholder="ex) http://전달 받을 주소를 입력해주세요." v-model="datasetUsage.notificationUrl">
	          </div>
	        </div>
      	</div>
      </div>
      <div class="modal__footer">
        <div class="button__group">
          <a class="button button__secondary" href="#none" @click="createDatasetUsage">신청</a>
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
				datasetId        : "",
				notificationUrl  : ""
			},
			closeDatasetUsagePopupCallback: function(){},
			checkedReceive: false
		}
	},	
	methods: {
		openDatasetUsagePopup: function(datasetId, callback){
			vm.datasetUsagePopupVisible = true;
			vm.datasetUsage.datasetId       = datasetId;
			vm.closeDatasetUsagePopupCallback = callback;
			$("html").addClass("is-scroll-blocking");
		},
		closeDatasetUsagePopup: function(e, success){
			vm.datasetUsagePopupVisible = false;
			vm.datasetUsage.datasetId       = "";
			vm.datasetUsage.notificationUrl = "";
			if($.isFunction(vm.closeDatasetUsagePopupCallback)){
				vm.closeDatasetUsagePopupCallback(success);
			}
			$("html").removeClass("is-scroll-blocking");
		},
		createDatasetUsage: function(){
        	if(Modal.REGIST()) return;
        	
        	var request = $.ajax({
                url : N2M_CTX + "/dataset/use/create.do",
                method : "POST",
                contentType: "application/json",
                data : JSON.stringify({
                    datasetId        : vm.datasetUsage.datasetId,
                    notificationUrl  : vm.datasetUsage.notificationUrl
                })
            });
        	
            request.done(function(data) {
            	Modal.OK();
	            vm.closeDatasetUsagePopup({}, true);
            });
            
            request.fail(console.log);
            
        },
	}
}
</script>