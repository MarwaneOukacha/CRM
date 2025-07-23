package com.crm.partner.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PartnerCodeGenerator {
    private static final Random RANDOM = new Random();

    public static String generatePartnerCode() throws Exception {
        // Generate product code: "PAR-" + yyyyMMdd + "-" + 4 random digits
        String datePart = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String randomPart = String.format("%04d", RANDOM.nextInt(10000));
        String productCode = "PAR-" + datePart + "-" + randomPart;
        return productCode;
    }
}
