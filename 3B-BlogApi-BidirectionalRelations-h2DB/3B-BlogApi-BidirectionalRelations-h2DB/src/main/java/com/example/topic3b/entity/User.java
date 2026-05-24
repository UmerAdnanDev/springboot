package com.example.topic3b.entity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String email;
    private String fullName;
    private LocalDateTime registeredAt;
    
    /**
     * BIDIRECTIONAL One-to-Many with Post
     * mappedBy = "user" - Post entity owns the relationship (has user_id column)
     * JsonIgnore - Prevents infinite loop when serializing User → Posts → User
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  // Without this, JSON infinite loop!
    private List<Post> posts = new ArrayList<>();
    
    public User() {}
    
    public User(String username, String email, String fullName) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.registeredAt = LocalDateTime.now();
    }
    
    // Helper method to maintain both sides
    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }
    
    public void removePost(Post post) {
        posts.remove(post);
        post.setUser(null);
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
    
    public List<Post> getPosts() { return posts; }
    public void setPosts(List<Post> posts) { this.posts = posts; }
}