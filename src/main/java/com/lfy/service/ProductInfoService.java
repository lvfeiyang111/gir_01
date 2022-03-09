package com.lfy.service;

import com.github.pagehelper.PageInfo;
import com.lfy.domain.ProductInfo;
import com.lfy.domain.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {
    //显示全部商品
    List<ProductInfo> getAll();

    //分页,mybatis工具调用
    /**
     *select * from product_info limit 起始记录数=（（当前页-1）*每页的条数），每页取几条
     * 所以需要当前页参数，和每页的条数参数
     */
    PageInfo splitPage(int pageNum,int pageSize);

    int save(ProductInfo info);
    //按主键查询商品
    ProductInfo getByID(int pid);
    //更新商品
    int update(ProductInfo info);
    //删除
    int delete(int pid);
    //批量删除
    int deleteBatch(String []ids);
    //多条件商品查询
    List<ProductInfo> selectCondition(ProductInfoVo vo);
    //多条件分页
    public PageInfo splitPageVo(ProductInfoVo vo,int pageSize);
}
