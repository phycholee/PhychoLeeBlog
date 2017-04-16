package com.phycholee.blog.test;

import com.phycholee.blog.model.Admin;
import com.phycholee.blog.model.User;
import com.phycholee.blog.service.AdminService;
import com.phycholee.blog.service.UserService;
import com.phycholee.blog.utils.EncryptUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

/**
 * Created by PhychoLee on 2016/11/3 19:35.
 * Description: 测试用户Service
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUser {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

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

    @Test
    public void addAdmin() throws InvalidKeySpecException, NoSuchAlgorithmException, SQLException {
        String salt = EncryptUtil.generateSalt();

        String encryptedPassword = EncryptUtil.encryptPassword("admin", salt);

        Admin admin = new Admin();
        admin.setUsername("llf");
        admin.setPassword(encryptedPassword);
        admin.setSalt(salt);
        admin.setEmail("llf@qq.com");

        adminService.insert(admin);
    }

}
