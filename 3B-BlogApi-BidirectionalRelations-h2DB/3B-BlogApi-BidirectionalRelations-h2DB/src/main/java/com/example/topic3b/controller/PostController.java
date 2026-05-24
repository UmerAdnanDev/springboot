package com.example.topic3b.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.topic3b.entity.Category;
import com.example.topic3b.entity.Post;
import com.example.topic3b.entity.User;
import com.example.topic3b.repository.CategoryRepository;
import com.example.topic3b.repository.PostRepository;
import com.example.topic3b.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    // CREATE post with author (bidirectional - both sides set)
    @PostMapping
    public Post createPost(@RequestBody PostRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Post post = new Post(request.getTitle(), request.getContent());
        
        // Set both sides of bidirectional relationship using helper
        user.addPost(post);  // Sets post.user AND adds post to user.posts
        
        // Add categories if provided
        if (request.getCategoryIds() != null) {
            for (Long catId : request.getCategoryIds()) {
                Category category = categoryRepository.findById(catId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
                post.addCategory(category);  // Sets both sides
            }
        }
        
        return postRepository.save(post);
    }
    
    // GET all posts
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // GET post by ID
    @GetMapping("/{id}")
     public Post getPostById(@PathVariable Long id) {
    return postRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }
    // GET post with author and comments (bidirectional fetch)
    @GetMapping("/{id}/details")
    public Post getPostWithDetails(@PathVariable Long id) {
        return postRepository.findPostWithAuthorAndComments(id);
    }
    
    // GET posts by category (from Post side)
    @GetMapping("/category/{categoryName}")
    public List<Post> getPostsByCategory(@PathVariable String categoryName) {
        return postRepository.findPostsByCategoryName(categoryName);
    }
    
    // PUT update post
    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found"));
        
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        
        return postRepository.save(post);
    }
    // Delete post by ID 
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
    postRepository.deleteById(id);
    return "Post deleted: " + id;
    }
    // GET comment count per post (aggregation)
    @GetMapping("/stats/comments")
    public List<Object[]> getCommentStats() {
        return postRepository.getCommentCountPerPost();
    }
    // Inner class for request
    static class PostRequest {
        private String title;
        private String content;
        private Long userId;
        private List<Long> categoryIds;
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public List<Long> getCategoryIds() { return categoryIds; }
        public void setCategoryIds(List<Long> categoryIds) { this.categoryIds = categoryIds; }
    }
}