package com.cinema.theatrepro.shared.utils;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        return formatter.format(date);
    }

    public static String extractTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
        return formatter.format(date);
    }

    public static Date parseDateOnly(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatReadableDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy");
        return formatter.format(date);
    }
}
