package com.myshop.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Cấu hình luồng
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "myshop.thread")
public class ThreadPoolProperties {


    /**
     * Số lượng luồng core
     */
    private Integer corePoolSize = 10;

    /**
     * Số lượng luồng tối đa
     */
    private Integer maxPoolSize = 50;

    /**
     * Độ dài tối đa của hàng đợi
     */
    private Integer queueCapacity = Integer.MAX_VALUE;

    /**
     * Cho phép đóng luồng hết thời gian chờ
     */
    private Boolean allowCoreThreadTimeOut = false;

    /**
     * Keep Alive
     */
    private Integer keepAliveSeconds = 60;


}