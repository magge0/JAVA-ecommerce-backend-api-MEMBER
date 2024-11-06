package com.myshop.modules.product.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.modules.product.entity.dos.ProductCategoryParameterGroup;
import com.myshop.modules.product.entity.dos.ProductParameters;
import com.myshop.modules.product.entity.vos.ProductParameterGroupVO;
import com.myshop.modules.product.mapper.ProductCategoryParameterGroupMapper;
import com.myshop.modules.product.service.ProductCategoryParameterGroupService;
import com.myshop.modules.product.service.ProductParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductCategoryParameterGroupServiceImpl extends ServiceImpl<ProductCategoryParameterGroupMapper, ProductCategoryParameterGroup> implements ProductCategoryParameterGroupService {

    @Autowired
    private ProductParametersService productParametersService;

    @Override
    public List<ProductParameterGroupVO> getProductCategoryParams(String categoryId) {
        // Truy vấn nhóm tham số theo ID
        List<ProductCategoryParameterGroup> groups = this.getProductCategoryGroup(categoryId);
        // Truy vấn tham số
        List<ProductParameters> params = productParametersService.list(new QueryWrapper<ProductParameters>().eq("category_id", categoryId));
        // Kết hợp VO tham số
        return convertParamList(groups, params);
    }

    /**
     * Ghép kết quả trả về của nhóm tham số và tham số
     *
     * @param groupList     Danh sách nhóm tham số
     * @param productParams Danh sách tham số sản phẩm
     * @return Kết quả trả về của nhóm tham số và tham số
     */
    public List<ProductParameterGroupVO> convertParamList(List<ProductCategoryParameterGroup> groupList, List<ProductParameters> productParams) {
        Map<String, List<ProductParameters>> map = new HashMap<>(productParams.size());
        for (ProductParameters productParameter : productParams) {
            List<ProductParameters> list = map.get(productParameter.getGroupId());
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(productParameter);
            map.put(productParameter.getGroupId(), list);
        }
        List<ProductParameterGroupVO> resList = new ArrayList<>();
        for (ProductCategoryParameterGroup group : groupList) {
            ProductParameterGroupVO parameterGroup = new ProductParameterGroupVO();
            parameterGroup.setGroupId(group.getId());
            parameterGroup.setGroupName(group.getGroupName());
            parameterGroup.setParams(map.get(group.getId()) == null ? new ArrayList<>() : map.get(group.getId()));
            resList.add(parameterGroup);
        }
        return resList;
    }

    @Override
    public List<ProductCategoryParameterGroup> getProductCategoryGroup(String categoryId) {
        return this.list(new QueryWrapper<ProductCategoryParameterGroup>().eq("category_id", categoryId));
    }

    @Override
    public void deleteByProductCategoryId(String categoryId) {
        this.baseMapper.delete(new LambdaUpdateWrapper<ProductCategoryParameterGroup>().eq(ProductCategoryParameterGroup::getCategoryId, categoryId));
    }
}
