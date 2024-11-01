package com.myshop.modules.product.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Thông số liên quan đến sản phẩm
 */
@Data
@ApiModel(value = "Nhóm thông số sản phẩm")
public class ProductParamsDTO implements Serializable {

    private static final long serialVersionUID = 4892783539320159200L;

    @TableField(value = "group_id")
    @ApiModelProperty(value = "ID nhóm")
    private String groupId;

    @TableField(value = "group_name")
    @ApiModelProperty(value = "Tên nhóm")
    private String groupName;

    @ApiModelProperty(value = "Danh sách thông số sản phẩm trong nhóm")
    private List<ProductParamsItemDTO> productParamsItemDTOList;

}
