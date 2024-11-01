package com.myshop.modules.store.entity.vos;

import com.myshop.modules.store.entity.dos.Store;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Thông tin cửa hàng
 */
@Data
public class StoreVO extends Store {

    @ApiModelProperty(value = "Số lượng cảnh báo tồn kho")
    private Integer stockWarning;

    @ApiModelProperty(value = "Tên người dùng đăng nhập")
    private String nickName;

}