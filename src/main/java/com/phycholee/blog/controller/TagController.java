package com.phycholee.blog.controller;

import com.phycholee.blog.model.Tag;
import com.phycholee.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PhychoLee on 2017/1/6 20:38.
 * Description: 标签Controller
 */
@RestController
@RequestMapping("/admin/")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 保存标签
     * @param tag
     * @return
     */
    @SuppressWarnings("Duplicates")
    @PostMapping("/tag")
    public Map<String, Object> saveTag(@RequestBody Tag tag){
        Map<String, Object> resultMap = new HashMap<>();

        String errorMessage = "错误：保存标签失败";
        try {
            //检验字段
            if (tag.getName() == null || "".equals(tag.getName())){
                errorMessage = "错误：标签名不能为空";
                throw new RuntimeException(errorMessage);
            }else if (tag.getJumbotron() == null || "".equals(tag.getJumbotron())){
                errorMessage = "错误：巨幕图内容不能为空";
                throw new RuntimeException(errorMessage);
            }

            tagService.insert(tag);

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 400);
            resultMap.put("message", errorMessage);
            return resultMap;
        }

        resultMap.put("code", 200);
        resultMap.put("message", "保存标签成功");

        return resultMap;
    }

    /**
     * 获取tags集合
     * @return
     */
    @GetMapping("/tags")
    public Map<String, Object> getTags(){
        Map<String, Object> resultMap = new HashMap<>();

        List<Tag> tags = null;
        try {
            tags = tagService.findTags();
        } catch (Exception e) {
            e.printStackTrace();
        }


        resultMap.put("code", 200);
        resultMap.put("rows", tags);
        return resultMap;
    }

    /**
     * 修改标签
     * @param tag
     * @return
     */
    @SuppressWarnings("Duplicates")
    @PutMapping("/tag")
    public Map<String, Object> updateTag(@RequestBody Tag tag){
        Map<String, Object> resultMap = new HashMap<>();

        String errorMessage = "错误：保存标签失败";
        try {
            //检验字段
            if (tag.getName() == null || "".equals(tag.getName())){
                errorMessage = "错误：标签名不能为空";
                throw new RuntimeException(errorMessage);
            }else if (tag.getJumbotron() == null || "".equals(tag.getJumbotron())){
                errorMessage = "错误：巨幕图内容不能为空";
                throw new RuntimeException(errorMessage);
            }

            tagService.updateImgsrc(tag);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 400);
            resultMap.put("message", errorMessage);
            return resultMap;
        }

        resultMap.put("code", 200);
        resultMap.put("message", "修改标签成功");
        return resultMap;
    }

    /**
     * 删除标签和巨幕图
     * @param id
     * @return
     */
    @GetMapping("/tag/{id}")
    public Map<String, Object> deleteTag(@PathVariable("id") Integer id){
        Map<String, Object> resultMap = new HashMap<>();

        try {
            tagService.deleteImgsrc(id);
        } catch (Exception e) {
            e.printStackTrace();

            resultMap.put("code", 400);
            resultMap.put("message", "删除标签失败");
            return resultMap;
        }

        resultMap.put("code", 200);
        resultMap.put("message", "删除标签成功");
        return resultMap;
    }
}
