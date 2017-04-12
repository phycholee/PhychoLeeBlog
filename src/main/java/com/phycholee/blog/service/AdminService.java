package com.phycholee.blog.service;

import com.phycholee.blog.base.service.BaseService;
import com.phycholee.blog.model.Admin;

import java.sql.SQLException;

/**
 * Created by PhychoLee on 2016/11/9 21:00.
 * Description: 管理员Service接口
 */
public interface AdminService extends BaseService<Admin> {

    Admin findByUsername(String username) throws SQLException;

}
