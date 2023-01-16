package com.demo.exchangeservice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd";


    public static Date format(String date, String format) throws ParseException {
        if (StringUtils.isEmpty(format)) {
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }
}
