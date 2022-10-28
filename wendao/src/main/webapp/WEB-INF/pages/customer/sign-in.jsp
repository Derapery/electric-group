<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
    <link rel="stylesheet" href="${ctxPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPath}/css/customer.css">
</head>
<body class="sign">
<div class="wrapper">
    <div class="logo">
        <img src="/image/logo.svg">
    </div>
    <div class="form-container">
        <p>${message}</p>
        <form action="${ctxPath}/customer/sign/in" method="post">
            <div class="form-row">
                <label for="username">登录名称</label>
                <input type="text" name="username" value="${username}" id="username" autocomplete="off">
            </div>
            <div class="form-row">
                <label for="password">登录密码</label>
                <input type="password" name="password" id="password">
            </div>
            <div class="form-row form-captcha">
                <label for="captcha">验证码</label>
                <input type="text" name="captcha" id="captcha" >
                <img src="${ctxPath}/captcha/sign/in" class="captcha-image">
            </div>
            <div class="form-buttons">
                <button type="submit">登录</button>
            </div>
        </form>
        <hr>
        <p>
            没有账号？请<a href="${ctxPath}/customer/login/up">注册</a>
        </p>
    </div>


</div>

<c:remove var="message" scope="session" />
<c:remove var="nickname" scope="session" />
<c:remove var="username" scope="session" />
<script type="text/javascript" src="${ctxPath}/js/captcha-refresh.js"></script>
<script type="text/javascript" src="${ctxPath}/js/sign-autofocus.js"></script>
</body>
</html>
