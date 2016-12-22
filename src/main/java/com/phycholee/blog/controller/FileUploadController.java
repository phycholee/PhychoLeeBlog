package com.phycholee.blog.controller;

import com.phycholee.blog.model.Article;
import com.phycholee.blog.service.ArticleService;
import com.phycholee.blog.utils.FileUtil;
import com.phycholee.blog.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by PhychoLee on 2016/11/14 10:29.
 * Description: 上传文件Controller
 */
@Controller
@RequestMapping("/admin/upload")
public class FileUploadController {

    //获取上传的文件夹，在application.yml中的配置
    @Value("${web.upload-path}")
    private String uploadPath;

    @Autowired
    private ArticleService articleService;

    final static String rootPath = "http://localhost:8080/";

    /**
     * 上传博客图片
     * @param request
     * @return
     */
    @RequestMapping(value = "/postImage")
    public ModelAndView uploadPostImage(HttpServletRequest request, HttpServletResponse response){
        String dialog_id = request.getParameter("dialog_id");
        String callback = request.getParameter("callback");

        MultipartHttpServletRequest multipartRequest =
                (MultipartHttpServletRequest) request;
        Iterator<String> fileNames = multipartRequest.getFileNames();
        MultipartFile file = multipartRequest.getFile(fileNames.next());

        //根据当前日期创建文件夹
        String createPath = "post" + File.separator + TimeUtil.getYearMonthDay() +File.separator;

        String path = uploadPath + File.separator + createPath;

        String url;

        if(file != null && !file.isEmpty()) {
                try {
                    //写入磁盘
                    url = FileUtil.writeImage2static(path, file);

                    //将'\'转成'/'
                    createPath = createPath.replace("\\","/");

                    response.sendRedirect(callback+"?dialog_id="+dialog_id+
                        "&success=1&message=upload success&url="+rootPath+createPath+url);

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return null;
    }


    /**
     * 上传巨幕图片
     * @param file
     * @return
     */
    @PostMapping("/jumbotronImage")
    @ResponseBody
    public Map<String, Object> uploadJumbotronImage(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();

        //需要有博客存入才能写入巨幕图片
//        Integer blogId = (Integer) request.getSession().getAttribute("articleId4Jumbotron");
//        if (blogId == null){
//            resultMap.put("code", 404);
//            resultMap.put("message", "博客未创建成功");
//            return resultMap;
//        }

        //根据当前日期创建文件夹
        String createPath = "jumbotron" +File.separator + TimeUtil.getYearMonthDay() + File.separator;

        String path = uploadPath + File.separator + createPath;

        String url;

        if(!file.isEmpty()) {

            try {
                //写入磁盘
                url = FileUtil.writeImage2static(path, file);

                //将'\'转成'/'
                createPath = createPath.replace("\\","/");

                //将url存入数据库
//                Article article = new Article();
//                article.setId(blogId);
//                article.setJumbotron(rootPath+createPath+url);
//                articleService.update(article);
            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put("code", 400);
                resultMap.put("message", "上传图片失败");
                return resultMap;
            }
        }else {
            resultMap.put("code", 400);
            resultMap.put("message", "上传图片为空");
            return resultMap;
        }

        //上传成功后移除id
//        request.getSession().removeAttribute("articleId4Jumbotron");

        resultMap.put("code", 200);
        resultMap.put("message", "上传图片成功");
        //将'\'转成'/'
        createPath = createPath.replace("\\","/");
        resultMap.put("url", rootPath+createPath+url);
        return resultMap;
    }
}
