package com.myshop.modules.member.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.modules.member.entity.dos.StoreMenu;
import com.myshop.modules.member.entity.vo.StoreMenuUserVO;
import com.myshop.modules.member.mapper.StoreMenuMapper;
import com.myshop.modules.member.service.StoreMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StoreMenuServiceImpl extends ServiceImpl<StoreMenuMapper, StoreMenu> implements StoreMenuService {
    @Override
    public List<StoreMenuUserVO> getUserRoleMenu(String employeeId) {
        return this.baseMapper.getUserRoleMenu(employeeId);
    }
}
