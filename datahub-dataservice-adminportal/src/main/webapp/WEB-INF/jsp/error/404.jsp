<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>로그인 | Smart City Hub 관리자 페이지</title>
	<link rel="stylesheet" href="<c:url value="/admin/css/sub.css"/>">
	<script src="<c:url value="/admin/js/jquery-3.4.1.min.js"/>"></script>
	<script src="<c:url value="/admin/js/vue.js"/>"></script>
</head>
<body>
	<div class="error">
	    <div class="error__top">
	        <h2 class="error__title">페이지를 찾을 수 없습니다.</h2>
	    </div>
	    <div class="error__body">
	        <p class="error__text">방문하시려는 페이지는 권한이 있는 회원만 보실 수 있습니다.</p>
	        <p class="error__text">권한이 있는 회원이 아니시라면</p>
	        <p class="error__text">먼저 운영자에게 문의하여 사용 권한을 얻으신 후 요청하신 페이지를 보실 수 있습니다.</p>
	        <!--<a class="button__line--large" href="#none">이전페이지</a>-->
	    </div>   
	</div>
</body>
</html>