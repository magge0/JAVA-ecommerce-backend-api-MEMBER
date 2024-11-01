package com.myshop.modules.product.entity.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.orm.IdBasedEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@TableName("myshop_wholesale")
@ApiModel(value = "Sản phẩm bán sỉ")
public class Wholesale extends IdBasedEntity {

    private static final long serialVersionUID = -6387806138583086068L;

    @ApiModelProperty(value = "ID sản phẩm")
    private String goodsId;
    @ApiModelProperty(value = "ID SKU")
    private String skuId;
    @ApiModelProperty(value = "ID mẫu")
    private String templateId;
    @ApiModelProperty(value = "Số lượng")
    private Integer num;
    @ApiModelProperty(value = "Giá tiền")
    private Double price;
}
