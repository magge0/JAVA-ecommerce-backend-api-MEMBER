package com.myshop.security;


import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.myshop.cache.Cache;
import com.myshop.cache.CachePrefix;
import com.myshop.common.security.AuthUser;
import com.myshop.common.security.enums.SecurityEnum;
import com.myshop.common.security.enums.UserEnums;
import com.myshop.common.security.token.SecretKeyUtil;
import com.myshop.common.utils.ResponseUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AuthenticationFilterForBuyer extends BasicAuthenticationFilter {

    @Autowired
    private Cache cache;

    /**
     * Custom constructor
     *
     * @param authenticationManager
     * @param cache
     */
    public AuthenticationFilterForBuyer(AuthenticationManager authenticationManager, Cache cache) {
        super(authenticationManager);
        this.cache = cache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Get jwt from header
        String authorizationToken = request.getHeader(SecurityEnum.AUTHORIZATION_HEADER.getValue());
        try {
            //If there is no token, return
            if (StrUtil.isBlank(authorizationToken)) {
                chain.doFilter(request, response);
                return;
            }
            //Get user information and store it in context
            UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationToken, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.error("BuyerAuthenticationFilter-> member authentication exception:", e);
        }
        chain.doFilter(request, response);
    }

    /**
     * parse user
     *
     * @param authorizationToken
     * @param response
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String authorizationToken, HttpServletResponse response) {

        try {
            Claims claims = Jwts.parser().setSigningKey(SecretKeyUtil.generalKeyByDecoders()).parseClaimsJws(authorizationToken).getBody();
            //Get user information stored in claims
            String userContextJson = claims.get(SecurityEnum.USER_CONTEXT_KEY.getValue()).toString();
            AuthUser user = new Gson().fromJson(userContextJson, AuthUser.class);

            //Verify whether there are permissions in redis
            if (cache.hasKey(CachePrefix.ACCESS_TOKEN.getPrefix(UserEnums.MEMBER, user.getId()) + authorizationToken)) {
                //Xây dựng thông tin trả về
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
                authentication.setDetails(user);
                return authentication;
            }
            ResponseUtil.output(response, HttpServletResponse.SC_FORBIDDEN, ResponseUtil.resultMap(false, HttpServletResponse.SC_FORBIDDEN, "Login has expired, please log in again"));
            return null;
        } catch (ExpiredJwtException e) {
            log.debug("user analysis exception:", e);
        } catch (Exception e) {
            log.error("user analysis exception:", e);
        }
        return null;
    }
}
