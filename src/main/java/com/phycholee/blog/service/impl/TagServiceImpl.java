package com.phycholee.blog.service.impl;

import com.phycholee.blog.base.service.impl.BaseServiceImpl;
import com.phycholee.blog.dao.TagDao;
import com.phycholee.blog.model.Tag;
import com.phycholee.blog.model.TagCriteria;
import com.phycholee.blog.service.TagService;
import com.phycholee.blog.utils.FileUtil;
import com.phycholee.blog.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 条件查询
     * @param params
     * @return
     */
    @Override
    public Pager findTagsByCondition(Map<String, Object> params){
        Pager<Tag> pager = new Pager<>();

        TagCriteria tagCriteria = new TagCriteria();
        TagCriteria.Criteria criteria = tagCriteria.createCriteria();

        setCriteria(criteria, params);

        pager.setData(tagDao.selectByCondition(tagCriteria));
        pager.setTotal(tagDao.countByCondition(tagCriteria));

        return pager;
    }

    private void setCriteria(TagCriteria.Criteria criteria, Map<String, Object> params) {
        String name = params.get("name") == null ? "" : params.get("name").toString();
        if (!StringUtils.isEmpty(name)){
            criteria.andNameEqualTo(name);
        }

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

    /**
     * 删除标签和巨幕图
     * @param id
     * @throws SQLException
     */
    @Override
    public void deleteImgsrc(Integer id) throws SQLException {
        Tag tag = findById(id);

        //TODO 后期需要判断此标签下是否有文章，如有，将不能删除

        String jumbotron = tag.getJumbotron();

        //删除巨幕图
        if(jumbotron != null){
            FileUtil.deleteImageByUrl(jumbotron, uploadPath);
        }

        deleteById(id);
    }

    /**
     * 插入文章标签中间表
     * @param articleId
     * @param tagIds
     * @throws SQLException
     */
    @Override
    public void insertArticleTag(Integer articleId, Integer[] tagIds) throws SQLException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("articleId", articleId);
        params.put("tagIds", tagIds);

        tagDao.insertArticleTag(params);
    }

    /**
     * 删除文章所有标签
     * @param articleId
     * @throws SQLException
     */
    @Override
    public void deleteArticleTag(Integer articleId) throws SQLException {
        tagDao.deleteArticleTag(articleId);
    }

    /**
     * 查找文章的标签id
     * @param articleId
     * @return
     * @throws SQLException
     */
    @Override
    public List<Tag> findTagsBayArticle(Integer articleId) throws SQLException {
        return tagDao.findTagsByArticle(articleId);
    }


}
