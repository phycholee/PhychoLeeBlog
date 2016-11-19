package com.phycholee.blog.test;

import com.phycholee.blog.utils.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import java.io.File;

/**
 * Created by PhychoLee on 2016/11/14 10:38.
 * Description: 测试上传文件到本地磁盘
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFileUpload {

    @Value("${web.upload-path}")
    private String path;

    @Test
    public void uploadTest() throws Exception {
        File f = new File("E:/avatar.jpg");
        FileCopyUtils.copy(f, new File(path+"/1.jpg"));
    }

    @Test
    public void listFilesTest() {
        File file = new File(path);
        for(File f : file.listFiles()) {
            System.out.println("fileName : "+f.getName());
        }
    }

    @Test
    public void testTime(){
        String yearMonthDay = TimeUtil.getDateTime();
        System.out.println(yearMonthDay);
    }

    @Test
    public void testString(){
        String a = "\\";
        a = a.replace("\\", "/");
        System.out.println(a);
    }
}
