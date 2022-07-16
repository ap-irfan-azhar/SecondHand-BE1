package id.binaracademy.secondhand.controller;

import id.binaracademy.secondhand.entity.Category;
import id.binaracademy.secondhand.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/")
    public List<Category> findAllCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping("/search")
    public List<Category> searchCategory(@RequestParam String name) {
        return categoryService.findAllCategories(name);
    }

    @GetMapping("/{id}")
    public Category findCategoryById(@PathVariable Long id) {
        return categoryService.findCategoryById(id);
    }

    @PostMapping("/")
    public Category saveCategory(@RequestParam String categoryName) {
        return categoryService.saveCategory(categoryName);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestParam String categoryName) {
        return categoryService.updateCategory(id, categoryName);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }


}
