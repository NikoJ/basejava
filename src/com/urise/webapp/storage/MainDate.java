package com.urise.webapp.storage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MainDate {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Date date = new Date();
        System.out.println(date);
        System.out.println(System.currentTimeMillis() - start);

        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        System.out.println(ldt);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YY/MM/dd");
        System.out.println(simpleDateFormat.format(date));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YY/MM/dd");
        System.out.println(dateTimeFormatter.format(ldt));
    }
}
