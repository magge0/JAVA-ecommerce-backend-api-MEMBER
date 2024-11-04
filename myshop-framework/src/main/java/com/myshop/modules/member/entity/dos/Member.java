package com.myshop.modules.member.entity.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.myshop.common.enums.ClientTypeEnum;
import com.myshop.common.security.sensitive.SensitiveData;
import com.myshop.common.security.sensitive.enums.SensitiveStrategy;
import com.myshop.common.utils.CommonUtil;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Thành viên
 */
@Data
@TableName("myshop_member")
@ApiModel(value = "Thành viên")
@NoArgsConstructor
public class Member extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Tên đăng nhập của thành viên")
    private String username;

    @ApiModelProperty(value = "Mật khẩu của thành viên")
    private String password;

    @ApiModelProperty(value = "Biệt danh")
    private String nickName;

    @Min(message = "Tham số giới tính thành viên không chính xác", value = 0)
    @ApiModelProperty(value = "Giới tính của thành viên, 1 là nam, 0 là nữ")
    private Integer sex;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "Ngày sinh của thành viên")
    private Date birthday;

    @ApiModelProperty(value = "ID địa chỉ của thành viên")
    private String regionId;

    @ApiModelProperty(value = "Địa chỉ của thành viên")
    private String region;

    @NotEmpty(message = "Số điện thoại không được để trống")
    @ApiModelProperty(value = "Số điện thoại", required = true)
    @SensitiveData(strategy = SensitiveStrategy.PHONE)
    private String mobile;

    @Min(message = "Phải là số", value = 0)
    @ApiModelProperty(value = "Số điểm tích lũy")
    private Long point;

    @Min(message = "Phải là số", value = 0)
    @ApiModelProperty(value = "Tổng số điểm tích lũy")
    private Long totalPoints;

    @ApiModelProperty(value = "Ảnh đại diện của thành viên")
    private String face;

    @ApiModelProperty(value = "Trạng thái của thành viên")
    private Boolean disabled;

    @ApiModelProperty(value = "Có mở cửa hàng hay không")
    private Boolean hasStore;

    @ApiModelProperty(value = "ID cửa hàng")
    private String storeId;

    /**
     * @see ClientTypeEnum
     */
    @ApiModelProperty(value = "Client")
    private String clientType;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Thời gian đăng nhập cuối cùng")
    private Date lastLoginDate;

    @ApiModelProperty(value = "ID cấp bậc thành viên")
    private String gradeId;

    @Min(message = "Phải là số", value = 0)
    @ApiModelProperty(value = "Số điểm kinh nghiệm")
    private Long experience;


    public Member(String username, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.mobile = phoneNumber;
        this.nickName = CommonUtil.generateSpecialString("User");
        this.disabled = true;
        this.hasStore = false;
        this.sex = 0;
        this.point = 0L;
        this.totalPoints = 0L;
        this.lastLoginDate = new Date();
    }

    public Member(String username, String password, String face, String nickName, Integer sex, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.mobile = phoneNumber;
        this.nickName = nickName;
        this.disabled = true;
        this.hasStore = false;
        this.face = face;
        this.sex = sex;
        this.point = 0L;
        this.totalPoints = 0L;
        this.lastLoginDate = new Date();
    }
}