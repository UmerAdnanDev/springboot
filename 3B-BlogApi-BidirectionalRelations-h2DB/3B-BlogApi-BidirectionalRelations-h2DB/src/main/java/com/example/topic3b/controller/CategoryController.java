package com.example.topic3b.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.topic3b.entity.Category;
import com.example.topic3b.repository.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }
    
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    }
    
    // NEW in Phase 2B - Get category with all its posts
    @GetMapping("/{id}/with-posts")
    public Category getCategoryWithPosts(@PathVariable Long id) {
        return categoryRepository.findCategoryWithPosts(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    }
    
    // NEW in Phase 2B - Get category statistics
    @GetMapping("/stats/post-counts")
    public List<Object[]> getCategoryPostCounts() {
        return categoryRepository.getCategoryPostCounts();
    }
    
    // NEW in Phase 2B - Get popular categories
    @GetMapping("/popular/{minPosts}")
    public List<Category> getPopularCategories(@PathVariable Integer minPosts) {
        return categoryRepository.findPopularCategories(minPosts);
    }
    
    @GetMapping("/name/{name}")
    public Category getCategoryByName(@PathVariable String name) {
        return categoryRepository.findByName(name)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    }
    
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "Category deleted: " + id;
    }
}