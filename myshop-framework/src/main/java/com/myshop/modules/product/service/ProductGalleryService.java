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
     * @param goodsGalleryList Danh sách album ảnh sản phẩm
     * @param goodsId          ID sản phẩm
     */
    void add(List<String> goodsGalleryList, String goodsId);
}
