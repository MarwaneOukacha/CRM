package com.crm.product.controller;

import com.crm.product.entities.dto.SearchDesignerCriteria;
import com.crm.product.entities.dto.request.DesignerRequestDTO;
import com.crm.product.entities.dto.request.DesignerUpdateRequestDTO;
import com.crm.product.entities.dto.response.DesignerResponseDTO;
import com.crm.product.entities.dto.response.DesignerUpdateResponseDTO;
import com.crm.product.service.DesignerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/designers")
@RequiredArgsConstructor
@Slf4j
public class DesignerController {

    private final DesignerService designerService;

    @PostMapping
    public DesignerResponseDTO create(@RequestBody DesignerRequestDTO dto) {
        log.info("Creating designer: {}", dto);
        return designerService.create(dto);
    }

    @GetMapping("/{id}")
    public DesignerResponseDTO getById(@PathVariable String id) {
        return designerService.getById(id);
    }

    @GetMapping
    public Page<DesignerResponseDTO> search(SearchDesignerCriteria criteria, Pageable pageable) {
        return designerService.search(criteria, pageable);
    }

    @PutMapping("/{id}")
    public DesignerUpdateResponseDTO   update(@PathVariable String id, @RequestBody DesignerUpdateRequestDTO dto) {
        return designerService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        designerService.delete(id);
    }
}

