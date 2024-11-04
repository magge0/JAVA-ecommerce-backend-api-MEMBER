package com.myshop.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.myshop.cache.Cache;
import com.myshop.cache.CachePrefix;
import com.myshop.common.security.AuthUser;
import com.myshop.common.security.enums.PermissionEnum;
import com.myshop.common.security.enums.SecurityEnum;
import com.myshop.common.security.enums.UserEnums;
import com.myshop.common.security.token.SecretKeyUtil;
import com.myshop.common.utils.ResponseUtil;
import com.myshop.modules.permission.service.MenuService;
import com.myshop.modules.system.token.ManagerTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Management-side token filtering
 */
@Slf4j
public class AuthenticationFilterForManagement extends BasicAuthenticationFilter {

    private final Cache cache;

    public final MenuService menuService;

    private final ManagerTokenProvider managerTokenProvider;

    public AuthenticationFilterForManagement(AuthenticationManager authenticationManager, MenuService menuService, ManagerTokenProvider managerTokenProvider, Cache cache) {
        super(authenticationManager);
        this.cache = cache;
        this.menuService = menuService;
        this.managerTokenProvider = managerTokenProvider;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {

        //Get jwt from header
        String authorizationToken = request.getHeader(SecurityEnum.AUTHORIZATION_HEADER.getValue());
        //If there is no token, return
        if (StrUtil.isBlank(authorizationToken)) {
            chain.doFilter(request, response);
            return;
        }

        //Get user information and store it in context
        UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationToken, response);
        //Custom permission filtering
        if (authentication != null) {
            customAuthentication(request, response, authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }


    /**
     * Custom permission filtering
     *
     * @param request
     * @param response
     * @param authentication
     * @throws NoPermissionException
     */
    private void customAuthentication(HttpServletRequest request, HttpServletResponse response, UsernamePasswordAuthenticationToken authentication) throws NoPermissionException {
        AuthUser user = (AuthUser) authentication.getDetails();
        String requestUrl = request.getRequestURI();


        //If you are not a super administrator, authenticate
        if (Boolean.FALSE.equals(user.getIsSuper())) {
            String permissionCacheKey = CachePrefix.PERMISSION_LIST.getPrefix(UserEnums.MANAGER) + user.getId();
            //Get permissions from cache
            Map<String, List<String>> permission = (Map<String, List<String>>) cache.get(permissionCacheKey);
            if (permission == null || permission.isEmpty()) {
                permission = managerTokenProvider.permissionList(this.menuService.findAllMenu(user.getId()));
                cache.put(permissionCacheKey, permission);
            }
            //Get data (GET request) permission
            if (request.getMethod().equals(RequestMethod.GET.name())) {
                //If the user's super permissions and query permissions do not include the current request's API.
                if (match(permission.get(PermissionEnum.SUPER.name()), requestUrl) || match(permission.get(PermissionEnum.QUERY.name()), requestUrl)) {
                } else {
                    ResponseUtil.output(response, ResponseUtil.resultMap(false, HttpServletResponse.SC_BAD_REQUEST, "Don't have enough permissions!"));
                    log.error("Current request path: {}, owned permissions: {}", requestUrl, JSONUtil.toJsonStr(permission));
                    throw new NoPermissionException("Don't have enough permissions!");
                }
            }
            //Non-GET requests (data manipulation) authentication determination
            else {
                if (!match(permission.get(PermissionEnum.SUPER.name()), requestUrl)) {
                    ResponseUtil.output(response, ResponseUtil.resultMap(false, HttpServletResponse.SC_BAD_REQUEST, "insufficient permissions"));
                    log.error("Currently requested path：{}, Permissions owned：{}", requestUrl, JSONUtil.toJsonStr(permission));
                    throw new NoPermissionException("insufficient permissions");
                }
            }
        }
    }


    /**
     * Verify permissions
     *
     * @param permissions
     * @param url
     * @return
     */
    boolean match(List<String> permissions, String url) {
        // Kiểm tra xem danh sách permissions có null hoặc rỗng hay không
        if (permissions == null || permissions.isEmpty()) {
            return false;
        }
        // Sử dụng PatternMatchUtils.simpleMatch() để kiểm tra xem url có khớp với bất kỳ pattern nào trong danh sách permissions hay không
        return PatternMatchUtils.simpleMatch(permissions.toArray(new String[0]), url);
    }


    /**
     * Get token information
     *
     * @param jwt
     * @param response
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String jwt, HttpServletResponse response) {

        try {
            Claims claims = Jwts.parser().setSigningKey(SecretKeyUtil.generalKeyByDecoders()).parseClaimsJws(jwt).getBody();
            //Get user information stored in claims
            String userContextJson = claims.get(SecurityEnum.USER_CONTEXT_KEY.getValue()).toString();
            AuthUser authUser = new Gson().fromJson(userContextJson, AuthUser.class);

            //Xác minh xem có quyền trong redis không
            if (cache.hasKey(CachePrefix.ACCESS_TOKEN.getPrefix(UserEnums.MANAGER, authUser.getId()) + jwt)) {
                //vai trò người dùng
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + authUser.getRole().name()));
                //Xây dựng thông tin trả về
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authUser.getUsername(), null, authorities);
                authentication.setDetails(authUser);
                return authentication;
            }
            ResponseUtil.output(response, HttpServletResponse.SC_FORBIDDEN, ResponseUtil.resultMap(false, HttpServletResponse.SC_FORBIDDEN, "Login has expired, please log in again"));
            return null;
        } catch (ExpiredJwtException e) {
            log.debug("user analysis exception:", e);
        } catch (Exception e) {
            log.error("other exception:", e);
        }
        return null;
    }
}