package com.phycholee.blog.authorization.controller;

import com.phycholee.blog.utils.JsonData;
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

    @RequestMapping(value = "admin",method = RequestMethod.POST)
    public JsonData adminLogin(String username, String password){


        return null;
    }

}
