<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>	
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>스마트 시티</title>
	<tiles:insertAttribute name="scripts" />
</head>
<body>
	<!-- skip-nav -->
	<div class="skip-nav">
		<ul class="skip-nav--list">
			<li class="skip-nav__item"><a class="skip-nav__link" href="#none">바로가기</a></li>
			<li class="skip-nav__item"><a class="skip-nav__link" href="#none">바로가기</a></li>
		</ul>
	</div>
	<!-- //skip-nav -->
	<hr>
	<!-- wrap -->
	<div class="wrap" id="content" v-cloak>
		<!-- header -->
		<tiles:insertAttribute name="header" />
		<!-- //header -->
		<!-- nav -->
		<tiles:insertAttribute name="nav" />
		<!-- //nav -->
		<!-- main -->
		<main class="main">
			<div class="loader__wrap" v-if="loadingFlag">
				<p class="loader__text">loading</p>
				<i class="loader"></i>
			</div>
		    <h2 class="hidden">본문</h2>
		    <tiles:insertAttribute name="content"/>
		</main>
		<hr>
		<!-- //main -->
		<!-- footer -->
		<tiles:insertAttribute name="footer" />
		<!-- //footer -->
	</div>
	<!-- //wrap -->
</body>
</html>