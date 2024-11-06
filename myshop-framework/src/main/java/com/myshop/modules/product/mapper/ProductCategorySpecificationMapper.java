package com.myshop.modules.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myshop.modules.product.entity.dos.ProductCategorySpecification;
import com.myshop.modules.product.entity.dos.ProductSpecification;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductCategorySpecificationMapper extends BaseMapper<ProductCategorySpecification> {
    /**
     * Truy vấn thông số kỹ thuật liên kết theo ID phân loại
     *
     * @param categoryId ID phân loại
     * @return Danh sách thông số kỹ thuật liên kết phân loại
     */
    @Select("select s.* from  myshop_product_specification s INNER join myshop_product_category_specification cs " + "on s.id = cs.product_specification_id and cs.product_category_id = #{categoryId} ")
    List<ProductSpecification> getProductCategorySpecList(String categoryId);
}
