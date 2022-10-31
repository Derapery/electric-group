<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<link>
<html>
<head>
    <title>首页</title>
    <link rel="stylesheet" href="${ctxPath}/css/search.css">
    <link rel="stylesheet" href="${ctxPath}/font-awesome/css/font-awesome.min.css">
</head>
<body>
<header class="header-wendao">

    <div>

        <div class="search-wrapper">

            <video  src="/image/earth.mp4" width="1920" height="330" controls autoplay="autoplay" loop muted></video>

            <div class="search-dtwo">
                <div>
                    <div class="search-dthree">
                        <img src="/image/logo.png">
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
                <div style="margin-top: 340px; height: 4000px; background: red ">

                </div>
            </div>
        </div>
    </div>>
</header>


</body>
</html>

