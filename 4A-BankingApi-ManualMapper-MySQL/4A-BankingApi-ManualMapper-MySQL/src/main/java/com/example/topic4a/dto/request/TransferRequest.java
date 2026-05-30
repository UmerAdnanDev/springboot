package com.example.topic4a.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferRequest {
    
    @NotBlank(message = "Source account number is required")
    private String fromAccountNumber;
    
    @NotBlank(message = "Destination account number is required")
    private String toAccountNumber;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be at least $0.01")
    @DecimalMax(value = "999999.99", message = "Amount cannot exceed $999,999.99")
    private BigDecimal amount;
    
    @Size(max = 200, message = "Description cannot exceed 200 characters")
    private String description;
}