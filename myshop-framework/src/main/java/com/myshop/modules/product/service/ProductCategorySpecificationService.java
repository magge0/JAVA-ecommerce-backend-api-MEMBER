package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.product.entity.dos.ProductCategorySpecification;
import com.myshop.modules.product.entity.dos.ProductSpecification;

import java.util.List;

public interface ProductCategorySpecificationService extends IService<ProductCategorySpecification> {
    /**
     * Truy vấn thông tin thông số kỹ thuật theo ID phân loại
     *
     * @param categoryId ID phân loại
     * @return Thông tin liên kết thông số kỹ thuật phân loại
     */
    List<ProductSpecification> getProductCategorySpecList(String categoryId);

    /**
     * Xóa các thông số kỹ thuật liên kết theo ID loại sản phẩm
     *
     * @param categoryId ID của loại sản phẩm
     */
    void deleteByProductCategoryId(String categoryId);
}
