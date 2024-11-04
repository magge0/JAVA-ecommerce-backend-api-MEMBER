package com.myshop.common.thread;

import com.myshop.common.properties.ThreadPoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Cấu hình đa luồng
 */
@Configuration
public class ThreadPoolConfig implements AsyncConfigurer {


    @Autowired
    private ThreadPoolProperties threadPoolProperties;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        //Số lượng luồng cốt lõi, mặc định là 5
        threadPool.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        //Số lượng luồng tối đa, mặc định là 10
        threadPool.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        //Độ dài tối đa của hàng đợi, thường cần thiết lập giá trị đủ lớn
        threadPool.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        //Thời gian chờ tối đa của luồng trong bộ nhớ cache, mặc định là 60s
        threadPool.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        //Cho phép đóng luồng hết thời gian chờ
        threadPool.setAllowCoreThreadTimeOut(threadPoolProperties.getAllowCoreThreadTimeOut());
        threadPool.initialize();
        return threadPool;
    }
}