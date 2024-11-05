package com.myshop.modules.product.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.common.enums.ResultCode;
import com.myshop.common.exception.ServiceException;
import com.myshop.modules.product.entity.dos.ProductCategorySpecification;
import com.myshop.modules.product.entity.dos.ProductSpecification;
import com.myshop.modules.product.mapper.ProductSpecificationMapper;
import com.myshop.modules.product.service.ProductCategoryService;
import com.myshop.modules.product.service.ProductCategorySpecificationService;
import com.myshop.modules.product.service.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSpecificationServiceImpl extends ServiceImpl<ProductSpecificationMapper, ProductSpecification> implements ProductSpecificationService {

    @Autowired
    private ProductCategorySpecificationService productCategorySpecificationService;
    /**
     * Phân loại
     */
    @Autowired
    private ProductCategoryService productCategoryService;


    @Override
    public boolean deleteProductSpecification(List<String> ids) {
        boolean result = false;
        for (String id : ids) {
            // Nếu thông số kỹ thuật này được liên kết với phân loại thì không được phép xóa
            List<ProductCategorySpecification> list = productCategorySpecificationService.list(new QueryWrapper<ProductCategorySpecification>().eq("product_specification_id", id));
            if (!list.isEmpty()) {
                List<String> categoryIds = new ArrayList<>();
                list.forEach(item -> categoryIds.add(item.getCategoryId()));
                throw new ServiceException(ResultCode.PRODUCT_SPEC_DELETE_ERROR, JSONUtil.toJsonStr(productCategoryService.getProductCategoryNameByIds(categoryIds)));
            }
            // Xóa thông số kỹ thuật
            result = this.removeById(id);
        }
        return result;
    }
}
