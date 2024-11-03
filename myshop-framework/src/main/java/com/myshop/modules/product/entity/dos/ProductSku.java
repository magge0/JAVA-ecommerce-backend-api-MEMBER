package com.myshop.modules.product.entity.dos;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myshop.modules.product.entity.enums.ProductAuthEnum;
import com.myshop.modules.product.entity.enums.ProductStatusEnum;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Max;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * Product sku
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("myshop_product_sku")
@ApiModel(value = "Đối tượng sản phẩm sku")
@NoArgsConstructor
public class ProductSku extends BaseEntity {

    private static final long serialVersionUID = 4865918658161118934L;

    @ApiModelProperty(value = "ID sản phẩm")
    private String productId;

    @ApiModelProperty(value = "Thông tin quy cách dạng json", hidden = true)
    @JsonIgnore
    private String specs;

    @ApiModelProperty(value = "Thông tin quy cách")
    private String shortSpecs;

    @ApiModelProperty(value = "ID mẫu vận chuyển")
    private String deliveryTemplateId;

    @ApiModelProperty(value = "Có phải là sản phẩm khuyến mãi hay không")
    private Boolean isPromotion;

    @ApiModelProperty(value = "Giá khuyến mãi")
    private Double discountedPrice;

    @ApiModelProperty(value = "Tên sản phẩm")
    private String productName;

    @Length(max = 30, message = "Mã sản phẩm quy cách quá dài, không được vượt quá 30 ký tự")
    @ApiModelProperty(value = "Mã sản phẩm")
    private String sn;

    @ApiModelProperty(value = "ID thương hiệu")
    private String brandId;

    @ApiModelProperty(value = "Đường dẫn danh mục")
    private String categoryPath;

    @ApiModelProperty(value = "Đơn vị tính")
    private String productUnit;

    @ApiModelProperty(value = "Điểm bán")
    private String productHighlight;

    @ApiModelProperty(value = "Trọng lượng")
    @Max(value = 999999999, message = "Trọng lượng không được vượt quá 999999999")
    private Double weight;
    /**
     * @see ProductStatusEnum
     */
    @ApiModelProperty(value = "Trạng thái đưa lên kệ")
    private String displayStatus;

    @ApiModelProperty(value = "Chi tiết sản phẩm")
    private String intro;

    @Max(value = 999999999, message = "Giá không được vượt quá 999999999")
    @ApiModelProperty(value = "Giá sản phẩm")
    private Double price;

    @Max(value = 999999999, message = "Giá vốn 999999999")
    @ApiModelProperty(value = "Giá vốn")
    private Double cost;

    @ApiModelProperty(value = "Số lượt xem")
    private Integer viewCount;

    @ApiModelProperty(value = "Số lượng đã mua")
    private Integer buyCount;

    @Max(value = 999999999, message = "Số lượng tồn kho không được vượt quá 99999999")
    @ApiModelProperty(value = "Số lượng tồn kho")
    private Integer quantity;

    @ApiModelProperty(value = "Tỷ lệ đánh giá tốt của sản phẩm")
    private Double grade;

    @ApiModelProperty(value = "Đường dẫn ảnh thu nhỏ")
    private String thumbnail;

    @ApiModelProperty(value = "Đường dẫn ảnh lớn")
    private String big;

    @ApiModelProperty(value = "Đường dẫn ảnh nhỏ")
    private String small;

    @ApiModelProperty(value = "Đường dẫn ảnh gốc")
    private String original;

    @ApiModelProperty(value = "ID danh mục cửa hàng")
    private String storeCategoryIds;

    @ApiModelProperty(value = "Số lượng bình luận")
    private Integer commentNum;

    @ApiModelProperty(value = "ID người bán")
    private String storeId;

    @ApiModelProperty(value = "Tên người bán")
    private String storeName;

    @ApiModelProperty(value = "ID mẫu vận chuyển")
    private String templateId;

    /**
     * @see ProductAuthEnum
     */
    @ApiModelProperty(value = "Trạng thái duyệt")
    private String isApproved;

    @ApiModelProperty(value = "Thông tin duyệt")
    private String authMessage;

    @ApiModelProperty(value = "Lý do đưa xuống kệ")
    private String hiddenReason;

    @ApiModelProperty(value = "Có phải là tự doanh hay không")
    private Boolean isSelfOperated;

    @ApiModelProperty(value = "Chi tiết sản phẩm trên di động")
    private String mobileDescription;

    @ApiModelProperty(value = "Video sản phẩm")
    private String productVideo;

    @ApiModelProperty(value = "Có phải là sản phẩm được đề xuất hay không", required = true)
    private Boolean recommend;

    @ApiModelProperty(value = "Mô hình bán hàng", required = true)
    private String salesMode;

    @ApiModelProperty(value = "Loại sản phẩm", required = true)
    private String productType;

    @ApiModelProperty(value = "Số lượng cảnh báo")
    private Integer stockAlertQuantity;

    public Double getWeight() {
        if (weight == null) {
            return 0d;
        }
        return weight;
    }

    public Integer getAlertQuantity() {
        if (stockAlertQuantity == null) {
            return 0;
        }
        return stockAlertQuantity;
    }

    @Override
    public Date getCreateTime() {
        // Kiểm tra xem thời gian tạo (createTime) được kế thừa từ lớp cha có null hay không
        if (super.getCreateTime() == null) {
            // Nếu null, trả về thời gian hiện tại
            return new Date();
        } else {
            // Nếu không null, trả về thời gian tạo được kế thừa từ lớp cha
            return super.getCreateTime();
        }
    }

    /**
     * Thiết lập thông tin sản phẩm cơ bản cho sản phẩm quy cách
     *
     * @param product Thông tin sản phẩm cơ bản
     */
    public ProductSku(Product product) {
        //Thông tin sản phẩm cơ bản
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.productType = product.getProductType();
        this.productVideo = product.getProductVideo();
        this.isSelfOperated = product.getIsSelfOperated();
        this.productHighlight = product.getProductHighlight();
        this.categoryPath = product.getCategoryPath();
        this.brandId = product.getBrandId();
        this.displayStatus = product.getDisplayStatus();
        this.intro = product.getIntro();
        this.mobileDescription = product.getMobileDescription();
        this.productUnit = product.getProductUnit();
        this.grade = 100D;
        this.stockAlertQuantity = 0;
        //Trạng thái sản phẩm
        this.isApproved = product.getIsApproved();
        this.salesMode = product.getSalesMode();
        //Thông tin người bán
        this.storeId = product.getStoreId();
        this.storeName = product.getStoreName();
        this.storeCategoryIds = product.getStoreCategoryIds();
        this.deliveryTemplateId = product.getTemplateId();
        this.recommend = product.getRecommend();
    }

}