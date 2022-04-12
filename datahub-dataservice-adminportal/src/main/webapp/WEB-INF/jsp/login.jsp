<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>로그인 | Smart City Hub 관리자 페이지</title>
	<link rel="stylesheet" href="<c:url value="/admin/css/login.css"/>">
	<script src="<c:url value="/admin/js/jquery-3.4.1.min.js"/>"></script>
	<script src="<c:url value="/admin/js/vue.js"/>"></script>
</head>
<body>
	<div class="login__wrap" id="wrap">
		<main class="login">
			<h1 class="login__title"><span class="hidden">Smart Hub Data City Login</span></h1>
			<div class="login__form">
					<fieldset>
						<legend>로그인 정보 입력</legend>
						<div class="login__row">
							<label for="loginId" class="login__label">ID</label>
							<input type="text" v-model="user.userId" class="input__login" autocapitalize="off" autocomplete="off" autofocus required placeholder="아이디">
						</div>
						<div class="login__row">
							<label for="loginPw" class="login__label">PASSWORD</label>
							<input type="password" v-model="user.userPw" @keypress.prevent.enter="login" class="input__login" autocapitalize="off" autocomplete="off" required placeholder="비밀번호" >
						</div>
						<div class="login__row">
							<button type="button" class="button__primary button__login" @click.prevent="login">Login</button>
							<p class="login__text">회원가입은 시스템 운영처에 문의하세요.</p>
						</div>
					</fieldset>
			</div>
		</main>
	</div>
	<script>
	var N2M_CTX = "${pageContext.request.contextPath}";
	$(function(){
		vm = new Vue({
			el: '#wrap',
			data: function(){
				return {
					user: {}
				}
			},
			methods: {
				login: function(){
					var request = $.ajax({
						method: "get",
						url: N2M_CTX + "/login/login.do",
						dataType: "json",
						data: vm.user
					});

					request.done(function(data){
						var resCd = data.resCd;
						if(resCd == "100"){
							setCookie("chaut", data.authToken, 1);
							location.href = N2M_CTX + "/pageIndex.do";
						}else if(resCd == "101"){
							alert("해당 사용자가 존재하지 않거나 비밀번호가 틀립니다.");
						}else if(resCd == "102"){
							alert("해당 사용자가 존재하지 않거나 비밀번호가 틀립니다.");
						}else if(resCd == "103"){
							alert("토큰 서버에서 오류가 발생하였습니다.");
						} 
					});

					request.fail(function(){
						alert("서버와의 연결이 끊어졌습니다.");
					});
				}
			}
		});
	});

	function setCookie(name,value,days) {
	    var expires = "";
	    if (days) {
	        var date = new Date();
	        date.setTime(date.getTime() + (days*24*60*60*1000));
	        expires = "; expires=" + date.toUTCString();
	    }
	    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
	}
	function getCookie(name) {
	    var nameEQ = name + "=";
	    var ca = document.cookie.split(';');
	    for(var i=0;i < ca.length;i++) {
	        var c = ca[i];
	        while (c.charAt(0)==' ') c = c.substring(1,c.length);
	        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	    }
	    return null;
	}
	</script>
</body>
</html>