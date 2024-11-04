package com.myshop.modules.store.entity.enums;

/**
 * Trạng thái cửa hàng
 */
public enum StoreStatusEnum {
    /**
     * Đang mở
     */
    OPEN("Đang mở"),
    /**
     * Cửa hàng đóng cửa
     */
    CLOSED("Cửa hàng đóng cửa"),
    /**
     * Đang yêu cầu mở cửa hàng
     */
    APPLY("Đang yêu cầu mở cửa hàng, chỉ cần hoàn thành bước đầu tiên là yêu cầu"),
    /**
     * Yêu cầu bị từ chối
     */
    REFUSED("Yêu cầu bị từ chối"),
    /**
     * Đang xét duyệt
     */
    APPLYING("Đang xét duyệt, đã gửi yêu cầu");

    private final String description;

    StoreStatusEnum(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}