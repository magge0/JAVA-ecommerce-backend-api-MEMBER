package com.myshop.controller.product;

import com.myshop.modules.product.entity.dos.ProductSpecification;
import com.myshop.modules.product.service.ProductCategorySpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Giao diện quản lý cửa hàng, thông số kỹ thuật
 */
@RestController
@Api(tags = "Giao diện quản lý cửa hàng, thông số kỹ thuật")
@RequestMapping("/store/product/spec")
public class ProductSpecificationStoreController {

    @Autowired
    private ProductCategorySpecificationService productCategorySpecificationService;

    @GetMapping(value = "/{categoryId}")
    @ApiOperation(value = "Lấy thông số kỹ thuật phân loại")
    public List<ProductSpecification> getProductSpecifications(@PathVariable String categoryId) {
        return productCategorySpecificationService.getProductCategorySpecList(categoryId);
    }
}
