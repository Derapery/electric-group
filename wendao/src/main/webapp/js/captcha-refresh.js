// 选择页面上匹配于指定选择器的首个元素
// 即选择页面上所有img中class属性值包含captcha-image的首个img元素
const captchaImage = document.querySelector("img.captcha-image");

// 为 captchaImage 元素设置 属性(attribute)
captchaImage.setAttribute('title', '看不清楚？点击刷新' );

function handleClick(){
    console.log(captchaImage);
    // 获得原来的 src 属性值
    let src = captchaImage.getAttribute('src');
    let index = src.indexOf('?');
    if( index != -1 ) {
        src = src.substring(0, index);
    }
    src = src + "?time=" + Date.now();
    // 重新设置 src 属性值
    captchaImage.setAttribute("src", src );
}

// 为 captchaImage 绑定 鼠标左键单击事件(click) 对应的 监听器(handleClick)
captchaImage.addEventListener("click", handleClick, false);