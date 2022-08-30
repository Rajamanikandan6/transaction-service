package com.maveric.transactionservice.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Constants {

    public static String getCurrentDateTime() {
        //System.out.println("dgdgf");
        // check for different date format without using calendar
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate=dateFormat.format(date);
        return formattedDate;
    }
}
