package com.myshop.common.security.enums;

/**
 * Token Role Type
 *
 * @author vantrang
 * @since 2024/10/22
 */
public enum UserEnums {
    /**
     * Vai trò
     */
    MEMBER("member"),
    STORE("store"),
    MANAGER("manager"),
    SYSTEM("system");
    private final String role;

    UserEnums(String r) {
        this.role = r;
    }

    public String getRole() {
        return role;
    }
}
