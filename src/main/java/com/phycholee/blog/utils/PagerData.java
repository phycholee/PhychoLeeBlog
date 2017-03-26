package com.phycholee.blog.utils;

/**
 * Created by PhychoLee on 2017/3/26 21:33.
 * Description:
 */
public class PagerData extends JsonData {

    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static PagerData page(Object data, int total){
        return new PagerData(CODE_SUCCESS, MSG_SUCCESS, data, total);
    }

    private PagerData(int code, String msg, Object data, int total) {
        super(code, msg, data);
        this.total = total;
    }

//    private PagerData(int code, String msg) {
//        super(code, msg);
//    }


}
