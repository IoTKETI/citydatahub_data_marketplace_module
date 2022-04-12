<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:set var="popup_mode" value="${( modifiedYn eq 'Y' or writeYn eq 'Y' ) ? '' : 'disabled'}"/>
<div class="modal" :style="codeRegistPopupVisible ? 'display:block;':'display:none;'">
	<div class="modal__wrap">
		<div class="modal__content w-650">
			<div class="modal__header">
				<h4 class="modal__title">코드 등록/수정 팝업</h4>
				<button class="modal__button--close" type="button" @click="closeCodeRegistPopup"><span class="hidden">모달 닫기</span></button>
			</div>
			<div class="modal__body">
				<div class="section">
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
									<th class="icon__require">코드ID</th>
									<td colspan="3">
										<input class="input__text" type="text" v-model="codeInfo.codeId" ref="codeId" :class="{'w-with-68' : !codeRegistPopupEdit}" :readonly="codeRegistPopupEdit" title="코드ID" @change="changeCodeId" required/>
										<button class="button__outline w-68" type="button" @click="checkCodeId" v-show="!codeRegistPopupEdit">중복확인</button>
									</td>
								</tr>
								<tr>
									<th class="icon__require">코드명</th>
									<td colspan="3">
										<input class="input__text" type="text" v-model="codeInfo.codeName" ref="codeName" title="코드명" ${popup_mode} required />
									</td>
								</tr>
								<tr>
									<th class="icon__require">순서</th>
									<td colspan="3">
										<input class="input__text" type="text" v-model="codeInfo.codeSort" ref="codeSort" title="순서" ${popup_mode} required />
									</td>
								</tr>
								<tr>
									<th class="icon__require">사용여부</th>
									<td colspan="3">
										<input class="input__checkbox" id="checkCodeUseFl" type="checkbox" ref="codeUseFl" v-model="codeInfo.codeUseFl" true-value="Y" false-value="N" title="사용여부" ${popup_mode} required/><label class="label__checkbox" for="checkCodeUseFl"></label>
									</td>
								</tr>
								<tr>
									<th class="icon__require">설명</th>
									<td colspan="3">
										<textarea rows="30" cols="100" style="height: 225px;"v-model="codeInfo.codeDesc" ref="codeDesc" title="설명" ${popup_mode} required></textarea>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal__footer">
				<c:if test="${writeYn eq 'Y'}">
					<button class="button__primary" type="button" @click="createCode" v-if="!codeRegistPopupEdit">저장</button>
				</c:if>
				<c:if test="${modifiedYn eq 'Y'}">
					<button class="button__primary" type="button" @click="modifyCode" v-if="codeRegistPopupEdit">저장</button>
				</c:if>
				<button class="button__secondary" type="button" @click="closeCodeRegistPopup">닫기</button>
			</div>
		</div>
	</div>
</div>
<script>
var codeRegistPopupMixin={
	data: function(){
		return {
			codeInfo : {
				codeGroupId: "${param.codeGroupId}",
				codeId: "",
				codeSort: "0",
				codeName: "",
				codeDesc: "",
				codeId: "",
				codeUseFl: "Y"
			},
			checkedCodeId: false,
			codeListCallback: function(){},
			codeRegistPopupEdit: false,
			codeRegistPopupVisible : false
		}
	},
	methods: {
		checkCodeId: function(){
			if(!vm.codeInfo.codeId){
				alert("[코드ID] 은(는) 필수 입력 값 입니다.");
				vm.$refs.codeId.focus();
				return;
			}
			var request = $.ajax({
				 method: "GET",
				 url: N2M_CTX + "/codegroup/code/get.do",
				 data: vm.codeInfo,
// 				 dataType: "json"
			 });
			request.done(function(data){
				console.log(data);
				if(!data){
					alert("사용 가능한 아이디 입니다.");
					vm.checkedCodeId = true;
					vm.$refs.codeName.focus();
				}else{
					alert("이미 사용중인 아이디 입니다.");
					vm.checkedCodeId = false;
					vm.codeInfo.codeId = "";
					vm.$refs.codeId.focus();
				}
			});
		},
		createCode: function(){
			Valid.init(vm, vm.codeInfo);
			
			var constraints = {};
			constraints = Valid.getConstraints();
			if(Valid.validate(constraints)) return;
			if(!vm.checkedCodeId){
				alert("아이디 중복 확인이 필요합니다.");
				return;
			}
			if(Modal.REGIST()) return;
			var request = $.ajax({
				url: N2M_CTX + "/codegroup/code/create.do",
				method: "POST",
				contentType: "application/json",
// 				dataType: "json",
				data: JSON.stringify(vm.codeInfo)
			});
			request.done(function(data){
				vm.codeListCallback();
				vm.closeCodeRegistPopup();
				vm.getCodeGroup();
			});
		},
		modifyCode: function(){
			Valid.init(vm, vm.codeInfo);
			
			var constraints = {};
			constraints = Valid.getConstraints();
			
			if(Valid.validate(constraints)) return;
			if(Modal.MODIFY()) return;
			
			var request = $.ajax({
				url: N2M_CTX + "/codegroup/code/modify.do",
				method: "PUT",
				contentType: "application/json",
// 				dataType: "json",
				data: JSON.stringify(vm.codeInfo)
			});
			request.done(function(data){
				vm.codeListCallback();
				vm.closeCodeRegistPopup();
			});
		},
		openCodeRegistPopup: function(codeInfo, codeGroupId, callback){
			if(codeInfo && codeInfo.codeId){
				vm.codeInfo = codeInfo;
				vm.codeRegistPopupEdit = true;
			}else{
				vm.codeRegistPopupEdit = false;
			}
			vm.codeInfo.codeGroupId = codeGroupId;
			vm.codeRegistPopupVisible = true;
			vm.codeListCallback = callback;
		},
		closeCodeRegistPopup: function(){
			vm.codeInfo = {
				codeSort: "0",
				codeName: "",
				codeDesc: "",
				codeId: "",
				codeUseFl: "Y"
			}
			vm.codeRegistPopupEdit = false;
			vm.codeRegistPopupVisible = false;
			vm.checkedCodeId = false;
		},
		changeCodeId: function() {
			vm.checkedCodeId = false;
		}
	}
}
</script>