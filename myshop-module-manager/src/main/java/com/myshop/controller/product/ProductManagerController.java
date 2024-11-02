package com.myshop.controller.product;

import com.myshop.modules.product.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Hệ thống quản trị, API quản lý sản phẩm")
@RequestMapping("/manager/product/product")
public class ProductManagerController {

    @Autowired
    ProductService productService;

}
