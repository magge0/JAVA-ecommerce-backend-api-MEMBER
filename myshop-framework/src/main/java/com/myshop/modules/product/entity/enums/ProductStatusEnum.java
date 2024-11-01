package com.myshop.modules.product.entity.enums;

/**
 * Kiểu sản phẩm enum
 */
public enum ProductStatusEnum {
    /**
     * Đang kinh doanh
     */
    UPPER("Đang kinh doanh"),
    /**
     * Ngừng kinh doanh
     */
    DOWN("Ngừng kinh doanh");

    private final String description;

    ProductStatusEnum(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}