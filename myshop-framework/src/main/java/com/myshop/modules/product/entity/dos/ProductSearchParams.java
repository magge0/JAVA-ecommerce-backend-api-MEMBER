package com.myshop.modules.product.entity.dos;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myshop.common.vo.PageVO.PageVO;
import com.myshop.modules.product.entity.enums.ProductAuthEnum;
import com.myshop.modules.product.entity.enums.ProductStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Arrays;
import java.util.List;

/**
 * Điều kiện tìm kiếm sản phẩm
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchParams extends PageVO {

    private static final long serialVersionUID = 2544015852728566887L;


    @ApiModelProperty(value = "Mã sản phẩm")
    private String productId;

    @ApiModelProperty(value = "Tên sản phẩm")
    private String productName;

    @ApiModelProperty(value = "Mã sản phẩm")
    private String id;

    @ApiModelProperty(value = "Danh sách mã sản phẩm")
    private List<String> ids;

    @ApiModelProperty(value = "ID cửa hàng")
    private String storeId;

    @ApiModelProperty(value = "Tên cửa hàng")
    private String storeName;

    @ApiModelProperty(value = "Giá cả, có thể là phạm vi, ví dụ: 10_1000")
    private String price;

    @ApiModelProperty(value = "Đường dẫn danh mục")
    private String categoryPath;

    @ApiModelProperty(value = "ID danh mục cửa hàng")
    private String storeCategoryIds;

    @ApiModelProperty(value = "Có phải tự vận hành hay không")
    private Boolean isSelfOperated;

    /**
     * @see ProductStatusEnum
     */
    @ApiModelProperty(value = "Trạng thái lên kệ/xuống kệ")
    private String displayStatus;

    /**
     * @see ProductAuthEnum
     */
    @ApiModelProperty(value = "Trạng thái duyệt")
    private String isApproved;

    @ApiModelProperty(value = "Số lượng tồn kho")
    private Integer maxQuantity;

    @ApiModelProperty(value = "Số lượng tồn kho")
    private Integer minQuantity;

    @ApiModelProperty(value = "Có phải là sản phẩm được đề xuất hay không")
    private Boolean recommend;

    @ApiModelProperty(value = "Loại sản phẩm")
    private String productType;

    @ApiModelProperty(value = "Chế độ bán hàng", required = true)
    private String salesModel;

    @ApiModelProperty(value = "Tồn kho cảnh báo")
    private Boolean alertQuantity;

    public <T> QueryWrapper<T> queryWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (CharSequenceUtil.isNotEmpty(productId)) {
            queryWrapper.eq("product_id", productId);
        }
        if (CharSequenceUtil.isNotEmpty(productName)) {
            queryWrapper.like("product_name", productName);
        }
        if (CharSequenceUtil.isNotEmpty(id)) {
            queryWrapper.in("id", Arrays.asList(id.split(",")));
        }
        if (CollUtil.isNotEmpty(ids)) {
            queryWrapper.in("id", ids);
        }
        if (CharSequenceUtil.isNotEmpty(storeId)) {
            queryWrapper.eq("store_id", storeId);
        }
        if (CharSequenceUtil.isNotEmpty(storeName)) {
            queryWrapper.like("store_name", storeName);
        }
        if (CharSequenceUtil.isNotEmpty(categoryPath)) {
            queryWrapper.like("category_path", categoryPath);
        }
        if (CharSequenceUtil.isNotEmpty(storeCategoryIds)) {
            queryWrapper.like("store_category_ids", storeCategoryIds);
        }
        if (isSelfOperated != null) {
            queryWrapper.eq("is_self_operated", isSelfOperated);
        }
        if (CharSequenceUtil.isNotEmpty(displayStatus)) {
            queryWrapper.eq("display_status", displayStatus);
        }
        if (CharSequenceUtil.isNotEmpty(isApproved)) {
            queryWrapper.eq("is_approved", isApproved);
        }
        if (maxQuantity != null) {
            queryWrapper.le("quantity", maxQuantity);
        }
        if (minQuantity != null) {
            queryWrapper.gt("quantity", minQuantity);
        }
        if (recommend != null) {
            queryWrapper.le("recommend", recommend);
        }
        if (CharSequenceUtil.isNotEmpty(productType)) {
            queryWrapper.eq("product_type", productType);
        }
        if (CharSequenceUtil.isNotEmpty(salesModel)) {
            queryWrapper.eq("sales_model", salesModel);
        }
        if (alertQuantity != null && alertQuantity) {
            queryWrapper.apply("quantity <= alert_quantity");
            queryWrapper.ge("alert_quantity", 0);
        }
        queryWrapper.in(CollUtil.isNotEmpty(ids), "id", ids);

        queryWrapper.eq("delete_flag", false);
        this.applyPriceRange(queryWrapper);
        return queryWrapper;
    }

    private <T> void applyPriceRange(QueryWrapper<T> queryWrapper) {
        // Kiểm tra xem giá (price) có được cung cấp hay không
        if (CharSequenceUtil.isNotEmpty(price)) {
            // Tách chuỗi giá dựa trên dấu "_"
            String[] s = price.split("_");
            // Nếu có nhiều hơn 1 phần (ví dụ: "10_1000"), áp dụng điều kiện between cho giá
            if (s.length > 1) {
                queryWrapper.between("price", s[0], s[1]);
            } else {
                // Nếu chỉ có 1 phần (ví dụ: "10"), áp dụng điều kiện lớn hơn hoặc bằng cho giá
                queryWrapper.ge("price", s[0]);
            }
        }
    }


}
