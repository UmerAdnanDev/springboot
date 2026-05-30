package com.example.topic4a.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateAccountRequest {
    
    @NotBlank(message = "Account number is required")
    private String accountNumber;
    
    @Pattern(regexp = "^(SAVINGS|CURRENT)$", message = "Account type must be SAVINGS or CURRENT")
    private String accountType;
    
    private Boolean isActive;
}