package com.example.topic4a.mapper;

import com.example.topic4a.dto.request.AccountRequest;
import com.example.topic4a.dto.response.AccountResponse;
import com.example.topic4a.entity.Account;
import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {
    
    // Convert Request to Entity
    public static Account toEntity(AccountRequest request) {
        if (request == null) {
            return null;
        }
        
        Account account = new Account();
        account.setAccountType(request.getAccountType());
        // Note: accountNumber, balance, user set separately in service
        return account;
    }
    
    // Convert Entity to Response
    public static AccountResponse toResponse(Account account) {
        if (account == null) {
            return null;
        }
        
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        response.setIsActive(account.getIsActive());
        response.setCreatedAt(account.getCreatedAt());
        response.setUpdatedAt(account.getUpdatedAt());
        
        if (account.getUser() != null) {
            response.setUserId(account.getUser().getId());
            response.setUserName(account.getUser().getFullName());
        }
        
        return response;
    }
    
    // Convert List<Entity> to List<Response>
    public static List<AccountResponse> toResponseList(List<Account> accounts) {
        if (accounts == null) {
            return null;
        }
        return accounts.stream()
            .map(AccountMapper::toResponse)
            .collect(Collectors.toList());
    }
}