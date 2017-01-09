package com.phycholee.blog.dao;

import com.phycholee.blog.base.dao.BaseDao;
import com.phycholee.blog.model.Tag;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TagDao extends BaseDao<Tag> {

    List<Tag> findTags() throws SQLException;

    void insertArticleTag(Map<String, Object> params) throws SQLException;

    void deleteArticleTag(Integer articleId) throws SQLException;

    Integer[] findTagIdsBayArticle(Integer articleId) throws SQLException;
}