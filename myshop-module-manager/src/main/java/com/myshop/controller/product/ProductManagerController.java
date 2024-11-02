package com.myshop.controller.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myshop.common.enums.ResultUtil;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductSearchParams;
import com.myshop.modules.product.entity.enums.ProductAuthEnum;
import com.myshop.modules.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Hệ thống quản trị, API quản lý sản phẩm")
@RequestMapping("/manager/product/product")
public class ProductManagerController {

    @Autowired
    ProductService productService;

    @ApiOperation(value = "Phân trang lấy danh sách sản phẩm chờ duyệt")
    @GetMapping(value = "/auth/list")
    public ResultMessage<IPage<Product>> getAuthPage(ProductSearchParams productSearchParams) {
        productSearchParams.setIsApproved(ProductAuthEnum.PENDING.name());
        return ResultUtil.data(productService.queryByParams(productSearchParams));
    }


}
