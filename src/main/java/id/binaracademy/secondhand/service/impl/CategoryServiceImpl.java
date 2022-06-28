package id.binaracademy.secondhand.service.impl;

import id.binaracademy.secondhand.entity.Category;
import id.binaracademy.secondhand.repository.CategoryRepository;
import id.binaracademy.secondhand.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(String categoryName) {
        LocalDate now = LocalDate.now();

        Category category = new Category();
        category.setName(categoryName);
        category.setCreatedAt(now);
        category.setUpdatedAt(now);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllCategories(String name) {
        List<Category> categories = categoryRepository.findAll();
        List<Category> outputCategories = new ArrayList<>();
        for (Category category: categories) {
            if (category.getName().contains(name)) {
                outputCategories.add(category);
            }
        }
        return outputCategories;
    }

    @Override
    public Category findCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()){
            throw new IllegalArgumentException(
                    String.format("Category with id: %s not found", id.toString())
            );
        }
        return category.get();
    }

    @Override
    public Category updateCategory(Long id, String categoryName) {
        Category existingCategory = findCategoryById(id);
        existingCategory.setName(categoryName);
        existingCategory.setUpdatedAt(LocalDate.now());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if(!existingCategory.isPresent()) {
            throw new IllegalArgumentException(
                    String.format(
                            "Category with id: %s not found", id.toString()
                    )
            );
        }
        categoryRepository.delete(existingCategory.get());
    }
}
