package com.maveric.transactionservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TransactionDto {

    private String _id;
    @NotNull(message = "Account Id is mandatory")
    private String accountId;
    @NotNull(message = "Type is mandatory - 'CREDIT' or 'DEBIT'")
    private String type;
    @NotNull(message = "Amount is mandatory")
    @Min(value=0,message = "Amount cannot be less than zero")
    private String amount;
    private String createdAt;
}
