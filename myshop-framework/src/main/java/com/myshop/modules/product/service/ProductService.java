package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductSearchParams;
import com.myshop.modules.product.entity.dto.ProductOperationDTO;

public interface ProductService extends IService<Product> {

    /**
     * Thêm sản phẩm
     *
     * @param productOperationDTO Điều kiện tìm kiếm sản phẩm
     */
    void addProduct(ProductOperationDTO productOperationDTO);

    /**
     * Truy vấn sản phẩm
     *
     * @param productSearchParams Tham số truy vấn
     * @return Phân trang sản phẩm
     */
    IPage<Product> queryByParams(ProductSearchParams productSearchParams);
}
