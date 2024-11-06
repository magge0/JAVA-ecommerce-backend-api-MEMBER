package com.myshop.modules.product.entity.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;


/**
 * Liên kết nhóm tham số phân loại
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("myshop_product_category_parameter_group")
@ApiModel(value = "Liên kết nhóm tham số phân loại")
public class ProductCategoryParameterGroup extends BaseEntity {

    private static final long serialVersionUID = -3254446505349029420L;


    @ApiModelProperty(value = "Tên nhóm tham số", required = true)
    @NotEmpty(message = "Tên nhóm tham số không được để trống")
    @Length(max = 20, message = "Tên nhóm tham số không được vượt quá 20 chữ")
    private String groupName;

    @ApiModelProperty(value = "ID phân loại liên kết", required = true)
    @NotNull(message = "Phân loại liên kết không được để trống")
    private String categoryId;

    @ApiModelProperty(value = "Sắp xếp", hidden = true)
    private Integer sort;

}
