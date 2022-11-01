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
            <aside >
                    <div class="right-one">
                        <div style="width: 100%;height: 90px">
                            图片
                        </div>
                        <div class="right-zi">
                            <button style="margin: 0 auto;display: block">
                                请<a style="color: #ffffff" href="${ctxPath}/customer/sign/in">登录</a>
                            </button>
                            <div style="width: 150px;margin:0 auto;">
                                没有账号？请<a href="${ctxPath}/customer/sign/up">注册</a>
                            </div>
                        </div>
                    </div>
                    <div class="right-two">
                        <div class="right-zi1">
                            <div style="float: left;margin-left: 18px">热度榜单</div>
                            <div style="float: right;margin-right: 18px">
                                <a class="a1" href="baidu.com"><i class="fa fa-refresh" aria-hidden="true"></i>点击刷新</a>
                            </div>
                        </div>
                        <div class="right-zi">
                            <div class="num" style="width: 24px;margin-left: 10px;    font-size: 16px; font-weight: 700;color: #f26d5f; line-height: 38px;">
                                1
                            </div>
                            <div style="font-size: 12px; line-height: 38px;">
                                什么是红旗精神
                            </div>
                            <div style="flex: 1;margin-left: 10px;color:#939393;font-size: 12px; line-height: 38px; ">
                                320.6万
                            </div>

                            <div style="width: 24px;margin-right: 10px;color: #ffffff;font-size: 12px;margin-top: 10px">
                                <div style="border: 1px solid  #ff9406;border-radius: 4px;width: 16px;height: 16px;background: #ff9406;line-height: 16px;margin: 0 auto">
                                    <div style="margin: 0 2px">热</div>
                                </div>
                            </div>

                        </div>
                        <div class="right-zi">
                            <div class="num">2</div>
                        </div>
                        <div class="right-zi">
                            <div class="num">3</div>
                        </div>
                        <div class="right-zi">
                            <div class="num">4</div>
                        </div>
                        <div class="right-zi">
                            <div class="num">5</div>
                        </div>
                        <div class="right-zi">
                            <div class="num">6</div>
                        </div>
                        <div class="right-zi">
                            <div class="num">7</div>
                        </div>
                        <div class="right-zi">
                            <div class="num">8</div>
                        </div>
                        <div class="right-zi">
                            <div class="num">9</div>
                        </div>
                        <div class="right-zi">
                            <div class="num">10</div>
                        </div>
                        <div class="right-zi2">
                            <a href="baidu.com" class="a2">查看完整榜单</a>
                        </div>
                    </div>

            </aside>
        </div>
        <div class="list-conter">

        <nav class="breadcrumb ">
            <a class="breadcrumb-item" href="${ctxPath}/index.jsp">首页</a>
            <a class="breadcrumb-item" href="#">话题列表</a>
        </nav>
        <%--当前页面主要内容--%>
        <main class="topics-container">
            <c:forEach items="${paging.dataList}" var="topic" varStatus="x">
                <div class="topic-all">
                    <div class="topic-list-detail-head">
                        <div>
                            <img class="topic-head-img rounded-circle" src="/image/ren.png" alt="">
                            <span class="icon-wrap" title="问道官方认证">
                            <img class="topic-anquan-img" src="/image/anquan.png" alt="">
                        </span>
                        </div>
                        <a class="athor-name" href="${ctxPath}/customer?id=${topic.author.id}">${topic.author.nickname}</a>
                        <span class="athor-where">发布于${topic.publishTime}</span>
                        <a class="guanzhu :hover"  href="#">+关注</a>
                    </div>
                    <div class="title-content">
                        <h3 class="topic-detail-title">${topic.title}</h3>
                    </div>
                    <div class="topic-content">${topic.content}</div>
                    <div class="category-content">
                        <h6 class="topic-detail-category">#${topic.category_name}</h6>
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
                           <span class="col-2 offset-3 ">
                                <i class="fa fa-eye"> </i>${topic.priority}
                            </span>
                        </span>
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
