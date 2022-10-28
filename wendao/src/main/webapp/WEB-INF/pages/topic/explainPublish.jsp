<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>解答${topic.title}</title>
    <title>${topic.title}</title>
    <link rel="stylesheet" href="${ctxPath}/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPath}/simditor/styles/simditor.css">
    <link rel="stylesheet" href="${ctxPath}/css/common.css">
    <link rel="stylesheet" href="${ctxPath}/css/topic.css">
    <jsp:include page="/WEB-INF/pages/commons/header.jsp"></jsp:include>
</head>
<body>
<%--面包屑导航--%>
<nav class="breadcrumb">
    <a class="breadcrumb-item" href="${ctxPath}/index.jsp">首页</a>
    <a class="breadcrumb-item" href="${ctxPath}/topic/detail?id=${topic.id}">${topic.title}</a>
    <a class="breadcrumb-item" href="#">发表解答</a>
</nav>
<main class="topic-detail-container ">
    <h3 class="topic-title">${topic.title}</h3>
    <div class="topic-author">${topic.author.nickname}发布于${topic.publishTime}</div>
    <div class="topic-content">${topic.content}</div>
    <c:if test="${not empty sessionScope.customer}">
        <div class="editor-container">
            <form action="${ctxPath}/explain/publish" method="post">
                <input type="hidden" name="topicId" value="${topic.id}">
                <textarea name="content" id="editor">${content}</textarea>
                <div class="row buttons">
                <span class="col-4 offset-8 row justify-content-end">
                    <button type="reset" class="col-5">重置</button>
                    <button type="submit" class="col-5 offset-1">发布</button>
                </span>
                </div>
            </form>
        </div>
    </c:if>
</main>
<script type="text/javascript" src="${ctxPath}/simditor/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${ctxPath}/simditor/scripts/module.js"></script>
<script type="text/javascript" src="${ctxPath}/simditor/scripts/uploader.js"></script>
<script type="text/javascript" src="${ctxPath}/simditor/scripts/hotkeys.js"></script>
<script type="text/javascript" src="${ctxPath}/simditor/scripts/dompurify.js"></script>
<script type="text/javascript" src="${ctxPath}/simditor/scripts/simditor.js"></script>
<script type="text/javascript" src="${ctxPath}/js/editor.js"></script>

</body>
<jsp:include page="/WEB-INF/pages/commons/footer.jsp"></jsp:include>
</html>
