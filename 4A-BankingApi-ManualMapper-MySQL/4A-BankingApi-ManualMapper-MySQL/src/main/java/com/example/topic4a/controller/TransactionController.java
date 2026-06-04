package com.example.topic4a.controller;

import com.example.topic4a.dto.request.TransferRequest;
import com.example.topic4a.dto.response.TransactionResponse;
import com.example.topic4a.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    
    private final TransactionService transactionService;
    
    @PostMapping("/transfer")
    public TransactionResponse transferMoney(@Valid @RequestBody TransferRequest request) {
        return transactionService.transferMoney(request);
    }
    
    @GetMapping("/history/{accountNumber}")
    public List<TransactionResponse> getTransactionHistory(@PathVariable String accountNumber) {
        return transactionService.getTransactionHistory(accountNumber);
    }
}