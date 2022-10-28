
let options = {
    // 通过CSS选择器命中表单中的<textarea>元素
    textarea: $('#editor'),
    placeholder: '请输入内容...'
}

let editor = new Simditor(options);
console.log(editor);