package com.crm.order.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utils {
    private static final Random RANDOM = new Random();

    public static String generateOrderCode() throws Exception {
        // Generate product code: "ORD-" + yyyyMMdd + "-" + 4 random digits
        String datePart = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String randomPart = String.format("%04d", RANDOM.nextInt(10000));
        String orderCode = "ORD-" + datePart + "-" + randomPart;

        return orderCode;
    }
}
