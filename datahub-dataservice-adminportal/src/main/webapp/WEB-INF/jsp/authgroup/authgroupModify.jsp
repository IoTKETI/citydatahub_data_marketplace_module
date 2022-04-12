<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">권한수정</h3>
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
          </colgroup>
          <tbody>
            <tr>
              <th class="icon__require">그룹명</th>
              <td colspan="3">
               <input class="input__text" type="text" ref="authgroupNm" v-model="authgroup.authgroupNm" title="그룹명" required/>
              </td>
            </tr>
            <tr>
              <th>설명</th>
              <td colspan="3">
                <textarea class="textarea" v-model="authgroup.authgroupDesc" title="설명"></textarea>
              </td>
            </tr>
            <tr>
              <th class="icon__require">구분</th>
              <td colspan="3">
             	 <label class="label__radio"><input name="radioGroup" class="input__radio"  ref="authgroupGbCd" id="admin" value="admin" type="radio" v-model="authgroupGbCd" title="구분" required>관리자포탈</label>
                 <label class="label__radio"><input name="radioGroup" class="input__radio" id="authgroup" value="user" type="radio" v-model="authgroupGbCd" title="구분">사용자포탈</label>
              </td>
            </tr>
            <tr>
              <th class="icon__require">사용여부</th>
              <td colspan="3">
              	<input class="input__checkbox" type="checkbox" id="check1" v-model="authgroupUseFl" ref="authgroupUseFl" ><label class="label__checkbox" for="check1">사용</label>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
    <!-- //section-default -->
    <div class="button__group">
      <button class="button__primary" type="button" @click="modifyAuthgroup">저장</button>
      <c:if test="${deleteYn eq 'Y'}">
            <button class="button__secondary" type="button" @click="deleteAuthgroup">삭제</button>
      </c:if>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/authgroup/pageDetail.do?authgroupOid=${param.authgroupOid}"/>'">취소</button>
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
        	authgroup : {
	        	authgroupOid: "${param.authgroupOid}",
	        	authgroupNm:"",
	        	authgroupGbCd:"",
	        	authgroupGbCdNm:"",
	        	authgroupUseFl:"Y"
	        	
			},
          authgroupGbCd : null,
          authgroupUseFl :null
        }
      },
      methods: {
        getAuthgroup: function(){
			var request = $.ajax({
				url: N2M_CTX + "/authgroup/get.do",
				method: "GET",
				dataType: "json",
				data: vm.authgroup
			});
			request.done(function(data){
				vm.authgroup = data;
				
				if(data.authgroup.authgroupGbCd == 'adminPortal'){
	        		vm.authgroupGbCd = "admin";
	        	}
				else if(data.authgroup.authgroupGbCd == 'normalPortal'){
	        		vm.authgroupGbCd = "user";
	        	}
				if(data.authgroup.authgroupUseFl == "Y"){
					vm.authgroupUseFl = true
	        	}
	        	else if(data.authgroup.authgroupUseFl == "N"){
	        		vm.authgroupUseFl = false
	        	}
			});
		},
		deleteAuthgroup: function(){
			if(Modal.DELETE()) return;
			
			var request = $.ajax({
				url: N2M_CTX + "/authgroup/remove.do",
				method: "DELETE",
				contentType: "application/json",
				data: JSON.stringify(vm.authgroup)
			});
			request.done(function(data){
				Modal.OK();
				location.href = N2M_CTX + "/authgroup/pageList.do";
			});
		},
		modifyAuthgroup: function(){
			//코드 처리
			if(vm.authgroupUseFl){
				vm.authgroup.authgroupUseFl = "Y"
        	}
        	else if(vm.authgroupUseFl == false){
        		vm.authgroup.authgroupUseFl = "N"
        	}
      
        	if(vm.authgroupGbCd == 'admin'){
        		vm.authgroup.authgroupGbCd = "adminPortal"
        	}
        	else if(vm.authgroupGbCd == 'user'){
        		vm.authgroup.authgroupGbCd = "normalPortal"
        	}
        	
        	Valid.init(vm, vm.authgroup);
			
			var constraints = {};
			constraints = Valid.getConstraints();

			if(Valid.validate(constraints)) return;
			if(Modal.MODIFY()) return;
				
			var request = $.ajax({
				url: N2M_CTX + "/authgroup/modify.do",
				method: "PUT",
				contentType: "application/json",
				data: JSON.stringify(vm.authgroup)
			});
			request.done(function(data){
				Modal.OK();
				location.href = N2M_CTX + "/authgroup/pageDetail.do?authgroupOid="+ vm.authgroup.authgroupOid;
			});
		}
	}
})
vm.getAuthgroup();
   
  });
</script>

