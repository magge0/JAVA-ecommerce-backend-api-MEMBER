package com.myshop.controller.product;

import com.myshop.common.enums.ResultCode;
import com.myshop.common.enums.ResultUtil;
import com.myshop.common.exception.ServiceException;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.dos.ProductCategory;
import com.myshop.modules.product.entity.dto.ProductCategorySearchParams;
import com.myshop.modules.product.entity.vos.ProductCategoryVO;
import com.myshop.modules.product.service.ProductCategoryService;
import com.myshop.modules.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ApiOperation(value = "Thêm loại sản phẩm")
    public ResultMessage<ProductCategory> saveCategory(@Valid ProductCategory productCategory) {
        // Không phải loại sản phẩm cấp cao nhất
        if (productCategory.getParentId() != null && !"0".equals(productCategory.getParentId())) {
            ProductCategory parent = productCategoryService.getById(productCategory.getParentId());
            if (parent == null) {
                throw new ServiceException(ResultCode.PRODUCT_CATEGORY_PARENT_NOT_EXIST);
            }
            if (productCategory.getLevel() >= 4) {
                throw new ServiceException(ResultCode.PRODUCT_CATEGORY_BEYOND_THREE);
            }
        }
        if (productCategoryService.saveCategory(productCategory)) {
            return ResultUtil.data(productCategory);
        }
        throw new ServiceException(ResultCode.PRODUCT_CATEGORY_SAVE_ERROR);
    }

    @PutMapping
    @ApiOperation(value = "Sửa đổi loại sản phẩm")
    public ResultMessage<ProductCategory> updateCategory(@Valid ProductCategoryVO productCategory) {
        ProductCategory catTemp = productCategoryService.getById(productCategory.getId());
        if (catTemp == null) {
            throw new ServiceException(ResultCode.PRODUCT_BRAND_NOT_EXIST); // Loại sản phẩm không tồn tại
        }

        productCategoryService.updateCategory(productCategory); // Cập nhật loại sản phẩm
        return ResultUtil.data(productCategory); // Trả về thông tin loại sản phẩm được cập nhật
    }

    @DeleteMapping(value = "/{id}")
    @ApiImplicitParam(name = "id", value = "ID loại sản phẩm", required = true, paramType = "path", dataType = "String")
    @ApiOperation(value = "Xóa loại sản phẩm theo ID")
    public ResultMessage<ProductCategory> del(@NotNull @PathVariable String id) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setParentId(id);
        List<ProductCategory> list = productCategoryService.findByAllBySortOrder(productCategory);
        if (list != null && !list.isEmpty()) {
            throw new ServiceException(ResultCode.PRODUCT_CATEGORY_HAS_CHILDREN);

        }
        // Kiểm tra số lượng sản phẩm thuộc loại sản phẩm này
        long count = productService.getProductCountByCategory(id);
        if (count > 0) {
            throw new ServiceException(ResultCode.CATEGORY_HAS_PRODUCT);
        }
        productCategoryService.delete(id); // Xóa loại sản phẩm
        return ResultUtil.success(); // Trả về thông báo thành công
    }

    @PutMapping(value = "/enable/{id}")
    @ApiImplicitParams({@ApiImplicitParam(name = "goodsId", value = "ID loại sản phẩm", required = true, paramType = "path", dataType = "String")})
    @ApiOperation(value = "Bật/Tắt loại sản phẩm từ phía quản trị")
    public ResultMessage<Object> enable(@PathVariable String id, @RequestParam Boolean enable) {

        ProductCategory productCategory = productCategoryService.getById(id);
        if (productCategory == null) {
            throw new ServiceException(ResultCode.PRODUCT_CATEGORY_NOT_EXIST);
        }
        productCategoryService.updateProductCategoryStatus(id, enable);
        return ResultUtil.success();
    }
}
