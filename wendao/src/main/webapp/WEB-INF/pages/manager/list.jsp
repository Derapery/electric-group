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
                <a class="left-zi" href="${ctxPath}/manager/list">
                    <i class="left-tu fa fa-external-link" aria-hidden="true"></i>
                    管理用户
                </a>
            </div>
            <div class="left-one">
                <a  class="left-zi" href="${ctxPath}/manager/topic">
                    <i class="fa fa-calendar-o left-tu" aria-hidden="true"></i>
                    管理话题
                </a>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/manager/badlog">
                    <i class=" left-tu fa fa-bars" aria-hidden="true"></i>
                    违规记录
                </a>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/customer/mine">
                    <i class="left-tu fa fa-user-circle-o" aria-hidden="true"></i>
                    操作记录
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
    <nav class="breadcrumb manager">
        <a class="breadcrumb-item" href="${ctxPath}/index.jsp">管理</a>
        <a class="breadcrumb-item" href="#">用户列表</a>
    </nav>

    <%--当前页面主要内容--%>
    <main class="topics-container manager">
    <c:if test="${not empty category}" >
        <form action="${ctxPath}/manager/topic/change/category?category_id=${category.id}" method="post">
            <div class="form-row">
                <label for="category_name">分类名称:</label>
                <input type="text" name="category_name" placeholder="${category.name}" id="category_name" >
                <button type="submit" style="margin: auto">确认修改</button>
            </div>
        </form>
    </c:if>
    <c:if test="${not empty customerMa}" >
        <form action="${ctxPath}/manager/changemanager?customer_id=${customerMa.id}" method="post">
            <div class="form-row">
                <label for="category_name">权限等级:</label>
                <input type="text" name="management" value="${customerMa.management}" placeholder="0默认，1管理员，-1禁止评论，-2禁止发布，-3禁止登录"  >
                <button type="submit" style="margin: auto">修改权限</button>
            </div>
        </form>
    </c:if>
        <p>${message}</p>
        <c:choose>
            <%--若会话对象中存在customer则认为用于已经登录--%>
            <c:when test="${ not empty requestScope.cus }">
                <div class="row title">
                    <span class="col-3">ID</span>
                    <span class="col-2">用户详情</span>
                    <span class="col-2">注册日期</span>
                    <span class="col-3">级别</span>
                    <span class="col-2">管理用户</span>
                </div>
                <c:forEach items="${paging.dataList}" var="customer" varStatus="x">
                    <div class="row topic">
                        <span class="col-3">${customer.id}</span>
                        <span class="col-2">
                            <a href="${ctxPath}/manager/customer/mine?id=${customer.id}">${customer.username}</a>
                        </span>
                        <span class="col-2">${customer.registerDate}</span>
                        <span class="col-2">
                        <c:choose>
                         <c:when test="${customer.management==1}">
                             <span>管理员</span>
                         </c:when>
                         <c:otherwise>
                            <span>用户</span>
                         </c:otherwise>
                        </c:choose>
                        </span>
                        <span class="col-3" style="font-size: 15px">
                            <a href="${ctxPath}/manager/editinfo?customer_id=${customer.id}">修改信息</a>
                            <a href="${ctxPath}/manager/changePwd?customer_id=${customer.id}">修改密码</a>
                            <a href="${ctxPath}/manager/badlogOne?customer_id=${customer.id}">不良记录</a>
                            <a href="${ctxPath}/manager/changemanager?customer_id=${customer.id}">更改权限</a>
                        </span>
                    </div>
                </c:forEach>
            </c:when>
            <c:when test="${ not empty requestScope.top }">
                <div class="row title">
                    <span class="col-2">标题</span>
                    <span class="col-1">分类</span>
                    <span class="col-2">发布时间</span>
                    <span class="col-1">浏览量</span>
                    <span class="col-3">发布者</span>
                    <span class="col-3">管理话题</span>
                </div>
                <c:forEach items="${paging.dataList}" var="topic" varStatus="x">
                    <div class="row topic">
                        <span class="col-2"><a href="${ctxPath}/topic/detail?id=${topic.id}">${topic.title}</a></span>
                        <span class="col-1">${topic.category_name}</span>
                        <span class="col-2">${topic.publishTime}</span>
                        <span class="col-1">${topic.priority}</span>
                        <span class="col-2">${topic.author.username}</span>
                        <span class="col-3" style="font-size: 15px">
                            <a href="${ctxPath}/manager/topic/change/category?id=${topic.category_id}">修改分类</a>
                            <a href="${ctxPath}/topic/publish">置顶话题</a>
                            <a href="${ctxPath}/manager/topic/edit?topic_id=${topic.id}&customer_name=${topic.author.username}">编辑内容</a>
                            <a href="${ctxPath}/manager/topic/deleteTopic?topic_id=${topic.id}&customer_name=${topic.author.username}">删除话题</a>
                        </span>
                    </div>
                </c:forEach>
            </c:when>


            <c:when test="${ not empty requestScope.badlog }">
                <div class="row title">
                    <span class="col-3">不良记录者</span>
                    <span class="col-4">不良记录类型</span>
                    <span class="col-5">管理不良记录者</span>
                </div>
                <c:forEach items="${paging.dataList}" var="badlog" varStatus="x">
                    <div class="row topic">
                        <span class="col-3"><a href="${ctxPath}/manager/customer/mine?id=${badlog.user_id}">${badlog.user_id}</a></span>
                        <span class="col-3">
                            <c:choose>
                                <c:when test="${badlog.type==1}">
                                    <span>暴力言论</span>
                                </c:when>
                                <c:otherwise>
                                    <span>敏感话题</span>
                                </c:otherwise>
                            </c:choose>
                        </span>
                        <span class="col-6" style="font-size: 15px">
                            <a class="col-3 href="${ctxPath}/manager/topic/change/category?id=${badlog.user_id}">禁言用户</a>
                            <a class="col-3 href="${ctxPath}/topic/publish">禁止登陆</a>
                            <a  class="col-3 href="${ctxPath}/manager/topic/edit?topic_id=${badlog.user_id}&customer_name=${badlog.user_id}">禁止发布</a>
                            <a  class="col-3 href="${ctxPath}/manager/topic/deleteTopic?topic_id=${badlog.user_id}&customer_name=${badlog.user_id}">删除用户</a>
                        </span>
                    </div>
                </c:forEach>
            </c:when>
        </c:choose>
        <div class=" row pagination-container">
            <div class="row col-6 justify-content-start">
                <form action="${requestScope.path}" method="GET" class="row">
                    <label for="size" class="col-4">每页行数</label>
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

    <c:remove var="message" scope="session" />
    <c:remove var="category" scope="request" />
    <c:remove var="customer" scope="request" />
    <c:remove var="customerMa" scope="request" />

</body>
<jsp:include page="/WEB-INF/pages/commons/footer.jsp"></jsp:include>

<script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>
