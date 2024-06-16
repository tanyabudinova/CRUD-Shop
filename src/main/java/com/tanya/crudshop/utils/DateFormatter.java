package com.tanya.crudshop.utils;

import java.time.LocalDateTime;

public class DateFormatter {
    public static String format(LocalDateTime dateTime) {
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
