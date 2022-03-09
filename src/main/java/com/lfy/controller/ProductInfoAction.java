package com.lfy.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.pagehelper.PageInfo;
import com.lfy.domain.ProductInfo;
import com.lfy.domain.vo.ProductInfoVo;
import com.lfy.service.ProductInfoService;
import com.lfy.util.FileNameUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *商品增删改查分页Controller
 */
@RequestMapping("/prod")
@Controller
public class ProductInfoAction {
    //每页显示的记录数
    public static final int PAGE_SIZE = 5;
    //图片路径
    String SaveFileName = "";
    @Resource
    ProductInfoService infoService;

    @RequestMapping("/getAll.action")
    public String getAll(HttpServletRequest req) {
        List<ProductInfo> list = infoService.getAll();
        req.setAttribute("list", list);
        return "/admin/product.jsp";
    }

    //显示第一页的第5条记录
    @RequestMapping("/split.action")
    public String split(HttpServletRequest req) {
        PageInfo info = null;
        Object vo = req.getSession().getAttribute("prodVo");
        if (vo != null){
            info = infoService.splitPageVo((ProductInfoVo)vo,PAGE_SIZE);
            req.getSession().removeAttribute("prodVo");
        }else  info = infoService.splitPage(1,PAGE_SIZE);



        req.setAttribute("info", info);
        return "/admin/product.jsp";
    }

    //ajax 分页，翻页
    @ResponseBody
    @RequestMapping("/ajaxsplit.action")
    public void ajaxSplit( HttpSession session,ProductInfoVo vo) {
        //获取当前page参数的页面数据
        PageInfo info = infoService.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info", info);
    }

    //ajax异步文件上传
    @ResponseBody
    @RequestMapping("/ajaxImg.action")
    public String ajaxImg(MultipartFile pimage, HttpServletRequest req) {
        //提取文件名，生成工具类封装过后的文件名，以及后缀
        SaveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项图片的存储路径
        String path = req.getServletContext().getRealPath("/image_big");
        //转存
        //File.separator反斜杠
        try {
            pimage.transferTo(new File(path + File.separator + SaveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ///返回json对象，封装图片的路径，在页面回显图片
       /* JSONObject jsonObject = new JSONObject();
        jsonObject.put("imgurl",SaveFileName);*/
//        return jsonObject.toString();
      /*  HashMap hashMap = new HashMap();
        hashMap.put("imgurl",SaveFileName);*/
    /*    List list = new ArrayList();
        list.add(SaveFileName);*/
        /*直接返回图片路径需要把页面的ajax中json改成text，因为json和String是有冲突的
        ，一开始用返回JSON的方式，这里不知道为什么不能用map
        ，用JSONobject工具是可以的但是需要用toString*/
        return SaveFileName;
    }

    //增加商品
    @RequestMapping("/save.action")
    public String save(ProductInfo info, HttpServletRequest request) {
        info.setpImage(SaveFileName);
        info.setpDate(new Date());
        //info对象中有表单提交的名称，介绍等，有异步ajax上来的图片，图片名称等。
        int save = infoService.save(info);
        if (save > 0) {
            request.setAttribute("message", "增加成功");
        } else request.setAttribute("message", "增加失败");
        return "/prod/split.action";
    }

    //编辑商品数据回显
    @RequestMapping("/one.action")
    public String one(int pid,ProductInfoVo vo, Model model,HttpSession session) {
        ProductInfo info = infoService.getByID(pid);
        model.addAttribute("prod", info);
        //多条件页码放入session
        session.setAttribute("prodVo",vo);
        return "/admin/update.jsp";
    }

    //更新商品
    @RequestMapping("/update.action")
    public String update(ProductInfo info, HttpServletRequest req) {
        //如果有异步上传图片直接赋值
        if (!SaveFileName.equals("")) {
            info.setpImage(SaveFileName);

        }
        //日期忘了，测试中出现了bug,上架时间会随着更新而更新，如果不new Date数据库又是空，
        // 经过查看前端页面分析这里不能用对象来更新数据库，应该根据主键进行局部更新，
        // 或者前端更新页面回显的时候就应该加上日期，因为前端没加日期页面，导致接收参数实体类info的日期变为空
        //这里偷懒直接new Date了，知道原因但是懒得改bug了
        info.setpDate(new Date());
        //如果没上传过，就用之前的不用更改直接更新
        int num=-1;
        try {
            num= infoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num>0){
            //更新成功
            req.setAttribute("msg","更新成功");
        }else req.setAttribute("msg","更新失败");
        //把路径清空不然下次出bug
        SaveFileName="";
        return "forward:/prod/split.action";

    }
    //删除，因为这里跳转的是分页面，所以要使用ajax进行删除的成功或失败提示
    @RequestMapping("/delete.action")
    public String delete(int pid,ProductInfoVo vo,HttpServletRequest request){
        int num=-1;
        try {
            num = infoService.delete(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num>0){
            //成功
            request.setAttribute("msg","删除成功");
            request.getSession().setAttribute("deleteProdVo",vo);
        }else request.setAttribute("msg","删除失败");
        return "/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit.action",produces ="text/html;charset=UTF-8")
    public Object aeleteAjaxSpilt(HttpServletRequest req){
        PageInfo info = null;
        Object vo = req.getSession().getAttribute("deleteProdVo");
        if (vo != null){
            info = infoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE );
            req.getSession().removeAttribute("deleteProdVo");
        }else {
            info = infoService.splitPage(1,PAGE_SIZE );
        }

        //前端用了load所以必须用session
        req.getSession().setAttribute("info",info);
        return req.getAttribute("msg");
    }
    //批量删除
    @RequestMapping("/deleteBatch.action")
    public String deleteBatch(String pids,HttpServletRequest req){
        ///prod/deleteBatch.action
//              deleteBatch.action
        //通过分析前端，pids是一个以1,2,3,4这种类型的字符串
        //直接调用字符串切割方法
        String []ps = pids.split(",");
        try {
            int num = infoService.deleteBatch(ps);
            if (num>0){
                req.setAttribute("msg","批量删除成功");
            }else req.setAttribute("msg","批量删除失败");
        } catch (Exception e) {
            req.setAttribute("msg","别删了，这玩意有人下订单了");
        }
        return "/prod/deleteAjaxSplit.action";
    }

}
