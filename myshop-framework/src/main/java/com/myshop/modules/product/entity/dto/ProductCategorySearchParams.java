package com.myshop.modules.product.entity.dto;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Tham số tìm kiếm danh mục
 **/
@Data
public class ProductCategorySearchParams {

    @ApiModelProperty(value = "Tên danh mục")
    private String name;

    @ApiModelProperty(value = "ID của danh mục cha")
    private String parentId;

    @ApiModelProperty(value = "Cấp độ")
    private Integer level;

    @ApiModelProperty(value = "Giá trị sắp xếp")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "Tỷ lệ hoa hồng")
    private BigDecimal commissionRate;

    @ApiModelProperty(value = "Tên của nút cha")
    private String parentTitle;

    @ApiModelProperty(value = "Có bị vô hiệu hóa hay không")
    private Boolean deleteFlag;

    public <T> QueryWrapper<T> searchWrapper() {
        QueryWrapper<T> searchWrapper = new QueryWrapper<>();
        searchWrapper.like(name != null, "name", name);
        searchWrapper.like(parentTitle != null, "parent_title", parentTitle);
        searchWrapper.eq(parentId != null, "parent_id", parentId);
        searchWrapper.eq(level != null, "level", level);
        searchWrapper.eq(sortOrder != null, "sort_order", sortOrder);
        searchWrapper.eq(commissionRate != null, "commission_rate", commissionRate);
        searchWrapper.eq(deleteFlag != null, "delete_flag", deleteFlag);
        return searchWrapper;
    }
}
