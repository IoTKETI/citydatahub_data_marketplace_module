<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal" :style="userSearchPopupVisible ? 'display: block' : ''">
  <div class="modal__wrap">
    <div class="modal__content w-650">
      <div class="modal__header">
        <h4 class="modal__title">협업개발자 검색 팝업</h4>
        <button class="modal__button--close button__modal--close" type="button" @click="closeUserSearchPopup"><span class="hidden">모달 닫기</span></button>
      </div>
      <div class="modal__body">
        <div class="section">
          <!-- <div class="section__header">
            <h4 class="section__title">데이터모델 검색</h4>
          </div> -->
          <div class="section__content">
            <table class="table--row">
              <caption>데이터모델 검색 테이블</caption> 
              <colgroup>
                <col style="width: 150px;"> 
                <col style="width: auto;">
              </colgroup> 
              <tbody>
                <tr>
                  <th>검색어</th> 
                  <td>
                    <select class="select">
                      <option value="선택">사용자ID</option> 
                      <option value="선택">사용자명</option> 
                    </select>
                    <input type="text" placeholder="검색어를 입력하세요." class="input__text">
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="button__group">
            <button type="button" class="button__primary">검색</button>
          </div>
        </div>
        
        <div class="section">
          <div class="section__content">
            <table class="table--column">
              <caption>테이블 제목</caption>
              <colgroup>
                <col style="width:60px">
                <col style="width:auto">
              </colgroup>
              <thead>
                <tr>
                  <th>
                    <input id="userallcheck" class="input__checkbox hidden" type="checkbox" @click="onClickUserPopup('SELECT_ALL_USER')" v-model="selectedUserFl">
                    <label class="label__checkbox material-icons" for="userallcheck"></label>
                  </th>
                  <th>사용자ID</th>
                  <th>사용자명</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(user, index) in userList">
                  <td>
                      <input :id="'userchk_'+index" class="input__checkbox hidden" type="checkbox" :value="user" v-model="selectedUserList">
                      <label :for="'userchk_'+index" class="label__checkbox material-icons"></label>
                  </td>
                  <td>{{user.userId}}</td>
                  <td>{{user.nickname}}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="modal__footer">
        <button class="button__primary" type="button" @click="selectUser">선택</button><button class="button__secondary button__modal--close" type="button"  @click="closeUserSearchPopup">닫기</button>
      </div>
    </div>
  </div>
</div>


<script>
var userSearchPopupMixin={
	data: function(){
		return {
			schValue: "",
			userList: [],
			selectedUserFl: false,
			selectedUserList: [],
			userSearchPopupVisible: false,
			selectUserCallback: function(){},
		}
	},	
	methods: {
		onKeyPressUserPopup: function(code){
	   		switch(code){
	   		case 'SEARCH_USER_LIST':
	   			vm.searchUserList();
	   			break;
	   		}
	   	},
	   	onClickUserPopup: function(code, data){
	   		switch(code){
	   		case 'SELECT_ALL_USER':
	   			if(vm.selectedUserFl){
	   				vm.selectedUserList = [];
	   			}else{
		   			vm.userList.forEach(function(user){
			   			vm.selectedUserList.push(user);
		   			});
	   			}
	   			break;
	   		case 'SEARCH_USER_LIST':
	   			vm.searchUserList();
	   			break;
	   		}
	   	},
	   	searchUserList: function(){
	   		var request = $.ajax({
	   			method: 'GET',
	   			url: N2M_CTX + "/dataset/user/getList.do",
	   			dataType: 'json'
	   		});
	   		request.done(function(data){
	   			for(key in data){
	   				if( data[key] instanceof Object){
			   			vm.userList.push(data[key]);
	   				}
	   				
	   			}
	   		});
	   	},
	   	selectUser: function(){
	   		vm.selectUserCallback(vm.selectedUserList);
			vm.closeUserSearchPopup();
		},
		openUserSearchPopup: function(callback){
			vm.selectUserCallback = callback;
			vm.userSearchPopupVisible = true;
			vm.searchUserList();
			$("html").addClass("is-scroll-blocking");
		},
		closeUserSearchPopup: function(){
			vm.userSearchPopupVisible = false;
			vm.schValue= "";
			vm.userList= [];
			vm.selectedUserList = [];
			vm.selectedUserFl = false;
			$("html").removeClass("is-scroll-blocking");
		}
	}
}
</script>