package com.phycholee.blog.dao;

import com.phycholee.blog.base.dao.BaseDao;
import com.phycholee.blog.model.Article;
import com.phycholee.blog.model.ArticleCriteria;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ArticleDao extends BaseDao<Article> {

    //分页查找文章集合
    List<Article> findByPage(Map<String, Object> params) throws SQLException;

    //根据status获取总数
    Integer countByStatus(Integer status) throws SQLException;

    Article findById(Integer id) throws SQLException;

    List<Article> selectByCondition(ArticleCriteria criteria);

    int countByCondition(ArticleCriteria criteria);

}