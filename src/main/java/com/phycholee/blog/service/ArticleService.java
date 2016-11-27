package com.phycholee.blog.service;

import com.phycholee.blog.base.service.BaseService;
import com.phycholee.blog.model.Article;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by PhychoLee on 2016/11/9 21:03.
 * Description: 文章Service接口
 */
public interface ArticleService extends BaseService<Article> {

    List<Article> findByPage(Integer offset, Integer limit, Integer status) throws SQLException;

    Integer countByStatus(Integer status) throws SQLException;
}
