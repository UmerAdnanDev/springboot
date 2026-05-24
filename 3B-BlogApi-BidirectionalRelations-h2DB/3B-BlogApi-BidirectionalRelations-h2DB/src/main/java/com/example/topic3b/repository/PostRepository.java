package com.example.topic3b.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.topic3b.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    List<Post> findByUserId(Long userId);
    List<Post> findByTitleContaining(String keyword);
    
    // BIDIRECTIONAL QUERIES
    
    // Get post with author (User) - FROM Post side
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.user WHERE p.id = :postId")
    Post findPostWithAuthor(@Param("postId") Long postId);
    
    // Get post with all comments - FROM Post side
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments WHERE p.id = :postId")
    Post findPostWithComments(@Param("postId") Long postId);
    
    // Get post with author AND comments (nested bidirectional)
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.user LEFT JOIN FETCH p.comments WHERE p.id = :postId")
    Post findPostWithAuthorAndComments(@Param("postId") Long postId);
    
    // Get posts by category name (from Post side)
    @Query("SELECT p FROM Post p JOIN p.categories c WHERE c.name = :categoryName")
    List<Post> findPostsByCategoryName(@Param("categoryName") String categoryName);
    
    // BIDIRECTIONAL AGGREGATION - Count comments per post
    @Query("SELECT p.title, SIZE(p.comments) FROM Post p")
    List<Object[]> getCommentCountPerPost();
}