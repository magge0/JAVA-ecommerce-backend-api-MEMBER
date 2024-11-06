package com.myshop.modules.product.entity.dos;


import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * Đơn vị đo lường sản phẩm
 */
@Data
@TableName("myshop_product_unit")
@ApiModel(value = "Đơn vị đo lường sản phẩm")
public class ProductUnit extends BaseEntity {

    @NotEmpty(message = "Tên đơn vị đo lường không được để trống")
    @Size(max = 5, message = "Độ dài tối đa của đơn vị đo lường là 5 ký tự")
    @ApiModelProperty(value = "Tên đơn vị đo lường")
    private String name;
}
