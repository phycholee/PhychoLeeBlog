package com.phycholee.blog.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PhychoLee on 2017/3/13 22:08.
 * Description: 分页包装类
 */
public class Pager<T> {

    private List<T> data = new ArrayList<T>();

    private int total = 0;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
