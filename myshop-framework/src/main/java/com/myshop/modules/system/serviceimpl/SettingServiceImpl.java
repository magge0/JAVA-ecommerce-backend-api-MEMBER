package com.myshop.modules.system.serviceimpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.modules.system.entity.dos.Setting;
import com.myshop.modules.system.mapper.SettingMapper;
import com.myshop.modules.system.service.SettingService;
import org.springframework.stereotype.Service;

/**
 * Cài đặt lớp dịch vụ
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {

    @Override
    public Setting get(String key) {
        return this.getById(key);
    }

}
