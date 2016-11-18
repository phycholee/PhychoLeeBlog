package com.phycholee.blog.controller;

import com.phycholee.blog.utils.FileUtil;
import com.phycholee.blog.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by PhychoLee on 2016/11/14 10:29.
 * Description: 上传文件Controller
 */
@Controller
@RequestMapping("/upload")
public class FileUploadController {

    //获取上传的文件夹，在application.yml中的配置
    @Value("${web.upload-path}")
    private String uploadPath;

    /**
     * 上传博客图片
     * @param request
     * @return
     */
    @PostMapping(value = "/uploadPostImage")
    @ResponseBody
    public Map<String, Object> uploadPostImage(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();

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
                } catch (Exception e) {
                    e.printStackTrace();
                    resultMap.put("success", 0);
                    resultMap.put("message", "上传图片失败");
                    return resultMap;
                }
        }else {
            resultMap.put("success", 0);
            resultMap.put("message", "上传图片为空");
            return resultMap;
        }

        resultMap.put("success", 1);
        resultMap.put("message", "上传图片成功");
        //将'\'转成'/'
        createPath = createPath.replace("\\","/");
        resultMap.put("url", "/"+createPath+url);
        return resultMap;
    }


    /**
     * 上传巨幕图片
     * @param file
     * @return
     */
    @PostMapping("/uploadJumbotronImage")
    @ResponseBody
    public Map<String, Object> uploadJumbotronImage(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();

        //需要有博客存入才能写入巨幕图片
//        Integer blogId = (Integer) request.getSession().getAttribute("articleId4Jumbotron");
//        if (blogId == null){
//            resultMap.put("code", 404);
//            resultMap.put("message", "博客未创建成功");
//
//            return resultMap;
//        }

        //根据当前日期创建文件夹
        String createPath = "jumbotron" +File.separator + TimeUtil.getYearMonthDay() + File.separator;

        String path = uploadPath + File.separator + createPath;

        if(!file.isEmpty()) {

            try {
                //写入磁盘
                FileUtil.writeImage2static(path, file);
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

        resultMap.put("code", 200);
        resultMap.put("message", "上传图片成功");
        return resultMap;
    }
}
