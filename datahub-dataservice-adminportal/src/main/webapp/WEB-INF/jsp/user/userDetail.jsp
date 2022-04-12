<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">사용자상세</h3>
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
	              	{{user.userId}}
	              </td>
	            </tr>
	            <tr>
	              <th>이름</th>
	              <td colspan="3">
	              	{{user.name}}
	              </td>
	            </tr>
	            <tr>
                  <th>닉네임</th>
                  <td colspan="3">
                    {{user.nickname}}
                  </td>
                </tr>
	            <tr>
	              <th>이메일</th>
	              <td colspan="3">
	              	{{user.email}}
	              </td>
	            </tr>
	            <tr>
	              <th>전화번호</th>
	              <td colspan="3">
	              	{{user.phone}}
	              </td>
	            </tr>
	            <tr>
                    <th>관리자 유무</th>
                    <td colspan="3">
                        <input class="input__checkbox" id="checkboxFirstFl" type="checkbox" v-model="user.userGbCd" true-value="Y" false-value="N" disabled/>
                        <label class="label__checkbox" for="checkboxFirstFl"></label>
                    </td>
                </tr>
	          </tbody>
	        </table>
	      </div>
	    </section>
	    <!-- //section-default -->
	    <div class="button__group">
			<c:if test="${modifiedYn eq 'Y'}">
				<button class="button__primary" type="button"  onclick="location.href='<c:url value="/user/pageModify.do?userId=${param.userId}"/>'">수정</button>
			</c:if>
			<button class="button__secondary" type="button" onclick="location.href='<c:url value="/user/pageList.do"/>'">목록</button>
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
				}
			}
		})
		vm.getUser();
	});
</script>
