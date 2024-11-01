package com.myshop.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.system.entity.dos.Setting;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "{SETTING_SYSTEM}")
public interface SettingService extends IService<Setting> {
    /**
     * Lấy cấu hình theo key
     *
     * @param settingKey
     * @return
     */
    @Cacheable(key = "#key")
    Setting get(String settingKey);
}
