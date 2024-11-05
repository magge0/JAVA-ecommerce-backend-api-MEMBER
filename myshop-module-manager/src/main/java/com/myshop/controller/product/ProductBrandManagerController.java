package com.myshop.controller.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myshop.common.enums.ResultCode;
import com.myshop.common.enums.ResultUtil;
import com.myshop.common.exception.ServiceException;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.dos.ProductBrand;
import com.myshop.modules.product.entity.dto.ProductBrandPageDTO;
import com.myshop.modules.product.entity.vos.ProductBrandVO;
import com.myshop.modules.product.service.ProductBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Giao diện thương hiệu cho quản trị viên
 */
@RestController
@Api(tags = "Giao diện thương hiệu cho quản trị viên")
@RequestMapping("/manager/product/brand")
public class ProductBrandManagerController {

    @Autowired
    private ProductBrandService productBrandService;

    @ApiOperation(value = "Lấy thông tin theo ID")
    @ApiImplicitParam(name = "id", value = "ID thương hiệu", required = true, dataType = "String", paramType = "path")
    @GetMapping(value = "/{id}")
    public ResultMessage<ProductBrand> get(@NotNull @PathVariable String id) {
        return ResultUtil.data(productBrandService.getById(id));
    }

    @GetMapping(value = "/all")
    @ApiOperation(value = "Lấy tất cả thương hiệu khả dụng")
    public List<ProductBrand> all() {
        List<ProductBrand> list = productBrandService.list(new QueryWrapper<ProductBrand>().eq("delete_flag", 0));
        return list;
    }

    @ApiOperation(value = "Lấy dữ liệu phân trang")
    @GetMapping(value = "/page")
    public ResultMessage<IPage<ProductBrand>> page(ProductBrandPageDTO page) {
        return ResultUtil.data(productBrandService.getProductBrandsByPage(page));
    }

    @ApiOperation(value = "Thêm mới thương hiệu")
    @PostMapping
    public ResultMessage<ProductBrandVO> add(@Valid ProductBrandVO brand) {
        if (productBrandService.addProductBrand(brand)) {
            return ResultUtil.data(brand);
        }
        throw new ServiceException(ResultCode.PRODUCT_BRAND_SAVE_ERROR);
    }

    @ApiOperation(value = "Cập nhật dữ liệu")
    @ApiImplicitParam(name = "id", value = "ID thương hiệu", required = true, dataType = "String", paramType = "path")
    @PutMapping("/{id}")
    public ResultMessage<ProductBrandVO> update(@PathVariable String id, @Valid ProductBrandVO brandVO) {
        brandVO.setId(id);
        if (productBrandService.updateProductBrand(brandVO)) {
            return ResultUtil.data(brandVO);
        }
        throw new ServiceException(ResultCode.PRODUCT_BRAND_UPDATE_ERROR);
    }

    @ApiOperation(value = "Vô hiệu hóa thương hiệu từ backend")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "ID thương hiệu", required = true, dataType = "String", paramType = "path"), @ApiImplicitParam(name = "disable", value = "Có thể sử dụng hay không", required = true, dataType = "boolean", paramType = "query")})
    @PutMapping(value = "/disable/{id}")
    public ResultMessage<Object> disable(@PathVariable String id, @RequestParam Boolean disable) {
        if (productBrandService.productBrandDisable(id, disable)) {
            return ResultUtil.success();
        }
        throw new ServiceException(ResultCode.PRODUCT_BRAND_DISABLE_ERROR);
    }

    @ApiOperation(value = "Xóa hàng loạt")
    @ApiImplicitParam(name = "ids", value = "ID thương hiệu", required = true, dataType = "String", allowMultiple = true, paramType = "path")
    @DeleteMapping(value = "/{ids}")
    public ResultMessage<Object> delByIds(@PathVariable List<String> ids) {
        productBrandService.deleteProductBrands(ids);
        return ResultUtil.success(ResultCode.SUCCESS);
    }
}
