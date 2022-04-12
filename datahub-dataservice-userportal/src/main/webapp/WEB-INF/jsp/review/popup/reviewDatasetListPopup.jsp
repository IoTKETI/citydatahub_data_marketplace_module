<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal" :class="reviewDatasetListPopupVisible ? 'js-modal-show':''">
  <!-- modal -->
  <div class="modal__outer">
    <div class="modal__inner">
      <div class="modal__header">
        <h3 class="hidden">활용데이터 선택 팝업</h3>
        <button class="modal__button--close js-modal-close material-icons" type="button" @click="closeReviewDatasetListPopup"><span class="hidden">모달 닫기</span></button>
      </div>
      <div class="modal__body">
        <section class="modal__select-form">
          <div class="select-form">
            <fieldset>
              <legend>상세 목록 필터 폼</legend>
              <div class="select-form__group">
                <select class="select" v-model="pageInfo.schCondition">
                  <option value="">선택</option>
                  <option value="title">데이터명</option>
                </select>
                <input class="input input__search" type="text" placeholder="검색어를 입력하세요" v-model="pageInfo.schValue" @keypress.enter="getDatasetList()">
                <button class="button__search material-icons" type="button" @click="getDatasetList()"><span class="hidden">검색</span></button>
              </div>
            </fieldset>
          </div>
        </section>
        <div class="fixed-table__wrap">
          <div class="fixed-table__thead">
            <table class="fixed-table">
              <caption>해당 테이블에 대한 캡션</caption>
              <colgroup>
                <col width="5%">
                <col width="10%">
                <col width="*">
                <col width="*">
                <col width="*">
                <col width="*">
              </colgroup>
              <thead>
                <tr>
                  <th scope="col">
                    <input id="allChk" class="checkbox hidden" type="checkbox">
                    <label for="allcheck1" class="label__checkbox label--all-checkbox material-icons" @click="allChkClick"></label>
                  </th>
                  <th scope="col">구분</th>
                  <th scope="col">데이터명</th>
                  <th scope="col">카테고리</th>
                  <th scope="col">소유자</th>
                  <th scope="col">등록일자</th>
                </tr>
              </thead>
            </table>
          </div>
          <div class="fixed-table__tbody" style="height:133px">
            <table class="fixed-table">
              <caption>해당 테이블에 대한 캡션</caption>
              <colgroup>
                <col width="5%">
                <col width="10%">
                <col width="*">
                <col width="*">
                <col width="*">
                <col width="*">
              </colgroup>
              <tbody>
                <tr v-for="(dataset, index) in datasetList">
                  <th class="text--center" scope="row">
                    <input :id="'chk_'+index" class="checkbox hidden" type="checkbox" :value="dataset" v-model="selectedDatasetList">
                    <label :for="'chk_'+index" class="label__checkbox material-icons"></label>
                  </th>
                  <th scope="row" class="text--center">{{index + 1}}</th>
                  <td class="text--left">{{dataset.title}}</td>
                  <td>{{dataset.categoryName}}</td>
                  <td class="text--left">{{dataset.modelOwnerId}}</td>
                  <td>{{dataset.createdAt|date}}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="modal__footer">
          <div class="button__group">
            <a class="button button__primary" href="#none" @click="selectDatasetList">저장</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
var reviewDatasetListPopupMixin={
	data: function(){
		return {
			reviewDatasetListPopupVisible: false,
			selectedDatasetList: [],
			selectReviewDatasetListCallback: function(){},
			datasetList: []
		}
	},
	mounted: function(){
	},
	methods: {
		selectDatasetList: function(){
			vm.selectReviewDatasetListCallback(vm.selectedDatasetList);
			vm.closeReviewDatasetListPopup();
		},
		openReviewDatasetListPopup: function(callback, parentDatasetList){
			vm.selectReviewDatasetListCallback = callback;
			vm.reviewDatasetListPopupVisible = true;
			
			vm.getDatasetList();
			
			$("html").addClass("is-scroll-blocking");
			
			if (parentDatasetList != null) {
				vm.selectedDatasetList = parentDatasetList;
			}
		},
		closeReviewDatasetListPopup: function(){
			vm.reviewDatasetListPopupVisible = false;
			
			$("html").removeClass("is-scroll-blocking");
		},
		getDatasetList: function() {
			var searchData = {};
            searchData.schValue     = vm.pageInfo.schValue;
            searchData.schCondition = vm.pageInfo.schCondition;
            searchData.reqType      = "review";
            
            var request = $.ajax({
                url : SMC_CTX + "/dataset/getList.do",
                method : "GET",
                dataType: "json",
                data: searchData
            });
            
            request.done(function(data) {
                vm.datasetList = data.list;
            });
            
            request.fail(function(data) {
                console.log("FAIL");
            });
        },
        allChkClick: function () {
            var chkFlag = false;
            
            if (!$("#allChk").is(":checked")) {
                chkFlag = true;
                
                vm.selectedDatasetList = [];
            } else {
                chkFlag = false;
            }
            
            $("#allChk").prop("checked", chkFlag);
            
            for (var i = 0; i < vm.datasetList.length; i++) {
                $("#chk_" + i).prop("checked", chkFlag);
                
                if(chkFlag) vm.selectedDatasetList.push(vm.datasetList[i]);
                else vm.selectedDatasetList = [];
            }
        }
	}
}
</script>