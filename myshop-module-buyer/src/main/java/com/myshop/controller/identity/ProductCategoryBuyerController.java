package com.myshop.controller.identity;


import com.myshop.common.enums.ResultUtil;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.vos.ProductCategoryVO;
import com.myshop.modules.product.service.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Giao diện phân loại hàng hóa dành cho người mua
 */
@RestController
@Api(tags = "Giao diện phân loại hàng hóa dành cho người mua")
@RequestMapping("/buyer/product/category")
public class ProductCategoryBuyerController {
    /**
     * Phân loại hàng hóa
     */
    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation(value = "Lấy danh sách phân loại hàng hóa")
    @ApiImplicitParam(name = "parentId", value = "ID phân loại cha, tất cả phân loại là: 0", required = true, dataType = "Long", paramType = "path")
    @GetMapping(value = "/{parentId}")
    public ResultMessage<List<ProductCategoryVO>> getAllCategories(@NotNull(message = "ID phân loại không được để trống") @PathVariable String parentId) {
        return ResultUtil.data(productCategoryService.listSubCategories(parentId));
    }
}