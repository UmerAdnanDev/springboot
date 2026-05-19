package com.example.topic3a.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.topic3a.Entity.Profile;
import com.example.topic3a.Entity.User;
import com.example.topic3a.Repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    // Create User
    @PostMapping
    public User createUser(@RequestBody UserRequest request) {
        User user = new User(request.getUsername(), request.getEmail());
        
        // Create and attach profile (OneToOne unidirectional)
        if (request.getProfile() != null) {
            Profile profile = new Profile(
                request.getProfile().getFullName(),
                request.getProfile().getPhone(),
                request.getProfile().getAddress(),
                request.getProfile().getCity(),
                request.getProfile().getPostalCode()
            );
            user.setProfile(profile);
        }
        
        return userRepository.save(user);
    }
    
    // GET all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // GET user by ID (with profile - lazy loading works inside transaction)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }
    
    // GET user by username (derived query)
    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
    
    // GET users by city (JPQL query - goes through unidirectional relationship)
    @GetMapping("/city/{city}")
    public List<User> getUsersByCity(@PathVariable String city) {
        return userRepository.findUsersByCity(city);
    }
    
    // DELETE user (cascade will delete profile too)
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted: " + id;
    }
    // GET users with active cart (JPQL with join)
    @GetMapping("/active-cart")
    public List<User> getUsersWithActiveCart() {
        return userRepository.findUsersWithActiveCart();
    }
    
    // Inner class for request body
    static class UserRequest {
        private String username;
        private String email;
        private ProfileRequest profile;
        
        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public ProfileRequest getProfile() { return profile; }
        public void setProfile(ProfileRequest profile) { this.profile = profile; }
    }
    
    static class ProfileRequest {
        private String fullName;
        private String phone;
        private String address;
        private String city;
        private String postalCode;
        
        // Getters and setters
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getPostalCode() { return postalCode; }
        public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    }
}