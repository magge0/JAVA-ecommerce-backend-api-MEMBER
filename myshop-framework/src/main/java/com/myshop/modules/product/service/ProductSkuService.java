package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.cache.CachePrefix;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductSku;
import com.myshop.modules.product.entity.vos.ProductSkuVO;

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

    /**
     * Lấy danh sách ProductSku theo ID sản phẩm
     *
     * @param productId ID sản phẩm
     * @return Danh sách ProductSku
     */
    List<ProductSkuVO> getProductListByProductId(String productId);

    /**
     * Tạo danh sách ProductSkuVO từ danh sách ProductSku
     *
     * @param list Danh sách ProductSku
     * @return Danh sách ProductSkuVO
     */
    List<ProductSkuVO> getProductSkuVOList(List<ProductSku> list);

    /**
     * Tạo ProductSkuVO từ thông tin ProductSku
     *
     * @param productSku Thông tin ProductSku
     * @return ProductSkuVO
     */
    ProductSkuVO getProductSkuVO(ProductSku productSku);
}
