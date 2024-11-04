package com.myshop.common.enums;

/**
 * Client Type Enum
 */

public enum ClientTypeEnum {
    /**
     * "Máy tính cá nhân"
     */
    PC("Máy tính cá nhân"),
    /**
     * "Chương trình nhỏ"
     */
    MINI_APP("Chương trình nhỏ Zalo"),
    /**
     * "Ứng dụng di động"
     */
    APP("Ứng dụng di động"),
    /**
     * "Không xác định"
     */
    UNKNOWN("Không xác định");

    private String clientName;

    ClientTypeEnum(String description) {
        this.clientName = description;
    }

    public String clientName() {
        return this.clientName;
    }

    public String value() {
        return this.name();
    }
}
