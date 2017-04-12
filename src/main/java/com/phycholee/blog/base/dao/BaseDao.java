package com.phycholee.blog.base.dao;

import java.sql.SQLException;

/**
 * Created by PhychoLee on 2016/11/9 20:31.
 * Description: 抽取公共dao
 */
public interface BaseDao<T> {
    T findById(Integer id) throws SQLException;

    int deleteById(Integer id) throws SQLException;

    int insert(T entity) throws SQLException;

    int update(T entity) throws SQLException;
}
