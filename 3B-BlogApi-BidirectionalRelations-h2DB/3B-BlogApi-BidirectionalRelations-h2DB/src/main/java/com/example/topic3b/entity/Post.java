package com.example.topic3b.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String content;
    private LocalDateTime publishedAt;
    
    /**
     * BIDIRECTIONAL Many-to-One with User (OWNER side)
     * This side owns the relationship (has user_id foreign key)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    /**
     * BIDIRECTIONAL One-to-Many with Comment (INVERSE side)
     * mappedBy = "post" - Comment entity owns the relationship
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();
    
    /**
     * BIDIRECTIONAL Many-to-Many with Category (OWNER side)
     * This side OWNS the relationship (has @JoinTable)
     * NO @JsonIgnore - We want to see categories when fetching a post
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "post_category",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();
    
    public Post() {}
    
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.publishedAt = LocalDateTime.now();
    }
    
    // Helper methods to maintain both sides
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }
    
    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
    
    public void addCategory(Category category) {
        categories.add(category);
        category.getPosts().add(this);
    }
    
    public void removeCategory(Category category) {
        categories.remove(category);
        category.getPosts().remove(this);
    }
    
    // Helper method to clear all categories (for deletion)
    public void clearCategories() {
        for (Category category : new ArrayList<>(categories)) {
            category.getPosts().remove(this);
        }
        this.categories.clear();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
    
    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }
}