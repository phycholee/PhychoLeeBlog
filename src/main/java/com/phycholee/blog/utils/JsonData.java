package com.phycholee.blog.utils;

/**
 * Created by PhychoLee on 2017/3/24 22:36.
 * Description: 返回json工具类
 */
public class JsonData {

    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 400;

    public static final String MSG_SUCCESS = "请求成功！";
    public static final String MSG_ERROR = "系统错误！";

    private int code;

    private String msg;

    private Object data;

    public static JsonData success(Object data){
        return new JsonData(CODE_SUCCESS, MSG_SUCCESS, data);
    }

    public static JsonData error(){
        return new JsonData(CODE_ERROR, MSG_ERROR);
    }

    public static JsonData generate(int code, String msg, Object data){
        return new JsonData(code, msg, data);
    }

    public static JsonData badParameter(String msg){
        return new JsonData(CODE_ERROR, msg);
    }

    private JsonData(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private JsonData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
