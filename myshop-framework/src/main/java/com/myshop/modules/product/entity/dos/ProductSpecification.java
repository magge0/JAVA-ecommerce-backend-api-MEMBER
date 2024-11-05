package com.myshop.modules.product.entity.dos;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.orm.IdBasedEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


/**
 * Thông số kỹ thuật sản phẩm
 */
@Data
@TableName("myshop_product_specification")
@ApiModel(value = "Thông số kỹ thuật")
public class ProductSpecification extends IdBasedEntity {

    private static final long serialVersionUID = 147793597901239486L;

    /**
     * Tên thông số kỹ thuật
     */
    @NotEmpty(message = "Tên thông số kỹ thuật không được để trống")
    @Size(max = 20, message = "Tên thông số kỹ thuật không được vượt quá 20 ký tự")
    @ApiModelProperty(value = "Tên thông số kỹ thuật", required = true)
    private String specName;

    /**
     * Thuộc về người bán 0 thuộc về nền tảng
     * <p>
     * Thông số kỹ thuật tùy chỉnh của cửa hàng đã bị loại bỏ vào ngày 2021-06-23
     * Sẽ giới thiệu phương thức cấu hình mới trong tương lai
     */
    @ApiModelProperty(hidden = true)
    private String storeId;

    /**
     * Tên giá trị thông số kỹ thuật
     */
    @TableField(value = "product_spec_value")
    @ApiModelProperty(value = "Tên giá trị thông số kỹ thuật, phân cách bởi 《,》")
    @Length(max = 255, message = "Độ dài vượt quá giới hạn")
    private String specValue;


}
