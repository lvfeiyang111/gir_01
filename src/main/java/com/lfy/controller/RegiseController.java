package com.lfy.controller;

import com.lfy.domain.Admin;
import com.lfy.service.RegistService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/admin")
@Controller
public class RegiseController {
    @Resource
    RegistService registService;
    @RequestMapping("/regist.action")
    public String registController(String myname, String mypwd, HttpServletRequest req){
        Admin admin = new Admin();
        admin.setA_name(myname);
        admin.setA_pass(mypwd);
        int i = registService.RegistService(admin);
        if (i>0){
            //登录成功
            req.setAttribute("registmsg","注册成功请返回登录");
        }else req.setAttribute("registmsg","注册失败");
        return "/admin/regist.jsp";

    }
}
