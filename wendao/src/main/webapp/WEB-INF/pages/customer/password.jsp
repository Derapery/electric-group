<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>个人主页</title>
    <link  rel="stylesheet" href="/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/aside.css">
</head>
<body>
<jsp:include page="/WEB-INF/pages/commons/search.jsp"></jsp:include>

<div class="big">
    <aside>
        <div class="left">
            <div class="left-one">
                <div class="left-zi"><b>修改密码</b></div>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/customer/list">
                    <i class="left-tu fa fa-user-circle-o" aria-hidden="true"></i>
                    个人信息
                </a>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/customer/change/nickname">
                    <i class="left-tu fa fa-arrows" aria-hidden="true"></i>
                    修改昵称
                </a>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/customer/delete">
                    <i class=" left-tu fa fa-window-close-o" aria-hidden="true"></i>
                    注销用户
                </a>
            </div>
        </div>
    </aside>
    <article>
        <div class="left-center">
            <div class="bgc-tu">
                <a href="${ctxPath}/index.jsp" class="fanhui"><i class="fa fa-chevron-left" aria-hidden="true"></i>&nbsp;首页</a>
                <img class="yuan-ren rounded-circle" src="/image/ren.png" alt="">
            </div>
            <div class="left-n-kuang-n">
                <p>${message}</p>
                <form action="${ctxPath}/customer/change" method="post">
                    <div class="form-row-m">
                        <label for="username">登录名称</label>
                        <input type="text" name="username" value="${customer.username}" id="username" autocomplete="off" readonly>
                    </div>
                    <div class="form-row-m">
                        <label for="password">原始密码</label>
                        <input type="password" name="password" id="password">
                    </div>
                    <div class="form-row-m">
                        <label for="newPassword">新  密 码</label>
                        <input type="password" name="newPassword" id="newPassword" >
                    </div>
                    <div class="form-row-m">
                        <label for="confirm">确认密码</label>
                        <input type="password" name="confirm" id="confirm" >
                    </div>
                    <div class="form-buttons-m">
                        <button type="submit">确认修改</button>
                    </div>
                </form>
            </div>
        </div>
    </article>
    <div class="right-n">

    </div>

</div>
</body>
</html>
