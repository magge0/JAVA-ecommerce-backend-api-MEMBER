package com.myshop.modules.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.member.entity.dos.StoreRoleMenu;
import com.myshop.modules.member.entity.vo.StoreMenuUserVO;

import java.util.List;

public interface StoreRoleMenuService extends IService<StoreRoleMenu> {


    /**
     * Lấy danh sách quyền hạn menu chi tiết dựa trên tập hợp vai trò
     *
     * @param clerkId  ID nhân viên
     * @param memberId ID thành viên
     * @return
     */
    List<StoreMenuUserVO> findAllMenu(String clerkId, String memberId);
}
