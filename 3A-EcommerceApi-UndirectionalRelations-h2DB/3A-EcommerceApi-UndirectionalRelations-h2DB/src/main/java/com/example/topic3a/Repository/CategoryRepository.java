package com.example.topic3a.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.topic3a.Entity.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    //DERIVED QUERIES
    
    // Find by name
    Optional<Category> findByName(String name);
    
    // Find by name containing
    List<Category> findByNameContaining(String keyword);
}