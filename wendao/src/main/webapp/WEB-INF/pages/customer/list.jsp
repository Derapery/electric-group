<%@ page import="com.kaifamiao.wendao.entity.Customer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>


<head>
    <title>个人主页</title>
    <link  rel="stylesheet" href="${ctxPath}/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPath}/css/common.css">
    <link rel="stylesheet" href="${ctxPath}/css/aside.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div style="user-select: none">
    <jsp:include page="/WEB-INF/pages/commons/search.jsp"></jsp:include>
</div>

<div class="big">
    <aside class="left-aside">
        <div class="left">
            <div class="left-one">
                <div class="left-zi"><b>个人主页</b></div>
            </div>
            <div class="left-one">
                <a class="left-zi" href="${ctxPath}/customer/edit">
                    <i class="left-tu fa fa-arrows" aria-hidden="true"></i>
                    修改信息
                </a>
            </div>
            <div class="left-one">
                <a  class="left-zi" href="${ctxPath}/customer/change">
                    <i class="fa fa fa-arrows-alt" aria-hidden="true"></i>
                    修改密码
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
        <div class="left-center">
            <div class="bgc-tu">
                <a href="${ctxPath}/index.jsp" class="fanhui"><i class="fa fa-chevron-left" aria-hidden="true"></i>&nbsp;首页</a>
                <img class="yuan-ren rounded-circle" src="/image/ren.png" alt="">
                <div class="ni-cheng">${customer.nickname}</div>
            </div>
            <%  Customer c =(Customer) request.getAttribute("customer");%>
            <div class="LIYAN">
                <div class="yan-fans"><a href="${ctxPath}/topic/like">我的喜欢:<%=c.getFans().size()%>个</a></div>
                <div class="yan-fans"><a href="${ctxPath}/customer/fans">粉丝:<%=c.getFans().size()%>个</a></div>
                <div class="yan-fans"><a href="${ctxPath}/customer/concern">关注:<%=c.getAttention().size()%>个</a></div>

            </div>
            <div class="jie-shao"> 简介:${customer.introduction} </div>
        </div>
    </article>


</div>
<div class="kong"></div>

<jsp:include page="/WEB-INF/pages/commons/footer.jsp"></jsp:include>
</body>

</html>
