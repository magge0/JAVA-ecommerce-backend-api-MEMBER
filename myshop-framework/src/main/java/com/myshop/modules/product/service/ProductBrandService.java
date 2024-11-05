package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.product.entity.dos.ProductBrand;
import com.myshop.modules.product.entity.dto.ProductBrandPageDTO;
import com.myshop.modules.product.entity.vos.ProductBrandVO;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductBrandService extends IService<ProductBrand> {

    /**
     * Lấy danh sách thương hiệu theo phân trang dựa trên điều kiện
     *
     * @param page Tham số điều kiện
     * @return Danh sách thương hiệu
     */
    IPage<ProductBrand> getProductBrandsByPage(ProductBrandPageDTO page);

    /**
     * Thêm thương hiệu
     *
     * @param brandVO Thông tin thương hiệu
     * @return Kết quả thêm
     */
    boolean addProductBrand(@Valid ProductBrandVO brandVO);

    /**
     * Cập nhật thương hiệu
     *
     * @param brandVO Thông tin thương hiệu
     * @return Kết quả cập nhật
     */
    boolean updateProductBrand(@Valid ProductBrandVO brandVO);

    /**
     * Cập nhật trạng thái có thể sử dụng của thương hiệu
     *
     * @param brandId ID thương hiệu
     * @param disable Có không thể sử dụng hay không
     * @return Kết quả cập nhật
     */
    boolean productBrandDisable(String brandId, Boolean disable);

    /**
     * Xóa thương hiệu
     *
     * @param ids Danh sách ID thương hiệu
     */
    void deleteProductBrands(List<String> ids);
}

