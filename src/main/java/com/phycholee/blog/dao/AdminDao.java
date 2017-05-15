package com.phycholee.blog.dao;

import com.phycholee.blog.base.dao.BaseDao;
import com.phycholee.blog.model.Admin;

import java.sql.SQLException;
import java.util.List;

public interface AdminDao extends BaseDao<Admin> {

    Admin findByUsername(String username) throws SQLException;

    List<Admin> getAdminList() throws SQLException;

}