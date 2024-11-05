package com.myshop.security;

import com.myshop.cache.Cache;
import com.myshop.common.properties.IgnoredUrlsProperties;
import com.myshop.common.security.InvalidAuthenticationEntryPoint;
import com.myshop.modules.member.service.EmployeeService;
import com.myshop.modules.member.service.StoreRoleMenuService;
import com.myshop.modules.member.token.StoreTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Slf4j
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@Configuration
public class SecurityConfigForStore {

    @Autowired
    private IgnoredUrlsProperties ignoredUrlsProperties;

    @Autowired
    private InvalidAuthenticationEntryPoint invalidAuthenticationEntryPoint;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

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

    @Autowired
    private StoreTokenProvider storeTokenProvider;

    @Autowired
    private StoreRoleMenuService storeRoleMenuService;

    @Autowired
    private EmployeeService employeeService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorize -> {
                    for (String url : ignoredUrlsProperties.getUrls()) {
                        authorize.requestMatchers(url).permitAll();
                    }
                    authorize.anyRequest().authenticated();
                }).logout(logout -> logout.permitAll())
                //Enable cross-domain
                .cors(cors -> cors.configurationSource(corsConfigurationSource)).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //A bunch of custom Spring Security handlers
                .exceptionHandling(c -> c.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)).headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        // Add custom JWT authentication filter
        http.addFilterBefore(new AuthenticationFilterForStore(authenticationManager, storeTokenProvider, storeRoleMenuService, employeeService, cache), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}