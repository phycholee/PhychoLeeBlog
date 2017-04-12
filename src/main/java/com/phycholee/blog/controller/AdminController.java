package com.phycholee.blog.controller;

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
 * Created by PhychoLee on 2017/4/12 23:08.
 * Description:
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 注册管理员账号
     * @param admin
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public JsonData register(Admin admin){
        if (admin == null)
            return JsonData.badParameter("参数不能为空");

        if (StringUtils.isEmpty(admin.getUsername()))
            return JsonData.badParameter("用户名不能为空");
        if (StringUtils.isEmpty(admin.getPassword()))
            return JsonData.badParameter("密码不能为空");
        if (StringUtils.isEmpty(admin.getEmail()))
            return JsonData.badParameter("邮箱不能为空");

        try {
            String salt = EncryptUtil.generateSalt();

            String encryptedPassword = EncryptUtil.encryptPassword(admin.getPassword(), salt);

            admin.setSalt(salt);
            admin.setPassword(encryptedPassword);

            int result = adminService.insert(admin);

            if (result > 0){
                return JsonData.success(null);
            } else {
                return JsonData.error();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error();
        }
    }

}
