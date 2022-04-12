<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal" :style="datasetSatisfactionPopupVisible ? 'display:block;':'display:none;'">
  <div class="modal__wrap">
    <div class="modal__content">
      <div class="modal__header">
        <h4 class="modal__title">만족도 평가 팝업</h4>
        <button class="modal__button--close button__modal--close" type="button" @click="closeDatasetSatisfactionPopup"><span class="hidden">모달 닫기</span></button>
      </div>
      <div class="modal__body">
        <div class="section">
          <div class="section__header">
            <h4 class="section__title">만족도 평가</h4>
          </div>
          <div class="section__content">
            <table class="table--row">
              <caption>만족도 평가 테이블</caption> 
              <colgroup>
                <col style="width: 13%;">
                <col style="width: auto;">
              </colgroup> 
              <tbody>
                <tr>
                  <th>별점</th>
                  <td>
                    <div class="rating__wrap">
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
        </div>
      </div>

      <div class="modal__footer">
          <button class="button__primary" type="button" @click="createDatasetSatisfaction">확인</button><button class="button__secondary button__modal--close" type="button" @click="closeDatasetSatisfactionPopup">닫기</button>
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
				datasetId     : "",
				score  : "",
				review : ""
			},
			closeDatasetSatisfactionPopupCallback: function(){},
		}
	},	
	methods: {
		createDatasetSatisfaction: function(){
			if(Modal.REGIST()) return;
			
			var request = $.ajax({
				method: "POST",
				url: N2M_CTX + "/dataset/satisfaction/create.do",
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
			});
		},
		openDatasetSatisfactionPopup: function(datasetId, callback){
			vm.datasetSatisfaction.datasetId = datasetId;
			vm.datasetSatisfactionPopupVisible = true;
			vm.closeDatasetSatisfactionPopupCallback = callback;
			$("html").addClass("is-scroll-blocking");
		},
		closeDatasetSatisfactionPopup: function(e, success){
			vm.datasetSatisfactionPopupVisible = false;
			if($.isFunction(vm.closeDatasetSatisfactionPopupCallback)){
				vm.closeDatasetSatisfactionPopupCallback(success);
			}
			$("html").removeClass("is-scroll-blocking");
		},
	}
}
</script>