<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<header class="header">
  <h1 class="header-logo"><a class="header-logo__link" href="<c:url value='/main/pageMain.do'/>"><img src="<c:url value="/smartcity/img/common/logo.png" />" style="width: 250px; height:auto;" alt="시티 허브"></a></h1>
  <form class="header-search">
    <fieldset>
      <legend>검색 영역</legend>
      <div class="header-search__field">
        <input class="header-search__input" type="search" placeholder="검색어를 입력하세요" v-model="totalSch" @keypress.enter.prevent="goTotalSearchList" @keyup="totalSchKeyUp">
        <button class="header-search__submit material-icons" type="button" @click="goTotalSearchList"><span class="hidden">검색</span></button>
        <button class="header-search__close" type="button"><span class="hidden">검색창 닫기</span></button>
      </div>
    </fieldset>
  </form>

    <c:if test="${empty sessionScope.user }">
     <ul class="header-util__list">
        <li class="header-util__item"><a class="header-util__link header-util__link--login material-icons" href="<c:url value='/login/oauthlogin.do'/>">로그인</a></li>
<!--         <li class="header-util__item"><a class="header-util__link header-util__link--signup material-icons" href="#none">회원가입</a></li> -->
      </ul>
	</c:if>
	<c:if test="${not empty sessionScope.user }">
	<ul class="header-util__list">
        <li class="header-util__item"><a class="header-util__link header-util__link--user material-icons" href="#none"><strong class="header-util__link--emphasis">${sessionScope.user.nickname}</strong> 님</a></li>
        <li class="header-util__item"><a class="header-util__link header-util__link--mypage material-icons" href="<c:url value='${sessionScope.mypageUrl}'/>">마이페이지</a></li>
        <li class="header-util__item"><button class="header-util__link header-util__link--logout material-icons" @click="logout">로그아웃</button></li>
      </ul>
    <!--<button class="all-menu" type="button"><i class="all-menu__icon"><span class="hidden">전체 메뉴 보기</span></i></button>-->
	</c:if>
    <!-- <li class="header-util__item"><a class="header-util__link material-icons" href="#none">회원가입</a></li> -->
  
  <button class="all-menu" type="button"><i class="all-menu__icon"><span class="hidden">전체 메뉴 보기</span></i></button>
</header>
<hr>