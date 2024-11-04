package com.myshop.modules.member.entity.dos;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.myshop.modules.member.entity.dto.EmployeeAddDTO;
import com.myshop.modules.store.entity.dos.Store;
import com.myshop.orm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mô hình nhân viên
 */
@Data
@TableName("myshop_employee")
@ApiModel(value = "Nhân viên")
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Tên nhân viên")
    private String name;

    @ApiModelProperty(value = "ID thành viên")
    private String memberID;

    @ApiModelProperty(value = "ID cửa hàng")
    private String storeID;

    @ApiModelProperty(value = "ID bộ phận")
    private String departmentID;

    @ApiModelProperty(value = "Bộ sưu tập ID vai trò")
    private String roleIDs;

    @ApiModelProperty(value = "Có phải là chủ cửa hàng hay không", hidden = true)
    private Boolean isShopkeeper = false;

    @ApiModelProperty(value = "Có phải là quản trị viên hay không", hidden = true)
    private Boolean isAdmin = false;

    @ApiModelProperty(value = "Trạng thái Mặc định true bình thường false vô hiệu hóa")
    private Boolean isEnabled = true;

    /**
     * Xây dựng nhân viên
     *
     * @param employeeAddDTO
     */
    public Employee(EmployeeAddDTO employeeAddDTO) {
        if (employeeAddDTO.getRoles() != null && !employeeAddDTO.getRoles().isEmpty()) {
            this.roleIDs = CharSequenceUtil.join(",", employeeAddDTO.getRoles());
        }
        this.memberID = employeeAddDTO.getMemberId();
        this.departmentID = employeeAddDTO.getDepartmentId();
        this.storeID = employeeAddDTO.getStoreId();
        this.name = employeeAddDTO.getUsername();

    }


    public Employee(Store store) {
        this.memberID = store.getMemberId();
        this.storeID = store.getId();
        this.name = store.getMemberName();
        this.setIsShopkeeper(true);
        this.setIsAdmin(true);
        this.setIsEnabled(true);
    }
}
