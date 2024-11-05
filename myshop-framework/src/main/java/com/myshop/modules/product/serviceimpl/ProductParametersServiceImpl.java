package com.myshop.modules.product.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.modules.product.entity.dos.ProductParameters;
import com.myshop.modules.product.mapper.ProductParametersMapper;
import com.myshop.modules.product.service.ProductParametersService;
import org.springframework.stereotype.Service;

@Service
public class ProductParametersServiceImpl extends ServiceImpl<ProductParametersMapper, ProductParameters> implements ProductParametersService {
}
