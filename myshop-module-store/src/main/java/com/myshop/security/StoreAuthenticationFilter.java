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
import com.myshop.modules.member.entity.dos.Employee;
import com.myshop.modules.member.service.EmployeeService;
import com.myshop.modules.member.service.StoreRoleMenuService;
import com.myshop.modules.member.token.StoreTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * StoreAuthenticationFilter
 *
 * @author vantrang
 */
@Slf4j
public class StoreAuthenticationFilter extends BasicAuthenticationFilter {

    private final Cache cache;

    private StoreTokenProvider storeTokenProvider;

    private StoreRoleMenuService storeRoleMenuService;

    private final EmployeeService employeeService;

    public StoreAuthenticationFilter(AuthenticationManager authenticationManager, StoreTokenProvider storeTokenProvider, StoreRoleMenuService storeRoleMenuService, EmployeeService employeeService, Cache cache) {
        super(authenticationManager);
        this.storeTokenProvider = storeTokenProvider;
        this.storeRoleMenuService = storeRoleMenuService;
        this.employeeService = employeeService;
        this.cache = cache;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Get jwt from header
        String jwt = request.getHeader(SecurityEnum.HEADER_TOKEN.getValue());
        //If there is no token, return
        if (StrUtil.isBlank(jwt)) {
            chain.doFilter(request, response);
            return;
        }
        //Get user information and store it in context
        UsernamePasswordAuthenticationToken authentication = getAuthentication(jwt, response);
        //Custom permission filtering
        if (authentication != null) {
            customAuthentication(request, response, authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
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
            String json = claims.get(SecurityEnum.USER_CONTEXT.getValue()).toString();
            AuthUser authUser = new Gson().fromJson(json, AuthUser.class);

            //Verify whether there are permissions in redis
            if (cache.hasKey(CachePrefix.ACCESS_TOKEN.getPrefix(UserEnums.STORE, authUser.getId()) + jwt)) {
                //user role
                List<GrantedAuthority> auths = new ArrayList<>();
                auths.add(new SimpleGrantedAuthority("ROLE_" + authUser.getRole().name()));
                //Construct return information
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authUser.getUsername(), null, auths);
                authentication.setDetails(authUser);
                return authentication;
            }
            ResponseUtil.output(response, HttpServletResponse.SC_FORBIDDEN, ResponseUtil.resultMap(false, HttpServletResponse.SC_FORBIDDEN, "Login has expired, please log in again!"));
            return null;
        } catch (ExpiredJwtException e) {
            log.debug("User analysis exception:", e);
        } catch (Exception e) {
            log.error("User analysis exception:", e);
        }
        return null;
    }


    /**
     * Custom permission filtering
     *
     * @param request        request
     * @param response       response
     * @param authentication authentication
     */
    private void customAuthentication(HttpServletRequest request, HttpServletResponse response, UsernamePasswordAuthenticationToken authentication) throws NoPermissionException {
        AuthUser authUser = (AuthUser) authentication.getDetails();
        String requestUrl = request.getRequestURI();


        //If you are not a super administrator, authenticate
        if (Boolean.FALSE.equals(authUser.getIsSuper())) {

            String permissionCacheKey = CachePrefix.PERMISSION_LIST.getPrefix(UserEnums.STORE) + authUser.getId();
            //Get permissions from cache
            Map<String, List<String>> permission = (Map<String, List<String>>) cache.get(permissionCacheKey);
            if (permission == null || permission.isEmpty()) {
                //Query store clerk information based on member ID
                Employee employee = employeeService.getEmployeeByMemberId(authUser.getId());
                if (employee != null) {
                    permission = storeTokenProvider.permissionList(storeRoleMenuService.findAllMenu(employee.getId(), authUser.getId()));
                    cache.put(permissionCacheKey, permission);
                }
            }
            //Get data (GET request) permission
            if (request.getMethod().equals(RequestMethod.GET.name())) {
                //Nếu cả quyền siêu cấp và quyền truy cập của người dùng đều không bao gồm API hiện được yêu cầu
                if (match(permission.get(PermissionEnum.SUPER.name()), requestUrl) || match(permission.get(PermissionEnum.QUERY.name()), requestUrl)) {
                } else {
                    ResponseUtil.output(response, ResponseUtil.resultMap(false, HttpServletResponse.SC_BAD_REQUEST, "Insufficient permissions"));
                    log.error("Current request path: {}, permissions owned: {}", requestUrl, JSONUtil.toJsonStr(permission));
                    throw new NoPermissionException("Insufficient permissions");
                }
            }
            //Yêu cầu không nhận được (thao tác dữ liệu) Xác định xác thực
            else {
                if (!match(permission.get(PermissionEnum.SUPER.name()), requestUrl)) {
                    ResponseUtil.output(response, ResponseUtil.resultMap(false, HttpServletResponse.SC_BAD_REQUEST, "Insufficient permissions"));
                    log.error("Current request path: {}, permissions owned: {}", requestUrl, JSONUtil.toJsonStr(permission));
                    throw new NoPermissionException("Insufficient permissions");
                }
            }
        }
    }

    /**
     * Verify permissions
     *
     * @param permissions permissions
     * @param url         url
     * @return Do you have permission?
     */
    boolean match(List<String> permissions, String url) {
        if (permissions == null || permissions.isEmpty()) {
            return false;
        }
        return PatternMatchUtils.simpleMatch(permissions.toArray(new String[0]), url);
    }

}