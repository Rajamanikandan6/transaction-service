package com.maveric.transactionservice.exception;

public class BalanceInsufficientException extends RuntimeException{
    public BalanceInsufficientException(String message)
    {
        super(message);
    }
}
