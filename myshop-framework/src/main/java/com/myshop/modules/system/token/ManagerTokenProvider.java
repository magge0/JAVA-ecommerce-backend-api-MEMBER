package com.myshop.modules.system.token;

import cn.hutool.core.text.CharSequenceUtil;
import com.myshop.cache.Cache;
import com.myshop.cache.CachePrefix;
import com.myshop.common.security.AuthUser;
import com.myshop.common.security.enums.PermissionEnum;
import com.myshop.common.security.enums.UserEnums;
import com.myshop.common.security.token.JwtTokenUtil;
import com.myshop.common.security.token.Token;
import com.myshop.common.security.token.base.TokenGeneratorBase;
import com.myshop.modules.permission.entity.dos.AdminUser;
import com.myshop.modules.permission.entity.vo.UserMenuVO;
import com.myshop.modules.permission.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lớp tạo token cho quản lý
 * Lớp này kế thừa từ AbstractTokenGenerate và chịu trách nhiệm tạo token JWT cho người dùng quản lý.
 */
@Component
public class ManagerTokenProvider extends TokenGeneratorBase<AdminUser> {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MenuService menuService;

    @Autowired
    private Cache cache;

    /**
     * Tạo token mới cho người dùng quản lý.
     *
     * @param adminUser Người dùng quản lý
     * @param longTerm  Cờ xác định token dài hạn hay ngắn hạn
     * @return Đối tượng Token
     */
    @Override
    public Token createToken(AdminUser adminUser, Boolean longTerm) {
        // Xây dựng đối tượng AuthUser từ AdminUser
        AuthUser user = AuthUser.builder().username(adminUser.getUsername()) // Tên đăng nhập
                .id(adminUser.getId()) // ID người dùng
                .face(adminUser.getAvatar()) // Ảnh đại diện
                .role(UserEnums.MANAGER) // Vai trò (MANAGER)
                .nickName(adminUser.getNickName()) // Biệt danh
                .isSuper(adminUser.getIsSuper()) // Cờ super admin
                .longTerm(longTerm) // Cờ token dài hạn
                .build();

        List<UserMenuVO> userMenuPermissions = menuService.findAllMenu(user.getId()); // Lấy danh sách menu của người dùng

        // Lưu danh sách quyền vào cache
        cache.put(CachePrefix.PERMISSION_LIST.getPrefix(UserEnums.MANAGER) + user.getId(), this.permissionList(userMenuPermissions));

        return jwtTokenUtil.createToken(user); // Tạo token JWT
    }

    /**
     * Làm mới token.
     *
     * @param refreshToken Refresh token
     * @return Token mới
     */
    @Override
    public Token refreshToken(String refreshToken) {
        return jwtTokenUtil.refreshToken(refreshToken); // Gọi phương thức refreshToken của TokenUtil
    }

    /**
     * Lấy danh sách quyền của người dùng.
     *
     * @param managerUserMenus Danh sách menu của người dùng
     * @return Map chứa danh sách quyền, với key là loại quyền (SUPER, QUERY) và value là danh sách URL được phép truy cập.
     */
    public Map<String, List<String>> permissionList(List<UserMenuVO> managerUserMenus) {
        Map<String, List<String>> permission = new HashMap<>(2); // Khởi tạo map

        List<String> superMenuPermissions = new ArrayList<>(); // Danh sách quyền super admin
        List<String> queryMenuPermissions = new ArrayList<>(); // Danh sách quyền xem
        initializePermissions(superMenuPermissions, queryMenuPermissions); // Khởi tạo quyền mặc định

        // Duyệt qua danh sách menu
        if (managerUserMenus != null && !managerUserMenus.isEmpty()) {
            managerUserMenus.forEach(menu -> {
                // Kiểm tra quyền của menu
                if (CharSequenceUtil.isNotEmpty(menu.getPermission())) {
                    String[] permissionUrl = menu.getPermission().split(","); // Tách chuỗi quyền thành mảng URL

                    for (String url : permissionUrl) { // Duyệt qua từng URL
                        if (Boolean.TRUE.equals(menu.getSuper())) { // Nếu là quyền super admin
                            if (!superMenuPermissions.contains(url)) { // Tránh trùng lặp
                                superMenuPermissions.add(url); // Thêm URL vào danh sách quyền super admin
                            }
                        } else { // Nếu là quyền xem
                            if (!queryMenuPermissions.contains(url)) { // Tránh trùng lặp
                                queryMenuPermissions.add(url); // Thêm URL vào danh sách quyền xem
                            }
                        }
                    }
                }
                queryMenuPermissions.removeAll(superMenuPermissions); // Loại bỏ quyền xem bị trùng với quyền super admin
            });
        }

        permission.put(PermissionEnum.SUPER.name(), superMenuPermissions); // Lưu danh sách quyền super admin vào map
        permission.put(PermissionEnum.QUERY.name(), queryMenuPermissions); // Lưu danh sách quyền xem vào map
        return permission;
    }

    /**
     * Khởi tạo quyền mặc định.
     * Quyền xem bao gồm quyền xem thống kê lưu lượng truy cập trang chủ.
     * Quyền super admin bao gồm quyền quản lý thông tin cá nhân và thay đổi mật khẩu.
     *
     * @param superMenuPermissions Danh sách quyền super admin
     * @param queryMenuPermissions Danh sách quyền xem
     */
    void initializePermissions(List<String> superMenuPermissions, List<String> queryMenuPermissions) {
        //TODO Quản lý thông tin người dùng--Quyền thao tác
    }

}
