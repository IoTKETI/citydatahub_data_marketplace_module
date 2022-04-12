<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">공지사항상세</h3>
	<form>
		<fieldset>
			<legend>필드셋 제목</legend>
			<!-- section-write -->
			<section class="section">
				<div class="section__content">
					<table class="table--row">
						<caption>테이블 제목</caption>
						<colgroup>
							<col style="width: 150px">
							<col style="width: auto">
							<col style="width: 150px">
							<col style="width: auto">
						</colgroup>
						<tbody>
							<tr>
								<th>번호</th>
								<td colspan="1">{{notice.noticeId}}</td>
								<th>등록자</th>
								<td colspan="1">{{notice.noticeCreUsrNm}}</td>
							</tr>
							<tr>
								<th>팝업유무</th>
								<td colspan="1"><input class="input__checkbox"
									id="checkboxPopup" type="checkbox"
									v-model="notice.noticeMainPopFl" true-value="Y" false-value="N"
									disabled /> <label class="label__checkbox" for="checkboxPopup">메인화면
										팝업 설정</label></td>
								<th>팝업기간</th>
								<td colspan="1"><input id="startPopupDate"
									class="input__text" type="text" style="width: 48%"
									v-model="notice.noticeMainStartDt" readonly /> - <input
									id="endPopupDate" class="input__text" type="text"
									style="width: 48%" v-model="notice.noticeMainEndDt" readonly />
								</td>
							</tr>
							<tr>
								<th>우선공지여부</th>
								<td colspan="3"><input class="input__checkbox"
									id="checkboxFirstFl" type="checkbox"
									v-model="notice.noticeFirstFl" true-value="Y" false-value="N"
									disabled /> <label class="label__checkbox"
									for="checkboxFirstFl">공지사항 목록의 최상위에 표시됩니다.</label></td>
							</tr>
							<tr>
								<th>제목</th>
								<td colspan="3">{{notice.noticeTitle}}</td>
							</tr>
							<tr>
								<th>내용</th>
								<td class="smart-editor" colspan="3"
									v-html="notice.noticeContent"></td>
							</tr>
							<tr>
								<th>첨부파일</th>
								<td class="file__group" colspan="3">
									<ul class="file__list">
										<li class="file__item file__item--none"
											v-if="notice.fileList.length === 0">첨부파일이 없습니다.</li>
										<li class="file__item"
											v-for="(file, index) in notice.fileList"><a
											@click="download(file.noticefileOid)">{{file.noticefileOrgNm}}
												({{file.noticefileSizeByte | size}})</a></li>
									</ul>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</section>
			<!-- //section-default -->
			<div class="button__group">
				<!-- 수정권한 체크 -->
				<c:if test="${modifiedYn eq 'Y'}">
					<button class="button__primary" type="button"
						onclick="location.href='<c:url value="/notice/pageModify.do?noticeOid=${param.noticeOid}"/>'">수정</button>
				</c:if>
				<button class="button__secondary" type="button"
					onclick="location.href='<c:url value="/notice/pageList.do"/>'">목록</button>
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
					notice : {
						noticeOid : "${param.noticeOid}",
						fileList : []
					}
				}
			},
			methods : {
				getNotice : function(nohit) {
					var request = $.ajax({
						url : N2M_CTX + "/notice/get.do",
						method : "GET",
						dataType : "json",
						data :{
							   noticeOid : "${param.noticeOid}",
							   nohit     : nohit
						}
					});
					request.done(function(data) {
						vm.notice = data;
					});
				},
				download : function(noticefileOid) {
					location.href = N2M_CTX
							+ "/notice/downloadFile.do?noticeOid="
							+ vm.notice.noticeOid + "&fileId=" + noticefileOid;
				}
			}
		})
		vm.getNotice();
	});
</script>
