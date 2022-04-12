<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>에러 페이지 | 스마트 시티</title>
  <link rel="stylesheet" href="<c:url value="/smartcity/css/sub.css"/>">
</head>
<body>
  <!-- error -->
  <div class="error__wrap">
    <div class="error__outer">
      <main class="error">
        <h1 class="error__title material-icons">${errorCd}</h1>
        <p class="error__text">${errorMsg}</p>
        <p class="error__company">Smart City Data Hub</p>
        <div class="error__bottom">
          <a class="button button__outline--primary" href="javascript:history.back();">이전페이지</a>
        </div>
      </main>
    </div>
  </div>
  <!-- //error -->
</body>
</html>