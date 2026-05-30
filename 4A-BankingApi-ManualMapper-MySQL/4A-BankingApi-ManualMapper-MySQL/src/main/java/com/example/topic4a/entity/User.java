package com.example.topic4a.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    
    private String address;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //Break the infinite loop in bidirectional relationships
    @ToString.Exclude //Don't print this field
    @EqualsAndHashCode.Exclude //Don't use this field for equality
    private List<Account> accounts = new ArrayList<>();
    
    // Manual date setting
    public User(String email, String fullName, String phoneNumber, String address) {
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}