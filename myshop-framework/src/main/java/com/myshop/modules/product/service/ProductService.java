package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dto.ProductOperationDTO;

public interface ProductService extends IService<Product> {

    /**
     * Thêm sản phẩm
     *
     * @param goodsOperationDTO Điều kiện tìm kiếm sản phẩm
     */
    void addProduct(ProductOperationDTO goodsOperationDTO);
}
