package com.lfy.dao;

import com.lfy.domain.Admin;

import java.util.List;

public interface SelectAdminDao {
    List<Admin> selectadmin(String name);
}
