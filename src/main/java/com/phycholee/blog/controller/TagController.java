package com.phycholee.blog.controller;

import com.phycholee.blog.model.Tag;
import com.phycholee.blog.service.TagService;
import com.phycholee.blog.utils.JsonData;
import com.phycholee.blog.utils.Pager;
import com.phycholee.blog.utils.PagerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PhychoLee on 2017/3/24 22:44.
 * Description:
 */
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 获取tags集合
     * @return
     */
    @PostMapping("/index-tags")
    public JsonData getIndexTags(@RequestBody Map<String, Object> params){
        Integer isIndex = params.get("isIndex") == null ? -1 : (StringUtils.isEmpty(params.get("isIndex").toString()) ? -1 : Integer.parseInt(params.get("isIndex").toString()));

        Map<String, Object> map = new HashMap<>();
        if(isIndex == 1 || isIndex == 0){
            map.put("isIndex", isIndex);
        }

        try {
            Pager pager = tagService.findTagsByCondition(map);
            return PagerData.page(pager.getData(), pager.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error();
        }
    }

    /**
     * 获取tags集合
     * @return
     */
    @GetMapping("/tags")
    public JsonData getTags(){
        try {
            List<Tag> list = tagService.findTags();
            return JsonData.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error();
        }
    }



    /**
     * 根据id获取tag
     * @return
     */
    @GetMapping("/tag/{id}")
    public JsonData getTag(@PathVariable Integer id){

        if (id == null){
            JsonData.badParameter("id不能为空");
        }

        try {
            Tag tag = tagService.findById(id);
            return JsonData.success(tag);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error();
        }
    }

}
