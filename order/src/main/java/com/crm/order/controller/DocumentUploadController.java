package com.crm.order.controller;

import com.crm.order.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class DocumentUploadController {

    @Value("${app.file-url}")
    private String UPLOAD_DIR;
    private final FileRepository fileRepository;

    @PostMapping("/upload")
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

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileId") String fileId) {
        log.info("Download request received for fileId: {}", fileId);
        try {
            com.crm.order.entities.File fileEntity = fileRepository.findById(UUID.fromString(fileId))
                    .orElseThrow(() -> new RuntimeException("File not found"));
            log.info("Found file entity: {}, path: {}", fileEntity.getName(), fileEntity.getUrl());

            File physicalFile = new File(fileEntity.getUrl());
            if (!physicalFile.exists()) {
                log.warn("Physical file not found at path: {}", fileEntity.getUrl());
                return ResponseEntity.notFound().build();
            }

            byte[] fileBytes = org.springframework.util.FileCopyUtils.copyToByteArray(physicalFile);
            log.info("File read successfully. Size: {} bytes", fileBytes.length);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + fileEntity.getName() + "\"")
                    .header("Content-Type", "application/pdf")  // <-- Make sure it's PDF
                    .body(fileBytes);

        } catch (IOException e) {
            log.error("IOException during file download", e);
            return ResponseEntity.status(500).body("Download failed: " + e.getMessage());
        }catch (RuntimeException e) {
            log.warn("RuntimeException: {}", e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }




}
