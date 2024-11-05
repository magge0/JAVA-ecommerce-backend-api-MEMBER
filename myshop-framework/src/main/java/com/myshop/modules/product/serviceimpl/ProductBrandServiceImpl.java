package com.myshop.modules.product.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.common.enums.ResultCode;
import com.myshop.common.exception.ServiceException;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductBrand;
import com.myshop.modules.product.entity.dos.ProductCategoryBrand;
import com.myshop.modules.product.entity.dto.ProductBrandPageDTO;
import com.myshop.modules.product.entity.vos.ProductBrandVO;
import com.myshop.modules.product.mapper.ProductBrandMapper;
import com.myshop.modules.product.service.ProductBrandService;
import com.myshop.modules.product.service.ProductCategoryBrandService;
import com.myshop.modules.product.service.ProductCategoryService;
import com.myshop.modules.product.service.ProductService;
import com.myshop.orm.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductBrandServiceImpl extends ServiceImpl<ProductBrandMapper, ProductBrand> implements ProductBrandService {

    @Autowired
    private ProductCategoryBrandService productCategoryBrandService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;

    @Override
    public IPage<ProductBrand> getProductBrandsByPage(ProductBrandPageDTO page) {
        LambdaQueryWrapper<ProductBrand> queryWrapper = new LambdaQueryWrapper<>();
        if (page.getName() != null) {
            queryWrapper.like(ProductBrand::getName, page.getName());
        }
        return this.page(PageUtil.buildPage(page), queryWrapper);
    }

    @Override
    public boolean addProductBrand(ProductBrandVO brandVO) {
        if (getOne(new LambdaQueryWrapper<ProductBrand>().eq(ProductBrand::getName, brandVO.getName())) != null) {
            throw new ServiceException(ResultCode.PRODUCT_BRAND_NAME_EXIST_ERROR);
        }
        return this.save(brandVO);
    }

    @Override
    public boolean updateProductBrand(ProductBrandVO brandVO) {
        this.checkExist(brandVO.getId());
        if (getOne(new LambdaQueryWrapper<ProductBrand>().eq(ProductBrand::getName, brandVO.getName()).ne(ProductBrand::getId, brandVO.getId())) != null) {
            throw new ServiceException(ResultCode.PRODUCT_BRAND_NAME_EXIST_ERROR);
        }
        return this.updateById(brandVO);
    }

    @Override
    public boolean productBrandDisable(String brandId, Boolean disable) {
        ProductBrand productBrand = this.checkExist(brandId);
        // Nếu cần vô hiệu hóa, cần kiểm tra trước các mối quan hệ liên kết
        if (Boolean.TRUE.equals(disable)) {
            List<String> ids = new ArrayList<>();
            ids.add(brandId);
            validateBrandBindings(ids);
        }
        productBrand.setDeleteFlag(disable);
        return updateById(productBrand);
    }

    @Override
    public void deleteProductBrands(List<String> ids) {
        validateBrandBindings(ids);
        this.removeByIds(ids);
    }

    /**
     * Kiểm tra các mối quan hệ liên kết
     *
     * @param brandIds Danh sách ID thương hiệu
     */
    private void validateBrandBindings(List<String> brandIds) {
        // Kiểm tra các mối quan hệ liên kết với danh mục
        List<ProductCategoryBrand> categoryBrands = productCategoryBrandService.getCategoryBrandsByBrandIds(brandIds);
        if (!categoryBrands.isEmpty()) {
            List<String> categoryIds = categoryBrands.stream().map(ProductCategoryBrand::getCategoryId).collect(Collectors.toList());
            throw new ServiceException(ResultCode.PRODUCT_BRAND_USE_DISABLE_ERROR, JSONUtil.toJsonStr(productCategoryService.getProductCategoryNameByIds(categoryIds)));
        }

        // Kiểm tra các mối quan hệ liên kết với sản phẩm
        List<Product> product = productService.getByBrandIds(brandIds);
        if (!product.isEmpty()) {
            List<String> productNames = product.stream().map(Product::getProductName).collect(Collectors.toList());
            throw new ServiceException(ResultCode.PRODUCT_BRAND_BIND_ERROR, JSONUtil.toJsonStr(productNames));
        }
    }

    /**
     * Kiểm tra sự tồn tại
     *
     * @param brandId ID thương hiệu
     * @return Thương hiệu
     */
    private ProductBrand checkExist(String brandId) {
        ProductBrand productBrand = getById(brandId);
        if (productBrand == null) {
            log.error("Thương hiệu có ID là " + brandId + " không tồn tại");
            throw new ServiceException(ResultCode.PRODUCT_BRAND_NOT_EXIST);
        }
        return productBrand;
    }

}
