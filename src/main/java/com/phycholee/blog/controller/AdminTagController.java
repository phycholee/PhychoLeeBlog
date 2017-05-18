package com.phycholee.blog.controller;

import com.phycholee.blog.model.Tag;
import com.phycholee.blog.service.TagService;
import com.phycholee.blog.utils.JsonData;
import org.apache.ibatis.annotations.Delete;
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
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/admin/")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    /**
     * 保存标签
     * @param tag
     * @return
     */
    @SuppressWarnings("Duplicates")
    @PostMapping("/tag")
    public JsonData saveTag(@RequestBody Tag tag){

        //检验字段
        if (tag.getName() == null || "".equals(tag.getName())){
            JsonData.badParameter("错误：标签名不能为空");
        } else if (tag.getJumbotron() == null || "".equals(tag.getJumbotron())){
            JsonData.badParameter("错误：巨幕图url不能为空");
        } else if (tag.getIsIndex() == null){
            JsonData.badParameter("错误：首页推荐不能为空");
        }

        try {
            if (tag.getIsIndex() != null && tag.getIsIndex() == 1) {
                Map<String, Object> params = new HashMap<>();
                params.put("isIndex", 1);
                int count = tagService.countByCondition(params);
                if (count >= 5) {
                    return JsonData.error("首页推荐不能超过5个！");
                }
            }

            tagService.insert(tag);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error();
        }

        return JsonData.success("保存标签成功");
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
    public JsonData updateTag(@RequestBody Tag tag){
        //检验字段
        if (tag.getName() == null || "".equals(tag.getName())){
            JsonData.badParameter("错误：标签名不能为空");
        } else if (tag.getJumbotron() == null || "".equals(tag.getJumbotron())){
            JsonData.badParameter("错误：巨幕图url不能为空");
        } else if (tag.getIsIndex() == null){
            JsonData.badParameter("错误：首页推荐不能为空");
        }

        try {
            if (tag.getIsIndex() != null && tag.getIsIndex() == 1) {
                Tag byId = tagService.findById(tag.getId());
                int isIndex = 0;
                if (byId != null && byId.getIsIndex() == 1) {
                    isIndex = 1;
                }

                Map<String, Object> params = new HashMap<>();
                params.put("isIndex", 1);
                int count = tagService.countByCondition(params);
                count -= isIndex;
                if (count >= 5) {
                    return JsonData.error("首页推荐不能超过5个！");
                }
            }

            tagService.updateImgsrc(tag);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error();
        }

        return JsonData.success("修改标签成功！");
    }

    /**
     * 删除标签和巨幕图
     * @param id
     * @return
     */
    @DeleteMapping("/tag/{id}")
    public JsonData deleteTag(@PathVariable("id") Integer id){
        try {
            tagService.deleteImgsrc(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error();
        }
        return JsonData.success("删除标签成功");
    }
}
