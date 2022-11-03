//每次刷新页面设置隐藏
document.getElementById("point").style.display="none"


var a = document.body.scrollHeight;
window.addEventListener("scroll", function(event) {
    var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
    if(scrollTop > (500)){

        // 距离顶部高度大于100显示回到顶部按钮
        document.getElementById("point").style.display=""
    }
    if (scrollTop < (500)){

        // 距离顶部高度小于100隐藏回到顶部按钮
        document.getElementById("point").style.display="none"
    }
});
