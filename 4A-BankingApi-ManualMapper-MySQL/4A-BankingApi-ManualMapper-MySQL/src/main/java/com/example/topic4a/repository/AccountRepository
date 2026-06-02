package com.example.topic4a.repository;

import com.example.topic4a.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByAccountNumber(String accountNumber);
    
    List<Account> findByUserId(Long userId);
    
    List<Account> findByIsActiveTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.balance = a.balance + :amount WHERE a.accountNumber = :accountNumber")
    int addBalance(@Param("accountNumber") String accountNumber, @Param("amount") BigDecimal amount);
    
    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.balance = a.balance - :amount WHERE a.accountNumber = :accountNumber AND a.balance >= :amount")
    int deductBalance(@Param("accountNumber") String accountNumber, @Param("amount") BigDecimal amount);
    
    boolean existsByAccountNumber(String accountNumber);
}