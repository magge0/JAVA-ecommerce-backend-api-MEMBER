package com.myshop.modules.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.member.entity.dos.Employee;

public interface EmployeeService extends IService<Employee> {
    /**
     * Lấy thông tin nhân viên dựa trên ID thành viên
     *
     * @param memberId ID thành viên
     * @return
     */
    Employee getEmployeeByMemberId(String memberId);
}
