<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:set var="modified_mode" value="${modifiedYn eq 'Y' ? 'false' : 'true'}"/>
<div id="content" v-cloak>
<h3 class="content__title">권한 상세</h3>
<form>
  <fieldset>
	<legend>필드셋 제목</legend>
	<!-- section-write -->
	<section class="section">
		<div class="section__header">
		   <h4 class="section__title">권한그룹 상세</h4>
		</div>
	  <div class="section__content">
		<table class="table--row">
		  <caption>테이블 제목</caption>
		  <colgroup>
			<col style="width:150px">
			<col style="width:auto">
			<col style="width:150px">
			<col style="width:auto">
		  </colgroup>
		  <tbody>
			<tr>
			  <th>그룹명</th>
			  <td colspan="3">
			   {{authgroup.authgroupNm}}
			  </td>
			</tr>
			<tr>
			  <th>설명</th>
			  <td colspan="3" v-html="$options.filters.enter(authgroup.authgroupDesc)">
			  </td>
			</tr>
			<tr>
			  <th>구분</th>
			  <td colspan="3">
				{{authgroup.authgroupGbCdNm}}
			  </td>
			</tr>
			<tr>
			  <th>사용여부</th>
			  <td colspan="3">
			  <input class="input__checkbox" id="authgroupUseFl" type="checkbox" v-model="authgroup.authgroupUseFl" true-value="Y" false-value="N" disabled/><label class="label__checkbox">사용</label>
			  </td>
			</tr>
		  </tbody>
		</table>
	  </div>
	</section>
	<!-- //section-default -->
	<div class="button__group">
	  <c:if test="${modifiedYn eq 'Y'}">
	  <button class="button__primary" type="button"  onclick="location.href='<c:url value="/authgroup/pageModify.do?authgroupOid=${param.authgroupOid}"/>'">수정</button>
	  </c:if>
	  <button class="button__secondary" type="button" onclick="location.href='<c:url value="/authgroup/pageList.do"/>'">목록</button>
	</div>
	<form>
		  <fieldset>
			<legend>필드셋 제목</legend>
			<section class="section">
			  <div class="section__header" v-if="authgroup.authgroupGbCd == 'adminPortal'">
				<h4 class="section__title">권한설정(관리자목록)</h4>
			  </div>
			  <div class="section__header" v-if="authgroup.authgroupGbCd == 'normalPortal'">
			  	<h4 class="section__title">권한설정(사용자목록)</h4>
			  </div>
			  <div class="section__content">
				<el-table :data="authUserList" border style="width: 100%" @selection-change="handleSelectionChange" empty-text="데이터가 존재하지 않습니다.">
					<c:if test="${modifiedYn eq 'Y'}">
				  	<el-table-column type="selection" width="55" label=""></el-table-column>
				 	</c:if>
					<el-table-column prop="userId" label="사용자 ID"></el-table-column>
					<el-table-column prop="userNm" label="이름"></el-table-column>
<!-- 					<el-table-column prop="userCompanyNm" label="업체 "></el-table-column> -->
		  		</el-table>
			  </div>
			  <div class="button__group">
			  	<c:if test="${modifiedYn eq 'Y'}">
					<!-- 관리자 추가 -->
			  		<button class="button__util button__util--add material-icons" type="button" v-if="authgroup.authgroupGbCd=='adminPortal'" @click="openUserPopup('admin')">추가</button>
					<!-- 사용자 추가-->
			  		<button class="button__util button__util--add material-icons" type="button" v-if="authgroup.authgroupGbCd=='normalPortal'" @click="openUserPopup('normal')">추가</button>
			  		<button class="button__util button__util--remove material-icons" type="button" v-on:click="removeUserData">삭제</button>
			  	</c:if>
			  </div>
			</section>
		  </fieldset>
		<div class="button__group">
			<c:if test="${modifiedYn eq 'Y'}">
			  	<button class="button__primary" type="button" @click="modifyAuthgroupuser">저장</button>
			</c:if>
		</div>
	</form>
		<form>
		  <fieldset>
			<legend>필드셋 제목</legend>
			<section class="section">
			<div class="section__header">
				<h4 class="section__title">권한설정(메뉴 권한)</h4>
			</div>
			<div class="section__content">
				<el-table
					:data="authMenuList"
					style="width: 100%"
					row-key="menuOid"
					border
					lazy 
					:tree-props="{children: 'childrens'}"
					default-expand-all 
					empty-text="데이터가 존재하지 않습니다."
					:row-style="setRowStyle"
					>
					<el-table-column align="left" prop="menuNm" label="메뉴"></el-table-column>
					<el-table-column prop="readYn" align="center" label="읽기">
						<template slot-scope="scope" >
								<el-checkbox v-model="scope.row.readYn" true-label="Y" false-label="N" :disabled="${modified_mode}" @change.native="onCheckboxChange(scope.row, 'readYn')"></el-checkbox>
						</template>
					</el-table-column>
					<el-table-column prop="writeYn" label="쓰기">
						<template slot-scope="scope" >
								<el-checkbox v-model="scope.row.writeYn" true-label="Y" false-label="N" :disabled="${modified_mode}" @change.native="onCheckboxChange(scope.row, 'writeYn')"></el-checkbox>
						</template>
					</el-table-column>
					<el-table-column prop="modifiedYn" label="수정">
						<template slot-scope="scope" >
								<el-checkbox v-model="scope.row.modifiedYn" true-label="Y" false-label="N" :disabled="${modified_mode}" @change.native="onCheckboxChange(scope.row, 'modifiedYn')"></el-checkbox>
						</template>
					</el-table-column>
					<el-table-column prop="deleteYn" label="삭제">
						<template slot-scope="scope" >
								<el-checkbox v-model="scope.row.deleteYn" true-label="Y" false-label="N" :disabled="${modified_mode}" @change.native="onCheckboxChange(scope.row, 'deleteYn')"></el-checkbox>
						</template>
					 </el-table-column>
				</el-table>
			</div> 
			</section>
		</fieldset> 
		<c:if test="${modifiedYn eq 'Y'}"> 
			<div class="button__group">
			<button class="button__primary" type="button" @click="modifyAuthgroupmenu">저장</button>
			</div>
		</c:if>
		</form>
  </fieldset>
</form>
	<!-- 관리자 등록 팝업(사용자) -->
	<jsp:include page="/WEB-INF/jsp/authgroup/popup/userPopup.jsp"/>
</div>
<script>
  $(function(){
	  Vue.use(N2MPagination);
	  vm = new Vue({
		  el: '#content',
		  mixins:[userSearchPopupMixin],
		  data: function(){
			  return {
				  	authUserList: [],
					authMenuList: [],
					removeuserlist:[],
					authgroup : {
						authgroupOid: "${param.authgroupOid}"
					},
					checkedMenu:[],
// 					menuchecklist:[],
// 					userPortalMenuList: [],
					loading: true, 
				}
			},
			methods: {
				getAuthgroupUserList: function(){
					  var request = $.ajax({
						  url: N2M_CTX + "/authgroup/user/getList.do?authgroupOid="+vm.authgroup.authgroupOid,
						  method: "GET",
						  dataType: "json",
						  data: vm.authgroup,
						  timeout: 3000
						});
						request.done(function(data){
							vm.authUserList = data.list;
							vm.getAuthgroupMenuList();
						});
				},
				handleSelectionChange: function(val) {
				   this.removeuserlist = val;
				},
				removeUserData: function() {
					var prevData = vm.authUserList;
					for(var i=0; i<vm.authUserList.length ;i++){
						for(var j=0; j<vm.removeuserlist.length;j++){
							if(vm.authUserList[i].userId == vm.removeuserlist[j].userId){
								prevData.splice(i,1)
							}
						}
					}
				   vm.authUserList = prevData;
				},
				getAuthgroup: function(){
					vm.loading = true;
					var isAjaxing = false;
					var request = $.ajax({
						url: N2M_CTX + "/authgroup/get.do",
						method: "GET",
						dataType: "json",
						data: vm.authgroup
					});
					request.done(function(data){
						vm.authgroup = data;
						vm.getAuthgroupUserList();
						if(data.authgroupGbCd == "normalPortal"){
							vm.pageInfo.schCondition2 = "normalPortal";
						}
						else{
							vm.pageInfo.schCondition2 = "adminPortal";
						}
					});
				},
				modifyAuthgroupuser: function(){
					if(Modal.MODIFY()) return; 
					
					var request = $.ajax({
						url: N2M_CTX + "/authgroup/user/modify.do?authgroupOid="+vm.authgroup.authgroupOid,
						method: "POST",
						contentType: "application/json",
// 						dataType: "json",
						data: JSON.stringify({
							userList: vm.authUserList,
							authgroupOid : vm.authgroup.authgroupOid
						})
					});
					request.done(function(data){
						Modal.OK();
						location.href = N2M_CTX+"/authgroup/pageDetail.do?authgroupOid="+vm.authgroup.authgroupOid;
					});
				},
				modifyAuthgroupmenu : function(){
					if(Modal.MODIFY()) return;
					
						var request = $.ajax({
							url: N2M_CTX + "/authgroup/menu/modify.do?authgroupOid="+vm.authgroup.authgroupOid,
							method: "POST",
							contentType: "application/json",
// 							dataType: "json",
							data: JSON.stringify({
								menuList: vm.authMenuList,
								authgroupOid : vm.authgroup.authgroupOid
							}),
						});
						request.done(function(data){
							Modal.OK();
							location.href = N2M_CTX+"/authgroup/pageDetail.do?authgroupOid="+vm.authgroup.authgroupOid;
						});
				},
				getAuthgroupMenuList : function(){
					var request = $.ajax({
						  url: N2M_CTX + "/authgroup/menu/getList.do?authgroupOid="+vm.authgroup.authgroupOid+"&menuGbCd="+ vm.authgroup.authgroupGbCd,
						  method: "GET",
						  dataType: "json",
						  data: vm.authgroup
					  });
					
					request.done(function(data){ 
						vm.authMenuList = data[0].childrens; 
						vm.circuitNode(vm.authMenuList, function(children, level){
							children.level = level;
						});
						vm.loading = false;
					});
				},
				setRowStyle: function(o){
					if(o.row.level == 0){
						return "background-color: #efefef";
					}else if(o.row.level == 1){
						return "background-color: #f9f9f9;";
					}else if(o.row.level == 2){
						return "background-color: #fff;";
					}else{
						return "background-color: #fff;";
					}
				},
				onCheckboxChange: function(currentRowData, currentProperty){
					//step 1. 현재 노드를 체크했을때 상태에 따라 연관된 모든 자식 노드 체크하기
					vm.checkChildrenNode(currentRowData.childrens, currentRowData, currentProperty);
					//step 2. 현재 노드를 체크했을때 상태에 따라 연관된 모든 부모 노드 체크하기
					vm.checkParentNode(vm.authMenuList, currentRowData, currentProperty);
				},
				/*
					 트리 순회하면서 찾은 자식 노드 정보를 콜백함수에 전달 
				*/
				circuitNode: function(tree, callback){
					var q = [{level: 0, childrens: tree}];
					while(q.length > 0){
						var data      = q.shift();
						var level     = data.level;
						var childrens = data.childrens;
						
						for(var i in childrens){
							var children = childrens[i];
							if(children.childrens && children.childrens.length > 0){
								q.push({level: level+1, childrens: children.childrens});
							}
							callback(children, level);
						}
					}
				},
				/*
					기준이 되는 노드로 자식 노드를 찾고
					기준 노드의 상태에 따라 
					자식 노드 체크
				*/
				checkChildrenNode: function(tree, currentRowData, currentProperty){
					var q = [tree];
					while(q.length > 0){
						var childrens = q.shift();
						for(var i in childrens){
							var children = childrens[i];
							if(children.childrens && children.childrens.length > 0){
								q.push(children.childrens);
							}
							children[currentProperty] = currentRowData[currentProperty];
						}
					}
				},
				/*
					기준이 되는 노드로 부모 노드를 찾고
					찾은 부모 노드의 자식 노드의 상태에 따라 
					부모 노드 체크
				*/
				checkParentNode: function(nodeList, target, property){
					var q = [target];
					while(q.length > 0){
						var targetNode = q.shift();
						var parentNode = vm.searchParentNode(nodeList, targetNode.menuParentId);
						if(parentNode){
							if(vm.hasCheckedNode(parentNode.childrens, property)){
								parentNode[property] = 'Y';
							}else{
								parentNode[property] = 'N';
							}
							q.push(parentNode);
						}
					}
				},
				/*
					전체 노드를 순회하면서 메뉴OID가 일치하는 노드 찾기
				*/
				searchParentNode: function(tree, menuOid){
					if(tree.menuOid === menuOid) return tree;
					var q = [tree];
					while(q.length > 0){
						var childrens = q.shift();
						for(var i in childrens){
							var node = childrens[i];
							if(node.menuOid === menuOid) return node;
							if(node.childrens && node.childrens.length > 0){
								q.push(node.childrens);
							}
						}
					}
				},
				/*
					자식 노드 목록의 체크여부 반환
					한건이라도 체크 되어있으면 TRUE
					한건도 체크 되어있지않으면 FALSE
				*/
				hasCheckedNode: function(childrens, property){
					for(var i in childrens){
						var children = childrens[i];
						if(children[property] == 'Y') return true;
					}
					return false;
				}
			}
		})
		vm.getAuthgroup();
	});
</script>

