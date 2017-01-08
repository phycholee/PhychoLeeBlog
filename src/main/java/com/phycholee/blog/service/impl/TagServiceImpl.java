package com.phycholee.blog.service.impl;

import com.phycholee.blog.base.service.impl.BaseServiceImpl;
import com.phycholee.blog.dao.TagDao;
import com.phycholee.blog.model.Tag;
import com.phycholee.blog.service.TagService;
import com.phycholee.blog.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by PhychoLee on 2016/11/9 21:09.
 * Description: 标签Service实现类
 */
@Service
public class TagServiceImpl extends BaseServiceImpl<Tag> implements TagService {

    //获取上传的文件夹，在application.yml中的配置
    @Value("${web.upload-path}")
    private String uploadPath;

    private TagDao tagDao;
    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
        super.setDao(tagDao);
    }

    /**
     * 查找所有标签
     * @return
     * @throws SQLException
     */
    @Override
    public List<Tag> findTags() throws SQLException {
        return tagDao.findTags();
    }

    /**
     * 更新标签，同时删除无效图片资源
     * @param tag
     * @throws SQLException
     */
    @Override
    public void updateImgsrc(Tag tag) throws SQLException {
        Tag oldTag = findById(tag.getId());
        String oldJumbotron = oldTag.getJumbotron();

        //如果更新了巨幕图，将原来的巨幕图资源删除
        if(oldJumbotron != null && !oldJumbotron.equals(tag.getJumbotron())){
            FileUtil.deleteImageByUrl(oldJumbotron, uploadPath);
        }

        update(tag);
    }
}
