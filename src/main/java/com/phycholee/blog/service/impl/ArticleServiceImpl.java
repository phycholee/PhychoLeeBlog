package com.phycholee.blog.service.impl;

import com.phycholee.blog.base.service.impl.BaseServiceImpl;
import com.phycholee.blog.dao.ArticleDao;
import com.phycholee.blog.model.Article;
import com.phycholee.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
