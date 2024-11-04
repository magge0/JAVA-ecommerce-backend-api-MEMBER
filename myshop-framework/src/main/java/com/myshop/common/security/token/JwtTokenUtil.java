package com.myshop.common.security.token;

import com.google.gson.Gson;
import com.myshop.cache.Cache;
import com.myshop.cache.CachePrefix;
import com.myshop.common.enums.ResultCode;
import com.myshop.common.exception.ServiceException;
import com.myshop.common.properties.JWTTokenProperties;
import com.myshop.common.security.AuthUser;
import com.myshop.common.security.enums.SecurityEnum;
import com.myshop.common.security.enums.UserEnums;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * JwtTokenUtil
 *
 * @author vantrang
 */
@Component
public class JwtTokenUtil {

    @Autowired
    private JWTTokenProperties tokenProperties;

    @Autowired
    private Cache cache;

    /**
     * Tạo token
     *
     * @param user Khai báo riêng tư
     * @return TOKEN
     */
    public Token createToken(AuthUser user) {
        Token token = new Token();

        String accessToken = createToken(user, tokenProperties.getTokenExpireTime());

        cache.put(CachePrefix.ACCESS_TOKEN.getPrefix(user.getRole(), user.getId()) + accessToken, 1, tokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);
        // Chiến lược tạo token làm mới: Nếu token có thời hạn dài (cho ứng dụng), thì token làm mới có thời hạn mặc định là 15 ngày. Nếu là đăng nhập người dùng thông thường, thì token làm mới có thời hạn gấp đôi token thông thường.
        Long expireTime = user.getLongTerm() ? 15 * 24 * 60L : tokenProperties.getTokenExpireTime() * 2;
        String refreshToken = createToken(user, expireTime);

        cache.put(CachePrefix.REFRESH_TOKEN.getPrefix(user.getRole(), user.getId()) + refreshToken, 1, expireTime, TimeUnit.MINUTES);

        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        return token;
    }

    /**
     * Làm mới token
     *
     * @param oldRefreshToken Token làm mới
     * @return token
     */
    public Token refreshToken(String oldRefreshToken) {

        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SecretKeyUtil.generalKeyByDecoders()).parseClaimsJws(oldRefreshToken).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            // Token hết hạn, xác thực thất bại,...
            throw new ServiceException(ResultCode.USER_SESSION_EXPIRED);
        }

        // Lấy thông tin người dùng được lưu trữ trong claims
        String json = claims.get(SecurityEnum.USER_CONTEXT_KEY.getValue()).toString();
        AuthUser user = new Gson().fromJson(json, AuthUser.class);
        UserEnums userEnums = user.getRole();

        String username = user.getUsername();
        // Lấy xem token có thời hạn dài hay không
        boolean longTerm = user.getLongTerm();


        // Nếu có token làm mới trong cache và...
        if (cache.hasKey(CachePrefix.REFRESH_TOKEN.getPrefix(userEnums, user.getId()) + oldRefreshToken)) {
            Token token = new Token();

            String accessToken = createToken(user, tokenProperties.getTokenExpireTime());
            cache.put(CachePrefix.ACCESS_TOKEN.getPrefix(userEnums, user.getId()) + accessToken, 1, tokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);

            // Nếu là thiết bị tin cậy, thì gia hạn thời gian token làm mới
            Long expirationTime = tokenProperties.getTokenExpireTime() * 2;
            if (longTerm) {
                expirationTime = 60 * 24 * 15L;
                user.setLongTerm(true);
            }

            // Chiến lược tạo token làm mới: Nếu token có thời hạn dài (cho ứng dụng), thì token làm mới có thời hạn mặc định là 15 ngày. Nếu là đăng nhập người dùng thông thường, thì token làm mới có thời hạn gấp đôi token thông thường.
            String refreshToken = createToken(user, expirationTime);

            cache.put(CachePrefix.REFRESH_TOKEN.getPrefix(userEnums, user.getId()) + refreshToken, 1, expirationTime, TimeUnit.MINUTES);
            token.setAccessToken(accessToken);
            token.setRefreshToken(refreshToken);
            cache.remove(CachePrefix.REFRESH_TOKEN.getPrefix(userEnums, user.getId()) + oldRefreshToken);
            return token;
        } else {
            throw new ServiceException(ResultCode.USER_SESSION_EXPIRED);
        }

    }

    /**
     * Tạo token
     *
     * @param user      Đối tượng chính JWT
     * @param expiresIn Thời gian hết hạn (phút)
     * @return Chuỗi token
     */
    private String createToken(AuthUser user, Long expiresIn) {
        // Tạo JWT
        return Jwts.builder()
                // Khai báo riêng tư JWT
                .claim(SecurityEnum.USER_CONTEXT_KEY.getValue(), new Gson().toJson(user))
                // Chủ thể JWT
                .setSubject(user.getUsername())
                // Thời gian hết hạn: hiện tại + thời gian hết hạn (phút)
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn * 60 * 1000))
                // Thuật toán ký và khóa
                .signWith(SecretKeyUtil.generalKey()).compact();
    }
}
