package com.myshop.modules.store.entity.dos;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.myshop.common.utils.BeanUtil;
import com.myshop.modules.member.entity.dos.Member;
import com.myshop.modules.store.entity.dto.ManagementStoreApplyDTO;
import com.myshop.modules.store.entity.enums.StoreStatusEnum;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Cửa hàng
 */
@Data
@TableName("myshop_store")
@ApiModel(value = "Cửa hàng")
@NoArgsConstructor
public class Store extends BaseEntity {

    private static final long serialVersionUID = -5861767526387892272L;

    @ApiModelProperty(value = "ID thành viên")
    private String memberId;

    @ApiModelProperty(value = "Tên thành viên")
    private String memberName;

    @ApiModelProperty(value = "Tên cửa hàng")
    private String storeName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "Thời gian đóng cửa hàng (theo định dạng yyyy-MM-dd)")
    private Date closingTime;

    /**
     * @see StoreStatusEnum
     */
    @ApiModelProperty(value = "Trạng thái của cửa hàng (bật/tắt)")
    private String storeEnabled;

    @ApiModelProperty(value = "Có phải tự kinh doanh")
    private Boolean isSelfOperated;

    @ApiModelProperty(value = "Logo cửa hàng")
    private String storeImage;

    @ApiModelProperty(value = "Tọa độ địa lý")
    @NotEmpty
    private String storeLocationData;

    @Size(min = 6, max = 200, message = "Mô tả cửa hàng phải từ 6 đến 200 ký tự")
    @NotBlank(message = "Mô tả cửa hàng không được để trống")
    @ApiModelProperty(value = "Mô tả cửa hàng")
    private String storeIntroduction;

    @ApiModelProperty(value = "Tên địa chỉ, cách nhau bởi dấu ','")
    private String storeAddress;

    @ApiModelProperty(value = "ID địa chỉ, cách nhau bởi dấu ','")
    private String storeAddressIds;

    @ApiModelProperty(value = "Địa chỉ chi tiết")
    private String storeAddressInfo;

    @ApiModelProperty(value = "Điểm đánh giá mô tả của cửa hàng")
    private Double descriptionRating;

    @ApiModelProperty(value = "Điểm đánh giá dịch vụ của cửa hàng")
    private Double serviceRating;

    @ApiModelProperty(value = "Điểm đánh giá vận chuyển của cửa hàng")
    private Double deliveryRating;

    @ApiModelProperty(value = "Số lượng sản phẩm")
    private Integer productNum;

    @ApiModelProperty(value = "Số lượng người yêu thích cửa hàng")
    private Integer favoriteCount;

    @ApiModelProperty(value = "Mã định danh duy nhất của Tencent Cloud Zhifu")
    private String tencentCloudZhifuId;

    @ApiModelProperty(value = "Mã định danh duy nhất của Tencent Cloud Zhifu Miniprogram")
    private String tencentCloudZhifuMiniprogramId;

    @ApiModelProperty(value = "Mã định danh của merchant trong Udesk IM")
    private String udeskMerchantId;

    public Boolean getPageShow() {
        return isDefaultPageEnabled != null && isDefaultPageEnabled;
    }

    public Boolean getSelfPickFlag() {
        return isSelfPickupEnabled != null && isSelfPickupEnabled;
    }

    @ApiModelProperty(value = "Trạng thái bật/tắt trang mặc định")
    private Boolean isDefaultPageEnabled;

    @ApiModelProperty(value = "Trạng thái bật/tắt tính năng tự lấy hàng")
    private Boolean isSelfPickupEnabled;

    public Store(Member mem) {
        this.memberId = mem.getId();
        this.memberName = mem.getUsername();
        storeEnabled = StoreStatusEnum.APPLY.value();
        isSelfOperated = false;
        deliveryRating = 5.0;
        serviceRating = 5.0;
        descriptionRating = 5.0;
        productNum = 0;
        favoriteCount = 0;
        this.isSelfPickupEnabled = false;
        this.isDefaultPageEnabled = false;
    }

    public Store(Member member, ManagementStoreApplyDTO managementStoreApplyDTO) {
        BeanUtil.copyProperties(managementStoreApplyDTO, this);

        this.memberId = member.getId();
        this.memberName = member.getUsername();
        storeEnabled = StoreStatusEnum.APPLY.value();
        isSelfOperated = false;
        deliveryRating = 5.0;
        serviceRating = 5.0;
        descriptionRating = 5.0;
        productNum = 0;
        favoriteCount = 0;
        this.isSelfPickupEnabled = false;
        this.isDefaultPageEnabled = false;
    }


}
