package com.phycholee.blog.controller;

import com.phycholee.blog.model.Article;
import com.phycholee.blog.service.ArticleService;
import com.phycholee.blog.service.TagService;
import com.phycholee.blog.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private TagService tagService;


    /**
     * 分页查询文章
     * @return
     */
    @SuppressWarnings("Duplicates")
    @PostMapping("articles")
    public Map<String, Object> getArticleByPage(@RequestBody Map<String, Object> params){
        Integer offset = params.get("offset") == null ? -1 : (StringUtils.isEmpty(params.get("offset").toString()) ? -1 : Integer.parseInt(params.get("offset").toString()));
        Integer limit = params.get("limit") == null ? -1 : (StringUtils.isEmpty(params.get("limit").toString()) ? -1 : Integer.parseInt(params.get("limit").toString()));
        Integer status = params.get("status") == null ? -1 : (StringUtils.isEmpty(params.get("status").toString()) ? -1 : Integer.parseInt(params.get("status").toString()));

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
            //查找文章所有标签id
            article.setTagIds(tagService.findTagIdsBayArticle(article.getId()));
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
            }else if (article.getMarkdownContent() == null || "".equals(article.getMarkdownContent())){
                errorMessage = "错误：MD内容不能为空";
                throw new RuntimeException(errorMessage);
            }else if (article.getHtmlContent() == null || "".equals(article.getHtmlContent())){
                errorMessage = "错误：HTML内容不能为空";
                throw new RuntimeException(errorMessage);
            }

            articleService.insertImgSrc(article);

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 400);
            resultMap.put("message", errorMessage);
            return resultMap;
        }

        resultMap.put("code", 200);
        resultMap.put("message", "保存文章成功");
        return resultMap;
    }

    /**
     * 更新文章
     * @param article
     * @return
     */
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
            }else if (article.getMarkdownContent() == null || "".equals(article.getMarkdownContent())){
                errorMessage = "错误：MD内容不能为空";
                throw new RuntimeException(errorMessage);
            }else if (article.getHtmlContent() == null || "".equals(article.getHtmlContent())){
                errorMessage = "错误：HTML内容不能为空";
                throw new RuntimeException(errorMessage);
            }

            articleService.updateImgsrc(article);

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

    /**
     * 删除文章
     * @param id
     * @return
     */
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
