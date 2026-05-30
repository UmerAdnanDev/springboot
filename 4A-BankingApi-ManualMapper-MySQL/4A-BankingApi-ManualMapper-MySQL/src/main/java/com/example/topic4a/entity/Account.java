package com.example.topic4a.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 20)
    private String accountNumber;
    
    @Column(nullable = false)
    private String accountType; // SAVINGS, CURRENT
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;
    
    @Column(nullable = false)
    private Boolean isActive;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    //Break the infinite loop in bidirectional relationships
    @ToString.Exclude //Don't print this field
    @EqualsAndHashCode.Exclude //Don't use this field for equality
    private User user;
    
    // Manual date setting
    public Account(String accountNumber, String accountType, BigDecimal balance, User user) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance != null ? balance : BigDecimal.ZERO;
        this.isActive = true;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}