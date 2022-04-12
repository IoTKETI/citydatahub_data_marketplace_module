<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">권한 등록</h3>
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
                 <label class="label__radio"><input name="radioGroup" class="input__radio" id="user" value="user" type="radio" v-model="authgroupGbCd" title="구분">사용자포탈</label>
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
      <button class="button__primary" type="button"  @click="createAuthgroup">저장</button>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/authgroup/pageList.do"/>'">취소</button>
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
        		authgroupNm:"",
	        	authgroupGbCd:"",
	        	authgroupGbCdNm:"",
	        	authgroupUseFl:"Y",
        		authgroupDesc: ""
          },
          authgroupGbCd : null,
          authgroupUseFl :null
        }
      },
      methods: {
        createAuthgroup: function(){
        	if(vm.authgroupUseFl){
        		vm.authgroup.authgroupUseFl = "Y"
        	}
        	else{
        		vm.authgroup.authgroupUseFl = "N"
        	}
      
        	if(this.authgroupGbCd == 'admin'){
        		vm.authgroup.authgroupGbCd = "adminPortal"
        	}
        	else if(this.authgroupGbCd == 'user'){
        		vm.authgroup.authgroupGbCd = "normalPortal"
        	}
        	
			Valid.init(vm, vm.authgroup);
			
			var constraints = {};
			constraints = Valid.getConstraints();

			if(Valid.validate(constraints)) return;
			if(Modal.REGIST()) return;
			
	          var request = $.ajax({
	            method: "POST",
	            url: N2M_CTX + "/authgroup/create.do",
	            contentType: "application/json",
	            data: JSON.stringify(vm.authgroup),
	            dataType: "json"
	          });
	          request.done(function(data){
	        	  Modal.OK();
	        	  location.href = N2M_CTX + "/authgroup/pageList.do";
	          });
        }
      }
    });
  });
</script>