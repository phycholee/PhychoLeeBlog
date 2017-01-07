package com.phycholee.blog.dao;

import com.phycholee.blog.base.dao.BaseDao;
import com.phycholee.blog.model.Tag;

import java.sql.SQLException;
import java.util.List;

public interface TagDao extends BaseDao<Tag> {

    List<Tag> findTags() throws SQLException;
}