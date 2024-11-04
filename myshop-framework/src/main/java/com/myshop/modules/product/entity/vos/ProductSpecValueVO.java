package com.myshop.modules.product.entity.vos;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Giá trị thông số kỹ thuật
 */
@Data
public class ProductSpecValueVO implements Serializable {

    private static final long serialVersionUID = -4433569132929428572L;

    @TableField(value = "spec_attribute_name")
    @ApiModelProperty(value = "Tên thuộc tính thông số kỹ thuật")
    private String specAttributeName;

    @TableField(value = "spec_attribute_value")
    @ApiModelProperty(value = "Giá trị thuộc tính thông số kỹ thuật")
    private String specAttributeValue;

    @ApiModelProperty(value = "Thông số kỹ thuật này có hình ảnh hay không, 1 là có, 0 là không")
    private Integer hasSpecImage;
    /**
     * Hình ảnh thông số kỹ thuật
     */
    @ApiModelProperty(value = "Danh sách hình ảnh thuộc tính thông số kỹ thuật")
    private List<String> specAttributeImages;
}
