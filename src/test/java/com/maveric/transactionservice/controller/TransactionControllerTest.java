package com.maveric.transactionservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.maveric.transactionservice.constants.Currency;
import com.maveric.transactionservice.constants.Type;
import com.maveric.transactionservice.dto.Balance;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.feignconsumer.BalanceServiceConsumer;
import com.maveric.transactionservice.service.TransactionService;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.maveric.transactionservice.TransactionServiceApplicationTests.apiV1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ContextConfiguration(classes=TransactionController.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(TransactionController.class)
@Tag("Integration Tests")

public class TransactionControllerTest {
    @Autowired
    private MockMvc mock;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private BalanceServiceConsumer balanceServiceConsumer;


    @Test
    public void shouldGetStatus200WhenRequestMadeTogetTransactions() throws Exception
    {
        mock.perform(get(apiV1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void shouldGetStatus200WhenRequestMadeTogetTransactionsByAccountId() throws Exception
    {
        mock.perform(get("/api/v1/accounts/1/transaction")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldGetStatus200WhenRequestMadeToGetTransactionDetails() throws Exception
    {
        mock.perform(get(apiV1+"/transactionId1").header("userId","32554364643")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldGetStatus200WhenRequestMadeToPostTransactionDetails() throws Exception
    {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAccountId("33");
        transactionDto.setAmount(34900);
        transactionDto.setType(Type.CREDIT);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(transactionDto );
        when(balanceServiceConsumer.getBalanceAccountDetails(any(),any())).thenReturn(getSampleBalance());
        mock.perform(post("/api/v1/accounts/33/transactions").header("userId","32554364643")
                        .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                        .andExpect(status().isOk());
    }

    @Test
    public void shouldGetStatus404WhenRequestMadeToPostTransactionDetails() throws Exception
    {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(34900);
        transactionDto.setType(Type.CREDIT);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(transactionDto );
        mock.perform(post("/api/v1/accounts/33/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void shouldGetStatus200WhenRequestMadeToDeleteTransaction() throws Exception
    {
        mock.perform(delete(apiV1+"/transactionId1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    public Balance getSampleBalance(){

        Balance balance = new Balance();
        balance.setCurrency(Currency.INR);
        balance.setAccountId("4");
        balance.setAmount("200");
        return balance;
    }
}
