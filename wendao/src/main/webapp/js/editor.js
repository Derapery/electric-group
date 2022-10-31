
let options = {
    // 通过CSS选择器命中表单中的<textarea>元素
    textarea: $('#editor'),
    placeholder: '请输入内容...',
    upload: {
        url: '/file/simditor/upload',
        fileKey: 'image',
        leaveConfirm: '图片正在上传中，你确定要离开当前页面吗？'
    }
}

let editor = new Simditor(options);
console.log(editor);