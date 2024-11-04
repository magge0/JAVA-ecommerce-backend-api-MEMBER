package com.myshop.controller.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myshop.common.enums.ResultUtil;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductSearchParams;
import com.myshop.modules.product.entity.vos.ProductVO;
import com.myshop.modules.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Giao diện sản phẩm cho người mua
 */
@Slf4j
@Api(tags = "Giao diện sản phẩm cho người mua")
@RestController
@RequestMapping("/buyer/product/product")
public class ProductBuyerController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Lấy danh sách sản phẩm phân trang")
    @GetMapping
    public ResultMessage<IPage<Product>> getByPage(ProductSearchParams productSearchParams) {
        return ResultUtil.data(productService.getByParams(productSearchParams));
    }

    @ApiOperation(value = "Lấy thông tin sản phẩm theo ID")
    @ApiImplicitParam(name = "productId", value = "ID sản phẩm", required = true, paramType = "path", dataType = "Long")
    @GetMapping(value = "/get/{productId}")
    public ResultMessage<ProductVO> get(@NotNull(message = "ID sản phẩm không được để trống") @PathVariable("productId") String productId) {
        return ResultUtil.data(productService.getProductVO(productId));
    }
}
