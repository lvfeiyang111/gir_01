package com.lfy.service.Impl;

import com.lfy.dao.RegiseAdminMapper;
import com.lfy.domain.Admin;
import com.lfy.service.RegistService;
import com.lfy.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RegistServiceImpl implements RegistService {
    @Resource
    RegiseAdminMapper mapper;
    @Override
    public int RegistService(Admin admin) {
        //考虑到是后台管理项目，不是面向用户，所以没加验证码
        String a_pass = admin.getA_pass();
        //调用MD5
        String md5 = MD5Util.getMD5(a_pass);
        admin.setA_pass(md5);
        return mapper.insertAdmin(admin);
    }
}
