package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.product.entity.dos.ProductCategory;
import com.myshop.modules.product.entity.dto.ProductCategorySearchParams;
import com.myshop.modules.product.entity.vos.ProductCategoryVO;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductCategoryService extends IService<ProductCategory> {

    /**
     * Lấy danh sách tất cả các phân loại, bao gồm cả quan hệ cha-con
     *
     * @param parentId ID của phân loại cha
     * @return Danh sách tất cả các phân loại, bao gồm cả quan hệ cha-con
     */
    List<ProductCategoryVO> listSubCategories(String parentId);

    /**
     * Lấy cây phân loại
     *
     * @return Cây phân loại
     */
    List<ProductCategoryVO> getCategoryTree();

    /**
     * Lấy tên danh mục theo danh sách ID danh mục đã cho
     *
     * @param categoryIds Danh sách ID danh mục
     * @return Danh sách tên danh mục
     */
    Object getProductCategoryNameByIds(List<String> categoryIds);


    /**
     * Quản trị viên lấy tất cả các danh mục
     * Nghĩa là các đối tượng được lấy, cho dù đã bị xóa hay chưa, đều được hiển thị, và không lấy từ bộ nhớ cache, đảm bảo nội dung là mới nhất
     *
     * @param parentId ID của danh mục cha
     * @return Danh sách danh mục sản phẩm
     */
    List<ProductCategory> getAllCategories(String parentId);

    /**
     * Truy vấn tất cả các danh mục, bao gồm cả mối quan hệ cha-con
     * Lấy dữ liệu từ cơ sở dữ liệu
     *
     * @param categorySearchParams Tham số tìm kiếm
     * @return Tất cả các danh mục, bao gồm cả mối quan hệ cha-con
     */
    List<ProductCategoryVO> getAllChildren(ProductCategorySearchParams categorySearchParams);

    /**
     * Truy vấn tất cả các danh mục, bao gồm cả mối quan hệ cha-con
     *
     * @param parentId ID của danh mục cha
     * @return Tất cả các danh mục, bao gồm cả mối quan hệ cha-con
     */
    List<ProductCategoryVO> getAllChildren(String parentId);

    /**
     * Thêm loại sản phẩm
     *
     * @param productCategory Thông tin loại sản phẩm
     * @return Kết quả thêm
     */
    boolean saveCategory(@Valid ProductCategory productCategory);

    /**
     * Sửa đổi loại sản phẩm
     *
     * @param productCategory Thông tin loại sản phẩm
     * @return Kết quả sửa đổi
     */
    void updateCategory(@Valid ProductCategoryVO productCategory);

    /**
     * Lấy danh sách loại sản phẩm
     *
     * @param productCategory Loại sản phẩm
     * @return Danh sách loại sản phẩm
     */
    List<ProductCategory> findByAllBySortOrder(ProductCategory productCategory);

    /**
     * Xóa nhiều loại sản phẩm
     *
     * @param id ID của loại sản phẩm
     */
    void delete(String id);

    /**
     * Thay đổi trạng thái của loại sản phẩm
     *
     * @param categoryId ID của loại sản phẩm
     * @param enable     Cho phép sử dụng hay không
     */
    void updateProductCategoryStatus(String categoryId, Boolean enable);
}
