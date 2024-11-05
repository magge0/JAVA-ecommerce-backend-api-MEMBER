package com.myshop.controller.product;

import com.myshop.modules.product.entity.vos.ProductParameterGroupVO;
import com.myshop.modules.product.service.ProductCategoryParameterGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Giao diện quản lý nhóm tham số liên kết danh mục cho cửa hàng
 */
@RestController
@Api(tags = "Giao diện quản lý nhóm tham số liên kết danh mục cho cửa hàng")
@RequestMapping("/store/product/categoryParameters")
public class ProductCategoryParameterGroupStoreController {

    @Autowired
    private ProductCategoryParameterGroupService productCategoryParameterGroupService;

    @ApiOperation(value = "Truy vấn thông tin tham số được liên kết với danh mục")
    @GetMapping(value = "/{id}")
    @ApiImplicitParam(name = "id", value = "ID danh mục", required = true, dataType = "String", paramType = "path")
    public List<ProductParameterGroupVO> getCategoryParam(@PathVariable("id") String categoryId) {
        return productCategoryParameterGroupService.getProductCategoryParams(categoryId);
    }
}
