package com.myshop.modules.store.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.modules.store.entity.dos.Store;
import com.myshop.modules.store.mapper.StoreMapper;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {
}
