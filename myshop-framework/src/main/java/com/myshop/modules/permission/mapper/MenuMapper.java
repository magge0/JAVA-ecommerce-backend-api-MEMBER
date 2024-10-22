package com.myshop.modules.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myshop.modules.permission.entity.dos.Menu;
import com.myshop.modules.permission.entity.vo.UserMenuVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * Get menu permissions based on user
     *
     * @param id user ID
     * @return User menu VO list
     */
    @Select("SELECT rm.is_super as is_super,m.*FROM myshop_menu AS m INNER JOIN myshop_role_menu AS rm ON rm.menu_id=m.id WHERE rm.role_id IN (" + "SELECT ur.role_id FROM myshop_user_role AS ur WHERE ur.user_id=#{id}) OR rm.role_id IN (" + "SELECT dr.role_id FROM myshop_department_role AS dr INNER JOIN myshop_admin_user AS au ON au.department_id=dr.department_id " + "WHERE au.id=#{id}) GROUP BY m.id,rm.is_super ORDER BY rm.is_super desc")
    List<UserMenuVO> getUserRoleMenu(String id);
}
