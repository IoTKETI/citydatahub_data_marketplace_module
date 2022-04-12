<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">코드그룹수정</h3>
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
				  <th class="icon__require">코드그룹명</th>
				  <td colspan="3">
					<input class="input__text" type="text" v-model="codeGroup.codeGroupName" ref="codeGroupName" title="코드그룹명" required />
				  </td>
				</tr>
				<tr>
				  <th class="icon__require">순서</th>
				  <td colspan="3">
					<input class="input__text" type="number"  v-model="codeGroup.codeGroupCol" ref="codeGroupCol" title="순서" required/>
				  </td>
				</tr>
				<tr>
				  <th class="icon__require">사용여부</th>
				  <td colspan="3">					
					<input class="input__checkbox" id="checkCodeGroupUseFl" type="checkbox" ref="codeGroupUseFl" v-model="codeGroup.codeGroupUseFl" true-value="Y" false-value="N" title="사용여부" required/><label class="label__checkbox" for="checkCodeGroupUseFl"></label>
				  </td>
				</tr>
				<tr>
				  <th class="icon__require">설명</th>
				  <td colspan="3">
					<textarea rows="30" cols="100" v-model="codeGroup.codeGroupDesc" ref="codeGroupDesc" title="설명" required></textarea>
				  </td>
				</tr>
			  </tbody>
			</table>
		  </div>
		</section>
		<!-- //section-default -->
		<div class="button__group">
			<button class="button__primary" type="button" @click="modifyCodeGroup">저장</button>
			<button class="button__secondary" type="button" onclick="location.href='<c:url value="/codegroup/pageDetail.do?codeGroupId=${param.codeGroupId}"/>'">취소</button>
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
					codeGroupId	    : "${param.codeGroupId}",
					codeGroupName   : "",
					codeGroupUseFl  : "Y",
					codeGroupCol    : "0",
					codeGroupDesc   : "",
				}
			}
		},
		methods: {
			
			getCodeGroup: function(){
				var request = $.ajax({
					url: N2M_CTX + "/codegroup/get.do",
					method: "GET",
					dataType: "json",
					data: vm.codeGroup
				});
				request.done(function(data){
					vm.codeGroup = data;
				});
			},
			modifyCodeGroup: function(){
				Valid.init(vm, vm.codeGroup);
				
				var constraints = {};
				constraints = Valid.getConstraints();

				if(Valid.validate(constraints)) return;
				if(Modal.MODIFY()) return;
				
				var request = $.ajax({
					method: "PUT",
					url: N2M_CTX + "/codegroup/modify.do",
					contentType: "application/json",
					data: JSON.stringify(vm.codeGroup),
// 					dataType: "json"
				});
				request.done(function(data){
					location.href = N2M_CTX + "/codegroup/pageList.do";
				});
				request.fail(function(data){
					console.log("Modifail");
				});
			},
		}
	});

	vm.getCodeGroup();
});
</script>
