package com.phycholee.blog.authorization.controller;

import com.phycholee.blog.service.AdminService;
import com.phycholee.blog.utils.EncryptUtil;
import com.phycholee.blog.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by PhychoLee on 2017/4/10 23:21.
 * Description:
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "admin",method = RequestMethod.POST)
    public JsonData adminLogin(String username, String password){
        try {
            String salt = EncryptUtil.generateSalt();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

}
