package com.maveric.transactionservice.mapper;

import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapperImpl implements TransactionMapper{
    @Override
    public Transaction map(TransactionDto transactionDto) {
        return new Transaction(
                transactionDto.get_id(),
                transactionDto.getAccountId(),
                transactionDto.getType(),
                transactionDto.getAmount(),
                transactionDto.getCreatedAt()
        );
    }

    @Override
    public TransactionDto map(Transaction transaction) {
        return new TransactionDto(
                transaction.get_id(),
                transaction.getAccountId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCreatedAt()
        );
    }

    @Override
    public List<Transaction> mapToModel(List<TransactionDto> transactions) {
        if(transactions.size()>0)
            return transactions.stream().map(transaction -> new Transaction(
                    transaction.get_id(),
                    transaction.getAccountId(),
                    transaction.getType(),
                    transaction.getAmount(),
                    transaction.getCreatedAt()
            )).collect(Collectors.toList());
        else
            return Collections.<Transaction>emptyList();
    }

    @Override
    public List<TransactionDto> mapToDto(List<Transaction> transactions) {
        if(transactions.size()>0)
            return transactions.stream().map(transactionDto -> new TransactionDto(
                    transactionDto.get_id(),
                    transactionDto.getAccountId(),
                    transactionDto.getType(),
                    transactionDto.getAmount(),
                    transactionDto.getCreatedAt()
            )).collect(Collectors.toList());  //Check
        else
            return Collections.<TransactionDto>emptyList();

    }

}
