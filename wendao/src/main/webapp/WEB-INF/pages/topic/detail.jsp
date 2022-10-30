
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${topic.title}</title>
    <link rel="stylesheet" href="${ctxPath}/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPath}/css/common.css">
    <link rel="stylesheet" href="${ctxPath}/css/topic.css">
    <jsp:include page="/WEB-INF/pages/commons/header.jsp"></jsp:include>
</head>
<body>
<%--面包屑导航--%>
<nav class="breadcrumb">
    <a class="breadcrumb-item" href="${ctxPath}/index.jsp">首页</a>
    <a class="breadcrumb-item" href="#">${topic.title}</a>
</nav>
<main class="topic-detail-container ">
    <h3 class="topic-title">${topic.title}</h3>
    <div class="topic-author">${topic.author.nickname}发布于${topic.publishTime}</div>
    <div class="topic-content">${topic.content}</div>
    <div>
        <a href="${ctxPath}/topic/publishExplain?id=${topic.id}">我来解答</a>
    </div>
    <div class="topic-explains">
       <c:if test="${ empty topic.explains }">
        暂无解答
       </c:if>
        <c:if test="${ not empty topic.explains}">
            <c:forEach items="${topic.explains}" var="e" varStatus="x">
                <div class="explains">
                   <div class="row author-and-time">
                       <span class="col-2">${x.count}</span>
                       <span class="col-2"></span>
                       <span class="col-8">${e.content}</span>
                   </div>
                    <div class="row ">
                        <span class="col-2"></span>
                        <span class="col-8">${e.author.nickname} 解答于 ${e.publishTime}</span>
                        <span class="col-2 row">
                               <a href="#" class="praise">
                                   <i class="fa fa-thumbs-up"></i>(${e.praise})
                               </a>
                               <a href="#" class="praise">
                                   <i class="fa fa-thumbs-up"></i>(${e.despise})
                               </a>
                        </span>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
</main>
<c:remove var="message" scope="session" />
<c:remove var="content" scope="session" />
<script type="text/javascript" src="${ctxPath}/js/editor.js"></script>
</body>
<jsp:include page="/WEB-INF/pages/commons/footer.jsp"></jsp:include>
</html>
