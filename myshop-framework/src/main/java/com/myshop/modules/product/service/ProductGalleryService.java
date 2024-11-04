package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.product.entity.dos.ProductGallery;

import java.util.List;

public interface ProductGalleryService extends IService<ProductGallery> {

    /**
     * Lấy ảnh thu nhỏ từ ảnh gốc
     *
     * @param origin Đường dẫn ảnh gốc
     * @return Album ảnh sản phẩm
     */
    ProductGallery getProductGallery(String origin);

    /**
     * Thêm album ảnh sản phẩm
     *
     * @param productGalleryList Danh sách album ảnh sản phẩm
     * @param goodsId          ID sản phẩm
     */
    void add(List<String> productGalleryList, String goodsId);

    /**
     * Tìm kiếm album ảnh sản phẩm theo ID sản phẩm
     *
     * @param productId ID sản phẩm
     * @return Danh sách album ảnh sản phẩm
     */
    List<ProductGallery> productGalleryList(String productId);
}
