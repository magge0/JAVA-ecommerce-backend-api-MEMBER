package com.myshop.modules.product.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.modules.product.entity.dos.Category;
import com.myshop.modules.product.mapper.CategoryMapper;
import com.myshop.modules.product.service.CategoryService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"CATEGORY_DETAILS"})
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private static final String DELETED_FLAG = "delete_flag";
}
