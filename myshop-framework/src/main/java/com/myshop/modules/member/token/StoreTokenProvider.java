package com.myshop.modules.member.token;


import cn.hutool.core.text.CharSequenceUtil;
import com.myshop.cache.Cache;
import com.myshop.cache.CachePrefix;
import com.myshop.common.enums.ResultCode;
import com.myshop.common.exception.ServiceException;
import com.myshop.common.security.AuthUser;
import com.myshop.common.security.enums.PermissionEnum;
import com.myshop.common.security.enums.UserEnums;
import com.myshop.common.security.token.Token;
import com.myshop.common.security.token.JwtTokenUtil;
import com.myshop.common.security.token.base.TokenGeneratorBase;
import com.myshop.modules.member.entity.dos.Employee;
import com.myshop.modules.member.entity.dos.Member;
import com.myshop.modules.member.entity.vo.StoreMenuUserVO;
import com.myshop.modules.member.service.EmployeeService;
import com.myshop.modules.member.service.StoreRoleMenuService;
import com.myshop.modules.store.entity.dos.Store;
import com.myshop.modules.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tạo token cho cửa hàng
 */
@Component
public class StoreTokenProvider extends TokenGeneratorBase<Member> {

    @Autowired
    private StoreService storeService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private StoreRoleMenuService storeMenuPermissionService;

    @Autowired
    private Cache cache;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Token createToken(Member member, Boolean longTerm) {
        if (Boolean.FALSE.equals(member.getHasStore())) {
            throw new ServiceException(ResultCode.STORE_NOT_OPENED);
        }
        // Tìm kiếm thông tin nhân viên dựa trên ID thành viên
        Employee employee = employeeService.getEmployeeByMemberId(member.getId());

        if (employee == null) {
            throw new ServiceException(ResultCode.EMPLOYEE_NOT_FOUND);
        }
        if (Boolean.FALSE.equals(employee.getIsEnabled())) {
            throw new ServiceException(ResultCode.EMPLOYEE_DISABLED);
        }
        // Lấy quyền hạn của người dùng hiện tại
        List<StoreMenuUserVO> storeMenuUserVOS = storeMenuPermissionService.findAllMenu(employee.getId(), member.getId());
        // Lưu vào cache danh sách quyền hạn
        cache.put(CachePrefix.PERMISSION_LIST.getPrefix(UserEnums.STORE) + member.getId(), this.permissionList(storeMenuUserVOS));
        // Tìm kiếm thông tin cửa hàng
        Store store = storeService.getById(employee.getStoreID());
        if (store == null) {
            throw new ServiceException(ResultCode.STORE_NOT_OPENED);
        }
        // Tạo đối tượng AuthUser
        AuthUser authUser = AuthUser.builder().username(member.getUsername()).id(member.getId()).role(UserEnums.STORE).nickName(member.getNickName()).isSuper(employee.getIsAdmin()).clerkId(employee.getId()).face(store.getStoreImage()).storeId(store.getId()).storeName(store.getStoreName()).longTerm(longTerm).build();
        return jwtTokenUtil.createToken(authUser);
    }

    @Override
    public Token refreshToken(String refreshToken) {
        return jwtTokenUtil.refreshToken(refreshToken);
    }

    /**
     * Lấy quyền hạn của người dùng
     *
     * @param storeUserMenus
     * @return
     */
    public Map<String, List<String>> permissionList(List<StoreMenuUserVO> storeUserMenus) {
        Map<String, List<String>> permission = new HashMap<>(2);

        List<String> superMenuPermissions = new ArrayList<>();
        List<String> queryMenuPermissions = new ArrayList<>();
        initializePermissions(superMenuPermissions, queryMenuPermissions);

        // Duyệt qua danh sách menu quyền hạn
        if (storeUserMenus != null && !storeUserMenus.isEmpty()) {
            storeUserMenus.forEach(currentMenu -> {
                // Duyệt qua từng menu, cấp quyền cho người dùng
                if (CharSequenceUtil.isNotEmpty(currentMenu.getPermission())) {
                    // Lấy danh sách URL
                    String[] permissionUrl = currentMenu.getPermission().split(",");
                    // Duyệt qua từng URL
                    for (String url : permissionUrl) {
                        // Nếu là quyền hạn quản trị, thêm vào danh sách quyền hạn quản trị
                        if (Boolean.TRUE.equals(currentMenu.getSuper())) {
                            // Nếu quyền hạn quản trị đã có, không cần thêm vào danh sách
                            if (!superMenuPermissions.contains(url)) {
                                superMenuPermissions.add(url);
                            }
                        }
                        // Nếu không, thêm vào danh sách quyền hạn xem
                        else {
                            // Nếu chưa có quyền hạn xem, thêm vào danh sách
                            if (!queryMenuPermissions.contains(url)) {
                                queryMenuPermissions.add(url);
                            }
                        }
                    }
                }
                // Loại bỏ các quyền hạn trùng lặp
                queryMenuPermissions.removeAll(superMenuPermissions);
            });
        }
        permission.put(PermissionEnum.SUPER.name(), superMenuPermissions);
        permission.put(PermissionEnum.QUERY.name(), queryMenuPermissions);
        return permission;
    }


    /**
     * Khởi tạo các quyền hạn ban đầu, quyền xem bao gồm thống kê lưu lượng truy cập trang chủ,
     * quyền quản trị bao gồm bảo trì thông tin cá nhân, quyền sửa đổi mật khẩu.
     *
     * @param superMenuPermissions Danh sách quyền hạn quản trị
     * @param queryMenuPermissions Danh sách quyền hạn xem
     */
    void initializePermissions(List<String> superMenuPermissions, List<String> queryMenuPermissions) {
        //TODO: Khởi tạo các quyền hạn ban đầu

    }
}