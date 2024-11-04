package com.myshop.modules.member.entity.dto;

import com.myshop.common.security.sensitive.SensitiveData;
import com.myshop.common.security.sensitive.enums.SensitiveStrategy;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * DTO của nhân viên
 */
@Data
@NoArgsConstructor
public class EmployeeAddDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Tên người dùng thành viên")
    @NotEmpty(message = "Tên người dùng thành viên không được để trống")
    @Length(max = 30, message = "Tên người dùng thành viên không được vượt quá 30 ký tự")
    private String username;

    @ApiModelProperty(value = "Mật khẩu thành viên")
    @NotEmpty(message = "Mật khẩu thành viên không được để trống")
    private String password;

    @NotEmpty(message = "Số điện thoại không được để trống")
    @ApiModelProperty(value = "Số điện thoại", required = true)
    @SensitiveData(strategy = SensitiveStrategy.PHONE)
    private String mobile;

    @ApiModelProperty(value = "ID bộ phận")
    private String departmentId;

    @ApiModelProperty(value = "Có phải là quản trị viên siêu cấp hay không Quản trị viên siêu cấp/Quản trị viên thông thường")
    private Boolean isSuper = false;

    @ApiModelProperty(value = "Vai trò")
    private List<String> roles;

    @ApiModelProperty(value = "ID thành viên", required = true)
    private String memberId;

    @ApiModelProperty(value = "Có phải là chủ cửa hàng hay không", hidden = true)
    private Boolean shopkeeper = false;

    @ApiModelProperty(value = "ID cửa hàng", hidden = true)
    private String storeId;


}