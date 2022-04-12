<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="modalDefault" class="modal" :class="datasetSatisfactionPopupVisible ? 'js-modal-show':''">
  <div class="modal__outer">
    <div class="modal__inner">
      <div class="modal__header">
        <h3 class="hidden">만족도 평가 팝업</h3>
        <button class="modal__button--close js-modal-close material-icons" type="button" @click="closeDatasetSatisfactionPopup"><span class="hidden">모달 닫기</span></button>
      </div>
      <div class="modal__body">
        <section class="modal__service-request">
          <div class="select-result__top">
            <h4 class="modal__heading1">만족도 평가</h4>
          </div>
          <div class="select-result__middle">
            <table class="select-result__table">
              <caption>해당 테이블에 대한 캡션</caption>
              <colgroup>
                <col width="13%">
                <col width="*">
              </colgroup>
              <tbody>
                <tr>
                  <th scope="row" class="text--center">별점</th>
                  <td>
                    <div class="data-unit__rating" style="display:flex;margin-top:6px;">
				      <h4 class="hidden">만족도 평가하기</h4>
				      <div class="rating" @click="openPopup('OPEN_POPUP_DATASET_INTEREST')">
				        <input class="rating__input" id="__rating5" type="radio" name="rating" value="5" v-model="datasetSatisfaction.score">
				        <label class="rating__label rating__label--full material-icons" for="__rating5" title="5점"></label>
				        <input class="rating__input" id="__rating4Half" type="radio" name="rating" value="4.5"  v-model="datasetSatisfaction.score">
				        <label class="rating__label rating__label--half material-icons" for="__rating4Half" title="4.5점"></label>
				        <input class="rating__input" id="__rating4" type="radio" name="rating" value="4" v-model="datasetSatisfaction.score">
				        <label class="rating__label rating__label--full material-icons" for="__rating4" title="4점"></label>
				        <input class="rating__input" id="__rating3Half" type="radio" name="rating" value="3.5"  v-model="datasetSatisfaction.score">
				        <label class="rating__label rating__label--half material-icons" for="__rating3Half" title="3.5점"></label>
				        <input class="rating__input" id="__rating3" type="radio" name="rating" value="3" v-model="datasetSatisfaction.score">
				        <label class="rating__label rating__label--full material-icons" for="__rating3" title="3점"></label>
				        <input class="rating__input" id="__rating2Half" type="radio" name="rating" value="2.5" v-model="datasetSatisfaction.score">
				        <label class="rating__label rating__label--half material-icons" for="__rating2Half" title="2.5점"></label>
				        <input class="rating__input" id="__rating2" type="radio" name="rating" value="2">
				        <label class="rating__label rating__label--full material-icons" for="__rating2" title="2점" v-model="datasetSatisfaction.score"></label>
				        <input class="rating__input" id="__rating1Half" type="radio" name="rating" value="1.5" v-model="datasetSatisfaction.score">
				        <label class="rating__label rating__label--half material-icons" for="__rating1Half" title="1.5점"></label>
				        <input class="rating__input" id="__rating1" type="radio" name="rating" value="1" v-model="datasetSatisfaction.score">
				        <label class="rating__label rating__label--full material-icons" for="__rating1" title="1점"></label>
				        <input class="rating__input" id="__ratingHalf" type="radio" name="rating" value="0.5" v-model="datasetSatisfaction.score">
				        <label class="rating__label rating__label--half material-icons" for="__ratingHalf" title="0.5점"></label>
				      </div>
				    </div>
                  </td>
                </tr>
                <tr>
                  <th scope="row" class="text--center">후기</th>
                  <td><textarea class="textarea" placeholder="후기를 입력해주세요." v-model="datasetSatisfaction.review"></textarea></td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>
      </div>
      <div class="modal__footer">
        <div class="button__group">
          <a class="button button__secondary" href="#none" @click="createDatasetSatisfaction">확인</a>
          <a class="button button__outline--secondary js-modal-close" href="#none" @click="closeDatasetSatisfactionPopup">닫기</a>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
var datasetSatisfactionPopupMixin={
	data: function(){
		return {
			datasetSatisfactionPopupVisible: false,
			datasetSatisfaction: {
				datasetId    : "",
				datasetTitle : "",
				score        : "",
				review       : "",
				providerId   : ""
			},
			closeDatasetSatisfactionPopupCallback: function(){},
		}
	},	
	methods: {
		createDatasetSatisfaction: function(){
			if(Modal.REGIST()) return;
			vm.loadingFlag = true;
			$.ajax({
				method:"POST",
				url: SMC_CTX + "/mypage/token/transfer.do",
				contentType: "application/json",
				dataType: "json",
				data: JSON.stringify({
					toId       : vm.loginUserId,
					amount     : new String(Math.floor(vm.datasetSatisfaction.score)),
					additional : JSON.stringify({
						type         : "만족도",
						datasetId    : vm.datasetSatisfaction.datasetId,
						datasetTitle : vm.datasetSatisfaction.datasetTitle,
					})
				})
			})
			.done(function(data){
				if(data.result < 0){
					alert("평점 저장 시 오류가 발생했습니다.");
					vm.loadingFlag = false;
					return;
				}
				var request = $.ajax({
					method: "POST",
					url: SMC_CTX + "/dataset/satisfaction/create.do",
					contentType: "application/json",
					data: JSON.stringify({
						datasetId : vm.datasetSatisfaction.datasetId,
						score     : vm.datasetSatisfaction.score,
						review    : vm.datasetSatisfaction.review,
					})
				});
				request.done(function(){
					Modal.OK();
					vm.closeDatasetSatisfactionPopup({}, true);
					vm.loadingFlag = false;
				});
				request.fail(function(){
					alert("평점 저장 시 오류가 발생했습니다.");
					vm.loadingFlag = false;
				});
			})
			.fail(function(){
				alert("평점 저장 시 오류가 발생했습니다.");
				vm.loadingFlag = false;
			});
		},
		openDatasetSatisfactionPopup: function(datasetId, datasetTitle, providerId, callback){
			vm.datasetSatisfaction.datasetId         = datasetId;
			vm.datasetSatisfaction.datasetTitle      = datasetTitle;
			vm.datasetSatisfaction.providerId        = providerId;
			vm.datasetSatisfactionPopupVisible       = true;
			vm.closeDatasetSatisfactionPopupCallback = callback;
			$("html").addClass("is-scroll-blocking");
		},
		closeDatasetSatisfactionPopup: function(e, success){
			vm.datasetSatisfactionPopupVisible  = false;
			vm.datasetSatisfaction.datasetId    = "";
			vm.datasetSatisfaction.datasetTitle = "";
			vm.datasetSatisfaction.providerId   = "";
			vm.datasetSatisfaction.score        = "";
			vm.datasetSatisfaction.review       = "";
			if($.isFunction(vm.closeDatasetSatisfactionPopupCallback)){
				vm.closeDatasetSatisfactionPopupCallback(success);
			}
			$("html").removeClass("is-scroll-blocking");
		},
	}
}
</script>