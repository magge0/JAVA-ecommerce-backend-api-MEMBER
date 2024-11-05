package com.myshop.modules.product.serviceimpl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.cache.Cache;
import com.myshop.cache.CachePrefix;
import com.myshop.common.enums.ResultCode;
import com.myshop.common.exception.ServiceException;
import com.myshop.common.security.AuthUser;
import com.myshop.common.security.context.UserContext;
import com.myshop.common.security.enums.UserEnums;
import com.myshop.modules.product.entity.dos.Product;
import com.myshop.modules.product.entity.dos.ProductCategory;
import com.myshop.modules.product.entity.dos.ProductGallery;
import com.myshop.modules.product.entity.dos.ProductSearchParams;
import com.myshop.modules.product.entity.dto.ProductOperationDTO;
import com.myshop.modules.product.entity.dto.ProductParamsDTO;
import com.myshop.modules.product.entity.enums.ProductAuthEnum;
import com.myshop.modules.product.entity.enums.ProductStatusEnum;
import com.myshop.modules.product.entity.vos.ProductSkuVO;
import com.myshop.modules.product.entity.vos.ProductVO;
import com.myshop.modules.product.mapper.ProductMapper;
import com.myshop.modules.product.service.ProductCategoryService;
import com.myshop.modules.product.service.ProductGalleryService;
import com.myshop.modules.product.service.ProductService;
import com.myshop.modules.product.service.ProductSkuService;
import com.myshop.modules.system.entity.dos.Setting;
import com.myshop.modules.system.entity.dto.ProductSetting;
import com.myshop.modules.system.entity.enums.SettingEnum;
import com.myshop.modules.system.service.SettingService;
import com.myshop.orm.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private SettingService settingService;

    @Autowired
    private ProductGalleryService productGalleryService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private Cache<ProductVO> cache;

    @Autowired
    private ProductSkuService productSkuService;


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

    @Override
    public IPage<Product> getByParams(ProductSearchParams productSearchParams) {
        return this.page(PageUtil.buildPage(productSearchParams), productSearchParams.queryWrapper());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    // TODO: sử dụng annotation để ghi log hành động update sản phẩm trong hệ thống.
    public Boolean updateProductMarketAble(List<String> productIds, ProductStatusEnum productStatusEnum, String underReason) {
        boolean operationResult;

        //Nếu sản phẩm rỗng, trả về trực tiếp
        if (productIds == null || productIds.isEmpty()) {
            return true;
        }

        LambdaUpdateWrapper<Product> updateWrapper = this.buildUpdateWrapperForStoreAuthority();
        updateWrapper.set(Product::getDisplayStatus, productStatusEnum.name());
        updateWrapper.set(Product::getHiddenReason, underReason);
        updateWrapper.in(Product::getId, productIds);
        operationResult = this.update(updateWrapper);

        //Sửa đổi sản phẩm quy cách
        LambdaQueryWrapper<Product> queryWrapper = this.buildQueryWrapperByStoreAuthority();
        queryWrapper.in(Product::getId, productIds);
        List<Product> productList = this.list(queryWrapper);
        this.updateProductStatus(productIds, productStatusEnum, productList);
        return operationResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // TODO: sử dụng annotation để ghi log hành động update sản phẩm trong hệ thống.
    public Boolean managerUpdateProductMarketAble(List<String> productIds, ProductStatusEnum productStatusEnum, String underReason) {
        boolean operationResult;

        // Nếu danh sách ID sản phẩm rỗng, trả về true
        if (productIds == null || productIds.isEmpty()) {
            return true;
        }

        // Kiểm tra quyền quản trị viên
        this.verifyManagerAccess();

        // Tạo đối tượng LambdaUpdateWrapper để cập nhật dữ liệu
        LambdaUpdateWrapper<Product> updateWrapper = new LambdaUpdateWrapper<>();
        // Cập nhật trường MarketEnable (trạng thái lên/xuống kệ)
        updateWrapper.set(Product::getDisplayStatus, productStatusEnum.name());
        // Cập nhật trường UnderMessage (lý do xuống kệ)
        updateWrapper.set(Product::getHiddenReason, underReason);
        // Lọc sản phẩm theo danh sách ID
        updateWrapper.in(Product::getId, productIds);
        // Thực hiện cập nhật dữ liệu
        operationResult = this.update(updateWrapper);

        // Cập nhật trạng thái của các sản phẩm thuộc loại SKU
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Product::getId, productIds); // Lọc sản phẩm theo danh sách ID
        List<Product> productList = this.list(queryWrapper); // Lấy danh sách sản phẩm
        this.updateProductStatus(productIds, productStatusEnum, productList); // Cập nhật trạng thái của các sản phẩm thuộc loại SKU

        return operationResult;
    }


    @Override
    public ProductVO getProductVO(String productId) {
        // Lấy từ bộ nhớ cache, nếu không có thì đọc từ cache
        ProductVO productVO = cache.get(CachePrefix.PRODUCT.getPrefix() + productId);
        if (productVO != null) {
            return productVO;
        }

        // Tìm kiếm thông tin sản phẩm
        Product product = this.getById(productId);
        if (product == null) {
            log.error("Sản phẩm có ID là " + productId + " không tồn tại");
            throw new ServiceException(ResultCode.PRODUCT_NOT_EXIST);
        }

        productVO = new ProductVO();
        // Gán giá trị
        BeanUtils.copyProperties(product, productVO);
        // ID sản phẩm
        productVO.setId(product.getId());
        // Gán giá trị cho album sản phẩm
        List<String> images = new ArrayList<>();
        List<ProductGallery> galleryList = productGalleryService.productGalleryList(productId);
        for (ProductGallery productGallery : galleryList) {
            images.add(productGallery.getOriginal());
        }
        productVO.setProductGalleryList(images);

        // Gán giá trị cho SKU sản phẩm
        List<ProductSkuVO> productListByProductId = productSkuService.getProductListByProductId(productId);
        if (productListByProductId != null && !productListByProductId.isEmpty()) {
            productVO.setSkuList(productListByProductId);
        }

        // Gán giá trị cho tên danh mục sản phẩm
        List<String> categoryName = new ArrayList<>();
        String categoryPath = product.getCategoryPath();
        String[] strArray = categoryPath.split(",");
        List<ProductCategory> categories = productCategoryService.listByIds(Arrays.asList(strArray));
        for (ProductCategory productCategory : categories) {
            categoryName.add(productCategory.getName());
        }
        productVO.setCategoryName(categoryName);

        // Điền thông số nếu không rỗng
        if (CharSequenceUtil.isNotEmpty(product.getParams())) {
            productVO.setProductParamsDTOList(JSONUtil.toList(product.getParams(), ProductParamsDTO.class));
        }

        //TODO: set whosale list


        cache.put(CachePrefix.PRODUCT.getPrefix() + productId, productVO);
        return productVO;
    }

    @Override
    public List<Product> getByBrandIds(List<String> brandIds) {
        LambdaQueryWrapper<Product> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Product::getBrandId, brandIds);
        return list(lambdaQueryWrapper);
    }

    /**
     * Cập nhật thông tin tham số sản phẩm
     *
     * @param productId ID của sản phẩm
     * @param params    Thông tin tham số sản phẩm
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProductParams(String productId, String params) {
        // Tạo đối tượng LambdaUpdateWrapper để cập nhật thông tin sản phẩm
        LambdaUpdateWrapper<Product> updateProductWrapper = new LambdaUpdateWrapper<>();
        // Thiết lập điều kiện ID của sản phẩm cần cập nhật
        updateProductWrapper.eq(Product::getId, productId);
        // Thiết lập giá trị mới cho thông tin tham số sản phẩm
        updateProductWrapper.set(Product::getParams, params);
        // Thực hiện cập nhật thông tin sản phẩm
        this.update(updateProductWrapper);
    }

    /**
     * Kiểm tra quyền quản trị viên của cửa hàng hiện tại đang đăng nhập
     *
     * @return Thông tin người dùng đang đăng nhập
     */
    private AuthUser verifyManagerAccess() {
        AuthUser currentAuthUser = UserContext.getCurrentUser();
        // Nếu người dùng hiện tại không rỗng và có vai trò quản trị viên
        if (currentAuthUser != null && (currentAuthUser.getRole().equals(UserEnums.MANAGER))) {
            return currentAuthUser;
        } else {
            throw new ServiceException(ResultCode.USER_AUTH_ERROR);
        }
    }

    /**
     * Cập nhật trạng thái sản phẩm
     *
     * @param productIds        ID sản phẩm
     * @param productStatusEnum Trạng thái sản phẩm
     * @param productList       Danh sách sản phẩm
     */
    private void updateProductStatus(List<String> productIds, ProductStatusEnum productStatusEnum, List<Product> productList) {
        List<String> productCacheKeys = new ArrayList<>();
        for (Product product : productList) {
            productCacheKeys.add(CachePrefix.PRODUCT.getPrefix() + product.getId());
            productSkuService.updateProductSkuStatus(product);
        }
        cache.batchDelete(productCacheKeys);

        if (ProductStatusEnum.DOWN.equals(productStatusEnum)) {
            //  TODO: xoá san pham tren ES theo productIds
        } else {
            // TODO: update san pham tren ES the productIds
        }

        //Gửi tin nhắn xuống kệ sản phẩm
        if (productStatusEnum.equals(ProductStatusEnum.DOWN)) {
            //TODO: trien khai MQ
        }
    }

    /**
     * Lấy QueryWrapper (Kiểm tra quyền hạn của người dùng)
     *
     * @return queryWrapper
     */
    private LambdaQueryWrapper<Product> buildQueryWrapperByStoreAuthority() {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        AuthUser authUser = this.getCurrentStoreUser();
        if (authUser != null) {
            queryWrapper.eq(Product::getStoreId, authUser.getStoreId());
        }
        return queryWrapper;
    }

    /**
     * Lấy UpdateWrapper (Kiểm tra quyền hạn của người dùng)
     *
     * @return updateWrapper
     */
    private LambdaUpdateWrapper<Product> buildUpdateWrapperForStoreAuthority() {
        LambdaUpdateWrapper<Product> updateWrapper = new LambdaUpdateWrapper<>();
        AuthUser authUser = this.getCurrentStoreUser();
        if (authUser != null) {
            updateWrapper.eq(Product::getStoreId, authUser.getStoreId());
        }
        return updateWrapper;
    }

    /**
     * Kiểm tra cửa hàng hiện tại đang đăng nhập
     *
     * @return Cửa hàng hiện tại đang đăng nhập
     */
    private AuthUser getCurrentStoreUser() {
        AuthUser currentAuthUser = UserContext.getCurrentUser();
        //Nếu thành viên hiện tại không rỗng và là vai trò cửa hàng
        if (currentAuthUser != null && (currentAuthUser.getRole().equals(UserEnums.STORE) && currentAuthUser.getStoreId() != null)) {
            return currentAuthUser;
        }
        return null;
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
