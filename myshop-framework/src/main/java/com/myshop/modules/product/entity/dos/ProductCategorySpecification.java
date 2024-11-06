package com.myshop.modules.product.entity.dos;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Liên kết nhóm tham số phân loại
 */
@Data
@TableName("myshop_product_category_specification")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Thông số kỹ thuật phân loại sản phẩm")
public class ProductCategorySpecification extends BaseEntity {


    private static final long serialVersionUID = -4041367493342243147L;
    /**
     * ID phân loại
     */
    @TableField(value = "product_category_id")
    @ApiModelProperty(value = "ID phân loại")
    private String categoryId;
    /**
     * ID thông số kỹ thuật
     */
    @TableField(value = "product_specification_id")
    @ApiModelProperty(value = "ID thông số kỹ thuật")
    private String specificationId;
}