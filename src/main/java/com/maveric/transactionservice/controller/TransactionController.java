package com.maveric.transactionservice.controller;

import com.maveric.transactionservice.constants.Type;
import com.maveric.transactionservice.dto.Balance;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.exception.AccountIDMismatch;
import com.maveric.transactionservice.exception.BalanceInsufficientException;
import com.maveric.transactionservice.feignconsumer.BalanceServiceConsumer;
import com.maveric.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.maveric.transactionservice.constants.Constants.ACCOUNT_ID_ERROR;
import static com.maveric.transactionservice.constants.Constants.INSUFFICIENT_BALANCE_MESSAGE;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    BalanceServiceConsumer balanceServiceConsumer;

    @GetMapping("accounts/{accountId}/transactions")
    public ResponseEntity<List<TransactionDto>> getTransactions(@PathVariable String accountId, @RequestParam(defaultValue = "0") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        List<TransactionDto> transactionDtoResponse = transactionService.getTransactions(page,pageSize);
        return new ResponseEntity<List<TransactionDto>>(transactionDtoResponse, HttpStatus.OK);
    }

    @PostMapping("accounts/{accountId}/transactions")
    public ResponseEntity<TransactionDto> createTransaction(@Valid @RequestBody TransactionDto transactionDto,@PathVariable String accountId, @RequestHeader(value = "userId") String userId) throws Exception {

        if(transactionDto.getAccountId().equals(accountId)) {

            Balance balances = balanceServiceConsumer.getBalanceAccountDetails(transactionDto.getAccountId(), userId);
            int newAmount;
            int amount = Integer.parseInt(balances.getAmount());
            if (transactionDto.getType() == Type.DEBIT) {
                if (((Integer) transactionDto.getAmount()) < amount) {
                    newAmount = amount - (Integer) transactionDto.getAmount();
                } else {
                    throw new BalanceInsufficientException(INSUFFICIENT_BALANCE_MESSAGE);
                }
            } else {
                newAmount = (Integer) transactionDto.getAmount() + amount;
            }
            TransactionDto transactionDtoResponse = transactionService.createTransaction(transactionDto);
            balances.setAmount(String.valueOf(newAmount));
            balanceServiceConsumer.updateBalance(balances, transactionDto.getAccountId(), balances.getId(), userId);

            return new ResponseEntity<TransactionDto>(transactionDtoResponse, HttpStatus.OK);
        }else{
            throw new AccountIDMismatch(ACCOUNT_ID_ERROR);
        }
    }

    @GetMapping("accounts/{accountId}/transactions/{transactionId}")
    public ResponseEntity<TransactionDto> getTransactionDetails(@PathVariable String accountId,@PathVariable String transactionId,@RequestHeader(value = "userId") String userId) {
        TransactionDto transactionDtoResponse = transactionService.getTransactionById(transactionId);
        return new ResponseEntity<TransactionDto>(transactionDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("accounts/{accountId}/transactions/{transactionId}")
    public ResponseEntity<String> deleteTransaction(@PathVariable String accountId,@PathVariable String transactionId) {
        String result = transactionService.deleteTransaction(transactionId);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
//    @DeleteMapping("accounts/{accountId}/transactions")
//    public ResponseEntity<String> deleteAllTransaction(@PathVariable String accountId) {
//        String result = transactionService.deleteAllTransaction(accountId);
//        return new ResponseEntity<String>(result, HttpStatus.OK);
//    }
    @GetMapping("accounts/{accountId}/transaction")
    public ResponseEntity<List<TransactionDto>> getTransactionsByAccountId(@PathVariable String accountId)  {
        List<TransactionDto> transactionDtoResponse = transactionService.getTransactionsByAccountId(accountId);
        return new ResponseEntity<>(transactionDtoResponse, HttpStatus.OK);
    }
}
