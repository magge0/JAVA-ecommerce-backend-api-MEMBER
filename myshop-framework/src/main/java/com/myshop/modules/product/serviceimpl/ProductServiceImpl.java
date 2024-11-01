package com.myshop.modules.product.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.common.enums.ResultCode;
import com.myshop.common.exception.ServiceException;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductGallery;
import com.myshop.modules.product.entity.dto.ProductOperationDTO;
import com.myshop.modules.product.entity.enums.ProductAuthEnum;
import com.myshop.modules.product.mapper.ProductMapper;
import com.myshop.modules.product.service.ProductGalleryService;
import com.myshop.modules.product.service.ProductService;
import com.myshop.modules.system.entity.dos.Setting;
import com.myshop.modules.system.entity.dto.ProductSetting;
import com.myshop.modules.system.entity.enums.SettingEnum;
import com.myshop.modules.system.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private SettingService settingService;

    @Autowired
    private ProductGalleryService productGalleryService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    // TODO: sử dụng annotation để ghi log hành động thêm sản phẩm trong hệ thống.
    public void addProduct(ProductOperationDTO productOperationDTO) {
        Product product = new Product(productOperationDTO);
        // Kiểm tra sản phẩm
        this.checkProduct(product);
        // Thêm hình ảnh vào sản phẩm
        if (productOperationDTO.getProductGalleryList().size() > 0) {
            this.setProductGalleryParam(productOperationDTO.getProductGalleryList().get(0), product);
        }
        // Thêm thông tin sản phẩm
        if (productOperationDTO.getProductParamsDTOList() != null && !productOperationDTO.getProductParamsDTOList().isEmpty()) {
            // Điền thông tin vào sản phẩm
            product.setParams(JSONUtil.toJsonStr(productOperationDTO.getProductParamsDTOList()));
        }
        // Thêm sản phẩm
        this.save(product);
        //TODO: Thêm thông tin SKU cho sản phẩm

        //TODO:  Thêm album ảnh
        if (productOperationDTO.getProductGalleryList() != null && !productOperationDTO.getProductGalleryList().isEmpty()) {
            this.productGalleryService.add(productOperationDTO.getProductGalleryList(), product.getId());
        }

        //TODO: tạo chỉ mục sản phẩm ES
    }

    /**
     * Kiểm tra thông tin sản phẩm
     * Nếu sản phẩm là sản phẩm ảo thì không cần cấu hình mẫu vận chuyển
     * Nếu sản phẩm là sản phẩm thực tế thì cần cấu hình mẫu vận chuyển
     * Kiểm tra xem sản phẩm có tồn tại hay không
     * Kiểm tra xem sản phẩm có cần phê duyệt hay không
     * Kiểm tra xem người dùng hiện tại có phải là cửa hàng hay không
     *
     * @param product Sản phẩm
     */
    private void checkProduct(Product product) {
        // Kiểm tra loại sản phẩm
        switch (product.getProductType()) {
            case "PHYSICAL_PRODUCT":
                if ("0".equals(product.getTemplateId())) {
                    throw new ServiceException(ResultCode.PHYSICAL_PRODUCT_NEED_TEMP);
                }
                break;
            case "VIRTUAL_PRODUCT":
                if (!"0".equals(product.getTemplateId())) {
                    product.setTemplateId("0");
                }
                break;
            default:
                throw new ServiceException(ResultCode.PRODUCT_TYPE_ERROR);
        }
        // Kiểm tra xem sản phẩm có tồn tại hay không -- được sử dụng khi sửa sản phẩm
        if (product.getId() != null) {
            this.checkExist(product.getId());
        } else {
            // Số lượng bình luận
            product.setCommentNum(0);
            // Số lần mua
            product.setBuyCount(0);
            // Số lượng
            product.setQuantity(0);
            // Xếp hạng sản phẩm
            product.setGrade(100.0);
        }

        // Lấy cấu hình hệ thống sản phẩm để quyết định có cần phê duyệt hay không
        Setting setting = settingService.get(SettingEnum.PRODUCT_SETTING.name());
        ProductSetting goodsSetting = JSONUtil.toBean(setting.getSettingValue(), ProductSetting.class);
        // Có cần phê duyệt hay không
        product.setAuthMessage(Boolean.TRUE.equals(goodsSetting.getProductCheck()) ? ProductAuthEnum.PENDING.name() : ProductAuthEnum.PASS.name());
        // TODO: Kiểm tra xem người dùng hiện tại có phải là cửa hàng hay không

    }


    /**
     * Kiểm tra xem sản phẩm có tồn tại hay không
     *
     * @param productId ID của sản phẩm
     * @return Thông tin sản phẩm
     */
    private Product checkExist(String productId) {
        Product product = getById(productId);
        if (product == null) {
            log.error("Sản phẩm có ID là " + productId + " không tồn tại");
            throw new ServiceException(ResultCode.PRODUCT_NOT_EXIST);
        }
        return product;
    }

    /**
     * Thêm ảnh mặc định cho sản phẩm
     *
     * @param origin  Ảnh
     * @param product Sản phẩm
     */
    private void setProductGalleryParam(String origin, Product product) {
        ProductGallery productGallery = productGalleryService.getProductGallery(origin);
        product.setOriginal(productGallery.getOriginal());
        product.setSmall(productGallery.getSmall());
        product.setThumbnail(productGallery.getThumbnail());
    }
}
