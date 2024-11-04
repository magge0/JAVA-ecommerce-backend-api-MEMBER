package com.myshop.modules.product.entity.dos;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.orm.IdBasedEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;

/**
 * Album ảnh sản phẩm
 */
@Data
@TableName("myshop_product_gallery")
@ApiModel(value = "Album ảnh sản phẩm")
public class ProductGallery extends IdBasedEntity {


    @CreatedBy
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "Người tạo", hidden = true)
    private String createBy;

    /**
     * ID sản phẩm
     */
    @ApiModelProperty(value = "ID sản phẩm")
    private String productId;

    /**
     * Đường dẫn ảnh thu nhỏ
     */
    @ApiModelProperty(value = "Đường dẫn ảnh thu nhỏ")
    private String thumbnail;

    /**
     * Đường dẫn ảnh nhỏ
     */
    @ApiModelProperty(value = "Đường dẫn ảnh nhỏ")
    private String small;

    /**
     * Đường dẫn ảnh gốc
     */
    @ApiModelProperty(value = "Đường dẫn ảnh gốc", required = true)
    private String original;

    /**
     * Có phải là ảnh mặc định hay không (1: có, 0: không)
     */
    @ApiModelProperty(value = "Có phải là ảnh mặc định hay không (1: có, 0: không)")
    private Integer isDefault;

    /**
     * Thứ tự sắp xếp
     */
    @ApiModelProperty(value = "Thứ tự sắp xếp", required = true)
    private Integer sort;

}