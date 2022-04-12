<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">가격정책관리등록</h3>
	<form>
		<fieldset>
			<legend>필드셋 제목</legend>
			<!-- section-write -->
			<section class="section">
				<div class="section__content">
		            <form>
		              <fieldset>
		                <legend>필드셋 제목</legend>
		                <table class="table--row">
		                  <caption>테이블 제목</caption>
		                  <colgroup>
		                    <col style="width:150px">
		                    <col style="width:auto">
		                  </colgroup>
		                  <tbody>
		                    <tr>
		                      <th class="icon__require">제목</th>
		                      <td colspan="3"><input class="input__text" type="text" v-model="pricePolicies.title" ref="title" title="제목" required></td>
		                    </tr>
		                    <tr>
		                      <th class="icon__require">사용여부</th>
		                      <td colspan="3">
								<label class="label__radio"><input  type="radio" name="radioGroup" class="input__radio" ref="useTf" v-model="pricePolicies.useTf" title="사용여부" value="true" required>활성화</label>
		                        <label class="label__radio"><input  type="radio" name="radioGroup" class="input__radio" v-model="pricePolicies.useTf" value="false	">비활성화</label>
		                      </td>
		                    </tr>
		                    <tr>
		                      <th class="icon__require">트래픽유형</th>
		                      <td colspan="3">
								<select class="select select--full" v-model="pricePolicies.trafficType"ref="trafficType" title="트래픽유형" required>
									<option value="">선택</option>
			                        <c:forEach var="code" items="${n2m:getCodeList('CG_ID_TRAFFIC_TYPE')}">
										<option value="${code.codeId}">${code.codeName}</option>
									</c:forEach>
								</select>
							  </td>
		                    </tr>
		                    <tr>
		                      <th class="icon__require">일일제한량</th>
		                      <td colspan="3"><input class="input__text" type="text" v-model="pricePolicies.limit" ref="limit" title="일일제한량" maxlength="14" required></td>
		                    </tr>
		                    <tr>
		                      <th class="icon__require">제공기간</th>
		                      <td>
		                        <c:forEach var="code" items="${n2m:getCodeList('CG_ID_PRICE_PERIOD_CODE')}" varStatus="status">
		                          <c:if test="${status.index == 0}">
		                          <input type="checkbox" id="check${status.index}" class="input__checkbox" v-model="pricePolicies.periodCdArr" :value="${code.codeId}" ref="periodCdArr" title="제공기간" required>
		                          </c:if>
		                          <c:if test="${status.index != 0}">
		                          <input type="checkbox" id="check${status.index}" class="input__checkbox" v-model="pricePolicies.periodCdArr" :value="${code.codeId}">
		                          </c:if>
		                          <label class="label__checkbox" for="check${status.index}">${code.codeName}</label>
								</c:forEach>
		                      </td>
		                    </tr>
		                  </tbody>
		                </table>
		              </fieldset>
		            </form>
				</div>
			</section>
			<!-- //section-default -->
			<div class="button__group">
				<button class="button__primary" type="button" @click="modifyPricePolicies">저장</button>
				<button class="button__secondary" type="button" @click="deletePricePolicies">삭제</button>
				<button class="button__secondary" type="button" onclick="location.href='<c:url value="/pricePolicies/pageDetail.do?id=${param.id}"/>'">취소</button>
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
				pricePolicies: {
					title: "",
					useTf: true,
					trafficType: "",
					limit: "",
					periodCdArr: [],
					pricePoliciesPeriodList: [],
				}
			}
		},
		watch: {
			"pricePolicies.limit": function(newValue){
				if(!newValue) return;
				if(typeof newValue !== 'string') return;
				vm.pricePolicies.limit = newValue.replace(/[^0-9]/g, "");
			}
		},
		methods: {
			getPricePolicies: function(){
				var request = $.ajax({
					method: "GET",
					url: N2M_CTX + "/pricePolicies/get.do",
					contentType: "application/json",
					data: {
						policyId: "${param.id}"
					},
					dataType: "json"
				});
				request.done(function(data){
					vm.pricePolicies = data;
					vm.pricePolicies.periodCdArr = vm.pricePolicies.pricePoliciesPeriodList.map(function(period){return period.periodCd});
				});
				request.fail(function(data){
					console.log(data);
				});
			},
			modifyPricePolicies: function(){
				Valid.init(vm, vm.pricePolicies);
				
				var constraints = {};
				constraints = Valid.getConstraints();
				constraints.periodCdArr.presence.allowEmpty = false;
				
				if(Valid.validate(constraints)) return;
				
				if(Modal.MODIFY()) return;
				
				var request = $.ajax({
					method: "PATCH",
					url: N2M_CTX + "/pricePolicies/modify.do",
					contentType: "application/json",
					data: JSON.stringify(vm.pricePolicies),
				});
				request.done(function(data){
					Modal.OK();
					location.href = N2M_CTX + "/pricePolicies/pageList.do";
				});
				request.fail(function(data){
					console.log(data);
				});
			},
			deletePricePolicies: function(){
				if(Modal.DELETE()) return;
				
				var request = $.ajax({
					method: "DELETE",
					url: N2M_CTX + "/pricePolicies/remove.do",
					contentType: "application/json",
					data: JSON.stringify(vm.pricePolicies),
				});
				request.done(function(data){
					Modal.OK();
					location.href = N2M_CTX + "/pricePolicies/pageList.do";
				});
				request.fail(function(data){
					console.log(data);
				});
			},
		}
	});
	vm.getPricePolicies();
});
</script>