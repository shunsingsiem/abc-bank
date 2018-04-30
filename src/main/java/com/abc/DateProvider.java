package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    private static Calendar calendar;

    private DateProvider() {
        calendar = Calendar.getInstance();
    }

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        calendar.setTimeInMillis(System.currentTimeMillis());
        return calendar.getTime();
    }
}
