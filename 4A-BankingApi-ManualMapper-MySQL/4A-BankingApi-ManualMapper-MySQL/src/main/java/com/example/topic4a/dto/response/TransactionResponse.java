package com.example.topic4a.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private Long id;
    private String transactionReference;
    private String transactionType;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private BigDecimal fee;
    private String status;
    private String description;
    private LocalDateTime timestamp;
}