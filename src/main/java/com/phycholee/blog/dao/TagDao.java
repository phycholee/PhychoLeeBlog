package com.phycholee.blog.dao;

import com.phycholee.blog.base.dao.BaseDao;
import com.phycholee.blog.model.Tag;
import com.phycholee.blog.model.TagCriteria;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TagDao extends BaseDao<Tag> {

    List<Tag> findTags() throws SQLException;

    void insertArticleTag(Map<String, Object> params) throws SQLException;

    void deleteArticleTag(Integer articleId) throws SQLException;

    Integer[] findTagIdsByArticle(Integer articleId) throws SQLException;

    List<Tag> selectByCondition(TagCriteria condition);

    int countByCondition(TagCriteria condition);

}