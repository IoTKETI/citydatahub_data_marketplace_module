<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">FAQ 상세</h3>
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
                {{faq.faqId}}
              </td>
            </tr>
            <tr>
              <th>등록자</th>
              <td>
                {{faq.faqCreUsrNm}}
              </td>
              <th>등록일시</th>
              <td>
                {{faq.faqCreDt|date}}
              </td>
            </tr>
            <tr>
              <th>제목</th>
              <td>
                {{faq.faqTitle}}
              </td>
              <th>조회수</th>
              <td>
                {{faq.faqCnt}}
              </td>
            </tr>
            <tr>
              <th>질문</th>
              <td colspan="3" v-html="$options.filters.enter(faq.faqQuestion)">
              </td>
            </tr>
            <tr>
              <th>답변</th>
              <td colspan="3" v-html="$options.filters.enter(faq.faqAnswer)">
            </tr>
            <tr>
              <th>첨부파일</th>
			    <td class="file__group" colspan="3">
			    	<ul class="file__list">
			            <li class="file__item file__item--none" v-if="faq.fileList.length === 0">첨부파일이 없습니다.</li>
			            <li class="file__item" v-for="(file, index) in faq.fileList">
			                <a @click="download(file.faqfileOid)">{{file.faqfileOrgNm}} ({{file.faqfileSizeByte | size}})</a>
			            </li>
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
             <button class="button__primary" type="button"  onclick="location.href='<c:url value="/faq/pageModify.do?faqOid=${param.faqOid}"/>'">수정</button>
       	</c:if>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/faq/pageList.do"/>'">목록</button>
    </div>
  </fieldset>
</form>
</div>
<script>
  $(function(){
	  Vue.use(N2MFileUploader);
		vm = new Vue({
			el: '#content',
			data: function(){
				return {
					faq: {
						faqOid: "${param.faqOid}",
						fileList : []
					},
				}
			},
			methods: {
				getFaq: function(nohit){
					var request = $.ajax({
						url: N2M_CTX + "/faq/get.do",
						method: "GET",
						dataType: "json",
						data: {
								faqOid: "${param.faqOid}",
								nohit : nohit
						}
					});
					request.done(function(data){
						vm.faq = data;
					});
				},
				download: function(faqfileOid){
					location.href = N2M_CTX + "/faq/downloadFile.do?faqOid="+vm.faq.faqOid+"&fileId="+faqfileOid;
				}
			}
		})
		vm.getFaq();
	});
</script>
