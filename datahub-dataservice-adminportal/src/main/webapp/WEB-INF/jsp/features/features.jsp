<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">확장기능관리</h3>
	<form>
		<div class="section" v-for="(feature, index) in features">
			<div class="section__header">
				<h4 class="section__title">{{feature.featureNm}}</h4>
			</div>
			<div class="section__content">
				<table class="table--row">
					<colgroup>
						<col style="width: auto;">
					</colgroup> 
					<tbody>
						<tr>
							<td>
								<input type="hidden" v-model="feature.featureCd"/>
								<label class="label__radio"><input :name="index + 'radio_ds'" class="input__radio" type="radio" v-model="feature.enabledTf" value="true">활성화</label>
								<label class="label__radio"><input :name="index + 'radio_ds'" class="input__radio" type="radio" v-model="feature.enabledTf" value="false">비활성화</label>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="button__group">
			<!-- 수정권한 체크 -->
			<c:if test="${writeYn eq 'Y'}">
				<button class="button__primary" type="button" @click="applyFeatures">저장</button>
			</c:if>
		</div>
	</form>
</div>
<script>
	$(function() {
		vm = new Vue({
			el : '#content',
			data : function() {
				return {
					features: [
						<c:forEach var="code" items="${featuresList}" varStatus="status">
							{
								featureNm: "${code.featureNm}",
								featureCd: "${code.featureCd}",
								enabledTf: ${code.enabledTf}
							},
						</c:forEach>
					],
				}
			},
			methods: {
				applyFeatures: function(){
					if(Modal.MODIFY()) return;
					
					var request = $.ajax({
						method: "PUT",
						url: N2M_CTX + "/features/modify.do",
						contentType: "application/json",
						data: JSON.stringify({
							features: vm.features
						}),
					});
					
					request.done(function(){
						Modal.OK();
						location.href = N2M_CTX + "/features/pageDetail.do";
					});
					
					request.fail(function(data){
						console.log(data);
					});
				}
			}
		});
		
	});
	
</script>
