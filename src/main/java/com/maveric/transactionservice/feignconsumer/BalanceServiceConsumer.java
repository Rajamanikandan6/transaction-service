package com.maveric.transactionservice.feignconsumer;

import com.maveric.transactionservice.dto.Balance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "feignUser",url = "http://localhost:3015/api/v1")
public interface BalanceServiceConsumer {
    @GetMapping("/accounts/{accountId}/balances/{balanceId}")
    Balance getBalanceDetails(@PathVariable String accountId , @PathVariable String balanceId);
    @PutMapping("/accounts/{accountId}/balances/{balanceId}")
    Balance updateBalanceDetails(@RequestBody Balance balance, @PathVariable String accountId, @PathVariable String balanceId);
    @GetMapping("/accounts/{accountId}/balances")
    List<Balance> getAllBalance(@PathVariable String accountId, @RequestParam int page , @RequestParam int pageSize);


    @PutMapping("/accounts/{accountId}/balances/{balanceId}")
    Balance updateBalance(@RequestBody Balance balance, @PathVariable String accountId, @PathVariable String balanceId);
    @GetMapping("accounts/{accountId}/balances/accountBalance")
    Balance getBalanceAccountDetails(@PathVariable String accountId);
}
