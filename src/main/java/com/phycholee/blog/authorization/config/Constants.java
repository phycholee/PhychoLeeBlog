package com.phycholee.blog.authorization.config;

/**
 * Created by PhychoLee on 2017/4/10 22:06.
 * Description: 常量
 */
public class Constants {

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER = "CURRENT_USER";

    public static final String TOKEN = "token";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 24;

    /**
     * token有效期（分钟）
     */
    public static final int TOKEN_EXPIRES_MINUTES = 30;
}
