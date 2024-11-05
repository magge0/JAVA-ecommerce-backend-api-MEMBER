package com.myshop.modules.product.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myshop.cache.Cache;
import com.myshop.cache.CachePrefix;
import com.myshop.modules.product.entity.dos.Category;
import com.myshop.modules.product.entity.vos.CategoryVO;
import com.myshop.modules.product.mapper.CategoryMapper;
import com.myshop.modules.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"CATEGORY_DETAILS"})
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private static final String DELETED_FLAG = "delete_flag";

    @Autowired
    private Cache cache;


    @Override
    public List<CategoryVO> listSubCategories(String parentId) {
        if ("0".equals(parentId)) {
            return getCategoryTree();
        }
        // Duyệt qua danh sách phân loại, tìm phân loại có ID phù hợp với parentId, sau đó trả về danh sách con của nó
        List<CategoryVO> topCatList = getCategoryTree();
        for (CategoryVO item : topCatList) {
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
     * @param categoryTreeVos Danh sách các đối tượng CategoryVO
     * @return Danh sách các đối tượng CategoryVO con
     */
    private List<CategoryVO> getChildren(String parentId, List<CategoryVO> categoryTreeVos) {
        for (CategoryVO item : categoryTreeVos) {
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


    @Override
    public List<CategoryVO> getCategoryTree() {
        // Kiểm tra xem cây danh mục đã có trong bộ nhớ cache chưa
        List<CategoryVO> cachedCategories = (List<CategoryVO>) cache.get(CachePrefix.CATEGORY_DETAILS.getPrefix());
        if (cachedCategories != null) {
            // Nếu có trong bộ nhớ cache, trả về trực tiếp
            return cachedCategories;
        }

        // Lấy tất cả các danh mục
        LambdaQueryWrapper<Category> categoryQuery = new LambdaQueryWrapper<>();
        categoryQuery.eq(Category::getDeleteFlag, false); // Lọc các danh mục không bị xóa
        List<Category> categoryItem = this.list(categoryQuery);

        // Xây dựng cây danh mục
        cachedCategories = new ArrayList<>();
        for (Category category : categoryItem) {
            // Nếu danh mục là danh mục gốc (parentId = "0")
            if ("0".equals(category.getParentId())) {
                // Tạo đối tượng CategoryVO tương ứng
                CategoryVO categoryVO = new CategoryVO(category);
                // Tìm danh mục con của danh mục hiện tại
                categoryVO.setChildCategories(findChildren(categoryItem, categoryVO));
                // Thêm danh mục vào danh sách
                cachedCategories.add(categoryVO);
            }
        }

        // Sắp xếp danh sách theo thứ tự
        cachedCategories.sort(Comparator.comparing(Category::getSortOrder));

        // Lưu cây danh mục vào bộ nhớ cache nếu danh sách không rỗng
        if (!cachedCategories.isEmpty()) {
            cache.put(CachePrefix.CATEGORY_DETAILS.getPrefix(), cachedCategories);
            cache.put(CachePrefix.CATEGORY_ARRAY.getPrefix(), categoryItem);
        }

        // Trả về danh sách cây danh mục
        return cachedCategories;
    }

    /**
     * Xây dựng cây danh mục VO (CategoryVO) đệ quy.
     *
     * @param buildCategoryTree Danh sách các đối tượng Category (danh mục)
     * @param findChildren      Đối tượng CategoryVO (danh mục) hiện tại
     * @return Danh sách các đối tượng CategoryVO (danh mục) con
     */
    private List<CategoryVO> findChildren(List<Category> buildCategoryTree, CategoryVO findChildren) {
        List<CategoryVO> foundChildren = new ArrayList<>();
        buildCategoryTree.forEach(item -> {
            // Kiểm tra xem ID của danh mục hiện tại có bằng ID của danh mục cha (`categoryVO`) không
            if (item.getParentId().equals(findChildren.getId())) {
                // Nếu có, tạo một đối tượng CategoryVO tạm thời từ danh mục hiện tại
                CategoryVO temp = new CategoryVO(item);
                // Đệ quy gọi `findChildren` để tìm các danh mục con của danh mục tạm thời
                temp.setChildCategories(findChildren(buildCategoryTree, temp));
                // Thêm danh mục tạm thời vào danh sách các danh mục con
                foundChildren.add(temp);
            }
        });

        // Trả về danh sách các danh mục con đã tìm được
        return foundChildren;
    }


}
