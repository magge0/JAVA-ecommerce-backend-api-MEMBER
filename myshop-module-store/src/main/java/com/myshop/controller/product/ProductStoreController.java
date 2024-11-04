package com.myshop.controller.product;

import com.myshop.common.enums.ResultUtil;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.dto.ProductOperationDTO;
import com.myshop.modules.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Giao diện sản phẩm cho cửa hàng
 */
@RestController
@Slf4j
@Api(tags = "Giao diện sản phẩm cho cửa hàng")
@RequestMapping("/store/product/product")
public class ProductStoreController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Thêm sản phẩm mới")
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResultMessage<ProductOperationDTO> save(@Valid @RequestBody ProductOperationDTO productOperationDTO) {
        productService.addProduct(productOperationDTO);
        return ResultUtil.success();
    }
}
