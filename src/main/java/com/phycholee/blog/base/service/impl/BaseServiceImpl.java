package com.phycholee.blog.base.service.impl;

import com.phycholee.blog.base.dao.BaseDao;
import com.phycholee.blog.base.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * Created by PhychoLee on 2016/11/9 20:54.
 * Description: 抽取公共Service实现类
 */
public class BaseServiceImpl<T> implements BaseService<T> {
    private BaseDao<T> dao;
    public BaseDao<T> getDao() {
        return dao;
    }
    /**
     * 注入Dao
     * @param dao
     */
    public void setDao(BaseDao<T> dao) {
        this.dao = dao;
    }


    @Override
    public T findById(Integer id) throws SQLException {
        return dao.findById(id);
    }

    @Transactional
    public int deleteById(Integer id) throws SQLException {
        return  dao.deleteById(id);
    }

    @Transactional
    public int insert(T entity) throws SQLException {
        return dao.insert(entity);
    }

    @Transactional
    public int update(T entity) throws SQLException {
        return dao.update(entity);
    }
}
