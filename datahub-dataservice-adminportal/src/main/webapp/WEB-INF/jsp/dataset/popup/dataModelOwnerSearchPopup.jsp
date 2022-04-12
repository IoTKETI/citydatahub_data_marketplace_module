<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal" :style="dataModelOwnerSearchPopupVisible ? 'display: block' : ''">
  <div class="modal__wrap">
    <div class="modal__content w-650">
      <div class="modal__header">
        <h4 class="modal__title">제공자 검색 팝업</h4>
        <button class="modal__button--close" type="button" @click="closeDataModelOwnerSearchPopup"><span class="hidden">모달 닫기</span></button>
      </div>
      <div class="modal__body">
        <div class="section__content">
          <el-table ref="ownerTable" :data="ownerList" border style="width: 100%" empty-text="검색  결과가 존재하지 않습니다." @selection-change="handleSelectionChangePopup">
            <el-table-column type="selection" width="55" label=""></el-table-column>
            <el-table-column prop="userId" label="제공자ID" width="180" align="left"></el-table-column>
            <el-table-column prop="name" label="제공자명" align="left"></el-table-column>
          </el-table>
        </div>
      </div>
      <div class="modal__footer">
        <button class="button__primary" type="button" @click="selectOwner">선택</button><button class="button__secondary button__modal--close" type="button"  @click="closeDataModelOwnerSearchPopup">닫기</button>
      </div>
    </div>
  </div>
</div>


<script>
var dataModelOwnerSearchPopupMixin={
	data: function(){
		return {
			schValue: "",
			ownerList: [],
			selectedOwnerFl: false,
			selectedOwner: [],
			dataModelOwnerSearchPopupVisible: false,
			selectOwnerCallback: function(){},
		}
	},	
	methods: {
	   	searchOwnerList: function(){
	   		var request = $.ajax({
	   			method: 'GET',
	   			url: N2M_CTX + "/dataset/user/getList.do",
	   			data: {
	   				pageListSize: "10",
	   				curPage: "1",
	   			},
	   			dataType: 'json'
	   		});
	   		request.done(function(data){
	   			for(key in data){
	   				if( data[key] instanceof Object){
			   			vm.ownerList.push(data[key]);
	   				}
	   				
	   			}
	   		});
	   	},
	   	selectOwner: function(){
	   		vm.selectOwnerCallback(vm.selectedOwner);
			vm.closeDataModelOwnerSearchPopup();
		},
		openDataModelOwnerSearchPopup: function(callback){
			vm.selectOwnerCallback = callback;
			vm.dataModelOwnerSearchPopupVisible = true;
			vm.searchOwnerList();
			$("html").addClass("is-scroll-blocking");
		},
		closeDataModelOwnerSearchPopup: function(){
			vm.dataModelOwnerSearchPopupVisible = false;
			vm.schValue= "";
			vm.ownerList= [];
			vm.selectedOwner = [];
			vm.selectedOwnerFl = false;
			$("html").removeClass("is-scroll-blocking");
		},
		handleSelectionChangePopup : function(val) {
            if (val.length > 1) {
            	alert("1명의 제공자만 선택 가능합니다.");
            	
            	vm.$refs.ownerTable.clearSelection();
            } else if (val.length === 1) {
            	vm.selectedOwner[0] = val[0];
            } else if (val.length === 0) {
            	vm.selectedOwner = [];
            }
        },
	}
}
</script>