package com.lfy.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lfy.dao.ProductInfoMapper;
import com.lfy.domain.ProductInfo;
import com.lfy.domain.ProductInfoExample;
import com.lfy.domain.vo.ProductInfoVo;
import com.lfy.service.ProductInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Resource
ProductInfoMapper infoMapper;
    @Override
    public List<ProductInfo> getAll() {

        return infoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //分页插件使用PagePageHelper工具类完成分页
        PageHelper.startPage(pageNum,pageSize);
        ProductInfoExample example = new ProductInfoExample();
        //设置排序，按主键降序
        example.setOrderByClause("p_id desc");
        List<ProductInfo> list = infoMapper.selectByExample(example);
        //将集合分装到pageinfo中
        PageInfo<ProductInfo> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    @Override
    public int save(ProductInfo info) {

        return infoMapper.insert(info);
    }

    @Override
    public ProductInfo getByID(int pid) {

        return infoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {

        return infoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {

        return infoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return infoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {

        return infoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo splitPageVo(ProductInfoVo vo, int pageSize) {

        //设置，PageHelper.startPage()属性
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list = infoMapper.selectCondition(vo);
        return new PageInfo(list);
    }

}
