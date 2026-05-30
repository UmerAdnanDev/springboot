package com.example.topic4a.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String transactionReference;
    
    @Column(nullable = false)
    private String transactionType; // DEPOSIT, WITHDRAWAL, TRANSFER
    
    @Column(nullable = false)
    private String fromAccountNumber;
    
    @Column(nullable = false)
    private String toAccountNumber;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal fee;
    
    @Column(nullable = false, length = 20)
    private String status; // PENDING, COMPLETED, FAILED
    
    private String description;
    
    private LocalDateTime timestamp;
    
    // Manual date setting
    public Transaction(String transactionReference, String transactionType, String fromAccountNumber, String toAccountNumber,BigDecimal amount, BigDecimal fee, String status, String description) {
        this.transactionReference = transactionReference;
        this.transactionType = transactionType;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.fee = fee;
        this.status = status;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }
}