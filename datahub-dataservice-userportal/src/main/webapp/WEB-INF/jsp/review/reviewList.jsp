<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
      <h2 class="hidden">본문</h2>
      <!-- sub-cover -->
      <section class="sub-cover">
        <h3 class="sub-cover__title material-icons">활용사례 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
      </section>
      <hr>
       <!-- sub-nav -->
     ${lnbHtml}
 <!-- sub-nav -->
    <hr>
      
      <div class="sub__container">
        <!-- breadcrumb -->
        <%@ include file="/WEB-INF/jsp/tiles/tiles_breadcrumb.jspf" %>
        <!-- //breadcrumb -->
        <h3 class="sub__heading1">활용후기<small class="sub__heading1--small">카테고리별 다양한 데이터를 확인하여 보시기 바랍니다.</small></h3>
        <div class="sub__content">
            <form class="search-form" action="">
              <fieldset>
                <legend>데이터 검색 폼</legend>
                <table class="form-table">
                  <caption>데이터 검색 테이블</caption>
                  <colgroup>
                    <col width="6%">
                    <col width="*">
                    <col width="6%">
                    <col width="*">
                    <col width="14%">
                  </colgroup>
                  <tbody>
                    <tr>
                      <th scope="row">검색어</th>
                      <td><select class="select" v-model="pageInfo.schCondition">
                          <option value="">선택</option>
                          <option value="title">제목</option>
                        </select>
                        <input class="input input__search" type="search" v-model="pageInfo.schValue" placeholder="검색어를 입력하세요." @keypress.enter="getReviewList(1)">
                      </td>
                      <th scope="row">등록일</th>
                      <td>
                        <div class="datepicker">
                          <label class="label__datepicker material-icons"><input class="input input__datepicker" id="fromDate" v-model="pageInfo.fromDate" type="text"></label><span class="period">~</span><label class="label__datepicker material-icons"><input class="input input__datepicker" id="toDate" v-model="pageInfo.toDate" type="text"></label>
                        </div>
                      </td>
                      <td rowspan="2"><button class="button__submit" type="button" @click="getReviewList(1)">검색</button></td>
                    </tr>
                  </tbody>
                </table>
              </fieldset>
            </form>
        </div>
        <div class="sub__content">
          <h4 class="sub__heading2">상세목록</h4>
          <ul class="data-list" style="border-top: 0px;">
            <li class="data-list__item" v-if="list.length === 0">
                <div class="data-unit data-unit--none">
                    데이터가 없습니다.
                </div>
            </li>
          
            <li class="data-list__item" v-for="review in list">
              <div class="data-unit">
                <a class="data-unit__link" :href="'/review/pageDetail.do?reviewOid='+review.reviewOid">
                  <div class="data-list__thumb">
                    <img class="data-list__image material-icons" :src="download(review.reviewOid,review.reviewfileOid)" onerror="this.src='<c:url value="/smartcity/img/dummy/thumb_empty_100x100.jpg"/>'"/>
                  </div>
                  <dl class="data-unit__caption">
                    <dt class="data-unit__title">{{review.reviewTitle}}</dt>
                    <dd class="data-unit__text">{{review.reviewDesc}}</dd>
                  </dl>
                </a>
                <div class="data-unit__info">
                  <span class="data-unit__url">{{review.reviewSrc}}</span>
                  <span class="data-unit__date">{{review.reviewCreDt|date}}</span>
                </div>
              </div>
            </li>
          </ul>
        </div>
        <div class="sub__bottom">
          <div>
              <n2m-pagination :pager="pageInfo" @pagemove="getReviewList"/>
          </div>
          <div class="button__group">
            <a class="button button__primary" href="<c:url value="/review/pageEdit.do"/>">등록</a>
          </div>
        </div>
    </div>
</div>

<script>
    $(function() {
        Vue.use(N2MPagination);
        vm = new Vue({
            el : '#content',
            data : function() {
                return {
                    list : []
                }
            },
            methods : {
                getReviewList : function(curPage) {
                    var request = $.ajax({
                        url : SMC_CTX + "/review/getList.do",
                        method : "GET",
                        dataType: "json",
                        data: {
                            schCondition  : vm.pageInfo.schCondition,
                            schValue      : vm.pageInfo.schValue,
                            curPage       : curPage,
                            fromDate      : vm.pageInfo.fromDate,
                            toDate        : vm.pageInfo.toDate
                        }
                    });
                    request.done(function(data) {
                        vm.list = data.list;
                        vm.pageInfo = data.page;
                    });
                    request.fail(function(data) {
                        console.log("FAIL");
                    });
                },
                download: function(reviewOid,reviewfileOid){
                    return SMC_CTX + "/review/downloadFile.do?reviewOid="+reviewOid+"&fileId="+reviewfileOid;
                }
            }
        });
        
        vm.keepPageInfo(function(curPage) {
            vm.getReviewList(curPage);
        });
    });
</script>