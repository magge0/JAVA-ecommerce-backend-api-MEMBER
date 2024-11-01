package com.myshop.modules.product.entity.enums;

/**
 * Loại sản phẩm
 *
 */
public enum ProductTypeEnum {

    /**
     * "Sản phẩm vật lý"
     */
    PHYSICAL_PRODUCT("Sản phẩm vật lý"),
    /**
     * "Sản phẩm kỹ thuật số"
     */
    VIRTUAL_PRODUCT("Sản phẩm kỹ thuật số"),
    /**
     * "Phiếu quà tặng điện tử"
     */
    E_COUPON("Phiếu quà tặng điện tử");


    private final String description;

    ProductTypeEnum(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

}