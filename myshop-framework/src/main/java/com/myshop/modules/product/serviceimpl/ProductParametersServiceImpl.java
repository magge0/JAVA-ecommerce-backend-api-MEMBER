package com.myshop.modules.product.serviceimpl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.common.enums.ResultCode;
import com.myshop.common.exception.ServiceException;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductParameters;
import com.myshop.modules.product.entity.dto.ProductParamsDTO;
import com.myshop.modules.product.entity.dto.ProductParamsItemDTO;
import com.myshop.modules.product.mapper.ProductParametersMapper;
import com.myshop.modules.product.service.ProductParametersService;
import com.myshop.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductParametersServiceImpl extends ServiceImpl<ProductParametersMapper, ProductParameters> implements ProductParametersService {


    @Autowired
    private ProductService productService;

    /**
     * Cập nhật thông tin nhóm tham số
     *
     * @param productParameters Thông tin nhóm tham số
     * @return Cập nhật thành công hay không
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateProductParameter(ProductParameters productParameters) {
        ProductParameters origin = this.getById(productParameters.getId());
        if (origin == null) {
            throw new ServiceException(ResultCode.PRODUCT_CATEGORY_NOT_EXIST);
        }

        List<String> productIds = new ArrayList<>();
        LambdaQueryWrapper<Product> productQuery = new LambdaQueryWrapper<>();
        productQuery.select(Product::getId, Product::getParams);
        productQuery.like(Product::getParams, productParameters.getGroupId());
        List<Map<String, Object>> productList = this.productService.listMaps(productQuery);

        if (!productList.isEmpty()) {
            for (Map<String, Object> product : productList) {
                String params = (String) product.get("params");
                List<ProductParamsDTO> goodsParamsDTOS = JSONUtil.toList(params, ProductParamsDTO.class);
                List<ProductParamsDTO> goodsParamsDTOList = goodsParamsDTOS.stream().filter(i -> i.getGroupId() != null && i.getGroupId().equals(productParameters.getGroupId())).collect(Collectors.toList());
                this.setProductItemDTOList(goodsParamsDTOList, productParameters);
                this.productService.updateProductParams(product.get("id").toString(), JSONUtil.toJsonStr(goodsParamsDTOS));
                productIds.add(product.get("id").toString());
            }

            // TODO:  Gửi tin nhắn mq
        }
        return this.updateById(productParameters);
    }

    /**
     * Cập nhật thông tin tham số sản phẩm
     *
     * @param productParamsDTOList Danh sách các mục tham số sản phẩm
     * @param productParameters    Thông tin tham số
     */
    private void setProductItemDTOList(List<ProductParamsDTO> productParamsDTOList, ProductParameters productParameters) {
        // Duyệt qua danh sách các mục tham số sản phẩm
        for (ProductParamsDTO productParamsDTO : productParamsDTOList) {
            // Lọc danh sách các mục tham số con có ID tham số phù hợp
            List<ProductParamsItemDTO> productParamsItemDTOList = productParamsDTO.getProductParamsItemDTOList().stream().filter(i -> i.getParamId() != null && i.getParamId().equals(productParameters.getId())).collect(Collectors.toList());
            // Duyệt qua danh sách các mục tham số con đã lọc
            for (ProductParamsItemDTO productParamsItemDTO : productParamsItemDTOList) {
                // Cập nhật thông tin cho mỗi mục tham số con
                this.setProductItemDTO(productParamsItemDTO, productParameters);
            }
        }
    }


    /**
     * Cập nhật chi tiết thông tin tham số sản phẩm
     *
     * @param productParamsItemDTO Thông tin mục tham số sản phẩm
     * @param productParameters    Thông tin tham số
     */
    private void setProductItemDTO(ProductParamsItemDTO productParamsItemDTO, ProductParameters productParameters) {
        // Nếu ID của mục tham số sản phẩm trùng với ID của thông tin tham số
        if (productParamsItemDTO.getParamId().equals(productParameters.getId())) {
            // Cập nhật thông tin mục tham số sản phẩm từ thông tin tham số
            productParamsItemDTO.setParamId(productParameters.getId());
            productParamsItemDTO.setParamName(productParameters.getParamName());
            productParamsItemDTO.setRequired(productParameters.getRequired());
            productParamsItemDTO.setIsIndex(productParameters.getIsIndex());
            productParamsItemDTO.setSort(productParameters.getSort());

            // Kiểm tra xem giá trị của mục tham số sản phẩm có nằm trong danh sách lựa chọn của tham số không
            if (CharSequenceUtil.isNotEmpty(productParameters.getOptions()) && CharSequenceUtil.isNotEmpty(productParamsItemDTO.getParamValue()) && !productParameters.getOptions().contains(productParamsItemDTO.getParamValue())) {
                // Nếu danh sách lựa chọn có dấu phẩy, chỉ lấy phần tử đầu tiên
                if (productParameters.getOptions().contains(",")) {
                    productParamsItemDTO.setParamValue(productParameters.getOptions().substring(0, productParameters.getOptions().indexOf(",")));
                } else {
                    // Nếu danh sách lựa chọn chỉ có một phần tử, gán giá trị đó cho mục tham số sản phẩm
                    productParamsItemDTO.setParamValue(productParameters.getOptions());
                }
            }
        }
    }

}
