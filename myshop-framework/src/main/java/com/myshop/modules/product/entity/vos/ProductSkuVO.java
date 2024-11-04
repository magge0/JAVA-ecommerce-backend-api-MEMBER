package com.myshop.modules.product.entity.vos;


import cn.hutool.core.bean.BeanUtil;
import com.myshop.modules.product.entity.dos.ProductSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * VO Thông số kỹ thuật sản phẩm
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductSkuVO extends ProductSku {

    private static final long serialVersionUID = -7651149660489332344L;

    @ApiModelProperty(value = "Danh sách thông số kỹ thuật")
    private List<ProductSpecValueVO> specList;

    @ApiModelProperty(value = "Hình ảnh sản phẩm")
    private List<String> productGalleryList;

    public ProductSkuVO(ProductSku productSku) {
        BeanUtil.copyProperties(productSku, this);
    }
}
