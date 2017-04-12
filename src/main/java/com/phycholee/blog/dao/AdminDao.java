package com.phycholee.blog.dao;

import com.phycholee.blog.base.dao.BaseDao;
import com.phycholee.blog.model.Admin;

import java.sql.SQLException;

public interface AdminDao extends BaseDao<Admin> {

    Admin findByUsername(String username) throws SQLException;

}