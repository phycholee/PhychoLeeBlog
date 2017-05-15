package com.phycholee.blog.controller;

import com.phycholee.blog.model.Admin;
import com.phycholee.blog.service.AdminService;
import com.phycholee.blog.utils.EncryptUtil;
import com.phycholee.blog.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

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

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public JsonData update(@RequestBody Admin admin){
        if (admin == null)
            return JsonData.badParameter("参数不能为空");

        if (admin.getId() == null)
            return JsonData.badParameter("id不能为空");

        try {

            int result = adminService.update(admin);

            if (result > 0){
                return JsonData.success(null);
            } else {
                return JsonData.error("修改用户信息失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error("修改用户信息失败！");
        }
    }


    @GetMapping("admins")
    public JsonData getAdminList(){
        try {
            List<Admin> adminList = adminService.getAdminList();
            return JsonData.success(adminList);
        } catch (SQLException e) {
            e.printStackTrace();
            return JsonData.error();
        }
    }

}
