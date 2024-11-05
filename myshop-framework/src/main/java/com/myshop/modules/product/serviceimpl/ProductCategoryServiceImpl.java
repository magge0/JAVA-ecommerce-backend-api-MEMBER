package com.myshop.modules.product.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.cache.Cache;
import com.myshop.cache.CachePrefix;
import com.myshop.modules.product.entity.dos.ProductCategory;
import com.myshop.modules.product.entity.vos.ProductCategoryVO;
import com.myshop.modules.product.mapper.CategoryMapper;
import com.myshop.modules.product.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"PRODUCT_CATEGORY"})
public class ProductCategoryServiceImpl extends ServiceImpl<CategoryMapper, ProductCategory> implements ProductCategoryService {

    private static final String DELETED_FLAG = "delete_flag";

    @Autowired
    private Cache cache;


    @Override
    public List<ProductCategoryVO> listSubCategories(String parentId) {
        if ("0".equals(parentId)) {
            return getCategoryTree();
        }
        // Duyệt qua danh sách phân loại, tìm phân loại có ID phù hợp với parentId, sau đó trả về danh sách con của nó
        List<ProductCategoryVO> topCatList = getCategoryTree();
        for (ProductCategoryVO item : topCatList) {
            if (item.getId().equals(parentId)) {
                return item.getChildCategories();
            } else {
                return getChildren(parentId, item.getChildCategories());
            }
        }
        return new ArrayList<>();
    }

    /**
     * Đệ quy tìm danh mục con có ID bằng parentId, và trả về danh sách các danh mục con đó.
     *
     * @param parentId        ID của danh mục cha
     * @param categoryTreeVos Danh sách các đối tượng ProductCategoryVO
     * @return Danh sách các đối tượng ProductCategoryVO con
     */
    private List<ProductCategoryVO> getChildren(String parentId, List<ProductCategoryVO> categoryTreeVos) {
        for (ProductCategoryVO item : categoryTreeVos) {
            // Kiểm tra xem ID của danh mục hiện tại có bằng parentId không
            if (item.getId().equals(parentId)) {
                // Nếu có, trả về danh sách các danh mục con của danh mục hiện tại
                return item.getChildCategories();
            }
            // Kiểm tra xem danh mục hiện tại có danh mục con không
            if (item.getChildCategories() != null && !item.getChildCategories().isEmpty()) {
                // Nếu có, đệ quy tìm danh mục con trong danh sách các danh mục con
                return getChildren(parentId, item.getChildCategories());
            }
        }
        // Nếu không tìm thấy danh mục con, trả về danh sách ban đầu
        return categoryTreeVos;
    }

    /**
     * Lấy tên danh mục theo danh sách ID danh mục đã cho
     *
     * @param ids Danh sách ID danh mục
     * @return Danh sách tên danh mục
     */
    @Override
    public List<String> getProductCategoryNameByIds(List<String> ids) {
        List<String> productCategoryName = new ArrayList<>();
        List<ProductCategory> productCategoryVOList = (List<ProductCategory>) cache.get(CachePrefix.CATEGORY_ARRAY.getPrefix());
        // Nếu bộ nhớ cache trống, lấy lại bộ nhớ cache
        if (productCategoryVOList == null) {
            getCategoryTree();
            productCategoryVOList = (List<ProductCategory>) cache.get(CachePrefix.CATEGORY_ARRAY.getPrefix());
        }
        // Nếu vẫn trống, trả về danh sách rỗng
        if (productCategoryVOList == null) {
            return new ArrayList<>();
        }
        // Duyệt danh sách danh mục cấp cao nhất
        for (ProductCategory productCategory : productCategoryVOList) {
            // Duyệt danh sách ID cần tìm để so sánh
            for (String id : ids) {
                if (productCategory.getId().equals(id)) {
                    // Thêm tên danh mục vào danh sách
                    productCategoryName.add(productCategory.getName());
                }
            }
        }
        return productCategoryName;
    }


    @Override
    public List<ProductCategoryVO> getCategoryTree() {
        // Kiểm tra xem cây danh mục đã có trong bộ nhớ cache chưa
        List<ProductCategoryVO> cachedCategories = (List<ProductCategoryVO>) cache.get(CachePrefix.PRODUCT_CATEGORY.getPrefix());
        if (cachedCategories != null) {
            // Nếu có trong bộ nhớ cache, trả về trực tiếp
            return cachedCategories;
        }

        // Lấy tất cả các danh mục
        LambdaQueryWrapper<ProductCategory> categoryQuery = new LambdaQueryWrapper<>();
        categoryQuery.eq(ProductCategory::getDeleteFlag, false); // Lọc các danh mục không bị xóa
        List<ProductCategory> productCategoryItem = this.list(categoryQuery);

        // Xây dựng cây danh mục
        cachedCategories = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryItem) {
            // Nếu danh mục là danh mục gốc (parentId = "0")
            if ("0".equals(productCategory.getParentId())) {
                // Tạo đối tượng ProductCategoryVO tương ứng
                ProductCategoryVO categoryVO = new ProductCategoryVO(productCategory);
                // Tìm danh mục con của danh mục hiện tại
                categoryVO.setChildCategories(findChildren(productCategoryItem, categoryVO));
                // Thêm danh mục vào danh sách
                cachedCategories.add(categoryVO);
            }
        }

        // Sắp xếp danh sách theo thứ tự
        cachedCategories.sort(Comparator.comparing(ProductCategory::getSortOrder));

        // Lưu cây danh mục vào bộ nhớ cache nếu danh sách không rỗng
        if (!cachedCategories.isEmpty()) {
            cache.put(CachePrefix.PRODUCT_CATEGORY.getPrefix(), cachedCategories);
            cache.put(CachePrefix.CATEGORY_ARRAY.getPrefix(), productCategoryItem);
        }

        // Trả về danh sách cây danh mục
        return cachedCategories;
    }


    /**
     * Xây dựng cây danh mục VO (ProductCategoryVO) đệ quy.
     *
     * @param buildProductCategoryTree Danh sách các đối tượng ProductCategory (danh mục)
     * @param findChildren             Đối tượng ProductCategoryVO (danh mục) hiện tại
     * @return Danh sách các đối tượng ProductCategoryVO (danh mục) con
     */
    private List<ProductCategoryVO> findChildren(List<ProductCategory> buildProductCategoryTree, ProductCategoryVO findChildren) {
        List<ProductCategoryVO> foundChildren = new ArrayList<>();
        buildProductCategoryTree.forEach(item -> {
            // Kiểm tra xem ID của danh mục hiện tại có bằng ID của danh mục cha (`categoryVO`) không
            if (item.getParentId().equals(findChildren.getId())) {
                // Nếu có, tạo một đối tượng ProductCategoryVO tạm thời từ danh mục hiện tại
                ProductCategoryVO temp = new ProductCategoryVO(item);
                // Đệ quy gọi `findChildren` để tìm các danh mục con của danh mục tạm thời
                temp.setChildCategories(findChildren(buildProductCategoryTree, temp));
                // Thêm danh mục tạm thời vào danh sách các danh mục con
                foundChildren.add(temp);
            }
        });

        // Trả về danh sách các danh mục con đã tìm được
        return foundChildren;
    }


}
