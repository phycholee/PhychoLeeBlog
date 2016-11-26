package com.phycholee.blog.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * Created by PhychoLee on 2016/11/18 10:26.
 * Description: 文件写入本地磁盘（静态资源文件夹）工具类
 */
public class FileUtil {

    /**
     * 将图片写入磁盘
     * @param path
     * @param file
     * @return
     * @throws Exception
     */
    public static String writeImage2static(String path, MultipartFile file) throws Exception{
        //返回生成的文件名
        String url = "";

        //判断文件夹是否存在
        File dir = new File(path);
        if (!dir.exists()){
            dir.mkdirs();
        }

        String fileName = file.getOriginalFilename();
        //判断是否有文件且是否为图片文件
        if(fileName!=null && !"".equals(fileName.trim()) && isImageFile(fileName)) {
            //创建文件名字
            url = createFileName(fileName);
            //创建输出文件对象
            File outFile = new File(path + url);
            //拷贝文件到输出文件对象
            FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
        }

        return url;
    }


    /**
     * 判断文件是否为图片文件
     * @param fileName
     * @return
     */
    private static Boolean isImageFile(String fileName) {
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
    private static String createFileName(String fileName) {
        if(fileName!=null && fileName.indexOf(".")>=0) {
            return UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return "";
    }
}