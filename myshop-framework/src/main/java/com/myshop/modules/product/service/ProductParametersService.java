package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.product.entity.dos.ProductParameters;
import jakarta.validation.Valid;

public interface ProductParametersService extends IService<ProductParameters> {
    /**
     * Cập nhật thông tin nhóm tham số
     *
     * @param productParameters Thông tin nhóm tham số
     * @return Cập nhật thành công hay không
     */
    boolean updateProductParameter(@Valid ProductParameters productParameters);
}
