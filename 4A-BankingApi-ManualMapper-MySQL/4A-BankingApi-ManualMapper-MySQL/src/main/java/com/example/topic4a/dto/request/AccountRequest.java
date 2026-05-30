package com.example.topic4a.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountRequest {
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "Account type is required")
    @Pattern(regexp = "^(SAVINGS|CURRENT)$", message = "Account type must be SAVINGS or CURRENT")
    private String accountType;
    
    @PositiveOrZero(message = "Initial deposit cannot be negative")
    @DecimalMax(value = "1000000", message = "Initial deposit cannot exceed $1,000,000")
    private BigDecimal initialDeposit;
}