package com.myshop.modules.member.entity.vo;

import com.myshop.modules.member.entity.dos.StoreMenu;
import lombok.Data;

/**
 * StoreUserMenuVO
 */
@Data
public class StoreMenuUserVO extends StoreMenu {

    private static final long serialVersionUID = -7478970595109016162L;

    /**
     * Là siêu quản trị viên hay không
     */
    private Boolean isSuper;

    public Boolean getSuper() {
        return isSuper != null && isSuper;
    }
}