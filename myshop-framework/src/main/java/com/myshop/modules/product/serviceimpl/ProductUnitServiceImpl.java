package com.myshop.modules.product.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.modules.product.entity.dos.ProductUnit;
import com.myshop.modules.product.mapper.ProductUnitMapper;
import com.myshop.modules.product.service.ProductUnitService;
import org.springframework.stereotype.Service;

@Service
public class ProductUnitServiceImpl extends ServiceImpl<ProductUnitMapper, ProductUnit> implements ProductUnitService {
}
