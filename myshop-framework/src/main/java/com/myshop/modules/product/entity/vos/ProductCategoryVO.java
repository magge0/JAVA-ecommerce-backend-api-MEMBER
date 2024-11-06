package com.myshop.modules.product.entity.vos;

import cn.hutool.core.bean.BeanUtil;
import com.myshop.modules.product.entity.dos.ProductCategory;
import com.myshop.modules.product.entity.dos.ProductBrand;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * VO (Value Object) của phân loại hàng hóa
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryVO extends ProductCategory {

    // Tự tìm hiểu thêm serialize và deserialize trong java nha!
    private static final long serialVersionUID = 3775766246076838410L;

    @ApiModelProperty(value = "Tên của nút cha")
    private String parentTitle;

    @ApiModelProperty("Danh sách phân loại con")
    private List<ProductCategoryVO> childCategories;

    @ApiModelProperty("Danh sách thương hiệu liên kết với phân loại")
    private List<ProductBrand> brandList;

    public ProductCategoryVO(ProductCategory cat) {
        BeanUtil.copyProperties(cat, this);
    }

    public ProductCategoryVO(String id, String createdBy, Date createdTime, String updatedBy, Date updatedTime, Boolean deleted, String name, String parentId, Integer level, BigDecimal sortOrder, Double commissionRate, String image, Boolean supportChannel) {
        super(id, createdBy, createdTime, updatedBy, updatedTime, deleted, name, parentId, level, sortOrder, commissionRate, image, supportChannel);
    }

    public List<ProductCategoryVO> getChildCategories() {
        if (childCategories != null) {
            childCategories.sort(new Comparator<ProductCategoryVO>() {
                @Override
                public int compare(ProductCategoryVO o1, ProductCategoryVO o2) {
                    return o1.getSortOrder().compareTo(o2.getSortOrder());
                }
            });
            return childCategories;
        }
        return null;
    }
}
