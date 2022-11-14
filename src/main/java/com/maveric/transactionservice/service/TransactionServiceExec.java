package com.maveric.transactionservice.service;

import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.exception.TransactionNotFoundException;
import com.maveric.transactionservice.exception.UserNotAllowedException;
import com.maveric.transactionservice.mapper.TransactionMapper;
import com.maveric.transactionservice.model.Account;
import com.maveric.transactionservice.model.Transaction;
import com.maveric.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.maveric.transactionservice.constants.Constants.*;
import static com.maveric.transactionservice.util.ModelDtoTransformer.toDto;
import static com.maveric.transactionservice.util.ModelDtoTransformer.toEntity;

@Service
public class TransactionServiceExec implements TransactionService {


   @Autowired
   TransactionRepository repository;
    @Autowired
    private TransactionMapper mapper;

    @Override
    public List<TransactionDto> getTransactionsByAccountId(Integer page, Integer pageSize,String accountId) {
        Pageable paging = PageRequest.of(page, pageSize);
        Page<Transaction> pageResult = repository.findByAccountId(paging,accountId);
        if(pageResult.hasContent()) {
            List<Transaction> transaction = pageResult.getContent();
            return mapper.mapToDto(transaction);
        } else {
            return new ArrayList<>();
        }
    }



    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        transactionDto.setCreatedAt(getCurrentDateTime());
        Transaction transaction = toEntity(transactionDto);
        Transaction transactionResult = repository.save(transaction);
        return toDto(transactionResult);
    }

    @Override
    public TransactionDto getTransactionById(String transactionId) {
        Transaction transactionResult=repository.findById(transactionId).orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        return toDto(transactionResult);
    }

    @Override
    public String deleteTransaction(String transactionId) {
        if(!repository.findById(transactionId).isPresent()){
            throw new TransactionNotFoundException(TRANSACTION_NOT_FOUND_MESSAGE+transactionId);
        }
        repository.deleteById(transactionId);
        return TRANSACTION_DELETED_SUCCESS;
    }

    @Override
    public String deleteTransactionByAccountId(String accountId) {
        repository.deleteByAccountId(accountId);
        return TRANSACTION_DELETED_SUCCESS;
    }

    @Override
    public void checkAccountIdforCurrentUser(List<Account> accountList, String accountId) {
        AtomicInteger count = new AtomicInteger(0);
        accountList.forEach(singleAccount -> {
            if (singleAccount.get_id().equals(accountId)){
                count.getAndIncrement();
            }
        });
        if(count.get() == 0){
            throw new UserNotAllowedException(UnauthorisedError);
        }
    }
//    @Override
//    public String deleteAllTransaction(String accountId) {
//        List<Transaction> transactions=repository.findByAccountId(accountId);
//        List <String> trList = transactions.stream().map(t-> t.get_id()).collect(Collectors.toList());
//        repository.deleteByIdIn(trList);
//        return TRANSACTION_DELETED_SUCCESS;
//    }

}
