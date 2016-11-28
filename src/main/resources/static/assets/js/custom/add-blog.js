/**
 * Created by Lee on 2016/11/16.
 */

//后台地址
// var address = 'http://127.0.0.1:8080/';
// var address = 'http://localhost:8080/';
var address = '/';

$(function() {
    // 图片上传初始化
    // var uploadImage = $("#zyupload").zyUpload({
    //     width            :   "96%",                 // 宽度
    //     height           :   "350px",                 // 宽度
    //     itemWidth        :   "140px",                 // 文件项的宽度
    //     itemHeight       :   "115px",                 // 文件项的高度
    //     url              :   address+"upload/uploadJumbotronImage",  // 上传文件的路径
    //     fileType         :   ["jpg", "jpeg", "gif", "png", "bmp", "webp"],// 上传文件的类型
    //     fileSize         :   10485760,                // 上传文件的大小
    //     multiple         :   true,                    // 是否可以多个文件上传
    //     dragDrop         :   true,                    // 是否可以拖动上传文件
    //     del              :   true,                    // 是否可以删除文件
    //     finishDel        :   false,  				  // 是否在上传文件完成后删除预览
    //     /* 外部获得的回调接口 */
    //     onSelect: function(selectFiles, allFiles){    // 选择文件的回调方法  selectFile:当前选中的文件  allFiles:还没上传的全部文件
    //         console.info("当前选择了以下文件：");
    //         console.info(selectFiles);
    //     },
    //     onDelete: function(file, files){              // 删除一个文件的回调方法 file:当前删除的文件  files:删除之后的文件
    //         console.info("当前删除了此文件：");
    //         console.info(file.name);
    //     },
    //     onSuccess: function(file, response){          // 文件上传成功的回调方法
    //         console.info("此文件上传成功：");
    //         console.info(file.name);
    //         console.info("返回信息：");
    //         console.info(response.message);
    //     },
    //     onFailure: function(file, response){          // 文件上传失败的回调方法
    //         console.info("此文件上传失败：");
    //         console.info(file.name);
    //     },
    //     onComplete: function(response){           	  // 上传完成的回调方法
    //         console.info("文件上传完成");
    //         console.info(response);
    //     }
    // });

    //编辑器初始化
    var editor = editormd("editormd", {
        width   : "100%",
        height  : 600,
        path : "../assets/editor.md/lib/",
        emoji : true,
        saveHTMLToTextarea : true,
        imageUpload : true,
        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL : address+"upload/uploadPostImage"
    });

    $('#btn-save').on('click',function () {
        //保存文章
        saveArticle(2)
    });

    $('#btn-publish').on('click',function () {
        //直接发布文章
        saveArticle(1)
    });
    
    function saveArticle(status) {
        //检查不能为空项
        if (!checkEmpty()){
            return;
        }

        //让父层也不能操作
        parent.layer.load(2, {
            shade: [0.2, '#fff']
        });

        var waitTime = 3000;
        var startTime = new Date();

        var code = 0;
        var message;

        var title1 = $('#title').val().trim();
        var subTitle = $('#sub-title').val().trim();
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
                status: status      //1表示发布，2表示只保存
            },
            success:function (data) {
                console.log(data.message);
                code = data.code;
                message = data.message;
            },
            error:function (data) {

            }
        });

        var uploadFile;
        //文章保存成功才上传巨幕图
        if (code == 200){
            console.log('上传巨幕图');
            $('#uploadBtn').click();
            uploader.on('uploadError', function (file) {
                console.log('巨幕图上传失败');
                uploadFile = file;
            });
            uploader.on( 'uploadSuccess', function( file ) {
                console.log('巨幕图上传成功');
                uploadFile = file;
            });
        }


        var endTime = new Date();
        var useTime = endTime.getTime()-startTime.getTime();
        if (useTime<waitTime){
            waitTime = waitTime - useTime;
        }else{
            waitTime = useTime;
        }

        //关闭加载
        setTimeout(function(){
            parent.layer.closeAll('loading');

            if(code == 200){
                swal({
                    title: "保存文章成功",
                    text: "",
                    type: "success"
                });

                //成功后清空，防止重复提交
                $('#title').val('');
                $('#sub-title').val('');
                editor.setMarkdown('');
                uploader.removeFile(uploadFile);
            }else if(code == 400) {
                swal({
                    title: "保存文章失败",
                    text: message,
                    type: "error"
                });
            } else{
                swal({
                    title: "保存文章失败",
                    text: "未知错误",
                    type: "error"
                });
            }


        }, waitTime);
    }

    function checkEmpty() {
        var title1 = $('#title').val().trim();
        var subTitle = $('#sub-title').val().trim();
        var markdown_content = editor.getMarkdown();      // 获取 Markdown 源码
        var html_content = editor.getHTML();

        if ('' == title1){
            showToastr('标题不能为空', '错误');
            return false;
        }else if('' == subTitle){
            showToastr('副标题不能为空', '错误');
            return false;
        }else if('' == markdown_content){
            showToastr('Markdown内容不能为空', '错误');
            return false;
        }else if('' == html_content){
            showToastr('HTML内容不能为空', '错误');
            return false;
        }

        return true;
    }

    function showToastr(title, message) {
        toastr.options = {
            closeButton: true,
            debug: false,
            progressBar: true,
            positionClass: "toast-bottom-center",
            onclick: null,
            showDuration: "400",
            hideDuration: "1000",
            timeOut: "7000",
            extendedTimeOut: "1000",
            showEasing: "swing",
            hideEasing: "linear",
            showMethod: "fadeIn",
            hideMethod: "fadeOut"
        };
        toastr.error(title, message);
    }
});
