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
        return materialService.create(material);
    }

    @PutMapping("/{id}")
    public MaterialUpdateResponseDTO update(@PathVariable String id,
                                            @RequestBody MaterialUpdateRequestDTO dto) {
        log.info("Received request to update material with id {}: {}", id, dto);
        return materialService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("Received request to delete material with id: {}", id);
        materialService.delete(id);
    }

    @GetMapping("/{id}")
    public MaterialResponseDTO getById(@PathVariable String id) {
        log.info("Fetching material by id: {}", id);
        return materialService.findById(id);
    }

    @GetMapping
    public Page<MaterialResponseDTO> search(SearchMaterialCriteria criteria, Pageable pageable) {
        log.info("Searching materials with criteria: {}", criteria);
        return materialService.search(criteria, pageable);
    }
}

