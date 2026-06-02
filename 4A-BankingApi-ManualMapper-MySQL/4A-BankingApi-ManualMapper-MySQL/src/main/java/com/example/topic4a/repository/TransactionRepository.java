package com.example.topic4a.repository;

import com.example.topic4a.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    Optional<Transaction> findByTransactionReference(String transactionReference);
    
    List<Transaction> findByFromAccountNumberOrderByTimestampDesc(String accountNumber);
    
    List<Transaction> findByToAccountNumberOrderByTimestampDesc(String accountNumber);
    
    List<Transaction> findByStatus(String status);
}