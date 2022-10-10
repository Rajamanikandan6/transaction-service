package com.maveric.transactionservice.service;

import com.maveric.transactionservice.constants.Type;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.exception.TransactionNotFoundException;
import com.maveric.transactionservice.mapper.TransactionMapperImpl;
import com.maveric.transactionservice.model.Transaction;
import com.maveric.transactionservice.repository.TransactionRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.maveric.transactionservice.TransactionServiceApplicationTests.getTransaction;
import static com.maveric.transactionservice.TransactionServiceApplicationTests.getTransactionDto;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class TransactionServiceTest {
    @InjectMocks
    private TransactionServiceExec service;

    @Mock
    private TransactionRepository repository;

    @Mock
    private TransactionMapperImpl mapper;

    @Mock
    private Page pageResult;


    @Test
    public void testCreateTransaction() throws Exception{
        when(mapper.map(any(TransactionDto.class))).thenReturn(getTransaction());
        when(mapper.map(any(Transaction.class))).thenReturn(getTransactionDto());
        when(repository.save(any())).thenReturn(getTransaction());

        TransactionDto transactionDto = service.createTransaction(getTransactionDto());

        assertSame(transactionDto.getAccountId(), getTransaction().getAccountId());
    }

    @Test
    public void testGetTransactions() {
        Page<Transaction> pagedResponse = new PageImpl(Arrays.asList(getTransaction(),getTransaction()));
        when(repository.findAll(any(Pageable.class))).thenReturn(pagedResponse);
        when(pageResult.hasContent()).thenReturn(true);
        when(pageResult.getContent()).thenReturn(Arrays.asList(getTransaction(),getTransaction()));
        when(mapper.mapToDto(any())).thenReturn(Arrays.asList(getTransactionDto(),getTransactionDto()));

        List<TransactionDto> transactions = service.getTransactions(1,1);

        assertEquals("1234", transactions.get(0).getAccountId());
        assertEquals(Type.CREDIT, transactions.get(1).getType());
    }



    @Test
    public void testGetTransactionById() {
        when(repository.findById("123")).thenReturn(Optional.of(getTransaction()));
        when(mapper.map(any(Transaction.class))).thenReturn(getTransactionDto());

        TransactionDto transactionDto = service.getTransactionById("123");

        assertSame(transactionDto.getType(),getTransactionDto().getType());
    }

    @Test(expected = TransactionNotFoundException.class)
    public void testGetTransactionByIdForInvalidId() {

        TransactionDto transactionDto = service.getTransactionById(" ");
    }

    @Test
    public void testGetTransactionByAccountId() {
        when(repository.findByAccountId("123")).thenReturn(Arrays.asList(getTransaction(),getTransaction()));
        when(mapper.mapToDto(any())).thenReturn(Arrays.asList(getTransactionDto(),getTransactionDto()));

        List<TransactionDto> transactionDto = service.getTransactionsByAccountId("123");

        assertEquals("1234", transactionDto.get(0).getAccountId());
        assertEquals(Type.CREDIT, transactionDto.get(1).getType());
    }

    @Test
    public void testGetTransactionForInvalidAccountId() {
        List<TransactionDto> transactionDto = service.getTransactionsByAccountId(" ");
        assertTrue(transactionDto.isEmpty());
    }


    @Test
    public void testDeleteTransactionById() {

        when(repository.findById("123")).thenReturn(Optional.of(getTransaction()));
        willDoNothing().given(repository).deleteById("123");

        String transactionDto = service.deleteTransaction("123");

        assertSame( "Transaction deleted successfully.",transactionDto);
    }

    @Test(expected = TransactionNotFoundException.class)
    public void testDeleteTransactionByIdForInvalidId() {
        String transactionDto = service.deleteTransaction(" ");
    }
}
