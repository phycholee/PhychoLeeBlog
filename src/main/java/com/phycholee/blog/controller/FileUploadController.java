package com.phycholee.blog.controller;

import com.phycholee.blog.service.ArticleService;
import com.phycholee.blog.utils.FileUtil;
import com.phycholee.blog.utils.PropertiesUtil;
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
@RequestMapping("")
public class FileUploadController {

    //获取上传的文件夹，在application.yml中的配置
    @Value("${web.upload-path}")
    private String uploadPath;

    @Autowired
    private ArticleService articleService;

    final static String rootPath = PropertiesUtil.getPropertyByKey("root");

    /**
     * 上传博客图片
     * @param request
     * @return
     */
    @PostMapping(value = "/postImage")
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
    @PostMapping("/admin/upload/jumbotronImage")
    @ResponseBody
    public Map<String, Object> uploadJumbotronImage(@RequestParam("file") MultipartFile file, String data){
        Map<String, Object> resultMap = new HashMap<>();

        //根据当前日期创建文件夹
        String createPath = "jumbotron" + File.separator + TimeUtil.getYearMonthDay() + File.separator;

        if(data != null && "tag".equals(data)){
            //标签巨幕图放在专属文件夹
            createPath = "jumbotron" + File.separator+"tag" + File.separator;
        }

        String path = uploadPath + File.separator + createPath;

        String url;

        if(!file.isEmpty()) {

            try {
                //写入磁盘
                url = FileUtil.writeImage2static(path, file);

                //将'\'转成'/'
                createPath = createPath.replace("\\","/");

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
        //将'\'转成'/'
        createPath = createPath.replace("\\","/");
        resultMap.put("url", rootPath+createPath+url);
        return resultMap;
    }

    /**
     * 根据url删除图片
     * @param param
     * @return
     */
    @PostMapping("/admin/upload/deleteImage")
    @ResponseBody
    public Map<String, Object> deleteImage(@RequestBody Map<String, String> param){
        Map<String, Object> resultMap = new HashMap<>();

        FileUtil.deleteImageByUrl(param.get("url"), uploadPath);

        resultMap.put("code", 200);
        resultMap.put("message", "删除成功");
        return resultMap;
    }
}
