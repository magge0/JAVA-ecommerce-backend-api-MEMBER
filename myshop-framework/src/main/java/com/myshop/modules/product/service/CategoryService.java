package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.product.entity.dos.Category;
import com.myshop.modules.product.entity.vos.CategoryVO;

import java.util.List;

public interface CategoryService extends IService<Category> {

    /**
     * Lấy danh sách tất cả các phân loại, bao gồm cả quan hệ cha-con
     *
     * @param parentId ID của phân loại cha
     * @return Danh sách tất cả các phân loại, bao gồm cả quan hệ cha-con
     */
    List<CategoryVO> listSubCategories(String parentId);

    /**
     * Lấy cây phân loại
     *
     * @return Cây phân loại
     */
    List<CategoryVO> getCategoryTree();
}
