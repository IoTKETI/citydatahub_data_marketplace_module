<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">사용자등록</h3>
	<form>
		<fieldset>
			<legend>필드셋 제목</legend>
			<!-- section-write -->
			<section class="section">
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
								<th class="icon__require">사용자 아이디</th>
								<td colspan="3">
									<input class="input__text w-with-68" type="text" ref="userId" v-model="user.userId" title="사용자 아이디" required>
									<button class="button__outline w-68" type="button" @click="checkUser">중복확인</button>
								</td>
							</tr>
							<tr>
								<th class="icon__require">사용자구분</th>
								<td colspan="3">
									<select class="select select--full" v-model="user.userGbCd" ref="userGbCd" title="사용자구분" required>
										<option value="">--선택--</option>
										<c:forEach var="code" items="${n2m:getCodeList('CG_ID_PORTAL_TYPE')}">
											<option value="${code.codeId}">${code.codeName}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th class="icon__require">사용자 비밀번호</th>
								<td colspan="3">
									<input class="input__text" type="password"	ref="userPw" v-model="user.userPw" title="사용자 비밀번호" required/>
								</td>
							</tr>
							<tr>
								<th class="icon__require">사용자 비밀번호(확인)</th>
								<td colspan="3">
									<input class="input__text" type="password" ref="userRePw" v-model="user.userRePw" title="사용자 비밀번호" required/>
								</td>
							</tr>
							<tr>
								<th class="icon__require">이름</th>
								<td colspan="3">
									<input class="input__text" type="text" ref="userNm" v-model="user.userNm" title="이름" required/>
								</td>
							</tr>
							<tr>
								<th class="icon__require">이메일</th>
								<td colspan="3">
									<input class="input__text" type="text" ref="userEmail" v-model="user.userEmail" title="이메일" required/>
								</td>
							</tr>
							<tr>
								<th class="icon__require">전화번호</th>
								<td colspan="3">
									<input class="input__text" type="text" ref="userPhone" v-model="user.userPhone" title="전화번호" required/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</section>
			<!-- //section-default -->
			<div class="button__group">
				<button class="button__primary" type="button" @click="createUser">저장</button>
				<button class="button__secondary" type="button" onclick="location.href='<c:url value="/user/pageList.do"/>'">취소</button>
			</div>
		</fieldset>
	</form>
</div>

<script>
$(function(){
	vm = new Vue({
		el: '#content',
		data: function(){
			return {
				user : {
					userId: "",
					userPw: "",
					userRePw: "",
					userNm: "",
					userEmail: "",
					userPhone: "",
					userGbCd: ""
				},
				checkedId: false
			}
		},
		methods: {
			checkUser: function(){
				if(!vm.user.userId){
					alert("[사용자 아이디] 은(는) 필수 입력 값 입니다.");
					vm.$refs.userId.focus();
					return;
				}
				var request = $.ajax({
					 method: "GET",
					 url: N2M_CTX + "/user/check.do",
					 data: vm.user,
					 dataType: "json"
				 });
				request.done(function(data){
					if(!data.duplicated){
						alert("사용 가능한 아이디 입니다.");
						vm.checkedId = true;
						vm.$refs.userPw.focus();
					}else{
						alert("이미 사용중인 아이디 입니다.");
						vm.checkedId = false;
						vm.user.userId = "";
						vm.$refs.userId.focus();
					}
				});
				request.fail(function(data){
				});
			},
			createUser: function(){
				Valid.init(vm, vm.user);
				
				var constraints = {};
				constraints = Valid.getConstraints();
				constraints.userEmail.email   = {message: MSG_VALID_INVALID};	//이메일
				constraints.userPhone.format  = {message: MSG_VALID_INVALID, pattern: '[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}'};	//전화번호
				
				if(Valid.validate(constraints)) return;

				if(!vm.checkedId){
					alert("아이디 중복 확인이 필요합니다.");
					return;
				}else if(vm.user.userPw != vm.user.userRePw){
					alert("입력한 비밀번호가 일치하지 않습니다.");
					vm.$refs.userRePw.focus();
					return;
				}
				
				if(Modal.REGIST()) return;
				
				
				var request = $.ajax({
					method: "POST",
					url: N2M_CTX + "/user/create.do",
					contentType: "application/json",
					data: JSON.stringify(vm.user),
					dataType: "json"
				});
				request.done(function(data){
					Modal.OK();
					location.href = N2M_CTX + "/user/pageList.do";
				});
			}
		}
	});
});
</script>
