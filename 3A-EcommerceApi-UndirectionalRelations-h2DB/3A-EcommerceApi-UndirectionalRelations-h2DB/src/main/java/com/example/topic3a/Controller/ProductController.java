package com.example.topic3a.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.topic3a.Entity.Category;
import com.example.topic3a.Entity.Product;
import com.example.topic3a.Repository.CategoryRepository;
import com.example.topic3a.Repository.ProductRepository;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public Product createProduct(@RequestBody ProductRequest request) {
        Product product = new Product(
            request.getName(),
            request.getDescription(),
            request.getPrice(),
            request.getStockQuantity()
        );
        
        // Add categories to product (unidirectional - product knows categories)
        if (request.getCategoryIds() != null) {
            for (Long categoryId : request.getCategoryIds()) {
                Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
                product.addCategory(category);
            }
        }
        
        return productRepository.save(product);
    }
    
    // GET all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    // GET product by ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }
    
    // GET products by price range (derived query)
    @GetMapping("/price")
    public List<Product> getProductsByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        return productRepository.findByPriceBetween(min, max);
    }
    
    // GET products by category (derived query - works despite unidirectional!)
    @GetMapping("/category/{categoryName}")
    public List<Product> getProductsByCategory(@PathVariable String categoryName) {
        return productRepository.findByCategoriesName(categoryName);
    }
    
    // GET products by multiple categories (JPQL)
    @GetMapping("/categories")
    public List<Product> getProductsByCategories(@RequestParam List<Long> categoryIds) {
        return productRepository.findProductsByCategoryIds(categoryIds);
    }
    
    // GET products with no categories
    @GetMapping("/uncategorized")
    public List<Product> getUncategorizedProducts() {
        return productRepository.findProductsWithNoCategories();
    }
    
    // GET search products (JPQL)
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        return productRepository.searchProducts(keyword);
    }
    
    // PUT reduce stock (when item is purchased)
    @PutMapping("/{id}/reduce-stock")
    public String reduceStock(@PathVariable Long id, @RequestParam Integer quantity) {
        int updated = productRepository.reduceStock(id, quantity);
        return updated > 0 ? "Stock reduced" : "Insufficient stock or product not found";
    }
    
    // DELETE product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "Product deleted: " + id;
    }
    
    // Inner class for request
    static class ProductRequest {
        private String name;
        private String description;
        private Double price;
        private Integer stockQuantity;
        private List<Long> categoryIds;
        
        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
        public Integer getStockQuantity() { return stockQuantity; }
        public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
        public List<Long> getCategoryIds() { return categoryIds; }
        public void setCategoryIds(List<Long> categoryIds) { this.categoryIds = categoryIds; }
    }
}