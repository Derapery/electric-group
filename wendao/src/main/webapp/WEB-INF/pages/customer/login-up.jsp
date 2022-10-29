<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新用户注册</title>
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
            <form action="${ctxPath}/customer/login/up" method="post">
                <div class="form-row">
                    <label for="nickname">昵称</label>
                    <input type="text" name="nickname" value="${nickname}" id="nickname" autocomplete="off">
                </div>
                <div class="form-row">
                    <label for="username">登录名称</label>
                    <input type="text" name="username" value="${username}" id="username" autocomplete="off">
                </div>
                <div class="form-row">
                    <label for="password">登录密码</label>
                    <input type="password" name="password" id="password">
                </div>
                <div class="form-row">
                    <label for="confirm">确认密码</label>
                    <input type="password" name="confirm" id="confirm" >
                </div>
                <div >
                    性别：
                    <input type="radio" name="gender"  checked value="女">女
                    <input type="radio" name="gender" id="gender" value="男">男
                </div>
                <div class="form-row form-captcha">
                    <label for="captcha">验证码</label>
                    <input type="text" name="captcha" id="captcha" >
                    <img src="${ctxPath}/captcha/sign/up" class="captcha-image">
                </div>
                <div class="form-buttons">
                    <button type="submit">注册</button>
                </div>
            </form>
            <hr>
            <p>
                已有账号？请<a href="${ctxPath}/customer/sign/in">登录</a>
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
