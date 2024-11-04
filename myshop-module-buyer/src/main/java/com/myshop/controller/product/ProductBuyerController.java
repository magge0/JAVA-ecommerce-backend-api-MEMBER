package com.myshop.controller.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myshop.common.enums.ResultUtil;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductSearchParams;
import com.myshop.modules.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
        return ResultUtil.data(productService.queryByParams(productSearchParams));
    }
}
