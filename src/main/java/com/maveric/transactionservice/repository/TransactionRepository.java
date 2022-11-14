package com.maveric.transactionservice.repository;

import com.maveric.transactionservice.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransactionRepository extends MongoRepository<Transaction,String> {
    Page<Transaction> findByAccountId(Pageable page, String accountId);
    //void deleteByIdIn(List<String> ids);
    String deleteByAccountId(String accountId);
}
