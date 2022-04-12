<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">사용자수정</h3>
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
	              <th>사용자 아이디</th>
	              <td colspan="3">
	                <input class="input__text w-with-68" type="text" readonly ref="userId" v-model="user.userId"/>
	              </td>
	            </tr>
	            <tr>
	              <th class="icon__require">이름</th>
	              <td colspan="3">
	                <input class="input__text" type="text" ref="userNm" v-model="user.name" title="이름" required readonly/>
	              </td>
	            </tr>
	            <tr>
	              <th class="icon__require">이메일</th>
	              <td colspan="3">
	                <input class="input__text" type="text" ref="userEmail" v-model="user.email" title="이메일" required readonly/>
	              </td>
	            </tr>
	            <tr>
	              <th class="icon__require">전화번호</th>
	              <td colspan="3">
	                <input class="input__text" type="text" ref="userPhone" v-model="user.phone" title="전화번호" required readonly/>
	              </td>
	            </tr>
                <tr>
                    <th>관리자 유무</th>
                    <td colspan="3">
                        <input class="input__checkbox" id="checkboxFirstFl" type="checkbox" v-model="user.userGbCd" true-value="Y" false-value="N"/>
                        <label class="label__checkbox" for="checkboxFirstFl"></label>
                    </td>
                </tr>
	          </tbody>
	        </table>
	      </div>
	    </section>
	    <!-- //section-default -->
	    <div class="button__group">
	      <button class="button__primary" type="button" @click="modifyUser">저장</button>
<%-- 	      <c:if test="${deleteYn eq 'Y'}"> --%>
<!-- 	      	<button class="button__secondary" type="button" @click="removeUser">삭제</button> -->
<%-- 	      </c:if> --%>
	      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/user/pageDetail.do?userId=${param.userId}"/>'">취소</button>
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
					user: {
						userId: "${param.userId}"
					}
				}
			},
			methods: {
				getUser: function(){
					var request = $.ajax({
						url: N2M_CTX + "/user/get.do",
						method: "GET",
						dataType: "json",
						data: vm.user
					});
					request.done(function(data){
						vm.user = data;
					});
				},
				removeUser: function(){
					if(Modal.DELETE()) return;
					
					var request = $.ajax({
						url: N2M_CTX + "/user/remove.do",
						method: "DELETE",
						contentType: "application/json",
						dataType: "json",
						data: JSON.stringify(vm.user)
					});
					request.done(function(data){
						Modal.OK();
						location.href = N2M_CTX + "/user/pageList.do";
					});
				},
				modifyUser: function(){
					var request = $.ajax({
						url: N2M_CTX + "/user/modify.do",
						method: "PUT",
						contentType: "application/json",
						dataType: "json",
						data: JSON.stringify(vm.user)
					});
					request.done(function(data){
						location.href = N2M_CTX + "/user/pageDetail.do?userId=" + vm.user.userId;
					});
				}
			}
		})
		vm.getUser();
	});
</script>
