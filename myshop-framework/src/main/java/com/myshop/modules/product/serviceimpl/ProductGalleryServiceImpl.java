package com.myshop.modules.product.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.modules.product.entity.dos.ProductGallery;
import com.myshop.modules.product.mapper.ProductGalleryMapper;
import com.myshop.modules.product.service.ProductGalleryService;
import com.myshop.modules.system.entity.dos.Setting;
import com.myshop.modules.system.entity.dto.ProductSetting;
import com.myshop.modules.system.entity.enums.SettingEnum;
import com.myshop.modules.system.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductGalleryServiceImpl extends ServiceImpl<ProductGalleryMapper, ProductGallery> implements ProductGalleryService {

    @Autowired
    private SettingService settingService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(List<String> productGalleryList, String goodsId) {
        // Xóa thông tin album ảnh sản phẩm cũ
        this.baseMapper.delete(new QueryWrapper<ProductGallery>().eq("product_id", goodsId));
        // Xử lý sau khi xác định trình chọn ảnh
        int i = 0;
        for (String origin : productGalleryList) {
            // Lấy album ảnh có tất cả ảnh thu nhỏ
            ProductGallery galley = this.getProductGallery(origin);
            galley.setProductId(goodsId);
            // Ảnh đầu tiên là ảnh mặc định
            galley.setIsDefault(i == 0 ? 1 : 0);
            i++;
            this.baseMapper.insert(galley);
        }
    }

    @Override
    public List<ProductGallery> productGalleryList(String productId) {
        return this.baseMapper.selectList(new QueryWrapper<ProductGallery>().eq("productId", productId));
    }

    @Override
    public ProductGallery getProductGallery(String origin) {
        ProductGallery goodsGallery = new ProductGallery();
        // Lấy cấu hình hệ thống sản phẩm để quyết định có cần phê duyệt hay không
        Setting setting = settingService.get(SettingEnum.PRODUCT_SETTING.name());
        ProductSetting productSetting = JSONUtil.toBean(setting.getSettingValue(), ProductSetting.class);
        // Ảnh thu nhỏ
        String thumbnail = this.getUrl(origin, productSetting.getThumbnailImageWidth(), productSetting.getThumbnailImageHeight());
        // Ảnh nhỏ
        String small = this.getUrl(origin, productSetting.getSmallImageWidth(), productSetting.getSmallImageHeight());
        // Gán giá trị
        goodsGallery.setSmall(small);
        goodsGallery.setThumbnail(thumbnail);
        goodsGallery.setOriginal(origin);
        return goodsGallery;
    }


    /**
     * Tạo ảnh có kích thước đã định từ ảnh gốc
     *
     * @param url    Liên kết
     * @param width  Chiều rộng
     * @param height Chiều cao
     * @return
     */
    private String getUrl(String url, Integer width, Integer height) {
        Setting setting = settingService.get(SettingEnum.OSS_SETTING.name());
        //TODO: trien khai with nhieu oss
        return url;
    }
}
