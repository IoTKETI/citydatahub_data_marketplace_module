<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal" :class="analyDatamodelPopupVisible ? 'js-modal-show':''">
	<div class="modal__outer">
		<div class="modal__inner">
            <div class="modal__header">
                <h4 class="modal__title">데이터 모델 선택 팝업</h4>
                <button class="modal__button--close js-modal-close material-icons" type="button" @click="closeAnalyDatamodelPopup"><span class="hidden">모달 닫기</span></button>
            </div>
            <div class="modal__body">
                <section class="modal__service-request">
		            <h4 class="modal__heading1">데이터 모델</h4>
		            <div class="fixed-table__wrap">
		            	<div class="fixed-table__thead">
			              <table class="fixed-table output-table">
				              <caption>해당 테이블에 대한 캡션</caption>
				              <colgroup>
				                <col width="10%">
				                <col width="20%">
				                <col width="*">
				                <col width="20%">
				              </colgroup>
				              <thead>
				                <tr>
				                  <th scope="col"><input id="allChk" class="checkbox hidden" type="checkbox">
				                    <label for="allcheck1" class="label__checkbox label--all-checkbox material-icons" @click="allChkClick"></label></th>
				                  <th scope="col">데이터 ID</th>
				                  <th scope="col">데이터 타입</th>
				                  <th scope="col">등록일자</th>
				                </tr>
				              </thead>
			              </table>
		            	</div>
		            	<div class="fixed-table__tbody" style="height:204px">
		            		<table class="fixed-table output-table">
				              <caption>해당 테이블에 대한 캡션</caption>
				              <colgroup>
				                <col width="10%">
				                <col width="20%">
				                <col width="*">
				                <col width="20%">
				              </colgroup>
				              <tbody>
				                <tr v-for="(list, index) in datamodelList">
				                  <th class="text--center" scope="row">
				                    <input :id="'chk_'+index" class="checkbox hidden" type="checkbox" :value="list" v-model="selectedDatamodel">
				                    <label :for="'chk_'+index" class="label__checkbox material-icons"></label>
				                  </th>
				                  <td class="text--left">{{list.id}}</td>
				                  <td class="text--left">{{list.typeUri}}</td>
				                  <td class="text--left">{{list.creationTime|date}}</td>
				                </tr>
				              </tbody>
				            </table>
		            	</div>
		            </div>
	          </section>
            </div>
            <div class="modal__footer">
            	<div class="button__group">
	            	<button class="button button__primary" type="button" @click="selectDatamodel">선택</button>
	                <button class="button button__secondary" type="button" @click="closeAnalyDatamodelPopup">닫기</button>
            	</div>
            </div>
        </div>
	</div>
</div>
<script>
var analyDatamodelPopupMixin = {
        data: function() {
            return {
                analyDatamodelPopupVisible: false,
                selectedDatamodel: [],
                selectDatamodelCallback: function () {},
                datamodelList: [],
                isSingle: false
            }
        },
        mounted: function() {
            var request = $.ajax({
            	method: "GET",
                url: SMC_CTX + "/dataset/model/getList.do",
                dataType: "json"
            });
            request.done(function(data) {
            	vm.datamodelList = data;
            });
        },
        methods: {
            selectDatamodel: function() {
            	if(vm.selectedDatamodel.length === 0 ){
            		
            		alert("데이터 모델을 선택해주세요.");
            		return;
            	}else{
	            	if(vm.singleFlag){
	            		if(vm.selectedDatamodel.length > 1){
	            			alert("데이터 모델을 1개만 선택해주세요.");
	            			return;
	            		}
	            	    vm.selectDatamodelCallback(vm.selectedDatamodel[0]);
	            	}else{
		                vm.selectDatamodelCallback(vm.selectedDatamodel);
	            	}
	                vm.closeAnalyDatamodelPopup();
            	}
            },
            openAnalyDatamodelPopup: function(callback, singleFlag) {
                vm.selectDatamodelCallback = callback;
                vm.analyDatamodelPopupVisible = true;
                vm.singleFlag = singleFlag;
                $("html").addClass("is-scroll-blocking");
            },
            closeAnalyDatamodelPopup: function() {
                vm.analyDatamodelPopupVisible = false;
                $("html").removeClass("is-scroll-blocking");
            },
            allChkClick: function () {
            	var chkFlag = false;
            	
            	if (!$("#allChk").is(":checked")) {
            		chkFlag = true;
            		
            		vm.selectedDatamodel = [];
            	} else {
            		chkFlag = false;
            	}
            	
            	$("#allChk").prop("checked", chkFlag);
            	
            	for (var i = 0; i < vm.datamodelList.length; i++) {
            		$("#chk_" + i).prop("checked", chkFlag);
            		
            		if(chkFlag) vm.selectedDatamodel.push(vm.datamodelList[i]);
            		else vm.selectedDatamodel = [];
            	}
            }
        }
}
</script>