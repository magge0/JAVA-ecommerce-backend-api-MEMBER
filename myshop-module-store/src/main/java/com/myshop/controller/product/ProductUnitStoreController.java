package com.myshop.controller.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myshop.common.enums.ResultUtil;
import com.myshop.common.vo.PageVO.PageVO;
import com.myshop.common.vo.ResultMessage;
import com.myshop.modules.product.entity.dos.ProductUnit;
import com.myshop.modules.product.service.ProductUnitService;
import com.myshop.orm.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Giao diện quản lý đơn vị đo lường sản phẩm cho cửa hàng
 */
@RestController
@Api(tags = "Giao diện quản lý đơn vị đo lường sản phẩm cho cửa hàng")
@RequestMapping("/store/product/productUnit")
public class ProductUnitStoreController {

    @Autowired
    private ProductUnitService productUnitService;

    @ApiOperation(value = "Phân trang lấy danh sách đơn vị đo lường sản phẩm")
    @GetMapping
    public ResultMessage<IPage<ProductUnit>> getByPage(PageVO pageVO) {
        return ResultUtil.data(productUnitService.page(PageUtil.buildPage(pageVO)));
    }
}
