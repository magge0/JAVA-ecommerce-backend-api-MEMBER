package com.myshop.modules.member.entity.dos;


import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Liên kết quyền hạn của vai trò
 */
@Data
@TableName("myshop_store_role_menu")
@ApiModel(value = "Quyền hạn của vai trò cửa hàng")
public class StoreRoleMenu extends BaseEntity {

    private static final long serialVersionUID = -4680260093546996026L;

    @ApiModelProperty(value = "ID của vai trò")
    private String roleId;

    @ApiModelProperty(value = "Menu")
    private String menuId;

    @ApiModelProperty(value = "ID của cửa hàng")
    private String storeId;

    @ApiModelProperty(value = "Có quyền thao tác dữ liệu hay không, nếu không thì chỉ có quyền xem")
    private Boolean isSuper;

}
