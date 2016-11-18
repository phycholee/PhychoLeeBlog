package com.phycholee.blog.controller;

import com.phycholee.blog.model.Article;
import com.phycholee.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PhychoLee on 2016/11/16 18:18.
 * Description: 文章Controller
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 保存文章
     * @param article
     * @return
     */
    @PostMapping("/addArticle")
    public Map<String, Object> addArticle(Article article, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();

        try {
            articleService.insert(article);

            //将id放进session中，给后面保存巨幕图用
            Integer id = article.getId();
            request.getSession().setAttribute("articleId4Jumbotron", id);
        } catch (SQLException e) {
            e.printStackTrace();
            resultMap.put("code", 400);
            resultMap.put("message", "保存文章失败");
            return resultMap;
        }

        resultMap.put("code", 200);
        resultMap.put("message", "保存文章成功");
        return resultMap;
    }

}
