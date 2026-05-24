package com.example.topic3b.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String authorName;
    private String content;
    private LocalDateTime createdAt;
    
    /**
     * BIDIRECTIONAL Many-to-One with Post (OWNER side)
     * This side owns the relationship (has post_id foreign key)
     * No @JsonIgnore - We want to show which post this comment belongs to
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    
    public Comment() {}
    
    public Comment(String authorName, String content) {
        this.authorName = authorName;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }
}