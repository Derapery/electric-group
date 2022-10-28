<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改密码</title>
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
        <form action="${ctxPath}/customer/change" method="post">
            <div class="form-row">
                <label for="username">登录名称</label>
                <input type="text" name="username" value="${customer.username}" id="username" autocomplete="off" readonly>
            </div>
            <div class="form-row">
                <label for="password">原始密码</label>
                <input type="password" name="password" id="password">
            </div>
            <div class="form-row">
                <label for="newPassword">新密码</label>
                <input type="password" name="newPassword" id="newPassword" >
            </div>
            <div class="form-row">
                <label for="confirm">确认密码</label>
                <input type="password" name="confirm" id="confirm" >
            </div>
            <div class="form-buttons">
                <button type="submit">确认修改</button>
            </div>
    <c:remove var="message" scope="session"/>
    <script type="text/javascript" src="${ctxPath}/js/sign-autofocus.js"></script>

</body>
</html>
