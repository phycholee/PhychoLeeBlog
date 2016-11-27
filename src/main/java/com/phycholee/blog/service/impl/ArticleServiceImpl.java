package com.phycholee.blog.service.impl;

import com.phycholee.blog.base.service.impl.BaseServiceImpl;
import com.phycholee.blog.dao.ArticleDao;
import com.phycholee.blog.model.Article;
import com.phycholee.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private ArticleDao articleDao;
    @Autowired
    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
        super.setDao(articleDao);
    }

    /**
     * 分页查找文章
     * @param start     起始数
     * @param offset    限制数
     * @return
     * @throws SQLException
     */
    @Override
    public List<Article> findByPage(Integer offset, Integer limit, Integer status) throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("limit", limit);
        params.put("status", status);

        return articleDao.findByPage(params);
    }

    @Override
    public Integer countByStatus(Integer status) throws SQLException {
        return articleDao.countByStatus(status);
    }
}
