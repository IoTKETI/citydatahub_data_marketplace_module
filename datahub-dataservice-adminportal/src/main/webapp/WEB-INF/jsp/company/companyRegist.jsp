<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">업체 등록</h3>
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
                <input class="input__text" type="text" ref="companyNm" v-model="company.companyNm" ref="companyNm" title="업체명" required/>
              </td>
            </tr>
            <tr>
              <th class="icon__require">담당자이름</th>
              <td colspan="3">
                <input class="input__text" type="text" ref="companyUsrNm" v-model="company.companyUsrNm" ref="companyUsrNm" title="담당자이름" required/>
              </td>
            </tr>
            <tr>
              <th>전화번호</th>
              <td colspan="3">
                <input class="input__text" type="text"  v-model="company.companyPhone" ref="companyPhone"/>
              </td>
            </tr>
            <tr>
              <th>이메일</th>
              <td colspan="3">
                <input class="input__text" type="text"  v-model="company.companyEmail" ref="companyEmail"/>
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
      <button class="button__primary" type="button" @click="createCompany">저장</button>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/company/pageList.do"/>'">취소</button>
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
        	company : {
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
        createCompany: function(){
        	Valid.init(vm, vm.company);
        	var constraints = {};
			constraints = Valid.getConstraints();
			
			if(Valid.validate(constraints)) return;
			if(Modal.REGIST()) return;
			
	          var request = $.ajax({
	            method: "POST",
	            url: N2M_CTX + "/company/create.do",
	            contentType: "application/json",
	            data: JSON.stringify(vm.company),
	            dataType: "json"
	          });

	          request.done(function(data){
	        	  Modal.OK();
	        	  location.href = N2M_CTX + "/company/pageList.do";
	          });
        }
      }
    });
  });
</script>
