package com.urise.webapp.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

    private DateUtil() {
    }

    public static Date valueOf(String time) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
