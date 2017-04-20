package com.phycholee.blog.controller;

import com.phycholee.blog.authorization.config.Constants;
import com.phycholee.blog.model.Admin;
import com.phycholee.blog.model.Article;
import com.phycholee.blog.service.AdminService;
import com.phycholee.blog.service.ArticleService;
import com.phycholee.blog.service.TagService;
import com.phycholee.blog.utils.JsonData;
import com.phycholee.blog.utils.Pager;
import com.phycholee.blog.utils.PagerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    @Autowired
    private TagService tagService;

    @Autowired
    private AdminService adminService;


    /**
     * 分页查询文章
     * @return
     */
    @SuppressWarnings("Duplicates")
    @PostMapping("articles")
    public JsonData getArticles(@RequestBody Map<String, Object> params){
        Integer page = params.get("page") == null ? -1 : (StringUtils.isEmpty(params.get("page").toString()) ? -1 : Integer.parseInt(params.get("page").toString()));
        Integer limit = params.get("limit") == null ? -1 : (StringUtils.isEmpty(params.get("limit").toString()) ? -1 : Integer.parseInt(params.get("limit").toString()));
        Integer status = params.get("status") == null ? -1 : (StringUtils.isEmpty(params.get("status").toString()) ? -1 : Integer.parseInt(params.get("status").toString()));
        Integer tagId = params.get("tagId") == null ? -1 : (StringUtils.isEmpty(params.get("tagId").toString()) ? -1 : Integer.parseInt(params.get("tagId").toString()));
        String title = params.get("title") == null ? "" : params.get("title").toString();

        try {
            Map<String, Object> params2 = new HashMap<>();
            if (page > -1 && limit > -1){
                int offset = (page - 1) * limit;
                params2.put("offset", offset);
                params2.put("limit", limit);
            }
            if (status >-1 )
                params2.put("status", status);
            if (tagId > -1)
                params2.put("tagId", tagId);
            params2.put("title", title);
            Pager pager = articleService.findArticlesByCondition(params2);

            return JsonData.success(pager);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error();
        }
    }

    /**
     * 获取文章详细信息
     * @param id
     * @return
     */
    @SuppressWarnings("Duplicates")
    @GetMapping("/article/{id}")
    public JsonData getArticle(@PathVariable("id") Integer id){
        Map<String, Object> resultMap = new HashMap<>();

        Article article = null;
        try {
            article = articleService.findById(id);

            Admin admin = adminService.findById(article.getAuthorId());
            article.setAuthorName(admin.getNickname());
            //查找文章所有标签id
            article.setTags(tagService.findTagsBayArticle(article.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error();
        }

        return JsonData.success(article);
    }

    /**
     * 保存文章
     * @param article
     * @return
     */
    @SuppressWarnings("Duplicates")
    @PostMapping("/article")
    public JsonData addArticle(@RequestBody Article article, HttpServletRequest request){
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

            Admin admin = (Admin) request.getSession().getAttribute(Constants.CURRENT_USER);
            article.setAuthorId(admin.getId());

            articleService.insertImgSrc(article);

        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.badParameter(errorMessage);
        }

        return JsonData.success("添加成功", null);
    }

    /**
     * 更新文章
     * @param article
     * @return
     */
    @PutMapping("article")
    @SuppressWarnings("Duplicates")
    public JsonData updateArticle(@RequestBody Article article){
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
            return JsonData.badParameter(errorMessage);
        }

        return JsonData.success("修改成功", null);
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @DeleteMapping("/article/{id}")
    public JsonData deleteArticle(@PathVariable("id") Integer id){

        try {
            articleService.deleteArticleAndResource(id);
            System.out.println("删除id："+id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error("删除失败");
        }

        return JsonData.success("删除成功", null);
    }

}
