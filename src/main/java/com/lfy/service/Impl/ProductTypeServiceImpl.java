package com.lfy.service.Impl;

import com.lfy.dao.ProductTypeMapper;
import com.lfy.domain.ProductType;
import com.lfy.domain.ProductTypeExample;
import com.lfy.service.ProductTypeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
@Service("productTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Resource
    ProductTypeMapper typeMapper;
    @Override
    public List<ProductType> getAll() {

        return typeMapper.selectByExample(new ProductTypeExample());
    }

}
