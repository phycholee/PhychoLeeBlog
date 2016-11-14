package com.phycholee.blog.controller;

import com.phycholee.blog.utils.TimeUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by PhychoLee on 2016/11/14 10:29.
 * Description: 上传文件Controller
 */
@RestController
public class FileUploadController {

    //获取上传的文件夹，在application.yml中的配置
    @Value("${web.upload-path}")
    private String uploadPath;

    @RequestMapping("test")
    public String testCon(){
        return "success";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        System.out.println("上传成功  "+fileName);
        return fileName;
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("/uploadImage")
    public Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file){
        System.out.println("上传文件，进来了");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String path = uploadPath + File.separator + TimeUtil.getYearMonthDay() +File.separator;

        if(!file.isEmpty()) {
                //判断文件夹是否存在
                File dir = new File(path);
                if (!dir.exists()){
                    dir.mkdirs();
                }

                try {
                    String fileName = file.getOriginalFilename();
                    //判断是否有文件且是否为图片文件
                    if(fileName!=null && !"".equals(fileName.trim()) && isImageFile(fileName)) {
                        //创建输出文件对象
                        File outFile = new File(path + createFileName(fileName));
                        //拷贝文件到输出文件对象
                        FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    resultMap.put("code", 400);
                    resultMap.put("message", "上传图片失败");
                }
        }

        resultMap.put("code",200);
        resultMap.put("message", "上传图片成功");
        return resultMap;
    }

    /**
     * 判断文件是否为图片文件
     * @param fileName
     * @return
     */
    private Boolean isImageFile(String fileName) {
        String [] img_type = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp", "webp"};
        if(fileName==null) {return false;}
        fileName = fileName.toLowerCase();
        for(String type : img_type) {
            if(fileName.endsWith(type)) {return true;}
        }
        return false;
    }

    /**
     * 创建文件名
     * @param fileName
     * @return
     */
    private String createFileName(String fileName) {
        if(fileName!=null && fileName.indexOf(".")>=0) {
            return UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return "";
    }

}
