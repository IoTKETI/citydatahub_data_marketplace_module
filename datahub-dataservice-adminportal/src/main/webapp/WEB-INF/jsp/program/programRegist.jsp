<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">프로그램등록</h3>
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
								<th>등록자</th>
								<td colspan="3">
									${sessionScope.user.userNm}
								</td>
							</tr>
							<tr>
								<th class="icon__require">프로그램 그룹 명</th>
								<td colspan="3">
									<input class="input__text" title="프로그램 그룹 명" type="text" v-model="programGroup.programgroupNm" ref="programgroupNm" required/>
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
				<button class="button__primary" type="button" @click="createProgramGroup">저장</button>
				<button class="button__secondary" type="button" onclick="location.href='<c:url value="/programgroup/pageList.do"/>'">취소</button>
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
					programgroupNm : "",
	                programgroupDesc : "",
	                programgroupCreUsrId : "",
	                programgroupUseFl : "Y"
				}
			}
		},
		methods : {
			createProgramGroup : function() {
				Valid.init(vm, vm.programGroup);
                var constraints = {};
                constraints = Valid.getConstraints();
                if(Valid.validate(constraints)) return;

                if(Modal.REGIST()) return;
				
				var request = $.ajax({
					method : "POST",
					url : N2M_CTX + "/programgroup/create.do",
					contentType : "application/json",
					data : JSON.stringify(vm.programGroup),
					dataType: "json"
				});
				request.done(function(data) {
				    Modal.OK();
					location.href = N2M_CTX + "/programgroup/pageList.do";
				});
			}
		}
	});
});
</script>