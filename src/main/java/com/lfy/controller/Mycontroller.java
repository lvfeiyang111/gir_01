package com.lfy.controller;

import com.lfy.domain.Admin;
import com.lfy.service.findService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 登录controller
 */
@RequestMapping("/admin")
@Controller
public class Mycontroller {
    @Resource
    findService service;
    @RequestMapping("/login.action")
    public String loginController(String name , String pwd, HttpServletRequest req){
        Admin findadmin = service.findadmin(name, pwd);
        if (findadmin !=null){
            req.setAttribute("admin",findadmin);
            return "/admin/main.jsp";
        }else{
            req.setAttribute("errmsg","用户名或密码错误");
            return "/admin/login.jsp";

        }
    }

}
