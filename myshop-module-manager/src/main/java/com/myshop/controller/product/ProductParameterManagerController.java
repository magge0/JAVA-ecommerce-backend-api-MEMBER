package com.myshop.controller.product;

import com.myshop.common.enums.ResultCode;
import com.myshop.common.enums.ResultUtil;
import com.myshop.common.exception.ServiceException;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.dos.ProductParameters;
import com.myshop.modules.product.service.ProductParametersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Giao diện quản lý nhóm tham số liên kết với danh mục, dành cho quản trị viên
 */
@RestController
@Api(tags = "Giao diện quản lý nhóm tham số liên kết với danh mục, dành cho quản trị viên")
@RequestMapping("/manager/product/parameters")
public class ProductParameterManagerController {

    @Autowired
    private ProductParametersService productParametersService;

    @ApiOperation(value = "Thêm tham số")
    @PostMapping
    public ResultMessage<ProductParameters> add(@Valid ProductParameters productParameters) {
        if (productParametersService.save(productParameters)) {
            return ResultUtil.data(productParameters);
        }
        throw new ServiceException(ResultCode.PRODUCT_PARAMETER_SAVE_ERROR);

    }

    @ApiOperation(value = "Chỉnh sửa tham số")
    @PutMapping
    public ResultMessage<ProductParameters> update(@Valid ProductParameters productParameters) {
        if (productParametersService.updateProductParameter(productParameters)) {
            return ResultUtil.data(productParameters);
        }
        throw new ServiceException(ResultCode.PRODUCT_PARAMETER_UPDATE_ERROR);
    }

    @ApiOperation(value = "Xóa tham số theo ID")
    @ApiImplicitParam(name = "id", value = "ID của tham số", required = true, paramType = "path")
    @DeleteMapping(value = "/{id}")
    public ResultMessage<Object> del(@PathVariable String id) {
        productParametersService.removeById(id);
        return ResultUtil.success();

    }
}
