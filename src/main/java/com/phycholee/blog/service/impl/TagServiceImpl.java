package com.phycholee.blog.service.impl;

import com.phycholee.blog.base.service.impl.BaseServiceImpl;
import com.phycholee.blog.dao.TagDao;
import com.phycholee.blog.model.Tag;
import com.phycholee.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by PhychoLee on 2016/11/9 21:09.
 * Description: 标签Service实现类
 */
@Service
public class TagServiceImpl extends BaseServiceImpl<Tag> implements TagService {

    private TagDao tagDao;
    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
        super.setDao(tagDao);
    }
}
