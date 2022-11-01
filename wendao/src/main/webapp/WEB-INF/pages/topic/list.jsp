<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>话题列表</title>
    <link rel="stylesheet" href="${ctxPath}/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPath}/css/common.css">
    <link rel="stylesheet" href="${ctxPath}/css/topic.css">
    <link rel="stylesheet" href="${ctxPath}/css/search.css">
    <link rel="stylesheet" href="${ctxPath}/css/right.css">
    <link rel="stylesheet" href="${ctxPath}/css/round.css">
    <meta charset="UTF-8">
</head>

<body>
    <jsp:include page="/WEB-INF/pages/commons/header.jsp"></jsp:include>
    <%--面包屑导航--%>
    <header class="header-wendao">
            <div class="search-wrapper">
                <video src="/image/earth.mp4" width="1520" height="330" STYLE="user-select: none" autoplay="autoplay" loop muted ></video>
                <div class="search-dtwo">
                    <div>
                        <div class="search-dthree">
                            <img src="/image/LOGO.png">
                        </div>
                        <div class="search-dfour">
                            <div class="search-dfive">
                                <div>
                                    <i class="fa fa-search search-icon"></i>
                                </div>
                            </div>
                            <div class="search-dan">
                                <form action="/topic/search" method="post">
                                    <input type="text" style="width: 540px ;outline:none;
                                      height: 46px;margin: 0px;padding: 0px;border: none" name="keyworkd" placeholder="请输入话题搜索关键字" >
                                    <button class="search-button" type="submit">搜索一下</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </header>
    <div>
        <div class="left-aside">
            <aside id="left-aside" class="left-aside">
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
        </div>
        <div class="right-aside">
            <jsp:include page="/WEB-INF/pages/commons/right.jsp"></jsp:include>
        </div>
        <div class="list-conter">

        <nav class="breadcrumb">
            <a class="breadcrumb-item" href="${ctxPath}/index.jsp">首页</a>
            <a class="breadcrumb-item" href="#">话题列表</a>
        </nav>

        <%--当前页面主要内容--%>
        <main class="topics-container">
            <c:forEach items="${paging.dataList}" var="topic" varStatus="x">
                <div class="topic-all">
                    <div class="topic-list-detail-head">
                        <img class="topic-head-img rounded-circle" src="/image/ren.png" alt="">
                        <a class="athor-name" href="${ctxPath}/customer?id=${topic.author.id}">${topic.author.nickname}</a>
                        <span class="athor-where">发布于${topic.publishTime}</span>
                    </div>
                    <h4 class="topic-detail-category">#${topic.category_name}</h4>
                    <h3 class="topic-detail-title">${topic.title}</h3>
                    <div class="topic-content">${topic.content}</div>
                    <div>
                        <a href="${ctxPath}/topic/publishExplain?id=${topic.id}">我来解答</a>
                    </div>
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
    </div>
</body>
<jsp:include page="/WEB-INF/pages/commons/footer.jsp"></jsp:include>
<script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>
