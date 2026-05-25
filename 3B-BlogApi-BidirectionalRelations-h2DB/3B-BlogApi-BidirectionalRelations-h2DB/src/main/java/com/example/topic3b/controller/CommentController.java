package com.example.topic3b.controller;
import com.example.topic3b.entity.Comment;
import com.example.topic3b.entity.Post;
import com.example.topic3b.repository.CommentRepository;
import com.example.topic3b.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    // CREATE comment (bidirectional - both sides set)
    @PostMapping
    public Comment createComment(@RequestBody CommentRequest request) {
        Post post = postRepository.findById(request.getPostId())
            .orElseThrow(() -> new RuntimeException("Post not found: " + request.getPostId()));
        
        Comment comment = new Comment(request.getAuthorName(), request.getContent());
        
        // Set both sides of bidirectional relationship using helper
        post.addComment(comment);  // Sets comment.post AND adds comment to post.comments
        
        return commentRepository.save(comment);
    }
    
    // GET all comments
    @GetMapping
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
    
    // GET comment by ID
    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Comment not found: " + id));
    }
    
    // GET comment with its post (BIDIRECTIONAL)
    @GetMapping("/{id}/with-post")
    public Comment getCommentWithPost(@PathVariable Long id) {
        return commentRepository.findCommentWithPost(id);
    }
    
    // GET comments by post ID
    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentRepository.findByPostId(postId);
    }
    
    // GET comments by author name
    @GetMapping("/author/{authorName}")
    public List<Comment> getCommentsByAuthor(@PathVariable String authorName) {
        return commentRepository.findByAuthorName(authorName);
    }
    
    // GET comments with post details for a specific post
    @GetMapping("/post/{postId}/with-details")
    public List<Comment> getCommentsWithPostDetails(@PathVariable Long postId) {
        return commentRepository.findCommentsWithPostDetails(postId);
    }
    
    // UPDATE comment
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment commentDetails) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Comment not found: " + id));
        
        comment.setContent(commentDetails.getContent());
        
        return commentRepository.save(comment);
    }
    
    // DELETE comment
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return "Comment deleted: " + id;
    }
    // Inner class for request
    static class CommentRequest {
        private String authorName;
        private String content;
        private Long postId;
        
        public String getAuthorName() { return authorName; }
        public void setAuthorName(String authorName) { this.authorName = authorName; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public Long getPostId() { return postId; }
        public void setPostId(Long postId) { this.postId = postId; }
    }
}