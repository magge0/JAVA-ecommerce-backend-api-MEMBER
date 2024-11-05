package com.myshop.modules.product.entity.dos;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.myshop.orm.IdBasedEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * Liên kết danh mục - thương hiệu
 */
@Data
@TableName("myshop_product_category_brand")
@ApiModel(value = "Liên kết danh mục - thương hiệu sản phẩm")
@NoArgsConstructor
public class ProductCategoryBrand extends IdBasedEntity {

    private static final long serialVersionUID = 3315719881926878L;


    @CreatedBy
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "Người tạo", hidden = true)
    private String createdBy;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "Thời gian tạo", hidden = true)
    private Date createdTime;

    /**
     * ID danh mục
     */
    @TableField(value = "category_id")
    @ApiModelProperty(value = "ID danh mục")
    private String categoryId;
    /**
     * ID thương hiệu
     */
    @TableField(value = "brand_id")
    @ApiModelProperty(value = "ID thương hiệu")
    private String brandId;

    public ProductCategoryBrand(String categoryId, String brandId) {
        this.categoryId = categoryId;
        this.brandId = brandId;
    }
}
