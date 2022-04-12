<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
      <section class="main-cover">
        <h3 class="main-cover__title"><small class="main-cover__title--small">"시티 데이터 허브" </small> CITY DATA HUB</h3>
        <p class="main-cover__text">도시와 도시의 데이터 및 다양한 데이터를 쉽게 연결하여 온라인에서 유통할 수 있습니다.<br>시티 허브에서 제공하는 다양한 서비스를 활용해 보세요.</p>
      </section>
      <!-- //main-c0ver -->
      <hr>
      <!-- 데이터 등록현황 -->
      <section class="main-section main-section--register">
        <h3 class="hidden">데이터 등록 현황</h3>
        <ul class="main-register__list">
          <li class="main-register__item">
            <strong class="main-register__title">전체 데이터</strong><span class="main-register__number main-register__number--total">{{datasetCountAll}}</span>
          </li>
          <li class="main-register__item">
            <strong class="main-register__title">OPEN API</strong><span class="main-register__number">{{datasetCountApi}}</span>
          </li>
          <li class="main-register__item">
            <strong class="main-register__title">파일</strong><sapn class="main-register__number">{{datasetCountFile}}</sapn>
          </li>
        </ul>
      </section>
      <!-- //데이터 등록현황 -->
      <hr>
      <!-- 데이터 활용현황 -->
      <section class="main-section main-section--use">
        <div class="main-section__header">
          <h3 class="main-section__title">데이터 활용 현황</h3>
          <p class="main-section__text">카테고리별 다양한데이터셋 현황</p>
        </div>
        <div class="main-section__body">
          <dl class="main-use__list">
            <dt class="main-use__title">데이터셋</dt>
            <dd class="main-use__text">데이터를 활용을 통한 <br>데이터셋 활용현황</dd>
            <dd class="main-use__number">10</dd>
          </dl>
          <dl class="main-use__list">
            <dt class="main-use__title">샌드박스</dt>
            <dd class="main-use__text">데이터를 활용을 통한 <br>샌드박스 활용현황</dd>
            <dd class="main-use__number">08</dd>
          </dl>
          <dl class="main-use__list">
            <dt class="main-use__title">데이터적재</dt>
            <dd class="main-use__text">데이터를 활용을 통한 <br>데이터적재 활용현황</dd>
            <dd class="main-use__number">20</dd>
          </dl>
        </div>
      </section>
      <!-- //데이터 활용현황 -->
      <hr>
      <!-- 데이터 카테고리 -->
      <section class="main-section main-section--category">
        <div class="main-section__header">
          <h3 class="main-section__title"><span class="hidden">데이터 </span>카테고리</h3>
          <p class="main-section__text">카테고리별 다양한 데이터셋 현황</p>
        </div>
        <div class="main-section__body">
          <!-- slider-->
          <div class="main-slider swiper-container" style="position:static;">
            <ul class="main-slider__list swiper-wrapper" >
              <li class="main-slider__item swiper-slide" v-for="data in categoryList">
                <a class="main-slider__link" :href="'/dataset/pageList.do?nav=MNU025&categoryId='+data.categoryOid">
                  <figure class="main-thumbnail">
                    <img class="main-thumbnail__image" :src="fileDownloadMain(data.categoryOid , 'T' ,'category')"  onerror="this.src='<c:url value="/smartcity/img/main/thumb_category.jpg"/>'" alt="" width="393" height="359">
                    <figcaption class="main-thumbnail__caption">
                      <h4 class="main-thumbnail__title">{{data.categoryNm}}</h4>
                      <p class="main-thumbnail__text">{{data.categoryDesc}}</p>
                      <p class="main-thumbnail__info">
                        <span class="main-thumbnail__number"><strong class="main-thumbnail__number--emphasis">{{data.datasetCount}}</strong>건 </span><span class="main-thumbnail__button">바로가기</span>
                      </p>
                    </figcaption>
                  </figure>
                </a>
              </li>
            </ul>
            <button class="main-slider__button main-slider__button--prev" type="button"><span class="hidden">이전으로</span></button>
            <button class="main-slider__button main-slider__button--next" type="button"><span class="hidden">다음으로</span></button>
          </div>
          <!-- //slider -->
          <a class="button__more" href="<c:url value="/dataset/pageList.do"/>"><span class="hidden">더 보기</span></a>
        </div>
      </section>
      <!-- //데이터 카테고리 -->
      <hr>
      <!-- 데이터 배너 -->
      <aside class="main-section main-section--banner">
        <h3 class="hidden">데이터 운영 배너</h3>
        <h4 class="main-banner__title material-icons">도시데이터를 경험할 준비가 되셨나요?</h4>
        <p class="main-banner__text">지금바로 시작하세요~!!</p>
        <a class="main-banner__link" href="#none">Go Data Travel</a>
      </aside>
      <!-- 데이터 배너 -->
      <hr>
      <!-- 인기 데이터 -->
      <section class="main-section main-section--best">
        <div class="main-section__header">
          <h3 class="main-section__title">인기데이터</h3>
        </div>
        <div class="main-section__body">
          <ul class="main-best-tab__list">
            <li class="main-best-tab__item is-active">
              <a class="main-best-tab__button" href="#none">전체</a>
              <div class="main-best-tab__panels">
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--pink">유료</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--orange">API</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--blue">파일</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--blue">파일</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--blue">파일</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--blue">파일</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
              </div>
            </li>
            <li class="main-best-tab__item">
              <a class="main-best-tab__button" href="#none">유료</a>
              <div class="main-best-tab__panels">
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--pink">유료</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--pink">유료</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--pink">유료</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--pink">유료</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--pink">유료</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--pink">유료</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
              </div>
            </li>
            <li class="main-best-tab__item">
              <a class="main-best-tab__button" href="#none">파일</a>
              <div class="main-best-tab__panels">
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--blue">파일</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--blue">파일</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--blue">파일</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--blue">파일</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--blue">파일</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--blue">파일</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
              </div>
            </li>
            <li class="main-best-tab__item">
              <a class="main-best-tab__button" href="#none">API</a>
              <div class="main-best-tab__panels">
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--orange">API</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="../smartcity/img/dummy/thumb_best-data.png" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--orange">API</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="../smartcity/img/dummy/thumb_best-data.png" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--orange">API</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="../smartcity/img/dummy/thumb_best-data.png" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--orange">API</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--orange">API</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
                <a class="main-best-tab__panel" href="#none">
                  <dl class="main-best-data">
                    <dt class="main-best-data__name"><strong class="main-best-data__name--emphasis">국토교통 </strong>지역별매출추정데이터</dt>
                    <dd class="main-best-data__label main-best-data__label--orange">API</dd>
                    <dd class="main-best-data__text">지역별매출추정데이터지역별매출추정데이터지역별</dd>
                    <dd class="main-best-data__info">2019.05.30 <span class="main-best-data__info--black">(주)엔투엠</span></dd>
                    <dd class="main-best-data__thumb"><img class="main-best-data__thumb-image" src="<c:url value="/smartcity/img/dummy/thumb_best-data.png"/>" alt="썸네일 이미지 대체 텍스트" width="55" height="55"></dd>
                  </dl>
                </a>
              </div>
            </li>
          </ul>
          <!-- [D]: MAP 영역, 임시로 넣은 이미지 태그를 삭제하고, MAP을 넣어주세요 -->
          <div class="main-best-map">
            <img src="<c:url value="/smartcity/img/dummy/img_map.png"/>" alt="">
          </div>
          <!-- //[D]: MAP 영역 -->
          <a class="button__more"><span class="hidden">더 보기</span></a>
        </div>
      </section>
      <!-- //인기 데이터 -->
      <hr>
      <!-- 알림마당 -->
      <section class="main-section main-section--etc">
        <div class="main-section__header">
          <h3 class="main-section__title">알림마당</h3>
        </div>
        <div class="main-section__body">
          <div class="main-etc-tab swiper">
            <ul class="main-etc-tab__list">
              <li class="main-etc-tab__item is-active"><a class="main-etc-tab__button" >공지사항</a>
                <ul class="main-etc-tab__panels swiper-wrapper">
                  <li class="main-etc-tab__panel swiper-slide" v-for="data in noticeList" >
                    <span class="main-etc-tab__date">{{data.noticeCreDt|mainDateYM}}<strong class="main-etc-tab__date--emphasis">{{data.noticeCreDt|mainDateD}}</strong></span>
                    <a class="main-etc-tab__text" :href="'/cusvc/notice/pageDetail.do?nav=MNU023&noticeOid='+data.noticeOid"><span class="main-etc-tab__text--new">[ {{data.noticeId}} ] </span>{{data.noticeTitle}}</a>
                  </li>
                </ul>
                <div class="main-etc-swiper__buttons">
                  <button class="main-etc-swiper__button main-etc-swiper__button--prev"><span class="hidden">이전으로</span></button>
                  <i class="main-etc-swiper__divide"></i>
                  <button class="main-etc-swiper__button main-etc-swiper__button--next"><span class="hidden">다음으로</span></button>
                </div>
              </li>
              <li class="main-etc-tab__item"><a class="main-etc-tab__button" >FAQ</a>
                <ul class="main-etc-tab__panels swiper-wrapper">
                  <li class="main-etc-tab__panel swiper-slide" v-for="data in faqList" >
                    <span class="main-etc-tab__date">{{data.faqCreDt|mainDateYM}}<strong class="main-etc-tab__date--emphasis">{{data.faqCreDt|mainDateD}}</strong></span>
                    <a class="main-etc-tab__text" :href="'/cusvc/faq/pageDetail.do?nav=MNU020&faqOid='+data.faqOid"><span class="main-etc-tab__text--new">[ {{data.faqId}} ] </span>{{data.faqTitle}}</a>
                  </li>
                </ul>
                <div class="main-etc-swiper__buttons">
                  <button class="main-etc-swiper__button main-etc-swiper__button--prev"><span class="hidden">이전으로</span></button>
                  <i class="main-etc-swiper__divide"></i>
                  <button class="main-etc-swiper__button main-etc-swiper__button--next"><span class="hidden">다음으로</span></button>
                </div>
              </li>
              <li class="main-etc-tab__item"><a class="main-etc-tab__button" >QnA</a>
                <ul class="main-etc-tab__panels swiper-wrapper">
                  <li class="main-etc-tab__panel swiper-slide" v-for="data in qnaList" >
                    <span class="main-etc-tab__date">{{data.qnaQuestionDt|mainDateYM}}<strong class="main-etc-tab__date--emphasis">{{data.qnaQuestionDt|mainDateD}}</strong></span>
                    <a class="main-etc-tab__text" :href="'/cusvc/qna/pageDetail.do?nav=MNU021&qnaOid='+data.qnaOid"><span class="main-etc-tab__text--new">[ {{data.qnaId}} ] </span>{{data.qnaTitle}}</a>
                  </li>
                </ul>
                <div class="main-etc-swiper__buttons">
                  <button class="main-etc-swiper__button main-etc-swiper__button--prev"><span class="hidden">이전으로</span></button>
                  <i class="main-etc-swiper__divide"></i>
                  <button class="main-etc-swiper__button main-etc-swiper__button--next"><span class="hidden">다음으로</span></button>
                </div>
              </li>
            </ul>
          </div>
          <div class="main-etc-guide">
            <a class="main-etc-guide__link" href="#none">
              <dl class="main-etc-guide__list">
                <dt class="main-etc-guide__name">시티허브소개<span class="main-etc-guide__name--eng">CITY HUB INFO</span></dt>
                <dd class="main-etc-guide__text">시티허브는 스마트시티에서 수집하는 데이터를 공유하고 유통할 수 있는 <span class="main-etc-guide__button">바로가기</span></dd>
              </dl>
            </a>
            <a class="main-etc-guide__link" href="#none">
              <dl class="main-etc-guide__list">
                <dt class="main-etc-guide__name">활용메뉴얼<span class="main-etc-guide__name--eng">MENUAL</span></dt>
                <dd class="main-etc-guide__text">데이터 제공, 데이터 활용 메뉴얼<span class="main-etc-guide__button">바로가기</span></dd>
              </dl>
            </a>
            <a class="main-etc-guide__link" href="#none">
              <dl class="main-etc-guide__list">
                <dt class="main-etc-guide__name">문의하기<span class="main-etc-guide__name--eng">INQUIRY</span></dt>
                <dd class="main-etc-guide__text">시스템 사용과 관련하여 무엇이든 물어보세요<span class="main-etc-guide__button">바로가기</span></dd>
              </dl>
            </a>
            <a class="main-etc-guide__link" href="#none">
              <dl class="main-etc-guide__list">
                <dt class="main-etc-guide__name">활용사례<span class="main-etc-guide__name--eng">CASE OF APPLICATION</span></dt>
                <dd class="main-etc-guide__text">시티허브를 활용한 다양한 서비스 사례를 만나보세요.<span class="main-etc-guide__button">바로가기</span></dd>
              </dl>
            </a>
          </div>
        </div>
      </section>
      
      <!-- //알림마당 -->
  
  <script>
    $(function() {
    	var jsonData = JSON.parse('${getMainData}');
        Vue.use(N2MPagination);
        vm = new Vue({
            el : '#content',
            data : function() {
                return {
                    categoryList : [],
                    noticeList : [],
                    faqList : [],
                    qnaList : [],
                    datasetCountAll : 0,
                    datasetCountApi : 0,
                    datasetCountFile : 0
                }
            },
            methods : {
            	getMainData: function(){
            		var request = $.ajax({
            			method: "GET",
            			url: SMC_CTX + "",
            			dataType: "json"
            		});
            		request.done(console.log);
        			request.fail(console.log);
            	},
                drawMainData : function() {
                	vm.datasetCountAll   = jsonData.datasetTotalCount;
                	vm.datasetCountApi   = jsonData.datasetTypeApiCount;
                	vm.datasetCountFile  = jsonData.datasetTypeFileCount;
                	vm.categoryList      = jsonData.listCategory;
                	vm.faqList           = jsonData.listFaq;
                	vm.noticeList        = jsonData.listOpernotice;
                	vm.qnaList           = jsonData.listQna;
                	
                    vm.$nextTick(function(){
                        var swiper1 = new Swiper('.main-slider', {
                        	autoplay           : {
                               delay : 2500,
                               disableOnInteraction : false,
                             },
                             grabCursor         : false,
                             loop               : true, 
                             keyboard           : {
                               enabled : true,
                             },
                             navigation         : {
                               nextEl: '.main-slider__button--next',
                               prevEl: '.main-slider__button--prev',
                             },
                             roundLengths	     : true,
                             slidesPerGroup     : 1, 
                             slidesPerView      : 1, 
                             spaceBetween       : 10, 
                             scrollbar          : {
                               draggable : false,
                             },
                             breakpointsInverse : true, 
                             breakpoints: { 
                               768: { 
                                 slidesPerView : 2, 
                               },
                               1024: {
                                 slidesPerView : 3, 
                                 spaceBetween  : 10, 
                               },
                               1260: { 
                                 slidesPerView : 3, 
                                 spaceBetween  : 41, 
                               },
                             },
                          });
                          // 메인 알림마당 슬라이더
                          var swipers = [];
                          $('.main-etc-tab__item').each(function(index, item){
                            var $this = $(this);
                            var swiper = new Swiper(this, {
                            	direction          : 'vertical',
                                grabCursor         : false,
                                initialSlide       :	0,
                                keyboard           : {
                                  enabled : true,
                                },
                                loop               : true,
                                navigation         : {
                                  nextEl : $this.find('.main-etc-swiper__button--next')[0],
                                  prevEl : $this.find('.main-etc-swiper__button--prev')[0],
                                },
                                noSwiping          : true,
                                noSwipingClass     : 'main-etc-tab__panel',
                                scrollbar          : {
                                  draggable : false,
                                },
                                roundLengths	     : true,
                                slidesPerView      : 3, 
                                slidesPerGroup     : 3,
                                spaceBetween       : 0,
                                breakpointsInverse : true, 
                                breakpoints: { 
                                  641: {
                                    direction: 'horizontal',
                                    noSwiping: false,
                                  },
                                  1260: { 
                                    direction: 'horizontal',
                                    slidesPerView: 4, 
                                    slidesPerGroup: 1,
                                  }, 
                                },
                            });
                            swipers.push(swiper);
                          });
                    });
                },
            }
        });
        vm.drawMainData();
    });
</script>
