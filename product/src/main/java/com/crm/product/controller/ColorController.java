package com.crm.product.controller;

import com.crm.product.entities.dto.SearchColorCriteria;
import com.crm.product.entities.dto.request.ColorRequestDTO;
import com.crm.product.entities.dto.request.ColorUpdateRequestDTO;
import com.crm.product.entities.dto.response.ColorResponseDTO;
import com.crm.product.entities.dto.response.ColorUpdateResponseDTO;
import com.crm.product.service.ColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/colors")
@RequiredArgsConstructor
@Slf4j
public class ColorController {

    private final ColorService colorService;

    @PostMapping
    public ColorResponseDTO create(@RequestBody ColorRequestDTO color) {
        log.info("ColorController::create - Creating color: {}", color);
        return colorService.create(color);
    }

    @GetMapping("/{id}")
    public ColorResponseDTO getById(@PathVariable String id) {
        log.info("ColorController::getById - Fetching color with id: {}", id);
        return colorService.getById(id);
    }

    @GetMapping
    public Page<ColorResponseDTO> search(SearchColorCriteria criteria, Pageable pageable) {
        log.info("ColorController::search - Searching colors with criteria: {}", criteria);
        return colorService.search(criteria, pageable);
    }

    @PutMapping("/{id}")
    public ColorUpdateResponseDTO update(@PathVariable String id, @RequestBody ColorUpdateRequestDTO dto) {
        log.info("ColorController::update - Updating color with id: {}, data: {}", id, dto);
        return colorService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("ColorController::delete - Deleting color with id: {}", id);
        colorService.delete(id);
        log.info("ColorController::delete - Successfully deleted color with id: {}", id);
    }
}
