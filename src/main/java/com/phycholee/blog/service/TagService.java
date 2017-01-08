package com.phycholee.blog.service;

import com.phycholee.blog.base.service.BaseService;
import com.phycholee.blog.model.Tag;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by PhychoLee on 2016/11/9 21:08.
 * Description: 标签Service接口
 */
public interface TagService extends BaseService<Tag> {

    List<Tag> findTags() throws SQLException;

    void updateImgsrc(Tag tag) throws SQLException;

}
