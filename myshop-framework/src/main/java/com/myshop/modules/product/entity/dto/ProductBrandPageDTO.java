package com.myshop.modules.product.entity.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.myshop.common.vo.PageVO.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO cho thương hiệu sản phẩm
 */
@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "DTO cho thương hiệu sản phẩm")
public class ProductBrandPageDTO extends PageVO {

    private static final long serialVersionUID = 8906820496037326039L;

    @ApiModelProperty(value = "Tên thương hiệu")
    private String name;
}
