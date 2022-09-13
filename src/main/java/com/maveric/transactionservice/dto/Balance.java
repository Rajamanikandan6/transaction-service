package com.maveric.transactionservice.dto;

import com.maveric.transactionservice.constants.Currency;
import lombok.Data;

@Data
public class Balance {
    private String id;
    private String amount;
    private Currency currency;
    private String accountId;
}
