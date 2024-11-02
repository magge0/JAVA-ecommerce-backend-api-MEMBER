package com.myshop.common.vo.PageVO;

import cn.hutool.core.text.CharSequenceUtil;
import com.myshop.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Tham số truy vấn
 */
@Data
public class PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Số trang")
    private Integer pageNumber = 1;

    @ApiModelProperty(value = "Số lượng mỗi trang")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "Trường sắp xếp")
    private String sort;

    @ApiModelProperty(value = "Cách sắp xếp asc/desc")
    private String order;

    @ApiModelProperty(value = "Cần chuyển đổi camel sang snake", notes = "Thường không xử lý, nếu cơ sở dữ liệu là snake, thì phần này cần được xử lý.")
    private Boolean convertCamelToSnake;

    public String getSortField() {
        if (CharSequenceUtil.isNotEmpty(sort)) {
            if (convertCamelToSnake == null || Boolean.FALSE.equals(convertCamelToSnake)) {
                return StringUtils.camel2Underline(sort);
            } else {
                return sort;
            }
        }
        return sort;
    }

}