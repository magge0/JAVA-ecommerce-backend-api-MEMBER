package com.myshop.modules.store.entity.dos;


import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cửa hàng
 */
@Data
@TableName("myshop_store")
@ApiModel(value = "Cửa hàng")
@NoArgsConstructor
public class Store extends BaseEntity {


}
