package com.example.topic4a.controller;

import com.example.topic4a.dto.request.AccountRequest;
import com.example.topic4a.dto.request.DepositRequest;
import com.example.topic4a.dto.request.UpdateAccountRequest;
import com.example.topic4a.dto.request.WithdrawRequest;
import com.example.topic4a.dto.response.AccountResponse;
import com.example.topic4a.dto.response.BalanceResponse;
import com.example.topic4a.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    
    private final AccountService accountService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody AccountRequest request) {
        return accountService.createAccount(request);
    }
    
    @PostMapping("/deposit")
    public BalanceResponse deposit(@Valid @RequestBody DepositRequest request) {
        return accountService.deposit(request);
    }
    
    @PostMapping("/withdraw")
    public BalanceResponse withdraw(@Valid @RequestBody WithdrawRequest request) {
        return accountService.withdraw(request);
    }
    
    @PutMapping("/{accountNumber}")
    public AccountResponse updateAccount(
            @PathVariable String accountNumber,
            @Valid @RequestBody UpdateAccountRequest request) {
        return accountService.updateAccount(accountNumber, request);
    }
    
    @GetMapping("/{accountNumber}/balance")
    public BalanceResponse getBalance(@PathVariable String accountNumber) {
        return accountService.getBalance(accountNumber);
    }
    
    @GetMapping("/user/{userId}")
    public List<AccountResponse> getUserAccounts(@PathVariable Long userId) {
        return accountService.getUserAccounts(userId);
    }
    
    @GetMapping("/{accountNumber}")
    public AccountResponse getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }
}