package com.maveric.transactionservice.exception;

public class AccountIDMismatch extends RuntimeException{
    public AccountIDMismatch(String message)
    {
        super(message);
    }
}
