<%@ page import="com.kaifamiao.wendao.entity.Customer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>


<head>
    <title>个人主页</title>
    <link  rel="stylesheet" href="/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPath}/css/common.css">
    <link rel="stylesheet" href="/css/aside.css">
</head>
<body>
<jsp:include page="/WEB-INF/pages/commons/search.jsp"></jsp:include>

<div class="big">
    <aside class="left-aside">
        <div class="left">
            <div class="left-one">
                <div class="left-zi"><b>个人主页</b></div>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/customer/edit">
                    <i class="left-tu fa fa-arrows" aria-hidden="true"></i>
                    修改昵称
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
                <div class="ni-cheng">${customer.nickname}</div>
            </div>
            <%  Customer c =(Customer) request.getSession().getAttribute("customer");%>
            <div class="guan-zhu">粉丝:<%=c.getFans().size()%>个</div>
            <div class="jie-shao"> 简介:${customer.introduction} </div>
        </div>
    </article>


</div>
<div class="kong"></div>

<jsp:include page="/WEB-INF/pages/commons/footer.jsp"></jsp:include>
</body>

</html>
