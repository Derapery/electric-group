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
    <link rel="stylesheet" href="${ctxPath}/css/round.css">
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
            <c:forEach items="${paging.dataList}" var="topic" varStatus="x">
                <div class="topic-all">
                    <div class="topic-list-detail-head">
                        <div>
                            <img class="topic-head-img rounded-circle" src="/image/ren.png" alt="">
                            <span class="icon-wrap" title="问道官方认证">
                            <img class="topic-anquan-img" src="/image/anquan.png" alt="">
                        </span>
                        </div>
                        <a class="athor-name" href="${ctxPath}/customer/mine?id=${topic.author.id}">${topic.author.nickname}</a>
                        <span class="athor-where">发布于${topic.publishTime}</span>
                        <c:if test="${topic.author.concern ==0}">
                            <a class="guanzhu :hover"  href="${ctxPath}/customer/fansAction?customer_id=${topic.author.id}&concern=0&size=${paging.size}&current=${paging.current}">+关注</a>
                        </c:if>
                        <c:if test="${topic.author.concern ==1}">
                            <a class="guanzhu :hover"  href="${ctxPath}/customer/fansAction?customer_id=${topic.author.id}&concern=1&size=${paging.size}&current=${paging.current}">-取消关注</a>
                        </c:if>
                    </div>
                    <div class="content-a">
                        <a href="${ctxPath}/topic/detail?id=${topic.id}">
                            <div class="title-content">
                                <h3 class="topic-detail-title">${topic.title}</h3>
                            </div>
                        </a>
                        <div class="topic-content">${topic.content}</div>
                        <div class="category-content">
                            <h6 class="topic-detail-category">#${topic.category_name}</h6>
                        </div>
                    </div>

                    <div class="footerr">
                        <span class="col-6  row justify-content-end">
                            <c:if test="${ topic.state==null}">
                            <span class="col-2 justify-content-start">
                             <a href="${ctxPath}/topic/thumbsState?praise=1&size=${paging.size}&current=${paging.current} &topic_id=${topic.id}" class="praise">
                                       <i class="fa fa-thumbs-o-up"></i>(${topic.thumbUpCount})
                            </a>
                             </span>
                                <span  class="col-2 offset-3">
                             <a href="${ctxPath}/topic/thumbsState?praise=0&size=${paging.size}&current=${paging.current} &topic_id=${topic.id}" class="praise">
                                   <i class="fa fa-thumbs-o-down"></i>(${topic.thumbDownCount})
                              </a>
                              </span>
                            </c:if>
                         <c:if test="${ topic.state == 1}">
                             <span class="col-2 justify-content-start">
                                <a href="${ctxPath}/topic/thumbsState?praise=1&size=${paging.size}&current=${paging.current} &topic_id=${topic.id}" class="praise">
                                   <i class="fa fa-thumbs-up"></i>(${topic.thumbUpCount})
                               </a>
                              </span>
                             <span class="col-2 offset-3">
                                  <a href="#" class="praise" style="user-select: none">
                                     <i class="fa fa-thumbs-o-down "></i>(${topic.thumbDownCount})
                                  </a>
                             </span>
                         </c:if>
                         <c:if test="${ topic.state == 0}">
                                <span class="col-2 justify-content-start">
                             <a href="#" class="praise disabled" style="user-select: none">
                                    <i class="fa fa-thumbs-o-up"></i>(${topic.thumbUpCount})
                            </a>
                               </span>
                             <span class="col-2 offset-3">
                                    <a href="${ctxPath}/topic/thumbsState?praise=0&size=${paging.size}&current=${paging.current} &topic_id=${topic.id}" class="praise">
                                       <i class="fa fa-thumbs-down "></i>(${topic.thumbDownCount})
                                    </a>
                               </span>
                         </c:if>
                           <span class="col-2 offset-1 ">
                                <i class="fa fa-eye"> </i>${topic.priority}
                            </span>
                            <c:if test="${topic.author.id == customer.id}" >
                                <span class="col-3 offset-1">
                                <a href="${ctxPath}/topic/delete?topicId=${topic.id}&size=${paging.size}&current=${paging.current} ">删除话题</a>
                                </span>
                            </c:if>
                        </span>
                    </div>
                </div>
            </c:forEach>
        </div>
    </article>


</div>
<div class="kong"></div>

<jsp:include page="/WEB-INF/pages/commons/footer.jsp"></jsp:include>
</body>

</html>
