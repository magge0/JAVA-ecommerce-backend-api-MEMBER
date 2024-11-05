package com.myshop.modules.product.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.modules.product.entity.dos.ProductCategorySpecification;
import com.myshop.modules.product.entity.dos.ProductSpecification;
import com.myshop.modules.product.mapper.ProductCategorySpecificationMapper;
import com.myshop.modules.product.service.ProductCategorySpecificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategorySpecificationServiceImpl extends ServiceImpl<ProductCategorySpecificationMapper, ProductCategorySpecification> implements ProductCategorySpecificationService {
    @Override
    public List<ProductSpecification> getProductCategorySpecList(String categoryId) {
        return this.baseMapper.getProductCategorySpecList(categoryId);
    }
}
