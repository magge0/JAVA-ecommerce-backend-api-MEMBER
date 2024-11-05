package com.myshop.security;

import com.myshop.cache.Cache;
import com.myshop.common.properties.IgnoredUrlsProperties;
import com.myshop.common.security.InvalidAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfigurationSource;

public class SecurityConfigForBuyer {

    @Autowired
    private IgnoredUrlsProperties ignoredUrlsProperties;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    /**
     * Handling of insufficient permissions
     */
    @Autowired
    private InvalidAuthenticationEntryPoint invalidAuthenticationEntryPoint;

    /**
     * Authentication failure handling class Bean
     */
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * Insufficient permissions for processor bean
     */
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private Cache<String> cache;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(ignoredUrlsProperties.getUrls().toArray(new String[0])).permitAll().anyRequest().authenticated()).headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)).logout(logout -> logout.permitAll())
                //Enable cross-domain
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                //CSRF is disabled because Session is not used
                .csrf(AbstractHttpConfigurer::disable).sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).exceptionHandling(c -> c.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)).addFilter(new AuthenticationFilterForBuyer(authenticationManager, cache));

        return http.build();
    }
}
