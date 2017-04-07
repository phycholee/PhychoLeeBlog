package com.phycholee.blog.authorization.model;

/**
 * Created by PhychoLee on 2017/4/7 22:31.
 * Description: Token实体类
 */
public class TokenModel {

    private Integer userId;

    private String token;

    public TokenModel(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
