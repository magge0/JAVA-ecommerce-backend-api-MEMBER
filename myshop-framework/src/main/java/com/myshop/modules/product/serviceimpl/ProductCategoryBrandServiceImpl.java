package com.myshop.modules.product.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.modules.product.entity.dos.ProductCategoryBrand;
import com.myshop.modules.product.mapper.ProductCategoryBrandMapper;
import com.myshop.modules.product.service.ProductCategoryBrandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryBrandServiceImpl extends ServiceImpl<ProductCategoryBrandMapper, ProductCategoryBrand> implements ProductCategoryBrandService {
    @Override
    public List<ProductCategoryBrand> getCategoryBrandsByBrandIds(List<String> brandIds) {
        return this.list(new LambdaQueryWrapper<ProductCategoryBrand>().in(ProductCategoryBrand::getBrandId, brandIds));
    }

    @Override
    public void deleteByProductCategoryId(String categoryId) {
        this.baseMapper.delete(new LambdaUpdateWrapper<ProductCategoryBrand>().eq(ProductCategoryBrand::getCategoryId, categoryId));
    }
}
