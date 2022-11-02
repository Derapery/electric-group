<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>发布新话题</title>
    <link rel="stylesheet" href="${ctxPath}/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPath}/simditor/styles/simditor.css">
    <link rel="stylesheet" href="${ctxPath}/css/right.css">
    <link rel="stylesheet" href="${ctxPath}/css/common.css">
    <link rel="stylesheet" href="${ctxPath}/css/topic.css">
    <link rel="stylesheet" href="${ctxPath}/css/search.css">
</head>
<body>
<div style="user-select: none">
    <jsp:include page="/WEB-INF/pages/commons/search.jsp"></jsp:include>
</div>

<%--面包屑导航--%>
<%--中间发布话题内容--%>
    <nav class="breadcrumb chi-cun">
        <a class="breadcrumb-item" href="${ctxPath}/index.jsp">首页</a>
        <a class="breadcrumb-item" href="#">发布新话题</a>
    </nav>
    <p class="message">${message}</p>
    <%--当前页面主要内容--%>
    <main class="editor-container">
        <form action="${ctxPath}/topic/publish" method="post">
            <p class="topic-title">
                <input type="text" name="title" value="${title}" placeholder="请输入标题"/>
                <div class="input-button">
                    请选择您的分类
                    <div class="btn-group">
                        <c:forEach items="${categoryList}" var="cate" varStatus="x">
                            <button type="submit" id="category" name="category" value="${cate.id}" class="btn btn-primary">${cate.name}</button>
                        </c:forEach>
                    </div>
                    <div>
                        <input hidden class="category-input" type="text" name="categoryID" value="${category.id}" />
                    </div>
                </div>
            </p>
            <textarea class="chi-cun" name="content" id="editor">${content}</textarea>
            <%--在Bootstrap中.row表示一行--%>
            <div class="row buttons">
            <span class="col-4 offset-8 row justify-content-end">
                <button type="reset" class="col-5" >重置</button>
                <button type="submit" class="col-5 offset-1" >发布</button>
            </span>
            </div>
        </form>
    </main>



<c:remove var="message" scope="session"/>
<c:remove var="title" scope="session"/>
<c:remove var="content" scope="session"/>
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
