package com.phycholee.blog.test;

import com.phycholee.blog.model.User;
import com.phycholee.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PhychoLee on 2016/11/3 16:26.
 * Description: 测试Controller
 */
@RestController
public class TestController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public User index() {
        User user = null;
        try {
            user = userService.findById(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public Map<String, Object> addUser(User user){
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            userService.insert(user);
        } catch (SQLException e) {
            e.printStackTrace();
            resultMap.put("code", 400);
            resultMap.put("message", "添加用户失败");
        }

        resultMap.put("code", 200);
        resultMap.put("message", "添加用户成功");
        return resultMap;
    }
}
