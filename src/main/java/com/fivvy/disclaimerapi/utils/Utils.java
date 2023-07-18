package com.fivvy.disclaimerapi.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Utils {
    private Utils () {}

    public static String getCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }
}
