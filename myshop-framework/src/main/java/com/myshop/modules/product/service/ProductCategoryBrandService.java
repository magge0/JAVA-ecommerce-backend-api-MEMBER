package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.product.entity.dos.ProductCategoryBrand;

import java.util.List;

public interface ProductCategoryBrandService extends IService<ProductCategoryBrand> {
    /**
     * Lấy thông tin liên kết danh mục - thương hiệu dựa trên ID thương hiệu
     *
     * @param brandIds ID thương hiệu
     * @return Thông tin liên kết danh mục - thương hiệu
     */
    List<ProductCategoryBrand> getCategoryBrandsByBrandIds(List<String> brandIds);
}
