<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal" :style="userPopupVisible ? 'display:block;':'display:none;'">
	<div class="modal__wrap">
		<div class="modal__content w-650">
			<div class="modal__header">
				<h4 class="modal__title">{{popupInfo.title}}</h4>
				<button class="modal__button--close" type="button" @click="closeUserPopup"><span class="hidden">모달 닫기</span></button>
			</div>
			<div class="modal__body">
				<section class="section">
					<div class="section__header">
						<h4 class="section__title" v-if="authgroup.authgroupGbCd == 'adminPortal'">관리자 목록</h4>
						<h4 class="section__title" v-if="authgroup.authgroupGbCd == 'normalPortal'">사용자 목록</h4>
					</div>
					<div class="section__content">
						<el-table :data="userList" border style="width: 100%" empty-text="검색  결과가 존재하지 않습니다." @selection-change="handleSelectionChangePopup" v-loading="userPopuploading" @sort-change="onSortChange">
							<el-table-column type="selection" width="55" label=""></el-table-column>
				      		<el-table-column prop="userId" label="사용자 아이디" width="180" align="left" v-if="authgroup.authgroupGbCd == 'normalPortal'"></el-table-column>
				      		<el-table-column prop="userId" label="관리자 아이디" width="180" align="left" v-if="authgroup.authgroupGbCd == 'adminPortal'"></el-table-column>
						    <el-table-column prop="name" label="사용자이름" align="left" v-if="authgroup.authgroupGbCd == 'normalPortal'"></el-table-column>
						    <el-table-column prop="name" label="관리자이름" align="left" v-if="authgroup.authgroupGbCd == 'adminPortal'"></el-table-column>
						</el-table>
						<n2m-pagination :pager="pageInfo" @pagemove="getUserList"/>
					</div>
				</section>
			</div>
			<div class="modal__footer">
				<button class="button__primary" type="button" @click="plusUserData">추가</button>
				<button class="button__secondary" type="button" @click="closeUserPopup">닫기</button>
			</div>
		</div>
	</div>
</div>
<script>
var userSearchPopupMixin = {
	data : function() {
		return {
			popupInfo : {
				title : "사용자 추가"
			},
			userList : [],
			userListSelect : [],
			userPopupVisible : false,
			userPopupEdit : false,
			userPopuploading : true,
			userType : ""
		}
	},
	methods : {
		closeUserPopup : function() {
			//user List 업데이트 
			vm.userPopupEdit = false;
			vm.userPopupVisible = false;
		},
		getUserList : function(curPage) {
			vm.userPopuploading = true;
			var request = $.ajax({
				url : N2M_CTX + "/user/getList.do?user=" + vm.userType,
				method : "GET",
				dataType : "json",
				data: {
					schCondition : vm.pageInfo.schCondition,
                    schValue     : vm.pageInfo.schValue,
                    schCondition2 : vm.pageInfo.schCondition2,
                    curPage      : curPage,
                    pageListSize : 5
				}
			});
			request.done(function(data) {
				vm.userPopuploading = false;
				if(data.userList==undefined){
					//관리자
					vm.userList = data.list;
				} else {
					//일반사용자
				    vm.userList = data.userList;
				}
				
				vm.pageInfo = data.page;
			});
			request.fail(function(data) {
				vm.userPopuploading = false;
			});
		},
		handleSelectionChangePopup : function(val) {
			this.userListSelect = val;
		},
		openUserPopup : function(whichUser) {
			vm.userType = whichUser;
			
			vm.getUserList(1);
			vm.userPopupEdit = true;
			vm.userPopupVisible = true;
		},
		plusUserData : function() {
			var prevData = vm.authUserList;
			var PlusData = vm.userListSelect;
			
			PlusData = PlusData.filter(function(val) {
				var flag = false;
				
				for (var i = 0; i < prevData.length; i++) {
					if (prevData[i].userId == val.userId) {
						flag = true;
						break;
					}
				}
				
				if (!flag) {
					return val;
				}
			});
			
			var newData = [];
			
			if (PlusData.length > 0) {
				for (var i = 0; i < PlusData.length; i++) {
					var newObj = {
							authgroupOid : vm.authgroup.authgroupOid,
							userId : PlusData[i].userId,
							userNm : PlusData[i].name
					};
					
					newData.push(newObj);
				}
			}
			
			var nextData = prevData.concat(newData);
		    
			vm.authUserList = nextData;
			vm.userPopupVisible = false;
			vm.userPopupEdit = false;
		},
		onSortChange : function(column) {
			vm.pageInfo.sortField = column.prop;
			vm.pageInfo.sortOrder = column.order;
			vm.getUserList(1);
		}
	}
}
</script>