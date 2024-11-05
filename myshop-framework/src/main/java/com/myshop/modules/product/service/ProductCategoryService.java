package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.product.entity.dos.ProductCategory;
import com.myshop.modules.product.entity.vos.ProductCategoryVO;

import java.util.List;

public interface ProductCategoryService extends IService<ProductCategory> {

    /**
     * Lấy danh sách tất cả các phân loại, bao gồm cả quan hệ cha-con
     *
     * @param parentId ID của phân loại cha
     * @return Danh sách tất cả các phân loại, bao gồm cả quan hệ cha-con
     */
    List<ProductCategoryVO> listSubCategories(String parentId);

    /**
     * Lấy cây phân loại
     *
     * @return Cây phân loại
     */
    List<ProductCategoryVO> getCategoryTree();

    /**
     * Lấy tên danh mục theo danh sách ID danh mục đã cho
     *
     * @param categoryIds Danh sách ID danh mục
     * @return Danh sách tên danh mục
     */
    Object getProductCategoryNameByIds(List<String> categoryIds);
}
