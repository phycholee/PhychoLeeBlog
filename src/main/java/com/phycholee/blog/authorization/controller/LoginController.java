package com.phycholee.blog.authorization.controller;

import com.phycholee.blog.authorization.model.TokenModel;
import com.phycholee.blog.authorization.service.TokenService;
import com.phycholee.blog.model.Admin;
import com.phycholee.blog.service.AdminService;
import com.phycholee.blog.utils.EncryptUtil;
import com.phycholee.blog.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by PhychoLee on 2017/4/10 23:21.
 * Description:
 */
@RestController
@RequestMapping("authorization")
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private TokenService tokenService;

    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public JsonData adminLogin(String username, String password){

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return JsonData.badParameter("参数错误");
        }

        try {
            Admin admin = adminService.findByUsername(username);

            if (admin == null){
                return JsonData.generate(JsonData.CODE_ERROR, "用户名或密码错误", null);
            }

            String salt = admin.getSalt();

            String encryptedPassword = admin.getPassword();

            boolean authenticate = EncryptUtil.authenticate(password, encryptedPassword, salt);

            if (authenticate){
                TokenModel model = tokenService.createToken(admin.getId());

                return JsonData.success(model);
            } else {
                return JsonData.generate(JsonData.CODE_ERROR, "用户名或密码错误", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error();
        }
    }


    /**
     * 管理员注销
     * @param id
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public JsonData adminLogout(Integer id){

        if (id == null){
            return JsonData.badParameter("id不能为空");
        }

        //删除token
        tokenService.deleteToken(id);

        return JsonData.success(null);
    }
}