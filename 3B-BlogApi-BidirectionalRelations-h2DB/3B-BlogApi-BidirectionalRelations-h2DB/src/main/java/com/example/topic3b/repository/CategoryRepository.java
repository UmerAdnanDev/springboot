package com.example.topic3b.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.topic3b.entity.Category;
import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    
    Optional<Category> findByName(String name);
    List<Category> findByNameContaining(String keyword);
    
    // BIDIRECTIONAL QUERIES (NEW in Phase 2B!)
    
    // Get category with all its posts
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.posts WHERE c.id = :categoryId")
    Optional<Category> findCategoryWithPosts(@Param("categoryId") Long categoryId);
    
    // Get category with post count
    @Query("SELECT c.name, SIZE(c.posts) FROM Category c")
    List<Object[]> getCategoryPostCounts();
    
    // Find categories that have posts
    @Query("SELECT DISTINCT c FROM Category c JOIN c.posts p")
    List<Category> findCategoriesWithPosts();
    
    // Find categories with more than N posts
    @Query("SELECT c FROM Category c WHERE SIZE(c.posts) > :minPosts")
    List<Category> findPopularCategories(@Param("minPosts") Integer minPosts);
    
    // Find categories that contain a specific post
    @Query("SELECT c FROM Category c JOIN c.posts p WHERE p.id = :postId")
    List<Category> findCategoriesByPostId(@Param("postId") Long postId);
}