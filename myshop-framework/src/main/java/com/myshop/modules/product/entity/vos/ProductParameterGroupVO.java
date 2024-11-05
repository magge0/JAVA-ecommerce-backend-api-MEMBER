package com.myshop.modules.product.entity.vos;


import com.myshop.modules.product.entity.dos.ProductParameters;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * VO nhóm tham số
 */
@ApiModel
@Data
public class ProductParameterGroupVO implements Serializable {

    private static final long serialVersionUID = 724447321881170297L;

    @ApiModelProperty("Bộ sưu tập tham số liên kết với nhóm tham số")
    private List<ProductParameters> params;

    @ApiModelProperty(value = "Tên nhóm tham số")
    private String groupName;

    @ApiModelProperty(value = "ID nhóm tham số")
    private String groupId;


}
