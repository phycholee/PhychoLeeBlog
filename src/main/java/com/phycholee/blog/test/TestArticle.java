package com.phycholee.blog.test;

import com.phycholee.blog.model.Article;
import com.phycholee.blog.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by PhychoLee on 2016/11/27 15:38.
 * Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestArticle {

    @Autowired
    private ArticleService articleServicel;

    @Test
    public void testFindByPage() throws SQLException {
        List<Article> byPage = articleServicel.findByPage(0, 10, 2);
        for (Article article : byPage){
            System.out.println(article);
        }
    }

    @Test
    public void testCount() throws SQLException {
        Integer status = articleServicel.countByStatus(2);
        System.out.println(status);
    }
}
