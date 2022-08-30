package com.maveric.transactionservice.service;

import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.model.Transaction;
import com.maveric.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.maveric.transactionservice.util.ModelDtoTransformer.toDto;

@Service
public class TransactionServiceExec implements TransactionService{

    @Autowired
    TransactionRepository repository;


    @Override
    public List<TransactionDto> getTransactions() {
        List<Transaction> list= repository.findAll();
        List<TransactionDto> listDto = new ArrayList<TransactionDto>(list.size());
        for(Transaction transaction:list)
        {
            listDto.add(toDto(transaction));
        }
        return listDto;
    }

    @Override
    public TransactionDto createTransaction(TransactionDto transaction) {
        return null;
    }

    @Override
    public TransactionDto getTransactionById(String transactionId) {
        return null;
    }

    @Override
    public String deleteTransaction(String transactionId) {
        return null;
    }
}
