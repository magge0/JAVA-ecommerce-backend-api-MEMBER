package com.myshop.modules.product.entity.enums;

/**
 * Chế độ bán hàng
 */
public enum ProductSalesModeEnum {

    RETAIL("Bán lẻ"),
    WHOLESALE("Bán sỉ");

    private final String description;

    ProductSalesModeEnum(String description) {
        this.description = description;

    }

    public String description() {
        return description;
    }

}