package com.example.topic3a.Entity;
import jakarta.persistence.*;

/*
 - USER entity: The owner side of the relationship
 - UNIDIRECTIONAL: User knows about Profile, but Profile does not know about User
 - Profile is just additional info you never need to find which user has this profile
 */
@Entity
@Table(name = "users")  // "user" is reserved keyword in H2, so use "users"
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    /**
     * ONE-TO-ONE UNIDIRECTIONAL
     - OneToOne: Each user has exactly one profile
     - cascade = CascadeType.ALL: When you save/delete user, profile is also saved/deleted
     - JoinColumn: Foreign key column in users table pointing to profile table 
     - NAVIGATION: User → Profile (YES), Profile → User (NO)
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
    
    // Constructors
    public User() {}
    
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }
}
