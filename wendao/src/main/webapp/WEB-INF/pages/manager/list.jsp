<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>话题列表</title>
    <link rel="stylesheet" href="${ctxPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPath}/css/common.css">
    <link rel="stylesheet" href="${ctxPath}/css/topic.css">

</head>

<body>
    <jsp:include page="/WEB-INF/pages/commons/header.jsp"></jsp:include>
    <%--面包屑导航--%>
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
    <div class="list-conter">
    <nav class="breadcrumb">
        <a class="breadcrumb-item" href="${ctxPath}/index.jsp">管理</a>
        <a class="breadcrumb-item" href="#">用户列表</a>
    </nav>

    <%--当前页面主要内容--%>
    <main class="topics-container">
        <div class="row title">
            <span class="col-1">ID</span>
            <span class="col-4">用户名</span>
            <span class="col-2">注册日期</span>
            <span class="col-3">级别</span>
            <span class="col-2">发布话题数</span>
        </div>
        <c:forEach items="${paging.dataList}" var="topic" varStatus="x">
            <div class="row topic">
                <span class="col-1">${paging.begin+x.count}</span>
                <span class="col-4">
                <a href="${ctxPath}/topic/detail?id=${topic.id}">${topic.title}</a>
            </span>
                <span class="col-2">${topic.category_name}</span>
                <span class="col-3">${topic.publishTime}</span>
                <span class="col-2">${topic.author.nickname}</span>
            </div>
        </c:forEach>
        <div class=" row pagination-container">
            <div class="row col-6 justify-content-start">
                <form action="${requestScope.path}" method="GET" class="row">
                    <label for="size" class="col-4">每页话题数</label>
                    <input name="size" id="size" class="col-4">
                    <button class="col-3">确认</button>
                </form>
            </div>
            <ul class="col-6 pagination pagination-sm justify-content-center">
                <li class="page-item ${paging.current == 1?'disabled':'' }">
                    <a class="page-link" href="${requestScope.path}?current=${paging.current-1}">上一页</a>
                </li>
                <c:forEach var="n" begin="1" step="1" end="${paging.total}">
                  <li class="page-item" ${paging.current==n? 'active':''}>
                      <a class="page-link" href="${requestScope.path}?current=${n}">${n}</a>
                  </li>
                </c:forEach>
                <li class="page-item ${paging.current == paging.total? 'disabled':'' }">
                    <a class="page-link" href="${requestScope.path}?current=${paging.current+1}">下一页</a>
                </li>

            </ul>
        </div>
    </main>
    </div>



</body>
<jsp:include page="/WEB-INF/pages/commons/footer.jsp"></jsp:include>

<script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>
