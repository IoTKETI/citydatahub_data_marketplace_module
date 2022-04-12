<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">프로그램 그룹 수정</h3>
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
                                <th>번호</th>
                                <td colspan="3">
                                    {{programGroup.programgroupId}}
                                </td>
                            </tr>
							<tr>
								<th>등록자</th>
								<td colspan="3">
									${sessionScope.user.userNm}
								</td>
							</tr>
							<tr>
								<th class="icon__require">프로그램 그룹 명</th>
								<td colspan="3">
									<input class="input__text" title="프로그램 그룹 명" type="text" v-model="programGroup.programgroupNm" ref="programgroupNm" required>
								</td>
							</tr>
							<tr>
								<th class="icon__require">프로그램 그룹 설명</th>
								<td colspan="3">
									<textarea title="프로그램 그룹 설명" rows="5" cols="168" v-model="programGroup.programgroupDesc" ref="programgroupDesc" required></textarea>
								</td>
							</tr>
                            <tr>
                                <th>사용여부</th>
                                <td colspan="3">
                                    <input class="input__checkbox" id="checkboxProgramGroupUseFl" title="사용여부" type="checkbox" v-model="programGroup.programgroupUseFl" true-value="Y" false-value="N"/>
                                    <label class="label__checkbox" for="checkboxProgramGroupUseFl"></label>
                                </td>
                            </tr>
						</tbody>
					</table>
				</div>
			</section>
			<!-- //section-default -->
			<div class="button__group">
			    <c:if test="${modifiedYn eq 'Y'}">
				    <button class="button__primary" type="button" @click="modifyProgramGroup">저장</button>
				</c:if>
<%-- 				<c:if test="${deleteYn eq 'Y'}"> --%>
<!-- 				    <button class="button__secondary" type="button" @click="deleteProgramGroup">삭제</button> -->
<%-- 				</c:if> --%>
				<button class="button__secondary" type="button" onclick="location.href='<c:url value="/programgroup/pageDetail.do?programGroupOid=${param.programGroupOid}"/>'">취소</button>
			</div>
		</fieldset>
	</form>
</div>

<script>
$(function() {
	vm = new Vue({
		el : '#content',
		data : function() {
			return {
				programGroup : {
					programgroupOid : "${param.programGroupOid}"
				}
			}
		},
		methods : {
			modifyProgramGroup : function() {
				Valid.init(vm, vm.programGroup);
                var constraints = {};
                constraints = Valid.getConstraints();
                if(Valid.validate(constraints)) return;
                
				if(Modal.MODIFY()) return;
				var request = $.ajax({
					method : "PUT",
					url : N2M_CTX + "/programgroup/modify.do",
					contentType : "application/json",
					data : JSON.stringify(vm.programGroup),
// 					dataType: "json"
				});
				request.done(function(data) {
				    Modal.OK();
					location.href = N2M_CTX + "/programgroup/pageDetail.do?programGroupOid=${param.programGroupOid}";
				});
			},
			getProgramGroup : function() {
				var request = $.ajax({
					method : "GET",
					url : N2M_CTX + "/programgroup/get.do",
					data : {
						programgroupOid: "${param.programGroupOid}"
					},
					dataType: "json"
				});
				request.done(function(data) {
					vm.programGroup = data;
				});
			}
		}
	});
	vm.getProgramGroup();
});
</script>
