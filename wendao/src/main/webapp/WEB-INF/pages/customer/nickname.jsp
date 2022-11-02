<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>个人主页</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <link  rel="stylesheet" href="/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/aside.css">
</head>
<body>
<jsp:include page="/WEB-INF/pages/commons/search.jsp"></jsp:include>
<div class="big">
    <aside class="left-aside">
        <div class="left">
            <div class="left-one">
                <div class="left-zi"><b>修改昵称、简介</b></div>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/customer/mine">
                    <i class="left-tu fa fa-user-circle-o" aria-hidden="true"></i>
                    个人信息
                </a>
            </div>
            <div class="left-one">
                <a  class="left-zi" href="${ctxPath}/customer/change">
                    <i class="fa fa fa-arrows-alt" aria-hidden="true"></i>
                    修改密码
                </a>
            </div>
            <div class="left-one">
                <div class="container">
                    <!-- 按钮：用于打开模态框 -->
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                        注销用户
                    </button>
                    <!-- 模态框 -->
                    <div class="modal fade" id="myModal">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <!-- 模态框头部 -->
                                <div class="modal-header">
                                    <h4 class="modal-title">模态框头部</h4>
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                </div>
                                <!-- 模态框主体 -->
                                <div class="modal-body">
                                    模态框内容..
                                </div>
                                <!-- 模态框底部 -->
                                <div class="modal-footer" style="background: #545b62">
                                    <a  href="${ctxPath}/customer/delete">
                                        <i class=" left-tu fa fa-window-close-o" aria-hidden="true">
                                        </i>确定注销
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </aside>
    <article>
        <div class="left-center">
            <div class="bgc-tu">
                <a href="${ctxPath}/index.jsp" class="fanhui"><i class="fa fa-chevron-left" aria-hidden="true"></i>&nbsp;首页</a>
                <img class="yuan-ren rounded-circle" src="/image/ren.png" alt="">
                <div class="ni-cheng">用户昵称</div>
            </div>
        <div class="left-n-kuang">
            <form action="${ctxPath}/customer/change/nickname" method="post">
                <div class="form-row">
                    <label for="nickname">昵称:</label>
                    <input type="text" name="nickname" value="${customer.nickname}" id="nickname" >
                </div>
                <div class="form-row">
                    <label for="nickname">简介:</label>
                    <input type="text" name="introduction" value="${customer.introduction}" id="nicknames" >
                </div>
                <div class="form-buttons">
                    <button type="submit">确认修改</button>
                </div>
            </form>
        </div>
        </div>
    </article>
</div>
<div class="kong"></div>
</body>
</html>
