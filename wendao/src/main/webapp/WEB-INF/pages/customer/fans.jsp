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
                <div class="ni-cheng">
                    <span>${customer.nickname}</span>
                </div>
            </div>


            <div class="topics-container">
                <div class="row title">
                    <span class="col-1">序号</span>
                    <span class="col-4">粉丝昵称</span>
                    <span class="col-4">注册时间</span>
                    <span class="col-3">性别</span>
                </div>
                <c:forEach items="${customer.fans}" var="customer" varStatus="x">
                    <div class="row title neirong">
                        <span class="col-1">${x.count}</span>
                        <span class="col-4">${customer.nickname}</span>
                        <span class="col-4">${customer.registerDate}</span>
                        <span class="col-3">${customer.gender}</span>
                    </div>
                </c:forEach>
                <c:forEach items="${pagination.dataList}" var="t" varStatus="x">
                    <div class="row topic">
                        <span class="col-1">${(pagination.current - 1 ) * pagination.size + x.count }</span>
                        <span class="col-4">
                            <a href="${ctxPath}/topic/detail?id=${t.id}">${t.title}</a>
                        </span>
                        <span class="col-4">${t.publishTime}</span>
                        <span class="col-3">${t.author.nickname}</span>
                    </div>
                </c:forEach>
                <div class="row pagination-container">
                    <div class="col-6">
                        每页显示
                        <a href="${requestScope.path}?size=3">3行</a>
                        <a href="${requestScope.path}?size=5">5行</a>
                        <a href="${requestScope.path}?size=10">10行</a>
                    </div>
                    <ul class="col-6 pagination pagination-sm justify-content-center">
                        <li class="page-item ${ pagination.current == 1 ? 'disabled' : '' }">
                            <a class="page-link" href="${requestScope.path}?current=${pagination.current-1}">上一页</a>
                        </li>
                        <c:forEach var="n" begin="1" step="1" end="${pagination.total}">
                            <li class="page-item ${ pagination.current == n ? 'active' : '' }">
                                <a class="page-link" href="${requestScope.path}?current=${n}">${n}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item ${ pagination.current == pagination.total ? 'disabled' : '' }">
                            <a class="page-link" href="${requestScope.path}?current=${pagination.current+1}">下一页</a>
                        </li>
                    </ul>
                </div>
            </div>


        </div>
    </article>


</div>
<div class="kong"></div>
</body>
</html>
