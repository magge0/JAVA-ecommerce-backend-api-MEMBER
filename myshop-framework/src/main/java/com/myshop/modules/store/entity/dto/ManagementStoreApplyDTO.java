package com.myshop.modules.store.entity.dto;

import com.myshop.common.validation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


/**
 * DTO thông tin thêm cửa hàng từ phía quản trị
 */
@Data
public class ManagementStoreApplyDTO {

    /****Thông tin cơ bản của cửa hàng***/
    @ApiModelProperty(value = "ID thành viên")
    public String memberId;

    @Size(min = 2, max = 200, message = "Tên cửa hàng phải từ 2 đến 200 ký tự")
    @NotBlank(message = "Tên cửa hàng không được để trống")
    @ApiModelProperty(value = "Tên cửa hàng")
    private String storeName;

    @ApiModelProperty(value = "Logo cửa hàng")
    private String storeImage;

    @Size(min = 6, max = 200, message = "Mô tả cửa hàng phải từ 6 đến 200 ký tự")
    @NotBlank(message = "Mô tả cửa hàng không được để trống")
    @ApiModelProperty(value = "Mô tả cửa hàng")
    private String storeIntroduction;

    @ApiModelProperty(value = "Tọa độ địa lý")
    private String storeLocationData;

    @ApiModelProperty(value = "Danh mục kinh doanh của cửa hàng")
    private String productManagementCategory;

    @ApiModelProperty(value = "Có phải tự kinh doanh")
    private Boolean isSelfOperated;

    @ApiModelProperty(value = "Tên địa chỉ, cách nhau bởi dấu ','")
    private String storeAddress;

    @ApiModelProperty(value = "ID địa chỉ, cách nhau bởi dấu ','")
    private String storeAddressIds;

    @ApiModelProperty(value = "Địa chỉ chi tiết")
    private String storeAddressInfo;

    /****Thông tin cơ bản của công ty***/
    @NotBlank(message = "Tên công ty không được để trống")
    @Size(min = 2, max = 100, message = "Tên công ty không hợp lệ")
    @ApiModelProperty(value = "Tên công ty")
    private String companyName;

    @Mobile
    @ApiModelProperty(value = "Số điện thoại công ty")
    private String companyPhone;

    @NotBlank(message = "Địa chỉ công ty không được để trống")
    @Size(min = 1, max = 200, message = "Địa chỉ công ty phải từ 1 đến 200 ký tự")
    @ApiModelProperty(value = "Địa chỉ công ty")
    private String companyAddress;

    @ApiModelProperty(value = "ID địa chỉ công ty")
    private String companyLocationIdPath;

    @ApiModelProperty(value = "Tên địa chỉ công ty")
    private String companyLocationPath;

    @ApiModelProperty(value = "Tổng số nhân viên")
    private String staffCount;

    @Min(value = 1, message = "Vốn đăng ký phải lớn hơn 1")
    @ApiModelProperty(value = "Vốn đăng ký")
    private Double companyRegisteredCapital;

    @NotBlank(message = "Tên người liên lạc không được để trống")
    @Length(min = 2, max = 20, message = "Tên người liên lạc phải từ 2 đến 20 ký tự")
    @ApiModelProperty(value = "Tên người liên lạc")
    private String contactName;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0[2-9][0-9]{6,7})|(0[2-9][0-9]{2}[0-9]{6})|(84[2-9][0-9]{6,7})|(84[2-9][0-9]{2}[0-9]{6})|(09[0-9]{8})|(01[2-9][0-9]{8})$", message = "Số điện thoại không hợp lệ")
    @ApiModelProperty(value = "Số điện thoại người liên lạc")
    private String contactPhone;

    @Email
    @ApiModelProperty(value = "Email")
    private String companyEmail;


    /****Thông tin giấy phép kinh doanh***/
    @Size(min = 10, max = 20, message = "Số giấy phép kinh doanh phải từ 10 đến 20 ký tự")
    @ApiModelProperty(value = "Số giấy phép kinh doanh")
    private String businessLicenseNumber;

    @ApiModelProperty(value = "Phạm vi kinh doanh hợp pháp")
    private String scope;

    @NotBlank(message = "Bản sao giấy phép kinh doanh không được để trống")
    @ApiModelProperty(value = "Bản sao giấy phép kinh doanh")
    private String businessLicenseImage;

    /****Thông tin pháp nhân***/
    @NotBlank(message = "Tên pháp nhân không được để trống")
    @Size(min = 2, max = 20, message = "Tên pháp nhân phải từ 2 đến 20 ký tự")
    @ApiModelProperty(value = "Tên pháp nhân")
    private String legalRepresentativeName;

    @NotBlank(message = "Số CCCD không được để trống")
    @Size(min = 12, max = 15, message = "Số CCCD phải từ 12 đến 15 ký tự")
    @ApiModelProperty(value = "Số CCCD")
    private String IdentityCardNumber;

    @NotBlank(message = "Ảnh CCCD không được để trống")
    @ApiModelProperty(value = "Ảnh CCCD")
    private String IdentityCardPhoto;

    /****Thông tin ngân hàng thanh toán***/
    @Size(min = 1, max = 50, message = "Tên ngân hàng thanh toán phải từ 1 đến 50 ký tự")
    @NotBlank(message = "Tên ngân hàng thanh toán không được để trống")
    @ApiModelProperty(value = "Tên ngân hàng thanh toán")
    private String bankAccountName;

    @Size(min = 10, max = 20, message = "Số tài khoản thanh toán phải từ 10 đến 20 ký tự")
    @NotBlank(message = "Số tài khoản thanh toán không được để trống")
    @ApiModelProperty(value = "Số tài khoản thanh toán")
    private String bankAccountNum;

    @Size(min = 1, max = 50, message = "Tên chi nhánh ngân hàng thanh toán phải từ 1 đến 50 ký tự")
    @NotBlank(message = "Tên chi nhánh ngân hàng thanh toán không được để trống")
    @ApiModelProperty(value = "Tên chi nhánh ngân hàng thanh toán")
    private String bankBranchName;

    @Size(min = 10, max = 10, message = "Mã liên ngân hàng phải là 10 chữ số")
    @NotBlank(message = "Mã liên ngân hàng không được để trống")
    @ApiModelProperty(value = "Mã liên ngân hàng")
    private String bankJointName;

    /****Địa chỉ nhận hàng trả lại của cửa hàng***/
    @ApiModelProperty(value = "Tên người nhận hàng trả lại")
    private String storeReturnConsigneeName;

    @ApiModelProperty(value = "Số điện thoại người nhận hàng trả lại")
    private String storeReturnConsigneeMobile;

    @ApiModelProperty(value = "Danh sách ID địa chỉ nhận hàng trả lại, cách nhau bởi dấu ','")
    private String storeReturnAddressIds;

    @ApiModelProperty(value = "Đường dẫn đến địa chỉ nhận hàng trả lại, cách nhau bởi dấu ','")
    private String storeReturnAddressPath;

    @ApiModelProperty(value = "Thông tin chi tiết về địa chỉ nhận hàng trả lại")
    private String storeReturnAddressDetail;


    /****Thông tin giao hàng***/
    @ApiModelProperty(value = "Mã cửa hàng trên nền tảng giao hàng trực tuyến")
    private String GHNStoreCode;

    /****Chu kỳ thanh toán***/
    @ApiModelProperty(value = "Chu kỳ thanh toán (ví dụ: hàng ngày, hàng tuần, hàng tháng)")
    private String paymentCycle;

}
