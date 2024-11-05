package com.myshop.modules.member.entity.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Quyền hạn menu
 */
@Data
@TableName("myshop_store_menu")
@ApiModel(value = "Quyền hạn menu của cửa hàng")
public class StoreMenu extends BaseEntity {

    private static final long serialVersionUID = 7050754476203495207L;


    @ApiModelProperty(value = "Tiêu đề menu")
    private String title;

    @ApiModelProperty(value = "Tên định tuyến")
    private String name;

    @ApiModelProperty(value = "Đường dẫn")
    private String path;

    @ApiModelProperty(value = "Cấp bậc menu")
    private Integer level;

    @ApiModelProperty(value = "Tệp đường dẫn frontend")
    private String frontendRoute;

    @ApiModelProperty(value = "ID cha")
    private String parentId = "0";

    @ApiModelProperty(value = "Giá trị sắp xếp")
    private Double sortOrder;

    @ApiModelProperty(value = "URL quyền hạn, * là ký hiệu đại diện, phân cách bằng dấu phẩy")
    private String permission;


}