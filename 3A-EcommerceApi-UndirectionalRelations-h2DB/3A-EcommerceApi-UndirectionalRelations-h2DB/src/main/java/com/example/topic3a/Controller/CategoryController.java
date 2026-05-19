package com.example.topic3a.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.topic3a.Entity.Category;
import com.example.topic3a.Repository.CategoryRepository;
import java.util.List;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    // CREATE category
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }
    
    // GET all categories
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    // GET category by ID
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found: " + id));
    }
    
    // GET category by name (derived query)
    @GetMapping("/name/{name}")
    public Category getCategoryByName(@PathVariable String name) {
        return categoryRepository.findByName(name)
            .orElseThrow(() -> new RuntimeException("Category not found: " + name));
    }
    // DELETE category
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "Category deleted: " + id;
    }
}