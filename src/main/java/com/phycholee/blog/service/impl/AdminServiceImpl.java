package com.phycholee.blog.service.impl;

import com.phycholee.blog.base.service.impl.BaseServiceImpl;
import com.phycholee.blog.dao.AdminDao;
import com.phycholee.blog.model.Admin;
import com.phycholee.blog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by PhychoLee on 2016/11/9 21:01.
 * Description: 管理员Service实现类
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    private AdminDao adminDao;
    @Autowired
    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
        super.setDao(adminDao);
    }

    @Override
    public Admin findByUsername(String username) throws SQLException {
        return adminDao.findByUsername(username);
    }

    @Override
    public List<Admin> getAdminList() throws SQLException {
        return adminDao.getAdminList();
    }
}
