package com.example.topic3a.Entity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
 PRODUCT entity - The "owner" side
 - UNIDIRECTIONAL: Product knows about Categories, but Categories do NOT know about Products
 - You usually search products by category, not all products in a category (performance)
 */
@Entity
@Table(name = "product")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    
    /**
     MANY-TO-MANY UNIDIRECTIONAL
     - ManyToMany: A product can belong to many categories, a category can have many products
     - JoinTable: Creates a separate table to manage the relationship
        - name: Name of the join table
        - joinColumns: Column in join table pointing to THIS entity (Product)
        - inverseJoinColumns: Column in join table pointing to the OTHER entity (Category)
     
     NAVIGATION: Product → Categories (YES), Categories → Products (NO)
     - CRITICAL: There's NO "mappedBy" attribute because Category doesn't have the reverse reference
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();
    
    // Constructors
    public Product() {}
    
    public Product(String name, String description, Double price, Integer stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    // Helper method to add category
    public void addCategory(Category category) {
        categories.add(category);
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    
    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }
}