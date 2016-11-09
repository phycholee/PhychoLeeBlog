package com.phycholee.blog.test;

import com.phycholee.blog.Application;
import com.phycholee.blog.model.User;
import com.phycholee.blog.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.SQLException;

/**
 * Created by PhychoLee on 2016/11/3 19:35.
 * Description: 测试用户Service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestUser {

    @Autowired
    private UserService userService;

    @Test
    public void testFind() throws SQLException {
        User user = userService.findById(1);
        System.out.println(user);
    }

    @Test
    public void testInsert() throws SQLException {
        User user = new User();
        user.setUsername("我是谁");
        user.setEmail("wss@163.com");

        userService.insert(user);
    }

    @Test
    public void testUpdate() throws SQLException {
        User user = userService.findById(1);
        user.setUsername(null);
        user.setEmail("wss@163.com");

        userService.update(user);
    }

}
