<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">메뉴등록</h3>
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
	              <th class="icon__require">메뉴명</th>
	              <td colspan="3">
	                <input class="input__text" type="text" v-model="menu.menuNm" ref="menuNm" title="메뉴명" required/>
	              </td>
	            </tr>
	            <tr>
	              <th class="icon__require">메뉴구분</th>
	              <td colspan="3">
	                <c:forEach var="code" items="${n2m:getCodeList('CG_ID_PORTAL_TYPE')}">
						<label class="label__radio"> <input name="radioGroup" class="input__radio" type="radio" value="${code.codeId}" v-model="menu.menuGbCd" title="메뉴구분" >${code.codeName}</label>
					</c:forEach>
	              </td>
	            </tr>
	            <tr>
	              <th class="icon__require">상위메뉴</th>
	              <td colspan="3">
	                <input class="input__text w-with-68" type="text"  v-model="menu.menuParentNm" ref="menuParentNm" title="상위메뉴" readonly required>
	                <button class="button__outline w-68" type="button" @click="openPopup('OPEN_POPUP_MENU')">메뉴선택</button>
	              </td>
	            </tr>
	            <tr>
	              <th class="icon__require">프로그램그룹</th>
	              <td colspan="3">
	                <input class="input__text w-with-94" type="text" v-model="menu.programGrpNm" ref="programGrpNm" title="프로그램그룹" readonly required>
	                <c:set var="programPopupDisabled" value="${readYn eq 'Y'}"/>
	                <button class="button__outline w-94" type="button" @click="openPopup('OPEN_POPUP_PROGRAM')" ${programPopupDisabled}>프로그램 그룹</button>
	              </td>
	            </tr>
	            <tr>
	              <th class="icon__require">메뉴순서</th>
	              <td colspan="3">
	                <input class="input__number" type="number" v-model="menu.menuOrd" ref="menuOrd" title="메뉴순서" required/>
	                <span class="input__number-group">
	                  <button class="input__number-button button__number--up" type="button" title="증가" @click="increaseOrder(menu.menuOrd)"><span class="hidden">증가</span></button>
	                  <button class="input__number-button button__number--down" type="button" title="감소" @click="decreaseOrder(menu.menuOrd)"><span class="hidden">감소</span></button>
	                </span>
	              </td>
	            </tr>
	            <tr>
	              <th>URL</th>
	              <td colspan="3">
	                <input class="input__text" type="text" v-model="menu.menuUrl" ref="menuUrl"/>
	              </td>
	            </tr>
	            <tr>
	              <th>파라미터</th>
	              <td>
	                <input class="input__text" type="text" v-model="menu.menuParam" ref="menuParam">
	              </td>
	              <td colspan="2"><span class="text__notice">※key1 = value &amp; key1 = value &amp; 형식으로표현</span></td>
	            </tr>
	            <tr>
	              <th class="icon__require">메뉴표시여부</th>
	              <td colspan="3">
	                  <input class="input__checkbox" id="check1" type="checkbox" v-model="menu.menuUseFl" ref="menuUseFl" true-value="Y" false-value="N"><label class="label__checkbox" for="check1">표시</label>
	              </td>
	            </tr>
	          </tbody>
	        </table>
	      </div>
	    </section>
	    <!-- //section-default -->
	    <div class="button__group">
	      <button class="button__primary" type="button" @click="createMenu">저장</button>
	      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/menu/pageList.do"/>'">취소</button>
	    </div>
	  </fieldset>
	</form>
	
	<!-- 코드 등록 팝업 -->
	<jsp:include page="/WEB-INF/jsp/menu/popup/menuSearchPopup.jsp"/>
	<jsp:include page="/WEB-INF/jsp/menu/popup/programSearchPopup.jsp"/>
</div>

<script>
$(function(){
	Vue.use(N2MPagination);
	vm = new Vue({
		el: '#content',
		mixins: [menuSearchPopupMixin, programSearchPopupMixin],
		data: function(){
			return {
				menu : {
					menuId        : "",
					menuNm        : "",
					menuUrl       : "",
					menuOrd       : "0",
					menuParentId  : "",
					menuParentNm  : "",
					menuGbCd      : "adminPortal",
					menuParam     : "",
					menuUseFl     : "N",
					programGrpOid : "",
					programGrpNm : ""
				}
			}
		},
		methods: {
			increaseOrder: function(num){
				vm.menu.menuOrd = ++num;
			},
			decreaseOrder: function(num){
				vm.menu.menuOrd = num == 0 ? 0 : --num; 
			},
			createMenu: function(){
				Valid.init(vm, vm.menu);
				
				var constraints = {};
				constraints = Valid.getConstraints();

				if(Valid.validate(constraints)) return;
				if(Modal.REGIST()) return;
				
				var request = $.ajax({
					method: "POST",
					url: N2M_CTX + "/menu/create.do",
					contentType: "application/json",
					data: JSON.stringify(vm.menu),
					dataType: "json"
				});
				request.done(function(data){
					Modal.OK();
					location.href = N2M_CTX + "/menu/pageList.do";
				});
			},
			openPopup: function(type){
				switch(type){
				case 'OPEN_POPUP_MENU':
					vm.openMenuSearchPopup(function(selectedMenu){
						vm.menu.menuParentId = selectedMenu.id;
						vm.menu.menuParentNm = selectedMenu.text;
					});
					break;
				case 'OPEN_POPUP_PROGRAM':
					vm.openProgramSearchPopup(function(selectedProgram){
						vm.menu.programGrpOid = selectedProgram.id;
						vm.menu.programGrpNm  = selectedProgram.text;
					});
					break;
				}
			}
		}
	});
});
</script>