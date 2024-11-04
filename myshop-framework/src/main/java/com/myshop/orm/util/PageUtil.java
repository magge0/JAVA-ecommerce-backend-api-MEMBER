package com.myshop.orm.util;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myshop.common.vo.PageVO.PageVO;
import com.myshop.modules.search.utils.SqlFilter;
import lombok.extern.slf4j.Slf4j;

/**
 * Công cụ phân trang
 */
@Slf4j
public class PageUtil {

    // Có nguy cơ tiêm order by, giới hạn độ dài
    static final Integer MAX_ORDER_BY_LENGTH = 20;

    /**
     * Phân trang Mybatis-Plus
     *
     * @param page Phân trang VO
     * @param <T>  Kiểu dữ liệu chung
     * @return Phân trang phản hồi
     */
    public static <T> Page<T> buildPage(PageVO page) {

        int currentPage = page.getCurrentPage();
        int pageLimit = page.getPageLimit();
        String sortBy = page.getSortBy();
        String sortOrder = page.getSortOrder();

        if (currentPage < 1) {
            currentPage = 1;
        }
        if (pageLimit < 1) {
            pageLimit = 10;
        }
        if (pageLimit > 100) {
            pageLimit = 100;
        }

        Page<T> p = new Page<>(currentPage, pageLimit);

        if (CharSequenceUtil.isNotBlank(sortBy)) {

            if (sortBy.length() > MAX_ORDER_BY_LENGTH || SqlFilter.checkSqlKeywords(sortBy)) {
                log.error("Độ dài trường sắp xếp vượt quá giới hạn hoặc chứa từ khóa sql, vui lòng chú ý: {}", sortBy);
                return p;
            }

            boolean isAsc = false;
            if (!CharSequenceUtil.isBlank(sortOrder)) {
                if ("desc".equals(sortOrder.toLowerCase())) {
                    isAsc = false;
                } else if ("asc".equals(sortOrder.toLowerCase())) {
                    isAsc = true;
                }
            }

            if (isAsc) {
                p.addOrder(OrderItem.asc(sortBy));
            } else {
                p.addOrder(OrderItem.desc(sortBy));
            }

        }
        return p;
    }

}