package com.maveric.transactionservice.repository;

import com.maveric.transactionservice.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction,String> {
    List<Transaction> findByAccountId(String accountId);
}
