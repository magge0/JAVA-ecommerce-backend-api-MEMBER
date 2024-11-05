package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.product.entity.dos.ProductSpecification;

import java.util.List;

public interface ProductSpecificationService extends IService<ProductSpecification> {
    /**
     * Xóa thông số kỹ thuật
     *
     * @param ids ID thông số kỹ thuật
     * @return  Liệu việc xóa có thành công hay không
     */
    boolean deleteProductSpecification(List<String> ids);
}
