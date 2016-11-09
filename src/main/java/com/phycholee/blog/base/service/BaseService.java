package com.phycholee.blog.base.service;

import java.sql.SQLException;

/**
 * Created by PhychoLee on 2016/11/9 20:54.
 * Description: 抽取公共Service接口
 */
public interface BaseService<T> {
    T findById(Integer id) throws SQLException;

    void deleteById(Integer id) throws SQLException;

    void insert(T entity) throws SQLException;

    void update(T entity) throws SQLException;
}
