package com.phycholee.blog.controller;

import com.phycholee.blog.model.Article;
import com.phycholee.blog.service.ArticleService;
import com.phycholee.blog.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PhychoLee on 2016/11/16 18:18.
 * Description: 此api共后台管理网页使用，需要权限验证
 */
@RestController
@RequestMapping("/admin/")
public class AdminArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * 分页查询文章
     * @return
     */
    @SuppressWarnings("Duplicates")
    @PostMapping("articles")
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

    /**
     * 获取文章详细信息
     * @param id
     * @return
     */
    @SuppressWarnings("Duplicates")
    @GetMapping("/article/{id}")
    public Map<String, Object> getArticle(@PathVariable("id") Integer id){
        Map<String, Object> resultMap = new HashMap<>();

        Article article = null;
        try {
            article = articleService.findById(id);
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
     * 保存文章
     * @param article
     * @return
     */
    @SuppressWarnings("Duplicates")
    @PostMapping("/article")
    public Map<String, Object> addArticle(@RequestBody Article article){
        Map<String, Object> resultMap = new HashMap<>();

        String errorMessage = "错误：保存文章失败";
        try {
            //检验字段
            if (article.getTitle() == null || "".equals(article.getTitle())){
                errorMessage = "错误：标题不能为空";
                throw new RuntimeException(errorMessage);
            }else if (article.getSubTitle() == null || "".equals(article.getSubTitle())){
                errorMessage = "错误：副标题不能为空";
                throw new RuntimeException(errorMessage);
            }else if (article.getMarkdownContent() == null || "".equals(article.getMarkdownContent())){
                errorMessage = "错误：MD内容不能为空";
                throw new RuntimeException(errorMessage);
            }else if (article.getHtmlContent() == null || "".equals(article.getHtmlContent())){
                errorMessage = "错误：HTML内容不能为空";
                throw new RuntimeException(errorMessage);
            }

            article.setCreateTime(TimeUtil.getDateTime());

            articleService.insertImgSrc(article);

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 400);
            resultMap.put("message", errorMessage);
            return resultMap;
        }

        resultMap.put("code", 200);
        resultMap.put("message", "成功：保存文章成功");
        return resultMap;
    }

    @PutMapping("article")
    @SuppressWarnings("Duplicates")
    public Map<String, Object> updateArticle(@RequestBody Article article){
        Map<String, Object> resultMap = new HashMap<>();
        String errorMessage = "错误：修改文章失败";
        try {
            //检验字段
            if (article.getTitle() == null || "".equals(article.getTitle())){
                errorMessage = "错误：标题不能为空";
                throw new RuntimeException(errorMessage);
            }else if (article.getSubTitle() == null || "".equals(article.getSubTitle())){
                errorMessage = "错误：副标题不能为空";
                throw new RuntimeException(errorMessage);
            }else if (article.getMarkdownContent() == null || "".equals(article.getMarkdownContent())){
                errorMessage = "错误：MD内容不能为空";
                throw new RuntimeException(errorMessage);
            }else if (article.getHtmlContent() == null || "".equals(article.getHtmlContent())){
                errorMessage = "错误：HTML内容不能为空";
                throw new RuntimeException(errorMessage);
            }

            articleService.update(article);

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 400);
            resultMap.put("message", errorMessage);
            return resultMap;
        }

        resultMap.put("code", 200);
        resultMap.put("message", "成功：修改文章成功");
        return resultMap;
    }

    @DeleteMapping("/article/{id}")
    public Map<String, Object> deleteArticle(@PathVariable("id") Integer id){
        Map<String, Object> resultMap = new HashMap<>();

        try {
            articleService.deleteArticleAndResource(id);
            System.out.println("删除id："+id);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 400);
            resultMap.put("message", "删除失败");
            return resultMap;
        }

        resultMap.put("code", 200);
        resultMap.put("message", "删除成功");
        return resultMap;
    }

}
