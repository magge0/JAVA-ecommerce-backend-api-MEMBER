package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.product.entity.dos.ProductCategoryParameterGroup;
import com.myshop.modules.product.entity.vos.ProductParameterGroupVO;

import java.util.List;

public interface ProductCategoryParameterGroupService extends IService<ProductCategoryParameterGroup> {

    /**
     * Truy vấn bộ sưu tập tham số được liên kết với phân loại
     *
     * @param categoryId ID phân loại
     * @return Tham số phân loại
     */
    List<ProductParameterGroupVO> getProductCategoryParams(String categoryId);

    /**
     * Truy vấn thông tin nhóm tham số được liên kết với phân loại
     *
     * @param categoryId ID phân loại
     * @return Danh sách nhóm tham số
     */
    List<ProductCategoryParameterGroup> getProductCategoryGroup(String categoryId);
}
