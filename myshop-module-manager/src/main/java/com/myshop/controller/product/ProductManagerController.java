package com.myshop.controller.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myshop.common.aop.annotation.AntiDuplicateSubmission;
import com.myshop.common.enums.ResultCode;
import com.myshop.common.enums.ResultUtil;
import com.myshop.common.exception.ServiceException;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductSearchParams;
import com.myshop.modules.product.entity.enums.ProductAuthEnum;
import com.myshop.modules.product.entity.enums.ProductStatusEnum;
import com.myshop.modules.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Api(tags = "Hệ thống quản trị, API quản lý sản phẩm")
@RequestMapping("/manager/product/product")
public class ProductManagerController {

    @Autowired
    ProductService productService;

    @ApiOperation(value = "Phân trang lấy danh sách sản phẩm chờ duyệt")
    @GetMapping(value = "/auth/pending")
    public ResultMessage<IPage<Product>> getAuthPage(ProductSearchParams productSearchParams) {
        productSearchParams.setIsApproved(ProductAuthEnum.PENDING.name());
        return ResultUtil.data(productService.queryByParams(productSearchParams));
    }


    @AntiDuplicateSubmission
    @ApiOperation(value = "Quản trị viên đưa sản phẩm lên kệ", notes = "Sử dụng khi quản trị viên đưa sản phẩm lên kệ")
    @PutMapping(value = "/{productId}/up")
    @ApiImplicitParams({@ApiImplicitParam(name = "productId", value = "ID sản phẩm", required = true, allowMultiple = true)})
    public ResultMessage<Object> upProduct(@PathVariable List<String> productId) {
        if (Boolean.TRUE.equals(productService.updateProductMarketAble(productId, ProductStatusEnum.UPPER, ""))) {
            return ResultUtil.success();
        }
        throw new ServiceException(ResultCode.PRODUCT_UPPER_ERROR);
    }

    @AntiDuplicateSubmission
    @ApiOperation(value = "Quản trị viên đưa sản phẩm xuống kệ", notes = "Quản trị viên sử dụng chức năng này để đưa sản phẩm xuống kệ")
    @ApiImplicitParams({@ApiImplicitParam(name = "productId", value = "ID sản phẩm", required = true, paramType = "query", allowMultiple = true), @ApiImplicitParam(name = "reason", value = "Lý do xuống kệ", required = true, paramType = "query")})
    @PutMapping(value = "/{productId}/under")
    public ResultMessage<Object> underProduct(@PathVariable String productId, @NotEmpty(message = "Lý do xuống kệ không được để trống") @RequestParam String reason) {
        List<String> productIds = Arrays.asList(productId.split(","));
        if (Boolean.TRUE.equals(productService.managerUpdateProductMarketAble(productIds, ProductStatusEnum.DOWN, reason))) {
            return ResultUtil.success();
        }
        throw new ServiceException(ResultCode.PRODUCT_UNDER_ERROR);
    }
}
