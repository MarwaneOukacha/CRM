package com.crm.product.controller;

import com.crm.product.entities.dto.request.OccasionRequestDTO;
import com.crm.product.entities.dto.request.OccasionUpdateRequestDTO;
import com.crm.product.entities.dto.response.OccasionResponseDTO;
import com.crm.product.service.OccasionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/occasions")
@RequiredArgsConstructor
@Slf4j
public class OccasionController {

    private final OccasionService occasionService;

    @PostMapping
    public OccasionResponseDTO create(@RequestBody OccasionRequestDTO dto) {
        log.info("Creating occasion: {}", dto);
        return occasionService.create(dto);
    }

    @GetMapping("/{id}")
    public OccasionResponseDTO getById(@PathVariable UUID id) {
        log.info("Getting occasion by ID: {}", id);
        return occasionService.getById(id);
    }

    @GetMapping
    public List<OccasionResponseDTO> getAll() {
        log.info("Getting all occasions");
        return occasionService.getAll();
    }

    @PutMapping("/{id}")
    public OccasionResponseDTO update(@PathVariable UUID id, @RequestBody OccasionUpdateRequestDTO dto) {
        log.info("Updating occasion {} with {}", id, dto);
        return occasionService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        log.info("Deleting occasion with ID: {}", id);
        occasionService.delete(id);
    }
}
