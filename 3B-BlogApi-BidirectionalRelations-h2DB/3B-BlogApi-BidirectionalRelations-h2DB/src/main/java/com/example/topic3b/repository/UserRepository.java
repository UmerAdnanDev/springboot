package com.example.topic3b.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.topic3b.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByFullNameContaining(String keyword);
    
    // BIDIRECTIONAL QUERY - Get user with their posts
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.posts WHERE u.id = :userId")
    Optional<User> findUserWithPosts(@Param("userId") Long userId);
    
    // BIDIRECTIONAL QUERY - Find users who have written posts
    @Query("SELECT DISTINCT u FROM User u JOIN u.posts p")
    List<User> findUsersWhoHaveWrittenPosts();
}