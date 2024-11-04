package com.myshop.modules.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.member.entity.dos.StoreMenu;
import com.myshop.modules.member.entity.vo.StoreMenuUserVO;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

@CacheConfig(cacheNames = "{store_menu_data}")
public interface StoreMenuService extends IService<StoreMenu> {

    List<StoreMenuUserVO> getUserRoleMenu(String employeeId);
}
