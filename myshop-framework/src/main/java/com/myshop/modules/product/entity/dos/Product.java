package com.myshop.modules.product.entity.dos;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.common.enums.ResultCode;
import com.myshop.common.exception.ServiceException;
import com.myshop.modules.product.entity.dto.ProductOperationDTO;
import com.myshop.modules.product.entity.enums.ProductSalesModeEnum;
import com.myshop.modules.product.entity.enums.ProductStatusEnum;
import com.myshop.modules.product.entity.enums.ProductTypeEnum;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jodd.util.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.Map;


@Data
@TableName("myshop_product")
@ApiModel(value = "product")
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {

    private static final long serialVersionUID = 370683595251252601L;

    @ApiModelProperty(value = "Tên sản phẩm")
    @NotEmpty(message = "Tên sản phẩm không được để trống")
    @Length(max = 100, message = "Tên sản phẩm quá dài, không được vượt quá 100 ký tự")
    private String productName;

    @ApiModelProperty(value = "Giá sản phẩm", required = true)
    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 0, message = "Giá sản phẩm không được là số âm")
    @Max(value = 999999999, message = "Giá sản phẩm không được vượt quá 99999999")
    private Double price;

    @ApiModelProperty(value = "ID thương hiệu")
    private String brandId;

    @ApiModelProperty(value = "Đường dẫn danh mục")
    private String categoryPath;

    @ApiModelProperty(value = "Đơn vị tính")
    private String productUnit;


    @Length(max = 60, message = "Điểm bán hàng quá dài, không được vượt quá 60 ký tự")
    @ApiModelProperty(value = "Điểm bán hàng")
    private String productHighlight;

    /**
     * @see ProductStatusEnum
     */
    @ApiModelProperty(value = "Trạng thái hiển thị")
    private String displayStatus;

    @ApiModelProperty(value = "Mô tả chi tiết")
    private String intro;

    @ApiModelProperty(value = "Số lượng mua")
    private Integer buyCount;

    @Max(value = 999999999, message = "Số lượng tồn kho không được vượt quá 99999999")
    @ApiModelProperty(value = "Số lượng tồn kho")
    private Integer quantity;

    @ApiModelProperty(value = "Tỷ lệ đánh giá tích cực")
    private Double grade;

    @ApiModelProperty(value = "Đường dẫn ảnh thu nhỏ")
    private String thumbnail;

    @ApiModelProperty(value = "Đường dẫn ảnh nhỏ")
    private String small;

    @ApiModelProperty(value = "Đường dẫn ảnh gốc")
    private String original;

    @ApiModelProperty(value = "Đường dẫn danh mục cửa hàng")
    private String storeCategoryIds;

    @ApiModelProperty(value = "Số lượng bình luận")
    private Integer commentNum;

    @ApiModelProperty(value = "ID người bán")
    private String storeId;

    @ApiModelProperty(value = "Tên người bán")
    private String storeName;

    @ApiModelProperty(value = "ID mẫu vận chuyển")
    private String templateId;

    @ApiModelProperty(value = "Trạng thái phê duyệt")
    private String isApproved;

    @ApiModelProperty(value = "Thông tin phê duyệt")
    private String authMessage;

    @ApiModelProperty(value = "Lý do ẩn sản phẩm")
    private String hiddenReason;

    @ApiModelProperty(value = "Có phải tự doanh hay không")
    private Boolean isSelfOperated;

    @ApiModelProperty(value = "Mô tả sản phẩm trên di động")
    private String mobileDescription;

    @ApiModelProperty(value = "Video sản phẩm")
    private String productVideo;


    @ApiModelProperty(value = "Có phải sản phẩm khuyến nghị hay không", required = true)
    private Boolean recommend;

    /**
     * @see com.myshop.modules.product.entity.enums.ProductSalesModeEnum
     */
    @ApiModelProperty(value = "Chế độ bán hàng", required = true)
    private String salesMode;


    /**
     * @see com.myshop.modules.product.entity.enums.ProductTypeEnum
     */
    @ApiModelProperty(value = "Loại sản phẩm", required = true)
    private String productType;

    @ApiModelProperty(value = "Thông tin sản phẩm JSON", hidden = true)
    private String params;


    public Product() {
    }

    public Product(ProductOperationDTO productOperationDTO) {
        // Gán giá trị cho thuộc tính goodsName từ đối tượng productOperationDTO
        this.productName = productOperationDTO.getProductName();
        // Gán giá trị cho thuộc tính categoryPath từ đối tượng productOperationDTO
        this.categoryPath = productOperationDTO.getCategoryPath();
        // Gán giá trị cho thuộc tính storeCategoryPath từ đối tượng productOperationDTO
        this.storeCategoryIds = productOperationDTO.getStoreCategoryIds();
        // Gán giá trị cho thuộc tính brandId từ đối tượng productOperationDTO
        this.brandId = productOperationDTO.getBrandId();
        // Gán giá trị cho thuộc tính templateId từ đối tượng productOperationDTO
        this.templateId = productOperationDTO.getTemplateId();
        // Gán giá trị cho thuộc tính recommend từ đối tượng productOperationDTO
        this.recommend = productOperationDTO.getRecommend();
        // Gán giá trị cho thuộc tính sellingPoint từ đối tượng productOperationDTO
        this.productHighlight = productOperationDTO.getProductHighlight();
        // Gán giá trị cho thuộc tính salesModel từ đối tượng productOperationDTO
        this.salesMode = productOperationDTO.getSalesMode();
        // Gán giá trị cho thuộc tính goodsUnit từ đối tượng productOperationDTO
        this.productUnit = productOperationDTO.getProductUnit();
        // Gán giá trị cho thuộc tính intro từ đối tượng productOperationDTO
        this.intro = productOperationDTO.getIntro();
        // Gán giá trị cho thuộc tính mobileIntro từ đối tượng productOperationDTO
        this.mobileDescription = productOperationDTO.getMobileDescription();
        // Gán giá trị cho thuộc tính goodsVideo từ đối tượng productOperationDTO
        this.productVideo = productOperationDTO.getProductVideo();
        // Gán giá trị cho thuộc tính price từ đối tượng productOperationDTO
        this.price = productOperationDTO.getPrice();
        // Kiểm tra xem danh sách tham số sản phẩm có tồn tại và không rỗng
        if (productOperationDTO.getProductParamsDTOList() != null && productOperationDTO.getProductParamsDTOList().isEmpty()) {
            // Chuyển đổi danh sách tham số sản phẩm sang chuỗi JSON
            this.params = JSONUtil.toJsonStr(productOperationDTO.getProductParamsDTOList());
        }
        // Nếu sản phẩm được phát hành ngay lập tức thì
        this.displayStatus = Boolean.TRUE.equals(productOperationDTO.getRelease()) ? ProductStatusEnum.UPPER.name() : ProductStatusEnum.DOWN.name();
        // Gán giá trị cho thuộc tính goodsType từ đối tượng productOperationDTO
        this.productType = productOperationDTO.getGoodsType();
        // Gán giá trị cho thuộc tính grade là 100D
        this.grade = 100D;

        // Lặp qua danh sách SKU để kiểm tra tính hợp lệ của SKU
        for (Map<String, Object> sku : productOperationDTO.getSkuList()) {
            // Kiểm tra xem thuộc tính "sn" (Mã SKU) có tồn tại và không rỗng
            if (!sku.containsKey("sn") || sku.get("sn") == null) {
                // Nếu Mã SKU không tồn tại hoặc rỗng, ném ra ngoại lệ
                throw new ServiceException(ResultCode.PRODUCT_SKU_SN_ERROR);
            }
            // Kiểm tra xem thuộc tính "price" (Giá) có tồn tại và không rỗng
            if ((!sku.containsKey("price") || StringUtil.isEmpty(sku.get("price").toString()) || Convert.toDouble(sku.get("price")) <= 0) && !productOperationDTO.getSalesMode().equals(ProductSalesModeEnum.WHOLESALE.name())) {
                // Nếu giá không tồn tại hoặc rỗng, hoặc giá <= 0 và không phải bán buôn, ném ra ngoại lệ
                throw new ServiceException(ResultCode.PRODUCT_SKU_PRICE_ERROR);
            }
            // Kiểm tra xem thuộc tính "cost" (Giá vốn) có tồn tại và không rỗng
            if ((!sku.containsKey("cost") || StringUtil.isEmpty(sku.get("cost").toString()) || Convert.toDouble(sku.get("cost")) <= 0) && !productOperationDTO.getSalesMode().equals(ProductSalesModeEnum.WHOLESALE.name())) {
                // Nếu giá vốn không tồn tại hoặc rỗng, hoặc giá vốn <= 0 và không phải bán buôn, ném ra ngoại lệ
                throw new ServiceException(ResultCode.PRODUCT_SKU_WEIGHT_ERROR);
            }
            // Kiểm tra xem thuộc tính "weight" (Trọng lượng) có tồn tại và không rỗng
            if (this.productType.equals(ProductTypeEnum.PHYSICAL_PRODUCT.name()) && (!sku.containsKey("weight") || sku.containsKey("weight") && (StringUtil.isEmpty(sku.get("weight").toString()) || Convert.toDouble(sku.get("weight").toString()) < 0))) {
                // Nếu trọng lượng không tồn tại hoặc rỗng, hoặc trọng lượng < 0, ném ra ngoại lệ
                throw new ServiceException(ResultCode.PRODUCT_SKU_WEIGHT_ERROR);
            }
            // Kiểm tra xem thuộc tính "quantity" (Số lượng) có tồn tại và không rỗng
            if (!sku.containsKey("quantity") || StringUtil.isEmpty(sku.get("quantity").toString()) || Convert.toInt(sku.get("quantity").toString()) < 0) {
                // Nếu số lượng không tồn tại hoặc rỗng, hoặc số lượng < 0, ném ra ngoại lệ
                throw new ServiceException(ResultCode.PRODUCT_SKU_QUANTITY_ERROR);
            }
            // Lặp qua các giá trị của SKU để kiểm tra xem chúng có rỗng hay không
            sku.values().forEach(i -> {
                // Nếu giá trị của SKU rỗng, ném ra ngoại lệ
                if (CharSequenceUtil.isBlank(i.toString())) {
                    throw new ServiceException(ResultCode.MUST_HAVE_PRODUCT_SKU_VALUE);
                }
            });
        }
    }
}
