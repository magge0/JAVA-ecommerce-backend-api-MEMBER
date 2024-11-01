package com.myshop.modules.product.entity.dto;


import com.myshop.common.validation.EnumValue;
import com.myshop.modules.product.entity.enums.ProductSalesModeEnum;
import com.myshop.modules.product.entity.enums.ProductTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DTO cho thao tác sản phẩm
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOperationDTO implements Serializable {

    private static final long serialVersionUID = -509667581371776913L;

    @ApiModelProperty(hidden = true)
    private String goodsId;

    @ApiModelProperty(value = "Giá sản phẩm", required = true)
    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 0, message = "Giá sản phẩm không được âm")
    @Max(value = 99999999, message = "Giá sản phẩm không được vượt quá 99999999")
    private Double price;

    @ApiModelProperty(value = "Đường dẫn phân loại")
    private String categoryPath;

    @ApiModelProperty(value = "ID phân loại cửa hàng", required = true)
    @Size(max = 200, message = "Bạn đã chọn quá nhiều phân loại cửa hàng")
    private String storeCategoryIds;

    @ApiModelProperty(value = "ID thương hiệu")
    @Min(value = 0, message = "Giá trị thương hiệu không chính xác")
    private String brandId;

    @ApiModelProperty(value = "Tên sản phẩm", required = true)
    @NotEmpty(message = "Tên sản phẩm không được để trống")
    @Length(max = 50, message = "Tên sản phẩm không được vượt quá 50 ký tự")
    private String productName;

    @ApiModelProperty(value = "Mô tả")
    private String intro;

    @ApiModelProperty(value = "Mô tả trên di động")
    private String mobileDescription;

    @ApiModelProperty(value = "Số lượng tồn kho")
    @Min(value = 0, message = "Số lượng tồn kho không được âm")
    @Max(value = 99999999, message = "Số lượng tồn kho không được vượt quá 99999999")
    private Integer quantity;

    @ApiModelProperty(value = "Có phát hành ngay không")
    private Boolean release;

    @ApiModelProperty(value = "Có phải là sản phẩm khuyến nghị không")
    private Boolean recommend;

    @ApiModelProperty(value = "Thông số sản phẩm")
    private List<ProductParamsDTO> productParamsDTOList;

    @ApiModelProperty(value = "Hình ảnh sản phẩm")
    private List<String> productGalleryList;

    @ApiModelProperty(value = "ID mẫu vận chuyển, giá trị là 0 khi không cần mẫu vận chuyển", required = true)
    @NotNull(message = "Mẫu vận chuyển không được để trống, truyền giá trị 0 khi không cần mẫu vận chuyển")
    @Min(value = 0, message = "Giá trị mẫu vận chuyển không chính xác")
    private String templateId;

    @ApiModelProperty(value = "Điểm bán hàng")
    private String productHighlight;

    /**
     * @see com.myshop.modules.product.entity.enums.ProductTypeEnum
     */
    @ApiModelProperty(value = "Chế độ bán hàng", required = true)
    private String salesMode;

    @ApiModelProperty(value = "Có thông số kỹ thuật không", hidden = true)
    private String haveSpec;

    @ApiModelProperty(value = "Đơn vị sản phẩm", required = true)
    @NotEmpty(message = "Đơn vị sản phẩm không được để trống")
    private String productUnit;

    @ApiModelProperty(value = "Mô tả sản phẩm")
    private String info;

    @ApiModelProperty(value = "Có tạo lại dữ liệu SKU không")
    private Boolean regeneratorSkuFlag = true;

    /**
     * @see com.myshop.modules.product.entity.enums.ProductTypeEnum
     */
    @ApiModelProperty(value = "Loại sản phẩm")
    @EnumValue(strValues = {"PHYSICAL_GOODS", "VIRTUAL_GOODS", "E_COUPON"}, message = "Giá trị tham số loại sản phẩm không chính xác")
    private String goodsType;

    /**
     * Video sản phẩm
     */
    @ApiModelProperty(value = "Video sản phẩm")
    private String productVideo;


    @ApiModelProperty(value = "Danh sách SKU")
    @Valid
    private List<Map<String, Object>> skuList;

    @ApiModelProperty(value = "Có phải là mẫu sản phẩm không")
    private Boolean productTemplateFlag = false;
    /**
     * Quy tắc sản phẩm bán buôn
     */
    @ApiModelProperty(value = "Quy tắc sản phẩm bán buôn")
    private List<WholesaleDTO> wholesaleList;

    @ApiModelProperty(value = "Lưu ý")
    private String needingAttention;


    @ApiModelProperty(value = "Có phải dành riêng cho thành viên năm không")
    private Boolean annualFeeExclusive;

    @ApiModelProperty(value = "Quyền xem")
    private String browsePermissions;

    public String getGoodsName() {
        // Xử lý cực hạn tên sản phẩm. Không sử dụng bộ lọc XSS ở đây vì bộ lọc XSS là bộ lọc toàn cục, ảnh hưởng lớn.
        // Trong nghiệp vụ, chỉ có tên sản phẩm không được có dấu phẩy tiếng Anh, là do tên sản phẩm tồn tại một truy vấn kết hợp cơ sở dữ liệu, kết quả phải được nhóm theo dấu phẩy
        return productName.replace(",", "");
    }


    public ProductOperationDTO(ProductImportDTO goodsImportDTO) {
        this.price = goodsImportDTO.getCostPrice();
        this.productName = goodsImportDTO.getProductName();
        this.intro = goodsImportDTO.getDescription();
        this.mobileDescription = goodsImportDTO.getDescription();
        this.quantity = goodsImportDTO.getQuantity();
        this.productGalleryList = goodsImportDTO.getGalleryImages();
        this.templateId = goodsImportDTO.getShippingTemplate();
        this.productHighlight = goodsImportDTO.getProductHighlight();
        this.salesMode = ProductSalesModeEnum.RETAIL.name();
        this.productUnit = goodsImportDTO.getGoodsUnit();
        this.goodsType = ProductTypeEnum.PHYSICAL_PRODUCT.name();
        this.release = goodsImportDTO.getIsPublished();
        this.recommend = false;

        Map<String, Object> map = new HashMap<>();
        map.put("sn", goodsImportDTO.getSku());
        map.put("price", goodsImportDTO.getSellingPrice());
        map.put("cost", goodsImportDTO.getCostPrice());
        map.put("weight", goodsImportDTO.getWeight());
        map.put("quantity", goodsImportDTO.getQuantity());
        map.put(goodsImportDTO.getSkuName(), goodsImportDTO.getSkuValue());
        map.put("images", goodsImportDTO.getImages());

        List<Map<String, Object>> skuList = new ArrayList<>();
        skuList.add(map);
        this.skuList = skuList;

    }
}