package com.phycholee.blog.service.impl;

import com.phycholee.blog.base.service.impl.BaseServiceImpl;
import com.phycholee.blog.dao.ArticleDao;
import com.phycholee.blog.model.Article;
import com.phycholee.blog.model.ArticleCriteria;
import com.phycholee.blog.service.ArticleService;
import com.phycholee.blog.service.TagService;
import com.phycholee.blog.utils.FileUtil;
import com.phycholee.blog.utils.Pager;
import com.phycholee.blog.utils.ParseHTMLUtil;
import com.phycholee.blog.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PhychoLee on 2016/11/9 21:04.
 * Description: 文章Service实现类
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {

    //获取上传的文件夹，在application.yml中的配置
    @Value("${web.upload-path}")
    private String uploadPath;

    private ArticleDao articleDao;
    @Autowired
    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
        super.setDao(articleDao);
    }

    @Autowired
    private TagService tagService;

    /**
     * 分页查找文章
     * @param offset     起始数
     * @param limit    限制数
     * @return
     * @throws SQLException
     */
    @Override
    public List<Article> findByPage(Integer offset, Integer limit, Integer status) throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("limit", limit);
        params.put("status", status);

        List<Article> articleList = articleDao.findByPage(params);

        //去掉HTML标签，并只截取180个字符
        for (Article article : articleList){
            article.setHtmlContent(ParseHTMLUtil.getText(article.getHtmlContent()));
        }

        return articleList;
    }



    @Override
    public Integer countByStatus(Integer status) throws SQLException {
        return articleDao.countByStatus(status);
    }

    /**
     * 保存文章内容,图片链接,标签
     * @param article
     * @throws SQLException
     */
    @Override
    @Transactional
    public void insertImgSrc(Article article) throws SQLException {
        String imgSrc = ParseHTMLUtil.getImgSrc(article.getHtmlContent());
        if (!"".equals(imgSrc)){
            article.setImgSrc(imgSrc);
        }

        article.setCreateTime(TimeUtil.getDateTime());

        insert(article);

        //插值进中间表
        Integer[] tagIds = article.getTagIds();
        if(tagIds != null && tagIds.length > 0){
            tagService.insertArticleTag(article.getId(), tagIds);
        }
    }


    /**
     *删除文章，并且删除文章所属的图片资源
     * @param id
     * @throws SQLException
     */
    @Override
    @Transactional
    public void deleteArticleAndResource(Integer id) throws SQLException {
        Article article = findById(id);

        //删除文章标签信息
        tagService.deleteArticleTag(id);

        deleteById(id);

        FileUtil.deleteImage(article, uploadPath);
    }

    /**
     * 更新文章，并且清除无效图片资源
     * @param article
     * @throws SQLException
     */
    @Override
    @Transactional
    public void updateImgsrc(Article article) throws SQLException {
        Article oldArticle = findById(article.getId());
        String oldJumbotron = oldArticle.getJumbotron();

        //如果更新了巨幕图，将原来的巨幕图资源删除
        if(oldJumbotron != null && !oldJumbotron.equals(article.getJumbotron())){
            FileUtil.deleteImageByUrl(oldJumbotron, uploadPath);
        }

        if (!oldArticle.getHtmlContent().equals(article.getHtmlContent())){
            String imgSrc = ParseHTMLUtil.getImgSrc(article.getHtmlContent());
            if (!"".equals(imgSrc)){
                article.setImgSrc(imgSrc);

                //清除无效图片资源
                FileUtil.deleteInvalidImgSrc(oldArticle.getImgSrc(), imgSrc, uploadPath);
            }
        }

        //如果文章状态改变，将重设时间
        if(!oldArticle.getStatus().equals(article.getStatus())){
            article.setCreateTime(TimeUtil.getDateTime());
        }

        update(article);

        //修改中间表值, 先全部删除，再重新插入
        Integer[] tagIds = article.getTagIds();
        tagService.deleteArticleTag(article.getId());
        if(tagIds != null && tagIds.length > 0){
            tagService.insertArticleTag(article.getId(), tagIds);
        }
    }

    /**
     * 条件查询
     * @param params
     * @return
     */
    @Override
    public Pager findArticlesByCondition(Map<String, Object> params) {

        Pager<Article> pager = new Pager<>();

        int offset = params.get("offset") == null ? -1 : (StringUtils.isEmpty(params.get("offset").toString()) ? -1 : Integer.parseInt(params.get("offset").toString()));
        int limit = params.get("limit") == null ? -1 : (StringUtils.isEmpty(params.get("limit").toString()) ? -1 : Integer.parseInt(params.get("limit").toString()));

        ArticleCriteria articleCriteria = new ArticleCriteria();
        ArticleCriteria.Criteria criteria = articleCriteria.createCriteria();
        setCriteria(criteria, params);

        articleCriteria.setLimitStart(offset);
        articleCriteria.setLimitEnd(limit);

        return pager;
    }

    private void setCriteria(ArticleCriteria.Criteria criteria, Map<String, Object> params) {
        String title = params.get("title") == null ? "" : params.get("title").toString();
        String subTitle = params.get("subTitle") == null ? "" : params.get("subTitle").toString();
        int status = params.get("status") == null ? -1 : (StringUtils.isEmpty(params.get("status").toString()) ? -1 : Integer.parseInt(params.get("status").toString()));

        if (!StringUtils.isEmpty(title))
            criteria.andTitleEqualTo(title);
        if (!StringUtils.isEmpty(subTitle))
            criteria.andSubTitleEqualTo(subTitle);
        if (status > -1)
            criteria.andStatusEqualTo(status);

    }
}
