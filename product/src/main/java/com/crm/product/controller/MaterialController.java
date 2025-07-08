package com.crm.product.controller;

import com.crm.product.entities.dto.SearchMaterialCriteria;
import com.crm.product.entities.dto.request.MaterialRequestDTO;
import com.crm.product.entities.dto.request.MaterialUpdateRequestDTO;
import com.crm.product.entities.dto.response.MaterialResponseDTO;
import com.crm.product.entities.dto.response.MaterialUpdateResponseDTO;
import com.crm.product.service.MaterialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
@Slf4j
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping
    public MaterialResponseDTO create(@RequestBody MaterialRequestDTO material) {
        log.info("Received request to create material: {}", material);
        MaterialResponseDTO response = materialService.create(material);
        log.info("Material created successfully: {}", response);
        return response;
    }

    @PutMapping("/{id}")
    public MaterialUpdateResponseDTO update(@PathVariable String id,
                                            @RequestBody MaterialUpdateRequestDTO dto) {
        log.info("Received request to update material with id {}: {}", id, dto);
        MaterialUpdateResponseDTO response = materialService.update(id, dto);
        log.info("Material updated successfully with id {}: {}", id, response);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("Received request to delete material with id: {}", id);
        materialService.delete(id);
        log.info("Material deleted successfully with id: {}", id);
    }

    @GetMapping("/{id}")
    public MaterialResponseDTO getById(@PathVariable String id) {
        log.info("Fetching material by id: {}", id);
        MaterialResponseDTO response = materialService.findById(id);
        log.info("Material fetched successfully by id {}: {}", id, response);
        return response;
    }

    @GetMapping
    public Page<MaterialResponseDTO> search(SearchMaterialCriteria criteria, Pageable pageable) {
        log.info("Searching materials with criteria: {}", criteria);
        Page<MaterialResponseDTO> response = materialService.search(criteria, pageable);
        log.info("Found {} materials matching criteria", response.getTotalElements());
        return response;
    }
}
