package com.phycholee.blog.controller;

import com.phycholee.blog.model.Article;
import com.phycholee.blog.service.ArticleService;
import com.phycholee.blog.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    @SuppressWarnings("Duplicates")
    @GetMapping("/article/{id}")
    public Map<String, Object> getArticle(@PathVariable("id") Integer id){
        Map<String, Object> resultMap = new HashMap<>();

        Article article = null;
        try {
            article = articleService.findById(id);
            if(Article.STATUS_SAVE.equals(article.getStatus())){
                //不允许查找未发布的文章
                throw new RuntimeException();
            }
            //不需要md文本
            article.setMarkdownContent(null);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 400);
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
        Integer offset = params.get("offset") == null ? -1 : (StringUtils.isEmpty(params.get("offset").toString()) ? -1 : Integer.parseInt(params.get("offset").toString()));
        Integer limit = params.get("limit") == null ? -1 : (StringUtils.isEmpty(params.get("limit").toString()) ? -1 : Integer.parseInt(params.get("limit").toString()));
        Integer status = Article.STATUS_PUBLISHED;

        Map<String, Object> resultMap = new HashMap<>();

        try {
            Map<String, Object> params2 = new HashMap<>();
            params2.put("offset", offset);
            params2.put("limit", limit);
            params2.put("status", status);
            Pager pager = articleService.findArticlesByCondition(params2);

            resultMap.put("code", 200);
            resultMap.put("rows", pager.getData());
            resultMap.put("total", pager.getTotal());
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 400);
            resultMap.put("message", "查找出错");
            return resultMap;
        }
    }
}
