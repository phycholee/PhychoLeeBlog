package com.phycholee.blog.test;

import com.phycholee.blog.model.User;
import com.phycholee.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

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
}
