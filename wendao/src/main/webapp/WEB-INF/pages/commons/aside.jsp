<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>话题列表</title>
    <link rel="stylesheet" href="${ctxPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPath}/css/common.css">
    <link rel="stylesheet" href="${ctxPath}/css/topic.css">

</head>
<body class="aside">
    <aside>
        <link  rel="stylesheet" href="/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" href="/css/aside.css">
        <div class="left">
            <div class="left-one">
                <div class="left-zi">在线人数${onlineCount}</div>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/topic/publish">
                <i class="left-tu fa fa-external-link" aria-hidden="true"></i>
                 发布话题
                </a>
            </div>
            <div class="left-one">
                <a  class="left-zi" href="${ctxPath}/topic/mine">
                    <i class="fa fa-calendar-o left-tu" aria-hidden="true"></i>
                    我的话题
                </a>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/explain/mine">
                    <i class=" left-tu fa fa-bars" aria-hidden="true"></i>
                    我的评论
                </a>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/customer/mine">
                      <i class="left-tu fa fa-user-circle-o" aria-hidden="true"></i>
                    个人信息
                </a>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/customer/sign/out">
                    <i class="left-tu fa fa-user-times" aria-hidden="true"></i>
                    退出
                </a>
            </div>
        </div>
    </aside>
</body>



