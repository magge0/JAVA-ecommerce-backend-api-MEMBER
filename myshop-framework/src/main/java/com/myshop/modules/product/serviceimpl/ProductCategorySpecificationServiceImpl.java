package com.myshop.modules.product.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.modules.product.entity.dos.ProductCategorySpecification;
import com.myshop.modules.product.mapper.ProductCategorySpecificationMapper;
import com.myshop.modules.product.service.ProductCategorySpecificationService;
import org.springframework.stereotype.Service;

@Service
public class ProductCategorySpecificationServiceImpl extends ServiceImpl<ProductCategorySpecificationMapper, ProductCategorySpecification> implements ProductCategorySpecificationService {
}
