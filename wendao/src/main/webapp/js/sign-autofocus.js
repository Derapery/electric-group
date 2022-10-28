
// 选择页面上匹配于指定选择器的所有元素
const inputs = document.querySelectorAll(".form-row>input");

function handler(event){
    // 获得事件源(即鼠标悬浮事件发生在那个元素上就返回那个元素)
    let t = event.target;
    console.log(t);
    // 调用函数让输入框自动获得焦点
    t.focus();
}

// 采用遍历数组的方式处理 inputs 对象
for( let i = 0; i < inputs.length; i++ ){
    let input = inputs[ i ];
    input.addEventListener("mouseover", handler, true);
}