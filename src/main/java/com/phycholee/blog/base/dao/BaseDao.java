package com.phycholee.blog.base.dao;

import java.sql.SQLException;

/**
 * Created by PhychoLee on 2016/11/9 20:31.
 * Description: 抽取公共dao
 */
public interface BaseDao<T> {
    T findById(Integer id) throws SQLException;

    void deleteById(Integer id) throws SQLException;

    void insert(T entity) throws SQLException;

    void update(T entity) throws SQLException;
}
