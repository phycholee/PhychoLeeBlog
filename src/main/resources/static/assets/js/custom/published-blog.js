/**
 * Created by Lee on 2016/11/27.
 */
// $('#published-table').bootstrapTable({
//     url:'/article/getArticleByPage',
//     method:'post',
//     sidePagination:'server',
//     pagination:true,
//     pageNumber:1,
//     pageSize:10,
//     pageList:[20,40,60,80],
//     striped: true,
//     queryParams:function queryParams(params) {
//         //已发布
//         params.status = 1;
//         return params;
//     },
//
//     queryParamsType:'limit',
//     columns:[{
//         field: 'id',
//         visible:false
//     },{
//         field:'title',
//         // title:'标题',
//         width:200
//     },{
//         field:'subTitle',
//         width:200
//     }
//     ]
// });

$(function () {
    //初始化数据
    getBlogs(0, 10);
    
});
function getBlogs(offset, limit) {
    $.ajax({
        url:'/article/getArticleByPage',
        type:'post',
        data:{
            offset: offset,
            limit: limit,
            status: 1
        },
        success:function (data) {
            if (data.code == 200){
                addBlogTable(data.rows)
            }
        },
        error:function (data) {

        }
    });
}

function addBlogTable(blogList) {
    $.each(blogList, function(i, item) {
        $('#blog-table').append(
            '<tr class="row">' +
            '<td class="blog-status col-md-1"> ' +
            '<span class="label label-success">已发布</span> ' +
            '</td> ' +
            '<td class="blog-title col-md-5"> ' +
            '<a href="#">'+item.title+'</a> ' +
            '<br> ' +
            '<small>'+item.subTitle+'</small> ' +
            '</td> ' +
            '<td class="blog-time col-md-2"> ' +
            '<small>创建时间：</small> ' +
            '<br> ' +
            '<small class="create-time">'+item.createTime+'</small> ' +
            '</td> ' +
            '<td class="blog-author col-md-2"> ' +
            '<a href="#" title="作者"> ' +
            '<img alt="image" class="img-circle" src="../assets/img/avatar.jpg"> ' +
            '<span>PhychoLee</span> ' +
            '</a> ' +
            '</td> ' +
            '<td class="blog-actions col-md-2"> ' +
            '<a href="#" class="btn btn-white btn-sm"><i class="fa fa-folder"></i> 查看 </a> ' +
            '<a href="#" class="btn btn-white btn-sm"><i class="fa fa-pencil"></i> 编辑 </a> ' +
            '</td> ' +
            '</tr>'
        )
    });
}