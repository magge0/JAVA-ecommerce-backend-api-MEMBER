package com.myshop.modules.product.entity.dos;


import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


/**
 * Thương hiệu sản phẩm
 */
@Data
@TableName("myshop_brand")
@ApiModel(value = "Thương hiệu sản phẩm")
public class Brand extends BaseEntity {


    private static final long serialVersionUID = -8236866838438521426L;

    @NotEmpty(message = "Tên thương hiệu không được để trống")
    @Length(max = 20, message = "Tên thương hiệu phải nhỏ hơn 20 ký tự")
    @ApiModelProperty(value = "Tên thương hiệu", required = true)
    private String name;

    @NotEmpty(message = "Biểu tượng thương hiệu không được để trống")
    @Length(max = 255, message = "Địa chỉ biểu tượng thương hiệu vượt quá 255 ký tự")
    @ApiModelProperty(value = "Biểu tượng thương hiệu", required = true)
    private String logo;

}
