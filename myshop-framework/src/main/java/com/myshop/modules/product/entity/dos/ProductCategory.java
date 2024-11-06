package com.myshop.modules.product.entity.dos;


import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("myshop_category")
@ApiModel(value = "Danh mục sản phẩm")
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Tên danh mục không được để trống")
    @Size(max = 20)
    @ApiModelProperty(value = "Tên danh mục")
    private String name;

    @NotEmpty(message = "Vui lòng chọn danh mục cha")
    @ApiModelProperty(value = "ID của danh mục cha, nút gốc có ID là 0")
    private String parentId;

    @NotNull(message = "Cấp độ không được để trống")
    @Min(value = 0, message = "Cấp độ phải lớn hơn 0")
    @Max(value = 3, message = "Cấp độ tối đa là 3")
    @ApiModelProperty(value = "Cấp độ của danh mục, bắt đầu từ 0")
    private Integer level;

    @NotNull(message = "Giá trị sắp xếp không được để trống")
    @Max(value = 999, message = "Giá trị sắp xếp tối đa là 999")
    @ApiModelProperty(value = "Giá trị sắp xếp")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "Tỷ lệ hoa hồng")
    private Double commissionRate;

    @ApiModelProperty(value = "Biểu tượng của danh mục")
    private String image;

    @ApiModelProperty(value = "Hỗ trợ kênh")
    private Boolean isChannelSupported;

    public ProductCategory(String id, String createBy, Date createTime, String updateBy, Date updateTime, Boolean deleteFlag, String name, String parentId, Integer level, BigDecimal sortOrder, Double commissionRate, String image, Boolean supportChannel) {
        super(id, createBy, createTime, updateBy, updateTime, deleteFlag);
        this.name = name;
        this.parentId = parentId;
        this.level = level;
        this.sortOrder = sortOrder;
        this.commissionRate = commissionRate;
        this.image = image;
        this.isChannelSupported = supportChannel;
    }

    public ProductCategory(String id, String name, String parentId, Integer level, BigDecimal sortOrder, Double commissionRate, String image, Boolean supportChannel) {
        this.name = name;
        this.parentId = parentId;
        this.level = level;
        this.sortOrder = sortOrder;
        this.commissionRate = commissionRate;
        this.image = image;
        this.isChannelSupported = supportChannel;
    }
}
