package com.example.topic3b.controller;
import com.example.topic3b.entity.User;
import com.example.topic3b.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    // CREATE user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
    
    // GET all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // GET user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }
    
    // GET user by username
    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
    
    // GET user by email
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }
    
    // GET users by name containing (search)
    @GetMapping("/search/{keyword}")
    public List<User> searchUsersByName(@PathVariable String keyword) {
        return userRepository.findByFullNameContaining(keyword);
    }
    
    // GET user with their posts (BIDIRECTIONAL - new in Phase 2B)
    @GetMapping("/{id}/with-posts")
    public User getUserWithPosts(@PathVariable Long id) {
        return userRepository.findUserWithPosts(id)
            .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }
    
    // GET users who have written posts (BIDIRECTIONAL)
    @GetMapping("/active-authors")
    public List<User> getActiveAuthors() {
        return userRepository.findUsersWhoHaveWrittenPosts();
    }
    
    // UPDATE user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found: " + id));
        
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFullName(userDetails.getFullName());
        
        return userRepository.save(user);
    }
    
    // DELETE user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted: " + id;
    }
}