package com.maveric.transactionservice.service;

import com.maveric.transactionservice.dto.TransactionDto    ;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getTransactions(Integer page, Integer pageSize);
    public List<TransactionDto> getTransactionsByAccountId(String accountId);
    public TransactionDto createTransaction(TransactionDto transaction);
    public TransactionDto getTransactionById(String transactionId);
    public String deleteTransaction(String transactionId);
   // public  String deleteAllTransaction(String accountId);
    public String deleteTransactionByAccountId(String accountId);
}
