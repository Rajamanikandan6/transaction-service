package com.maveric.transactionservice.exception;

public class UserNotAllowedException extends RuntimeException {
    public UserNotAllowedException(String message)
    {
        super(message);
    }

}
