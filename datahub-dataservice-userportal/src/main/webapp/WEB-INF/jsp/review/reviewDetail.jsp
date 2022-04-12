<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<h2 class="hidden">본문</h2>
<!-- sub-cover -->
<section class="sub-cover">
  <h3 class="sub-cover__title material-icons">활용 사례 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
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
  
  <h3 class="sub__heading1">활용후기 상세<small class="sub__heading1--small">카테고리별 다양한 데이터를 확인하여 보시기 바랍니다.</small></h3>
  <div class="data-view">
    <div class="data-view__header" style="margin-bottom:10px;">
      <div class="data-unit">
        <div class="data-view__thumb">
          <img width="165" height="150" class="data__image" :src="download(review.reviewfileOid)" onerror="this.src='<c:url value="/smartcity/img/dummy/thumb_empty_150x150.jpg"/>'">
        </div>
        <dl class="data-unit__caption">
          <dt class="data-unit__title">{{review.reviewTitle}}</dt>
          <dd class="data-unit__url">{{review.reviewSrc}}</dd>
        </dl>
      </div>
    </div>
    <div class="sub__content">
      <h4 class="sub__heading2">설명</h4>
      <p class="sub__text">{{review.reviewDesc}}</p>
    </div>
    <div class="sub__content" style="margin-top:-14px;">
      <h4 class="sub__heading2">활용사례</h4>
      <div class="review-slider">
        <div class="review-slider__container swiper-container">
          <ul class="review-slider__list swiper-wrapper">
            <!-- [D] 기본 이미지 -->
            <li class="review-slider__item swiper-slide" v-if="reviewFileList.length === 0"><img class="review-slider__image" src="../smartcity/img/dummy/thumb_empty_300x320.jpg"></li>
            <!-- // [D] 기본 이미지 -->
            <li class="review-slider__item swiper-slide" v-for="file in reviewFileList">
              <img class="review-slider__image" :src="download(file.reviewfileOid)" onerror="this.src='<c:url value="/smartcity/img/dummy/thumb_empty_300x320.jpg"/>'">
            </li>
          </ul>
        </div>
      </div>
      <button class="review-slider__button review-slider__button--prev" type="button"><span class="hidden">이전으로</span></button>
      <button class="review-slider__button review-slider__button--next" type="button"><span class="hidden">다음으로</span></button>
    </div>
    <div class="sub__content">
      <h4 class="sub__heading2">추가정보</h4>
      <div class="metadata-table">
        <table>
          <caption>메타 데이터 표</caption>
          <colgroup>
            <col width="33.33%">
            <col width="33.33%">
            <col width="*">
          </colgroup>
          <tbody>
            <tr>
              <th scope="col">개발자</th>
              <th scope="col">서비스개시일</th>
              <th scope="col">개발유형</th>
            </tr>
            <tr>
              <td data-metadata-th="개발자">{{review.reviewDeveloperId}}</td>
              <td data-metadata-th="서비스개시일">{{review.reviewOpenDt|date}}</td>
              <td data-metadata-th="개발유형">{{review.reviewDevType}}</td>
            </tr>
            <tr>
              <th scope="col">활용사례등록일</th>
              <th scope="col">개발자유형</th>
              <th scope="col">개발자소재지</th>
            </tr>
            <tr>
              <td data-metadata-th="활용사례등록일">{{review.reviewCreDt|date}}</td>
              <td data-metadata-th="개발자유형">{{review.reviewDeveloperType}}</td>
              <td data-metadata-th="개발자소재지">{{review.reviewDeveloperLoc}}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="sub__content">
      <h5 class="sub__heading2">활용데이터셋</h5>
      <table class="board-table">
        <caption>해당 테이블에 대한 캡션</caption>
        <colgroup>
          <col width="*">
          <col width="20%">
          <col width="20%">
          <col width="20%">
        </colgroup>
        <thead>
          <tr>
            <th scope="col">데이터명</th>
            <th scope="col">카테고리</th>
            <th scope="col">상태</th>
            <th scope="col">소유자</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="dataset in reviewDatasetList">
            <th class="text--left" scope="row">{{dataset.title}}</th>
            <td class="text--center">{{dataset.categoryName}}</td>
            <td class="text--center">{{dataset.statusName}}</td>
            <td class="text--left">{{dataset.modelOwnerId}}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="sub__bottom">
      <div class="button__group">
        <a class="button button__primary" v-if="review.reviewCreUsrId === loginUserId" :href="'/review/pageModify.do?reviewOid='+review.reviewOid">수정</a>
        <a class="button button__outline--primary" href="<c:url value="/review/pageList.do"/>">목록</a>
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
                    review: {
                        reviewOid: "${param.reviewOid}",
                        fileList : []
                    },
                    imgFile: {},
                    reviewFileList: [],
                    reviewDatasetList: []
                }
            },
            methods: {
                getReview: function(){
                    vm.loadingFlag = true;
                    var request = $.ajax({
                        url: SMC_CTX + "/review/get.do",
                        method: "GET",
                        dataType: "json",
                        data: vm.review
                    });
                    
                    request.done(function(data){
                        vm.loadingFlag = false;
                        vm.review = data.review;
                        vm.reviewDatasetList = data.reviewDatasetList;
                        
                        var fileListData = data.review.fileList;
                        
                        for (var i = 0; i < fileListData.length; i++) {
                        	if (fileListData[i].reviewfileFlag === "file") {
                        		vm.reviewFileList.push(fileListData[i]);
                        	}
                        }
                        
                        vm.$nextTick(function() {
                        	var swiper4 = new Swiper('.review-slider__container', {
                        	      autoplay            : {
                        	        delay : 2500,
                        	        disableOnInteraction : false,
                        	      },
                        	      direction           : 'horizontal',
                        	      grabCursor          : false,
                        	      keyboard            : {
                        	        enabled : true,
                        	      },
                        	      loop                : false,
                        	      navigation          : {
                        	        nextEl : '.review-slider__button--next',
                        	        prevEl : '.review-slider__button--prev',
                        	      },
                        	      roundLengths        : true,
                        	      slidesPerView       : 'auto', 
                        	      slidesPerGroup      : 1,
                        	      spaceBetween        : 10,
                        	      breakpointsInverse  : true, 
                        	      breakpoints         : { 
                        	        1024: {
                        	          slidesPerView   : 'auto', 
                        	          spaceBetween    : 10,
                        	          centeredSlides  : true,
                        	        },
                        	        1260: {
                        	          slidesPerView   : 3.58,
                        	          spaceBetween    : 40,
                        	          centeredSlides  : false,
                        	        },
                        	      },
                        	    });
                        });
                    });
                },
                download: function(reviewfileOid){
                	if(!reviewfileOid) return;
                    return SMC_CTX + "/review/downloadFile.do?reviewOid="+vm.review.reviewOid+"&fileId="+reviewfileOid;
                }
            }
        })
        vm.getReview();
    });
</script>
