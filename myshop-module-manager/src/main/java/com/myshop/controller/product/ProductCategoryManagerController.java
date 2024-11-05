package com.myshop.controller.product;

import com.myshop.common.enums.ResultUtil;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.dos.ProductCategory;
import com.myshop.modules.product.entity.dto.ProductCategorySearchParams;
import com.myshop.modules.product.entity.vos.ProductCategoryVO;
import com.myshop.modules.product.service.ProductCategoryService;
import com.myshop.modules.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Giao diện quản lý danh mục sản phẩm cho quản trị viên
 */
@RestController
@Api(tags = "Giao diện quản lý danh mục sản phẩm cho quản trị viên")
@RequestMapping("/manager/product/category")
@CacheConfig(cacheNames = "product_category")
public class ProductCategoryManagerController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Truy vấn danh sách tất cả các danh mục con của một danh mục")
    @ApiImplicitParam(name = "parentId", value = "ID của danh mục cha, danh mục cấp cao nhất có ID là 0", required = true, dataType = "String", paramType = "path")
    @GetMapping(value = "/{parentId}/children")
    public ResultMessage<List<ProductCategory>> list(@PathVariable String parentId) {
        return ResultUtil.data(this.productCategoryService.getAllCategories(parentId));
    }

    @ApiOperation(value = "Truy vấn danh sách tất cả các danh mục")
    @GetMapping(value = "/all")
    public ResultMessage<List<ProductCategoryVO>> list(ProductCategorySearchParams categorySearchParams) {
        return ResultUtil.data(this.productCategoryService.getAllChildren(categorySearchParams));
    }
}
