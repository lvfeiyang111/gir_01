package com.lfy.service.Impl;

import com.lfy.dao.SelectAdminDao;
import com.lfy.domain.Admin;
import com.lfy.service.findService;
import com.lfy.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录service
 */
@Service
public class findServiceImpl implements findService {
    @Resource
SelectAdminDao dao;
    @Override
    public Admin findadmin(String name,String passwd) {
        List<Admin> list = dao.selectadmin(name);
        if (list.size()>0){
            //因为数据库构建时就不允许用户名重复，所以直接get0
            Admin admin = list.get(0);
            //加密对比数据库密码
            String md5 = MD5Util.getMD5(passwd);
            boolean equals = admin.getA_pass().equals(md5);
            if (equals){
                //登录成功
                return admin;
            }
        }
    //失败密码或用户名不存在
        return null;
    }
}
