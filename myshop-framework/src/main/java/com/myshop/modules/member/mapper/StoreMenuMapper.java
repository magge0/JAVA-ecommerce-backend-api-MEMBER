package com.myshop.modules.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myshop.modules.member.entity.dos.StoreMenu;
import com.myshop.modules.member.entity.vo.StoreMenuUserVO;

import java.util.List;

public interface StoreMenuMapper extends BaseMapper<StoreMenu> {


    /**
     * Lấy quyền hạn menu dựa trên người dùng
     *
     * @param userId ID người dùng
     * @return Danh sách StoreMenuUserVO của người dùng
     */
    List<StoreMenuUserVO> getUserRoleMenu(String userId);
}
