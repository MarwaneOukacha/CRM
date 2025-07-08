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
        log.info("DesignerController::create - Creating designer: {}", dto);
        DesignerResponseDTO response = designerService.create(dto);
        log.info("DesignerController::create - Created designer with id: {}", response.getId());
        return response;
    }

    @GetMapping("/{id}")
    public DesignerResponseDTO getById(@PathVariable String id) {
        log.info("DesignerController::getById - Fetching designer by id: {}", id);
        DesignerResponseDTO response = designerService.getById(id);
        log.info("DesignerController::getById - Found designer: {}", response);
        return response;
    }

    @GetMapping
    public Page<DesignerResponseDTO> search(SearchDesignerCriteria criteria, Pageable pageable) {
        log.info("DesignerController::search - Searching designers with criteria: {}", criteria);
        Page<DesignerResponseDTO> results = designerService.search(criteria, pageable);
        log.info("DesignerController::search - Found {} designers", results.getTotalElements());
        return results;
    }

    @PutMapping("/{id}")
    public DesignerUpdateResponseDTO update(@PathVariable String id, @RequestBody DesignerUpdateRequestDTO dto) {
        log.info("DesignerController::update - Updating designer with id: {}, data: {}", id, dto);
        DesignerUpdateResponseDTO response = designerService.update(id, dto);
        log.info("DesignerController::update - Updated designer with id: {}", id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("DesignerController::delete - Deleting designer with id: {}", id);
        designerService.delete(id);
        log.info("DesignerController::delete - Deleted designer with id: {}", id);
    }
}
