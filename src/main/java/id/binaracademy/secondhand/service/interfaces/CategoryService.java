package id.binaracademy.secondhand.service.interfaces;

import id.binaracademy.secondhand.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Category saveCategory(String categoryName);
    Page<Category> findAllCategories(int page, int size, String sortBy, String sortType);
    List<Category> findAllCategories(String name);
    Category findCategoryById(Long id);
    Category updateCategory(Long id, String categoryName);
    void deleteCategory(Long id);
}
