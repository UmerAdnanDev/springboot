package com.example.topic4a.mapper;

import com.example.topic4a.dto.response.TransactionResponse;
import com.example.topic4a.entity.Transaction;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionMapper {
    
    // Convert Entity to Response
    public static TransactionResponse toResponse(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setTransactionReference(transaction.getTransactionReference());
        response.setTransactionType(transaction.getTransactionType());
        response.setFromAccountNumber(transaction.getFromAccountNumber());
        response.setToAccountNumber(transaction.getToAccountNumber());
        response.setAmount(transaction.getAmount());
        response.setFee(transaction.getFee());
        response.setStatus(transaction.getStatus());
        response.setDescription(transaction.getDescription());
        response.setTimestamp(transaction.getTimestamp());
        
        return response;
    }
    
    // Convert List<Entity> to List<Response>
    public static List<TransactionResponse> toResponseList(List<Transaction> transactions) {
        if (transactions == null) {
            return null;
        }
        return transactions.stream()
            .map(TransactionMapper::toResponse)
            .collect(Collectors.toList());
    }
}