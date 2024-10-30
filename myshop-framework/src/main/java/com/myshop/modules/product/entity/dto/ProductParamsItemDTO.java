package com.myshop.modules.product.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Thông số sản phẩm
 */
@Data
@ApiModel(value = "Danh sách thông số sản phẩm")
public class ProductParamsItemDTO implements Serializable {

    private static final long serialVersionUID = -8823775607604091035L;

    @ApiModelProperty(value = "ID thông số")
    private String paramId;

    @ApiModelProperty(value = "Tên thông số")
    private String paramName;

    @ApiModelProperty(value = "Giá trị thông số")
    private String paramValue;

    @ApiModelProperty(value = "Cho phép lập chỉ mục, 0 không lập chỉ mục, 1 lập chỉ mục")
    private Integer isIndex = 0;

    @ApiModelProperty(value = "Bắt buộc nhập, 0 không hiển thị, 1 hiển thị")
    private Integer required = 0;

    @ApiModelProperty(value = "Thứ tự sắp xếp")
    private Integer sort;
}
