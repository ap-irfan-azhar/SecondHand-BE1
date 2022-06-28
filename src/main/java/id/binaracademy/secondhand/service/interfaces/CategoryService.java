package id.binaracademy.secondhand.service.interfaces;

import id.binaracademy.secondhand.entity.Category;

import java.util.List;

public interface CategoryService {
    Category saveCategory(String categoryName);
    List<Category> findAllCategories();
    List<Category> findAllCategories(String name);
    Category findCategoryById(Long id);
    Category updateCategory(Long id, String categoryName);
    void deleteCategory(Long id);
}
