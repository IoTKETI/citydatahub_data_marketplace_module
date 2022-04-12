<%@page import="kr.co.smartcity.admin.common.entity.UserInfo"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>  
<html lang="ko">
<head>
<meta charset="UTF-8" />
    <title><tiles:getAsString name="title"/></title>
	<tiles:insertAttribute name="scripts" />
</head>
<% UserInfo user = (UserInfo)session.getAttribute("user"); %>

<body>
	  <!-- wrap -->
	  <div class="wrap">
	    <!-- aside -->
	    <div class="aside">
	      <div class="aside__header">
	        <h1 class="aside__logo"><a href="<c:url value="/pageIndex.do"/>"><span class="hidden">Smart City Hub</span></a></h1>
	      </div>
	      <div class="aside__content">
	        <h2 class="hidden">주메뉴</h2>
	        <div class="aside__user">
	          <p class="aside__user-message"><strong class="aside__user-message--strong">${sessionScope.user.userNm} 님</strong> 진심으로 환영합니다.</p>
	        </div>
	        <!-- nav -->
	        <tiles:insertAttribute name="nav" />
	        <!-- //nav -->
	      </div>
	    </div>
	    <!-- //aside -->
	    <hr>
	    <!-- main -->
	    <main class="main">
	      <h2 class="hidden">본문</h2>
	      <header class="header">
	        <div class="header__util">
	          <button class="button__nav--toggle" type="button"><i class="nav__icon"><span class="hidden">주메뉴 확장/축소 하기</span></i></button>
	          <span class="header__user">${sessionScope.user.userNm} 님</span>
	          <button id="logout" class="header__logout">로그아웃</button> 
	        </div>
	        <div class="breadcrumb">
	          <tiles:insertAttribute name="header" />
	        </div>
	      </header>
	      <div class="content">
	        <!-- content -->
	        <tiles:insertAttribute name="content" />
	        <!-- //content -->
	        <!-- footer -->
	        <tiles:insertAttribute name="footer" />
	        <!-- //footer -->
	      </div>
	    </main>
	    <!-- //main -->
	  </div>
	  <!-- //wrap -->
	  <hr>
	<script>
	    $(function() {
	        $("#logout").click(function(){
	        	var request = $.ajax({
                    url : N2M_CTX + "/login/logout.do",
                    method : "GET",
                    contentType : "application/text"
                });
                request.done(function(data) {
                    window.location.href = N2M_CTX + '/login/oauthlogin.do';
                });
	        });
	    });
	</script>
</body>
</html>