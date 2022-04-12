<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!-- sub-cover -->
<section class="sub-cover">
  <h3 class="sub-cover__title sub-cover__title--search material-icons">통합검색 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
</section>
<hr>
<!-- sub-nav -->

<!-- sub-nav -->
<hr>
<div class="sub__container">
  <h3 class="sub__heading1">통합검색</h3>

  <div class="sub__content">
    <form class="integrated-search__form">
      <input class="integrated-search__input" type="text" v-model="totalSch" @keypress.enter.prevent="goTotalSearchList" @keyup="totalSchKeyUp" placeholder="검색어를 입력하세요">
      <button class="integrated-search__submit" type="button" @click="goTotalSearchList"><span class="hidden">검색</span></button>
    </form>
    <h4 class="sub__heading2">전체 <span class="color--primary">{{totalListSize}}</span>건에 검색결과가 있습니다. </h4>
  </div>
  <!-- 데이터셋 -->
  <div class="sub__content">
    <h5 class="sub__heading3">데이터셋 <span class="color--primary">{{dataset.totalListSize}}</span>건</h5>
    <ul class="data-list">
      <li class="data-list__item--none material-icons" v-if="dataset.list.length === 0">데이터가 없습니다.</li>
      <li class="data-list__item" v-for="dataset in dataset.list">
        <div class="data-unit">
          <a class="data-unit__link" href="#none" @click="onRowClick('DATASET', dataset)">
            <div class="data-list__thumb">
              <img class="data-list__image material-icons" :src="fileDownload(dataset.dsOid, dataset.dsFileOid)" onerror="this.src='<c:url value="/smartcity/img/dummy/thumb_empty_100x100.jpg"/>'"/>
            </div>
            <dl class="data-unit__caption">
              <dt class="data-unit__title">{{dataset.dsTitle}}</dt>
              <dd class="data-unit__category"><span class="data-unit__status" v-if="dataset.dsStatus != 'prodMode'">[{{dataset.dsStatusNm}}]</span>{{dataset.dsCategoryNm}}</dd>
              <dd class="data-unit__text">{{dataset.dsDesc}}</dd>
              <dd class="data-unit__badge">
                <span class="badge badge--blue" v-if="dataset.dsExtensionNm">{{dataset.dsExtensionNm}}</span>
                <span class="badge badge--green" v-if="dataset.dsOfferTypeNm">{{dataset.dsOfferTypeNm}}</span>
                <span class="badge badge--orange" v-if="dataset.dsDataTypeNm">{{dataset.dsDataTypeNm}}</span>
                <span class="badge badge--pink">무료</span>
              </dd>
            </dl>
          </a>
          <div class="data-unit__info">
            <span class="data-unit__owner">{{dataset.dsCreateUserId}}</span>
            <span class="data-unit__date">{{dataset.dsCreated|date}}</span>
            <span class="data-unit__view material-icons"><span class="hidden">조회수</span>{{dataset.dsHits}}</span>
            <span class="data-unit__like material-icons"><span class="hidden">관심 상품으로 담긴 회수</span>{{dataset.itrtTotalCount}}</span>
          </div>
        </div>
      </li>
    </ul>
    <a class="link-more" href="#none" v-if="dataset.totalListSize > 10" @click="goPageList('DATASET')">더보기</a>
  </div>
  <!-- FAQ -->
  <div class="sub__content">
    <h5 class="sub__heading3">FAQ <span class="color--primary">{{faq.totalListSize}}</span>건</h5>
    <ul class="integrated-search__list">
      <li class="integrated-search__item--none material-icons" v-if="faq.list.length === 0">데이터가 없습니다.</li>
      <li class="integrated-search__item" v-for="faq in faq.list">
        <a class="integrated-search__link" href="#none" @click="onRowClick('FAQ', faq)">
          <dl class="integrated-search-result">
            <dt class="integrated-search-result__title">{{faq.faqTitle}}</dt>
            <dd class="integrated-search-result__date">{{faq.faqCreDt|date}}</dd>
            <dd class="integrated-search-result__text">{{faq.faqQuestion}}</dd>
          </dl>
        </a>
      </li>
    </ul>
    <a class="link-more" href="#none" v-if="faq.totalListSize > 10" @click="goPageList('FAQ')">더보기</a>
  </div>
  <!-- 묻고답하기 -->
  <div class="sub__content">
    <h5 class="sub__heading3">묻고답하기 <span class="color--primary">{{qna.totalListSize}}</span>건</h5>
    <ul class="integrated-search__list">
      <li class="integrated-search__item--none material-icons" v-if="qna.list.length === 0">데이터가 없습니다.</li>
      <li class="integrated-search__item" v-for="qna in qna.list">
        <a class="integrated-search__link" href="#none" @click="onRowClick('QNA', qna)">
          <dl class="integrated-search-result">
            <dt class="integrated-search-result__title">{{qna.qnaTitle}}</dt>
            <dd class="integrated-search-result__date">{{qna.qnaQuestionDt|date}}</dd>
            <dd class="integrated-search-result__text">{{qna.qnaQuestion}}</dd>
          </dl>
        </a>
      </li>
    </ul>
    <a class="link-more" href="#none" v-if="qna.totalListSize > 10" @click="goPageList('QNA')">더보기</a>
  </div>      
  <!-- 신고하기 -->
  <div class="sub__content">
    <h5 class="sub__heading3">신고하기 <span class="color--primary">{{datacomplain.totalListSize}}</span>건</h5>
    <ul class="integrated-search__list">
      <li class="integrated-search__item--none material-icons" v-if="datacomplain.list.length === 0">데이터가 없습니다.</li>
      <li class="integrated-search__item" v-for="datacomplain in datacomplain.list">
        <a class="integrated-search__link" href="#none" @click="onRowClick('DATACOMPLAINT', datacomplain)">
          <dl class="integrated-search-result">
            <dt class="integrated-search-result__title">{{datacomplain.datacomplainTitle}}</dt>
            <dd class="integrated-search-result__date">{{datacomplain.datacomplainCreDt|date}}</dd>
            <dd class="integrated-search-result__text">{{datacomplain.datacomplainContent}}</dd>
          </dl>
        </a>
      </li>
    </ul>
    <a class="link-more" href="#none" v-if="datacomplain.totalListSize > 10" @click="goPageList('DATACOMPLAINT')">더보기</a>
  </div>
  <!-- 공지사항 -->
  <div class="sub__content">
    <h5 class="sub__heading3">공지사항 <span class="color--primary">{{notice.totalListSize}}</span>건</h5>
    <ul class="integrated-search__list">
      <li class="integrated-search__item--none material-icons" v-if="notice.list.length === 0">데이터가 없습니다.</li>
      <li class="integrated-search__item" v-for="notice in notice.list">
        <a class="integrated-search__link" href="#none" @click="onRowClick('NOTICE', notice)">
          <dl class="integrated-search-result">
            <dt class="integrated-search-result__title">{{notice.noticeTitle}}</dt>
            <dd class="integrated-search-result__date">{{notice.noticeCreDt|date}}</dd>
            <dd class="integrated-search-result__text" v-html="notice.noticeContent"></dd>
          </dl>
        </a>
      </li>
    </ul>
    <a class="link-more" href="#none" v-if="notice.totalListSize > 10" @click="goPageList('NOTICE')">더보기</a>
  </div>  
</div>

<script>
$(function(){
    vm = new Vue({
        el: '#content',
        data: function(){
            return {
            	totalListSize: 0,
            	dataset: {list: []},
            	faq: {list: []},
            	qna: {list: []},
            	datacomplain: {list: []},
            	notice: {list: []},
            }
        },
        created: function() {
        	this.totalSch = "${param.totalSch}";
        },
        methods: {
        	getTotalSearchList: function() {
        		vm.loadingFlag = true;
        		
        		var request = $.ajax({
        			url: SMC_CTX + "/search/getList.do",
        			method: "GET",
        			dataType: "json",
        			data: {
        				totalSch : vm.totalSch,
        			}
        		});
        		
        		request.done(function(data) {
        			vm.dataset = data.dataset;
        			vm.faq = data.faq;
        			vm.qna = data.qna;
        			vm.datacomplain = data.datacomplain;
        			vm.notice = data.notice;
        			
        			vm.totalListSize += vm.dataset.totalListSize + vm.faq.totalListSize + vm.qna.totalListSize + vm.datacomplain.totalListSize + vm.notice.totalListSize;
        			
        			vm.loadingFlag = false;
        		});
        		
        		request.done(function(data) {
        			vm.loadingFlag = false;
        		});
        	},
        	onRowClick: function(type, row) {
        		switch(type) {
        		  case 'DATASET':
        			  //데이터셋은 Controller에 상세 페이지 이동시 조회수 증가 메소드 추가되어 있음
        			  location.href = SMC_CTX + "/dataset/pageDetail.do?nav=MNU025&dsOid=" + row.dsOid;
        			  
        			  break;
        		  case 'FAQ':
        			  location.href = SMC_CTX + "/cusvc/faq/pageDetail.do?nav=MNU020&faqOid="+row.faqOid;
                      
                      break;
        		  case 'QNA':
        			  location.href = SMC_CTX + "/cusvc/qna/pageDetail.do?nav=MNU021&qnaOid="+row.qnaOid;
                      break;
        		  case 'DATACOMPLAINT':
        			  location.href = SMC_CTX + "/cusvc/datacomplaint/pageDetail.do?nav=MNU022&datacomplainOid="+row.datacomplainOid;
                      break;
        		  case 'NOTICE':
        			  location.href = SMC_CTX + "/cusvc/notice/pageDetail.do?nav=MNU023&noticeOid="+row.noticeOid;
                      break;
        		}
        	},
        	goPageList: function(type) {
        		switch(type) {
        		  case 'DATASET':
                    location.href = SMC_CTX + "/dataset/pageList.do?nav=MNU025&schValue="+vm.totalSch+"&schCondition=title";
                    
                    break;
        		  case 'FAQ':
        			  location.href = SMC_CTX + "/cusvc/faq/pageList.do?nav=MNU020&schValue="+vm.totalSch;
        			  
        			  break;
        		  case 'QNA':
        			  location.href = SMC_CTX + "/cusvc/qna/pageList.do?nav=MNU021&schValue="+vm.totalSch+"&schCondition=title";
                      
                      break;
        		  case 'DATACOMPLAINT':
                      location.href = SMC_CTX + "/cusvc/datacomplaint/pageList.do?nav=MNU022&schValue="+vm.totalSch+"&schCondition=datacomplainTitle";
                      
                      break;
        		  case 'NOTICE':
                      location.href = SMC_CTX + "/cusvc/notice/pageList.do?nav=MNU023&schValue="+vm.totalSch;
                      
                      break;
        		}
        		
        	}
        }
    });
    
    vm.getTotalSearchList();
});

</script>