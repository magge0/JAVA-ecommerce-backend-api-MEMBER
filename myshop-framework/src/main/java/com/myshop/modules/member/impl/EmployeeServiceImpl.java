package com.myshop.modules.member.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.modules.member.entity.dos.Employee;
import com.myshop.modules.member.mapper.EmployeeMapper;
import com.myshop.modules.member.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Override
    public Employee getEmployeeByMemberId(String memberId) {
        return this.getOne(new QueryWrapper<Employee>().eq("member_id", memberId));
    }
}
