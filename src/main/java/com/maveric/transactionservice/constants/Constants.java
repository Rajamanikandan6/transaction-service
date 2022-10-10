package com.maveric.transactionservice.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Constants {
    private Constants() {
    }

    public static String getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate=dateFormat.format(date);
        return formattedDate;

    }
    public static final String TRANSACTION_NOT_FOUND_CODE="404";
    public static final String TRANSACTION_NOT_FOUND_MESSAGE="Transaction not found for Id-";
    public static final String TRANSACTION_DELETED_SUCCESS="Transaction deleted successfully.";
    public static final String METHOD_NOT_ALLOWED_CODE="405";
    public static final String METHOD_NOT_ALLOWED_MESSAGE="Method Not Allowed. Kindly check the Request URL and Request Type.";
    public static final String BAD_REQUEST_CODE="400";
    public static final String BAD_REQUEST_MESSAGE="Invalid inputs!";
    public static final String INCORRECT_URL_CODE="404";
    public static final String INCORRECT_URL_MESSAGE="The server can not find the requested resource.";
    public static final String BALANCE_NOT_FOUND_MESSAGE="Balance details not found for Id-";
    public static final String INSUFFICIENT_BALANCE_MESSAGE="Insufficient Balance";
    public static final String TYPE_ERROR="Type should be 'CREDIT' or 'DEBIT'";
    public static final String ACCOUNT_ID_ERROR="account id should be same in request body and url";
}
