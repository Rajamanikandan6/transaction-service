package com.maveric.transactionservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TransactionDto {
    private String _id;
    private String accountId;
    private String type;
    private Number amount;
    private String createdAt;
}
