package com.myshop.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * Bean Security
 */
@Configuration
public class SecurityBean {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Định nghĩa cấu hình chéo miền
     *
     * @return bean
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // Khởi tạo một đối tượng UrlBasedCorsConfigurationSource, một lớp được sử dụng để cấu hình CORS dựa trên URL
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Khởi tạo một đối tượng CorsConfiguration, một lớp được sử dụng để xác định các thông số cấu hình CORS
        CorsConfiguration config = new CorsConfiguration();
        // Cho phép gửi cookie trong các yêu cầu cross-origin
        config.setAllowCredentials(true);
        // Cho phép tất cả các nguồn gốc
        config.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
        // Cho phép tất cả các header
        config.addAllowedHeader(CorsConfiguration.ALL);
        // Cho phép tất cả các phương thức HTTP
        config.addAllowedMethod(CorsConfiguration.ALL);
        // Đăng ký cấu hình CORS cho tất cả các URL
        source.registerCorsConfiguration("/**", config);
        // Trả về đối tượng CorsConfigurationSource
        return source;
    }
}
