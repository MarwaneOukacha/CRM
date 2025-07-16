package com.crm.partner.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/partners/upload")
public class DocumentUploadController {

    @Value("${app.file-url}")
    private String UPLOAD_DIR;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty.");
        }

        try {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // Create a unique folder for this file (e.g., using filename without extension)
            String folderName = file.getOriginalFilename().split("\\.")[0];
            File fileFolder = new File(uploadDir, folderName);
            if (!fileFolder.exists()) fileFolder.mkdirs();

            // Save file inside its folder
            File savedFile = new File(fileFolder, file.getOriginalFilename());
            file.transferTo(savedFile);

            return ResponseEntity.ok("File uploaded successfully in folder: " + folderName);

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }

}

