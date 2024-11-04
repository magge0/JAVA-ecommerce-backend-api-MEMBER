package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.cache.CachePrefix;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductSku;

import java.util.List;

public interface ProductSkuService extends IService<ProductSku> {

    /**
     * Lấy ID bộ nhớ cache của sản phẩm SKU
     *
     * @param id Id của SKU
     * @return ID bộ nhớ cache của sản phẩm SKU
     */
    static String getSkuCacheId(String id) {
        return CachePrefix.PRODUCT_SKU.getPrefix() + id;
    }

    /**
     * Cập nhật trạng thái sản phẩm SKU
     *
     * @param product Thông tin sản phẩm (Id, DisplayStatus/IsApproved)
     */
    void updateProductSkuStatus(Product product);


    /**
     * Lấy danh sách tất cả các goodsSku thuộc goodsId
     *
     * @param productId Id của sản phẩm
     * @return Danh sách goodsSku
     */
    List<ProductSku> getProductSkuListByProductId(String productId);
}
