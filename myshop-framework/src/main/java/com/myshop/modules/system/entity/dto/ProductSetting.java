package com.myshop.modules.system.entity.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Cài đặt sản phẩm
 */
@Data
public class ProductSetting implements Serializable {

    private static final long serialVersionUID = -4132785717179910025L;
    @ApiModelProperty(value = "Có bật phê duyệt sản phẩm hay không")
    private Boolean productCheck;

    @ApiModelProperty(value = "Chiều rộng ảnh nhỏ")
    private Integer smallImageWidth;

    @ApiModelProperty(value = "Chiều cao ảnh nhỏ")
    private Integer smallImageHeight;

    @ApiModelProperty(value = "Chiều rộng ảnh thu nhỏ")
    private Integer thumbnailImageWidth;

    @ApiModelProperty(value = "Chiều cao ảnh thu nhỏ")
    private Integer thumbnailImageHeight;

    @ApiModelProperty(value = "Chiều rộng ảnh gốc")
    private Integer originalImageWidth;

    @ApiModelProperty(value = "Chiều cao ảnh gốc")
    private Integer originalImageHeight;

}