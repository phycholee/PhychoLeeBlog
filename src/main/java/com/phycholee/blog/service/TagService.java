package com.phycholee.blog.service;

import com.phycholee.blog.base.service.BaseService;
import com.phycholee.blog.model.Tag;
import com.phycholee.blog.utils.Pager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by PhychoLee on 2016/11/9 21:08.
 * Description: 标签Service接口
 */
public interface TagService extends BaseService<Tag> {

    List<Tag> findTags() throws SQLException;

    void updateImgsrc(Tag tag) throws SQLException;

    void deleteImgsrc(Integer id) throws SQLException;

    void insertArticleTag(Integer articleId, Integer[] tagIds) throws SQLException;

    void deleteArticleTag(Integer articleId) throws SQLException;

    List<Tag> findTagsBayArticle(Integer articleId) throws SQLException;

    Pager findTagsByCondition(Map<String, Object> params);

    int countByCondition(Map<String, Object> params);

}
