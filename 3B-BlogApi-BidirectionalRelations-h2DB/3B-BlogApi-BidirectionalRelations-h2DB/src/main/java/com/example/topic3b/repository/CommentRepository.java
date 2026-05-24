package com.example.topic3b.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.topic3b.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByPostId(Long postId);
    List<Comment> findByAuthorName(String authorName);
    
    // BIDIRECTIONAL - Get comment with its post
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.post WHERE c.id = :commentId")
    Comment findCommentWithPost(@Param("commentId") Long commentId);
    
    // BIDIRECTIONAL - Get all comments for a post with post details
    @Query("SELECT c FROM Comment c JOIN FETCH c.post WHERE c.post.id = :postId")
    List<Comment> findCommentsWithPostDetails(@Param("postId") Long postId);
}