package com.myshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * Manager API
 *
 * @author vantrang
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
public class ManagerApplicationApi {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplicationApi.class, args);
    }

}