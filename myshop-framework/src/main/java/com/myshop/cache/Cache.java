package com.myshop.cache;

import java.util.concurrent.TimeUnit;

/**
 * Interface cache
 *
 * @author vantrang
 */
public interface Cache<T> {

    /**
     * Lấy một mục từ cache, không giao dịch.
     *
     * @param key Khóa cache
     * @return Đối tượng được lưu trong cache hoặc <tt>null</tt>
     */
    T get(Object key);

    /**
     * Thêm một mục vào bộ nhớ cache, phi giao dịch, với
     * ngữ nghĩa thất bại nhanh
     *
     * @param key   Khóa cache
     * @param value Giá trị cache
     */
    void put(Object key, T value);

    /**
     * Ghi nội dung vào bộ nhớ cache
     *
     * @param key   Khóa cache
     * @param value Giá trị cache
     * @param exp   Thời gian hết hạn, đơn vị là giây
     */
    void put(Object key, T value, Long exp);

    /**
     * Ghi nội dung vào bộ nhớ cache
     *
     * @param key      Khóa cache
     * @param value    Giá trị cache
     * @param exp      Thời gian hết hạn
     * @param timeUnit Đơn vị thời gian hết hạn
     */
    void put(Object key, T value, Long exp, TimeUnit timeUnit);

    /**
     * Kiểm tra xem có chứa khóa hay không
     *
     * @param key Khóa cache
     * @return True nếu có chứa khóa, False nếu không
     */
    boolean hasKey(Object key);

    /**
     * Xóa
     *
     * @param key Khóa cache
     */
    Boolean remove(Object key);

}
