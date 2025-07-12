package com.crm.product.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;

import com.crm.product.entities.dto.ProductCodeAndBarcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

public class ProductCodeGenerator {

    private static final Random RANDOM = new Random();


    public static ProductCodeAndBarcode generateProductCodeAndBarcode() throws Exception {
        // Generate product code: "PRD-" + yyyyMMdd + "-" + 4 random digits
        String datePart = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String randomPart = String.format("%04d", RANDOM.nextInt(10000));
        String productCode = "PRD-" + datePart + "-" + randomPart;

        // Generate barcode base64 for this code
        String barcodeBase64 = generateBase64Barcode(productCode);

        return new ProductCodeAndBarcode(productCode, barcodeBase64);
    }

    private static String generateBase64Barcode(String text) throws Exception {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, 300, 100);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(image, "png", baos);
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}

