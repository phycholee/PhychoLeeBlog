/**
 * Created by Lee on 2016/11/16.
 */

//后台地址
// var address = 'http://127.0.0.1:8080/';
// var address = 'http://localhost:8080/';
var address = '/';

$(function() {
    // 图片上传初始化
    $("#zyupload").zyUpload({
        width            :   "100%",                 // 宽度
        height           :   "400px",                 // 宽度
        itemWidth        :   "140px",                 // 文件项的宽度
        itemHeight       :   "115px",                 // 文件项的高度
        url              :   address+"upload/uploadJumbotronImage",  // 上传文件的路径
        fileType         :   ["jpg", "jpeg", "gif", "png", "bmp", "webp"],// 上传文件的类型
        fileSize         :   10485760,                // 上传文件的大小
        multiple         :   true,                    // 是否可以多个文件上传
        dragDrop         :   true,                    // 是否可以拖动上传文件
//            tailor           :   true,                    // 是否可以裁剪图片
        del              :   true,                    // 是否可以删除文件
        finishDel        :   false,  				  // 是否在上传文件完成后删除预览
        /* 外部获得的回调接口 */
        onSelect: function(selectFiles, allFiles){    // 选择文件的回调方法  selectFile:当前选中的文件  allFiles:还没上传的全部文件
            console.info("当前选择了以下文件：");
            console.info(selectFiles);
        },
        onDelete: function(file, files){              // 删除一个文件的回调方法 file:当前删除的文件  files:删除之后的文件
            console.info("当前删除了此文件：");
            console.info(file.name);
        },
        onSuccess: function(file, response){          // 文件上传成功的回调方法
            console.info("此文件上传成功：");
            console.info(file.name);
            console.info("返回信息：");
            console.info(response.message);
        },
        onFailure: function(file, response){          // 文件上传失败的回调方法
            console.info("此文件上传失败：");
            console.info(file.name);
        },
        onComplete: function(response){           	  // 上传完成的回调方法
            console.info("文件上传完成");
            console.info(response);
        }
    });

//        编辑器初始化
    var editor = editormd("editormd", {
        width   : "100%",
        height  : 600,
        path : "../assets/editor.md/lib/",
        emoji : true,
        saveHTMLToTextarea : true,
        imageUpload : true,
        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL : address+"upload/uploadPostImage"
        /*
         上传的后台只需要返回一个 JSON 数据，结构如下：
         {
         success : 0 | 1,           // 0 表示上传失败，1 表示上传成功
         message : "提示的信息，上传成功或上传失败及错误信息等。",
         url     : "图片地址"        // 上传成功时才返回
         }
         */
    });

    $('#btn-save').on('click',function () {
        var status = 2;     //表示保存，并不发布
        var title1 = $('#title').val();
        var subTitle = $('#sub-title').val();
        var markdown_content = editor.getMarkdown();       // 获取 Markdown 源码
        var html_content = editor.getHTML();           // 获取 Textarea 保存的 HTML 源码
        $.ajax({
            url:address+'article/addArticle',
            type:'post',
            async:false,
            data:{
                title: title1,
                subTitle: subTitle,
                markdownContent: markdown_content,
                htmlContent: html_content,
                status: status
            },
            success:function (data) {
                console.log(data.message);
            },
            error:function (data) {

            }
        });
    });

});
