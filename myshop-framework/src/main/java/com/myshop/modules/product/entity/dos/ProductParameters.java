package com.myshop.modules.product.entity.dos;


import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.orm.IdBasedEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;


/**
 * Tham số sản phẩm
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("myshop_product_parameters")
@ApiModel(value = "Tham số sản phẩm")
public class ProductParameters extends IdBasedEntity {


    private static final long serialVersionUID = -566510714456317006L;

    @ApiModelProperty(value = "Tên tham số", required = true)
    @NotEmpty(message = "Tên tham số bắt buộc")
    @Length(max = 5, message = "Tên tham số không được vượt quá 5 chữ")
    private String paramName;


    @ApiModelProperty(value = "Giá trị lựa chọn")
    @NotEmpty(message = "Giá trị tùy chọn tham số bắt buộc")
    @Length(max = 255, message = "Tùy chọn tham số quá dài, vui lòng viết gọn")
    private String options;

    @ApiModelProperty(value = "Có thể lập chỉ mục hay không, 0 không hiển thị 1 hiển thị", required = true)
    @NotNull(message = "Có thể lập chỉ mục bắt buộc")
    @Min(value = 0, message = "Giá trị truyền cho có thể lập chỉ mục không chính xác")
    @Max(value = 1, message = "Giá trị truyền cho có thể lập chỉ mục không chính xác")
    private Integer isIndex;

    @ApiModelProperty(value = "Có phải bắt buộc hay không 1 có 0 không", required = true)
    @NotNull(message = "Có phải bắt buộc hay không bắt buộc")
    @Min(value = 0, message = "Giá trị truyền cho có phải bắt buộc hay không không chính xác")
    @Max(value = 1, message = "Giá trị truyền cho có phải bắt buộc hay không không chính xác")
    private Integer required;

    @ApiModelProperty(value = "ID nhóm tham số", required = true)
    @NotNull(message = "Nhóm tham số thuộc về không được để trống")
    private String groupId;

    @ApiModelProperty(value = "ID phân loại", hidden = true)
    private String categoryId;

    @ApiModelProperty(value = "Sắp xếp", hidden = true)
    @NotNull(message = "Vui lòng nhập giá trị sắp xếp")
    @Max(value = 9999, message = "Giá trị sắp xếp không được lớn hơn 9999")
    private Integer sort;

}
