package com.maveric.transactionservice.exception;


import com.maveric.transactionservice.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.maveric.transactionservice.constants.Constants.*;

@RestControllerAdvice
public class ExceptionControllerAdvisor {
    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorDto handleTransactionNotFoundException(TransactionNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(TRANSACTION_NOT_FOUND_CODE);
        errorDto.setMessage(exception.getMessage());
        return errorDto;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(BAD_REQUEST_CODE);
        errorDto.setMessage(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(BalanceInsufficientException.class)
    public ResponseEntity<ErrorDto> handleInsufficientException(
            BalanceInsufficientException balanceInsufficientException) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
        errorDto.setMessage(balanceInsufficientException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(AccountIDMismatch.class)
    public ErrorDto handleAccountMismatchException(AccountIDMismatch accountIDMismatch) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
        errorDto.setMessage(accountIDMismatch.getMessage());
        return errorDto;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorDto handleTypeException() {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
        errorDto.setMessage(TYPE_ERROR);
        return errorDto;
    }

//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//    public ErrorDto handleHttpRequestMethodNotSupportedException(
//            HttpRequestMethodNotSupportedException ex) {
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setCode(METHOD_NOT_ALLOWED_CODE);
//        errorDto.setMessage(METHOD_NOT_ALLOWED_MESSAGE);
//        return errorDto;
//    }
}
