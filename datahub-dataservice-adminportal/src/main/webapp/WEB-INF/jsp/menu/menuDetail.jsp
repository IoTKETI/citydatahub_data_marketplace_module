<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">메뉴상세</h3>
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
	                {{menu.menuNm}}
	              </td>
	            </tr>
	            <tr>
	              <th class="icon__require">메뉴구분</th>
	              <td colspan="3">
	                <c:forEach var="code" items="${n2m:getCodeList('CG_ID_PORTAL_TYPE')}">
						<label class="label__radio"> <input name="radioGroup" class="input__radio" type="radio" value="${code.codeId}" v-model="menu.menuGbCd" disabled>${code.codeName}</label>
					</c:forEach>
	              </td>
	            </tr>
	            <tr>
	              <th class="icon__require">상위메뉴</th>
	              <td colspan="3">
	                {{menu.menuParentNm}}
	              </td>
	            </tr>
	            <tr>
	              <th class="icon__require">프로그램그룹</th>
	              <td colspan="3">
	                {{menu.programGrpNm}}
	              </td>
	            </tr>
	            <tr>
	              <th class="icon__require">메뉴순서</th>
	              <td colspan="3">
	                {{menu.menuOrd}}
	              </td>
	            </tr>
	            <tr>
	              <th>URL</th>
	              <td colspan="3">
	                {{menu.menuUrl}}
	              </td>
	            </tr>
	            <tr>
	              <th>파라미터</th>
	              <td>
	                {{menu.menuParam}}
	              </td>
	            </tr>
	            <tr>
	              <th class="icon__require">메뉴표시여부</th>
	              <td colspan="3">
	                  <input class="input__checkbox" id="check1" type="checkbox" v-model="menu.menuUseFl" ref="menuUseFl" true-value="Y" false-value="N" disabled><label class="label__checkbox" for="check1">표시</label>
	              </td>
	            </tr>
	          </tbody>
	        </table>
	      </div>
	    </section>
	    <!-- //section-default -->
	    <div class="button__group">
			<c:if test="${modifiedYn eq 'Y'}">
				<button class="button__primary" type="button"  onclick="location.href='<c:url value="/menu/pageModify.do?menuOid=${param.menuOid}"/>'">수정</button>
			</c:if>
			<button class="button__secondary" type="button" onclick="location.href='<c:url value="/menu/pageList.do"/>'">목록</button>
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
				menu : {},
			}
		},
		methods: {
			getMenu: function(){
				var request = $.ajax({
					method: "GET",
					url: N2M_CTX + "/menu/get.do",
					data: {
						menuOid: "${param.menuOid}"
					},
					dataType: "json"
				});
				request.done(function(data){
					vm.menu = data;
				});
			}
		}
	});
	vm.getMenu();
});
</script>