package com.example.topic4a.service;

import com.example.topic4a.dto.request.TransferRequest;
import com.example.topic4a.dto.response.TransactionResponse;
import com.example.topic4a.entity.Account;
import com.example.topic4a.entity.Transaction;
import com.example.topic4a.mapper.TransactionMapper;
import com.example.topic4a.repository.AccountRepository;
import com.example.topic4a.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {
    
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    
    private static final BigDecimal TRANSFER_FEE = new BigDecimal("1.00");
    
    @Transactional
    public TransactionResponse transferMoney(TransferRequest request) {
        Account fromAccount = accountRepository.findByAccountNumber(request.getFromAccountNumber())
            .orElseThrow(() -> new RuntimeException("Source account not found: " + request.getFromAccountNumber()));
        
        Account toAccount = accountRepository.findByAccountNumber(request.getToAccountNumber())
            .orElseThrow(() -> new RuntimeException("Destination account not found: " + request.getToAccountNumber()));
        
        // Check if source account is active
        if (!fromAccount.getIsActive()) {
            throw new RuntimeException("Source account is deactivated");
        }
        
        // Calculate total deduction (amount + fee)
        BigDecimal totalDeduction = request.getAmount().add(TRANSFER_FEE);
        
        // Check sufficient balance
        if (fromAccount.getBalance().compareTo(totalDeduction) < 0) {
            throw new RuntimeException("Insufficient balance. Available: $" + 
                fromAccount.getBalance() + ", Need: $" + totalDeduction + 
                " (Amount: $" + request.getAmount() + " + Fee: $" + TRANSFER_FEE + ")");
        }
        
        // Perform transfer
        fromAccount.setBalance(fromAccount.getBalance().subtract(totalDeduction));
        fromAccount.updateTimestamp();
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
        toAccount.updateTimestamp();
        
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        
        // Create transaction record
        String reference = "TXN" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 5);
        
        Transaction transaction = new Transaction(
            reference,
            "TRANSFER",
            request.getFromAccountNumber(),
            request.getToAccountNumber(),
            request.getAmount(),
            TRANSFER_FEE,
            "COMPLETED",
            request.getDescription() != null ? request.getDescription() : "Money transfer"
        );
        
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        return TransactionMapper.toResponse(savedTransaction);
    }
    
    public List<TransactionResponse> getTransactionHistory(String accountNumber) {
        // Check if account exists
        if (!accountRepository.existsByAccountNumber(accountNumber)) {
            throw new RuntimeException("Account not found: " + accountNumber);
        }
        
        List<Transaction> transactions = transactionRepository.findByFromAccountNumberOrderByTimestampDesc(accountNumber);
        return TransactionMapper.toResponseList(transactions);
    }
}