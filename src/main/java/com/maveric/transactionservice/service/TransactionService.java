package com.maveric.transactionservice.service;

import com.maveric.transactionservice.dto.TransactionDto    ;
import com.maveric.transactionservice.model.Account;

import java.util.List;

public interface TransactionService {
    public List<TransactionDto> getTransactionsByAccountId(Integer page, Integer pageSize,String accountId);

    public TransactionDto createTransaction(TransactionDto transaction);
    public TransactionDto getTransactionById(String transactionId);
    public String deleteTransaction(String transactionId);
   // public  String deleteAllTransaction(String accountId);
    public String deleteTransactionByAccountId(String accountId);
    void checkAccountIdforCurrentUser(List<Account> accountList, String accountId);
}
