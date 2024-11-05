package com.myshop.modules.member.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.cache.Cache;
import com.myshop.cache.CachePrefix;
import com.myshop.modules.member.entity.dos.StoreRoleMenu;
import com.myshop.modules.member.entity.vo.StoreMenuUserVO;
import com.myshop.modules.member.mapper.StoreRoleMenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Triển khai lớp nghiệp vụ của menu vai trò
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class StoreRoleMenuServiceImpl extends ServiceImpl<StoreRoleMenuMapper, StoreRoleMenu> implements StoreRoleMenuService {

    @Autowired
    private StoreMenuService storeMenuService;

    @Autowired
    private Cache<Object> cache;

    @Override
    public List<StoreMenuUserVO> findAllMenu(String employeeId, String memberId) {
        String userMenuCacheKey = CachePrefix.STORE_MENU_USER + memberId;
        List<StoreMenuUserVO> menuList = (List<StoreMenuUserVO>) cache.get(userMenuCacheKey);
        if (menuList == null || menuList.isEmpty()) {
            menuList = storeMenuService.getUserRoleMenu(employeeId);
            cache.put(userMenuCacheKey, menuList);
        }
        return menuList;
    }
}
