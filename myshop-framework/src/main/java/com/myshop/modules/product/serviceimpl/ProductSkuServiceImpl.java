package com.myshop.modules.product.serviceimpl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.cache.Cache;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductSku;
import com.myshop.modules.product.entity.enums.ProductAuthEnum;
import com.myshop.modules.product.entity.enums.ProductStatusEnum;
import com.myshop.modules.product.mapper.ProductSkuMapper;
import com.myshop.modules.product.service.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements ProductSkuService {

    @Autowired
    private Cache cache;

    /**
     * Cập nhật trạng thái sản phẩm sku
     *
     * @param product Thông tin sản phẩm (ID, Trạng thái đưa lên kệ/Trạng thái duyệt)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProductSkuStatus(Product product) {
        LambdaUpdateWrapper<ProductSku> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CharSequenceUtil.isNotEmpty(product.getId()), ProductSku::getProductId, product.getId());
        updateWrapper.eq(CharSequenceUtil.isNotEmpty(product.getStoreId()), ProductSku::getStoreId, product.getStoreId());
        updateWrapper.set(ProductSku::getDisplayStatus, product.getDisplayStatus());
        updateWrapper.set(ProductSku::getIsApproved, product.getIsApproved());
        updateWrapper.set(ProductSku::getDeleteFlag, product.getDeleteFlag());
        boolean updateStatus = this.update(updateWrapper);
        if (Boolean.TRUE.equals(updateStatus)) {
            List<ProductSku> productSkus = this.getProductSkuListByProductId(product.getId());
            for (ProductSku sku : productSkus) {
                cache.remove(ProductSkuService.getSkuCacheId(sku.getId()));
                if (ProductStatusEnum.UPPER.name().equals(product.getDisplayStatus()) && ProductAuthEnum.PASS.name().equals(product.getIsApproved())) {
                    cache.put(ProductSkuService.getSkuCacheId(sku.getId()), sku);
                }
            }
        }
    }

    @Override
    public List<ProductSku> getProductSkuListByProductId(String productId) {
        return this.list(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, productId));
    }
}
