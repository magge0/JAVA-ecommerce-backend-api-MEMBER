package com.myshop.modules.product.entity.enums;

/**
 * Phê duyệt sản phẩm
 */
public enum ProductAuthEnum {
    /**
     * Cần phê duyệt và đang chờ phê duyệt
     */
    PENDING("Đang chờ phê duyệt"),
    /**
     * Phê duyệt thành công
     */
    PASS("Phê duyệt thành công"),
    /**
     * Từ chối phê duyệt
     */
    REFUSE("Từ chối phê duyệt");

    private final String description;

    ProductAuthEnum(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
