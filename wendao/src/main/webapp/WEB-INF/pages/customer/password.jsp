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
                <div class="left-zi yanse"><b>修改密码</b></div>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/customer/mine">
                    <i class="left-tu fa fa-user-circle-o" aria-hidden="true"></i>
                    个人信息
                </a>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/customer/edit">
                    <i class="left-tu fa fa-arrows" aria-hidden="true"></i>
                    修改信息
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
        <div class="left-center chang">
            <div class="bgc-tu">
                <a href="${ctxPath}/index.jsp" class="fanhui"><i class="fa fa-chevron-left" aria-hidden="true"></i>&nbsp;首页</a>
                <img class="yuan-ren rounded-circle" src="/image/ren.png" alt="">
            </div>
            <div class="left-n-kuang-n">
                <p>${message}</p>
    <c:if test="${empty type}" >
                <form class="kuan" action="${ctxPath}/customer/change" method="post">
                    <div class="form-row-m">
                        <label for="username">登录名称</label>
                        <input class="wu-border" type="text" name="username" value="${customer.username}" id="username" autocomplete="off" readonly>
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
    </c:if>
    <c:if test="${not empty type}" >
        <form class="kuan" action="${ctxPath}/manager/changePwd" method="post">
            <div class="form-row-m">
                <label for="username">登录名称</label>
                <input class="wu-border" type="text" name="username" value="${customer.username}"  autocomplete="off" readonly>
            </div>
            <div class="form-row-m">
                <label for="newPassword">新  密  码</label>
                <input type="password" name="newPassword" />
            </div>
            <div class="form-buttons-m">
                <button type="submit">确认修改</button>
            </div>
        </form>
    </c:if>
            </div>
        </div>
    </article>
</div>
<div class="kong"></div>
</body>
</html>
