<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">코드그룹등록</h3>
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
								<th class="icon__require">코드그룹ID</th>
								<td colspan="3">
									<input class="input__text w-with-68" type="text" ref="codeGroupId" v-model="codeGroup.codeGroupId" @change="changeCodeGroupId" title="코드그룹ID" required/>
									<button class="button__outline w-68" type="button" @click="checkCodeGroupId">중복확인</button>
								</td>
							</tr>
							<tr>
								<th class="icon__require">코드그룹명</th>
								<td colspan="3">
									<input class="input__text" type="text" ref="codeGroupName" v-model="codeGroup.codeGroupName" title="코드그룹명" required/>
								</td>
							</tr>
							<tr>
								<th class="icon__require">순서</th>
								<td colspan="3">
									<input class="input__text" type="text" ref="codeGroupCol" v-model="codeGroup.codeGroupCol" title="순서" required/>
								</td>
							</tr>
							<tr>
								<th class="icon__require">사용여부</th>
								<td colspan="3">
									<input class="input__checkbox" id="checkUseFl" type="checkbox" ref="codeGroupUseFl" v-model="codeGroup.codeGroupUseFl" true-value="Y" false-value="N" title="사용여부" required/><label class="label__checkbox" for="checkUseFl"></label>
								</td>
							</tr>
							<tr>
								<th class="icon__require">설명</th>
								<td colspan="3">
									<textarea rows="30" cols="100" ref="codeGroupDesc" v-model="codeGroup.codeGroupDesc" title="설명" required></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</section>
			<!-- //section-default -->
			<div class="button__group">
				<button class="button__primary" type="button" @click="createCodeGroup">저장</button>
				<button class="button__secondary" type="button" onclick="location.href='<c:url value="/codegroup/pageList.do"/>'">취소</button>
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
				codeGroup : {
					codeGroupId       : "",
					codeGroupName     : "",
					codeGroupUseFl    : "Y",
					codeGroupCol      : "0",
					codeGroupDesc     : "",
				},
				checkedCodeGroupId: false
			}
		},
		methods: {
			checkCodeGroupId: function(){
				if(!vm.codeGroup.codeGroupId){
					alert("[코드그룹ID] 은(는) 필수 입력 값 입니다.");
					vm.$refs.codeGroupId.focus();
					return;
				}
				var request = $.ajax({
					 method: "GET",
					 url: N2M_CTX + "/codegroup/get.do",
					 data: vm.codeGroup,
// 					 dataType: "json"
				 });
				request.done(function(data){
					if(!data){
						alert("사용 가능한 아이디 입니다.");
						vm.checkedCodeGroupId = true;
						vm.$refs.codeGroupName.focus();
					}else{
						alert("이미 사용중인 아이디 입니다.");
						vm.checkedCodeGroupId = false;
						vm.codeGroup.codeGroupId = "";
						vm.$refs.codeGroupId.focus();
					}
				});
				request.fail(function(data){
				});
			},
			createCodeGroup: function(){
				Valid.init(vm, vm.codeGroup);
				
				var constraints = {};
				constraints = Valid.getConstraints();
 				constraints.codeGroupCol.format   = {message: MSG_VALID_ONLY_NUMBER, pattern: '[0-9]+'};	//순서

				if(Valid.validate(constraints)) return;
				if(!vm.checkedCodeGroupId){
					alert("아이디 중복 확인이 필요합니다.");
					return;
				}
				if(Modal.REGIST()) return;
				
				var request = $.ajax({
					method: "POST",
					url: N2M_CTX + "/codegroup/create.do",
					contentType: "application/json",
					data: JSON.stringify(vm.codeGroup),
				});
				request.done(function(data){
					alert("저장되었습니다.");
					location.href = N2M_CTX + "/codegroup/pageList.do";
				});
				request.fail(function(data){
					console.log(data);
				});
			},
			changeCodeGroupId: function(val) {
				vm.checkedCodeGroupId = false;
			}
		}
	});
});
</script>