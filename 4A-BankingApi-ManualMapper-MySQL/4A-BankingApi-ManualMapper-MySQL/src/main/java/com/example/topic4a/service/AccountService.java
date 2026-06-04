package com.example.topic4a.service;
import com.example.topic4a.dto.request.AccountRequest;
import com.example.topic4a.dto.request.DepositRequest;
import com.example.topic4a.dto.request.UpdateAccountRequest;
import com.example.topic4a.dto.request.WithdrawRequest;
import com.example.topic4a.dto.response.AccountResponse;
import com.example.topic4a.dto.response.BalanceResponse;
import com.example.topic4a.entity.Account;
import com.example.topic4a.entity.Transaction;
import com.example.topic4a.entity.User;
import com.example.topic4a.mapper.AccountMapper;
import com.example.topic4a.repository.AccountRepository;
import com.example.topic4a.repository.TransactionRepository;
import com.example.topic4a.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    
    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));
        
        // Generate unique account number
        String accountNumber = generateAccountNumber();
        
        // Set initial balance
        BigDecimal initialBalance = request.getInitialDeposit() != null ? 
            request.getInitialDeposit() : BigDecimal.ZERO;
        
        // Create account manually (no @PrePersist)
        Account account = new Account(
            accountNumber,
            request.getAccountType(),
            initialBalance,
            user
        );
        
        Account savedAccount = accountRepository.save(account);
        
        // Record initial deposit transaction if any
        if (initialBalance.compareTo(BigDecimal.ZERO) > 0) {
            createTransactionRecord(
                accountNumber,
                accountNumber,
                initialBalance,
                "DEPOSIT",
                BigDecimal.ZERO,
                "Initial deposit"
            );
        }
        
        return AccountMapper.toResponse(savedAccount);
    }
    
    @Transactional
    public BalanceResponse deposit(DepositRequest request) {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
            .orElseThrow(() -> new RuntimeException("Account not found: " + request.getAccountNumber()));
        
        // Update balance
        account.setBalance(account.getBalance().add(request.getAmount()));
        account.updateTimestamp();
        accountRepository.save(account);
        
        // Record transaction
        createTransactionRecord(
            request.getAccountNumber(),
            request.getAccountNumber(),
            request.getAmount(),
            "DEPOSIT",
            BigDecimal.ZERO,
            request.getDescription() != null ? request.getDescription() : "Cash deposit"
        );
        
        return new BalanceResponse(
            account.getAccountNumber(),
            account.getBalance(),
            "USD"
        );
    }
    
    @Transactional
    public BalanceResponse withdraw(WithdrawRequest request) {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
            .orElseThrow(() -> new RuntimeException("Account not found: " + request.getAccountNumber()));
        
        // Check sufficient balance
        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance. Available: $" + 
                account.getBalance() + ", Requested: $" + request.getAmount());
        }
        
        // Update balance
        account.setBalance(account.getBalance().subtract(request.getAmount()));
        account.updateTimestamp();
        accountRepository.save(account);
        
        // Record transaction
        createTransactionRecord(
            request.getAccountNumber(),
            request.getAccountNumber(),
            request.getAmount(),
            "WITHDRAWAL",
            BigDecimal.ZERO,
            request.getDescription() != null ? request.getDescription() : "Cash withdrawal"
        );
        
        return new BalanceResponse(
            account.getAccountNumber(),
            account.getBalance(),
            "USD"
        );
    }
    
    @Transactional
    public AccountResponse updateAccount(String accountNumber, UpdateAccountRequest request) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));
        
        if (request.getAccountType() != null) {
            account.setAccountType(request.getAccountType());
        }
        if (request.getIsActive() != null) {
            account.setIsActive(request.getIsActive());
        }
        
        account.updateTimestamp();
        Account updatedAccount = accountRepository.save(account);
        
        return AccountMapper.toResponse(updatedAccount);
    }
    
    public BalanceResponse getBalance(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));
        
        return new BalanceResponse(
            account.getAccountNumber(),
            account.getBalance(),
            "USD"
        );
    }
    
    public List<AccountResponse> getUserAccounts(Long userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        return AccountMapper.toResponseList(accounts);
    }
    
    public AccountResponse getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));
        return AccountMapper.toResponse(account);
    }
    
    private String generateAccountNumber() {
        return "ACC" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }
    
    private void createTransactionRecord(String fromAccount, String toAccount, BigDecimal amount,
                                         String type, BigDecimal fee, String description) {
        String reference = "TXN" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 5);
        
        Transaction transaction = new Transaction(
            reference, type, fromAccount, toAccount, amount, fee, "COMPLETED", description
        );
        
        transactionRepository.save(transaction);
    }
}