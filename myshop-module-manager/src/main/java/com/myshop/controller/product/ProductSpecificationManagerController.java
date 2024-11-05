package com.myshop.controller.product;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myshop.common.enums.ResultUtil;
import com.myshop.common.vo.PageVO.PageVO;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.dos.ProductSpecification;
import com.myshop.modules.product.service.ProductSpecificationService;
import com.myshop.orm.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Giao diện quản lý sản phẩm, thông số kỹ thuật
 */
@RestController
@Api(tags = "Giao diện quản lý sản phẩm, thông số kỹ thuật")
@RequestMapping("/manager/product/spec")
public class ProductSpecificationManagerController {

    @Autowired
    private ProductSpecificationService productSpecificationService;

    @GetMapping("/all")
    @ApiOperation(value = "Lấy tất cả thông số kỹ thuật có thể sử dụng")
    public ResultMessage<List<ProductSpecification>> getAll() {
        return ResultUtil.data(productSpecificationService.list());
    }

    @GetMapping
    @ApiOperation(value = "Tìm kiếm thông số kỹ thuật")
    public ResultMessage<Page<ProductSpecification>> page(String specName, PageVO page) {
        LambdaQueryWrapper<ProductSpecification> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(CharSequenceUtil.isNotEmpty(specName), ProductSpecification::getSpecName, specName);
        return ResultUtil.data(productSpecificationService.page(PageUtil.buildPage(page), lambdaQueryWrapper));
    }

    @PostMapping
    @ApiOperation(value = "Lưu thông số kỹ thuật")
    public ResultMessage<Object> add(@Valid ProductSpecification specification) {
        productSpecificationService.save(specification);
        return ResultUtil.success();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Thay đổi thông số kỹ thuật")
    public ResultMessage<Object> update(@Valid ProductSpecification specification, @PathVariable String id) {
        specification.setId(id);
        return ResultUtil.data(productSpecificationService.saveOrUpdate(specification));
    }

    @DeleteMapping("/{ids}")
    @ApiImplicitParam(name = "ids", value = "ID thông số kỹ thuật", required = true, dataType = "String", allowMultiple = true, paramType = "path")
    @ApiOperation(value = "Xóa hàng loạt")
    public ResultMessage<Object> delAll(@PathVariable List<String> ids) {
        return ResultUtil.data(productSpecificationService.deleteProductSpecification(ids));
    }
}
