package com.myshop.common.security;

import com.myshop.common.enums.ResultCode;
import com.myshop.common.exception.ServiceException;
import com.myshop.common.security.context.UserContext;
import com.myshop.common.utils.BeanUtil;

import java.util.Objects;

/**
 * Xác định toàn cục xem có thể thao tác thuộc tính nào không
 */
public class OperationalAssessment {

    /**
     * Đối tượng cần xác định phải chứa thuộc tính memberId, storeId đại diện cho vai trò được xác định
     *
     * @param object Đối tượng được xác định
     * @param <T>    Loại đối tượng được xác định
     * @return Kết quả xử lý
     */
    public static <T> T judgment(T object) {
        // Gọi phương thức judgment với đối tượng object và các tên trường mặc định "memberId" và "storeId"
        return judgment(object, "memberId", "storeId");
    }

    /**
     * Đối tượng cần xác định phải chứa thuộc tính memberId, storeId đại diện cho vai trò được xác định
     *
     * @param object       Đối tượng được xác định
     * @param buyerIdField ID người mua
     * @param storeIdField ID cửa hàng
     * @param <T>          Loại đối tượng
     * @return Trả về bản thân đối tượng đã được xác định, tránh việc truy vấn đối tượng nhiều lần
     */
    public static <T> T judgment(T object, String buyerIdField, String storeIdField) {
        // Lấy đối tượng AuthUser của người dùng hiện tại từ UserContext và kiểm tra xem nó có null hay không
        AuthUser tokenUser = Objects.requireNonNull(UserContext.getCurrentUser());
        // Kiểm tra vai trò của người dùng
        switch (tokenUser.getRole()) {
            // Nếu người dùng là MANAGER
            case MANAGER:
                return object;
            // Nếu người dùng là MEMBER
            case MEMBER:
                // Kiểm tra xem ID của người dùng hiện tại có khớp với giá trị của trường buyerIdField trong đối tượng object hay
                if (tokenUser.getId().equals(BeanUtil.getFieldValueByName(buyerIdField, object))) {
                    // Nếu khớp, trả về đối tượng object
                    return object;
                } else {
                    throw new ServiceException(ResultCode.USER_PERMISSION_ERROR);
                }
                // Nếu người dùng là STORE
            case STORE:
                // Kiểm tra xem storeId của người dùng hiện tại có khớp với giá trị của trường storeIdField trong đối tượng
                if (tokenUser.getStoreId().equals(BeanUtil.getFieldValueByName(storeIdField, object))) {
                    // Nếu khớp, trả về đối tượng object
                    return object;
                } else {
                    throw new ServiceException(ResultCode.USER_PERMISSION_ERROR);
                }
            default:
                throw new ServiceException(ResultCode.USER_PERMISSION_ERROR);
        }
    }
}