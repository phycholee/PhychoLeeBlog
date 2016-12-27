package com.phycholee.blog.controller;

import com.phycholee.blog.model.Article;
import com.phycholee.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PhychoLee on 2016/12/10 11:29.
 * Description: 此api供前端博客使用
 */

@RestController
@RequestMapping("/")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/article/{id}")
    public Map<String, Object> getArticle(@PathVariable("id") Integer id){
        Map<String, Object> resultMap = new HashMap<>();

        Article article = null;
        try {
            article = articleService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 200);
            resultMap.put("message", "查找失败");
        }

        resultMap.put("code", 200);
        resultMap.put("article", article);
        return resultMap;
    }

    /**
     * 分页查询文章
     * @return
     */
    @SuppressWarnings("Duplicates")
    @PostMapping("/articles")
    public Map<String, Object> getArticleByPage(@RequestBody Map<String, Object> params){
        Integer offset = (Integer) params.get("offset");
        Integer limit = (Integer) params.get("limit");
        Integer status = (Integer) params.get("status");

        Map<String, Object> resultMap = new HashMap<>();

        Integer count = 0;
        List<Article> articleList = null;
        try {
            count = articleService.countByStatus(status);
            if (count != null && !count.equals(0)){
                articleList = articleService.findByPage(offset, limit, status);
            }
            System.out.println(offset+limit+status+"总数："+count);

        } catch (SQLException e) {
            e.printStackTrace();
            resultMap.put("code", 400);
            resultMap.put("message", "查找出错");
        }

        resultMap.put("code", 200);
        resultMap.put("total", count);
        resultMap.put("rows", articleList);
        return resultMap;
    }
}
