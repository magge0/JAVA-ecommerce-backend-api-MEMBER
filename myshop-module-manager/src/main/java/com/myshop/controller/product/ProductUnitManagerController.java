package com.myshop.controller.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myshop.common.enums.ResultUtil;
import com.myshop.common.vo.PageVO.PageVO;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.dos.ProductUnit;
import com.myshop.modules.product.service.ProductUnitService;
import com.myshop.orm.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Giao diện quản lý đơn vị đo lường sản phẩm cho quản trị viên
 */
@RestController
@Api(tags = "Giao diện quản lý đơn vị đo lường sản phẩm cho quản trị viên")
@RequestMapping("/manager/product/productUnit")
public class ProductUnitManagerController {

    @Autowired
    private ProductUnitService productUnitService;

    @ApiOperation(value = "Phân trang lấy danh sách đơn vị đo lường sản phẩm")
    @GetMapping
    public ResultMessage<IPage<ProductUnit>> getByPage(PageVO pageVO) {
        return ResultUtil.data(productUnitService.page(PageUtil.buildPage(pageVO)));
    }

    @ApiOperation(value = "Lấy thông tin đơn vị đo lường sản phẩm")
    @ApiImplicitParam(name = "id", value = "ID của đơn vị đo lường", required = true, paramType = "path")
    @GetMapping("/{id}")
    public ResultMessage<ProductUnit> getById(@NotNull @PathVariable String id) {
        return ResultUtil.data(productUnitService.getById(id));
    }

    @ApiOperation(value = "Thêm đơn vị đo lường sản phẩm")
    @PostMapping
    public ResultMessage<ProductUnit> add(@Valid ProductUnit productUnit) {
        productUnitService.save(productUnit);
        return ResultUtil.data(productUnit);
    }

    @ApiOperation(value = "Chỉnh sửa đơn vị đo lường sản phẩm")
    @ApiImplicitParam(name = "id", value = "ID của đơn vị đo lường", required = true, paramType = "path")
    @PutMapping("/{id}")
    public ResultMessage<ProductUnit> update(@NotNull @PathVariable String id, @Valid ProductUnit productUnit) {
        productUnit.setId(id);
        productUnitService.updateById(productUnit);
        return ResultUtil.data(productUnit);
    }

    @ApiOperation(value = "Xóa đơn vị đo lường sản phẩm")
    @ApiImplicitParam(name = "ids", value = "Danh sách ID của đơn vị đo lường", required = true, paramType = "path")
    @DeleteMapping("/{ids}")
    public ResultMessage<Object> remove(@NotNull @PathVariable List<String> ids) {
        productUnitService.removeByIds(ids);
        return ResultUtil.success();
    }
}
