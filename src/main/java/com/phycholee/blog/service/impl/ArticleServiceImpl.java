package com.phycholee.blog.service.impl;

import com.phycholee.blog.base.service.impl.BaseServiceImpl;
import com.phycholee.blog.dao.ArticleDao;
import com.phycholee.blog.model.Article;
import com.phycholee.blog.service.ArticleService;
import com.phycholee.blog.utils.FileUtil;
import com.phycholee.blog.utils.ParseHTMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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
     * 保存文章图片链接到数据库
     * @param article
     * @throws SQLException
     */
    @Override
    public void insertImgSrc(Article article) throws SQLException {
        String imgSrc = ParseHTMLUtil.getImgSrc(article.getHtmlContent());
        if (!"".equals(imgSrc)){
            article.setImgSrc(imgSrc);
        }

        insert(article);
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

        deleteById(id);
        //TODO 后期还会删除标签信息

        FileUtil.deleteImage(article, uploadPath);
    }
}
