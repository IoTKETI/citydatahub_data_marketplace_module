<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">업체수정</h3>
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
              <th class="icon__require">업체명</th>
              <td colspan="3">
                <input class="input__text" type="text" ref="companyNm" v-model="company.companyNm" title="업체명" required/>
              </td>
            </tr>
            <tr>
              <th class="icon__require">담당자이름</th>
              <td colspan="3">
                <input class="input__text" type="text" ref="companyUsrNm" v-model="company.companyUsrNm" title="담당자이름" required/>
              </td>
            </tr>
            <tr>
              <th>전화번호</th>
              <td colspan="3">
                <input class="input__text" type="text"  v-model="company.companyPhone"/>
              </td>
            </tr>
            <tr>
              <th>이메일</th>
              <td colspan="3">
                <input class="input__text" type="text"  v-model="company.companyEmail"/>
              </td>
            </tr>
            <tr>
              <th>홈페이지</th>
              <td colspan="3">
                <input class="input__text" type="text"  v-model="company.companyHomepage"/>
              </td>
            </tr>
            <tr>
              <th>주소</th>
              <td colspan="3">
                <input class="input__text" type="text"  v-model="company.companyAddr"/>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
    <!-- //section-default -->
    <div class="button__group">
      <button class="button__primary" type="button" @click="modifyCompany">저장</button>
	      <c:if test="${deleteYn eq 'Y'}">
	      	<button class="button__secondary" type="button" @click="deleteCompany">삭제</button>
	      </c:if>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/company/pageDetail.do?companyOid=${param.companyOid}"/>'">취소</button>
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
					company: {
						companyOid: "${param.companyOid}",
						companyNm: null,
		        		companyUsrNm: null,
		        		companyPhone: null,
		        		companyEmail:null,
		        		companyHomepage: null,
		        		companyAddr: null
					}
				}
			},
			methods: {
				getCompany: function(){
					var request = $.ajax({
						url: N2M_CTX + "/company/get.do",
						method: "GET",
						dataType: "json",
						data: vm.company
					});
					request.done(function(data){
						vm.company = data.company;
					});
				},
				deleteCompany: function(){
					if(Modal.DELETE()) return;					
					var request = $.ajax({
						url: N2M_CTX + "/company/remove.do",
						method: "DELETE",
						contentType: "application/json",
						dataType: "json",
						data: JSON.stringify(vm.company)
					});
					request.done(function(data){
						Modal.OK();
						location.href = N2M_CTX + "/company/pageList.do";
					});
				},
				modifyCompany: function(){
					Valid.init(vm, vm.company);
		        	var constraints = {};
					constraints = Valid.getConstraints();

					if(Valid.validate(constraints)) return;
					if(Modal.MODIFY()) return;
					
					var request = $.ajax({
						url: N2M_CTX + "/company/modify.do",
						method: "PUT",
						contentType: "application/json",
						dataType: "json",
						data: JSON.stringify(vm.company)
					});
					request.done(function(data){
						Modal.OK();
						location.href = N2M_CTX + "/company/pageDetail.do?companyOid=" + vm.company.companyOid;
					});
				}
			}
		})
		vm.getCompany();
	});
</script>


