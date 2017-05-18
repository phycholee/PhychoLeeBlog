package com.phycholee.blog.authorization.controller;

import com.phycholee.blog.authorization.config.Constants;
import com.phycholee.blog.authorization.model.TokenModel;
import com.phycholee.blog.authorization.service.TokenService;
import com.phycholee.blog.model.Admin;
import com.phycholee.blog.service.AdminService;
import com.phycholee.blog.utils.EncryptUtil;
import com.phycholee.blog.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


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
    @PostMapping("login")
    public JsonData adminLogin(@RequestBody Map<String, Object> params){

        String username = params.get("username") == null ? "" : params.get("username").toString().trim();
        String password = params.get("password") == null ? "" : params.get("password").toString().trim();


        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return JsonData.badParameter("参数错误");
        }

        try {
            Admin admin = adminService.findByUsername(username);

            if (admin == null){
                return JsonData.generate(JsonData.CODE_ERROR, "用户名或密码错误", null);
            }

            /**
             * 校验密码
             */
            String salt = admin.getSalt();
            String encryptedPassword = admin.getPassword();
            boolean authenticate = EncryptUtil.authenticate(password, encryptedPassword, salt);

            if (authenticate){
                TokenModel model = tokenService.createToken(admin.getId());

                String token = model.getToken();
                token += "-" + model.getUserId();

                return JsonData.success(token);
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
     * @param request
     * @return
     */
    @PostMapping("logout")
    public JsonData adminLogout(@RequestBody String token, HttpServletRequest request){

        if (StringUtils.isEmpty(token)){
            return JsonData.badParameter("token错误");
        }

        //获取token和userId
        String[] split = token.split("-");
        if (split.length <2){
            return JsonData.badParameter("token错误");
        }
        Integer userId = Integer.parseInt(split[1]);

        //删除token
        tokenService.deleteToken(userId);

        return JsonData.success(null);
    }
}
