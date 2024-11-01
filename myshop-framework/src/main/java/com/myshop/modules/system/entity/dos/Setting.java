package com.myshop.modules.system.entity.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Cấu hình
 */
@Data
@TableName("myshop_setting")
@ApiModel(value = "Cấu hình")
@NoArgsConstructor
public class Setting extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Giá trị cấu hình")
    private String settingValue;

}