package com.myshop.common.enums;

/**
 * Trả về mã trạng thái
 * Chữ số đầu tiên: 1: Sản phẩm; 2: Người dùng; 3: Giao dịch,
 * 4: Khuyến mãi, 5: Cửa hàng, 6: Trang web, 7: Cài đặt, 8: Khác
 *
 * @author vantrang
 */
public enum ResultCode {

    /**
     * Mã trạng thái thành công
     */
    SUCCESS(200, "Thành công"),

    /**
     * Tham số bất thường
     */
    PARAMS_ERROR(4002, "Tham số bất thường"),

    /**
     * Mã lỗi trả về
     */
    DEMO_SITE_FORBIDDEN_ERROR(4001, "Trang web demo không được phép sử dụng"),

    /**
     * Mã lỗi trả về
     */
    ERROR(400, "Máy chủ bận, vui lòng thử lại sau"),

    /**
     * Người dùng
     */
    USER_SESSION_EXPIRED(20004, "Phiên đăng nhập của người dùng đã hết hạn, vui lòng đăng nhập lại"),
    USER_PERMISSION_ERROR(20005, "Quyền hạn không đủ"),
    USER_PASSWORD_ERROR(20010, "Mật khẩu không chính xác");


    private final Integer code;
    private final String message;


    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}