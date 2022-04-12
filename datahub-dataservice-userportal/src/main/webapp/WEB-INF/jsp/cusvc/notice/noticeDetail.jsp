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
    <h3 class="sub__heading1">공지사항</h3>
  <div class="sub__content">
    <form>
      <fieldset>
      <h4 class="sub__heading2">공지사항 상세</h4>
      <table class="detail-table">
          <caption>공지사항 테이블</caption>
          <colgroup>
            <col width="10%">
            <col width="*">
            <col width="10%">
            <col width="*">
          </colgroup>
          <tbody>
                <tr>
                  <th scope="row">번호</th>
                  <td colspan="3">
                    {{notice.noticeId}}
                  </td>
                </tr>
                <tr>
                  <th scope="row">등록자</th>
                  <td>
                    {{notice.noticeCreUsrNm}}
                  </td>
                  <th scope="row">등록일시</th>
                  <td>
                    {{notice.noticeCreDt|date}}
                  </td>
                </tr>
                <tr>
                  <th scope="row">제목</th>
                  <td>
                    {{notice.noticeTitle}}
                  </td>
                  <th scope="row">조회수</th>
                  <td>
                    {{notice.noticeCnt}}
                  </td>
                </tr>
                <tr>
                  <th scope="row">내용</th>
                  <td v-html="notice.noticeContent">
                  </td>
                </tr>
	            <tr>
	                <th scope="row">첨부파일</th>
	                <td colspan="3">
	                  <ul class="attach-file__list">
	                    <li class="attach-file__item--none" v-if="notice.fileList.length === 0">첨부파일이 없습니다.</li>
	                    <li class="attach-file__item material-icons"  v-for="(file, index) in notice.fileList">
	                    <a @click="download(file.noticefileOid)">
	                        {{file.noticefileOrgNm}}<span class="attach-file__item--size">({{file.noticefileSizeByte | size}})</span>
	                        <button type="button" class="attach-file__button--delete material-icons" title="파일 삭제"><span class="hidden">파일 삭제</span></button>
	                    </a>
	                    </li>
	                  </ul>
	                </td>
	              </tr>
              </tbody>
        </table>
      </fieldset>
    </form>
    </div>
    
    <div class="sub__bottom">
      <div class="button__group">
        <a class="button button__danger" href="<c:url value="/cusvc/notice/pageList.do"/>">목록</a>
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
                    notice: {
                        noticeOid: "${param.noticeOid}",
                        fileList : []
                    },
                }
            },
            methods: {
                getnotice: function(nohit){
                	vm.loadingFlag = true;
                    var request = $.ajax({
                        url: SMC_CTX + "/cusvc/notice/get.do",
                        method: "GET",
                        dataType: "json",
                        data: vm.notice,
                        	 nohit : nohit
                    });
                    request.done(function(data){
                    	vm.loadingFlag = false;
                        vm.notice = data;
                    });
                },
                download: function(noticefileOid){
                    location.href = SMC_CTX + "/cusvc/notice/downloadFile.do?noticeOid="+ vm.notice.noticeOid + "&fileId=" + noticefileOid;
                },
            }
        })
        vm.getnotice();
    });
</script>
