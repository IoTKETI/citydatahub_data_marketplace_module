<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
	<div id="content" v-cloak>
	<h2 class="hidden">본문</h2>
      <!-- sub-cover -->
      <section class="sub-cover">
        <h3 class="sub-cover__title material-icons">고객 서비스 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
      </section>
      <hr>
       <!-- sub-nav -->
     ${lnbHtml}
 		<!-- sub-nav -->
	<hr>
	<div class="sub__container">
	<%@ include file="/WEB-INF/jsp/tiles/tiles_breadcrumb.jspf" %>
    <h3 class="sub__heading1">FAQ</h3>
           <div class="sub__content">
          <h4 class="sub__heading2">FAQ 상세</h4>
          <table class="detail-table">
            <caption>해당 테이블에 대한 캡션</caption>
            <colgroup>
              <col width="10%">
              <col width="*">
              <col width="10%">
              <col width="*">
            </colgroup>
            <tbody>
            <tr>
                <th scope="row">번호</th>
                <td colspan="3">{{faq.faqId}}</td>
              </tr>
              <tr>
                <th scope="row">등록자</th>
                <td>{{faq.faqCreUsrNm}}</td>
                <th scope="row">등록일시</th>
                <td>{{faq.faqCreDt|date}}</td>
              </tr>
              <tr>
                <th scope="row">제목</th>
                <td>{{faq.faqTitle}}</td>
                <th scope="row">조회수</th>
                <td>{{faq.faqCnt}}</td>
              </tr>
              <tr>
                <th scope="row">질문</th>
                <td colspan="3" v-html="$options.filters.enter(faq.faqQuestion)">
              </tr>
              <tr>
               	  <th>답변</th>
	              <td colspan="3" v-html="$options.filters.enter(faq.faqAnswer)">
              </tr>
              <tr>
                <th scope="row">첨부파일</th>
                <td colspan="3">
                  <ul class="attach-file__list">
                    <li class="attach-file__item--none" v-if="faq.fileList.length === 0">첨부파일이 없습니다.</li>
                    <li class="attach-file__item material-icons" v-for="(file, index) in faq.fileList">
                        <a @click="download(file.faqfileOid)">{{file.faqfileOrgNm}}<span class="attach-file__item--size">({{file.faqfileSizeByte | size}})</span></a>
                        <button type="button" class="attach-file__button--delete material-icons" title="파일 삭제"><span class="hidden">파일 삭제</span></button>
                    </li>
                    
                  </ul>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="sub__bottom">
          <div class="button__group">
            <a class="button button__danger" href="<c:url value="/cusvc/faq/pageList.do?nav=MNU020"/>">목록</a>
          </div>
        </div>
  </div>
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
						url: SMC_CTX + "/cusvc/faq/get.do",
						method: "GET",
						dataType: "json",
						data: vm.faq,
							nohit : nohit
					});
					request.done(function(data){
						vm.faq = data;
					});
				},
				download: function(faqfileOid){
					location.href = SMC_CTX + "/cusvc/faq/downloadFile.do?faqOid="+vm.faq.faqOid+"&fileId="+faqfileOid;
				}
			}
		})
		vm.getFaq();
	});
</script>
