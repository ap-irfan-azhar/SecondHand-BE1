package id.binaracademy.secondhand.controller;

import id.binaracademy.secondhand.entity.Category;
import id.binaracademy.secondhand.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> findAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortType
    ) {
        Page<Category> pageUser = categoryService.findAllCategories(page, size, sortBy, sortType);
        if (pageUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("categories", pageUser.getContent());
        response.put("currentPage", pageUser.getNumber());
        response.put("totalItem", pageUser.getTotalElements());
        response.put("totalPage", pageUser.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
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
