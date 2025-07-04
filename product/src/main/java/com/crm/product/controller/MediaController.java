package com.crm.product.controller;

import com.crm.product.entities.dto.request.MediaRequestDTO;
import com.crm.product.entities.dto.response.MediaResponseDTO;
import com.crm.product.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
@Slf4j
public class MediaController {

    private final MediaService mediaService;

    @PostMapping
    public void addMedia( @RequestBody MediaRequestDTO request) {
        log.info("Received request to add media: {}", request);
        mediaService.addMedia(request);
    }

    @GetMapping("/{id}")
    public MediaResponseDTO getMediaById(@PathVariable String id) {
        log.info("Fetching media with ID: {}", id);
        return mediaService.getById(id);
    }

    @GetMapping
    public Page<MediaResponseDTO> getAllMedia(Pageable pageable) {
        log.info("Fetching all media (page: {})", pageable);
        return mediaService.findAll(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteMedia(@PathVariable String id) {
        log.info("Deleting media with ID: {}", id);
        mediaService.remove(id);
    }

    @GetMapping("/product/{productId}")
    public Page<MediaResponseDTO> getMediaByProductId(
            @PathVariable String productId,
            Pageable pageable
    ) {
        log.info("Fetching media for product ID: {}", productId);
        return mediaService.getByProductId(productId, pageable);
    }

    @PutMapping("/{id}")
    public MediaResponseDTO updateMedia(
            @PathVariable String id,
             @RequestBody MediaRequestDTO request
    ) {
        log.info("Updating media with ID: {} and payload: {}", id, request);
        return mediaService.updateMedia(id, request);
    }
}

