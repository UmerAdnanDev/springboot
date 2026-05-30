package com.example.topic4a.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class WithdrawRequest {
    
    @NotBlank(message = "Account number is required")
    private String accountNumber;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be at least $0.01")
    @DecimalMax(value = "999999.99", message = "Amount cannot exceed $999,999.99")
    private BigDecimal amount;
    
    private String description;
}