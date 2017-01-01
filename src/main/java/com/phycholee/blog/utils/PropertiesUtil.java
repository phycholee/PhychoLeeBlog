package com.phycholee.blog.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by PhychoLee on 2017/1/1 15:10.
 * Description: 解析Properties文件
 */
public class PropertiesUtil {

    public static String getPropertyByKey(String key){
        Properties props = new Properties();

        String property = "";

        try {
            InputStream in = Object.class.getResourceAsStream("/url.properties");
            props.load(in);

            property = props.getProperty(key);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return property;
    }
}
