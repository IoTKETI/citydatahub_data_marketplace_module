<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">업체 상세</h3>
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
              <th>업체명</th>
              <td colspan="3">
                {{company.companyNm}}
              </td>
            </tr>
            <tr>
              <th>담당자이름</th>
              <td colspan="3">
                {{company.companyUsrNm}}
              </td>
            </tr>
            <tr>
              <th>전화번호</th>
              <td colspan="3">
               {{company.companyPhone}}
              </td>
            </tr>
            <tr>
              <th>이메일</th>
              <td colspan="3">
                {{company.companyEmail}}
              </td>
            </tr>
            <tr>
              <th>홈페이지</th>
              <td colspan="3">
                {{company.companyHomepage}}
              </td>
            </tr>
            <tr>
              <th>주소</th>
              <td colspan="3">
                {{company.companyAddr}}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
    <!-- //section-default -->
    <div class="button__group">
      <c:if test="${modifiedYn eq 'Y'}">
      	<button class="button__primary" type="button"  onclick="location.href='<c:url value="/company/pageModify.do?companyOid=${param.companyOid}"/>'">수정</button>
      </c:if>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/company/pageList.do"/>'">목록</button>
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
						companyOid: "${param.companyOid}"
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
				}
			}
		})
		vm.getCompany();
	});
</script>



