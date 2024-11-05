package com.myshop.controller.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myshop.common.enums.ResultCode;
import com.myshop.common.enums.ResultUtil;
import com.myshop.common.exception.ServiceException;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.dos.ProductCategoryParameterGroup;
import com.myshop.modules.product.entity.dos.ProductParameters;
import com.myshop.modules.product.entity.vos.ProductParameterGroupVO;
import com.myshop.modules.product.service.ProductCategoryParameterGroupService;
import com.myshop.modules.product.service.ProductParametersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Giao diện quản lý, liên kết nhóm tham số phân loại
 */
@RestController
@Api(tags = "Giao diện quản lý, liên kết nhóm tham số phân loại")
@RequestMapping("/manager/product/categoryParameters")
public class ProductCategoryParameterGroupManagerController {

    @Autowired
    private ProductParametersService productParametersService;

    @Autowired
    private ProductCategoryParameterGroupService productCategoryParameterGroupService;

    @ApiOperation(value = "Truy vấn thông tin tham số được liên kết trong một phân loại")
    @GetMapping(value = "/{categoryId}")
    @ApiImplicitParam(value = "ID phân loại", required = true, dataType = "String", paramType = "path")
    public ResultMessage<List<ProductParameterGroupVO>> getProductCategoryParam(@PathVariable String categoryId) {
        return ResultUtil.data(productCategoryParameterGroupService.getProductCategoryParams(categoryId));
    }

    @ApiOperation(value = "Lưu dữ liệu")
    @PostMapping
    public ResultMessage<ProductCategoryParameterGroup> saveOrUpdate(@Validated ProductCategoryParameterGroup productCategoryParameterGroup) {
        if (productCategoryParameterGroupService.save(productCategoryParameterGroup)) {
            return ResultUtil.data(productCategoryParameterGroup);
        }
        throw new ServiceException(ResultCode.PRODUCT_CATEGORY_PARAMETER_SAVE_ERROR);
    }

    @ApiOperation(value = "Cập nhật dữ liệu")
    @PutMapping
    public ResultMessage<ProductCategoryParameterGroup> update(@Validated ProductCategoryParameterGroup productCategoryParameterGroup) {

        if (productCategoryParameterGroupService.updateById(productCategoryParameterGroup)) {
            return ResultUtil.data(productCategoryParameterGroup);
        }
        throw new ServiceException(ResultCode.PRODUCT_CATEGORY_PARAMETER_UPDATE_ERROR);
    }

    @ApiOperation(value = "Xóa nhóm tham số theo id")
    @ApiImplicitParam(name = "id", value = "ID của nhóm tham số", required = true, dataType = "String", paramType = "path")
    @DeleteMapping(value = "/{id}")
    public ResultMessage<Object> del(@PathVariable String id) {
        productParametersService.remove(new QueryWrapper<ProductParameters>().eq("group_id", id));
        productCategoryParameterGroupService.removeById(id);
        return ResultUtil.success();
    }
}
