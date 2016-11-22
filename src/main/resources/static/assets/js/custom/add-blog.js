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
        $('#save-modal').modal({backdrop: 'static', keyboard: false});
        $('#mySmallModalLabel').text('保存中，请勿做其他操作！');
        $('.spinner').css('display','block');
        $('.success').css('display','none');
        $('.error').css('display','none');
        $('#btn-dismiss').attr('disabled',true);

        var waitTime = 3000;
        var startTime = new Date();

        var code;
        var message;
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
                code = data.code;
                message = data.message;
            },
            error:function (data) {

            }
        });

        //文章保存成功才上传巨幕图
        if (code === 200){
            $('#btn-upload-jb').click();
        }


        var endTime = new Date();
        var useTime = endTime.getTime()-startTime.getTime();
        console.log(endTime.getTime());
        if (useTime<waitTime){
            waitTime = waitTime - useTime;
        }else{
            waitTime = useTime;
        }

        setTimeout(function () {
            if(code == 200){
                $('#mySmallModalLabel').text('保存成功');
                $('.success').css('display','block');
            }else if(code == 400) {
                $('#mySmallModalLabel').text('保存失败');
                $('.error').css('display','block');
                $('#error-msg').text(message);
            } else{
                $('#mySmallModalLabel').text('保存失败');
                $('.error').css('display','block');
                $('#error-msg').text('未知错误');
            }

            $('.spinner').css('display','none');
            $('#btn-dismiss').attr('disabled',false);
        }, waitTime);

    });
});
