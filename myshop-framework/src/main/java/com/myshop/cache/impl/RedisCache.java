package com.myshop.cache.impl;

import com.myshop.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisCache implements Cache {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public RedisCache() {
    }

    @Override
    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void put(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void put(Object key, Object value, Long exp) {
        put(key, value, exp, TimeUnit.SECONDS);
    }

    /**
     * Lưu trữ giá trị vào Redis với thời hạn hiệu lực.
     *
     * @param key      Khóa của giá trị
     * @param value    Giá trị cần lưu trữ
     * @param ttl      Thời hạn hiệu lực
     * @param timeUnit Đơn vị thời gian cho thời hạn hiệu lực
     */
    @Override
    public void put(Object key, Object value, Long ttl, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, ttl, timeUnit);
    }

    @Override
    public boolean hasKey(Object key) {
        return this.redisTemplate.opsForValue().get(key) != null;
    }

    @Override
    public Boolean remove(Object key) {
        return redisTemplate.delete(key);
    }
}
