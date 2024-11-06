package com.myshop.modules.product.entity.dto;

import com.myshop.modules.product.entity.dos.ProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO nhập sản phẩm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImportDTO {

    @ApiModelProperty(value = "Tên sản phẩm")
    private String productName;

    @ApiModelProperty(value = "Điểm bán hàng")
    private String productHighlight;

    @ApiModelProperty(value = "Phân loại sản phẩm")
    private ProductCategory productCategory;

    @ApiModelProperty(value = "Mẫu vận chuyển")
    private String shippingTemplate;

    @ApiModelProperty(value = "Đơn vị đo lường")
    private String goodsUnit;

    @ApiModelProperty(value = "Trạng thái xuất bản")
    private Boolean isPublished;

    @ApiModelProperty(value = "Hình ảnh sản phẩm")
    private List<String> images;
    private List<String> galleryImages;

    @ApiModelProperty(value = "Giá vốn")
    private Double costPrice;

    @ApiModelProperty(value = "Giá bán")
    private Double sellingPrice;

    @ApiModelProperty(value = "Số lượng tồn kho")
    private Integer quantity;

    @ApiModelProperty(value = "Trọng lượng")
    private Double weight;

    @ApiModelProperty(value = "Mã SKU")
    private String sku;

    @ApiModelProperty(value = "Mô tả")
    private String description;

    @ApiModelProperty(value = "Tên thuộc tính")
    private String skuName;

    @ApiModelProperty(value = "Giá trị thuộc tính")
    private String skuValue;


}
