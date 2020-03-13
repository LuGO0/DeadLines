package com.example.deadlines.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class Utils {

    public static Date getDatefromString(String date) throws ParseException {

        Date resultantDate=new SimpleDateFormat("dd/MM/yyyy").parse(date);

        return resultantDate;
    }
}
