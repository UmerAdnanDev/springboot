package com.example.topic3a.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.topic3a.Entity.Product;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    //DERIVED QUERIES
    
    // Find by name containing (search)
    List<Product> findByNameContaining(String keyword);
    
    // Find by price between
    List<Product> findByPriceBetween(Double min, Double max);
    
    // Find by stock quantity less than (low stock alert)
    List<Product> findByStockQuantityLessThan(Integer threshold);
    
    // Find by category name (goes through the unidirectional relationship)
    // Despite being unidirectional, we can still query from product side!
    List<Product> findByCategoriesName(String categoryName);
    
    // Order by price
    List<Product> findAllByOrderByPriceDesc();
    
    //JPQL QUERIES
    
    // Find products in multiple categories
    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories c WHERE c.id IN :categoryIds")
    List<Product> findProductsByCategoryIds(@Param("categoryIds") List<Long> categoryIds);
    
    // Find products with no categories assigned
    @Query("SELECT p FROM Product p WHERE p.categories IS EMPTY")
    List<Product> findProductsWithNoCategories();
    
    // Search products by name or description
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);
    // Update stock after purchase
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.stockQuantity = p.stockQuantity - :quantity WHERE p.id = :productId AND p.stockQuantity >= :quantity")
    int reduceStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);
}