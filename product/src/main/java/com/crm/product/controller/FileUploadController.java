package com.crm.product.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Value("${app.file-url}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Ensure directory exists
            Files.createDirectories(Paths.get(uploadDir));

            // Clean and rename file
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // Save file to target directory
            Path filePath = Paths.get(uploadDir, originalFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Map<String, String> response = new HashMap<>();
            response.put("url", filePath.toString()); // Return full path or just filename
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Upload failed: " + e.getMessage()));
        }
    }
}
