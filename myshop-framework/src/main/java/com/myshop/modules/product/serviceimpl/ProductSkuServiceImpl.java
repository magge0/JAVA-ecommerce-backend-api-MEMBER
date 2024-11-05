package com.myshop.modules.product.serviceimpl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.cache.Cache;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductSku;
import com.myshop.modules.product.entity.enums.ProductAuthEnum;
import com.myshop.modules.product.entity.enums.ProductStatusEnum;
import com.myshop.modules.product.entity.vos.ProductSkuVO;
import com.myshop.modules.product.entity.vos.ProductSpecValueVO;
import com.myshop.modules.product.mapper.ProductSkuMapper;
import com.myshop.modules.product.service.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<ProductSkuVO> getProductListByProductId(String productId) {
        List<ProductSku> list = this.list(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, productId));
        return this.getProductSkuVOList(list);
    }

    @Override
    public List<ProductSkuVO> getProductSkuVOList(List<ProductSku> list) {
        List<ProductSkuVO> productSkuVOS = new ArrayList<>();
        for (ProductSku productSku : list) {
            ProductSkuVO productSkuVO = this.getProductSkuVO(productSku);
            productSkuVOS.add(productSkuVO);
        }
        return productSkuVOS;
    }


    @Override
    public ProductSkuVO getProductSkuVO(ProductSku productSku) {
        // Khởi tạo thông tin sản phẩm
        ProductSkuVO productSkuVO = new ProductSkuVO(productSku);
        // Lấy thông tin SKU
        JSONObject jsonObject = JSONUtil.parseObj(productSku.getSpecs());
        // Lưu trữ thông tin SKU
        List<ProductSpecValueVO> specAttributeValueVOS = new ArrayList<>();
        // Lưu trữ album ảnh SKU
        List<String> productGalleryList = new ArrayList<>();
        // Duyệt qua các trường dữ liệu trong form SKU
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            ProductSpecValueVO productSpecValueVO = new ProductSpecValueVO();
            if ("images".equals(entry.getKey())) {
                productSpecValueVO.setSpecAttributeName(entry.getKey());
                List<String> specAttributeImages = JSONUtil.toList(JSONUtil.parseArray(entry.getValue()), String.class);
                productSpecValueVO.setSpecAttributeImages(specAttributeImages);
                productGalleryList = new ArrayList<>(specAttributeImages);
            } else {
                productSpecValueVO.setSpecAttributeName(entry.getKey());
                productSpecValueVO.setSpecAttributeValue(entry.getValue().toString());
            }
            specAttributeValueVOS.add(productSpecValueVO);
        }
        productSkuVO.setProductGalleryList(productGalleryList);
        productSkuVO.setSpecList(specAttributeValueVOS);
        return productSkuVO;
    }
}
