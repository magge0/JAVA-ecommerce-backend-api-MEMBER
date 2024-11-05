package com.myshop.modules.product.entity.vos;


import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.Wholesale;
import com.myshop.modules.product.entity.dto.ProductParamsDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * VO sản phẩm
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductVO extends Product {

    private static final long serialVersionUID = 6377723919990713567L;

    @ApiModelProperty(value = "Tên danh mục")
    private List<String> categoryName;

    @ApiModelProperty(value = "Thông số sản phẩm")
    private List<ProductParamsDTO> productParamsDTOList;

    @ApiModelProperty(value = "Hình ảnh sản phẩm")
    private List<String> productGalleryList;

    @ApiModelProperty(value = "Danh sách SKU")
    private List<ProductSkuVO> skuList;

    @ApiModelProperty(value = "Danh sách quy tắc mua sỉ sản phẩm")
    private List<Wholesale> wholesaleRules;
}