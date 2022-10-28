<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>编辑用户</title>
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
        <form action="${ctxPath}/customer/edit" method="post">
            <div class="form-row">
                <label for="username">登录名称</label>
                <input type="text" name="username" value="${customer.username}" id="username" autocomplete="off" readonly>
            </div>
            <div class="form-row">
                <label for="nickname">昵称</label>
                <input type="text" name="nickname" value="${pageContext.request.getParameter("nickname")}" id="nickname">
            </div>
            <div class="form-buttons">
                <button type="submit">确认修改</button>
            </div>
            <c:remove var="message" scope="session"/>
            <c:remove var="nickname" scope="request"/>
            <script type="text/javascript" src="${ctxPath}/js/sign-autofocus.js"></script>

</body>
</html>
