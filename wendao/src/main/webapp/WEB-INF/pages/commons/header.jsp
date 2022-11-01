<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="page">
    <link  rel="stylesheet" href="/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet " href="/css/customer.css">
    <div class="status">
        <div class="between">
        <ul class="nav justify-content-start">
             <li class="nav-item ">
                    <a href="${ctxPath}/index.jsp">首页</a>
             </li>
        </ul>
        <ul class="nav justify-content-end end">
            <c:choose>
                <%--若会话对象中存在customer则认为用于已经登录--%>
                <c:when test="${ not empty sessionScope.customer }">
                    <li class="nav-item">
                        <a>欢迎${customer.nickname}</a>
                    </li>
                    <li class="nav-item">
                        <a>与您同时在线用户${applicationScope.onlineCustomer}位</a>
                    </li>
                    <li class="nav-item">
                        <a href="${ctxPath}/topic/publish">发布话题</a>
                    </li>
                    <li class="nav-item">
                        <a href="${ctxPath}/customer/sign/out">退出</a>
                    </li>
                    <li class="dropdown nav-item">
                        <button type="button" class="btn btn-primary dropdown-toggle FIS" data-toggle="dropdown">
                            <i class="fa fa-user"></i>
                        </button>
                        <div class="dropdown-menu dropdown-menu-right">
                            <a class="dropdown-item" href="${ctxPath}/customer/edit">设置</a>
                            <a class="dropdown-item" href="${ctxPath}/customer/change">修改密码</a>
                            <a class="dropdown-item" href="${ctxPath}/topic/mine">我的话题</a>
                            <a class="dropdown-item" href="${ctxPath}/customer/delete">注销</a>
                        </div>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a href="${ctxPath}/customer/login/up">注册</a>
                    </li>
                    <li class="nav-item">
                        <a href="${ctxPath}/customer/sign/in">登录</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
    </div>

    </div>
</header>