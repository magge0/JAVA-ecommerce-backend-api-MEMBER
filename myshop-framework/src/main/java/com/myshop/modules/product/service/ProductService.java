package com.myshop.modules.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductCategory;
import com.myshop.modules.product.entity.dos.ProductSearchParams;
import com.myshop.modules.product.entity.dto.ProductOperationDTO;
import com.myshop.modules.product.entity.enums.ProductStatusEnum;
import com.myshop.modules.product.entity.vos.ProductVO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ProductService extends IService<Product> {

    /**
     * Thêm sản phẩm
     *
     * @param productOperationDTO Điều kiện tìm kiếm sản phẩm
     */
    void addProduct(ProductOperationDTO productOperationDTO);

    /**
     * Truy vấn sản phẩm
     *
     * @param productSearchParams Tham số truy vấn
     * @return Phân trang sản phẩm
     */
    IPage<Product> getByParams(ProductSearchParams productSearchParams);

    /**
     * Cập nhật trạng thái đưa sản phẩm lên kệ
     *
     * @param productIds        Danh sách ID sản phẩm
     * @param productStatusEnum Trạng thái sản phẩm cần cập nhật
     * @param underReason       Lý do đưa sản phẩm xuống kệ
     * @return Kết quả cập nhật
     */
    Boolean updateProductMarketAble(List<String> productIds, ProductStatusEnum productStatusEnum, String underReason);

    /**
     * Cập nhật trạng thái lên kệ của sản phẩm
     *
     * @param productIds        Danh sách ID sản phẩm
     * @param productStatusEnum Trạng thái sản phẩm cần cập nhật
     * @param underReason       Lý do xuống kệ
     * @return Kết quả cập nhật
     */
    Boolean managerUpdateProductMarketAble(List<String> productIds, ProductStatusEnum productStatusEnum, String underReason);

    /**
     * Tìm kiếm sản phẩm VO
     *
     * @param productId ID sản phẩm
     * @return VO sản phẩm
     */
    ProductVO getProductVO(String productId);

    /**
     * Lấy sản phẩm dựa trên ID thương hiệu
     *
     * @param brandIds Danh sách ID thương hiệu
     */
    List<Product> getByBrandIds(List<String> brandIds);

    /**
     * Cập nhật thông tin tham số sản phẩm
     *
     * @param productId ID của sản phẩm
     * @param params    Thông tin tham số sản phẩm
     */
    void updateProductParams(String productId, String params);


    /**
     * Lấy số lượng sản phẩm thuộc một loại sản phẩm nhất định
     *
     * @param categoryId ID của loại sản phẩm
     * @return Số lượng sản phẩm
     */
    long getProductCountByCategory(@NotNull String categoryId);
}
