package com.phycholee.blog.controller;

import com.phycholee.blog.model.Article;
import com.phycholee.blog.service.ArticleService;
import com.phycholee.blog.utils.JsonData;
import com.phycholee.blog.utils.Pager;
import com.phycholee.blog.utils.PagerData;
import com.phycholee.blog.utils.ParseHTMLUtil;
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
    public JsonData getArticle(@PathVariable("id") Integer id){

        try {
            Article article = articleService.findById(id);
            if(Article.STATUS_SAVE.equals(article.getStatus())){
                return JsonData.badParameter("查找错误！");
            }
            //不需要md文本
            article.setMarkdownContent(null);



            return JsonData.success(article);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error();
        }
    }

    /**
     * 条件查询文章
     * @return
     */
    @SuppressWarnings("Duplicates")
    @PostMapping("/articles")
    public JsonData getArticleByPage(@RequestBody Map<String, Object> params){
        Integer page = params.get("page") == null ? -1 : (StringUtils.isEmpty(params.get("page").toString()) ? -1 : Integer.parseInt(params.get("page").toString()));
        Integer limit = params.get("limit") == null ? -1 : (StringUtils.isEmpty(params.get("limit").toString()) ? -1 : Integer.parseInt(params.get("limit").toString()));
        Integer tagId = params.get("tagId") == null ? -1 : (StringUtils.isEmpty(params.get("tagId").toString()) ? -1 : Integer.parseInt(params.get("tagId").toString()));
        Integer status = Article.STATUS_PUBLISHED;

        Map<String, Object> params2 = new HashMap<>();
        if (page > -1 && limit > -1){
            int offset = (page - 1) * limit;
            params2.put("offset", offset);
            params2.put("limit", limit);
        }

        params2.put("status", status);
        if (tagId > -1){
            params2.put("tagId", tagId);
        }

        try {
            Pager pager = articleService.findArticlesByCondition(params2);

            List<Article> articles = pager.getData();
            for (Article article : articles) {
                article.setHtmlContent(ParseHTMLUtil.getText(article.getHtmlContent()));
            }

            return PagerData.page(pager.getData(), pager.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
            return PagerData.error();
        }
    }

}